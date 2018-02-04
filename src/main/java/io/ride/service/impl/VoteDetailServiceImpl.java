package io.ride.service.impl;

import io.ride.DTO.ResultDTO;
import io.ride.PO.VoteDetail;
import io.ride.PO.VoteItem;
import io.ride.PO.VoteTheme;
import io.ride.dao.VoteDetailDao;
import io.ride.dao.VoteItemDao;
import io.ride.dao.VoteThemeDao;
import io.ride.service.VoteDetailService;

import java.sql.SQLException;
import java.util.List;

public class VoteDetailServiceImpl implements VoteDetailService {

    private VoteDetailDao detailDao = new VoteDetailDao();
    private VoteItemDao itemDao = new VoteItemDao();
    private VoteThemeDao themeDao = new VoteThemeDao();

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



        int result = 1;
        for (VoteDetail detail : details) {
            if (detailDao.insert(detail) == 0) {
                result = 0;
            }
        }
        return result == 1 ? ResultDTO.SUCCESS("投票成功") : ResultDTO.FAIL("投票失败");
    }
}
