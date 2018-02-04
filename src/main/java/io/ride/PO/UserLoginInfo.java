package io.ride.PO;

import java.util.Date;

public class UserLoginInfo {
    private int id;
    private int userId;
    private Date loginTime;
    private String loginIp;

    public UserLoginInfo() {
    }

    public UserLoginInfo(int userId, String loginIp) {
        this.userId = userId;
        this.loginIp = loginIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Override
    public String toString() {
        return "UserLoginInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", loginTime=" + loginTime +
                ", loginIp='" + loginIp + '\'' +
                '}';
    }
}
