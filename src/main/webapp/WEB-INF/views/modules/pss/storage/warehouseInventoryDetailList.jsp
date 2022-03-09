<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>盘点记录管理</title>
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
		<li class="active"><a href="${ctx}/pss/storage/warehouseInventoryDetail/">盘点记录列表</a></li>
		<shiro:hasPermission name="pss:storage:warehouseInventoryDetail:edit"><li><a href="${ctx}/pss/storage/warehouseInventoryDetail/form">盘点记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="warehouseInventoryDetail" action="${ctx}/pss/storage/warehouseInventoryDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>库存ID：</label>
				<form:input path="warehouseStockId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>仓库编号：</label>
				<form:input path="warehouseId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品编号：</label>
				<form:input path="goodsCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>库存盘点ID</th>
				<th>仓库编号</th>
				<th>商品编号</th>
				<th>原有库存</th>
				<th>修改数量</th>
				<th>操作人员</th>
				<th>盘点时间</th>
				<th>供应商ID</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:storage:warehouseInventoryDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="warehouseInventoryDetail">
			<tr>
				<td><a href="${ctx}/pss/storage/warehouseInventoryDetail/form?id=${warehouseInventoryDetail.id}">
					${warehouseInventoryDetail.warehouseStockId}
				</a></td>
				<td>
					${warehouseInventoryDetail.warehouseId}
				</td>
				<td>
					${warehouseInventoryDetail.goodsCode}
				</td>
				<td>
					${warehouseInventoryDetail.origNum}
				</td>
				<td>
					${warehouseInventoryDetail.modifyNum}
				</td>
				<td>
					${warehouseInventoryDetail.operator}
				</td>
				<td>
					<fmt:formatDate value="${warehouseInventoryDetail.checkTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${warehouseInventoryDetail.supplierId}
				</td>
				<td>
					<fmt:formatDate value="${warehouseInventoryDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${warehouseInventoryDetail.remarks}
				</td>
				<shiro:hasPermission name="pss:storage:warehouseInventoryDetail:edit"><td>
    				<a href="${ctx}/pss/storage/warehouseInventoryDetail/form?id=${warehouseInventoryDetail.id}">修改</a>
					<a href="${ctx}/pss/storage/warehouseInventoryDetail/delete?id=${warehouseInventoryDetail.id}" onclick="return confirmx('确认要删除该盘点记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>