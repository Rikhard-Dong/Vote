package io.ride.dao;

import io.ride.PO.VoteItem;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class VoteItemDao extends BaseDao {

    public Long count() throws SQLException {
        String sql = "select count(*) from t_vote_item";
        return runner.query(sql, new ScalarHandler<Long>());
    }

    public Integer countByItemId(int itemId) throws SQLException {
        String sql = "select count(*) from t_vote_item where id = ?";
        return runner.query(sql, new ScalarHandler<Integer>());
    }

    public List<VoteItem> queryByThemeId(int themeId) throws SQLException {
        String sql = "select * from t_vote_item where themeId = ?";

        return runner.query(sql, new BeanListHandler<>(VoteItem.class), themeId);
    }

    public List<VoteItem> queryByThemeLimit(int themeId, int start, int offset) throws SQLException {
        String sql = "select * from t_vote_item where themeId = ? limit ?, ?";
        return runner.query(sql, new BeanListHandler<>(VoteItem.class), themeId, start, offset);
    }

    public int add(VoteItem item) throws SQLException {
        String sql = "insert into t_vote_item(themeId, title, detail, photo, photo2) values(?,?,?,?,?)";
        Object[] param = {item.getThemeId(), item.getTitle(), item.getDetail(), item.getPhoto(), item.getPhoto2()};
        return runner.update(sql, param);
    }

    public int delete(int itemId) throws SQLException {
        String sql = "delete from t_vote_item where id = ?";
        return runner.update(sql, itemId);
    }

    public VoteItem queryById(int itemId) throws SQLException {
        String sql = "select * from t_vote_item where id = ?";
        return runner.query(sql, new BeanHandler<>(VoteItem.class), itemId);
    }
}
