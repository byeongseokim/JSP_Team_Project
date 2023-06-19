package com.example.culture.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="cultureRegisterController",value="/register")
@Slf4j
public class CultureRegisterController extends CultureController{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("register ... get");
        req.getRequestDispatcher("register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("register ... post");
        //현재 culture의 모든 데이터를 지우고 다시 등록 -> 서비스에서 tx로
        cultureService.removeAll();
        cultureService.register();
        //
        log.info("db 최신화 성공");
        resp.sendRedirect("/project");
    }
}
