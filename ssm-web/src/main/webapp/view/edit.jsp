<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("title","修改产品"); %>
<%@ include  file="./modules/web-header.jsp"%>
<%@ include  file="./modules/header.jsp"%>
<div class="container" style="margin-top: 3.3%;margin-bottom: auto;">
    <div class="row">
        <h1>编辑</h1>
        <h4>产品信息</h4>
    </div>
    <hr>
    <div class="row">
        <form id="product-edit-form" class="form-horizontal col-md-6" action="${pageContext.request.contextPath}/product/update" method="post">
            <input type="hidden" name="productId" value="${model.productId}">
            <div class="form-group">
                <label for="productNumber" class="col-sm-2 control-label">编号</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="productNumber" name="productNumber" value="${model.productNumber}" placeholder="productNumber">
                    <span class="text-danger">${errors.productNumber}</span>
                </div>
            </div>
            <div class="form-group">
                <label for="productName" class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="productName" name="productName" value="${model.productName}" placeholder="productName">
                    <span class="text-danger">${errors.productName}</span>
                </div>
            </div>
            <div class="form-group">
                <label for="productClass" class="col-sm-2 control-label">类别</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="productClass" name="productClass" value="${model.productClass}" placeholder="productClass">
                    <span class="text-danger">${errors.productClass}</span>
                </div>
            </div>
            <div class="form-group">
                <label for="productStatus" class="col-sm-2 control-label">状态</label>
                <div class="col-sm-10">
                    <select class="form-control" id="productStatus" name="productStatus" value="${model.productStatus}">
                        <option value="1">启用</option>
                        <option value="0">不启用</option>
                    </select>
                    <span class="text-danger">${errors.productStatus}</span>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">修改</button>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/product/list">返回列表</a>
    </div>
</div>
<%@ include  file="./modules/javascript.jsp"%>
<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/js/form-validate.js"></script>
<%@ include  file="./modules/web-footer.jsp"%>