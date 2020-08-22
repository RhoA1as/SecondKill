<%--
  Created by IntelliJ IDEA.
  User: 27314
  Date: 2020/7/19
  Time: 3:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<html>
<head>
    <title>秒杀列表页</title>
    <%@include file="common/header.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>名称</th>
                        <th>库存</th>
                        <th>开启时间</th>
                        <th>结束时间</th>
                        <th>创建时间</th>
                        <th>详情页</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="secKill">
                            <tr>
                                <td>${secKill.name}</td>
                                <td>${secKill.number}</td>
                                <td>
                                    <fmt:formatDate value="${secKill.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${secKill.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${secKill.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <a  class="btn btn-info" href="/web/secKill/${secKill.secKill_id}/detail" target="_blank">link</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
