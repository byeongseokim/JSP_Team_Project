package com.example.user.controller;


import com.example.common.util.SingletonProvideUtil;
import com.example.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {
    //**session id 목록**
    // "user" 로그인 성공저장(일반유저 - userid,관리자 - adminid)

    //**cookie id 목록**
    //"key" 아이디기억(remember_id)
    //"remember_login" 로그인유지

    protected final UserService userService = SingletonProvideUtil.SINGLETON_UTIL.userService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
