package com.example.reservation.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 예약행사 테이블
 */
@Data
@Builder
public class ReservationCultureVO {
    private Long rc_no; //pk
    private Long rno;
    private Long cno;
    private int resPrice;
    private Date resDate;
    private Date regDate;
}
