package io.ride.wechat.service;

import io.ride.wechat.PO.wechat.response.RespMessageNews;
import io.ride.wechat.PO.wechat.response.RespMessageText;
import io.ride.wechat.service.impl.FollowServiceImpl;
import io.ride.wechat.service.impl.ThemeServiceImpl;
import io.ride.wechat.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class CoreService {

    private FollowService followService = new FollowServiceImpl();
    private ThemeService themeService = new ThemeServiceImpl();

    public String processRequest(HttpServletRequest request) throws Exception {

        Map<String, String> map = MessageUtil.parseXml(request);

        String msgType = map.get("MsgType");

        // 发送消息者openId
        String openId = map.get("FromUserName");

        // 封装返回消息
        RespMessageText responseText = new RespMessageText();
        responseText.setFromUserName(map.get("ToUserName"));
        responseText.setToUserName(openId);
        responseText.setCreateTime(new Date().getTime());
        responseText.setMsgType("text");


        String msg = "";

        if (StringUtils.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT, msgType)) {
            // 发送消息, 发送数字则表明查询ID, 返回图文消息
            System.out.println("发送消息 openId =====> " + map.get("FromUserName"));
            msg = map.get("Content");
            boolean isNum = msg.matches("[0-9]+");
            if (isNum) {
                int themeId = Integer.parseInt(msg);

                RespMessageNews news = themeService.getTheme(themeId, openId);
                if (news == null) {
                    msg = "请求主题id为" + themeId + "的投票不存在";
                } else {
                    request.getSession().setAttribute("openId", openId);

                    news.setFromUserName(map.get("ToUserName"));
                    news.setToUserName(map.get("FromUserName"));
                    news.setMsgType("news");

                    return MessageUtil.messageXMLToNews(news);
                }
            } else {
                msg = "您的消息已经收到";
            }
        } else if (StringUtils.equals(msgType, "event")) {
            // 事件
            String event = map.get("Event");
            // System.out.println("event ======> " + event);

            if (StringUtils.equals(event, MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                // 关注事件
                followService.subscribe(openId);
                msg = "感谢您的关注!";
            } else if (StringUtils.equals(event, MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                // 取消关注事件
                followService.unsubscribe(openId);
                msg = "您已取消关注";
            } else if (StringUtils.equals(event, MessageUtil.EVENT_TYPE_CLICK)) {
                // 菜单点击拉取信息事件
                System.out.println(map.get("FromUserName"));
            } else if (StringUtils.equals(event, MessageUtil.EVENT_TYPE_VIEW)) {
                request.getSession().setAttribute("openId", openId);

                // 菜单点击跳转链接事件
                System.out.println("菜单点击跳转链接事件 openId ====> " + map.get("FromUserName"));
            } else {
                msg = "其他事件暂未处理";
            }
        } else {
            msg = "当前为其他消息";
        }

        responseText.setContent(msg);
        return MessageUtil.messageXMLToText(responseText);
    }
}
