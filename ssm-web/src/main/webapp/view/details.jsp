<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("title","产品详情"); %>
<%@ include  file="./modules/web-header.jsp"%>
<%@ include  file="./modules/header.jsp"%>
<div class="container" style="margin-top: 3.3%;margin-bottom: auto;">
    <div class="row">
        <h1>详情</h1>
        <hr>
    </div>
    <div class="row">
        <form class="form-horizontal col-md-6">
            <div class="form-group">
                <label for="productNumber" class="col-sm-2 control-label">编号</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="productNumber" name="productNumber" value="${model.productNumber}" disabled="disabled">
                </div>
            </div>
            <div class="form-group">
                <label for="productName" class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="productName" name="productName" value="${model.productName}" disabled="disabled">
                </div>
            </div>
            <div class="form-group">
                <label for="productClass" class="col-sm-2 control-label">类别</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="productClass" name="productClass" value="${model.productClass}" disabled="disabled">
                </div>
            </div>
            <div class="form-group">
                <label for="productStatus" class="col-sm-2 control-label">状态</label>
                <div class="col-sm-10">
                    <select class="form-control" id="productStatus" name="productStatus" value="${model.productStatus}" disabled="disabled">
                        <option value="1">启用</option>
                        <option value="0">不启用</option>
                    </select>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="btn-group">
            <a class="btn btn-default" href="${pageContext.request.contextPath}/product/update/${model.productId}">编辑</a>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/product/list">返回列表</a>
        </div>
    </div>
</div>
<%@ include  file="./modules/javascript.jsp"%>
<%@ include  file="./modules/web-footer.jsp"%>