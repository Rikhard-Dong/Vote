package io.ride.DTO;

import io.ride.PO.VoteItem;

import java.util.List;

public class VoteDetailDTO {
    private int themeId;
    private int isSingle;       // 0 单选         1. 多选
    private int status;         // 0 未开始        1. 进行中(未投票)      2. 进行中, 已投票或者为发起投票者        3. 已结束
    private int num;            // 可以投票的数量
    private long sum;            // 当前总票数
    private int isEmpty;        // 0 没有选项       1. 有选项
    private int timeDiff;       // 两次投票时间
    private List<VoteItem> items;

    public VoteDetailDTO() {

    }

    public VoteDetailDTO(int themeId, int isSingle, int status, int num, int isEmpty) {
        this.isSingle = isSingle;
        this.themeId = themeId;
        this.status = status;
        this.isEmpty = isEmpty;
        this.num = num;
    }

    public int getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(int timeDiff) {
        this.timeDiff = timeDiff;
    }

    public int getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(int isEmpty) {
        this.isEmpty = isEmpty;
    }

    public int getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(int isSingle) {
        this.isSingle = isSingle;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<VoteItem> getItems() {
        return items;
    }

    public void setItems(List<VoteItem> items) {
        this.items = items;
    }
}
