package io.ride.DTO;

import io.ride.PO.User;
import io.ride.PO.VoteTheme;
import io.ride.util.DateUtil;

import java.util.Date;

public class SimpleVoteThemeDto {
    private int themeId;
    private int userId;
    private String theme;
    private String desc;
    private String username;
    private String headImage;
    private String countDown;
    private int status;
    private String createTime;
    private String startTime;
    private String endTime;
    private int isAnonymous;


    public SimpleVoteThemeDto(VoteTheme theme, User user) {
        this.theme = theme.getTheme();
        this.desc = theme.getDesc();
        this.username = user.getUsername();
        this.headImage = user.getHeadImage();
        this.themeId = theme.getId();
        this.userId = user.getId();
        this.isAnonymous = theme.getIsAnonymous();
        Date currDate = new Date();
        if (currDate.before(theme.getStartTime())) {
            // 未开始
            status = 0;
            this.countDown = DateUtil.calcTime(theme.getStartTime()) + "后开始";
        } else if (currDate.after(theme.getEndTime())) {
            // 已结束
            status = 2;
            this.countDown = "投票已结束";
        } else {
            // 投票中
            status = 1;
            this.countDown = DateUtil.calcTime(theme.getEndTime()) + "后结束";
        }

        this.createTime = DateUtil.date2StrCN(theme.getCreateTime());
        this.startTime = DateUtil.date2StrCN(theme.getStartTime());
        this.endTime = DateUtil.date2StrCN(theme.getEndTime());
    }

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCountDown() {
        return countDown;
    }

    public void setCountDown(String countDown) {
        this.countDown = countDown;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getEndDesc() {
        return countDown;
    }

    public void setEndDesc(String endDesc) {
        this.countDown = endDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SimpleVoteThemeDto{" +
                "themeId=" + themeId +
                ", userId=" + userId +
                ", theme='" + theme + '\'' +
                ", desc='" + desc + '\'' +
                ", username='" + username + '\'' +
                ", headImage='" + headImage + '\'' +
                ", countDown='" + countDown + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
