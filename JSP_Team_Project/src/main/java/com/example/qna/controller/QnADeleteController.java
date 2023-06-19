package com.example.qna.controller;

import com.example.common.util.MyTransactional;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.common.util.Validation.validateUser;

@WebServlet(name="qnADeleteController",value="/qnaDelete")
@Slf4j
public class QnADeleteController extends QnAController {

    @Override
    @MyTransactional
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("QnADeleteController.doGet");
        try {
            Long qqno = Long.valueOf(req.getParameter("qqno"));
            //관리자 권한 심어둠
            validateUser(req,req.getSession(),qnAService.getQnaQ(qqno).getId());
            qnAService.removeQnAQ(qqno);
            resp.sendRedirect("/project/qnaList?page="+
                    req.getParameter("page"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error(e.getMessage());
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
}
