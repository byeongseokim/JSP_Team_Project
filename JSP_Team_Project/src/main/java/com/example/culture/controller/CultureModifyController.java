package com.example.culture.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="cultureModifyController",value="/modify")
@Slf4j
public class CultureModifyController extends CultureController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("CultureModifyController.doGet");
        Long cno = Long.valueOf(req.getParameter("cno"));
        //선택한 culture의 cno를 가져와 수정 페이지로 보냄

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
