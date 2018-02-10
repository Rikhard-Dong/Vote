package io.ride.DTO;

import io.ride.PO.User;
import io.ride.util.DateUtil;

import java.util.Date;

public class SimpleUserDTO {
    private int userId;
    private String username;
    private String email;
    private String nickname;
    private String desc;
    private String sex;
    private String headImage;
    private String createTime;


    public SimpleUserDTO(User user) {
        userId = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        nickname = user.getNickname();
        desc = user.getDesc();
        headImage = user.getHeadImage();
        this.sex = user.getSex();
        createTime = DateUtil.date2StrCN(user.getCreateTime());
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SimpleUserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
