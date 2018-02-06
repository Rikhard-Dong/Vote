package io.ride.servlet;

import io.ride.DTO.ResultDTO;
import io.ride.PO.VoteItem;
import io.ride.PO.VotePlayer;
import io.ride.service.VoteItemService;
import io.ride.service.impl.VoteItemServiceImpl;
import io.ride.util.JacksonUtil;
import io.ride.util.UUIDUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/vote/item")
public class VoteItemServlet extends HttpServlet {

    private PrintWriter out;
    private VoteItemService itemService;

    private String photoPath;
    private String basePath;

    // 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    @Override
    public void init() {
        photoPath = File.separator + "upload" + File.separator + "photo";
        basePath = getServletContext().getRealPath("/");
        itemService = new VoteItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        out = response.getWriter();
        String op = request.getParameter("op");
        if (StringUtils.equals(op, "addItem")) {
            addItem(request);
        } else if (StringUtils.equals(op, "applyItem")) {
            applyItem(request);
        }

    }


    private void addItem(HttpServletRequest request) {


        int themeId = Integer.parseInt(request.getParameter("themeId"));
        String title;
        String detail;
        String photo = null;
        String photo2 = null;
        int count = 0;

        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("Error: 表单必须包含 enctype=multipart/form-data")));
            out.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        Map<String, String> params = new HashMap<>();

        try {
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String filename = photoPath + File.separator + UUIDUtil.getUUID() + ".jpg";
                        if (item.getSize() != 0) {
                            item.write(new File(basePath + filename));
                            count++;

                            if (count == 1) {
                                photo = filename;
                            }
                            if (count == 2) {
                                photo2 = filename;
                            }
                        }
                    } else {
                        // fileUpload 组件需要在这指定
                        System.out.println(item.getFieldName() + "====>" + item.getString("UTF-8"));
                        params.put(item.getFieldName(), item.getString("UTF-8"));
                    }
                }
            }

            title = params.get("title");
            detail = params.get("detail");
            VoteItem voteItem = new VoteItem(themeId, title, detail, photo, photo2);
            System.out.println("vote item is =====> " + voteItem);

            out.print(JacksonUtil.toJSon(itemService.addItem(voteItem)));
            out.flush();
        } catch (SQLException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("数据库操作失败").addData("trace", e.getStackTrace())
                    .addData("cause", e.getCause()).addData("sqlState", e.getSQLState()).addData("errorCode", e.getErrorCode())
            .addData("message", e.getMessage()).addData("localizedMessage", e.getLocalizedMessage())
            .addData("suppressed", e.getSuppressed())));
            out.flush();
            e.printStackTrace();
        } catch (FileUploadException e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("文件上传失败").addData("trace", e.getStackTrace()).addData("cause", e.getCause())));
            out.flush();
            e.printStackTrace();
        } catch (Exception e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("系统异常").addData("trace", e.getStackTrace()).addData("cause", e.getCause())));
            out.flush();
            e.printStackTrace();
        }
    }

    private void applyItem(HttpServletRequest request) {
        int themeId = Integer.parseInt(request.getParameter("themeId"));
        String name;
        String phone;
        String email;
        String sex;
        Integer age;
        String address;
        String title;
        String detail;
        String photo = null;
        String photo2 = null;
        int count = 0;

        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("Error: 表单必须包含 enctype=multipart/form-data")));
            out.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        Map<String, String> params = new HashMap<>();

        try {
            List<FileItem> formItems = upload.parseRequest(request);
            System.out.println("form item size =====> " + formItems.size());
            if (formItems.size() > 0) {
                for (FileItem item : formItems) {
                    System.out.println(item.isFormField());
                    if (!item.isFormField()) {
                        String filename = photoPath + File.separator + UUIDUtil.getUUID() + ".jpg";
                        if (item.getSize() != 0) {
                            item.write(new File(basePath + filename));
                            count++;
                            if (count == 2) {
                                photo2 = filename;
                            }
                            if (count == 1) {
                                photo = filename;
                            }
                        }
                    } else {
                        // fileUpload 组件需要在这指定
                        System.out.println(item.getFieldName() + "====>" + item.getString("UTF-8"));
                        params.put(item.getFieldName(), item.getString("UTF-8"));
                    }
                }
            }

            name = params.get("name");
            phone = params.get("phone");
            email = params.get("email");
            title = params.get("title");
            detail = params.get("detail");
            sex = params.get("sex");
            age = StringUtils.isEmpty(params.get("age")) ? null : Integer.valueOf(params.get("age"));
            address = params.get("address");
            VotePlayer player = new VotePlayer(themeId, name, phone, email, title, detail, photo);
            player.setPhoto2(photo2);
            player.setSex(sex);
            player.setAge(age);
            player.setAddress(address);

            ResultDTO result = itemService.applyItem(player);

            out.print(JacksonUtil.toJSon(result));
            out.flush();
        } catch (Exception e) {
            out.print(JacksonUtil.toJSon(ResultDTO.FAIL("文件上传失败")));
            out.flush();
            e.printStackTrace();
        }
    }
}
