package jk.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", value = "/Servlet/*")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html:charset=utf-8");
        String action = request.getPathInfo();

        if(action.equals("/calendar")){
            String y = request.getParameter("sel_y");
            String m = request.getParameter("sel_m");
            String d = request.getParameter("sel_d");

            System.out.println(y + " / " + m + " / " + d);
        }




    }
}
