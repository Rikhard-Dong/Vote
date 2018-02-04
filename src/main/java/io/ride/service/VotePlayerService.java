package io.ride.service;

import io.ride.DTO.ResultDTO;

import java.sql.SQLException;

public interface VotePlayerService {

    ResultDTO listByUser(int page, int userId) throws SQLException;

    ResultDTO detail(int playerId) throws SQLException;

    ResultDTO allowPlayer(int userId, int playerId) throws SQLException;

    ResultDTO denyPlayer(int userId, int playerId) throws SQLException;

    ResultDTO delete(int playerId) throws SQLException;
}
