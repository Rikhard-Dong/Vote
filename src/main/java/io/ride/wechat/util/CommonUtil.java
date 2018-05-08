package io.ride.wechat.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ride.util.PropertiesUtils;
import io.ride.wechat.PO.wechat.AccessToken;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class CommonUtil {

    private static HttpClient CLIENT = new DefaultHttpClient();
    private static ObjectMapper MAPPER = new ObjectMapper();

    private static String APPID;
    private static String SECRET;

    static {
        try {
            APPID = (String) PropertiesUtils.readResource("properties/wechat-config.properties", "wechat.appid");
            SECRET = (String) PropertiesUtils.readResource("properties/wechat-config.properties", "wechat.secret");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url获取返回对象
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T returnMsg(String url, Class<T> clazz) throws IOException {
        HttpPost mgsPost = new HttpPost(url);
        String str = EntityUtils.toString(CLIENT.execute(mgsPost).getEntity(), "UTF-8");
        return MAPPER.readValue(str, clazz);
    }

    /**
     * httpclient模拟get请求
     */
    public static String Get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return EntityUtils.toString(CLIENT.execute(httpGet).getEntity(), "UTF-8");
    }

    /**
     * http模拟post请求，请求参数是json数据
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String postJson(String url, String param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        //防止参数乱码
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setEntity(new StringEntity(param, "UTF-8"));
        //执行请求
        HttpResponse execute = CLIENT.execute(httpPost);
        return EntityUtils.toString(execute.getEntity());
    }

    /**
     * httpclient模拟表单提交
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String postForm(String url, List<NameValuePair> list) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        //防止参数乱码
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entity);
        //执行请求
        HttpResponse execute = CLIENT.execute(httpPost);
        return EntityUtils.toString(execute.getEntity());
    }


    /**
     * 获取access_token
     *
     * @return
     * @throws IOException
     */
    public static String getAccessToken() throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET + "";
        AccessToken token = returnMsg(url, AccessToken.class);
        return token.getAccess_token();
    }

    /**
     * 获取调用微信JS接口的临时票据
     *
     * @param access_token
     * @return
     * @throws Exception
     */
    public static String getJsApiTicket(String access_token) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        // 发起GET请求获取凭证
        String resq = Get(url);
        JsonNode tree = MAPPER.readTree(resq);
        return tree.get("ticket").toString().replaceAll("\"", "");
    }

    public static String getAPPID() {
        return APPID;
    }

    public static void setAPPID(String APPID) {
        CommonUtil.APPID = APPID;
    }

    public static String getSECRET() {
        return SECRET;
    }

    public static void setSECRET(String SECRET) {
        CommonUtil.SECRET = SECRET;
    }
}
