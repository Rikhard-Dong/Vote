<%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-1-23
  Time: 下午7:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
    }
%>
<html>
<head>
    <title>个人资料</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
    <script src="${pageContext.request.contextPath}/js/cropbox.js"></script>
    <script src="${pageContext.request.contextPath}/js/commons.js"></script>
    <style>
        .head-wrap {
            text-align: center;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: 40px;
            vertical-align: middle;
        }

        .head {
            width: 128px;
            height: 128px;
            border-radius: 50%;
        }

        .profile-username, .profile-email {
            padding-left: 32px;
        }

        .profile-username {
            padding-top: 12px;
            font-size: 32px;
            font-weight: 700;

        }

        .head-container {
            width: 400px;
            margin: 40px auto 0 auto;
            position: relative;
            font-family: 微软雅黑, serif;
            font-size: 12px;
        }

        .head-container p {
            line-height: 0;
            height: 0;
            margin: 10px;
            color: #bbb
        }

        .imageBox {
            position: relative;
            height: 400px;
            width: 400px;
            border: 1px solid #aaa;
            overflow: hidden;
            background: #fff no-repeat;
            cursor: move;
            box-shadow: 4px 4px 12px #B0B0B0;
        }

        .imageBox .thumbBox {
            position: absolute;
            top: 50%;
            left: 50%;
            width: 200px;
            height: 200px;
            margin-top: -100px;
            margin-left: -100px;
            box-sizing: border-box;
            border: 1px solid rgb(102, 102, 102);
            box-shadow: 0 0 0 1000px rgba(0, 0, 0, 0.5);
            background: none repeat scroll 0 0 transparent;
        }

        .imageBox .spinner {
            position: absolute;
            top: 0;
            left: 0;
            bottom: 0;
            right: 0;
            text-align: center;
            line-height: 400px;
            background: rgba(0, 0, 0, 0.7);
        }

        .Btnsty_peyton {
            float: right;
            width: 66px;
            display: inline-block;
            height: 57px;
            line-height: 57px;
            font-size: 20px;
            color: #FFFFFF;
            margin: 0 2px;
            background-color: #f38e81;
            border-radius: 3px;
            text-decoration: none;
            cursor: pointer;
            box-shadow: 0px 0px 5px #B0B0B0;
            border: 0px #fff solid;
        }

        /*选择文件上传*/
        .new-contentarea {
            width: 165px;
            overflow: hidden;
            margin: 0 auto;
            position: relative;
            float: left;
        }

        .new-contentarea label {
            width: 100%;
            height: 100%;
            display: block;
        }

        .new-contentarea input[type=file] {
            width: 188px;
            height: 60px;
            background: #333;
            margin: 0 auto;
            position: absolute;
            right: 50%;
            margin-right: -94px;
            top: 0;
            right /*\**/: 0px \9;
            margin-right /*\**/: 0px \9;
            width /*\**/: 10px \9;
            opacity: 0;
            filter: alpha(opacity=0);
            z-index: 2;
        }

        a.upload-img {
            width: 165px;
            display: inline-block;
            margin-bottom: 10px;
            height: 57px;
            line-height: 57px;
            font-size: 20px;
            color: #FFFFFF;
            background-color: #f38e81;
            border-radius: 3px;
            text-decoration: none;
            cursor: pointer;
            border: 0px #fff solid;
            box-shadow: 0px 0px 5px #B0B0B0;
        }

        a.upload-img:hover {
            background-color: #ec7e70;
        }

        .tc {
            text-align: center;
        }

        #desc {
            resize: none;
        }
    </style>
</head>
<body>
<%@include file="../nav.jsp" %>
<div class="container">
    <div class="col-md-3 col-xs-12">
        <div class="list-group">
            <a href="${pageContext.request.contextPath}/pages/manager/profile.jsp"
               class="list-group-item active">个人资料</a>
            <a href="${pageContext.request.contextPath}/pages/manager/vote.jsp" class="list-group-item">
                投票管理
            </a>
            <a href="${pageContext.request.contextPath}/pages/manager/check.jsp" class="list-group-item">审核管理</a>
            <c:if test="${sessionScope.user.type == 0}">
                <a href="${pageContext.request.contextPath}/pages/manager/user.jsp" class="list-group-item">用户管理</a>
            </c:if>
        </div>
    </div>
    <div class="col-md-9 col-xs-12">
        <div id="profile">
        </div>
    </div>
</div>

<%@include file="../messageModal.jsp" %>
<%-- 修改密码模态框 --%>
<div class="modal fade bs-example-modal-lg" tabindex="1" role="dialog" aria-labelledby="myLargeModalLabel"
     id="update-password-modal" data-keyborad="false">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="update-password-form">
                    <div class="form-group">
                        <label for="oldPassword" class="col-sm-2 control-label">旧密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="oldPassword" class="form-control" id="oldPassword"
                                   placeholder="旧密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">新密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" name="password" id="password" placeholder="新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password2" class="col-sm-2 control-label">确认密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" name="password2" id="password2"
                                   placeholder="确认密码">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="update-password-submit-btn">更新密码</button>
            </div>
        </div>
    </div>
</div>
<%-- 修改密码模态框结束 --%>
<%-- 更新头像模态框 --%>
<div class="modal fade bs-example-modal-lg" tabindex="-2" role="dialog" aria-labelledby="myLargeModalLabel"
     id="update-head-modal" data-keyborad="false">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改头像</h4>
            </div>
            <div class="modal-body" style="margin-bottom: 100px;">
                <div class="head-container">
                    <div class="imageBox">
                        <div class="thumbBox"></div>
                        <div class="spinner" style="display: none">Loading...</div>
                    </div>
                    <div class="action">
                        <div class="new-contentarea tc">
                            <a href="javascript:void(0)" class="upload-img">
                                <label for="upload-file">上传图像</label>
                            </a>
                            <input type="file" class="" name="upload-file" id="upload-file"/>
                        </div>
                        <%--<input type="button" id="btnCrop" class="Btnsty_peyton" value="裁切">--%>
                        <input type="button" id="btnZoomIn" class="Btnsty_peyton" value="+">
                        <input type="button" id="btnZoomOut" class="Btnsty_peyton" value="-">
                    </div>
                    <div class="cropped"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="update-head-submit-btn">上传头像</button>
            </div>
        </div>
    </div>
</div>
<%-- 更新头像模态框结束 --%>

</body>

<%-- 个人信息模板 --%>
<script type="text/html" id="profile-temp">
    <div class="row profile-head">
        <div class="col-md-4 col-xs-12 head-wrap">
            <img src="${pageContext.request.contextPath}{{user.headImage}}" alt="" class="head">
        </div>
        <div class="col-md-8 col-xs-12">
            <div class="row">
                <p class="profile-username">{{user.username}}</p>
                <p class="profile-email">{{user.email}}</p>
            </div>
            <div class="row">
                <div class="col-md-6 col-xs-6">
                    <a href="javascript:void(0)" id="upload-head-btn" class="btn btn-default">上传头像</a></div>
                <div class="col-md-6 col-xs-6">
                    <a href="javascript:void(0)" id="upload-password-btn" class="btn btn-primary">更新密码</a></div>
            </div>
        </div>
    </div>
    <div class="row profile-edit-wrap">
        <div class="col-md-12">
            <form class="form form-horizontal">
                <div class="form-group">
                    <label for="nickname" class="col-md-2">昵&nbsp;&nbsp;称&nbsp;&nbsp;</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" id="nickname" placeholder="昵称"
                               value="{{user.nickname}}"
                               disabled="disabled">
                    </div>
                    <div class="col-md-2">
                        <a href="javascript:void(0)" class="btn btn-default" id="upload-nickname-btn">修改</a>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-xs-12">
            <form class="form form-horizontal">
                <div class="form-group">
                    <label for="desc" class="col-md-2">个人简介</label>
                    <div class="col-md-8">
                        <textarea name="desc" id="desc" rows="5" class="form-control col-md-8" disabled="disabled"
                                  placeholder="这个人很懒, 什么也没有留下">{{user.desc}}</textarea>
                    </div>
                    <div class="col-md-2">
                        <a href="javascript:void(0)" class="btn btn-default" id="upload-desc-btn">修改</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</script>
<script>
    var img;

    $(function () {
        loadProfile();


        var options =
            {
                thumbBox: '.thumbBox',
                spinner: '.spinner',
                // imgSrc: 'img/5.jpg'
            };
        var cropper = $('.imageBox').cropbox(options);
        $('#upload-file').on('change', function () {
            var reader = new FileReader();
            reader.onload = function (e) {
                options.imgSrc = e.target.result;
                cropper = $('.imageBox').cropbox(options);
            };
            reader.readAsDataURL(this.files[0]);
            this.files = [];
        });
        // $('#btnCrop').on('click', function(){
        //     img = cropper.getDataURL();
        // });
        $('#btnZoomIn').on('click', function () {
            cropper.zoomIn();
        });
        $('#btnZoomOut').on('click', function () {
            cropper.zoomOut();
        });

        $('#update-head-submit-btn').on('click', function () {
            img = cropper.getDataURL();
            // console.log(img);
            /* 以BASE64形式上传 */
            $.ajax({
                url: '${pageContext.request.contextPath}/user?op=update',
                method: 'POST',
                data: {
                    'attr': 'headImage',
                    'img': img
                },
                success: function (request) {
                    request = eval("(" + request + ")");
                    console.log(request);
                    if (request.code === 1) {
                        loadProfile();
                    } else if (request.code === 0) {
                        $('#modal-body').empty().text(request.msg);
                        $('#message-modal').modal('show');
                    }
                    $('#update-head-modal').modal('hide');
                }
            });
        });
    });

    function loadProfile() {
        $.ajax({
            url: '${pageContext.request.contextPath}/user?op=profile',
            method: 'GET',
            success: function (request) {
                request = eval('(' + request + ')');
                console.log(request);
                let data = {
                    'user': request.data.user
                };
                $('title').empty().append(request.data.user.nickname + '的个人资料 -- 投票');
                console.log(data);
                let tempStr = template('profile-temp', data);
                $('#profile').empty().append(tempStr);
            }
        })
    }

    $(document).on('click', '#upload-head-btn', function () {
        console.log("upload head btn click ...");
        $('#update-head-modal').modal('show');

    });

    $(document).on('click', '#upload-password-btn', function () {
        console.log("upload password btn click ...");
        $('#update-password-modal').modal('show');
    });

    $('#update-password-submit-btn').click(function () {
        $.ajax({
            url: '${pageContext.request.contextPath}/user?op=update&attr=password',
            method: 'POST',
            data: $('#update-password-form').serialize(),
            success: function (request) {
                request = eval("(" + request + ")");
                console.log(request);
                if (request.code === 1) {
                    $('#modal-body').empty().text("密码已更新, 请重新登录");
                    $('#message-modal').modal('show');
                    $.ajax({
                        url: '${pageContext.request.contextPath}/user?op=logout',
                        method: 'GET',
                        success: function (request) {
                            request = eval("(" + request + ")");
                            console.log(request);
                            if (request.code === 1) {
                                $('#logout-modal').modal('show');
                            }
                        }
                    })
                } else if (request.code === 0) {
                    $('#modal-body').empty().text(request.msg);
                    $('#message-modal').modal('show');
                }
            }
        });
        $('#update-password-modal').modal('hide');
    });

    $(document).on('click', '#upload-nickname-btn', function () {
        console.log("upload nickname btn click ...");
        var content = $(this).text();
        if (content === '修改') {
            $(this).text('更新');
            $(this).removeClass("btn-default");
            $(this).addClass("btn-primary");
            $('#nickname').removeAttr("disabled");
        } else if (content === '更新') {
            $(this).removeClass("btn-primary");
            $(this).addClass("btn-default");
            $(this).text('修改');
            $('#nickname').attr("disabled", "disabled");
            // 提交到服务器
            $.ajax({
                url: '${pageContext.request.contextPath}/user?op=update',
                method: 'POST',
                data: {
                    'attr': 'nickname',
                    'value': $('#nickname').val()
                },
                success: function (request) {
                    request = eval("(" + request + ")");
                    console.log(request);
                    if (request.code === 1) {
                        loadProfile();
                    } else if (request.code === 0) {
                        $('#modal-body').empty().text(request.msg);
                        $('#message-modal').modal('show');
                    }
                }
            })
        }
    });

    $(document).on('click', '#upload-desc-btn', function () {
        console.log("upload desc btn click ...");
        var content = $(this).text();
        if (content === '修改') {
            $(this).text('更新');
            $(this).removeClass("btn-default");
            $(this).addClass("btn-primary");
            $('#desc').removeAttr("disabled");
        } else if (content === '更新') {
            $(this).removeClass("btn-primary");
            $(this).addClass("btn-default");
            $(this).text('修改');
            $('#desc').attr("disabled", "disabled");

            $.ajax({
                url: '${pageContext.request.contextPath}/user?op=update',
                method: 'POST',
                data: {
                    'attr': 'desc',
                    'value': $('#desc').val()
                },
                success: function (request) {
                    request = eval("(" + request + ")");
                    console.log(request);
                    if (request.code === 1) {
                        loadProfile();
                    } else if (request.code === 0) {
                        $('#modal-body').empty().text(request.msg);
                        $('#message-modal').modal('show');
                    }
                }
            });
        }
    });


</script>
</html>
