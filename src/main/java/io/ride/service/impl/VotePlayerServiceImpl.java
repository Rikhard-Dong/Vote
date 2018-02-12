package io.ride.service.impl;

import io.ride.DTO.PlayerDetailDTO;
import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimplePlayerDto;
import io.ride.PO.VoteItem;
import io.ride.PO.VotePlayer;
import io.ride.PO.VoteTheme;
import io.ride.dao.VoteItemDao;
import io.ride.dao.VotePlayerDao;
import io.ride.dao.VoteThemeDao;
import io.ride.service.VotePlayerService;
import io.ride.util.PageHelper;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VotePlayerServiceImpl implements VotePlayerService {

    private VotePlayerDao playerDao = new VotePlayerDao();
    private VoteThemeDao themeDao = new VoteThemeDao();
    private VoteItemDao itemDao = new VoteItemDao();
    private String basePath;


    public VotePlayerServiceImpl() {
    }

    public VotePlayerServiceImpl(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public ResultDTO listByUser(int page, int userId) throws SQLException {
        updateStatus(userId);

        Long count = playerDao.countByUserId(userId);
        PageHelper helper = new PageHelper(count, 10, page);

        List<VotePlayer> players = playerDao.queryByUserIdLimit(userId, (helper.getCurr() - 1) * helper.getSize(), helper.getSize());
        if (players.size() == 0) {
            return ResultDTO.FAIL("当前没有审核申请!");
        }
        List<Object> simplePlayers = new ArrayList<>();
        for (VotePlayer player : players) {
            SimplePlayerDto playerDto = new SimplePlayerDto(player);
            simplePlayers.add(playerDto);
        }
        helper.setData(simplePlayers);

        return ResultDTO.SUCCESS("查询成功!").addData("pageHelper", helper);
    }

    @Override
    public ResultDTO detail(int playerId) throws SQLException {
        VotePlayer player = playerDao.queryById(playerId);
        if (player == null) {
            return ResultDTO.FAIL("当前申请不存在");
        }
        VoteTheme theme = themeDao.queryByThemeId(player.getThemeId());
        if (theme == null) {
            return ResultDTO.FAIL("当前投票不存在");
        }
        PlayerDetailDTO playerDetail = new PlayerDetailDTO(theme, player);
        return ResultDTO.SUCCESS("查询成功").addData("player", playerDetail);
    }

    @Override
    public ResultDTO allowPlayer(int userId, int playerId) throws SQLException {
        return base(userId, playerId, 1);
    }

    @Override
    public ResultDTO denyPlayer(int userId, int playerId) throws SQLException {
        return base(userId, playerId, 2);
    }


    private ResultDTO base(int userId, int playerId, int status) throws SQLException {
        VotePlayer player = playerDao.queryById(playerId);
        if (player == null) {
            return ResultDTO.FAIL("该申请不存在!");
        }
        VoteTheme theme = themeDao.queryByThemeId(player.getThemeId());
        Date curDate = new Date();
        if (curDate.after(theme.getEndTime())) {
            updateStatus(userId);
            return ResultDTO.FAIL("该申请已过期!");
        }
        if (status == 1) {
            // 同步到item表
            VoteItem item = new VoteItem(player.getThemeId(), player.getTitle(), player.getDetail(),
                    player.getPhoto(), player.getPhoto2());
            itemDao.add(item);
        }

        int result = playerDao.updateStatus(playerId, status);
        return result == 0 ? ResultDTO.FAIL("更新失败") : ResultDTO.SUCCESS("更新成功");
    }

    @Override
    public ResultDTO delete(int playerId) throws SQLException {
        VotePlayer player = playerDao.queryById(playerId);
        int result = playerDao.delete(playerId);
        if (result == 1) {
            // 删除成功联通照片一起删掉
            if (player.getPhoto() != null) {
                deleteFile(basePath + player.getPhoto());
            }
            if (player.getPhoto2() != null) {
                deleteFile(basePath + player.getPhoto2());
            }
        }
        return result == 0 ? ResultDTO.FAIL("删除失败!") : ResultDTO.SUCCESS("删除成功!");
    }

    private void updateStatus(int userId) throws SQLException {
        Date curDate = new Date();

        List<VotePlayer> players = playerDao.queryByUserId(userId);
        for (VotePlayer player : players) {
            if (player.getStatus() == 0) {
                VoteTheme theme = themeDao.queryByThemeId(player.getThemeId());
                if (curDate.after(theme.getEndTime())) {
                    // 投票已经开始, 当前申请已过期 状态3 过期
                    playerDao.updateStatus(player.getId(), 3);
                }
            }
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
