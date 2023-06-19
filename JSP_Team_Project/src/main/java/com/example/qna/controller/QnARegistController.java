package com.example.qna.controller;

import com.example.qna.vo.QnA_Q_VO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.common.util.Validation.validateUser;

@WebServlet(name="qnARegistController",value="/qnaRegist")
@Slf4j
public class QnARegistController extends QnAController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("QnARegistController.doGet");
        try {
            validateUser(req.getSession());
            req.setAttribute("page",req.getParameter("page"));
            req.getRequestDispatcher("/WEB-INF/view/qna/qnaRegist.jsp").forward(req,resp);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resp.sendRedirect("/project/qnaList?page="+
                    req.getParameter("page")+"&msg="+
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("qna작성페이지 접근 실패");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("QnARegistController.doPost");
        try {
            QnA_Q_VO qnaQ = QnA_Q_VO
                    .builder()
                    .id(req.getParameter("id"))
                    .title(req.getParameter("title"))
                    .content(req.getParameter("content"))
                    .build();
            validateUser(req,req.getSession(),qnaQ.getId());
            if(!qnAService.writeQnAQ(qnaQ)){
                throw new Exception("db 작성 실패");
            }
            resp.sendRedirect("/project/qnaList?page="+req.getParameter("page"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resp.sendRedirect("/project/qnaList?page="+
                    req.getParameter("page")+"&msg="+
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("qna작성 예외");
        }
    }
}
