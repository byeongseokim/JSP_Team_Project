package com.example.reservation.controller;

import com.example.reservation.vo.ReservationCntVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@WebServlet(name="reservationCntController",value="/resCnt")
@Slf4j
public class ReservationCntController extends ReservationController {

    private final Gson gson = SINGLETON_UTIL.gson();

    //자바객체를 json 객체로 응답하는 메서드
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
        log.info("ReservationCntController.doGet");
        try {
            Long cno = Long.valueOf(req.getParameter("cno"));
            Map<Long, List<ReservationCntVO>> reservationCnt = reservationService.getReservationCnt(cno);
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date parseDate = df.parse
                    (req.getParameter("selYear") + "/" +
                            req.getParameter("selMonth") + "/" +
                            req.getParameter("selDay"));
            //예약날짜가 있는경우
            for (ReservationCntVO reservationCntVO : reservationCnt.get(cno)) {
                if(reservationCntVO.getResDate().equals(parseDate)){
                    sendAsJson(resp,reservationCntVO);
                    resp.setStatus(200);
                    return;
                }
            }
            //없는경우 초기값
            ReservationCntVO reservationCntVO = ReservationCntVO
                    .builder()
                    .cno(cno)
                    .resDate(parseDate)
                    .currentResCnt(0)
                    .capacity(cultureService.getCulture(cno).getCapacity())
                    .build();
            sendAsJson(resp,reservationCntVO);
            resp.setStatus(200);
        } catch (ParseException e) {
            e.printStackTrace();
            resp.sendError(400);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("예약 가능 인원 조회 오류");
        }
    }
}
