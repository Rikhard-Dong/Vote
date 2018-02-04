package io.ride.DTO;

import io.ride.PO.User;

import java.util.Date;

public class SimpleUserDTO {
    private int userId;
    private String username;
    private String email;
    private String nickname;
    private String desc;
    private String headImage;


    public SimpleUserDTO(User user) {
        userId = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        nickname = user.getNickname();
        desc = user.getDesc();
        headImage = user.getHeadImage();
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
