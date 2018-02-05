package io.ride.dao;

import org.junit.Test;

import java.sql.SQLException;

public class VoteDetailDaoTest {

    private VoteDetailDao detailDao = new VoteDetailDao();


    @Test
    public void getLeastDetail() throws SQLException {
        System.out.println(detailDao.getLeastDetailForIpAddress(8, "127.0.0.1"));

    }
}