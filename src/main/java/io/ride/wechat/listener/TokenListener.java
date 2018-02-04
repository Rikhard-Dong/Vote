package io.ride.wechat.listener;

import io.ride.wechat.service.TokenService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class TokenListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("定时刷新access_token监听器启动");

        /*
         * 启动后一秒后执行, 然后每一小时刷新一次access_token
         */
        Timer timer = new Timer();
        timer.schedule(new TokenTimerTask(), 1000, 60 * 60 * 1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


    class TokenTimerTask extends TimerTask {

        TokenService tokenService;

        TokenTimerTask() {
            tokenService = new TokenService();
        }

        @Override
        public void run() {
            tokenService.flush();
        }
    }
}
