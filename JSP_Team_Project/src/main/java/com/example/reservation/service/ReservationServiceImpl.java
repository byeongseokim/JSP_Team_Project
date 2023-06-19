package com.example.reservation.service;

import com.example.common.util.MyTransactional;
import com.example.culture.dao.CultureDAO;
import com.example.culture.vo.CultureVO;
import com.example.reservation.dao.ReservationDAO;
import com.example.reservation.vo.ReservationCntVO;
import com.example.reservation.vo.ReservationCultureVO;
import com.example.reservation.vo.ReservationVO;
import com.example.user.dao.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService{

    private final ReservationDAO reservationDAO;
    private final CultureDAO<CultureVO> cultureDAO;
    private final UserDAO userDAO;



    private void validateRes(String id , Long cno , Date resDate) throws ParseException,IllegalStateException {
        Connection conn = CONN_UTIL.getConnection();
        try {
            //1.예약가능 날짜가 아닌경우
            validateRes_resDate(resDate, cultureDAO.selectOne(cno));

            //2.같은 유저의 , 같은 행사 중복 예약
            //3.같은 유저의 , 예약한 날짜에 / 다른 행사를 예약할경우
            validateRes_user(id, cno, resDate, conn);

            //4.선택한 예약이 마감됐을경우
            validateRes_cno(cno, resDate);

        } catch (ParseException | IllegalStateException e) {
            throw new IllegalStateException(e);
        } finally {
            CONN_UTIL.close(conn);
        }
    }//validateRes

    private void validateRes_cno(Long cno, Date resDate) {
        //예약인원정보를 가져와서 resDate가 같은 reservationCntVO를 찾아서 검증
        Map<Long, List<ReservationCntVO>> resCntMap
                = getReservationCnt(cno);

        List<ReservationCntVO> reservationCntVOList
                = resCntMap.get(cno);

        for (ReservationCntVO reservationCntVO : reservationCntVOList) {
            //같은 행사의 같은 예약날짜일때
            if(resDate.equals(reservationCntVO.getResDate())){
                //해당 날짜의 예약가능 인원수 체크
                if(!reservationCntVO.checkResCnt()){
                    throw new IllegalStateException("선택한 날짜는 예약 마감");
                }
            }
        }
    }//validateRes_cno

    //해당 날짜 예약 가능 인원 검증
    private void validateCapacity(Long cno, Date resDate, Integer resCnt) {
        Map<Long, List<ReservationCntVO>> longListMap = reservationDAO.selectReservationCnt(cno);
        Integer capacity = cultureDAO.selectOne(cno).getCapacity();
        List<ReservationCntVO> reservationCntVOList = longListMap.get(cno);
        if(reservationCntVOList.isEmpty()){
            if(resCnt>capacity){
                throw new IllegalStateException("예약 가능 범위 초과");
            }
        }
        for (ReservationCntVO reservationCntVO : reservationCntVOList) {
            if(resDate.equals(reservationCntVO.getResDate())){
                //해당 resDate의 현재예약수 + 지금resCnt <= capacity
                if(resCnt +reservationCntVO.getCurrentResCnt()>capacity){
                    System.out.println("capacity = " + capacity);
                    throw new IllegalStateException("예약 가능 범위 초과");
                }
            }
        }
    }

    private void validateRes_user(String id, Long cno, Date resDate, Connection conn) {
        //id 로 예약한 reservation.rno를 모두 찾고
        List<Long> rnoList = reservationDAO.selectAllRnoById(id, conn);
        //rnoList를 순회하며 예약하려는 cno가 예약행사 테이블에 존재하는지 찾는다
        rnoList.stream().forEach(rno -> {
            //예약행사 테이블
            ReservationCultureVO reservationCultureVO
                    = reservationDAO.selectResCultureByRno(rno, conn);
            if(Objects.equals(cno,reservationCultureVO.getCno())){
                //중복 cno가 확인되면
                throw new IllegalStateException("중복 행사 예약 불가");
            }
            if(resDate.getTime()==reservationCultureVO.getResDate().getTime()){
                //중복 resDate가 확인되면
                throw new IllegalStateException("중복 날짜 예약 불가");
            }
        });
    }//validateRes_user

    private void validateRes_resDate(Date resDate, CultureVO cultureVO) throws ParseException {
        if(resDate.before(new Date())){
            throw new IllegalStateException("잘못된 예약 날짜 입력");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date from = df.parse(cultureVO.getRcpt_bgn_dt());
        Date to = df.parse(cultureVO.getRcpt_end_dt());
        if(resDate.getTime()<from.getTime()|| resDate.getTime()>to.getTime()){
            throw new IllegalStateException("잘못된 예약 날짜 입력");
        }
    }//validateRes_resDate

    /**
     * 예약하기
     */
    @Override
    @MyTransactional
    public Long reservation(ReservationVO reservationVO) throws SQLException,IllegalStateException {


        Connection conn = null;
        try {
            if(reservationVO.getResCnt()<1){
                throw new IllegalStateException("잘못된 예약 인원 입력");
            }
            conn = CONN_UTIL.getConnection();
            //////////////////////////
            conn.setAutoCommit(false);
            //////////////////////////
            String id = reservationVO.getId();
            Long cno = reservationVO.getCno();
            Date resDate = reservationVO.getResDate();
            Integer resCnt = reservationVO.getResCnt();
            //예약 인원수 검증
            validateCapacity(cno, resDate, resCnt);
            //유효한 예약정보인지 검증 실패시 throw IllegalstateException
            validateRes(id,cno,resDate);
            //선택한 cno의 요금 조회
            Integer resPrice = reservationDAO.selectPriceFromCulture(reservationVO.getCno(),conn);
            if(resPrice==null||resPrice<0) {
                throw new SQLException("cultrue_res price 조회에 실패하여, reservation이 실패했습니다");
            }
            //reservation insert
            reservationDAO.insertReservation(reservationVO,conn);
            //id,resDate 그룹은 중복될수 없으니 고유한 rno 조회 가능
            Long rno = reservationDAO.selectRno(id,resDate,conn);
            reservationVO.setRno(rno);
            if(rno==null) {
                throw new SQLException("reservation rno 조회에 실패하여, reservation이 실패했습니다");
            }

            //res_culture insert
            reservationDAO.insertResCulture(reservationVO,resPrice,conn);
            // 유료일 경우 유저의 payment_amount 업데이트
            if(resPrice>0){
                Integer userPrice = getPayment_amount(id);
                userPrice += (resPrice*resCnt);
                if(!reservationDAO.updateUserPaymentAmount(id,userPrice,conn)){
                    throw new SQLException("유저 요금 업데이트 실패");
                }
            }
            ///////////////////////////
            conn.commit();
            conn.setAutoCommit(true);
            ///////////////////////////
            return rno;
        } catch (IllegalStateException | ParseException e){
            e.printStackTrace();
            log.error("잘못된 값 입력");
            throw new IllegalStateException(e.getMessage());
        } catch (SQLException e) {
            try {
                ////////////////////////
                conn.rollback();
                ////////////////////////
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new SQLException("롤백 도중 예외가 발생했습니다");
            }
            e.printStackTrace();
            throw new SQLException("conn.rollback()");
        } finally {
            CONN_UTIL.close(conn);
        }
    }//reservation



    /**
     * 고객의 예약 리스트 조회
     */
    @Override
    @MyTransactional
    public List<ReservationVO> getReservationsVOById(String id) {
        Connection conn = CONN_UTIL.getConnection();
        List<ReservationVO> reservationList = new ArrayList<>(); //resprice,resdate,regdate
        try {
            //////////////////////////
            conn.setAutoCommit(false);
            //////////////////////////
            //예약조회 (
            //id에 해당하는 rno list 가져온후
            List<Long> rnoList = reservationDAO.selectAllRnoById(id,conn);
            if(rnoList.isEmpty()){
                throw new SQLException("rnoList를 조회하지 못했습니다");
            }

            //id의 rno(예약)의 개수만큼 foreach를 순회하면서 reservationVO list를 초기화
            rnoList.stream().forEach(rno -> {
                ReservationCultureVO reservationCultureVO = reservationDAO.selectResCultureByRno(rno, conn);
//                CultureVO cultureVO = cultureDAO.selectOne(reservationCultureVO.getCno());
                ReservationVO reservationVO = ReservationVO
                        .builder()
                        .rno(rno)
                        .id(id)
                        .resDate(reservationCultureVO.getResDate())
                        .resCnt(reservationDAO.selectResCnt(rno,conn))
                        .cno(reservationCultureVO.getCno())
                        .resPrice(reservationCultureVO.getResPrice())
                        .regDate(reservationCultureVO.getRegDate())
                        .cultureVO(cultureDAO.selectOne(reservationCultureVO.getCno()))
                        .build();
                //아직 실행전인 날짜만 조회
                if(reservationCultureVO.getResDate().after(new Date())||
                        reservationCultureVO.getResDate().equals(new Date())){
                    reservationList.add(reservationVO);
                }
            });
            ///////////////////////////
            conn.commit();
            conn.setAutoCommit(true);
            ///////////////////////////
            return reservationList;
        } catch (SQLException e) {
            try {
                ////////////////////////
                conn.rollback();
                ////////////////////////
            } catch (SQLException ex) {
                ex.printStackTrace();
                log.error("롤백 도중 예외가 발생했습니다");
            }
            e.printStackTrace();
            log.error("conn.rollback()");
//            throw new RuntimeException("conn.rollback()");
        } finally {
            CONN_UTIL.close(conn);
        }
        return null;
    }//getReservationsById

    /**
     * 행사 진행일당 예약한인원수,예약정원 조회
     */
    @Override
    public Map<Long, List<ReservationCntVO>> getReservationCnt(Long cno) {
            return reservationDAO.selectReservationCnt(cno);
    }//getReservationCnt

    /**
     * 예약 취소
     */
    @Override
    @MyTransactional
    public boolean cancelReservation(String id,Long rno) {
        Connection conn = CONN_UTIL.getConnection();
        try {
            //////////////////////////
            conn.setAutoCommit(false);
            //////////////////////////
            //결제금액 취소를 위한 resPrice
            Integer resPrice = reservationDAO.selectResCultureByRno(rno,conn).getResPrice();
            //유저의 결제 업데이트
            if(resPrice<0) {
                throw new SQLException("resPrice 조회 실패");
            } else if(resPrice>0) {
                Integer userPrice = getPayment_amount(id);
                userPrice -= resPrice;
                if(!reservationDAO.updateUserPaymentAmount(id,userPrice,conn)){
                    throw new SQLException("유저 요금 업데이트 실패");
                }
            }
            //res_culture delete
            reservationDAO.deleteResCulture(rno,conn);
            //reservation delete
            reservationDAO.deleteReservation(rno,conn);

            ///////////////////////////
            conn.commit();
            conn.setAutoCommit(true);
            ///////////////////////////
            return true;
        } catch (Exception e) {
            try {
                ////////////////////////
                conn.rollback();
                ////////////////////////
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("롤백 도중 예외가 발생했습니다");
            }
            e.printStackTrace();
            throw new RuntimeException("conn.rollback()");
        } finally {
            CONN_UTIL.close(conn);
        }
    }//cancelReservation

    private Integer getPayment_amount(String id) {
        return userDAO.getById(id).getPayment_amount();
    }
}
