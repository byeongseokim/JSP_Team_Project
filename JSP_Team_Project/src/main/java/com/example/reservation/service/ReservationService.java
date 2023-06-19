package com.example.reservation.service;

import com.example.reservation.vo.ReservationCntVO;
import com.example.reservation.vo.ReservationVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ReservationService {

    //예약하기 (예약검증메서드{날짜,인원수,동일cno,동일시간대})
    Long reservation(ReservationVO reservationVO) throws SQLException,IllegalStateException;

    //예약조회
    List<ReservationVO> getReservationsVOById(String id);

    //예약취소
    boolean cancelReservation(String id,Long rno);

    //일자별 예약 가능인원 정보 조회
    Map<Long, List<ReservationCntVO>> getReservationCnt(Long cno);
}
