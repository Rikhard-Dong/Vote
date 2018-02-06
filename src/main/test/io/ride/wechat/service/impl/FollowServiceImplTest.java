package io.ride.wechat.service.impl;

import io.ride.wechat.service.FollowService;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class FollowServiceImplTest {

    private FollowService followService = new FollowServiceImpl();

    @Test
    public void getFollow() throws SQLException {
        String openId = "opE4s1VUajz9Ft7Vg6e6Voo1EOOo";
        System.out.println(followService.getFollow(openId));
    }
}