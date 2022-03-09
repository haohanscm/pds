<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规格名称管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/retail/goodsModelTotal/">规格名称列表</a></li>
		<shiro:hasPermission name="xiaodian:retail:goodsModelTotal:edit"><li><a href="${ctx}/xiaodian/retail/goodsModelTotal/form">规格名称添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsModelTotal" action="${ctx}/xiaodian/retail/goodsModelTotal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品id：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>规格名称：</label>
				<form:input path="modelName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>规格ID：</label>
				<form:input path="modelId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>子规格ID：</label>
				<form:input path="subModelId" htmlEscape="false" maxlength="5" class="input-medium"/>
			</li>
			<li><label>子规格名称：</label>
				<form:input path="subModelName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品id</th>
				<th>规格名称</th>
				<th>规格ID</th>
				<th>子规格ID</th>
				<th>子规格名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:retail:goodsModelTotal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsModelTotal">
			<tr>
				<td><a href="${ctx}/xiaodian/retail/goodsModelTotal/form?id=${goodsModelTotal.id}">
					${goodsModelTotal.goodsId}
				</a></td>
				<td>
					${goodsModelTotal.modelName}
				</td>
				<td>
					${goodsModelTotal.modelId}
				</td>
				<td>
					${goodsModelTotal.subModelId}
				</td>
				<td>
					${goodsModelTotal.subModelName}
				</td>
				<td>
					<fmt:formatDate value="${goodsModelTotal.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsModelTotal.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:retail:goodsModelTotal:edit"><td>
    				<a href="${ctx}/xiaodian/retail/goodsModelTotal/form?id=${goodsModelTotal.id}">修改</a>
					<a href="${ctx}/xiaodian/retail/goodsModelTotal/delete?id=${goodsModelTotal.id}" onclick="return confirmx('确认要删除该规格名称吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>