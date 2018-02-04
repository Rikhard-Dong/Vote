package io.ride.PO;

import java.util.Date;

public class VoteTheme {
    private int id;
    private int userId;
    private String theme;
    private String desc;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private int isSingle;           // 0 单选  1 可以多选
    private int maxSelect;
    private int isAnonymous;        // 0 允许匿名用户投票  1 不允许  2 使用微信投票
    private int isRestrictedZone;   // 是否限制投票区域  0 不限制  1 限制
    private String ipZone;
    private int timeDiff;
    private int ipMax;

    public VoteTheme() {

    }

    public VoteTheme(int userId, String theme, String desc, Date startTime, Date endTime, int isSingle, int maxSelect, int isAnonymous, int timeDiff) {
        this.userId = userId;
        this.theme = theme;
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isSingle = isSingle;
        this.maxSelect = maxSelect;
        this.isAnonymous = isAnonymous;
        this.timeDiff = timeDiff;
    }

    public VoteTheme(int userId, String theme, String desc, Date startTime, Date endTime, int isSingle, int isAnonymous, int isRestrictedZone) {
        this.userId = userId;
        this.theme = theme;
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isSingle = isSingle;
        this.isAnonymous = isAnonymous;
        this.isRestrictedZone = isRestrictedZone;
    }

    public int getIpMax() {
        return ipMax;
    }

    public void setIpMax(int ipMax) {
        this.ipMax = ipMax;
    }

    public int getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(int timeDiff) {
        this.timeDiff = timeDiff;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(int isSingle) {
        this.isSingle = isSingle;
    }

    public int getMaxSelect() {
        return maxSelect;
    }

    public void setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
    }

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getIsRestrictedZone() {
        return isRestrictedZone;
    }

    public void setIsRestrictedZone(int isRestrictedZone) {
        this.isRestrictedZone = isRestrictedZone;
    }

    public String getIpZone() {
        return ipZone;
    }

    public void setIpZone(String ipZone) {
        this.ipZone = ipZone;
    }

    @Override
    public String toString() {
        return "VoteTheme{" +
                "id=" + id +
                ", userId=" + userId +
                ", theme='" + theme + '\'' +
                ", desc='" + desc + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isSingle=" + isSingle +
                ", maxSelect=" + maxSelect +
                ", isAnonymous=" + isAnonymous +
                ", isRestrictedZone=" + isRestrictedZone +
                ", ipZone='" + ipZone + '\'' +
                '}';
    }
}
