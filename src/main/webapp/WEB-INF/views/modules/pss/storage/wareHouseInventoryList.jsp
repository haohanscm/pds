<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存盘点管理</title>
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
		<li class="active"><a href="${ctx}/pss/storage/wareHouseInventory/">库存盘点列表</a></li>
		<shiro:hasPermission name="pss:storage:wareHouseInventory:edit"><li><a href="${ctx}/pss/storage/wareHouseInventory/form">库存盘点添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wareHouseInventory" action="${ctx}/pss/storage/wareHouseInventory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品编码：</label>
				<form:input path="goodsCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>仓库编号：</label>
				<form:input path="warehouseNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商家ID</th>
				<th>商品编码</th>
				<th>仓库编号</th>
				<th>商品名称</th>
				<th>库存数量</th>
				<th>入库时间</th>
				<th>上次盘点时间</th>
				<th>规格</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:storage:wareHouseInventory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wareHouseInventory">
			<tr>
				<td><a href="${ctx}/pss/storage/wareHouseInventory/form?id=${wareHouseInventory.id}">
					${wareHouseInventory.merchantId}
				</a></td>
				<td>
					${wareHouseInventory.goodsCode}
				</td>
				<td>
					${wareHouseInventory.warehouseNum}
				</td>
				<td>
					${wareHouseInventory.goodsName}
				</td>
				<td>
					${wareHouseInventory.stockNum}
				</td>
				<td>
					<fmt:formatDate value="${wareHouseInventory.instockTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${wareHouseInventory.lastInventoryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wareHouseInventory.attr}
				</td>
				<td>
					<fmt:formatDate value="${wareHouseInventory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wareHouseInventory.remarks}
				</td>
				<shiro:hasPermission name="pss:storage:wareHouseInventory:edit"><td>
					<a href="${ctx}/pss/storage/wareHouseInventory/form?id=${wareHouseInventory.id}">修改</a>
					<a href="${ctx}/pss/storage/wareHouseInventory/delete?id=${wareHouseInventory.id}" onclick="return confirmx('确认要删除该商品订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>