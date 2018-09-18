<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("title","商品"); %>
<%@ include  file="./modules/web-header.jsp"%>
<%@ include  file="./modules/header.jsp"%>
<div class="container" style="margin-top: 3.3%;margin-bottom: auto;">
    <div class="row">
        <h1>列表</h1>
        <h4></h4>
    </div>
    <hr>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <a class="btn btn-default pull-right" href="${pageContext.request.contextPath}/product/create">创建</a>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>类别</th>
                        <th>状态</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${model.data}" var="list">
                    <tr>
                        <td style="width:20%">${list.productNumber}</td>
                        <td style="width:30%">${list.productName}</td>
                        <td style="width:15%">${list.productClass}</td>
                        <td style="width:15%">
                            <c:if test="${list.productStatus == true}">启用</c:if>
                            <c:if test="${list.productStatus == false}">不启用</c:if>
                        </td>
                        <th style="width:20%">
                            <div class="btn-group">
                                <a class="btn btn-default" href="${pageContext.request.contextPath}/product/details/${list.productId}">详情</a>
                                <a class="btn btn-default" href="${pageContext.request.contextPath}/product/update/${list.productId}">编辑</a>
                                <a class="btn btn-default" href="${pageContext.request.contextPath}/product/delete/${list.productId}">删除</a>
                            </div>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row pull-right">
        <%@ include  file="./modules/page-toolkit.jsp"%>
    </div>
</div>
<%@ include  file="./modules/javascript.jsp"%>
<%@ include  file="./modules/web-footer.jsp"%>