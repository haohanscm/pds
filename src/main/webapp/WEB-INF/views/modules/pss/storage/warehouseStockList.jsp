<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pss/storage/warehouseStock/">库存管理列表</a></li>
		<shiro:hasPermission name="pss:storage:warehouseStock:edit"><li><a href="${ctx}/pss/storage/warehouseStock/form">库存管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="warehouseStock" action="${ctx}/pss/storage/warehouseStock/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家：</label>
				<form:select path="merchantId" class="input-medium">
					<form:option value="" label="查询所有"/>
					<form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>仓库编号：</label>
				<form:input path="warehouseId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>仓库编号</th>
				<th>条形码</th>
				<th>商品名称</th>
				<th>单位</th>
				<th>规格</th>
				<th>数量</th>
				<th>单价</th>
				<th>金额</th>
				<th>创建者</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:storage:warehouseStock:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="warehouseStock">
			<tr>
				<td><a href="${ctx}/pss/storage/warehouseStock/form?id=${warehouseStock.id}">
					${warehouseStock.warehouseId}
				</a></td>
				<td>
					${warehouseStock.barCode}
				</td>
				<td>
					${warehouseStock.goodsName}
				</td>
				<td>
					${warehouseStock.unit}
				</td>
				<td>
					${warehouseStock.attr}
				</td>
				<td>
					${warehouseStock.stockNum}
				</td>
				<td>
					${warehouseStock.price}
				</td>
				<td>
					${warehouseStock.amount}
				</td>
				<td>
					${warehouseStock.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${warehouseStock.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${warehouseStock.remarks}
				</td>
				<shiro:hasPermission name="pss:storage:warehouseStock:edit"><td>
    				<a href="${ctx}/pss/storage/warehouseStock/form?id=${warehouseStock.id}">修改</a>
					<a href="${ctx}/pss/storage/warehouseStock/delete?id=${warehouseStock.id}" onclick="return confirmx('确认要删除该库存管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>