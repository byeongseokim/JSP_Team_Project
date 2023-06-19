package com.example.culture.controller;

import com.example.culture.vo.CultureVO;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@WebServlet(name="cultureCalController",value ="/cultureCal")
@Slf4j
public class CultureCalController extends CultureController{

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

    @Getter
    @Setter
    static class ResDate {
        Date from;
        Date to;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("CultureCalController.doGet");
        try {
            Long cno = Long.valueOf(req.getParameter("cno"));
            CultureVO culture = cultureService.getCulture(cno);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date from = df.parse(culture.getRcpt_bgn_dt());
            Date to = df.parse(culture.getRcpt_end_dt());
            ResDate resDate = new ResDate();
            resDate.setFrom(from);
            resDate.setTo(to);
            sendAsJson(resp,resDate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error("예약cnt 조회 실패");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //400에러
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("예약cnt 조회 실패");
        }
    }
}
