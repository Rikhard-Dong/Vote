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
     */
    User validateAccount(String account, String password) throws SQLException;

    ResultDTO addUser(String username, String password, String password2, String email, String defaultHeadImage) throws SQLException;

    /**
     * 得到用户的简要信息
     *
     * @param userId
     * @return
     */
    SimpleUserDTO getSimpleUser(int userId) throws SQLException;

    /**
     * 用户登录记录
     *
     * @param userId
     * @param loginIp
     * @return
     * @throws SQLException
     */
    boolean addLoginInfo(int userId, String loginIp) throws SQLException;

    /**
     * 分页查询
     *
     * @param page
     * @return
     * @throws SQLException
     */
    ResultDTO listUser(int page) throws SQLException;

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     * @throws SQLException
     */
    ResultDTO deleteUser(int id) throws SQLException;

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    ResultDTO getUser(int id) throws SQLException;

    /**
     * 更新用户属性
     *
     * @param attr
     * @param value
     * @return
     */
    ResultDTO updateUser(int userId, String attr, String value) throws SQLException;
}
