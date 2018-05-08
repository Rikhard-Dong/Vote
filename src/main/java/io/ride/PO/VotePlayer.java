package io.ride.PO;

import lombok.Data;

import java.util.Date;

@Data
public class VotePlayer {
    private Integer id;
    private Integer themeId;
    private Integer itemId;
    private String name;
    private String phone;
    private String email;
    private String title;
    private String detail;
    private String photo;
    private String photo2;
    private String sex;
    private Integer age;
    private String occupation;
    private String address;
    private Integer status;
    private Date createTime;

    public VotePlayer() {
    }

    public VotePlayer(Integer themeId, String name, String phone, String email, String title, String detail, String photo) {
        this.themeId = themeId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.title = title;
        this.detail = detail;
        this.photo = photo;
    }
}
