package io.ride.wechat.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ride.wechat.PO.wechat.menu.Button;
import io.ride.wechat.PO.wechat.menu.CommonButton;
import io.ride.wechat.PO.wechat.menu.Menu;
import io.ride.wechat.service.TokenService;
import org.apache.commons.lang3.StringUtils;

public class MenuUtil {

    private static ObjectMapper MAPPER = new ObjectMapper();

    public static int createMenu(Menu menu, String accessToken) throws Exception {
        int result = 0;
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken + "";
        // 将菜单转换成json
        String jsonMenu = MAPPER.writeValueAsString(menu);
        // 调用创建接口
        String resp = CommonUtil.postJson(url, jsonMenu);
        JsonNode tree = MAPPER.readTree(resp);
        if (StringUtils.equals(tree.get("errcode").toString(), "0")) {
            result = 1;
            System.out.println("创建菜单成功");
        } else {
            System.out.println("创建菜单失败, 错误代码 -> " + tree.get("errcode") + ", 错误消息 -> " + tree.get("errmsg"));
        }

        return result;
    }

    private static Menu getMenu() {
        CommonButton btn1 = new CommonButton();
        btn1.setName("参与投票");
        btn1.setType("view");
        // TODO 如果需要修改url这里

        String myUrl = "http://ridddddde.free.ngrok.cc/redirect_index";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + CommonUtil.getAPPID()
                + "&redirect_uri=" + myUrl
                + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirec";

        btn1.setUrl(url);

        Menu menu = new Menu();
        menu.setButton(new Button[]{btn1});
        return menu;
    }

    public static void main(String[] args) {
        try {
            createMenu(getMenu(), CommonUtil.getAccessToken());
        } catch (Exception e) {
            System.out.println("创建菜单失败");
            e.printStackTrace();
        }
    }
}
