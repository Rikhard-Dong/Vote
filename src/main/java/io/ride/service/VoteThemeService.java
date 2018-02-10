package io.ride.service;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleVoteThemeDto;
import io.ride.PO.VoteTheme;

import java.sql.SQLException;

public interface VoteThemeService {

    SimpleVoteThemeDto getTheme(int id) throws SQLException;

    ResultDTO addTheme(VoteTheme theme) throws SQLException;

    ResultDTO listTheme(int page) throws SQLException;

    ResultDTO listThemeByUserId(int userId, int page) throws SQLException;

    ResultDTO deleteTheme(int themeId) throws SQLException;

    ResultDTO updateDesc(int themeId, int userId, String desc) throws SQLException;

    /**
     * 提供数据提供给前端highcharts使用
     *
     * @param themeId 投票主题id
     * @return
     * @throws SQLException 数据库异常
     */
    ResultDTO getPieData(int themeId) throws SQLException;

    /**
     * 根据用户id查询该用户发起的所有投票
     *
     * @param userId 用户id
     * @return
     * @throws SQLException 数据库异常
     */
    ResultDTO getVoteByUserId(int userId) throws SQLException;

    /**
     * 根据用户id查询该用户参与过的投票
     *
     * @param userId 用户id
     * @return
     * @throws SQLException 数据库操作异常
     */
    ResultDTO getUserVotedTheme(int userId) throws SQLException;
}
