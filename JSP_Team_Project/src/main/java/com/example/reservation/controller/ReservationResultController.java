package com.example.reservation.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="reservationResultController",value="/reservation/result")
@Slf4j
public class ReservationResultController extends ReservationController{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("ReservationResultController.doGet");
        req.setAttribute("page",req.getParameter("page"));
        req.getRequestDispatcher("/WEB-INF/view/reservation/resultReservation.jsp").forward(req,resp);
    }
}
