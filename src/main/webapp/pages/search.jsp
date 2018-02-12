<%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-2-9
  Time: 下午10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>搜索结果</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>

    <style>
        a, a:hover {
            text-decoration: none;
            color: #000;
        }

        .user-card {
            margin-top: 1.2rem;
            margin-left: 1rem;
            padding: 1rem 2rem 3rem;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            border-color: #5e5e5e;
        }

        .user-head {
            width: 128px;
            height: 128px;
        }

        .user-nickname {
            font-size: 32px;
            font-weight: bold;
        }

        .vote-head {
            width: 160px;
            height: 160px;
            margin-bottom: 0.6rem;
        }

        .vote-wrapper {
            margin-top: 1.2rem;
            border-bottom: #eeeeee 2px solid;
        }

        .vote-title {
            font-size: 32px;
            font-weight: bold;
        }

        .vote-desc {
            font-size: 22px;
            color: #5e5e5e;
        }

        .vote-desc, .vote-status {
            margin-top: .8rem;
        }

    </style>
</head>
<body>
<%@include file="nav.jsp" %>
<div class="container">
    <%-- 搜索框 --%>
    <div class="row">
        <div class="col-md-8 col-xs-12">
            <form class="form-horizontal" id="search-form">
                <div class="form-group">
                    <label class="sr-only" for="search">搜索:</label>
                    <div class="input-group col-md-10">
                        <input type="text" class="form-control input-lg" id="search" name="search"
                               placeholder="输入关键字搜索用户和投票主题(๑´ㅂ`๑)">
                        <div class="input-group-addon"><a href="javascript:void(0)" id="search-btn">
                            <span class="glyphicon glyphicon-search"></span>
                        </a></div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="row" style="margin-top: 3rem">
        <div class="col-xs-12 search-content">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active">
                    <a href="#search-user" aria-controls="search-user" role="tab" data-toggle="tab">用户</a>
                </li>
                <li role="presentation">
                    <a href="#search-vote" aria-controls="search-vote" role="tab" data-toggle="tab">投票</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content" style="margin-top: 2rem">
                <div role="tabpanel" class="tab-pane active" id="search-user">
                    <h1>服务器开小差了(／‵Д′)／~ ╧╧</h1>
                </div>
                <div role="tabpanel" class="tab-pane" id="search-vote">
                    <h1>服务器开小差了(／‵Д′)／~ ╧╧</h1>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/html" id="search-user-temp">
    <div class="row">
        {{each users user i}}
        <div class="col-md-6 col-xs-12 ">
            <div class="row user-card">
                <div class="col-md-4">
                    <img src="${pageContext.request.contextPath}{{user.headImage}}" alt="" class="img-circle user-head">
                </div>
                <div class="col-md-8">
                    <div class="row user-info">
                        <div class="col-xs-12 user-nickname">
                            <p>
                                <a href="${pageContext.request.contextPath}/user/detail?op=profile&userId={{user.id}}">
                                    {{user.nickname}}</a>
                            </p>
                        </div>
                        <div class="col-xs-12 user-desc">
                            <p>
                                {{if user.desc == null}}
                                这个人很懒, 什么也没有留下(●′ω`●)
                                {{else}}
                                {{user.desc}}
                                {{/if}}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        {{/each}}
    </div>
</script>

<script type="text/html" id="search-vote-temp">
    {{each votes vote i}}
    <div class="row vote-wrapper">
        <div class="col-md-3">
            <a href="${pageContext.request.contextPath}/user/detail?op=profile&userId={{vote.userId}}"
               title="点击查看{{vote.nickname}}的主页">
                <img src="${pageContext.request.contextPath}{{vote.headImage}}" alt="" class="img-circle vote-head">
            </a>
        </div>

        <div class="col-md-9">
            <div class="row">
                <div class="col-xs-12 vote-title">
                    <p><a href="${pageContext.request.contextPath}/vote/detail?op=detail&themeId={{vote.themeId}}">
                        <h1>{{vote.title}}</h1>
                    </a></p>
                </div>
                <div class="col-xs-12 vote-desc">
                    <p>{{vote.desc}}</p>
                </div>
                <div class="col-xs-12 vote-status">
                    <p>{{vote.status}}</p>
                </div>
            </div>
        </div>
    </div>
    {{/each}}
</script>

<script>
    $(function () {
        $('#search').val('${requestScope.content}');
        loadSearchResult('${requestScope.content}');
    });

    $('#search-btn').click(function () {
        // console.log("search-btn click..." + $("#search").val());
        loadSearchResult($('#search').val());
    });


    function loadSearchResult(content) {
        console.log("search content " + content);

        /* 用户搜索结果展示 */
        $.ajax({
            url: '${pageContext.request.contextPath}/search',
            method: 'GET',
            data: {
                'op': 'search',
                'search': 'user',
                'content': content
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 0) {
                    const errMag = $('<h1></h1>').append(result.msg);
                    $('#search-user').empty().append(errMag);
                } else if (result.code === 1) {
                    const data = {
                        'users': result.data.users
                    };
                    const temp = template('search-user-temp', data);
                    $('#search-user').empty().append(temp);
                }
            }
        });

        /* 主题搜索结果展示 */
        $.ajax({
            url: '${pageContext.request.contextPath}/search',
            method: 'GET',
            data: {
                'op': 'search',
                'search': 'vote',
                'content': content
            },
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 0) {
                    const errMsg = $('<h1></h1>').append(result.msg);
                    $('#search-vote').empty().append(errMsg);
                } else if (result.code === 1) {
                    const data = {
                        'votes': result.data.votes
                    };
                    const temp = template('search-vote-temp', data);
                    $('#search-vote').empty().append(temp);
                }
            }
        })

    }
</script>

</html>
