package io.ride.PO;

import java.util.Date;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String headImage;
    private String desc;
    private int type;           // 0 管理员  1 平台注册用户
    private int status;         // 0 正常    1 冻结        2 删除
    private String sex;
    private Date createTime;

    public User() {

    }

    public User(String username, String password, String email, String headImage) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.headImage = headImage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImage='" + headImage + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", sex='" + sex + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
