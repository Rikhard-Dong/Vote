package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleUserDTO;
import io.ride.service.UserService;
import io.ride.service.VoteThemeService;
import io.ride.service.impl.UserServiceImpl;
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

@WebServlet("/user/detail")
public class UserDetailServlet extends HttpServlet {

    private PrintWriter out;
    private UserService userService = new UserServiceImpl();
    private VoteThemeService themeService = new VoteThemeServiceImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        ResultDTO result;
        try {
            Integer userId = Integer.valueOf(request.getParameter("userId"));
            if (StringUtils.equals(op, "profile")) {
                SimpleUserDTO simpleUser = userService.getSimpleUser(userId);
                request.getSession().setAttribute("profile", simpleUser);
                response.sendRedirect(request.getContextPath() + "/pages/user/profile.jsp");
            } else if (StringUtils.equals(op, "vote1")) {
                /* 参与过的投票 */
                result = themeService.getUserVotedTheme(userId);
                out.print(JacksonUtil.toJSon(result));
//                out.flush();
            } else if (StringUtils.equals(op, "vote2")) {
                /* 发起过的投票 */
                result = themeService.getVoteByUserId(userId);
                out.print(JacksonUtil.toJSon(result));
                out.flush();
            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("sql异常")));
            out.flush();
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");

    }


}
