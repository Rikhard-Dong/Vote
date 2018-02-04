package io.ride.wechat.PO;

import java.util.Date;

public class Follow {
    private int id;
    private String openId;
    private Date followTime;

    public Follow() {
    }

    public Follow(String openId) {
        this.openId = openId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }
}
