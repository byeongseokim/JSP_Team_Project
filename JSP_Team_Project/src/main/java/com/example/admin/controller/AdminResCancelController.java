package com.example.admin.controller;

import com.example.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;
import static com.example.common.util.Validation.validateUser;

@WebServlet(name="adminResCancelController",value="/adminResCancel")
@Slf4j
public class AdminResCancelController extends HttpServlet {
    private final ReservationService reservationService = SINGLETON_UTIL.reservationService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("adminResCancelController.doGet");
            String id = req.getParameter("id");
            System.out.println("id = " + id);
            Long rno = Long.valueOf(req.getParameter("rno"));
            System.out.println("rno = " + rno);
            validateUser(req,req.getSession(),id);
            reservationService.cancelReservation(id,rno);
            resp.sendRedirect("/project/admin");
        } catch (NumberFormatException | IOException e) {
            resp.sendError(400);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("예약 취소 실패");
        }
    }
}
