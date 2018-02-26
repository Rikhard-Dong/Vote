package io.ride.service;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleVoteThemeDto;
import io.ride.PO.VoteTheme;

import java.sql.SQLException;

public interface VoteThemeService {

    /**
     * 得到一个前端显示的theme信息
     *
     * @param id theme id
     * @return simple vote theme dto
     * @throws SQLException 数据库操作异常
     */
    SimpleVoteThemeDto getTheme(int id) throws SQLException;

    /**
     * 添加一个投票
     *
     * @param theme 待添加的theme
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO addTheme(VoteTheme theme) throws SQLException;

    /**
     * 分页显示所有的
     *
     * @param page 页数
     * @return result
     * @throws SQLException sql语句异常
     */
    ResultDTO listTheme(int page) throws SQLException;

    /**
     * 对于某个用户分页显示该用户发起的所有投票
     *
     * @param userId 用户id
     * @param page   分页
     * @return result
     * @throws SQLException sql操作异常
     */
    ResultDTO listThemeByUserId(int userId, int page) throws SQLException;

    /**
     * 删除投票
     *
     * @param themeId theme id
     * @return result
     * @throws SQLException sql操作异常
     */
    ResultDTO deleteTheme(int themeId) throws SQLException;

    /**
     * 更新投票描述
     *
     * @param themeId theme id
     * @param userId  user id
     * @param desc    desc
     * @return result
     * @throws SQLException sql操作异常
     */
    ResultDTO updateDesc(int themeId, int userId, String desc) throws SQLException;

    /**
     * 提供数据提供给前端highcharts使用
     *
     * @param themeId 投票主题id
     * @return result
     * @throws SQLException 数据库异常
     */
    ResultDTO getPieData(int themeId) throws SQLException;

    /**
     * 根据用户id查询该用户发起的所有投票
     *
     * @param userId 用户id
     * @return result
     * @throws SQLException 数据库异常
     */
    ResultDTO getVoteByUserId(int userId) throws SQLException;

    /**
     * 根据用户id查询该用户参与过的投票
     *
     * @param userId 用户id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO getUserVotedTheme(int userId) throws SQLException;
}
