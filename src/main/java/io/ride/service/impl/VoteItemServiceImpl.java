package io.ride.service.impl;

import io.ride.DTO.RankDTO;
import io.ride.DTO.ResultDTO;
import io.ride.DTO.VoteDetailDTO;
import io.ride.PO.User;
import io.ride.PO.VoteItem;
import io.ride.PO.VotePlayer;
import io.ride.PO.VoteTheme;
import io.ride.dao.VoteDetailDao;
import io.ride.dao.VoteItemDao;
import io.ride.dao.VotePlayerDao;
import io.ride.dao.VoteThemeDao;
import io.ride.service.VoteItemService;

import java.text.DecimalFormat;
import java.util.*;
import java.sql.SQLException;

public class VoteItemServiceImpl implements VoteItemService {

    private VoteThemeDao themeDao = new VoteThemeDao();
    private VoteItemDao itemDao = new VoteItemDao();
    private VoteDetailDao detailDao = new VoteDetailDao();
    private VotePlayerDao playerDao = new VotePlayerDao();

    @Override
    public ResultDTO addItem(VoteItem item) throws SQLException {
        int result = itemDao.add(item);

        return result == 0 ? ResultDTO.FAIL("添加失败") : ResultDTO.SUCCESS("添加成功");
    }

    @Override
    public ResultDTO applyItem(VotePlayer player) throws SQLException {

        int result = playerDao.insert(player);
        return result == 0 ? ResultDTO.FAIL("申请失败!") : ResultDTO.SUCCESS("申请成功!等待投票发起者审核");
    }

    @Override
    public ResultDTO getItems(int themeId, String votedItem, User user) throws SQLException {
        VoteTheme theme = themeDao.queryByThemeId(themeId);
        if (theme == null) {
            return ResultDTO.FAIL("该投票主题不存在");
        }

        if (theme.getIsAnonymous() == 1) {
            // 不允许匿名投票
            if (user == null) {
                return ResultDTO.FAIL("此投票需要登录操作");
            }
        }

        Date currTime = new Date();
        int status;
        if (currTime.before(theme.getStartTime())) {
            // 投票未开始
            status = 0;
        } else if (currTime.after(theme.getEndTime())) {
            // 投票已结束
            status = 3;
        } else {
            // 投票进行中
            if (votedItem != null) {
                // 已投票
                status = 2;
            } else {
                // 未投票
                status = 1;
            }
        }

        int isSingle = theme.getIsSingle();
        int num = isSingle == 0 ? 1 : theme.getMaxSelect();
        List<VoteItem> items = itemDao.queryByThemeId(themeId);
        int isEmpty = items.size() == 0 ? 1 : 0;

        VoteDetailDTO detailDTO = new VoteDetailDTO(themeId, isSingle, status, num, isEmpty);
        detailDTO.setTimeDiff(theme.getTimeDiff());
        long sum = 0;
        if (status == 2 || status == 3) {
            for (VoteItem item : items) {
                Long itemSum = detailDao.countByItemId(item.getId());
                item.setSum(itemSum);
                sum += itemSum;
            }
            detailDTO.setSum(sum);
            DecimalFormat format = new DecimalFormat("#.00");
            // 计算每个票数的百分比
            for (VoteItem item : items) {
                float per;
                if (sum == 0) {
                    per = 0;
                } else {
                    per = (float) (item.getSum() * 1.0 / sum);
                    per *= 100;
                    per = Float.parseFloat(format.format(per));
                }
                item.setPercent(per);
            }
        }
        detailDTO.setItems(items);
        System.out.println("item service ======> " + detailDTO);

        return ResultDTO.SUCCESS("查询成功").addData("itemDetail", detailDTO);
    }

    @Override
    public ResultDTO rankItems(int themeId) throws SQLException {
        VoteTheme theme = themeDao.queryByThemeId(themeId);
        if (theme == null) {
            return ResultDTO.FAIL("该投票不存在=~=");
        }

        Date curr = new Date();
        if (curr.before(theme.getStartTime())) {
            return ResultDTO.FAIL("投票未开始");
        }

        List<VoteItem> items = itemDao.queryByThemeId(themeId);
        List<RankDTO> ranks = new ArrayList<>();
        if (items == null || items.size() <= 0) {
            return ResultDTO.FAIL("当前没有选项");
        }

        long count = detailDao.countByThemeId(themeId);
        DecimalFormat format = new DecimalFormat("#.00");
        for (VoteItem item : items) {
            double per;
            long itemCount = detailDao.countByItemId(item.getId());
            if (count == 0) {
                per = 0;
            } else {
                per = itemCount * 1.0 / count;
                per *= 100;
                per = Double.parseDouble(format.format(per));
            }

            RankDTO rank = new RankDTO();
            rank.setPer(per);
            rank.setItem(item.getTitle());
            rank.setDesc("(" + itemCount + "/" + count + ")");

            ranks.add(rank);
        }

        Collections.sort(ranks, (rank1, rank2) -> rank1.getPer() > rank2.getPer() ? -1 : 1);

        /*
         * 增加排名信息
         */
        int rank = 1;
        boolean first = true;
        int cnt = 1;
        double pre = 0;
        for (RankDTO var : ranks) {
            if (first) {
                pre = var.getPer();
                var.setItem("第" + rank + "名:" + var.getItem());
                first = false;

            } else {
                if (pre == var.getPer()) {
                    var.setItem("第" + rank + "名:" + var.getItem());
                    cnt++;
                } else {
                    rank += cnt;
                    cnt = 1;
                    var.setItem("第" + rank + "名:" + var.getItem());
                    pre = var.getPer();
                }
            }
        }

        return ResultDTO.SUCCESS("查询成功").addData("ranks", ranks);
    }
}
