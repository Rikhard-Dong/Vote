package io.ride.service;

import io.ride.DTO.ResultDTO;
import io.ride.PO.VoteDetail;

import java.sql.SQLException;
import java.util.List;

public interface VoteDetailService {

    /**
     * 添加一组投票细则
     *
     * @param details 一组投票细则
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO addDetails(List<VoteDetail> details) throws SQLException;

    /**
     * 投票来源饼图分析
     *
     * @param themeId theme id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO votedSourcePie(int themeId) throws SQLException;


    ResultDTO detailList(int page, int themeId) throws SQLException;
}
