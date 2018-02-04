package io.ride.dao;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class VoteItemDaoTest {

    private  VoteItemDao itemDao;

    @Test
    public void queryByThemeId() throws SQLException {
        System.out.println(itemDao.queryByThemeId(5));
    }
}