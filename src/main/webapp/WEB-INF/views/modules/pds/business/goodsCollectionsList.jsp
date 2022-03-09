<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品收藏管理</title>
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
		<li class="active"><a href="${ctx}/pds/business/goodsCollections/">商品收藏列表</a></li>
		<shiro:hasPermission name="pds:business:goodsCollections:edit"><li><a href="${ctx}/pds/business/goodsCollections/form">商品收藏添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsCollections" action="${ctx}/pds/business/goodsCollections/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家：</label>
				<form:input path="pmId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>通行证ID：</label>
				<form:input path="uid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品ID：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>规格ID：</label>
				<form:input path="modelId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>平台商家</th>
				<th>通行证ID</th>
				<th>商品ID</th>
				<th>规格ID</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:business:goodsCollections:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsCollections">
			<tr>
				<td><a href="${ctx}/pds/business/goodsCollections/form?id=${goodsCollections.id}">
					${goodsCollections.pmId}
				</a></td>
				<td>
					${goodsCollections.uid}
				</td>
				<td>
					${goodsCollections.goodsId}
				</td>
				<td>
					${goodsCollections.modelId}
				</td>
				<td>
					<fmt:formatDate value="${goodsCollections.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsCollections.remarks}
				</td>
				<shiro:hasPermission name="pds:business:goodsCollections:edit"><td>
    				<a href="${ctx}/pds/business/goodsCollections/form?id=${goodsCollections.id}">修改</a>
					<a href="${ctx}/pds/business/goodsCollections/delete?id=${goodsCollections.id}" onclick="return confirmx('确认要删除该商品收藏吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>