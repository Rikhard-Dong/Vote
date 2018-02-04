package io.ride.dao;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class VoteDetailDaoTest {

    private VoteDetailDao detailDao = new VoteDetailDao();


    @Test
    public void getLeastDetail() throws SQLException {
        System.out.println(detailDao.getLeastDetail(3, "127.0.0.1"));

    }
}