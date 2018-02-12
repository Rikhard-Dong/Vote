package io.ride.service.impl;

import io.ride.DTO.ResultDTO;
import io.ride.DTO.SearchUserDto;
import io.ride.DTO.SearchVoteDTO;
import io.ride.PO.User;
import io.ride.PO.VoteTheme;
import io.ride.dao.UserDao;
import io.ride.dao.VoteThemeDao;
import io.ride.service.SearchService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl implements SearchService {

    private UserDao userDao = new UserDao();
    private VoteThemeDao themeDao = new VoteThemeDao();

    @Override
    public ResultDTO searchUser(String content) throws SQLException {
        String regex = "[" + content + "]{" + content.length() + "}";
        List<User> users = userDao.searchUser(regex);
        if (users == null || users.size() == 0) {
            return ResultDTO.FAIL("没有符合的用户(´ﾟдﾟ`)");
        }
        List<SearchUserDto> var1 = new ArrayList<>();

        for (User user : users) {
            SearchUserDto var2 = new SearchUserDto(user);
            var1.add(var2);
        }
        return ResultDTO.SUCCESS("查询成功").addData("users", var1);
    }

    @Override
    public ResultDTO searchVote(String content) throws SQLException {
        String regex = "[" + content + "]{" + content.length() + "}";
        List<VoteTheme> themes = themeDao.searchTheme(regex);
        if (themes == null || themes.size() == 0) {
            return ResultDTO.FAIL("没有符合的投票(´ﾟдﾟ`)");
        }

        List<SearchVoteDTO> var1 = new ArrayList<>();
        for (VoteTheme theme : themes) {
            User user = userDao.queryById(theme.getUserId());
            SearchVoteDTO var2 = new SearchVoteDTO(user, theme);
            var1.add(var2);
        }

        return ResultDTO.SUCCESS("查询成功").addData("votes", var1);
    }
}
