package com.example.user.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "userLogoutController", value = "/logout")
@Slf4j
public class UserLogoutController extends UserController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("logout ... get");
        HttpSession session = req.getSession();
        //
        session.removeAttribute("user");
        Cookie cookie = new Cookie("logined_cookie","delete");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        log.info("로그아웃 합니다");
        resp.sendRedirect("/project");
    }
}
