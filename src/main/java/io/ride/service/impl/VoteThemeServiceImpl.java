package io.ride.service.impl;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleVoteThemeDto;
import io.ride.PO.User;
import io.ride.PO.VoteItem;
import io.ride.PO.VotePlayer;
import io.ride.PO.VoteTheme;
import io.ride.dao.UserDao;
import io.ride.dao.VoteItemDao;
import io.ride.dao.VotePlayerDao;
import io.ride.dao.VoteThemeDao;
import io.ride.service.VoteThemeService;
import io.ride.util.PageHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteThemeServiceImpl implements VoteThemeService {
    private VoteThemeDao themeDao = new VoteThemeDao();
    private UserDao userDao = new UserDao();
    private VoteItemDao itemDao = new VoteItemDao();
    private VotePlayerDao playerDao = new VotePlayerDao();

    private String basePath;

    public VoteThemeServiceImpl(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public SimpleVoteThemeDto getTheme(int id) throws SQLException {
        VoteTheme theme = themeDao.queryByThemeId(id);
        User user = userDao.queryById(theme.getUserId());
        return new SimpleVoteThemeDto(theme, user);
    }

    @Override
    public ResultDTO addTheme(VoteTheme theme) throws SQLException {
        if (StringUtils.isEmpty(theme.getTheme())) {
            return ResultDTO.FAIL("请输入投票主题");
        }
        if (StringUtils.isEmpty(theme.getDesc())) {
            return ResultDTO.FAIL("请输入描述");
        }

        int result = themeDao.insertOne(theme);
        int themeId = -1;
        if (result != 0) {
            themeId = themeDao.getLeastInsert(theme.getUserId());
        }

        return result == 0 ? ResultDTO.FAIL("发起投票失败") : ResultDTO.SUCCESS("发起投票成功").addData("themeId", themeId);
    }

    @Override
    public ResultDTO listTheme(int page) throws SQLException {
        Long count = themeDao.count();
        if (count == 0) {
            return ResultDTO.FAIL("当前没有数据");
        }
        PageHelper helper = new PageHelper(count, 10, page);
        List<VoteTheme> themes = themeDao.queryAllLimit((helper.getCurr() - 1) * helper.getSize(), helper.getSize());
        if (themes == null || themes.size() == 0) {
            return ResultDTO.FAIL("查询为空");
        }
        List<Object> simpleThemes = new ArrayList<>();
        for (VoteTheme theme : themes) {
            User user = userDao.queryById(theme.getUserId());
            SimpleVoteThemeDto simpleTheme = new SimpleVoteThemeDto(theme, user);
            simpleThemes.add(simpleTheme);
        }
        helper.setData(simpleThemes);
        return ResultDTO.SUCCESS("查询成功").addData("pageHelper", helper);
    }

    @Override
    public ResultDTO listThemeByUserId(int userId, int page) throws SQLException {
        Long count = themeDao.countByUserId(userId);
        if (count == 0) {
            return ResultDTO.FAIL("当前没有数据!");
        }
        User user = userDao.queryById(userId);
        if (user == null) {
            return ResultDTO.FAIL("用户不存在!");
        }
        PageHelper helper = new PageHelper(count, 10, page);
        List<VoteTheme> themes = themeDao.queryByUserIdLimit(userId,
                (helper.getCurr() - 1) * helper.getSize(), helper.getSize());

        List<Object> simpleThemes = new ArrayList<>();
        for (VoteTheme theme : themes) {
            simpleThemes.add(new SimpleVoteThemeDto(theme, user));
        }
        helper.setData(simpleThemes);
        return ResultDTO.SUCCESS("查询成功").addData("pageHelper", helper);
    }

    @Override
    public ResultDTO deleteTheme(int themeId) throws SQLException {
        deleteThemAbout(themeId);
        int result = themeDao.delete(themeId);
        return result == 0 ? ResultDTO.FAIL("删除失败") : ResultDTO.SUCCESS("删除成功");
    }

    @Override
    public ResultDTO updateDesc(int themeId, int userId, String desc) throws SQLException {
        VoteTheme theme = themeDao.queryByThemeId(themeId);
        if (theme == null) {
            return ResultDTO.FAIL("该投票不存在");
        }
        User user = userDao.queryById(userId);
        if (theme.getUserId() != userId && (user != null && user.getType() != 0)) {
            return ResultDTO.FAIL("没有权限");
        }
        int result = themeDao.updateDesc(themeId, desc);
        return result == 0 ? ResultDTO.FAIL("更新失败") : ResultDTO.SUCCESS("更新成功").addData("desc", desc);
    }

    private void deleteThemAbout(int themeId) throws SQLException {
        List<VotePlayer> players = playerDao.queryByThemeId(themeId);
        List<VoteItem> items = itemDao.queryByThemeId(themeId);

        for (VotePlayer player : players) {
            if (player.getPhoto() != null) {
                deleteFile(basePath + player.getPhoto());
            }
            if (player.getPhoto2() != null) {
                deleteFile(basePath + player.getPhoto2());
            }
            playerDao.delete(player.getId());
        }

        for (VoteItem item : items) {
            if (item.getPhoto() != null) {
                deleteFile(basePath + item.getPhoto());
            }
            if (item.getPhoto2() != null) {
                deleteFile(basePath + item.getPhoto2());
            }
            itemDao.delete(item.getId());
        }
    }

    private void deleteFile(String filename) {
        System.out.println("filename ======> " + filename);
        File file = new File(filename);
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println(file.getAbsoluteFile() + "文件删除失败!");
            }
        }
    }
}
