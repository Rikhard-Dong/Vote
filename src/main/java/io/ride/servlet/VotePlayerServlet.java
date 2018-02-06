package io.ride.servlet;


import io.ride.DTO.ResultDTO;
import io.ride.PO.User;
import io.ride.service.VotePlayerService;
import io.ride.service.impl.VotePlayerServiceImpl;
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

@WebServlet("/vote/player")
public class VotePlayerServlet extends HttpServlet {

    private PrintWriter out;

    private VotePlayerService playerService;

    @Override
    public void init() {
        String basePath = getServletContext().getRealPath("/");
        playerService = new VotePlayerServiceImpl(basePath);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        try {
            if (StringUtils.equals(op, "listAll")) {
                listAll(request);
            } else if (StringUtils.equals(op, "detail")) {
                detail(request);
            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("未知异常!")));
            out.flush();
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        try {
            if (StringUtils.equals(op, "allow")) {
                allow(request);
            } else if (StringUtils.equals(op, "deny")) {
                deny(request);
            } else if (StringUtils.equals(op, "delete")) {
                delete(request);
            }
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("未知异常!")));
            out.flush();
            e.printStackTrace();
        }
    }

    private void detail(HttpServletRequest request) throws SQLException {
        int playerId = Integer.parseInt(request.getParameter("playerId"));
        ResultDTO result = playerService.detail(playerId);
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }


    private void listAll(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("登陆后操作")));
            out.flush();
            return;
        }

        int page = Integer.parseInt(request.getParameter("page"));
        ResultDTO result = playerService.listByUser(page, user.getId());
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }

    private void delete(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("需要用户登录")));
            out.flush();
            return;
        }
        int playerId = Integer.parseInt(request.getParameter("playerId"));
        ResultDTO result = playerService.delete(playerId);
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }

    private void deny(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("需要用户登录")));
            out.flush();
            return;
        }
        int playerId = Integer.parseInt(request.getParameter("playerId"));
        ResultDTO result = playerService.denyPlayer(user.getId(), playerId);
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }

    private void allow(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("需要用户登录")));
            out.flush();
            return;
        }
        int playerId = Integer.parseInt(request.getParameter("playerId"));
        ResultDTO result = playerService.allowPlayer(user.getId(), playerId);
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }
}
