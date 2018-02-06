package io.ride.filter;


import io.ride.DTO.ResultDTO;
import io.ride.PO.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "adminFilter", urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {
    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("=====> 此页面需要管理员权限");

        HttpServletRequest req = (HttpServletRequest) request;
        PrintWriter out = response.getWriter();

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getType() != 0) {
            out.print(ResultDTO.FAIL("此页面需要管理员权限"));
            out.flush();
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
