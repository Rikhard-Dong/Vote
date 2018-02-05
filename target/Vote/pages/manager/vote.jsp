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
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
    }
%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>投票管理</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
    <script src="${pageContext.request.contextPath}/js/commons.js"></script>
    <script src="${pageContext.request.contextPath}/js/highcharts.js"></script>
</head>
<body>
<%@include file="../nav.jsp" %>
<div class="container">
    <div class="col-md-3">
        <div class="list-group">
            <a href="${pageContext.request.contextPath}/pages/manager/profile.jsp" class="list-group-item">个人资料</a>

            <a href="${pageContext.request.contextPath}/pages/manager/vote.jsp" class="list-group-item active">
                投票管理
            </a>
            <a href="${pageContext.request.contextPath}/pages/manager/check.jsp" class="list-group-item">审核管理</a>
            <c:if test="${sessionScope.user.type == 0}">
                <a href="${pageContext.request.contextPath}/pages/manager/user.jsp" class="list-group-item">用户管理</a>
            </c:if>
        </div>
    </div>
    <div class="col-md-9" id="vote-table-body">

    </div>
</div>

<%@include file="../messageModal.jsp" %>

<!-- 详情modal Modal -->
<div class="modal fade" id="theme-detail-modal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog  modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">查看投票数据</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="pie-inner">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="go-theme-detail-btn">前往投票页查看</button>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/html" id="vote-themes-temp">
    <table class="table table-bordered table-hover" id="user-info-table" align="center">
        <thead>
        <tr>
            <th>ID</th>
            <c:if test="${sessionScope.user.type == 0}">
                <th>发起者</th>
            </c:if>
            <th width="42%">投票主题</th>
            <th width="22%">状态</th>
            <th width="16%">操作</th>
        </tr>
        </thead>
        <tbody>
        {{each helper.data value i}}
        <tr class="text-center">
            <td>{{value.themeId}}</td>
            <c:if test="${sessionScope.user.type == 0}">
                <td>{{value.username}}</td>
            </c:if>
            <td>{{value.theme}}</td>
            <td>{{value.countDown}}</td>
            <td>
                <a href="#" style="margin-top: 10px;" class="btn btn-default theme-detail"
                   data-theme-id="{{value.themeId}}">详情</a>
                <a href="#" style="margin-top: 10px;" class="btn btn-danger theme-delete"
                   data-theme-id="{{value.themeId}}">删除</a></td>
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
    var cur;
    var themeId;


    $(function () {
        toPage(1);
    });

    function toPage(page) {
        $.ajax({
            url: '${pageContext.request.contextPath}/vote',
            method: 'GET',
            data: {
                'op': 'listUserThemes',
                'page': page
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);

                if (result.code === 1) {
                    cur = result.data.pageHelper.curr;

                    var data = {
                        'helper': result.data.pageHelper
                    };
                    var temp = template('vote-themes-temp', data);
                    $('#vote-table-body').empty().append(temp);

                    /* 页面跳转 */
                    $('.to-page-link').each(function (index, elem) {
                        $(this).click(function () {
                            var page = $(this).attr("data-page");
                            // console.log(page);
                            toPage(page);
                        });
                    });
                } else if (result.code === 0) {
                    $('#modal-body').empty().append(result.msg);
                    $('#message-modal').modal('show');
                }
            }
        })
    }

    /**
     * 详情按钮
     */
    $(document).on('click', '.theme-detail', function () {
        themeId = $(this).attr("data-theme-id");

        $.ajax({
            url: '${pageContext.request.contextPath}/vote/detail',
            method: 'GET',
            data: {
                'op': 'pie',
                'themeId': themeId
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 0) {
                    var inner = $("<h2></h2>").append(result.msg);
                    $('#pie-inner').css('min-width', '');
                    $('#pie-inner').css('min-height', '');
                    $('#pie-inner').empty().append(inner);
                } else if (result.code === 1) {
                    $('#pie-inner').empty();
                    $('#pie-inner').css('min-width', '768px');
                    $('#pie-inner').css('min-height', '768px');

                    $('#pie-inner').highcharts({
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false
                        },
                        title: {
                            text: result.data.name
                        },
                        tooltip: {
                            headerFormat: '{series.name}<br>',
                            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: false
                                },
                                showInLegend: true
                            }
                        },
                        series: [{
                            type: 'pie',
                            name: '各投票占比',
                            data: result.data.data
                        }]
                    });
                }

                $('#theme-detail-modal').modal('show');

            }
        })
    });


    $('#go-theme-detail-btn').click(function () {
        window.location.href = '${pageContext.request.contextPath}/vote/detail?op=detail&themeId=' + themeId;
    });

    /**
     * 删除按钮
     */
    $(document).on('click', '.theme-delete', function () {
        var themeId = $(this).attr("data-theme-id");

        $.ajax({
            url: '${pageContext.request.contextPath}/vote',
            method: 'GET',
            data: {
                'op': 'delete',
                'themeId': themeId
            },
            success: function (result) {
                result = eval("(" + result + ")");
                if (result.code == 0) {
                    $('#modal-body').empty().append(result.msg);
                    $('#message-modal').modal('show');
                } else if (result.code == 1) {
                    toPage(cur);
                }
            }
        })
    });
</script>
</html>
