<%--
  Created by IntelliJ IDEA.
  User: 27314
  Date: 2020/7/19
  Time: 3:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/header.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading ">
                <h1>${detail.name}</h1>
            </div>
            <div class="panel-body">
                <h2 class="text-danger">
                    <span class="glyphicon glyphicon-time"></span>
                    <span class="glyphicon" id="secKill-box"></span>
                </h2>
            </div>
        </div>
    </div>
    <div id ="killPhoneModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"></span>秒杀电话
                    </h3>
                </div>
                <div class="modal-body">
                    <div class = "row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey" placeholder="输入手机号" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="killPhoneMessage" class="glyphicon"></span>
                    <button type="button" id = "killPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        submit
                    </button>
                </div>
            </div>
        </div>
    </div>
<!-- jQuery文件务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--jquery cookie 操作插件-->
<script src="https://cdn.bootcdn.net/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!--jquery countdown倒计时插件-->
<script src="https://cdn.bootcdn.net/ajax/libs/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
<script type="text/javascript" src="/web/resources/script/secKill.js"></script>
<script type="text/javascript">
    $(function () {
        secKill.detail.init({
            secKillId: ${detail.secKill_id},
            startTime: ${detail.start_time.time},
            endTime: ${detail.end_time.time},
        });
    });
</script>

</body>
</html>
