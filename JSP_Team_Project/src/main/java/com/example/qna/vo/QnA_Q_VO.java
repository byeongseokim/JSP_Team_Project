package com.example.qna.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class QnA_Q_VO {

    private Long qqno;//QnA_Q pk
    private String id;//유저아이디
    private String title;
    private String content;
    private Integer cnt; //조회수
    private Integer commentCnt; //댓글카운트
    private Date regDate;
    private Date updateDate;
}
