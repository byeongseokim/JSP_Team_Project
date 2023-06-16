package com.example.admin.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="fesAnnoController",value="/fesAnno")
@Slf4j
public class FesAnnoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("FesAnnoController.doGet");
        req.getRequestDispatcher("WEB-INF/view/introduce.jsp").forward(req,resp);
    }
}
