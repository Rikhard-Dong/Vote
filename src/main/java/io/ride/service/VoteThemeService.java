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
}
