package io.ride.wechat.service;

import io.ride.wechat.PO.wechat.response.RespMessageNews;

import java.sql.SQLException;

public interface ThemeService {
    RespMessageNews getTheme(int themeId, String openId) throws SQLException;
}
