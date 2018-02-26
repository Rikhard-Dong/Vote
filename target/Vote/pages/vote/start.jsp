<%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-1-23
  Time: 下午6:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>发起投票</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/commons.js"></script>
    <script src="${pageContext.request.contextPath}/js/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
    <style>
        .wrap {
            border: 1px solid #5e5e5e;
            background: #eeeeee;
            padding: 10px 30px 20px;
        }
    </style>
</head>

<body>
<%@include file="../nav.jsp" %>
<br><br><br>
<div class="container">
    <div class="wrap">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 col-xs12">
                <h1>发起一个新的投票</h1>
            </div>
        </div>
        <br>
        <br>
        <br>
        <div class="row">
            <form class="form-horizontal col-md-8 col-md-offset-2 " id="start-vote-form">
                <div class="form-group">
                    <label for="theme" class="col-sm-2 control-label">主题</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="theme" id="theme" placeholder="主题">
                    </div>
                </div>
                <div class="form-group">
                    <label for="desc" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-10">
                    <textarea placeholder="描述" id="desc" name="desc" class="form-control" rows="5"
                              style="resize:none;"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3 col-sm-offset-2">
                        <label>
                            <input type="radio" name="isSingle" value="0" id="isSingle1" checked>&nbsp;&nbsp;单选
                        </label>
                    </div>
                    <div class="col-sm-2">
                        <label>
                            <input type="radio" name="isSingle" value="1" id="isSingle2">&nbsp;&nbsp;多选
                        </label>
                    </div>
                    <div class="col-sm-5">
                        <input type="text" id="maxSelect" name="maxSelect" placeholder="最大选择数量"
                               disabled="disabled">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3 col-sm-offset-2">
                        <label>
                            <input type="radio" name="isAnonymous" value="0" checked>允许匿名
                        </label>
                    </div>
                    <div class="col-sm-3">
                        <label>
                            <input type="radio" name="isAnonymous" value="1">禁止匿名
                        </label>
                    </div>
                    <div class="col-sm-3">
                        <label>
                            <input type="radio" name="isAnonymous" value="2">微信投票
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="timeDiff" class="col-sm-2 control-label">两次投票时间差(小时)</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="timeDiff" id="timeDiff" placeholder="多久之后能再次投票"
                               value="24">
                    </div>
                </div>
                <div class="form-group">
                    <label for="timeDiff" class="col-sm-2 control-label">IP限制</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="ipMax" id="ipMax"
                               placeholder="每个IP的投票数量限制,默认为-1不限制,如果为多选应乘选择的数量,建议设为预期值的两倍">
                    </div>
                </div>
                <%--<div class="form-group">--%>
                <%--<div class="col-sm-3 col-sm-offset-2">--%>
                <%--<label>--%>
                <%--<input type="radio" name="isRestrictedZone" value="0" checked>不限制区域--%>
                <%--</label>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="form-group">
                    <label for="theme" class="col-sm-2 control-label">起止时间</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="startTime" id="startTime"
                               placeholder="yyyy-mm-dd hh:MM:ss">
                    </div>
                    <label for="endTime">&nbsp;-&nbsp;</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="endTime" id="endTime"
                               placeholder="yyyy-mm-dd hh:MM:ss">
                    </div>
                </div>
                <div class="col-sm-offset-2 col-sm-10">
                    <a class="btn btn-success" id="voted-submit-btn">发起</a>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="../messageModal.jsp" %>
<div class="modal fade" id="success-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-theme">系统提示</h4>
            </div>
            <div class="modal-body" id="success-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="go-to-theme">查看详细
                </button>
            </div>
        </div>
    </div>
</div>
<script>

    laydate.render({
        elem: '#startTime',
        type: 'datetime',
        min: new Date().getTime()
    });

    laydate.render({
        elem: '#endTime',
        type: 'datetime',
        min: $('#startTime').val()
    });

    $(function () {
        $('#success-modal').modal(modal_params);
    });

    var success;
    $('#isSingle1').click(function () {
        console.log("is single 1 radio click....");
        $('#maxSelect').attr("disabled", "disabled");
    });

    $('#isSingle2').click(function () {
        console.log("is single 2 radio click....");
        $('#maxSelect').removeAttr('disabled');
    });

    $('#voted-submit-btn').click(function () {
        console.log("voted submit btn click.....");
        $.ajax({
            url: '${pageContext.request.contextPath}/vote?op=start',
            method: 'POST',
            data: $('#start-vote-form').serialize(),
            success: function (request) {
                request = eval("(" + request + ")");
                console.log(request);
                if (request.code === 1) {
                    success = true;
                    $('#success-body').empty().append("投票发起成功, 请前往详情页查看");
                    $('#success-modal').modal('show');
                    $('#go-to-theme').click(function () {
                        window.location.href = "${pageContext.request.contextPath}/vote/detail?op=detail&themeId=" + request.data.themeId;
                    });
                } else if (request.code === 0) {
                    $('#modal-body').empty().append(request.msg);
                    $('#message-modal').modal('show');
                }
            }
        });
    });

</script>
</body>
</html>
