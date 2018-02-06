<%@ page import="io.ride.PO.User" %>
<%@ page import="io.ride.PO.VoteTheme" %><%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-1-26
  Time: 下午7:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${sessionScope.theme.theme} -- 投票</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
    <script src="${pageContext.request.contextPath}/js/commons.js"></script>
    <%
        if (session.getAttribute("theme") == null) {
            response.sendRedirect("../index.jsp");
        }
    %>

    <%
        if (session.getAttribute("user") != null) {
            System.out.println("detail.jsp user ====> " + ((User) session.getAttribute("user")).getUsername() + ", openId ====> " + session.getAttribute("openId"));
        } else {
            System.out.println("detail.jsp user ====> null , openId ====> " + session.getAttribute("openId"));

        }
    %>

    <style>
        body {
            line-height: 1.5;
            padding-bottom: 3rem;
        }

        textarea {
            resize: none;
        }

        .vote-info {
            margin-top: 2rem;
            padding: 1rem 2rem 3rem;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            border-color: #5e5e5e;
        }

        .theme-desc {
            font-size: 22px;
            padding-top: 2rem;
            padding-bottom: 4rem;
        }

        .theme-info {
            font-size: 14px;
            color: #5e5e5e;
        }

        .card-wrap {
            margin: 20px auto;
        }

        .card {
            width: 300px;
            max-width: 314px;
            margin-top: 2rem;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
        }

        .card-head {

            max-width: 100%;
            height: 300px;
            /*padding-bottom: 40%;*/
            /*background-color:#eeeeee;*/
            /*overflow: hidden;*/
            /*width: 300px;*/
            /*height: 300px;*/
            /*-webkit-box-sizing: border-box;*/
            /*-moz-box-sizing: border-box;*/
            /*box-sizing: border-box;*/
            /*overflow: hidden;*/
            /*text-align: center;*/
            /*float: left;*/
            box-shadow: 0 0 4px rgba(0, 0, 0, 0.5);
            border-radius: 10px 10px 0 0;
        }

        .card-img {
            box-shadow: 0 0 4px rgba(0, 0, 0, 0.5);
            border-radius: 10px 10px 0 0;
            width: 100%;
            height: 100%;
            /*max-width: 100%;*/
            /*max-height: 100%;*/
            /*display: inline;*/
            /*vertical-align: middle;*/
            object-fit: cover;
        }

        .card-content {
            padding: 12px;
        }

        .card-title {
            font-size: 24px;
            font-weight: 700;
            color: #5e5e5e;
            word-wrap: break-word;
        }

        .card-detail {
            font-size: 14px;
            color: #666;
            word-wrap: break-word;
        }

        .card-bottom {
            margin-left: 3rem;
            margin-right: 3rem;
            padding-bottom: 1.5rem;
        }

        .hide {
            display: none;
        }

        #vote-btn {
            font-size: 32px;
            padding: 0.4rem 1.6rem;
        }
    </style>

</head>
<body>
<%-- 导航条 --%>
<%@include file="../nav.jsp" %>
<div class="container">
    <%-- 内容开始 --%>
    <%-- 投票主题内容开始 --%>
    <div class="row vote-info">
        <div class="col-xs-12">
            <h1>${sessionScope.theme.theme}</h1>
        </div>
        <div class="col-xs-12 theme-desc">
            <p id="desc-content">${sessionScope.theme.desc}</p>
        </div>
        <div class="col-xs-12">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-6 theme-info">
                    <span class="glyphicon glyphicon-user" style="padding-right: 1.2rem;"></span>
                    ${sessionScope.theme.username}
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 theme-info">创建时间:&nbsp;${sessionScope.theme.createTime}</div>
                <div class="col-xs-12 col-sm-6 col-md-6 theme-info">开始时间:&nbsp;${sessionScope.theme.startTime}</div>
                <div class="col-xs-12 col-sm-6 col-md-6 theme-info">结束时间:&nbsp;${sessionScope.theme.endTime}</div>
                <div class="col-xs-12 col-sm-6 col-md-6 theme-info">投票状态:&nbsp;${sessionScope.theme.countDown}</div>
            </div>
        </div>
        <%-- 一些操作 --%>
        <div class="row">
            <div class="col-xs-12" style="padding-top: 0.8rem">
                <%-- 投票已经结束 --%>
                <%--<c:if test="${sessionScope.theme.status == 2}">--%>
                <%--<a href="javascript:void(0)" class="btn btn-default" id="show-result">查看结果</a>--%>
                <%--</c:if>--%>
                <%--  投票未开始 --%>
                <c:if test="${sessionScope.theme.status == 0}">
                    <c:if test="${not empty sessionScope.user}">
                        <c:if test="${sessionScope.user.id == sessionScope.theme.userId}">
                            <div class="row">
                                <div class="col-xs-6 col-sm-4 col-md-2">
                                    <a href="javascript:void(0)" class="btn btn-success" id="add-vote-item">添加选项</a>
                                </div>
                                <div class="col-xs-6 col-sm-4 col-md-2">
                                    <a href="javascript:void(0)" class="btn btn-success" id="update-desc">更新描述</a>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.user.id != sessionScope.theme.userId}">
                            <a href="javascript:void(0)" class="btn btn-default" id="apply-for-vote-item">提交选项</a>
                        </c:if>
                    </c:if>
                    <c:if test="${empty sessionScope.user}">
                        <a href="javascript:void(0)" class="btn btn-default" id="apply-for-vote-item">提交选项</a>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
    <%-- 投票主题内容结束 --%>
    <%-- 选项列表开始 --%>
    <div id="items">
        <%-- 等待填充 --%>
    </div>
    <%-- 选项列表结束 --%>
    <%-- 内容结束 --%>
</div>
<%-- 提示模态框 --%>
<%@include file="../messageModal.jsp" %>
<%-- 更新描述模态框 --%>
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="updateThemeDesc"
     id="update-theme-desc-modal">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">更新投票主题描述</h4>
            </div>
            <div class="modal-body" style="padding-right: 2rem;">
                <div class="row">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="desc" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="5" id="desc" style="resize: none"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="update-theme-desc-btn">提交</button>
            </div>
        </div>
    </div>
</div>
<%-- 更新描述模态框结束 --%>
<%-- 添加item模态框 --%>
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="addItemModal"
     id="add-item-modal">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加条目</h4>
            </div>
            <div class="modal-body">
                <%-- 添加表单开始 --%>
                <form class="form-horizontal" enctype="multipart/form-data;charset=utf-8" id="add-item-form"
                      method="post">
                    <div class="form-group">
                        <label for="add-title" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="add-title" placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-detail" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-10">
                            <textarea name="detail" id="add-detail" rows="5" class="form-control"
                                      placeholder="内容"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-photo" class="col-sm-2 control-label">选择图片1(必选)</label>
                        <div class="col-sm-10"><input type="file" id="add-photo" name="photo"></div>
                    </div>
                    <div class="form-group">
                        <label for="add-photo2" class="col-sm-2 control-label">选择图片2(可选)</label>
                        <div class="col-sm-10"><input type="file" id="add-photo2" name="photo2"></div>
                    </div>
                </form>
                <%-- 添加表单结束 --%>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="add-vote-item-btn">提交</button>
            </div>
        </div>
    </div>
</div>
<%-- 添加item模态框结束 --%>
<%-- 申请模态框 --%>
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="applyForItemModal"
     id="apply-for-item-modal">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">申请成为item</h4>
            </div>
            <div class="modal-body">
                <%-- 添加表单开始 --%>
                <form class="form-horizontal" enctype="multipart/form-data" id="apply-item-form"
                      method="post">
                    <div class="form-group">
                        <label for="apply-name" class="col-sm-2 control-label">姓名*</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="apply-name" name="name" placeholder="姓名(必填)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="apply-phone" class="col-sm-2 control-label">手机号*</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="apply-phone" name="phone" placeholder="手机号(必填)">
                        </div>
                    </div>
                    <div class="form-group"><label for="apply-email" class="col-sm-2 control-label">邮箱*</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="apply-email" name="email" placeholder="邮箱(必须)">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="apply-title" class="col-sm-2 control-label">提交标题*</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="apply-title" placeholder="标题(必填)">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="apply-detail" class="col-sm-2 control-label">提交内容*</label>
                        <div class="col-sm-10">
                            <textarea name="detail" id="apply-detail" rows="5" class="form-control"
                                      placeholder="内容(必填)"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-photo" class="col-sm-2 control-label">选择图片1(必选)</label>
                        <div class="col-sm-10"><input type="file" id="apply-photo" name="photo"></div>
                    </div>
                    <div class="form-group">
                        <label for="add-photo2" class="col-sm-2 control-label">选择图片2(可选)</label>
                        <div class="col-sm-10"><input type="file" id="apply-photo2" name="photo2"></div>
                    </div>

                    <div class="form-group"><label for="apply-sex" class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10"><input type="text" class="form-control" id="apply-sex" name="sex"
                                                      placeholder="性别(选填)"></div>
                    </div>
                    <div class="form-group"><label for="apply-age" class="col-sm-2 control-label">年龄</label>
                        <div class="col-sm-10"><input type="text" class="form-control" id="apply-age" name="age"
                                                      placeholder="年龄(选填)"></div>
                    </div>
                    <div class="form-group"><label for="apply-address" class="col-sm-2 control-label">住址</label>
                        <div class="col-sm-10"><input type="text" class="form-control" id="apply-address"
                                                      name="address" placeholder="住址(选填)">
                        </div>
                    </div>
                </form>
                <%-- 添加表单结束 --%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="apply-vote-item-btn">提交</button>
            </div>
        </div>
    </div>
</div>
<%-- 申请模态框结束 --%>

<div class="modal fade" id="need-login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-theme" id="myModalLabel">登录提醒</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="need-login-close-btn">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="vote-vc-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-theme">请输入验证码</h4>
            </div>
            <div class="modal-body">
                <form class="">
                    <div class="form-group">
                        <div class="col-xs-6 col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-picture"></span></span>
                                <input type="text" id="code" name="code" class="form-control"
                                       placeholder="验证码, 点击图片刷新">
                            </div>
                        </div>
                        <div class="col-xs5 col-xs-offset-1">
                            <img src="${pageContext.request.contextPath}/verify" id="vote-vc-img" alt="">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="input-vc-code-submit">投票
                </button>
            </div>
        </div>
    </div>
</div>

<%-- 保存当前页面的theme-id --%>
<div id="save-theme-id" style="display: none;">${sessionScope.theme.themeId}</div>
</body>
<script type="text/html" id="vote-item-list">
    {{if detail.isEmpty == 1}}
    <div class="info" style="margin-top: 3rem;"><h1>当前没有选项</h1></div>
    {{else}}
    <div class="row card-wrap">
        {{each detail.items item i}}
        <div class="col-md-4 col-sm-6 col-xs-12">
            <div class="card">
                <div class="card-head">
                    <img src="${pageContext.request.contextPath}{{item.photo}}" alt="" class="card-img">
                </div>
                <div class="card-content">
                    <h3 class="card-title">{{item.title}}</h3>
                    <p class="card-detail">{{item.detail}}</p>
                </div>
                <div class="card-bottom">
                    {{if detail.status == 0}}
                    <a href="javascript:void(0)" class="btn btn-default disabled">投票未开始</a>
                    {{else if detail.status == 1}}
                    <a href="javascript:void(0)" class="btn btn-default choose-btn" data-item-id="{{item.id}}">选择</a>
                    {{else}}
                    <div class="progress">
                        <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"
                             aria-valuenow="{{item.percent}}" aria-valuemin="0" aria-valuemax="100"
                             style="width: {{item.percent}}%; min-width: 3rem;">
                            {{item.percent}}%
                        </div>
                    </div>
                    <div class="is-voted">票数:{{item.sum}}/{{detail.sum}}
                        <span class="hide is-voted" style="color: darkred" data-item-id="{{item.id}}">(已选择)</span></div>
                    {{/if}}
                </div>
            </div>
        </div>
        {{/each}}
    </div>
    {{/if}}
    <div class="row">
        <div class="col-xs-offset-1 col-xs-11">
            {{if detail.isEmpty == 0}}
            {{if detail.status == 1}}
            <a href="javascript:void(0)" class="btn btn-primary" id="vote-btn">投票</a>
            {{else if detail.status == 2}}
            {{/if}}
            {{/if}}
        </div>
    </div>
    <div class="hide" id="detail-num">{{detail.num}}</div>
    <div class="hide" id="detail-status">{{detail.status}}</div>
    <div class="hide" id="detail-sum">{{detail.sum}}</div>
    <div class="hide" id="detail-time-diff">{{detail.timeDiff}}</div>
</script>

<script type="text/html" id="only-wechat">
    <div class="row">
        <div class="col-md-7 col-md-offset-2">
            <h3>扫描下面的二维码, 或者微信搜索并关注"XXX", 回复"{{themeId}}"参与本次投票</h3>
        </div>
        <div class="col-6 col-md-offset-3">
            <img src="${pageContext.request.contextPath}/img/wechat.jpg" alt="微信二维码">
        </div>
    </div>
</script>

<script>
    var themeId;
    var status;    // 当前投票状态
    var num;       // 可以选择的数量
    var sum;       // 总票数
    var timeDiff;   // 时间差
    var itemIdArray = [];

    $(function () {
        /* 禁止点击空白处和esc关闭模态框 */
        $('#update-theme-desc-modal').modal(modal_params);
        $('#add-item-modal').modal(modal_params);
        $('#apply-for-item-modal').modal(modal_params);
        $('#need-login-modal').modal(modal_params);

        themeId = $('#save-theme-id').text();
        console.log("theme id is " + themeId);

        if ('${sessionScope.openId}' === '' && '${sessionScope.theme.isAnonymous}' === '2' && '${sessionScope.user.id}' !== '${sessionScope.theme.userId}') {
            var data = {
                'themeId': themeId
            };
            var only_wechat = template('only-wechat', data);
            $('#items').empty().append(only_wechat);
        } else {
            loadItemList();
        }
    });

    function loadItemList() {
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/detail?op=items',
            method: 'GET',
            data: {
                'themeId': themeId
            },
            success: function (reslut) {
                reslut = eval("(" + reslut + ")");
                console.log(reslut);
                if (reslut.code === 0) {
                    if (reslut.msg === "此投票需要登录操作") {
                        $('#need-login-modal .modal-body').empty().append(reslut.msg);
                        $('#need-login-modal').modal('show');
                        return;
                    }
                }

                var data = {
                    'detail': reslut.data.itemDetail
                };
                var itemListTemp = template('vote-item-list', data);
                $('#items').empty().append(itemListTemp);

                status = $('#detail-status').text();
                num = $('#detail-num').text();
                sum = $('#detail-sum').text();
                timeDiff = $('#detail-time-diff').text();

                console.log("status = " + status + ", num = " + num + ", sum = " + sum);


                if (status == 2 || status == 3) {
                    var idStr = $.cookie(themeId);
                    var ids = idStr.split("-");
                    $('.is-voted').each(function (index, elem) {
                        var id = $(this).attr("data-item-id");
                        if (ids.indexOf(id) != -1) {
                            $(this).removeClass('hide');
                        }
                    });
                }
            }
        });
    }

    /* 需要登录跳转到登录界面 */
    $('#need-login-close-btn').click(function () {
        $('#need-login-modal').modal('hide');
        window.location.href = '${pageContext.request.contextPath}/pages/user/login.jsp';
    });

    /* 绑定选择按钮 */
    $(document).on('click', '.choose-btn', function () {
        var btnText = $(this).text();
        var itemId = $(this).attr("data-item-id");
        console.log(btnText + " " + itemId);
        if (btnText === '选择') {
            if (num !== itemIdArray.length) {
                $(this).removeClass('btn-default');
                $(this).addClass("btn-success");
                $(this).text('已选择');
                itemIdArray.push(itemId);
                console.log(num + '==' + itemIdArray.length);
                if (num == itemIdArray.length) {
                    $('.choose-btn').each(function (index, elem) {
                        if ($(this).text() === '选择') {
                            $(this).addClass("disabled");
                        }
                    })
                }
            }
        } else if (btnText === '已选择') {
            $(this).addClass('btn-default');
            $(this).removeClass("btn-success");
            $(this).text('选择');
            itemIdArray.splice($.inArray($(this).attr(itemId), itemIdArray), 1);
            $('.choose-btn').each(function (index, elem) {
                $(this).removeClass("disabled");
            })
        }
    });


    /* 添加item点击事件  */
    $('#add-vote-item').click(function () {
        $('#add-item-modal').modal('show');
    });

    $('#add-vote-item-btn').click(function () {
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/item?op=addItem&themeId=' + themeId,
            method: 'POST',
            cache: false,
            data: new FormData($('#add-item-form')[0]),
            processData: false,
            contentType: false,
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                // if (result.code === 1) {
                // 添加成功
                // } else if (result.code === 0) {
                // 添加失败
                $("#modal-body").empty().append(result.msg);
                $('#message-modal').modal('show');
                // }
                $('#add-item-modal').modal('hide');
                loadItemList();
            }
        });
    });

    /* 更新theme desc */
    $('#update-desc').click(function () {
        var content = $('#desc-content').text();
        $('#desc').empty().append(content);
        $('#update-theme-desc-modal').modal('show');
    });

    $('#update-theme-desc-btn').click(function () {
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/detail',
            method: 'POST',
            data: {
                'op': 'update',
                'attr': 'desc',
                'themeId': themeId,
                'desc': $('#desc').val()
            },
            success: function (request) {
                request = eval("(" + request + ")");
                console.log(request);
                if (request.code === 1) {
                    $('#desc-content').empty().append(request.data.desc);
                } else if (request.code === 0) {
                    $('#modal-body').empty().append(request.msg);
                    $('#message-modal').modal('show');
                }
                $('#update-theme-desc-modal').modal('hide');
            }
        })
    });

    /* 申请item点击 */
    $('#apply-for-vote-item').click(function () {
        console.log("apply for vote item btn click....");
        $('#apply-for-item-modal').modal('show');
    });

    /* 点击投票按钮 */
    $(document).on('click', '#vote-btn', function () {
        console.log(" vote  btn click....");
        if (itemIdArray.length === 0) {
            $('#modal-body').empty().append("请至少选择一项");
            $('#message-modal').modal('show');
        } else {
            $('#vote-vc-modal').modal('show');
        }
    });
    $('#input-vc-code-submit').click(function () {
        $.ajax({
            url: '${pageContext.request.contextPath}/verify',
            method: 'POST',
            data: {
                'code': $('#code').val()
            },
            success: function (result) {
                result = eval("(" + result + ")");
                if (result.code === 1) {
                    var itemIdStr = itemIdArray.join("-");
                    console.log(itemIdStr);
                    if ($.cookie(themeId) === undefined) {
                        console.log("当前可以投票");
                        $.ajax({
                            url: '${pageContext.request.contextPath}/vote',
                            method: 'POST',
                            data: {
                                'op': 'voted',
                                'itemIds': itemIdStr
                            },
                            success: function (result1) {
                                result1 = eval("(" + result1 + ")");
                                console.log(result1);
                                if (result1.code === 1) {
                                    var date = new Date();
                                    date.setTime(date.getTime() + (timeDiff * 60 * 60 * 1000));
                                    $.cookie(themeId, itemIdStr, {path: '/', expires: date});
                                    loadItemList();
                                    $('#modal-body').empty().append("投票成功");
                                    $('#message-modal').modal('show');
                                } else {
                                    $('#modal-body').empty().append(result1.msg);
                                    $('#message-modal').modal('show');
                                }
                            }
                        });
                    }
                } else if (result.code === 0) {
                    $('#modal-body').empty().append(result.msg);
                    $('#message-modal').modal('show');
                }
                $("#vote-vc-modal").modal('hide');
            }
        })
    });

    /* 提交自己添加的item */
    $('#apply-vote-item-btn').click(function () {
        console.log("apply vote item btn click .....");
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/item?op=applyItem&themeId=' + themeId,
            method: 'POST',
            cache: false,
            data: new FormData($('#apply-item-form')[0]),
            processData: false,
            contentType: false,
            success: function (result) {
                result = eval("(" + result + ")");
                $('#modal-body').empty().append(result.msg);
                $('#apply-for-item-modal').modal('hide');
                $('#message-modal').modal('show');
            }

        })
    });


    /* 点击刷新验证码 */
    $('#vote-vc-img').click(function () {
        $(this).attr('src', '${pageContext.request.contextPath}/verify?time=' + new Date().getTime());
    })
</script>
</html>
