package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleVoteThemeDto;
import io.ride.PO.User;
import io.ride.PO.VoteTheme;
import io.ride.service.VoteItemService;
import io.ride.service.VoteThemeService;
import io.ride.service.impl.VoteItemServiceImpl;
import io.ride.service.impl.VoteThemeServiceImpl;
import io.ride.util.JacksonUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/vote/detail")
public class VoteThemeDetailServlet extends HttpServlet {
    private VoteThemeService themeService;
    private VoteItemService itemService;

    private PrintWriter out;
    private int themeId;

    @Override
    public void init() throws ServletException {
        String basePath = getServletContext().getRealPath("/");
        themeService = new VoteThemeServiceImpl(basePath);
        itemService = new VoteItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        System.out.println("vote theme detail servlet doGet() in .....");
        try {
            if (StringUtils.equals(op, "detail")) {
                themeId = Integer.parseInt(request.getParameter("themeId"));
                String openId = request.getParameter("openId");
                SimpleVoteThemeDto theme = themeService.getTheme(themeId);
                System.out.println("vote detail theme ========> " + theme);
                request.getSession().setAttribute("theme", theme);
                if (openId != null) {
                    request.getSession().setAttribute("openId", openId);
                }
                response.sendRedirect(request.getContextPath() + "/pages/vote/detail.jsp");
            } else if (StringUtils.equals(op, "items")) {
                System.out.println("doGet() items in .....");
                listItems(request);
            }

//            for (Cookie cookie1 : request.getCookies()) {
//                System.out.println(cookie1.getName() + " ====> " + cookie1.getValue() + "  过期时间:" + cookie1.getMaxAge());
//            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("数据库未知异常")));
            out.flush();
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();

        String op = request.getParameter("op");

        try {
            if (StringUtils.equals(op, "update")) {
                update(request);
            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("数据库未知异常")));
            out.flush();
            e.printStackTrace();
        }
    }

    private void update(HttpServletRequest request) throws SQLException {
        ResultDTO result;

        String attr = request.getParameter("attr");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("登陆后操作!")));
            out.flush();
            return;
        }
        if (StringUtils.equals(attr, "desc")) {
            int themeId = Integer.parseInt(request.getParameter("themeId"));
            String desc = request.getParameter("desc");
            result = themeService.updateDesc(themeId, user.getId(), desc);
            out.print(JacksonUtil.toJSon(result));
            out.flush();
        } else {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("更新失败")));
            out.flush();
            return;
        }

        SimpleVoteThemeDto simpleTheme = themeService.getTheme(themeId);
        request.getSession().setAttribute("theme", simpleTheme);
    }

    private void listItems(HttpServletRequest request) throws SQLException {

        SimpleVoteThemeDto theme = (SimpleVoteThemeDto) request.getSession().getAttribute("theme");
        if (theme == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("该theme不存在")));
            out.flush();
            return;
        }
        String votedItem = null;
        for (Cookie cookie : request.getCookies()) {
            if (StringUtils.equals(cookie.getName(), String.valueOf(themeId))) {
                votedItem = cookie.getValue();
                System.out.println(cookie.getName() + "=====>" + votedItem + "    get !");
                break;
            }
        }

        User user = (User) request.getSession().getAttribute("user");
        ResultDTO resultDTO = itemService.getItems(theme.getThemeId(), votedItem, user);

        out.print(JacksonUtil.toJSon(resultDTO));
        out.flush();
    }
}
