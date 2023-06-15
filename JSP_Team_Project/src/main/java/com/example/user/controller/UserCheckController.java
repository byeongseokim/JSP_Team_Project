package com.example.user.controller;

import com.example.user.vo.UserVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@WebServlet(name="userCheckController",value="/check")
@Slf4j
public class UserCheckController extends UserController {

    private final Gson gson = SINGLETON_UTIL.gson();

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
        try {
            log.info("UserCheckController.doGet");
            String id = req.getParameter("id");
            UserVO user = userService.getUser(id);
            if(user==null){
                System.out.println("user is null");
                resp.sendError(400);
                return;
            }
            sendAsJson(resp,user);
            resp.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("중복 아이디 조회 에러");
        }
    }
}
