package io.ride.DTO;

import io.ride.PO.VotePlayer;
import io.ride.PO.VoteTheme;
import io.ride.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

public class PlayerDetailDTO {
    private String name;
    private String phone;
    private String email;
    private String sex;
    private Integer age;
    private String address;
    private String title;
    private String detail;
    private Integer themeId;
    private String theme;
    private String photo;
    private String photo2;
    private String createTime;


    public PlayerDetailDTO() {
    }

    public PlayerDetailDTO(VoteTheme theme, VotePlayer player) {
        this.theme = theme.getTheme();
        this.themeId = theme.getId();

        this.name = player.getName();
        this.phone = player.getPhone();
        this.email = player.getEmail();
        this.sex = StringUtils.isEmpty(player.getSex()) ? null : player.getSex();
        this.age = player.getAge();
        this.address = StringUtils.isEmpty(player.getAddress()) ? null : player.getAddress();
        this.title = player.getTitle();
        this.detail = player.getDetail();
        this.photo = player.getPhoto();
        this.photo2 = player.getPhoto2();
        this.createTime = DateUtil.date2StrCN(player.getCreateTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    @Override
    public String toString() {
        return "PlayerDetailDTO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", themeId=" + themeId +
                ", theme='" + theme + '\'' +
                ", photo='" + photo + '\'' +
                ", photo2='" + photo2 + '\'' +
                '}';
    }
}
