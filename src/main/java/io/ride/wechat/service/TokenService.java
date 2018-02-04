package io.ride.wechat.service;

import io.ride.util.DateUtil;
import io.ride.wechat.util.CommonUtil;

import java.io.IOException;
import java.util.Date;

public class TokenService {

    private String accessToken;
    private String jsapiTicket;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public void flush() {
        System.out.println(DateUtil.date2StrCN(new Date()) + "===========>获取access_token及jsapi_ticket");

        try {
            this.accessToken = CommonUtil.getAccessToken();
            this.jsapiTicket = CommonUtil.getJsApiTicket(accessToken);
            System.out.println("access_token ====> " + accessToken + ", =======> " + jsapiTicket);
        } catch (Exception e) {
            System.out.println("刷新access_token失败");
            e.printStackTrace();
        }
    }
}
