package com.example.user.service;


import com.example.user.vo.UserVO;

public interface UserService {
    //회원가입
    void join(UserVO userVO);
    //로그인
    boolean login(String id, String pwd);
    //유저정보 조회
    UserVO getUser(String id);
}
