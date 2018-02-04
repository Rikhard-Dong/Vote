package io.ride.wechat.dao;

import io.ride.dao.BaseDao;
import io.ride.wechat.PO.Follow;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class FollowDao extends BaseDao {

    public int insert(Follow follow) throws SQLException {
        String sql = "insert into t_wechat_follow(openId) values(?)";
        return runner.update(sql, follow.getOpenId());
    }

    public int delete(String openId) throws SQLException {
        String sql = "delete from t_wechat_follow where openId = ?";
        return runner.update(sql, openId);
    }

    public Follow queryByOpenId(String openId) throws SQLException {
        String sql = "select * from t_wechat_follow where openId = ?";
        return runner.query(sql, new BeanHandler<>(Follow.class), openId);
    }

}
