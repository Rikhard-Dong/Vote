package io.ride.service;

import io.ride.DTO.ResultDTO;

import java.sql.SQLException;

public interface SearchService {

    /**
     * 根据输入的内容搜索用户
     *
     * @param content 查询内容
     * @return 搜索结果
     * @throws SQLException 数据库操作异常
     */
    ResultDTO searchUser(String content) throws SQLException;

    /**
     * 根据输入的内容查询投票
     *
     * @param content 查询内容
     * @return 搜索结果
     * @throws SQLException 数据库操作异常
     */
    ResultDTO searchVote(String content) throws SQLException;
}
