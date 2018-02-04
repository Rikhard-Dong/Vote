package io.ride.dao;

import io.ride.PO.VoteTheme;
import io.ride.util.DateUtil;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class VoteThemeDaoTest {

    private VoteThemeDao themeDao = new VoteThemeDao();

    @Test
    public void count() throws SQLException {
        System.out.println(themeDao.count());
    }

    @Test
    public void insertOne() throws ParseException, SQLException {
        Date startTime = DateUtil.str2Date("2018-01-24 12:10:13");
        Date endTime = DateUtil.str2Date("2018-01-25 12:10:13");
        VoteTheme voteTheme = new VoteTheme(1, "测试投票", "这是一个测试投票", startTime, endTime, 0, 0, 0);
        themeDao.insertOne(voteTheme);
    }

    @Test
    public void queryAll() {
    }

    @Test
    public void queryByUserId() {
    }

    @Test
    public void count1() throws SQLException {
    }

    @Test
    public void getLasertInsert() throws SQLException {
        System.out.println(themeDao.getLeastInsert(2));
    }
}