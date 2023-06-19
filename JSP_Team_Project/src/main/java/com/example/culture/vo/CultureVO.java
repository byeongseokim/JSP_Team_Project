package com.example.culture.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CultureVO {
    //서비스명 말고 다 null가능하게

    private Long cno;
    private String svc_id;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String svc_nm="";//서비스명 SVCNM

    //지역명+장소명 = 주소
    private String area_nm="";//지역명 AREANM
    private String place_nm="";//장소명 PLACENM

    private String tel_no="";//전화번호 TELNO

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String pay_ay_nm="";//결제방법 PAYATNM (유료or무료)
    private String use_tgt_info="";//서비스대상타겟 USETGTINFO
    private String svc_url="";//서비스 바로가기 url SVCURL
    private String img_url="";//이미지경로 url IMGURL
    private String dtlcont="";//상세내용(html태그) DTLCONT //text타입으로 저장

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String svc_opn_bgn_dt="";//서비스개시시작일시 SVCOPNBGNDT
    private String svc_opn_end_dt="";//서비스개시종료일시 SVCOPNENDDT
    private String v_min="";//서비스이용 시작시간 V_MIN
    private String v_max="";//서비스이용 종료시간 V_MAX

    private String rcpt_bgn_dt="";//접수시작일시 RCPTBGNDT
    private String rcpt_end_dt="";//접수종료일시 RCPTENDDT

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**/
    private Integer capacity;//인원제한 (임의로 정해야함)
    private Integer price;//요금 (임의로 정해야함)
    /**/
    private String revstd_day_nm="";//취소기간기준정보?? REVSTDDAYNM
    private String revstd_day="";//취소기간 기준일까지 REVSTDDAY

}
//        7	SVCNM	서비스명
//        8	PAYATNM	결제방법
//        9	PLACENM	장소명
//        10	USETGTINFO	서비스대상
//        11	SVCURL	바로가기URL
//        14	SVCOPNBGNDT	서비스개시시작일시
//        15	SVCOPNENDDT	서비스개시종료일시
//        16	RCPTBGNDT	접수시작일시
//        17	RCPTENDDT	접수종료일시
//        18	AREANM	지역명
//        20	IMGURL	이미지경로
//        21	DTLCONT	상세내용
//        22	TELNO	전화번호
//        23	V_MIN	서비스이용 시작시간
//        24	V_MAX	서비스이용 종료시간
//        25	REVSTDDAYNM	취소기간 기준정보
//        26	REVSTDDAY	취소기간 기준일까지