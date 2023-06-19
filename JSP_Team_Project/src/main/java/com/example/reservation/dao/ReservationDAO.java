package com.example.reservation.dao;

import com.example.reservation.vo.ReservationCntVO;
import com.example.reservation.vo.ReservationCultureVO;
import com.example.reservation.vo.ReservationVO;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReservationDAO {
    //예약관련 메서드
    void insertReservation(ReservationVO reservationVO , Connection conn);

    void insertResCulture(ReservationVO reservationVO , Integer price ,Connection conn);

    Integer selectPriceFromCulture(Long cno , Connection conn);

    boolean updateUserPaymentAmount(String id , Integer price, Connection conn);

    Long selectRno(String id,Date resDate,Connection conn);
    //예약관련 메서드


    //조회관련 메서드
    Map<Long,List<ReservationCntVO>> selectReservationCnt(Long cno);

    Integer selectResCnt(Long rno,Connection conn);
    //조회관련 메서드


    //예약취소 관련 메서드
    List<Long> selectAllRnoById(String id,Connection conn);

    ReservationCultureVO selectResCultureByRno(Long rno, Connection conn);

    void deleteResCulture(Long rno, Connection conn);

    void deleteReservation(Long rno, Connection conn);
    //예약취소 관련 메서드
}
