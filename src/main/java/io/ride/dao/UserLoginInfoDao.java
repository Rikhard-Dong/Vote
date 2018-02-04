package io.ride.dao;

import io.ride.PO.UserLoginInfo;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class UserLoginInfoDao extends BaseDao {

    public int addOne(UserLoginInfo info) throws SQLException {
        String sql = "insert into t_user_login_info(userId, loginIp) values (? , ?)";
        Object[] params = {info.getUserId(), info.getLoginIp()};
        return runner.update(sql, params);
    }

    /**
     * 根据用户ID查询该用户的所有登录记录
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public List<UserLoginInfo> queryByUserId(int userId) throws SQLException {
        String sql = "Select * from t_user where userId = ?";
        return runner.query(sql, new BeanListHandler<UserLoginInfo>(UserLoginInfo.class), userId);
    }

}
