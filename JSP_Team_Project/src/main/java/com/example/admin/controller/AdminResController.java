package com.example.admin.controller;

import com.example.culture.service.CultureService;
import com.example.reservation.service.ReservationService;
import com.example.reservation.vo.ReservationVO;
import com.example.user.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;
import static com.example.common.util.Validation.validateUser;

@WebServlet(name="adminResController",value="/adminRes")
@Slf4j
public class AdminResController extends HttpServlet {
    private final ReservationService reservationService = SINGLETON_UTIL.reservationService();

    private final CultureService cultureService = SINGLETON_UTIL.cultureService();
    private final UserService userService = SINGLETON_UTIL.userService();
    private final Gson gson = SINGLETON_UTIL.gson();

    private void sendAsJson(HttpServletResponse response,
                            Object obj) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        String json = gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(json);
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("adminResCancelController.doGet");
            String id = req.getParameter("id");
            validateUser(req,req.getSession(), id);
            //예약 정보 가져와야함
            List<ReservationVO> reservationsVOById = reservationService.getReservationsVOById(id);
            if(reservationsVOById.isEmpty()){
                resp.sendError(400);
            }
            System.out.println("reservationsVOById!!!!!!!!!!!!!!!!!!!! = " + reservationsVOById);
            sendAsJson(resp, reservationsVOById);
            resp.setStatus(200);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            log.error("예약조회 실패");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //400에러
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("예약조회 예외");
        }
    }
}
