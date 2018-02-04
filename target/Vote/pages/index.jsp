<%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-1-23
  Time: 上午11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>主页 -- 投票</title>

    <!-- Bootstrap -->
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .card {
            border: 1px solid #5e5e5e;
            border-radius: 7px;
            background: #eeeeee;
            margin: 30px 10px;
        }

        .card-head {
            border-radius: 7px 7px 0 0;
            background: #b2dfdb;
        }

        .card-theme {
            margin: 0 auto;
        }

        .card-img {
            margin-top: 20px;
        }

        .card-img img {
            width: 32px;
            height: 32px;
        }

        .card-info hr {
            color: #2e6da4;
        }

        .card-info p {
            font-size: 12px;
            font-weight: 700;
            color: #5e5e5e;
        }

        .card-content {
            padding-top: 20px;
        }
    </style>
</head>
<body>
<%@include file="nav.jsp" %>
<br><br>
<div class="container" id="content">

</div>

</body>
<%-- 投票内容 --%>
<script type="text/html" id="vote-theme-temp">
    <div class="row">
        {{each helper.data value i }}
        <div class="col-md-6 col-xs-12">
            <div class="card">
                <div class="card-head">
                    <div class="row card-theme">
                        <div class="col-md-3 col-xs-12 card-img">
                            <img src="${pageContext.request.contextPath}{{value.headImage}}" alt=""
                                 class="img-circle">
                        </div>
                        <div class="col-md-9 col-xs-12">
                            <h2>{{value.theme}}</h2>
                        </div>
                    </div>
                    <div class="row card-info">
                        <hr width="80%">
                        <div class="col-xs-2"></div>
                        <div class="col-xs-6 card-time">
                            <p><span class="glyphicon glyphicon-time"></span>
                                <span id="card-time">{{value.countDown}}</span></p>
                        </div>
                        <div class="col-xs-4 card-author">
                            <p>
                                <span class="glyphicon glyphicon-user"></span>
                                <span id="card-author">{{value.username}}</span></p>
                        </div>
                        <hr width="80%">
                    </div>
                </div>
                <div class="card-content">
                    <div class="row">
                        <div class="col-xs-2"></div>
                        <div class="col-xs-8">
                            <p>{{value.desc}}</p>
                        </div>
                    </div>
                </div>
                <div class="row card-button">
                    <div class="col-xs-8">
                        <a class="join-button btn btn-default vote-detail" data-theme-id="{{value.themeId}}">
                            {{if value.status == 2}}
                            查看结果
                            {{else}}
                            参与投票
                            {{/if}}
                        </a>
                    </div>
                </div>
            </div>
        </div>
        {{/each}}
    </div>

    <div class="row">
        <div class="col-xs-12">
            <%-- 分页条 --%>
            <nav aria-label="Page navigation">
                <ul class="pagination pagination-lg">
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
        </div>
    </div>
</script>
<script>

    $(function () {
        loadTheme(1);
    });

    function loadTheme(page) {
        $.ajax({
            url: '${pageContext.request.contextPath}/vote?op=list',
            method: 'GET',
            data: {
                'page': page
            },
            success: function (request) {
                request = eval("(" + request + ")");
                console.log(request);
                var data = {
                    'helper': request.data.pageHelper
                };
                var tempStr = template('vote-theme-temp', data);
                $('#content').empty().append(tempStr);

                /* 页面跳转 */
                $('.to-page-link').each(function (index, elem) {
                    $(this).click(function () {
                        var page = $(this).attr("data-page");
                        // console.log(page);
                        loadTheme(page);
                    });
                });
            }
        });
    }

    $(document).on('click', '.vote-detail', function () {
        var themeId = $(this).attr('data-theme-id');
        console.log('vote detail btn click .... theme id is ' + themeId);
        window.location.href = '${pageContext.request.contextPath}/vote/detail?op=detail&themeId=' + themeId;
    })
</script>
</html>
