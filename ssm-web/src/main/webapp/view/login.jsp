<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<% request.setAttribute("title","登录"); %>
<%@ include  file="./modules/web-header.jsp"%>
<%@ include  file="./modules/header.jsp"%>
<div class="container" style="margin-top: 3.3%;margin-bottom: auto;">
    <h1>统一登录平台</h1>
    <h4>Central Authentication Service</h4>
    <hr>
    <div class="row">
        <form id="login-form" class="form-horizontal col-md-6" action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="userName" class="col-sm-2 control-label">用户名:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="userName" name="userName" value="${model.userName}" placeholder="请输入用户名">
                    <span class="text-danger">${errors.userName}</span>
                </div>
            </div>
            <div class="form-group">
                <label for="passWord" class="col-sm-2 control-label">密码:</label>
                <div class="col-sm-10">
                    <input type="passWord" class="form-control" id="passWord" name="passWord" placeholder="请输入密码">
                    <span class="text-danger">${errors.passWord}</span>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember" value="${model.remember}"> 记住我?
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">登 录</button>
                    <span class="text-danger">${errorMsg}</span><br>
                </div>
            </div>
        </form>
    </div>
    <a href="${pageContext.request.contextPath}/register">去注册</a><br>
    <a href="${pageContext.request.contextPath}/forget">忘记密码</a>
</div>
<%@ include  file="./modules/javascript.jsp"%>
<script src="${pageContext.request.contextPath}/js/jquery.md5.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/js/form-validate.js"></script>
<%@ include  file="./modules/web-footer.jsp"%>