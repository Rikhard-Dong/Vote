package io.ride.service;

import io.ride.DTO.ResultDTO;

import java.sql.SQLException;

public interface VotePlayerService {

    /**
     * 根据用户显示向该用户申请的所有的选手信息
     *
     * @param page   页数
     * @param userId 用户id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO listByUser(int page, int userId) throws SQLException;

    /**
     * 显示该选手信息的详情
     *
     * @param playerId 选手id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO detail(int playerId) throws SQLException;

    /**
     * 同意某个选手的申请
     *
     * @param userId   用户id
     * @param playerId 选手id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO allowPlayer(int userId, int playerId) throws SQLException;

    /**
     * 拒绝某个选手的申请
     *
     * @param userId   用户id
     * @param playerId 选手id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO denyPlayer(int userId, int playerId) throws SQLException;

    /**
     * 删除某个选手的申请
     *
     * @param playerId 用户id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO delete(int playerId) throws SQLException;
}
