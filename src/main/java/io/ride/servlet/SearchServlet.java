package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.service.SearchService;
import io.ride.service.impl.SearchServiceImpl;
import io.ride.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private PrintWriter out;
    private SearchService searchService = new SearchServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");

        ResultDTO result;

        try {
            if (StringUtils.equals(op, "search")) {
                String search = request.getParameter("search");
                String content = request.getParameter("content");
                System.out.println("search servlet search ====> " + search);
                System.out.println("search servlet content ====> " + content);

                if (StringUtils.isEmpty(content)) {
                    out.print(JacksonUtil.toJSon(ResultDTO.FAIL("查询内容不能为空(ᇂдᇂ )")));
                    out.flush();
                    return;
                }

                if (StringUtils.equals(search, "user")) {
                    result = searchService.searchUser(content);
                    out.print(JacksonUtil.toJSon(result));
                    out.flush();
                } else if (StringUtils.equals(search, "vote")) {

                }
            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("数据库异常")));
            out.flush();
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("search servlet doPost()...");

        out = response.getWriter();
        String op = request.getParameter("op");

        if (StringUtils.equals(op, "search")) {
            String content = request.getParameter("content");
            // request.getSession().setAttribute("content", content);
            request.setAttribute("content", content);
            request.getRequestDispatcher("/pages/search.jsp").forward(request, response);
        }
    }
}
