package io.ride.dao;

import io.ride.PO.VoteTheme;
import io.ride.util.DateUtil;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class VoteThemeDao extends BaseDao {

    public Long count() throws SQLException {
        String sql = "select count(*) from t_vote_theme";
        return runner.query(sql, new ScalarHandler<Long>());
    }

    public Long countByUserId(int userId) throws SQLException {
        String sql = "select count(*) from t_vote_theme where userId = ?";
        return runner.query(sql, new ScalarHandler<Long>(), userId);
    }

    public int insertOne(VoteTheme theme) throws SQLException {
        String sql = "insert into t_vote_theme(userId, theme, `desc`, startTime, endTime, isSingle, maxSelect, isAnonymous, timeDiff, ipMax) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {theme.getUserId(), theme.getTheme(), theme.getDesc(), DateUtil.date2Str(theme.getStartTime()),
                DateUtil.date2Str(theme.getEndTime()), theme.getIsSingle(), theme.getMaxSelect(),
                theme.getIsAnonymous(), theme.getTimeDiff(), theme.getIpMax()};

        return runner.update(sql, params);
    }


    public List<VoteTheme> queryAll() throws SQLException {
        String sql = "select * from t_vote_theme";
        return runner.query(sql, new BeanListHandler<>(VoteTheme.class));
    }

    public VoteTheme queryByThemeId(int id) throws SQLException {
        String sql = "select * from t_vote_theme where id = ?";

        return runner.query(sql, new BeanHandler<>(VoteTheme.class), id);
    }

    public List<VoteTheme> queryAllLimit(int start, int offset) throws SQLException {
        String sql = "select * from t_vote_theme order by createTime desc limit ?, ? ";
        return runner.query(sql, new BeanListHandler<>(VoteTheme.class), start, offset);
    }

    public List<VoteTheme> queryByUserId(int id) throws SQLException {
        String sql = "select * from t_vote_theme where userId = ?";
        return runner.query(sql, new BeanListHandler<>(VoteTheme.class), id);
    }

    public List<VoteTheme> queryByUserIdLimit(int id, int start, int offset) throws SQLException {
        String sql = "select * from t_vote_theme where userId = ? order by createTime desc limit ? , ?";
        return runner.query(sql, new BeanListHandler<>(VoteTheme.class), id, start, offset);
    }

    public int delete(int themeId) throws SQLException {
        String sql = "delete from t_vote_theme where id = ?";
        return runner.update(sql, themeId);
    }

    public int updateDesc(int themeId, String desc) throws SQLException {
        String sql = "update t_vote_theme set `desc` = ? where id = ?";
        return runner.update(sql, desc, themeId);
    }

    public int getLeastInsert(int userId) throws SQLException {
        String sql = "select id from t_vote_theme where userId = ? order by createTime desc limit 1";
        return runner.query(sql, new ScalarHandler<Integer>(), userId);
    }
}
