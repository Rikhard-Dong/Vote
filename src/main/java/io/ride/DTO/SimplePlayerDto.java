package io.ride.DTO;

import io.ride.PO.VotePlayer;

public class SimplePlayerDto {
    private int playerId;
    private String name;
    private String email;
    private String title;
    private int status;
    private String statusDesc;      // 状态描述

    public SimplePlayerDto() {

    }

    public SimplePlayerDto(VotePlayer player) {
        this.playerId = player.getId();
        this.name = player.getName();
        this.email = player.getEmail();
        this.title = player.getTitle();
        this.status = player.getStatus();
        switch (status) {
            case 0:
                this.statusDesc = "未审核";
                break;
            case 1:
                this.statusDesc = "审核通过";
                break;
            case 2:
                this.statusDesc = "审核失败";
                break;
            case 3:
                this.statusDesc = "已过期";
                break;
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    @Override
    public String toString() {
        return "SimplePlayerDto{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", statusDesc='" + statusDesc + '\'' +
                '}';
    }
}
