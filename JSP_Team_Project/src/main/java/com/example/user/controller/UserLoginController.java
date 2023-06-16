package com.example.user.controller;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLDataException;

@WebServlet(name = "userLoginController", value = "/login")
@Slf4j
public class UserLoginController extends UserController {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login get ...");
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        req.getRequestDispatcher("WEB-INF/view/user/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login post ...");
        HttpSession session = req.getSession();
        String msg = "";

        //id pwd chk(1=아이디기억 , 2=로그인유지)
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");
        String chk2 = req.getParameter("chk2");

        //로그인체크
        try {
            if (userService.login(id, pwd)) {
                log.info("로그인 성공");
                //로그인 성공후 로그인 유지 기능이 체크되어 있으면
                if("2".equals(chk2)){
                    if(!id.equals("admin")) {
                        msg = URLEncoder.encode(id + " 로그인 기억", StandardCharsets.UTF_8);
                        Cookie cookie = new Cookie("logined_cookie", id);
                        cookie.setMaxAge(60 * 60 * 24 * 7);
                        resp.addCookie(cookie);
                    }
                    else{
                        msg=URLEncoder.encode("관리자는 로그인 기억 할 수 없습니다", StandardCharsets.UTF_8);
                    }
                }
                //관리자모드
                if ("admin".equals(id)) {
                    session.setAttribute("user", "admin");
                }
                //유저모드
                else {
                    session.setAttribute("user", id);
                }
                //로그인 성공시 홈으로 리다이렉트
                if(msg.length()>0){
                    resp.sendRedirect("/project?msg="+msg);
                }
                else{
                    resp.sendRedirect("/project");
                }
            }//if
            else{
                throw new SQLDataException("로그인 실패");
            }
        } catch (Exception e) {
            //로그인 실패시 login ... get
            e.printStackTrace();
            msg = URLEncoder.encode("아이디와 비밀번호를 확인해주세요", StandardCharsets.UTF_8);
            resp.sendRedirect("/project/login?msg="+msg);
        }
    }
}
