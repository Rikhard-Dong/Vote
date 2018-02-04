package io.ride.dao;

import io.ride.PO.VotePlayer;
import io.ride.PO.VoteTheme;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class VotePlayerDao extends BaseDao {

    public Long count() throws SQLException {
        String sql = "select count(*) from t_vote_player";
        return runner.query(sql, new ScalarHandler<Long>());
    }

    public List<VotePlayer> queryByThemeLimit(int id, int start, int size) throws SQLException {
        String sql = "select * from t_vote_player where themeId = ?  limit ? offset ?";
        return runner.query(sql, new BeanListHandler<>(VotePlayer.class), id, size, start);
    }

    public Long countByUserId(int userId) throws SQLException {
        String sql = "select count(*) from t_vote_player p, t_vote_theme t where t.id = p.themeId and t.userId = ?";
        return runner.query(sql, new ScalarHandler<Long>(), userId);

    }

    public List<VotePlayer> queryByUserId(int userId) throws SQLException {
        String sql = "select p.* from t_vote_player p, t_vote_theme t where t.id = p.themeId and t.userId = ?";
        return runner.query(sql, new BeanListHandler<>(VotePlayer.class), userId);
    }

    public List<VotePlayer> queryByUserIdLimit(int userId, int start, int size) throws SQLException {
        String sql = "select p.* from t_vote_player p, t_vote_theme t where t.id = p.themeId and t.userId = ? order by createTime desc limit ?, ?";
        return runner.query(sql, new BeanListHandler<>(VotePlayer.class), userId, start, size);
    }

    public int updateStatus(int id, int status) throws SQLException {
        String sql = "update t_vote_player set status = ? where id = ?";
        return runner.update(sql, status, id);
    }

    public List<VotePlayer> queryByThemeId(int id) throws SQLException {
        String sql = "select * from t_vote_player where themeId = ?";
        return runner.query(sql, new BeanListHandler<>(VotePlayer.class), id);
    }

    public int delete(int id) throws SQLException {
        String sql = "delete from t_vote_player where id = ?";
        return runner.update(sql, id);
    }

    public int insert(VotePlayer player) throws SQLException {
        String sql = "insert into t_vote_player(themeId, name, phone, email, title, detail, photo, photo2, sex, age, address)" +
                "values(?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {player.getThemeId(), player.getName(), player.getPhone(), player.getEmail(), player.getTitle(),
                player.getDetail(), player.getPhoto(), player.getPhoto2(), player.getSex(), player.getAge(), player.getAddress()};
        return runner.update(sql, params);
    }

    public VotePlayer queryById(int playerId) throws SQLException {
        String sql = "select * from t_vote_player where id = ?";
        return runner.query(sql, new BeanHandler<>(VotePlayer.class), playerId);
    }
}
