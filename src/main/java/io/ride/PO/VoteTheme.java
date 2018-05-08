package io.ride.PO;

import lombok.Data;

import java.util.Date;

@Data
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
    private String region;          // 省份
    private String city;            // 城市

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
}
