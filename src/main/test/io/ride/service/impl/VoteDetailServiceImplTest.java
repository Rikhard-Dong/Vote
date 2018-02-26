package io.ride.service.impl;

import io.ride.service.VoteDetailService;
import org.junit.Test;

import java.sql.SQLException;

public class VoteDetailServiceImplTest {
    private VoteDetailService detailService = new VoteDetailServiceImpl();

    @Test
    public void votedStatistics() throws SQLException {
        System.out.println(detailService.votedSourcePie(5));
    }
}