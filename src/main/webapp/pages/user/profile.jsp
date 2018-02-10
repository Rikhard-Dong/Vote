<%@ page import="io.ride.DTO.SimpleUserDTO" %><%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-2-8
  Time: 下午3:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${sessionScope.profile.nickname}的个人信息 -- 投票</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>

    <%
        SimpleUserDTO simpleUser = (SimpleUserDTO) session.getAttribute("profile");
        if (simpleUser == null) {
            response.sendRedirect(request.getContextPath() + "/");
        }
    %>

    <style>
        body {
            /*background: #e7e7e7;*/
        }

        a, a:hover {
            text-decoration: none;
        }

        .user-info {
            margin-top: 3rem;
            padding: 1rem 2rem 3rem;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            border-color: #5e5e5e;
        }

        .user-head {
            height: 256px;
            width: 256px;
        }

        .user-desc, .register-time {
            margin-top: 2rem;
        }

        .user-nickname h1 {
            font-size: 56px;
            font-weight: bold;
        }

        .user-desc p {
            font-size: 28px;
            color: grey;
        }

        .voted-content ul li a {
            font-size: 28px;
        }

        .vote-info {
            border-left: #2e6da4 8px solid;
            border-top: #1b6d85 2px solid;
            border-bottom: #1b6d85 2px solid;
            margin-top: 1rem;
        }

        .vote-img {
            margin-left: 2rem;
            height: 128px;
            width: 128px;
        }

        .vote-desc, .vote-status {
            margin-top: 1rem;
        }

        .vote-title {
            font-size: 28px;
            font-weight: bold;
        }

        .vote-desc {
            font-size: 20px;
            color: #5e5e5e;
        }

        .vote-status {
            font-size: 12px;
        }
    </style>
</head>
<body>
<%@include file="../nav.jsp" %>
<div class="container">
    <div class="row user-info">
        <div class="col-md-3 col-xs-12">
            <img src="${pageContext.request.contextPath}${sessionScope.profile.headImage}" alt=""
                 class="img-circle user-head">
        </div>
        <div class="col-md-9 col-xs-12">
            <div class="row">
                <div class="col-xs-12 user-nickname">
                    <h1>${sessionScope.profile.nickname}</h1>
                </div>
                <div class="col-xs-12 user-desc">
                    <c:if test="${sessionScope.profile.desc == null}">
                        <p>这个人很懒, 什么都没有留下</p>
                    </c:if>
                    <c:if test="${sessionScope.profile.desc != null}">
                        <p>${sessionScope.profile.desc}</p>
                    </c:if>
                </div>
                <div class="col-xs-12 register-time">
                    <span>注册时间: ${sessionScope.profile.createTime}</span>
                </div>
            </div>
        </div>
    </div>

    <%-- 发起与参过的投票 --%>
    <div class="row" style="margin-top: 3rem">
        <div class="col-xs-12 voted-content">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active">
                    <a href="#voted1" aria-controls="voted1" role="tab" data-toggle="tab">参与过的投票</a>
                </li>
                <li role="presentation">
                    <a href="#voted2" aria-controls="voted2" role="tab" data-toggle="tab">发起过的投票</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content" style="margin-top: 2rem">
                <div role="tabpanel" class="tab-pane active" id="voted1">
                    <h1>服务器开小差了(／‵Д′)／~ ╧╧</h1>
                </div>
                <div role="tabpanel" class="tab-pane" id="voted2">
                    <h1>服务器开小差了(／‵Д′)／~ ╧╧</h1>
                </div>
            </div>
        </div>
    </div>

    <div style="margin-top: 3rem"></div>
</div>

</body>

<%-- 参与过的投票模板 --%>
<script id="voted1-temp" type="text/html">

</script>

<%-- 发起过的投票 --%>
<script type="text/html" id="voted-temp">
    {{each votes vote i}}
    <div class="vote-info">
        <div class="row">
            <div class="col-md-3" style="padding: 1rem">
                <a href="${pageContext.request.contextPath}/user/detail?op=profile&userId={{vote.userId}}"
                   title="点击查看用户{{vote.nickname}}的主页">
                    <img src="${pageContext.request.contextPath}{{vote.headImage}}"
                         alt="点击查看用户{{vote.nickname}}的主页"
                         class="img-circle vote-img">
                </a>
            </div>
            <div class="col-md-9" style="margin-top: 1rem">
                <div class="row">
                    <div class="col-xs-12 vote-title">
                        <p><a href="${pageContext.request.contextPath}/vote/detail?op=detail&themeId={{vote.themeId}}"
                              title="点击查看投票详情">{{vote.theme}}</a></p>
                    </div>
                    <div class="col-xs-12 vote-desc"><p>{{vote.desc}}</p></div>
                    <div class="col-xs-12 vote-status"><p>{{vote.countDown}}</p></div>
                </div>
            </div>
        </div>
    </div>
    {{/each}}
</script>

<script>
    $(document).ready(function () {
        /* 查询参与过的投票 */
        $.ajax({
            url: '${pageContext.request.contextPath}/user/detail',
            method: 'GET',
            data: {
                'op': 'vote1',
                'userId': '${sessionScope.profile.userId}'
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 1) {
                    var data = {
                        "votes": result.data.votes
                    };
                    var temp = template('voted-temp', data);
                    $("#voted1").empty().append(temp);
                } else if (result.code === 0) {
                    var errMsg = $('<h1></h1>').append(result.msg);
                    $("#voted1").empty().append(errMsg);
                }
            }
        });


        /* 查询发起的投票 */
        $.ajax({
            url: '${pageContext.request.contextPath}/user/detail',
            method: 'GET',
            data: {
                'op': 'vote2',
                'userId': '${sessionScope.profile.userId}'
            },
            success: function (result1) {
                result1 = eval("(" + result1 + ")");
                console.log(result1);
                if (result1.code === 1) {
                    var data = {
                        "votes": result1.data.votes
                    };
                    var temp = template('voted-temp', data);
                    $("#voted2").empty().append(temp);
                } else if (result1.code === 0) {
                    var errMsg = $('<h1></h1>').append(result1.msg);
                    $("#voted2").empty().append(errMsg);
                }
            }
        });
    });

    $(function () {

    });

</script>
</html>
