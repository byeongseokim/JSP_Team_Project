package com.example.user.controller;

import com.example.culture.service.CultureService;
import com.example.reservation.service.ReservationService;
import com.example.reservation.vo.ReservationVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@WebServlet(name="userMyPageController",value="/myPage")
@Slf4j
public class UserMyPageController extends UserController {

    private final ReservationService reservationService = SINGLETON_UTIL.reservationService();

    private final CultureService cultureService = SINGLETON_UTIL.cultureService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("userMyPageController.doGet");
        HttpSession session = req.getSession();
        if(session.getAttribute("user")==null){
            resp.sendError(401);
            return;
        }
        String id = (String) session.getAttribute("user");
        req.setAttribute("user",userService.getUser(id));
        //예약 정보 가져와야함
        List<ReservationVO> reservationsVOById = reservationService.getReservationsVOById(id);
        req.setAttribute("reservationList",reservationsVOById);
        req.getRequestDispatcher("WEB-INF/view/user/myPage.jsp").forward(req,resp);
    }
}
