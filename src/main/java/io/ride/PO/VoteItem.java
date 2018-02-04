package io.ride.PO;

import java.util.Date;

public class VoteItem {
    private int id;
    private int themeId;
    private String title;
    private String detail;
    private String photo;
    private String photo2;
    private long sum;    // 该选项票数
    private float percent; //  百分比
    private Date createTime;

    public VoteItem() {
    }

    public VoteItem(int themeId, String title, String detail, String photo, String photo2) {
        this.themeId = themeId;
        this.title = title;
        this.detail = detail;
        this.photo = photo;
        this.photo2 = photo2;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VoteItem{" +
                "id=" + id +
                ", themeId=" + themeId +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", photo='" + photo + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
