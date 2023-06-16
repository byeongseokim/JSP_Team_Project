package com.example.admin.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="adminController",value = "/admin")
@Slf4j
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("AdminController.doGet");
        HttpSession session = req.getSession();
        if(session.getAttribute("user")==null||!session.getAttribute("user").equals("admin")){
            resp.sendError(400);
        }else{
            req.getRequestDispatcher("/WEB-INF/view/admin/adminPage.jsp").forward(req,resp);
        }
    }
}
