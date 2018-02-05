package io.ride.dao;

import io.ride.PO.VoteDetail;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.xml.soap.Detail;
import java.sql.SQLException;

public class VoteDetailDao extends BaseDao {

    /**
     * 根据itemId统计该选项下的投票数
     *
     * @param itemId
     * @return
     * @throws SQLException
     */
    public Long countByItemId(int itemId) throws SQLException {
        String sql = "select count(*) from t_vote_detail where itemId = ?";
        return runner.query(sql, new ScalarHandler<Long>(), itemId);
    }

    /**
     * 统计该投票主题下所有的投票数目
     *
     * @param themeId
     * @return
     * @throws SQLException
     */
    public Long countByThemeId(int themeId) throws SQLException {
        String sql = "select count(*) from t_vote_theme theme, t_vote_item item, t_vote_detail detail " +
                "where theme.id = item.themeId and item.id = detail.itemId " +
                "and theme.id = ?";
        return runner.query(sql, new ScalarHandler<Long>(), themeId);
    }

    /**
     * 同个某个主题投票下某个IP的参与次数
     *
     * @param themeId
     * @param ipAddress
     * @return
     * @throws SQLException
     */
    public Long countIpAddressItems(int themeId, String ipAddress) throws SQLException {
        String sql = "select count(*) from t_vote_theme t, t_vote_item i, t_vote_detail d " +
                "where t.id = i.themeId and i.id = d.itemId " +
                "and t.id = ? and d.ipAddress = ?";

        return runner.query(sql, new ScalarHandler<Long>(), themeId, ipAddress);
    }

    public Long count(int itemId, int ipAddress) throws SQLException {
        String sql = "select count(*) from t_vote_detail where itemId = ? and ipAddress = ?";
        return runner.query(sql, new ScalarHandler<Long>(), itemId, ipAddress);
    }

    /**
     * 插入一条记录
     *
     * @param detail
     * @return
     * @throws SQLException
     */
    public int insert(VoteDetail detail) throws SQLException {
        String sql = "insert into t_vote_detail(itemId, userId, openId, ipAddress) values (?,?,?,?)";
        Object[] params = {detail.getItemId(), detail.getUserId() == 0 ? null : detail.getUserId(),
                detail.getOpenId(), detail.getIpAddress()};
        return runner.update(sql, params);
    }


    /**
     * 根据主题和ip地址查询该IP地址在该主题下最近的一次投票
     *
     * @param themeId
     * @param ipAddress
     * @return
     * @throws SQLException
     */
    public VoteDetail getLeastDetailForIpAddress(int themeId, String ipAddress) throws SQLException {
        String sql = "select d.* from t_vote_theme t, t_vote_item i, t_vote_detail d " +
                "where t.id = i.themeId and i.id = d.itemId and t.id = ? and d.ipAddress = ? " +
                "order by d.voteTime desc " +
                "limit 1";
        return runner.query(sql, new BeanHandler<>(VoteDetail.class), themeId, ipAddress);
    }


    /**
     * 查询该主题下该微信号最近的一次投票
     *
     * @param themeId
     * @param openId
     * @return
     * @throws SQLException
     */
    public VoteDetail getLeastDetailForOpenId(int themeId, String openId) throws SQLException {
        String sql = "select d.* from t_vote_theme t, t_vote_item i, t_vote_detail d " +
                "where t.id = i.themeId and i.id = d.itemId and t.id = ? and d.openId = ? " +
                "order by d.voteTime desc " +
                "limit 1";
        return runner.query(sql, new BeanHandler<>(VoteDetail.class), themeId, openId);

    }
}
