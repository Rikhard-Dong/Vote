package io.ride.service;

import io.ride.DTO.ResultDTO;
import io.ride.PO.VoteDetail;

import java.sql.SQLException;
import java.util.List;

public interface VoteDetailService {

    ResultDTO addDetails(List<VoteDetail> details) throws SQLException;
}
