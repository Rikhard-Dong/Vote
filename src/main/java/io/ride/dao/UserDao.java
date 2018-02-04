package io.ride.dao;

import io.ride.PO.User;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDao extends BaseDao {

    /**
     * 添加一个用户
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public int addOne(User user) throws SQLException {
        String sql = "insert into t_user(username, password, email, nickname, headImage) values (?, ?, ?, ?, ?)";
        Object[] params = {user.getUsername(), user.getPassword(), user.getEmail(), user.getUsername(), user.getHeadImage()};
        return runner.update(sql, params);
    }

    /**
     * 查询所有用户
     *
     * @return
     * @throws SQLException
     */
    public List<User> queryAll() throws SQLException {
        String sql = "SELECT * from t_user";
        return runner.query(sql, new BeanListHandler<>(User.class));
    }

    public List<User> queryLimit(int start, int size) throws SQLException {
        String sql = "select * from t_user limit ?, ?";
        return runner.query(sql, new BeanListHandler<>(User.class), start, size);
    }

    /**
     * 根据id查询所有用户
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public User queryById(int userId) throws SQLException {
        String sql = "select * from t_user where id = ?";
        return runner.query(sql, new BeanHandler<>(User.class), userId);
    }

    /**
     * 根据邮箱查询所有用户
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public User queryByEmail(String email) throws SQLException {
        String sql = "select * from t_user where email = ?";
        return runner.query(sql, new BeanHandler<>(User.class), email);
    }

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     * @throws SQLException
     */
    public User queryByUsername(String username) throws SQLException {
        String sql = "select * from  t_user where username = ?";
        return runner.query(sql, new BeanHandler<>(User.class), username);
    }

    /**
     * 验证账户
     *
     * @param account
     * @param password
     * @return
     * @throws SQLException
     */
    public User validateAccount(String account, String password) throws SQLException {
        String sql = "select * from t_user where (username = ? or email = ?) and password = ?";
        return runner.query(sql, new BeanHandler<>(User.class), account, account, password);
    }

    public Long count() throws SQLException {
        String sql = "select count(*) from t_user";
        return runner.query(sql, new ScalarHandler<Long>());
    }

    public int delete(int id) throws SQLException {
        String sql = "delete from t_user where id = ?";
        return runner.update(sql, id);
    }


    public int updateHeadImage(int userId, String headImage) throws SQLException {
        String sql = "update t_user set headImage = ? where id = ?";
        return runner.update(sql, headImage, userId);
    }

    public int updatePassword(int userId, String password) throws SQLException {
        String sql = "update t_user set password = ? where id = ?";
        return runner.update(sql, password, userId);
    }

    public int updateNickname (int userId, String nickname) throws SQLException {
        String sql = "update t_user set nickname = ? where id = ?";
        return runner.update(sql, nickname, userId);
    }

    public int updateDesc (int userId, String desc) throws SQLException {
        String sql = "update t_user set `desc` = ? where id = ?";
        return runner.update(sql, desc, userId);
    }
}
