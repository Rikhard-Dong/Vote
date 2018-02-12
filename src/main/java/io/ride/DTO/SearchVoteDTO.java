package io.ride.DTO;

import io.ride.PO.User;
import io.ride.PO.VoteTheme;
import io.ride.util.DateUtil;

import java.util.Date;

/**
 * 投票搜索结果dto
 */
public class SearchVoteDTO {
    private int userId;
    private String nickname;
    private String headImage;

    private int themeId;
    private String title;
    private String desc;
    private String status;

    public SearchVoteDTO() {
    }

    public SearchVoteDTO(User user, VoteTheme theme) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.headImage = user.getHeadImage();

        this.themeId = theme.getId();
        this.title = theme.getTheme();
        this.desc = theme.getDesc();

        Date currDate = new Date();
        if (currDate.before(theme.getStartTime())) {
            // 未开始
            this.status = DateUtil.calcTime(theme.getStartTime()) + "后开始";
        } else if (currDate.after(theme.getEndTime())) {
            // 已结束
            this.status = "投票已结束";
        } else {
            // 投票中
            this.status = DateUtil.calcTime(theme.getEndTime()) + "后结束";
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
