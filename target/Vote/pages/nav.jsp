<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!-- Start Navigation -->
<nav class="navbar navbar-default">
    <div class="container">
        <!-- Start Header Navigation -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-menu">
                <span class="glyphicon glyphicon-align-justify"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">
                <img style="width: 30px;height: 30px;" src="" id="head-img"
                     class="logo img-circle" alt="">

            </a>


        </div>
        <!-- End Header Navigation -->

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar-menu">
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/">主页</a></li>
                    <li><a href="${pageContext.request.contextPath}/pages/user/login.jsp">登录</a></li>
                    <li><a href="${pageContext.request.contextPath}/pages/user/register.jsp">注册</a></li>
                </c:if>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="javascript:void(0);">你好, ${sessionScope.user.nickname}!</a></li>
                    <li><a href="${pageContext.request.contextPath}/">主页</a></li>
                    <li><a href="${pageContext.request.contextPath}/pages/vote/start.jsp">发起投票</a></li>
                    <li><a href="#">消息</a></li>
                    <li><a href="${pageContext.request.contextPath}/pages/manager/profile.jsp">管理</a></li>
                    <li><a href="javascript:void(0)" id="logout-btn">登出</a></li>
                </c:if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div>
</nav>
<!-- End Navigation -->

<!-- 登出提示Modal -->
<div class="modal fade" id="logout-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-theme" id="myModalLabel">系统提示</h4>
            </div>
            <div class="modal-body">
                您已登出!
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="logout-modal-close-btn" data-keyboard="false">关闭
                </button>
            </div>
        </div>
    </div>
</div>


<script>
    $.ajax({
        url: '${pageContext.request.contextPath}/user?op=head',
        method: 'GET',
        success: function (request) {
            console.log(request);
            $('#head-img').attr('src', '${pageContext.request.contextPath}' + request);
        }
    });

    $("#logout-btn").click(function () {
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
    });

    $('#logout-modal-close-btn').click(function () {
        $('#logout-modal').modal('hide');
        window.location.reload();
    });
</script>