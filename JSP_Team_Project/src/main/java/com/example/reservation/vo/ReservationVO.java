package com.example.reservation.vo;

import com.example.culture.vo.CultureVO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 조회를 위한 예약정보 클래스
 */
@Data
@Builder
public class ReservationVO {

    private Long rno;//pk
    private String id;//유저아이디 fk
    private Date resDate;//예약희망날짜
    ///////////////////////////
    //조회를 위한 멤버변수들
    private Integer resCnt;//예약한 인원
    private Long cno;//행사번호
    private Integer resPrice;//res_culture 예약당시 결제금액
    private Date regDate;//res_culture 예약 등록일
    private CultureVO cultureVO;
}
