package io.ride.DTO;

import io.ride.PO.User;

public class SearchUserDto {
    private int id;
    private String headImage;
    private String username;
    private String nickname;
    private String desc;

    public SearchUserDto(User user) {
        this.id = user.getId();
        this.headImage = user.getHeadImage();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.desc = user.getDesc();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SearchUserDto{" +
                "id=" + id +
                ", headImage='" + headImage + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
