package com.example.reservation.dao;

import com.example.reservation.vo.ReservationCntVO;
import com.example.reservation.vo.ReservationCultureVO;
import com.example.reservation.vo.ReservationVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

public class JdbcReservationDAO implements ReservationDAO {

    @Override
    public void insertReservation(ReservationVO reservationVO , Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "insert into reservation (id,resDate,resCnt) values (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,reservationVO.getId());
            pstmt.setDate(2,new java.sql.Date(reservationVO.getResDate().getTime()));
            pstmt.setInt(3,reservationVO.getResCnt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("reservation 등록에 실패했습니다");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public void insertResCulture(ReservationVO reservationVO,Integer price,Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into res_culture (rno, cno, resPrice, resDate)\n" +
                    "values (?,?,?,?);";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,reservationVO.getRno());
            pstmt.setLong(2,reservationVO.getCno());
            pstmt.setInt(3,price*(reservationVO.getResCnt()));
            pstmt.setDate(4,new java.sql.Date(reservationVO.getResDate().getTime()));
            pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("insertResCultrue 등록에 실패했습니다");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public Integer selectPriceFromCulture(Long cno , Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select price from culture_res where cno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,cno);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("culture_res price 조회에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs);
            CONN_UTIL.close(pstmt);
        }
        return null;
    }

    @Override
    public boolean updateUserPaymentAmount(String id , Integer price, Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "update user_res\n" +
                    "set payment_amount = ?\n" +
                    "where id = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,price);
            pstmt.setString(2,id);
            return pstmt.executeUpdate()==1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CONN_UTIL.close(pstmt);
        }
        return false;
    }

    @Override
    public Long selectRno(String id, Date resDate,Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select rno from reservation where id = ? and resDate = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setDate(2,new java.sql.Date(resDate.getTime()));
            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getLong("rno");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONN_UTIL.close(rs);
            CONN_UTIL.close(pstmt);
        }
        return null;
    }

    @Override
    public List<Long> selectAllRnoById(String id,Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select rno from reservation where id = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            List<Long> list = new ArrayList<>();
            while(rs.next()) {
                list.add(rs.getLong(1));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("rno 리스트 조회중 예외 발생");
        } finally {
            CONN_UTIL.close(rs);
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public ReservationCultureVO selectResCultureByRno(Long rno, Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from res_culture where rno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,rno);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return ReservationCultureVO
                        .builder()
                        .rc_no(rs.getLong(1))
                        .rno(rs.getLong(2))
                        .cno(rs.getLong(3))
                        .resPrice(rs.getInt(4))
                        .resDate(new Date(rs.getDate(5).getTime()))
                        .regDate(new Date(rs.getDate(6).getTime()))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("예약_행사 테이블 조회중 예외 발생");
        } finally {
            CONN_UTIL.close(rs);
            CONN_UTIL.close(pstmt);
        }
        return null;
    }

    @Override
    public Map<Long, List<ReservationCntVO>> selectReservationCnt(Long cno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Long,List<ReservationCntVO>> resCntMap = new HashMap<>();
        List<ReservationCntVO> reservationCntVOList = new ArrayList<>();
        try {
            String sql = "select rc.cno , rc.resDate , r.resCnt, cr.capacity\n" +
                    "                    from res_culture as rc\n" +
                    "                    inner join culture_res as cr\n" +
                    "                    on rc.cno = cr.cno\n" +
                    "                    inner join reservation r on rc.rno = r.rno\n" +
                    "                    where rc.cno = ?\n" +
                    "                    group by resDate\n" +
                    "                    order by resDate;";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,cno);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                ReservationCntVO reservationCntVO = ReservationCntVO
                        .builder()
                        .cno(rs.getLong("cno"))
                        .resDate(new Date(rs.getDate("resDate").getTime()))
                        .currentResCnt(rs.getInt("resCnt"))
                        .capacity(rs.getInt("capacity"))
                        .build();
                reservationCntVOList.add(reservationCntVO);
            }
            resCntMap.put(cno,reservationCntVOList);
            return resCntMap;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("예약 가능 인원 조회중 예외 발생");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public void deleteResCulture(Long rno, Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from res_culture where rno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,rno);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("예약_행사 테이블 삭제중 예외 발생");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public void deleteReservation(Long rno, Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from reservation where rno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,rno);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("예약 테이블 삭제중 예외 발생");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public Integer selectResCnt(Long rno,Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select resCnt from reservation where rno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,rno);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("예약 테이블 조회중 예외 발생");
        } finally {
            CONN_UTIL.close(pstmt);
        }
        return null;
    }
}
