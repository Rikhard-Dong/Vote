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
     * @param item
     * @return
     * @throws SQLException
     */
    ResultDTO addItem(VoteItem item) throws SQLException;

    /**
     * 申请成为一个item
     *
     * @param player
     * @return
     */
    ResultDTO applyItem(VotePlayer player) throws SQLException;

    /**
     * 得到该投票的一些信息及其items
     */
    ResultDTO getItems(int themeId, String votedItem, User user) throws SQLException;
}
