<%@ page import="io.ride.PO.User" %><%--
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
    User user = (User) session.getAttribute("user");

    if (user == null || user.getType() != 0) {
        response.sendRedirect("../index.jsp");
    }
%>
<html>
<head>
    <title>用户管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
</head>
<body>
<%@include file="../nav.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-3">
            <div class="list-group">
                <a href="${pageContext.request.contextPath}/pages/manager/profile.jsp" class="list-group-item">个人资料</a>
                <a href="${pageContext.request.contextPath}/pages/manager/vote.jsp" class="list-group-item">
                    投票管理
                </a>
                <a href="${pageContext.request.contextPath}/pages/manager/check.jsp" class="list-group-item">审核管理</a>
                <c:if test="${sessionScope.user.type == 0}">
                    <a href="${pageContext.request.contextPath}/pages/manager/user.jsp"
                       class="list-group-item active">用户管理</a>
                </c:if>
            </div>
        </div>
        <div class="col-md-9" id="user-info-table-body">
        </div>
    </div>
</div>

<%-- 导入modal --%>
<%@include file="../messageModal.jsp" %>
</body>

<%-- 表格模板 --%>
<script id="user-info-temp" type="text/html">
    <table class="table table-bordered table-hover" id="user-info-table" align="center">
        <thead>
        <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>邮箱</th>
            <th>昵称</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        {{each helper.data value i}}
        <tr class="text-center">
            <td>{{value.userId}}</td>
            <td>{{value.username}}</td>
            <td>{{value.email}}</td>
            <td>{{value.nickname}}</td>
            <td><a href="#" class="btn btn-default user-detail" user-id="{{value.userId}}">详情</a>
                <a href="#" class="btn btn-danger user-delete" user-id="{{value.userId}}">删除</a></td>
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

<script>
    var curr;

    /* 页面跳转 */
    function toPage(page) {
        $.ajax({
            url: '${pageContext.request.contextPath}/user?op=listTheme',
            method: 'GET',
            data: {
                'page': page
            },
            success: function (result) {
                result = eval("(" + result + ")");
                var pageInfo = result.data.pageInfo;
                console.log(pageInfo);
                curr = pageInfo.curr;
                var data = {
                    helper: pageInfo
                };
                var temp = template('user-info-temp', data);
                $('#user-info-table-body').empty().append(temp);

                /* 页面跳转 */
                $('.to-page-link').each(function (index, elem) {
                    $(this).click(function () {
                        var page = $(this).attr("data-page");
                        // console.log(page);
                        toPage(page);
                    });
                });
            }
        });
    }

    $(function () {
        toPage(1);
    });

    $(document).on("click", ".user-delete", function () {
        var id = $(this).attr("user-id");
        console.log(id);
        $.ajax({
            url: '${pageContext.request.contextPath}/user?op=deleteTheme',
            method: 'GET',
            data: {
                'userId': id
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                $('#modal-body').empty().append(result.msg);
                $("#message-modal").modal('show');
                if (result.code === 1) {
                    toPage(curr);
                }
            }
        })
    })
</script>
</html>
