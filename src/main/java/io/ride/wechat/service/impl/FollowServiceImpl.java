package io.ride.wechat.service.impl;

import io.ride.wechat.PO.Follow;
import io.ride.wechat.dao.FollowDao;
import io.ride.wechat.service.FollowService;

import java.sql.SQLException;

public class FollowServiceImpl implements FollowService {
    private FollowDao followDao = new FollowDao();


    @Override
    public Follow getFollow(String openId) throws SQLException {
        return followDao.queryByOpenId(openId);
    }

    @Override
    public void subscribe(String openId) throws SQLException {
        int result = followDao.insert(new Follow(openId));
        System.out.println(result == 0 ? "插入关注数据失败!" : "插入关注数据成功! 关注者openId ====> " + openId);
    }

    @Override
    public void unsubscribe(String openId) throws SQLException {
        int result = followDao.delete(openId);
        System.out.println(result == 0 ? "删除关注数据失败" : "删除openId为" + openId + "的关注者成功!");
    }
}
