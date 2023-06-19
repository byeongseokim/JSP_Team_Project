package com.example.culture.controller;

import com.example.culture.vo.CultureVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name="cultureDetailController",value="/detail")
@Slf4j
public class CultureDetailController extends CultureController{
    //------->>예약
    //------->>리뷰

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("CultureDetailController.doGet");
        HttpSession session = req.getSession();
        String msg = "";
        try {
//            if(session.getAttribute("user")==null){
//                msg = urlEncoding("먼저 로그인을 해주세요");
//                resp.sendError(401);
//                return;
//            }
            //1. 클릭한 목록의 cno를 가져와 db에서 조회
            //2. request영역에 저장후 detail.jsp로 전달
            Long cno = Long.valueOf(req.getParameter("cno"));
            req.setAttribute("culture",cultureService.getCulture(cno));
            req.setAttribute("page",req.getParameter("page"));
            req.getRequestDispatcher("WEB-INF/view/culture/detail.jsp").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("culture 조회에 실패했습니다");
            if("".equals(msg)){
                msg = urlEncoding("다시 조회해 주세요");
            }
            resp.sendRedirect("/project?msg="+msg);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("CultureDetailController.doPost");
        HttpSession session = req.getSession();

        String res_dt = req.getParameter("res_dt"); //2022-10-29
        String cno = req.getParameter("cno");

        CultureVO culture = cultureService.getCulture(Long.valueOf(cno));
        session.setAttribute("culture",culture);
        resp.sendRedirect("/project/reservation?res_dt="+res_dt);
    }

    private String urlEncoding(String msg){
        return URLEncoder.encode(msg, StandardCharsets.UTF_8);
    }
}
