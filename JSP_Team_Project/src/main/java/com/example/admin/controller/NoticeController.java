package com.example.admin.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="noticeController",value="/notice")
@Slf4j
public class NoticeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("AdminNoticeController.doGet");
        req.getRequestDispatcher("/WEB-INF/view/admin/notice.jsp").forward(req,resp);
    }
}
