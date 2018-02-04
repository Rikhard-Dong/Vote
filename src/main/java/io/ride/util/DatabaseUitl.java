package io.ride.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.ride.test.Test;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUitl {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static Connection conn;

//    static {
//        try {
//            String basePath = Test.class.getResource("/").getPath();
//            String jdbcFilePath = basePath + "jdbc.properties";
//            Properties properties = new Properties();
//            properties.load(new FileInputStream(jdbcFilePath));
//
//            String username = properties.getProperty("jdbc.username");
//            String password = properties.getProperty("jdbc.password");
//            String driver = properties.getProperty("jdbc.driver");
//            String url = properties.getProperty("jdbc.url");
//
//            cpds.setDriverClass(driver);
//            cpds.setJdbcUrl(url);
//            cpds.setUser(username);
//            cpds.setPassword(password);
//        } catch (PropertyVetoException | IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static ComboPooledDataSource getCpds() {
        return cpds;
    }

    public static Connection getConn() {
        conn = null;
        try {
            conn = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }

    public static void closeConn() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
                e.printStackTrace();
            }
        }
    }
}
