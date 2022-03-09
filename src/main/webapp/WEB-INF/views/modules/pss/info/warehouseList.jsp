<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>仓库管理管理</title>
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
		<li class="active"><a href="${ctx}/pss/info/warehouse/">仓库管理列表</a></li>
		<shiro:hasPermission name="pss:info:warehouse:edit"><li><a href="${ctx}/pss/info/warehouse/form">仓库管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pssWarehouse" action="${ctx}/pss/info/warehouse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>仓库编号：</label>
				<form:input path="warehouseNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>仓库名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>仓库编号</th>
				<th>仓库名称</th>
				<th>管理员</th>
				<th>联系方式</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:info:warehouse:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="warehouse">
			<tr>
				<td><a href="${ctx}/pss/info/warehouse/form?id=${warehouse.id}">
					${warehouse.warehouseNum}
				</a></td>
				<td>
					${warehouse.name}
				</td>
				<td>
					${warehouse.manager}
				</td>
				<td>
					${warehouse.contact}
				</td>
				<td>
					${fns:getDictLabel(warehouse.status, 'common_is_enable', '')}
				</td>
				<td>
					<fmt:formatDate value="${warehouse.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${warehouse.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${warehouse.remarks}
				</td>
				<shiro:hasPermission name="pss:info:warehouse:edit"><td>
    				<a href="${ctx}/pss/info/warehouse/form?id=${warehouse.id}">修改</a>
					<a href="${ctx}/pss/info/warehouse/delete?id=${warehouse.id}" onclick="return confirmx('确认要删除该仓库管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>