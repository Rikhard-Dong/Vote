package io.ride.service.impl;

import io.ride.service.UserService;
import io.ride.util.JacksonUtil;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    private UserService userService = new UserServiceImpl();

    @Test
    public void validateAccount() throws SQLException {
        System.out.println(userService.validateAccount("admin", "admin"));
    }

    @Test
    public void listUser() throws SQLException {
        System.out.println(JacksonUtil.toJSon(userService.listUser(1)));
    }
}