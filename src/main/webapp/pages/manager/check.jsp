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
    <title>审核管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
    <script src="${pageContext.request.contextPath}/js/commons.js"></script>
</head>
<body>
<%@include file="../nav.jsp" %>
<div class="container">
    <div class="col-md-3">
        <div class="list-group">
            <a href="${pageContext.request.contextPath}/pages/manager/profile.jsp" class="list-group-item">审核管理</a>
            <a href="${pageContext.request.contextPath}/pages/manager/vote.jsp" class="list-group-item">
                投票管理
            </a>
            <a href="${pageContext.request.contextPath}/pages/manager/check.jsp" class="list-group-item active">审核管理</a>
            <c:if test="${sessionScope.user.type == 0}">
                <a href="${pageContext.request.contextPath}/pages/manager/user.jsp" class="list-group-item">用户管理</a>
            </c:if>
        </div>
    </div>

    <div class="col-md-9" id="player-table-body">

    </div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
     id="player-detail-modal">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">申请详情</h4>
            </div>
            <div class="modal-body" id="player-detail-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>

        </div>
    </div>
</div>

<%@include file="../messageModal.jsp" %>
</body>

<script type="text/html" id="vote-player-temp">
    <table class="table table-bordered table-hover" id="user-info-table" align="center">
        <thead>
        <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>标题</th>
            <th>状态</th>
            <th>审核</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        {{each helper.data value i}}
        <tr class="text-center">
            <td>{{value.playerId}}</td>
            <td>{{value.name}}</td>
            <td>{{value.title}}</td>
            <td>{{value.statusDesc}}</td>
            <td>
                {{if value.status == 0}}
                <a href="#" style="margin-top: 10px;" class="btn btn-success player-allow"
                   data-player-id="{{value.playerId}}">通过</a>
                <a href="#" style="margin-top: 10px;" class="btn btn-danger player-deny"
                   data-player-id="{{value.playerId}}">拒绝</a>
                {{else}}
                <a href="#" style="margin-top: 10px;" class="btn btn-success disabled">通过</a>
                <a href="#" style="margin-top: 10px;" class="btn btn-danger disabled">拒绝</a>
                {{/if}}
            </td>
            <td>
                <a href="#" style="margin-top: 10px;" class="btn btn-default player-detail"
                   data-player-id="{{value.playerId}}">详情</a>
                {{if value.status != 1}}
                <a href="#" style="margin-top: 10px;" class="btn btn-danger player-delete"
                   data-player-id="{{value.playerId}}">删除</a>
                {{else}}
                <a href="#" style="margin-top: 10px;" class="btn btn-danger disabled">删除</a>
                {{/if}}
            </td>
        </tr>
        {{/each}}
        </tbody>
    </table>
    <nav aria-label="Page navigation">
        <ul class="pagination">
            <li>
                <a href="javascript:void(0)" aria-label="Previous" class="to-page-link" data-page="{{helper.pre}}">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            {{each helper.pageList value i}}
            {{if value == helper.curr}}
            <li class="active"><a href="javascript:void(0)" class="to-page-link" data-page="{{value}}">{{value}}</a>
            </li>
            {{else}}
            <li><a href="javascript:void(0)" class="to-page-link" data-page="{{value}}">{{value}}</a></li>
            {{/if}}
            {{/each}}
            <li>
                <a href="javascript:void(0)" aria-label="Next" class="to-page-link" data-page="{{helper.next}}">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</script>

<script type="text/html" id="player-detail-temp">
    <div class="row">
        <div class="col-md-4 col-sm-6 col-xs-12">
            <span class="glyphicon glyphicon-user"></span>
            {{player.name}}
            {{if player.sex}}
            <small>{{player.sex}}</small>
            {{/if}}
            {{if player.age}}
            <small>{{palyer.age}}岁</small>
            {{/if}}
        </div>
        <div class="col-md-4 col-sm-6 col-xs-12">
            <span class="glyphicon glyphicon-phone"></span>
            {{player.phone}}
        </div>
        <div class="col-md-4 col-sm-6 col-xs-12">
            <span class="glyphicon glyphicon-envelope"></span>
            {{player.email}}
        </div>
        {{if player.address}}
        <div class="col-md-4 col-sm-6 col-xs-12">
            <span class="glyphicon glyphicon-map-marker"></span>
            {{player.address}}
        </div>
        {{/if}}
    </div>
    <div class="row">
        <div class="col-md-2 col-xs-3">
            <strong>申请标题:</strong>
        </div>
        <div class="col-md-4 col-xs-9">
            {{player.title}}
        </div>
        <div class="col-md-2 col-xs-3">
            <strong>对应主题:</strong>
        </div>
        <div class="col-md-3 col-xs-6">
            {{player.theme}}
        </div>
        <div class="col-md-1 col-xs-3">
            <a href="${pageContext.request.contextPath}/vote/detail?op=detail&themeId={{player.themeId}}"
               class="btn btn-default">详情</a>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-2"><strong>内容:</strong></div>
        <div class="col-xs-10">{{player.detail}}</div>
    </div>

    <div class="row">
        <div class="col-md-2 col-xs-12">
            <strong>图片</strong>
        </div>
        <div class="col-md-5 col-xs-12">
            {{if player.photo}}
            <img width="100%" src="${pageContext.request.contextPath}{{player.photo}}" alt="图片1">
            {{/if}}
        </div>
        <div class="col-md-5 col-xs-12">
            {{if player.photo2}}
            <img src="${pageContext.request.contextPath}{{player.photo2}}" alt="图片2">
            {{/if}}
        </div>
    </div>

</script>

<script>
    var curr;
    $(function () {
        toPage(1);
    });

    function toPage(page) {
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/player',
            method: 'GET',
            data: {
                'op': 'listAll',
                'page': page
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);

                if (result.code == 0) {
                    var info = $('<h2></h2>').append(result.msg);
                    $('#player-table-body').empty().append(info);
                } else if (result.code == 1) {
                    curr = result.data.pageHelper.curr;

                    var data = {
                        'helper': result.data.pageHelper
                    };

                    var temp = template('vote-player-temp', data);
                    $('#player-table-body').empty().append(temp);

                    /* 页面跳转 */
                    $('.to-page-link').each(function (index, elem) {
                        $(this).click(function () {
                            var page = $(this).attr("data-page");
                            // console.log(page);
                            toPage(page);
                        });
                    });
                }
            }
        });
    }

    /* 通过 */
    $(document).on('click', '.player-allow', function () {
        var playerId = $(this).attr('data-player-id');
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/player',
            method: 'POST',
            data: {
                'op': 'allow',
                'playerId': playerId
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code == 0) {
                    $('#modal-body').empty().append(result.msg);
                    $('#message-modal').modal('show');
                } else if (result.code == 1) {
                    toPage(curr);
                }
            }
        });
    });

    /* 拒绝 */
    $(document).on('click', '.player-deny', function () {
        var playerId = $(this).attr('data-player-id');
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/player',
            method: 'POST',
            data: {
                'op': 'deny',
                'playerId': playerId
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code == 0) {
                    $('#modal-body').empty().append(result.msg);
                    $('#message-modal').modal('show');
                } else if (result.code == 1) {
                    toPage(curr);
                }
            }
        });
    });

    /* 详情 */
    $(document).on('click', '.player-detail', function () {
        var playerId = $(this).attr('data-player-id');
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/player',
            method: 'GET',
            data: {
                'op': 'detail',
                'playerId': playerId
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code == 1) {
                    var data = {
                        'player': result.data.player
                    };
                    var playerTemp = template('player-detail-temp', data);
                    $('#player-detail-body').empty().append(playerTemp);
                    $('#player-detail-modal').modal('show');
                } else if (code == 0) {
                    $('#modal-body').empty().append(result.msg);
                    $('#message-modal').modal('show');
                }

            }
        });
    });

    /* 删除 */
    $(document).on('click', '.player-delete', function () {
        var playerId = $(this).attr('data-player-id');
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/player',
            method: 'POST',
            data: {
                'op': 'delete',
                'playerId': playerId
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code == 0) {
                    $('#modal-body').empty().append(result.msg);
                    $('#message-modal').modal('show');
                } else if (result.code == 1) {
                    toPage(curr);
                }
            }
        });
    });

</script>
</html>
