package io.ride.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@WebListener
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String basePath = servletContextEvent.getServletContext().getRealPath("/");
        // 创建上传文件夹
        String uploadPath = basePath + File.separator + "upload";
        File baseFolder = new File(uploadPath);
        if (!baseFolder.exists()) {
            if (!baseFolder.mkdir()) {
                System.out.println("InitListener =====> 创建" + uploadPath + "文件夹失败");
            }
        }
        // 创建存储头像的文件夹
        String headPath = uploadPath + File.separator + "head";
        File headFolder = new File(headPath);
        if (!headFolder.exists()) {
            if (!headFolder.mkdir()) {
                System.out.println("InitListener =====> 创建" + headPath + "文件夹失败");
            }
        }
        // 拷贝默认头像
        System.out.println("listener ====> " + headPath);
        String defaultHeadSourcePath = basePath + File.separator + "img" + File.separator + "default-head.jpg";
        String defaultHeadDestPath = headPath + File.separator + "default-head.jpg";
        copyFile(defaultHeadSourcePath, defaultHeadDestPath);
        // 创建存放投票item的照片
        String photoPath = uploadPath + File.separator + "photo";
        File photoFolder = new File(photoPath);
        if (!photoFolder.exists()) {
            if (!photoFolder.mkdir()) {
                System.out.println("InitListener =====> 创建" + photoPath + "文件夹失败");
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * 复制单个文件
     *
     * @param oldPath String  原文件路径  如：c:/fqf.txt
     * @param newPath String  复制后路径  如：f:/fqf.txt
     */
    private void copyFile(String oldPath, String newPath) {
        try {
            int byteRead;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);  //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
}
