<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务评价明细管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/customer/customerEvaluateDetail/">服务评价明细列表</a></li>
		<shiro:hasPermission name="xiaodian:customer:customerEvaluateDetail:edit"><li><a href="${ctx}/xiaodian/customer/customerEvaluateDetail/form">服务评价明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerEvaluateDetail" action="${ctx}/xiaodian/customer/customerEvaluateDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>评分ID：</label>
				<form:input path="evaluateId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>评分类型：</label>
				<form:select path="evaluateType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluate_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>评分名称</th>
				<th>评分类型</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:customer:customerEvaluateDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerEvaluateDetail">
			<tr>
				<td><a href="${ctx}/xiaodian/customer/customerEvaluateDetail/form?id=${customerEvaluateDetail.id}">
					${customerEvaluateDetail.evaluateName}
				</a></td>
				<td>
					${fns:getDictLabel(customerEvaluateDetail.evaluateType, 'evaluate_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerEvaluateDetail.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customerEvaluateDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerEvaluateDetail.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:customer:customerEvaluateDetail:edit"><td>
    				<a href="${ctx}/xiaodian/customer/customerEvaluateDetail/form?id=${customerEvaluateDetail.id}">修改</a>
					<a href="${ctx}/xiaodian/customer/customerEvaluateDetail/delete?id=${customerEvaluateDetail.id}" onclick="return confirmx('确认要删除该服务评价明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>