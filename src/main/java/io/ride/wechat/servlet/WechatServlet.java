package io.ride.wechat.servlet;

import io.ride.wechat.service.CoreService;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@WebServlet("/vote/wechat")
public class WechatServlet extends HttpServlet {

    private CoreService coreService = new CoreService();

    private PrintWriter out;
    public static final String TOKEN = "abc123";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();

        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");

            String[] params = new String[]{TOKEN, timestamp, nonce};
            Arrays.sort(params);

            String clearText = params[0] + params[1] + params[2];
            String algorithm = "SHA-1";
            String sign = new String(Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));
            if (StringUtils.equals(sign, signature)) {
                out.print(echostr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("接收到信息!");

        out = response.getWriter();

        try {
            String respXml = coreService.processRequest(request);
            System.out.println(respXml);
            out.print(respXml);
            out.flush();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
