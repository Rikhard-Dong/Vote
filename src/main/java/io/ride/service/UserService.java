package io.ride.service;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleUserDTO;
import io.ride.PO.User;

import java.sql.SQLException;

public interface UserService {
    /**
     * 账号认证
     *
     * @param account  账号
     * @param password 密码
     * @return 认证用户
     * @throws SQLException 数据库操作异常
     */
    User validateAccount(String account, String password) throws SQLException;

    /**
     * 添加一个用户
     *
     * @param username         用户名
     * @param password         密码
     * @param password2        确认密码
     * @param email            邮箱
     * @param defaultHeadImage 默认头像
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO addUser(String username, String password, String password2, String email, String defaultHeadImage) throws SQLException;

    /**
     * 得到用户的简要信息
     *
     * @param userId user id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    SimpleUserDTO getSimpleUser(int userId) throws SQLException;

    /**
     * 用户登录记录
     *
     * @param userId  用户id
     * @param loginIp 登录ip
     * @return result
     * @throws SQLException 数据库操作异常
     */
    boolean addLoginInfo(int userId, String loginIp) throws SQLException;

    /**
     * 分页查询
     *
     * @param page 分页
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO listUser(int page) throws SQLException;

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO deleteUser(int id) throws SQLException;

    /**
     * 获取用户
     *
     * @param id 用户id
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO getUser(int id) throws SQLException;

    /**
     * 更新用户属性
     *
     * @param attr  更新的属性
     * @param value 对应属性的值
     * @return result
     * @throws SQLException 数据库操作异常
     */
    ResultDTO updateUser(int userId, String attr, String value) throws SQLException;
}
