package io.ride.wechat.service.impl;

import io.ride.PO.User;
import io.ride.PO.VoteTheme;
import io.ride.dao.UserDao;
import io.ride.dao.VoteThemeDao;
import io.ride.wechat.PO.wechat.response.Article;
import io.ride.wechat.PO.wechat.response.RespMessageNews;
import io.ride.wechat.service.ThemeService;

import java.util.Date;
import java.sql.SQLException;

public class ThemeServiceImpl implements ThemeService {
    private VoteThemeDao themeDao = new VoteThemeDao();
    private UserDao userDao = new UserDao();
    private static final String DOMAIN = "http://ridddddde.free.ngrok.cc";

    @Override
    public RespMessageNews getTheme(int themeId, String openId) throws SQLException {
        VoteTheme theme = themeDao.queryByThemeId(themeId);

        if (theme == null) {
            return null;
        }

        User user = userDao.queryById(theme.getUserId());

        String title = theme.getTheme();
        String description = theme.getDesc();
        String picUrl = DOMAIN + user.getHeadImage();
        String url = DOMAIN + "/vote/detail?op=detail&themeId=" + themeId + "&openId=" + openId;
        System.out.println("wechat news url is =====> " + url);

        Article article = new Article(title, description, picUrl, url);

        RespMessageNews news = new RespMessageNews(1, new Article[]{article});
        news.setCreateTime(new Date().getTime());

        return news;
    }
}
