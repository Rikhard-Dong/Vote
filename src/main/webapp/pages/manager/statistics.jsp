<%@ page import="io.ride.PO.VoteTheme" %>
<%@ page import="io.ride.PO.User" %>
<%@ page import="io.ride.DTO.SimpleVoteThemeDto" %><%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-2-12
  Time: 下午1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%
    SimpleVoteThemeDto theme = (SimpleVoteThemeDto) session.getAttribute("theme");
    if (theme == null) {
        response.sendRedirect("/");
        return;
    }
    User user = (User) session.getAttribute("user");
    if (user == null || (user.getId() != theme.getUserId() && user.getType() != 0)) {
        response.sendRedirect("/");
        return;
    }
%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>投票统计</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
    <script src="${pageContext.request.contextPath}/js/highcharts.js"></script>

</head>
<body>
<%@include file="../nav.jsp" %>
<div class="container">
    <c:if test="${(sessionScope.theme.startTime) }">
        <div class="row">
            <div class="col-xs-12"><h1>投票未开始, 暂无数据</h1></div>
        </div>
    </c:if>

    <c:if test="${sessionScope.theme.status != 0}">
        <div class="row" style="margin-top: 2rem">
            <div class="col-xs-12 col-md-6" id="rank-area">
                <h1>前端开小差了(／‵Д′)／~ ╧╧</h1>
            </div>
            <div class="col-xs-12 col-md-6" id="vote-source-area">
                <h1>前端开小差了(／‵Д′)／~ ╧╧</h1>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12" id="vote-detail-area">
                <h1>前端开小差了(／‵Д′)／~ ╧╧</h1>
            </div>
        </div>

    </c:if>
</div>


<%@include file="../messageModal.jsp" %>
</body>

<script type="text/html" id="rank-temp">
    {{each ranks rank i}}
    <div class="row">
        <div class=" col-md-3">{{rank.item}}</div>
        <div class=" col-md-6">
            <div class="progress">
                <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="{{rank.per}}"
                     aria-valuemin="0" aria-valuemax="100"
                     style="min-width: 2em; width: {{rank.per}}%">
                    {{rank.per}}%
                </div>
            </div>
        </div>
        <div class=" col-md-3">{{rank.desc}}</div>
    </div>
    {{/each}}
</script>

<script type="text/html" id="record-temp">
    <div class="row">
        <div class="col-md-12">
            <table class="table">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>选项名称</th>
                    <th>用户</th>
                    <th>微信openid</th>
                    <th>投票IP地址</th>
                    <th>投票时间</th>
                </tr>
                </thead>
                <tbody>
                {{each helper.data item i}}
                <tr>
                    <td>{{item.detailId}}</td>
                    <td>{{item.itemTitle}}</td>
                    <td>
                        {{if item.userId == null}}

                        {{else}}
                        <a href="${pageContext.request.contextPath}/user/detail?op=profile&userId={{item.userId}}">
                            {{item.nickname}}
                        </a>
                        {{/if}}
                    </td>
                    <td>{{item.openId}}</td>
                    <td>{{item.ipAddress}}</td>
                    <td>{{item.votedDate}}</td>
                </tr>
                {{/each}}
                </tbody>
            </table>
        </div>
    </div>


    <div class="row">
        <div class="col-xs-12">
            <%-- 分页条 --%>
            <nav aria-label="Page navigation">
                <ul class="pagination pagination-lg">
                    <li>
                        <a href="javascript:void(0)" aria-label="Previous" class="to-page-link"
                           data-page="{{helper.pre}}">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    {{each helper.pageList value i}}
                    {{if value == helper.curr}}
                    <li class="active"><a href="javascript:void(0)" class="to-page-link"
                                          data-page="{{value}}">{{value}}</a>
                    </li>
                    {{else}}
                    <li><a href="javascript:void(0)" class="to-page-link" data-page="{{value}}">{{value}}</a>
                    </li>
                    {{/if}}
                    {{/each}}
                    <li>
                        <a href="javascript:void(0)" aria-label="Next" class="to-page-link" data-page="{{helper.next}}">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</script>

<script>

    $(function () {
        /* 加载排名信息 */
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/statistics',
            method: 'GET',
            data: {
                'op': 'rank',
                'themeId': '${sessionScope.theme.themeId}'
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 0) {
                    $('#rank-area h1').empty().append(result.msg);
                } else if (result.code === 1) {
                    const data = {
                        'ranks': result.data.ranks
                    };
                    var temp = template('rank-temp', data);
                    $('#rank-area').empty().append(temp);
                }
            }
        });

        /* 加载投票来源饼图 */
        $.ajax({
            url: '${pageContext.request.contextPath}/vote/statistics',
            method: 'GET',
            data: {
                'themeId': '${sessionScope.theme.themeId}',
                'op': 'source'
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 0) {

                } else if (result.code === 1) {
                    const pic = $('<div id="container" style="width: 500px;height:500px"></div>');
                    pic.highcharts({
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false
                        },
                        title: {
                            text: result.data.title
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
                                    enabled: true,
                                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                    style: {
                                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                    }
                                }
                            }
                        },
                        series: [{
                            type: 'pie',
                            name: result.data.title,
                            data: result.data.data
                        }]
                    });

                    $("#vote-source-area").empty().append(pic);
                }
            }
        });

        loadPage(1);
    });

    function loadPage(page) {

        $.ajax({
            url: '${pageContext.request.contextPath}/vote/statistics',
            method: 'GET',
            data: {
                'op': 'record',
                'themeId': '${sessionScope.theme.themeId}',
                'page': page
            }, success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 1) {
                    const data = {
                        'helper': result.data.helper
                    };
                    const temp = template('record-temp', data);
                    $('#vote-detail-area').empty().append(temp);

                    /* 页面跳转 */
                    $('.to-page-link').each(function (index, elem) {
                        $(this).click(function () {
                            var page = $(this).attr("data-page");
                            // console.log(page);
                            loadPage(page);
                        });
                    });
                } else if (result.code === 0) {
                    const errMsg = $('<h1></h1>').append(result.msg);
                    $('#vote-detail-area').empty().append(errMsg);

                }
            }
        })

    }

</script>
</html>
