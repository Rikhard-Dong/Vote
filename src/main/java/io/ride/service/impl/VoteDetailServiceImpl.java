package io.ride.service.impl;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SimpleVoteDetailDTO;
import io.ride.PO.User;
import io.ride.PO.VoteDetail;
import io.ride.PO.VoteItem;
import io.ride.PO.VoteTheme;
import io.ride.dao.UserDao;
import io.ride.dao.VoteDetailDao;
import io.ride.dao.VoteItemDao;
import io.ride.dao.VoteThemeDao;
import io.ride.service.VoteDetailService;
import io.ride.util.DateUtil;
import io.ride.util.PageHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteDetailServiceImpl implements VoteDetailService {

    private VoteDetailDao detailDao = new VoteDetailDao();
    private VoteItemDao itemDao = new VoteItemDao();
    private VoteThemeDao themeDao = new VoteThemeDao();
    private UserDao userDao = new UserDao();

    @Override
    public ResultDTO addDetails(List<VoteDetail> details) throws SQLException {
        if (details.size() == 0) {
            return ResultDTO.FAIL("添加投票过程错误");
        }

        VoteItem firstItem = itemDao.queryById(details.get(0).getItemId());
        VoteTheme theme = themeDao.queryByThemeId(firstItem.getThemeId());
        if (theme == null) {
            return ResultDTO.FAIL("投票主题不存在");
        }

        if (theme.getIpMax() != -1) {
            long count = detailDao.countIpAddressItems(theme.getId(), details.get(0).getIpAddress());
            if (count >= theme.getIpMax()) {
                return ResultDTO.FAIL("暂时无法投票, 投票超出IP限制");
            }
        }

        VoteDetail leastDetail = detailDao.getLeastDetailForIpAddress(theme.getId(), details.get(0).getIpAddress());
        if (leastDetail != null) {
            if (!DateUtil.expire(leastDetail.getVoteTime(), theme.getTimeDiff() * 60 * 1000)) {
                return ResultDTO.FAIL("投票冷却中(；´ﾟωﾟ｀人)");
            }
        }

        int result = 1;
        for (VoteDetail detail : details) {
            if (detailDao.insert(detail) == 0) {
                result = 0;
            }
        }
        return result == 1 ? ResultDTO.SUCCESS("投票成功") : ResultDTO.FAIL("投票失败");
    }

    @Override
    public ResultDTO votedSourcePie(int themeId) throws SQLException {
        VoteTheme theme = themeDao.queryByThemeId(themeId);
        if (theme == null) {
            return ResultDTO.FAIL("投票不存在=~=");
        }

        long count = detailDao.countByThemeId(themeId);
        if (count <= 0) {
            return ResultDTO.FAIL("当前没有投票=~=");
        }
        List<List<Object>> data = new ArrayList<>();

        long all = detailDao.countByWechatAndLogin(themeId);
        long onlyWechat = detailDao.countByOnlyWechat(themeId);
        long onlyLogin = detailDao.countByOnlyLogin(themeId);
        long anonymous = detailDao.countByAnonymous(themeId);


        List<Object> allList = new ArrayList<>();
        allList.add("既是微信投票, 也是登录投票");
        allList.add(all * 1.0 / count * 100);

        List<Object> onlyWechatList = new ArrayList<>();
        onlyWechatList.add("微信投票");
        onlyWechatList.add(onlyWechat * 1.0 / count * 100);

        List<Object> onlyLoginList = new ArrayList<>();
        onlyLoginList.add("登录投票");
        onlyLoginList.add(onlyLogin * 1.0 / count * 100);

        List<Object> anonymousList = new ArrayList<>();
        anonymousList.add("匿名投票");
        anonymousList.add(anonymous * 1.0 / count * 100);


        if (all > 0)
            data.add(allList);
        if (onlyWechat > 0)
            data.add(onlyWechatList);
        if (onlyLogin > 0)
            data.add(onlyLoginList);
        if (anonymous > 0)
            data.add(anonymousList);

        ResultDTO resultDTO = ResultDTO.SUCCESS("查询成功");
        resultDTO.addData("data", data);
        resultDTO.addData("title", "投票来源占比");
        resultDTO.addData("count", count);

        return resultDTO;
    }

    @Override
    public ResultDTO detailList(int page, int themeId) throws SQLException {
        int count = Math.toIntExact(detailDao.countByThemeId(themeId));
        PageHelper helper = new PageHelper(count, 10, page);
        List<VoteDetail> details = detailDao.queryDetailByThemeIdLimit(themeId,
                (helper.getCurr() - 1) * helper.getSize(), helper.getSize());
        if (details == null || details.size() <= 0) {
            return ResultDTO.FAIL("当前没有投票");
        }
        List<Object> detailDTOS = new ArrayList<>();
        for (VoteDetail detail : details) {
            SimpleVoteDetailDTO detailDTO = new SimpleVoteDetailDTO(detail);
            if (detailDTO.getUserId() != null) {
                User user = userDao.queryById(detailDTO.getUserId());
                if (user != null) {
                    detailDTO.setNickname(user.getNickname());
                }
            }
            VoteItem item = itemDao.queryById(detailDTO.getItemId());
            if (item != null) {
                detailDTO.setItemTitle(item.getTitle());
            }
            detailDTOS.add(detailDTO);
        }

        helper.setData(detailDTOS);

        return ResultDTO.SUCCESS("helper").addData("helper", helper);
    }
}
