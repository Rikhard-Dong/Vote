package io.ride.service;

import io.ride.DTO.ResultDTO;
import io.ride.PO.User;
import io.ride.PO.VoteItem;
import io.ride.PO.VotePlayer;

import java.sql.SQLException;

public interface VoteItemService {

    /**
     * 添加一条Item
     *
     * @param item 待添加的item
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO addItem(VoteItem item) throws SQLException;

    /**
     * 申请成为一个item
     *
     * @param player 待添加的player
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO applyItem(VotePlayer player) throws SQLException;

    /**
     * 得到该投票的一些信息及其items
     *
     * @param themeId   theme id
     * @param votedItem 已投票的选项的id, 如果多选使用'-'联系, 例如多选3个, 则为'3-12-15'
     * @param user      参与投票的用户
     * @return result
     * @throws SQLException 数据库异常
     */
    ResultDTO getItems(int themeId, String votedItem, User user) throws SQLException;

    /**
     * 将该主题的item排序
     *
     * @param themeId theme id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO rankItems(int themeId) throws SQLException;
}
