package io.ride.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(filterName = "characterEncodingFilter", urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "encoding", value = "utf-8")})
public class CharacterEncodingFilter implements Filter {

    private static String encoding;


    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // System.out.println("=======> 编码过滤");
        // HttpServletRequest request = (HttpServletRequest) servletRequest;
        // System.out.println("filter ======> " + request.getSession().getServletContext().getRealPath("/"));

        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
