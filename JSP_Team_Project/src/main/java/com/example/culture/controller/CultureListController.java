package com.example.culture.controller;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.culture.vo.CultureVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name="cultureListController",value="/list")
@Slf4j
public class CultureListController extends CultureController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("CultureListController.doGet");
        String paramPage = req.getParameter("page");
        String paramSize = req.getParameter("size");
        PageRequestVO pageRequestVO;
        try {
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
            //생성한 PageRequest객체를 전달해 pageResponse를 받아온다
            PageResponseVO<CultureVO> pageResponseVO = cultureService.getCultures(pageRequestVO);
            //받아온 pageResponse를 list.jsp에 전달
            req.setAttribute("pageResponse", pageResponseVO);
//            req.setAttribute("page",pageResponseVO.getPage());
            req.getRequestDispatcher("WEB-INF/view/culture/list.jsp").forward(req,resp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String msg = URLEncoder.encode("다시 시도해주세요", StandardCharsets.UTF_8);
            resp.sendRedirect("/project?msg="+msg);
        }
    }
}