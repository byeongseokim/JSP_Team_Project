package com.example.review.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReviewVO {

    private Long re_no;//pk
    private String id;//유저아이디
    private Long cno;//행사번호
    private String content;
    private Integer grade;
    private Date regDate;
    private Date upDate;
}
