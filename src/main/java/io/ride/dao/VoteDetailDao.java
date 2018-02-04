package io.ride.dao;

import io.ride.PO.VoteDetail;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.xml.soap.Detail;
import java.sql.SQLException;

public class VoteDetailDao extends BaseDao {

    public Long count(int itemId) throws SQLException {
        String sql = "select count(*) from t_vote_detail where itemId = ?";
        return runner.query(sql, new ScalarHandler<Long>(), itemId);
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
                "adn t.id = ? and d.ipAddress = ?";

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
    public VoteDetail getLeastDetail(int themeId, String ipAddress) throws SQLException {
        String sql = "select d.* from t_vote_theme t, t_vote_item i, t_vote_detail d " +
                "where t.id = i.themeId and i.id = d.itemId and t.id = ? and d.ipAddress = ? " +
                "order by d.voteTime desc " +
                "limit 1";
        return runner.query(sql, new BeanHandler<>(VoteDetail.class), themeId, ipAddress);
    }
}
