package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.PO.User;
import io.ride.dao.UserDao;
import io.ride.service.UserService;
import io.ride.service.impl.UserServiceImpl;
import io.ride.util.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private PrintWriter out;
    private UserService userService = new UserServiceImpl();

    private String headPath;
    private String basePath;

    @Override
    public void init() throws ServletException {
        headPath = File.separator + "upload" + File.separator + "head";
        basePath = getServletContext().getRealPath("/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        try {
            if (StringUtils.equals(op, "logout")) {
                request.getSession().removeAttribute("user");
                out.print(JacksonUtil.toJSon(ResultDTO.SUCCESS("登出成功")));
                out.flush();
            } else if (StringUtils.equals(op, "listTheme")) {
                list(request);
            } else if (StringUtils.equals(op, "profile")) {
                detail(request);
            } else if (StringUtils.equals(op, "deleteTheme")) {
                delete(request);
            } else if (StringUtils.equals(op, "head")) {
                getHead(request);
            }
        } catch (SQLException e) {
            System.out.println("=========>    UserServlet doPost Exception.......");
            out.print(ResultDTO.FAIL("未知异常"));
            out.flush();
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        try {
            if (StringUtils.equals(op, "login")) {
                login(request);
            } else if (StringUtils.equals(op, "register")) {
                register(request);
            } else if (StringUtils.equals(op, "update")) {
                update(request);
            }
        } catch (SQLException e) {
            out.print(ResultDTO.FAIL("未知异常"));
            out.flush();
            System.out.println("=========>    UserServlet doPost Exception.......");
            e.printStackTrace();
        }

    }

    private void getHead(HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String headPath;
        if (user == null) {
            headPath = File.separator + "upload" + File.separator + "head" + File.separator + "default-head.jpg";
        } else {
            headPath = user.getHeadImage();
        }
        out.print(headPath);
        out.flush();
    }


    /**
     * 登录
     *
     * @param request
     * @throws SQLException
     */
    private void login(HttpServletRequest request) throws SQLException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        String code = request.getParameter("code");
        String realCode = (String) request.getSession().getAttribute("code");
        if (!StringUtils.equals(code, realCode)) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("验证码错误")));
            out.flush();
            return;
        }
        // System.out.println("===========> account is " + account + ", password is " + password);
        User user = userService.validateAccount(account, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            String ip = CusAccessObjectUtil.getIpAddress(request);
            System.out.println("====> ip is " + ip);
            userService.addLoginInfo(user.getId(), ip);
            out.print(JacksonUtil.toJSon(ResultDTO.SUCCESS("登录成功")));
        } else {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("登录失败")));
        }
        out.flush();
    }

    /**
     * 注册
     *
     * @param request
     * @throws SQLException
     */
    private void register(HttpServletRequest request) throws SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        String defaultHeadImage = headPath + File.separator + "default-head.jpg";

        String code = request.getParameter("code");
        String realCode = (String) request.getSession().getAttribute("code");
        if (!StringUtils.equals(code, realCode)) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("验证码错误")));
            out.flush();
            return;
        }

        String result = JacksonUtil.toJSon(userService.addUser(username, password, password2, email, defaultHeadImage));
        out.print(result);
        out.flush();
    }

    private void list(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("登录后操作")));
            out.flush();
            return;
        }
        if (user.getType() != 0) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("需要管理员权限")));
            out.flush();
            return;
        }
        Integer page = Integer.parseInt(request.getParameter("page"));
        if (page == null) {
            page = 1;
        }
        System.out.println("=====> page is " + page);
        out.print(JacksonUtil.toJSon(userService.listUser(page)));
        out.flush();
    }

    private void detail(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("当前没有用户登录")));
            out.flush();
            return;
        }
        out.print(JacksonUtil.toJSon(userService.getUser(user.getId())));
        out.flush();
    }

    private void delete(HttpServletRequest request) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getType() != 0) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("该操作需要管理员")));
            out.flush();
        }
        int userId = Integer.parseInt(request.getParameter("userId"));
        ResultDTO result = userService.deleteUser(userId);
        out.print(JacksonUtil.toJSon(result));
        out.flush();
    }

    /**
     * 更新
     *
     * @param request
     * @throws SQLException
     * @throws IOException
     */
    private void update(HttpServletRequest request) throws SQLException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("当前没有用户登录")));
            out.flush();
            return;
        }
        String attr = request.getParameter("attr");
        String value;
        if (StringUtils.equals(attr, "headImage")) {
            String imgBase64 = request.getParameter("img");
            // 去掉头
            imgBase64 = imgBase64.split(",")[1];
//            System.out.println(imgBase64);
            String headFilePath = headPath + File.separator + UUIDUtil.getUUID() + ".jpg";
            boolean result = ImageBase64Util.base64ToImageFile(imgBase64, basePath + headFilePath);
            if (!result) {
                out.print(JacksonUtil.toJSon(ResultDTO.FAIL("上传图片失败")));
                out.flush();
                return;
            }
            // 删除原先的图片
            if (!user.getHeadImage().contains("default-head")) {
                System.out.println(basePath + user.getHeadImage());
                File oldHeadImage = new File(basePath + user.getHeadImage());
                if (oldHeadImage.exists()) {
                    System.out.println("文件存在");
                    if (!oldHeadImage.delete()) {
                        System.out.println("删除失败");
                    }
                } else {
                    System.out.println("文件不存在");
                }
            }
            value = headFilePath;
        } else if (StringUtils.equals(attr, "password")) {
            String oldPassword = request.getParameter("oldPassword");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");

            if (!StringUtils.equals(user.getPassword(), EncryptionUtil.md5(oldPassword))) {
                out.print(JacksonUtil.toJSon(ResultDTO.FAIL("旧密码输入错误")));
                out.flush();
                return;
            }

            if (!StringUtils.equals(password, password2)) {
                out.print(JacksonUtil.toJSon(ResultDTO.FAIL("两次密码不一致")));
                out.flush();
                return;
            }
            value = password;
        } else {
            value = request.getParameter("value");
        }

        System.out.println("user update ======> attr ---> " + attr + ", value ----> " + value);
        ResultDTO result = userService.updateUser(user.getId(), attr, value);
        out.print(JacksonUtil.toJSon(result));
        out.flush();

        // 更新当前用户信息到session中
        UserDao dao = new UserDao();
        user = dao.queryById(user.getId());
        request.getSession().setAttribute("user", user);
    }
}
