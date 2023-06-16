package com.example.admin.controller;

import com.example.culture.service.CultureService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@WebServlet(value="/apiRegist")
@Slf4j
public class CultureRegisterController extends HttpServlet {

    private final CultureService cultureService = SINGLETON_UTIL.cultureService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("CultureRegisterController.doGet");
            cultureService.register();
            resp.sendRedirect("/project/admin");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("api 최신화 실패");
        }

    }
}
