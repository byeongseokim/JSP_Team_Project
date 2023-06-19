package com.example.qna.controller;

import com.example.common.vo.PageRequestVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="qnAListController",value="/qnaList")
@Slf4j
public class QnAListController extends QnAController{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.info("QnAListController.doGet");
            String paramPage = req.getParameter("page");
            String paramSize = req.getParameter("size");
            PageRequestVO pageRequestVO;
            //쿼리스트링으로 받아온 page size를 검증후 그에 맞는 PageRequest생성
            if(paramPage==null&&paramSize==null){
                pageRequestVO = PageRequestVO.builder().build();
            }
            else if (paramPage==null||"".equals(paramPage)){
                pageRequestVO = PageRequestVO.builder()
                        .size(Integer.parseInt(paramSize))
                        .build();
            }
            else if (paramSize==null|| "".equals(paramSize)) {
                pageRequestVO = PageRequestVO.builder()
                        .page(Integer.parseInt(paramPage))
                        .build();
            }
            else{ //paramPage!=null && paramSize!=null
                pageRequestVO = PageRequestVO.builder()
                        .page(Integer.parseInt(paramPage))
                        .size(Integer.parseInt(paramSize))
                        .build();
            }
            req.setAttribute("pageResponse",qnAService.getQnAQList(pageRequestVO));
            req.getRequestDispatcher("WEB-INF/view/qna/qnaList.jsp").forward(req,resp);
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
