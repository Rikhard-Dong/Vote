package io.ride.wechat.service;

import io.ride.wechat.PO.Follow;

import java.sql.SQLException;

public interface FollowService {


    Follow getFollow(String openId) throws SQLException;

    void subscribe(String openId) throws SQLException;

    void unsubscribe(String openId) throws SQLException;

}
