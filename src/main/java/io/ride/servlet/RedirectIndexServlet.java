package io.ride.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ride.wechat.util.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/redirect_index")
public class RedirectIndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String code = request.getParameter("code");
            System.out.println("redirect index servlet doGet() .... \t code ====> " + code);


            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + CommonUtil.getAPPID() +
                    "&secret=" + CommonUtil.getSECRET() +
                    "&code=" + code + "&grant_type=authorization_code";

            String resultJson = CommonUtil.Get(url);
//            System.out.println("url is ======>" + url + " \nresult json =====> " + resultJson);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(resultJson);
            String openId = String.valueOf(tree.get("openid"));
            openId = openId.replace("\"", "");
//            System.out.println("openId ===> " + openId);


            request.getSession().setAttribute("openId", openId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect("pages/index.jsp");
        }
    }
}
