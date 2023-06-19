package com.example.reservation.controller;

import com.example.reservation.vo.ReservationVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.common.util.Validation.validateUser;

@WebServlet(name="reservationController",value="/reservation")
@Slf4j
public class ReservationResController extends ReservationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("ReservationController.doGet");
            if(req.getSession().getAttribute("user")==null){
                resp.sendError(401);
                return;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date resDate = df.parse
                    (req.getParameter("sel_y") + "/" +
                            req.getParameter("sel_m") + "/" +
                            req.getParameter("sel_d"));
            Integer resCnt = Integer.valueOf(req.getParameter("useNum"));
            Long cno = Long.valueOf(req.getParameter("cno"));
            String id = (String) req.getSession().getAttribute("user");
            validateUser(req,req.getSession(),id);
            ReservationVO reservationVO = ReservationVO
                    .builder()
                    .resDate(resDate)
                    .resCnt(resCnt)
                    .resPrice(cultureService.getCulture(cno).getPrice()*resCnt)
                    .cno(cno)
                    .id(id)
                    .build();
            /////////////////////////////////
            req.setAttribute("reservationVO",reservationVO);
            req.setAttribute("id",reservationVO.getId());
            req.setAttribute("resDate",reservationVO.getResDate());
            req.setAttribute("resCnt",reservationVO.getResCnt());
            req.setAttribute("cno",reservationVO.getCno());
            req.setAttribute("resPrice",reservationVO.getResPrice());
            ///////////////////////////////////
            req.setAttribute("page",req.getParameter("page"));
            req.setAttribute("reservationVO",reservationVO);
            req.setAttribute("userInfo",userService.getUser(id));
            req.setAttribute("price",cultureService.getCulture(cno).getPrice());
            req.getRequestDispatcher("WEB-INF/view/reservation/insertReservation.jsp").forward(req,resp);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("잘못된 요청");
            resp.sendError(400);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("예약 오류");
            throw new RuntimeException("예약 오류");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("ReservationController.doPost");
            if(Integer.parseInt(req.getParameter("resCnt"))<1){
                throw new IllegalStateException("잘못된 예약 인원 선택");
            }
            SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy",Locale.ENGLISH);
            Date resDate = recvSimpleFormat.parse(req.getParameter("resDate"));
            ReservationVO reservationVO = ReservationVO
                    .builder()
                    .id(req.getParameter("id"))
                    .resDate(resDate)
                    .resCnt(Integer.valueOf(req.getParameter("resCnt")))
                    .cno(Long.valueOf(req.getParameter("cno")))
                    .resPrice(Integer.valueOf(req.getParameter("resPrice")))
                    .build();
            reservationService.reservation(reservationVO);
            resp.sendRedirect("/project/reservation/result?page="+req.getParameter("page"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            log.error("잘못된 값 전달받음");
            resp.sendError(400);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("예약 db 저장 실패");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("예약 작업 실패");
        }
    }
}
