package io.ride.test;

import io.ride.util.CusAccessObjectUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        System.out.println("x-forwarded-for ===> " + req.getHeader("X-Forwarded-For"));

        System.out.println("test ====> " + CusAccessObjectUtil.getIpAddress(req));
        out.print(req.getRemoteAddr());
        out.print("\n");
        out.print(CusAccessObjectUtil.getIpAddress(req));
        out.flush();
    }
}
