package com.example.qna.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class QnA_A_VO {

    private Long qano;//pk
    private Long qqno;//QnA_Q 번호 fk
    private String content;
    private Date regDate;
    private Date updateDate;
    private String id;
}
