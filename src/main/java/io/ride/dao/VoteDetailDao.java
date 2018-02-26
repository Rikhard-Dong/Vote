package io.ride.dao;

import io.ride.PO.VoteDetail;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class VoteDetailDao extends BaseDao {

    /**
     * 根据itemId统计该选项下的投票数
     *
     * @param itemId itemId
     * @return 该投票的数目
     * @throws SQLException 数据库操作异常
     */
    public Long countByItemId(int itemId) throws SQLException {
        String sql = "select count(*) from t_vote_detail where itemId = ?";
        return runner.query(sql, new ScalarHandler<Long>(), itemId);
    }

    /**
     * 统计该投票主题下所有的投票数目
     *
     * @param themeId theme id
     * @return result
     * @throws SQLException 数据库操作异常
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
     * @param themeId   theme id
     * @param ipAddress ip地址
     * @return 该IP地址参与本次投票的次数
     * @throws SQLException 数据库操作异常
     */
    public Long countIpAddressItems(int themeId, String ipAddress) throws SQLException {
        String sql = "select count(*) from t_vote_theme t, t_vote_item i, t_vote_detail d " +
                "where t.id = i.themeId and i.id = d.itemId " +
                "and t.id = ? and d.ipAddress = ?";

        return runner.query(sql, new ScalarHandler<Long>(), themeId, ipAddress);
    }

    /**
     * 插入一条投票细则记录
     *
     * @param detail 投票细则
     * @return 更新行数
     * @throws SQLException 数据库异常
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
     * @param themeId   theme id
     * @param ipAddress ip地址
     * @return 该ip最近一次的投票
     * @throws SQLException 数据库操作异常
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
     * @param themeId theme id
     * @param openId  微信 open id
     * @return 该微信号在该投票下最近的一次投票记录
     * @throws SQLException 数据库操作异常
     */
    public VoteDetail getLeastDetailForOpenId(int themeId, String openId) throws SQLException {
        String sql = "select d.* from t_vote_theme t, t_vote_item i, t_vote_detail d " +
                "where t.id = i.themeId and i.id = d.itemId and t.id = ? and d.openId = ? " +
                "order by d.voteTime desc " +
                "limit 1";
        return runner.query(sql, new BeanHandler<>(VoteDetail.class), themeId, openId);
    }

    /**
     * 统计某个投票下既是微信投票又是用户投票的数量
     *
     * @param themeId theme id
     * @return 数量
     * @throws SQLException 数据库操作异常
     */
    public Long countByWechatAndLogin(int themeId) throws SQLException {
        String sql = "select count(*) from t_vote_theme theme, t_vote_item item, t_vote_detail detail " +
                "where theme.id = item.themeId and item.id = detail.itemId " +
                "and detail.userId is not null and detail.openId is not null " +
                "and theme.id = ?";

        return runner.query(sql, new ScalarHandler<Long>(), themeId);
    }

    /**
     * 查询某个投票下微信投票的数量
     *
     * @param themeId themeId
     * @return 数量
     * @throws SQLException 数据库操作异常
     */
    public Long countByOnlyWechat(int themeId) throws SQLException {
        String sql = "select count(*) from t_vote_theme theme, t_vote_item item, t_vote_detail detail " +
                "where theme.id = item.themeId and item.id = detail.itemId " +
                "and detail.userId is null and detail.openId is not null " +
                "and theme.id = ?";

        return runner.query(sql, new ScalarHandler<Long>(), themeId);
    }

    /**
     * 统计某个投票下用户投票的数量
     *
     * @param themeId theme id
     * @return 数量
     * @throws SQLException 数据库操作异常
     */
    public Long countByOnlyLogin(int themeId) throws SQLException {
        String sql = "select count(*) from t_vote_theme theme, t_vote_item item, t_vote_detail detail " +
                "where theme.id = item.themeId and item.id = detail.itemId " +
                "and detail.userId is not null and detail.openId is null " +
                "and theme.id = ?";

        return runner.query(sql, new ScalarHandler<Long>(), themeId);
    }

    /**
     * 统计某个投票下匿名投票的情况
     *
     * @param themeId theme id
     * @return 数量
     * @throws SQLException 数据库操作异常
     */
    public Long countByAnonymous(int themeId) throws SQLException {
        String sql = "select count(*) from t_vote_theme theme, t_vote_item item, t_vote_detail detail " +
                "where theme.id = item.themeId and item.id = detail.itemId " +
                "and detail.userId is null and detail.openId is null " +
                "and theme.id = ?";

        return runner.query(sql, new ScalarHandler<Long>(), themeId);
    }

    /**
     * 分页查询某个投票的投票情况
     *
     * @param themeId theme id
     * @param start   开始
     * @param offset  偏移量
     * @return 分页查询的集合
     * @throws SQLException 数据库操作异常
     */
    public List<VoteDetail> queryDetailByThemeIdLimit(int themeId, int start, int offset) throws SQLException {
        String sql = "select detail.* from t_vote_theme theme, t_vote_item item, t_vote_detail detail " +
                "where theme.id = item.themeId and item.id = detail.itemId and theme.id = ? " +
                "order by detail.voteTime desc limit ?, ?" ;

        return runner.query(sql, new BeanListHandler<>(VoteDetail.class), themeId, start, offset);
    }
}
