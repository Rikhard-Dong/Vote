package io.ride.PO;

import java.util.Date;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VotePlayer{" +
                "id=" + id +
                ", themeId=" + themeId +
                ", itemId=" + itemId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", photo='" + photo + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
