package com.example.user.controller;



import com.example.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "userJoinController", value = "/join")
@Slf4j
public class UserJoinController extends UserController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("register ... get");
        req.getRequestDispatcher("WEB-INF/view/user/join.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("register ... post");
        //valid추가 id,pwd notblank
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");
        if("".equals(id)||"".equals(pwd)||id.contains(" ")||pwd.contains(" ")){
            log.error("잘못된 값 입력");
            throw new IllegalStateException("잘못된 id,pwd값 입력");
        }
        HttpSession session = req.getSession();
        //폼태그 입력값 유저객체에 저장
        try {
            String phone = req.getParameter("phone1")+"-"
                    + req.getParameter("phone2")+"-"
                    + req.getParameter("phone3");
            UserVO userVO = UserVO.builder()
                    .id(req.getParameter("id"))
                    .pwd(req.getParameter("pwd"))
                    .name(req.getParameter("name"))
                    .email(req.getParameter("email"))
                    .phone(phone)
                    .build();
            //회원가입후 세션에 저장
            userService.join(userVO);
            //회원 로그인 기억 세션
            session.setAttribute("user", userVO.getId());
            log.info("회원가입 성공");
            resp.sendRedirect("/project?msg="+URLEncoder.encode(userVO.getId()+"님 환영합니다", StandardCharsets.UTF_8));

        } catch (IOException | RuntimeException e) {
            //회원가입 실패시 join ... get
            e.printStackTrace();
            resp.sendRedirect("/project/join?msg="+URLEncoder.encode("회원가입에 실패했습니다(입력값을 확인해주세요)", StandardCharsets.UTF_8));
        }
    }
}
