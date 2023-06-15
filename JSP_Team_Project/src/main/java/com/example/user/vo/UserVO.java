package com.example.user.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserVO {

    private String id; // pk
    private String pwd;
    private String name;
    private Date regDate;
    private String email;
    private String phone;
    private Integer payment_amount;
}
