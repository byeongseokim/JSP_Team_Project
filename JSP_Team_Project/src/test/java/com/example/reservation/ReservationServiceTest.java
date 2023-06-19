package com.example.reservation;

import com.example.AppConfig;
import com.example.culture.service.CultureService;
import com.example.reservation.dao.ReservationDAO;
import com.example.reservation.service.ReservationService;
import com.example.reservation.vo.ReservationCntVO;
import com.example.reservation.vo.ReservationVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationServiceTest {

    ReservationService reservationService = SINGLETON_UTIL.reservationService();
    ReservationDAO reservationDAO = new AppConfig().reservationDAO();

    CultureService cultureService = SINGLETON_UTIL.cultureService();

    @Test
    @DisplayName("예약 성공 케이스(유료)+예약취소")
    public void reservationTest1() throws Exception {
        //given
        String id = "test2";
        Long cno = 68L; //
        Date resDate = java.sql.Timestamp.valueOf(LocalDate.of(2022,11,5).atStartOfDay());
        //when,then
        ReservationVO reservationVO = ReservationVO
                .builder()
                .id(id)
                .resDate(resDate)
                .resCnt(5)
                .cno(cno)
                .resPrice(cultureService.getCulture(cno).getPrice())
                .build();
        Long rno = reservationService.reservation(reservationVO);
        assertTrue(reservationService.cancelReservation(id,rno));
    }

    @Test
    @DisplayName("예약 성공 케이스(무료)+예약취소")
    public void reservationTest2() throws Exception {
        //given
        String id = "user1";
        Long cno = 69L; //
        Date resDate = java.sql.Timestamp.valueOf(LocalDate.of(2022,11,7).atStartOfDay());
        //when,then
        ReservationVO reservationVO = ReservationVO
                .builder()
                .id(id)
                .resDate(resDate)
                .resCnt(10)
                .cno(cno)
                .resPrice(cultureService.getCulture(cno).getPrice())
                .build();
        Long rno = reservationService.reservation(reservationVO);
        System.out.println("rno = " + rno);
        assertTrue(reservationService.cancelReservation(id,rno));
    }

    @Test
    @DisplayName("고객 예약 조회")
    public void getReservationByIdTest() throws Exception {
        //given
        String id = "user1";
        //when
        List<ReservationVO> reservations = reservationService.getReservationsVOById(id);
        System.out.println("reservations = " + reservations);
        //then
        assertTrue(reservations.size()>0);
    }

    @Test
    @DisplayName("일자별 예약 가능인원 정보 조회")
    public void getReservationCntTest() throws Exception {
        //given
        Long cno = 30L;
//        Long cno = 31L;
        //when
        Map<Long, List<ReservationCntVO>> resCntMap = reservationDAO.selectReservationCnt(cno);
        //then
        assertTrue(resCntMap.size()>0);
        System.out.println("resCntMap = " + resCntMap);
    }
}
