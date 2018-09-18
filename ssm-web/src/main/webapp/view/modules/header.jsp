<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
            <%--<a class="navbar-brand" href="${pageContext.request.contextPath}/product/list">首页</a>--%>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <%--<ul class="nav navbar-nav">
                <li><a href="#">栏目一</a></li>
                <li><a href="#">栏目二</a></li>
                <li><a href="#">栏目三</a></li>
            </ul>--%>
            <ul class="nav navbar-nav navbar-right">
                <shiro:authenticated>
                    <li><a href="${pageContext.request.contextPath}/logout">退 出</a></li>
                </shiro:authenticated>
                <shiro:notAuthenticated>
                    <li><a href="${pageContext.request.contextPath}/register">注 册</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">登 录</a></li>
                </shiro:notAuthenticated>
            </ul>
        </div>
    </div>
</nav>