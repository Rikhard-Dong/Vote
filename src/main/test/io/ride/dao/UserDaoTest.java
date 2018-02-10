package io.ride.dao;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDaoTest {
    private UserDao userDao = new UserDao();

    @Test
    public void queryAll() throws SQLException {
        System.out.println(userDao.queryAll());
    }

    @Test
    public void validateAccount() throws SQLException {
        System.out.println(userDao.validateAccount("admin", "admin"));
        System.out.println(userDao.validateAccount("1270458214@qq.com", "admin"));
    }

    @Test
    public void count() throws SQLException {
        System.out.println(userDao.count());
    }

    @Test
    public void queryLimit() throws SQLException {
        System.out.println(userDao.queryLimit(3, 0));
    }

    @Test
    public void searchUser() throws SQLException {
        String regex = "ride";
        regex = "[" + regex + "]{" + regex.length() + "}";
        System.out.println(userDao.searchUser(regex));
    }
}