package com.example.qna.controller;

import com.example.qna.vo.QnA_Q_VO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.common.util.Validation.validateUser;

@WebServlet(name="qnAModifyController",value="/qnaModify")
@Slf4j
public class QnAModifyController extends QnAController{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("QnAModifyController.doGet");
        HttpSession session = req.getSession();
        try {
            //로그인중에만 detail.jsp 가능하게 , 쿠키부터 검색
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("logined_cookie")){
                    session.setAttribute("user", URLEncoder.encode(cookie.getValue(), StandardCharsets.UTF_8));
                }
            }
            if(session.getAttribute("user")==null){
                throw new IllegalStateException("비로그인 예외");
            }
            QnA_Q_VO qnaQ = qnAService.getQnaQ(Long.valueOf(req.getParameter("qqno")));
            validateUser(req, session, qnaQ.getId());
            req.setAttribute("qna", qnaQ);
            req.setAttribute("page", req.getParameter("page"));
            req.getRequestDispatcher("WEB-INF/view/qna/qnaModify.jsp").forward(req,resp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(400);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resp.sendRedirect("/project/qnaList?page="+
                    req.getParameter("page")+"&msg="+
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("QnAModifyController.doPost");
        HttpSession session = req.getSession();
        try {
            QnA_Q_VO qnaQ = qnAService.getQnaQ(Long.valueOf(req.getParameter("qqno")));
            validateUser(req,session,qnaQ.getId());
            qnaQ.setTitle(req.getParameter("title"));
            qnaQ.setContent(req.getParameter("content"));
            qnAService.modify(qnaQ);
            resp.sendRedirect("/project/qnaList?page="+
                    req.getParameter("page"));
        }  catch (NumberFormatException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            resp.setStatus(400);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resp.sendRedirect("/project/qnaList?page="+
                    req.getParameter("page")+"&msg="+
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
