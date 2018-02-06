package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.util.JacksonUtil;
import io.ride.util.VerifyCodeUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/verify")
public class VerifyCodeServlet extends HttpServlet {

    private VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil(new VerifyCodeUtil.VerifyCodeStyle());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage vcImg = verifyCodeUtil.getVCImg();
        String code = verifyCodeUtil.getCodeValue().toString();
        request.getSession().setAttribute("code", code);
        verifyCodeUtil.outputImg(response, vcImg);
        verifyCodeUtil.outputImg(response, vcImg);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        String code = request.getParameter("code");
        String realCode = (String) request.getSession().getAttribute("code");
        if (StringUtils.equals(code, realCode)) {
            out.print(JacksonUtil.toJSon(ResultDTO.SUCCESS("验证码正确")));
            out.flush();
        } else {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("验证码不正确")));
            out.flush();
        }
    }
}
