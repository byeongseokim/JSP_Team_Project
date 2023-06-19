package com.example.qna.controller;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="qnADetailController",value = "/qnaDetail")
@Slf4j
public class QnADetailController extends QnAController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("QnADetailController.doGet");
        try {
            req.setAttribute("qna",
                    qnAService.getQnaQ(Long.valueOf(req.getParameter("qqno"))));
            req.setAttribute("page",
                    req.getParameter("page"));
            req.getRequestDispatcher("WEB-INF/view/qna/qnaDetail.jsp").forward(req,resp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            resp.sendError(400);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
