package io.ride.dao;

import org.junit.Test;

import java.sql.SQLException;

public class VoteDetailDaoTest {

    private VoteDetailDao detailDao = new VoteDetailDao();


    @Test
    public void getLeastDetail() throws SQLException {
        System.out.println(detailDao.getLeastDetailForIpAddress(8, "127.0.0.1"));

    }

    @Test
    public void countByWechatAndLogin() throws SQLException {
        System.out.println("count: " + detailDao.countByThemeId(5));
        System.out.println("ALL: " + detailDao.countByWechatAndLogin(5));
        System.out.println("wechat: " + detailDao.countByOnlyWechat(5));
        System.out.println("login: " + detailDao.countByOnlyLogin(5));
        System.out.println("anonymous: " + detailDao.countByAnonymous(5));
    }
}