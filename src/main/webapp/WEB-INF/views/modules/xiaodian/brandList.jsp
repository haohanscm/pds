<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/brand/">品牌列表</a></li>
		<shiro:hasPermission name="xiaodian:brand:edit"><li><a href="${ctx}/xiaodian/brand/form">品牌添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="brand" action="${ctx}/xiaodian/brand/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>行业：</label>
				<form:input path="industry" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>品牌名称：</label>
				<form:input path="brand" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>行业</th>
				<th>品牌名称</th>
				<th>品牌网址</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:brand:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="brand">
			<tr>
				<td><a href="${ctx}/xiaodian/brand/form?id=${brand.id}">
					${brand.industry}
				</a></td>
				<td>
					${brand.brand}
				</td>
				<td>
					${brand.website}
				</td>
				<td>
					${fns:getDictLabel(brand.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${brand.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${brand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${brand.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:brand:edit"><td>
    				<a href="${ctx}/xiaodian/brand/form?id=${brand.id}">修改</a>
					<a href="${ctx}/xiaodian/brand/delete?id=${brand.id}" onclick="return confirmx('确认要删除该品牌吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>