package com.example.reservation.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.common.util.Validation.validateUser;

@WebServlet(name="reservationCancelController",value="/resCancel")
@Slf4j
public class ReservationCancelController extends ReservationController{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("ReservationCancelController.doGet");
            String id = req.getParameter("id");
            Long rno = Long.valueOf(req.getParameter("rno"));
            validateUser(req,req.getSession(),id);
            reservationService.cancelReservation(id,rno);
            resp.sendRedirect("/project/myPage");
        } catch (NumberFormatException | IOException e) {
            resp.sendError(400);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("예약 취소 실패");
        }
    }
}
