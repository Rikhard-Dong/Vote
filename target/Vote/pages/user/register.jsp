<%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-1-23
  Time: 上午11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>注册 -- 投票系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>

<body>
<div class="box">
    <div class="inner-box register-box">
        <div class="login-theme text-center">
            <h1>
                <small>注册</small>
            </h1>
        </div>
        <div class="register-content">
            <div class="form">
                <form method="post" id="register-form">
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="text" id="username" name="username" class="form-control" placeholder="用户名">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input type="password" id="password" name="password" class="form-control"
                                       placeholder="密码">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12 ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input type="password" id="password2" name="password2" class="form-control"
                                       placeholder="确认密码">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12 ">
                            <div class="input-group">
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-envelope"></span></span>
                                <input type="email" id="email" name="email" class="form-control" placeholder="邮箱">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-6 col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-picture"></span></span>
                                <input type="text" id="code" name="code" class="form-control"
                                       placeholder="验证码, 点击图片刷新">
                            </div>
                        </div>
                        <div class="col-xs5 col-xs-offset-1">
                            <img src="${pageContext.request.contextPath}/verify" id="vc-img" alt="">
                        </div>
                    </div>
                </form>
                <div class="col-xs-4 col-xs-offset-4 ">
                    <a class="btn btn-sm btn-info" id="register-submit"><span
                            class="glyphicon glyphicon-off"></span> 注册</a>
                </div>
                <div class="col-xs-6 link col-xs-offset-6">
                    <p class="text-center remove-margin">
                        <small>已经注册?</small>
                        <a href="${pageContext.request.contextPath}/pages/user/login.jsp">
                            <small>登录</small>
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../messageModal.jsp" %>
</body>
<script>
    $('#register-submit').on('click', function () {
        console.log("register submit click....");
        $.ajax({
            url: '${pageContext.request.contextPath}/user?op=register',
            method: 'POST',
            data: $('#register-form').serialize(),
            success: function (request) {
                request = eval("(" + request + ")");
                console.log(request);
                if (request.code === 1) {
                    location.href = 'login.jsp'
                } else if (request.code === 0) {
                    $("#modal-body").empty().append(request.msg);
                    $('#message-modal').modal('show');
                }
            }
        })
    });

    $('#vc-img').click(function () {
        $(this).attr("src", '${pageContext.request.contextPath}/verify?time=' + new Date().getTime());
    });
</script>
</html>
