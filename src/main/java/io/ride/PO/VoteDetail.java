package io.ride.PO;

import java.util.Date;

public class VoteDetail {
    private int id;
    private int itemId;
    private int userId;
    private String openId;  // 微信ID
    private String ipAddress;
    private Date voteTime;

    public VoteDetail() {

    }

    public VoteDetail(int itemId, int userId, String ipAddress) {
        this.itemId = itemId;
        this.userId = userId;
        this.ipAddress = ipAddress;
    }

    public VoteDetail(int itemId, int userId, String openId, String ipAddress) {
        this.itemId = itemId;
        this.userId = userId;
        this.openId = openId;
        this.ipAddress = ipAddress;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    @Override
    public String toString() {
        return "VoteDetail{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", userId=" + userId +
                ", openId='" + openId + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", voteTime=" + voteTime +
                '}';
    }
}
