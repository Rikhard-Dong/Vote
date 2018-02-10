package io.ride.service.impl;

import com.github.pagehelper.util.StringUtil;
import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleUserDTO;
import io.ride.PO.User;
import io.ride.PO.UserLoginInfo;
import io.ride.dao.UserDao;
import io.ride.dao.UserLoginInfoDao;
import io.ride.service.UserService;
import io.ride.util.EncryptionUtil;
import io.ride.util.PageHelper;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDao();
    private UserLoginInfoDao infoDao = new UserLoginInfoDao();

    public User validateAccount(String account, String password) throws SQLException {
        password = EncryptionUtil.md5(password);
        return userDao.validateAccount(account, password);
    }

    public ResultDTO addUser(String username, String password, String password2, String email, String defaultHeadImage) throws SQLException {
        if (!StringUtils.equals(password, password2)) {
            return ResultDTO.FAIL("密码不一致");
        }
        User user = userDao.queryByUsername(username);
        if (user != null) {
            return ResultDTO.FAIL("该用户已存在");
        }
        user = userDao.queryByEmail(email);
        if (user != null) {
            return ResultDTO.FAIL("邮箱已存在");
        }
        password = EncryptionUtil.md5(password);
        user = new User(username, password, email, defaultHeadImage);
        return userDao.addOne(user) != 0 ? ResultDTO.SUCCESS("用户注册成功") :
                ResultDTO.FAIL("用户注册失败");
    }

    @Override
    public SimpleUserDTO getSimpleUser(int userId) throws SQLException {
        User user = userDao.queryById(userId);
        return new SimpleUserDTO(user);
    }

    public boolean addLoginInfo(int userId, String loginIp) throws SQLException {
        UserLoginInfo info = new UserLoginInfo(userId, loginIp);
        return infoDao.addOne(info) != 0;
    }

    public ResultDTO listUser(int page) throws SQLException {
        int count = Math.toIntExact(userDao.count());
        PageHelper helper = new PageHelper(count, 10, page);
        List<User> users = userDao.queryLimit((helper.getCurr() - 1) * helper.getSize(), helper.getSize());
        if (users == null || users.size() <= 0) {
            return ResultDTO.FAIL("没有用户");
        }

        List<Object> simpleUsers = new ArrayList<>();
        for (User user : users) {
            simpleUsers.add(new SimpleUserDTO(user));
        }
        helper.setData(simpleUsers);

        return ResultDTO.SUCCESS("查询所有成功").addData("pageInfo", helper);
    }

    @Override
    public ResultDTO deleteUser(int id) throws SQLException {
        User user = userDao.queryById(id);
        if (user == null) {
            return ResultDTO.FAIL("删除失败, 不存在该用户");
        }
        int result = userDao.delete(id);
        return result == 0 ? ResultDTO.FAIL("删除失败") : ResultDTO.SUCCESS("删除成功");
    }

    @Override
    public ResultDTO getUser(int id) throws SQLException {
        User user = userDao.queryById(id);
        if (user == null) {
            return ResultDTO.FAIL("用户不存在");
        }
        SimpleUserDTO simpleUser = new SimpleUserDTO(user);
        return ResultDTO.SUCCESS("查询成功").addData("user", simpleUser);
    }

    @Override
    public ResultDTO updateUser(int userId, String attr, String value) throws SQLException {
        User user = userDao.queryById(userId);
        if (user == null) {
            return ResultDTO.FAIL("更新用户不存在");
        }
        int result = 0;
        if (StringUtils.equals(attr, "headImage")) {
            result = userDao.updateHeadImage(userId, value);
        } else if (StringUtils.equals(attr, "password")) {
            result = userDao.updatePassword(userId, EncryptionUtil.md5(value));
        } else if (StringUtils.equals(attr, "nickname")) {
            result = userDao.updateNickname(userId, value);
        } else if (StringUtils.equals(attr, "desc")) {
            result = userDao.updateDesc(userId, value);
        }
        return result == 0 ? ResultDTO.FAIL("更新失败") : ResultDTO.SUCCESS("更新成功");
    }
}
