package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleVoteThemeDto;
import io.ride.PO.User;
import io.ride.service.VoteDetailService;
import io.ride.service.VoteItemService;
import io.ride.service.VoteThemeService;
import io.ride.service.impl.VoteDetailServiceImpl;
import io.ride.service.impl.VoteItemServiceImpl;
import io.ride.service.impl.VoteThemeServiceImpl;
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

@WebServlet("/vote/statistics")
public class VoteStatisticsServlet extends HttpServlet {

    PrintWriter out;
    private VoteThemeService themeService = new VoteThemeServiceImpl();
    private VoteItemService itemService = new VoteItemServiceImpl();
    private VoteDetailService detailService = new VoteDetailServiceImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        try {
            Integer themeId = Integer.valueOf(request.getParameter("themeId"));
            if (StringUtils.equals(op, "statistics")) {
                User user = (User) request.getSession().getAttribute("user");
                if (user == null) {
                    response.sendRedirect(request.getContextPath() + "/pages/user/login.jsp");
                    return;
                }
                SimpleVoteThemeDto voteDetail = themeService.getTheme(themeId);

                request.getSession().setAttribute("theme", voteDetail);
                response.sendRedirect(request.getContextPath() + "/pages/manager/statistics.jsp");
            } else if (StringUtils.equals(op, "source")) {
                ResultDTO result = detailService.votedSourcePie(themeId);
                out.print(JacksonUtil.toJSon(result));
//                out.flush();
            } else if (StringUtils.equals(op, "rank")) {
                ResultDTO result = itemService.rankItems(themeId);
                out.print(JacksonUtil.toJSon(result));
            } else if (StringUtils.equals(op, "record")) {
                int page = Integer.parseInt(request.getParameter("page"));
                ResultDTO result = detailService.detailList(page, themeId);
                out.print(JacksonUtil.toJSon(result));
//                out.flush();
            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("数据库操作异常")));
            out.flush();
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        if (StringUtils.equals(op, "")) {

        }
    }
}
