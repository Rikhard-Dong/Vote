package io.ride.DTO;

import io.ride.PO.VoteDetail;
import io.ride.util.DateUtil;

public class SimpleVoteDetailDTO {
    private int detailId;
    private int itemId;
    private String itemTitle;

    private Integer userId;
    private String nickname;

    private String openId;
    private String ipAddress;
    private String votedDate;

    public SimpleVoteDetailDTO() {

    }

    public SimpleVoteDetailDTO(VoteDetail detail) {
        this.detailId = detail.getId();
        this.itemId = detail.getItemId();
        this.userId = detail.getUserId();
        this.openId = detail.getOpenId();
        this.ipAddress = detail.getIpAddress();

        this.votedDate = DateUtil.date2StrCN(detail.getVoteTime());
    }


    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getVotedDate() {
        return votedDate;
    }

    public void setVotedDate(String votedDate) {
        this.votedDate = votedDate;
    }
}
