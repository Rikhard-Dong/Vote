package io.ride.wechat.PO.wechat.request;

/**
 * 封装普通消息类
 */
public class ReqMessageText extends ReqMessageBase {
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }
}
