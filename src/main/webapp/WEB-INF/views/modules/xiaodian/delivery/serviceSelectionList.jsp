<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务选项管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/delivery/serviceSelection/">服务选项列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:serviceSelection:edit"><li><a href="${ctx}/xiaodian/delivery/serviceSelection/form">服务选项添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="serviceSelection" action="${ctx}/xiaodian/delivery/serviceSelection/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品id：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>服务名称：</label>
				<form:input path="serviceName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>服务周期：</label>
				<form:select path="serviceSchedule" class="input-small ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>商品id</th>
				<th>服务名称</th>
				<th>服务内容</th>
				<th>服务价格</th>
				<th>服务次数</th>
				<th>服务周期</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:serviceSelection:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serviceSelection">
			<tr>
				<td><a href="${ctx}/xiaodian/delivery/serviceSelection/form?id=${serviceSelection.id}">
					${serviceSelection.goodsId}
				</a></td>
				<td>
					${serviceSelection.serviceName}
				</td>
				<td>
					${serviceSelection.serviceDetail}
				</td>
				<td>
					${serviceSelection.servicePrice}
				</td>
				<td>
					${serviceSelection.serviceNum}
				</td>
				<td>
					${serviceSelection.serviceSchedule}
				</td>
				<td>
					<fmt:formatDate value="${serviceSelection.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${serviceSelection.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:delivery:serviceSelection:edit"><td>
    				<a href="${ctx}/xiaodian/delivery/serviceSelection/form?id=${serviceSelection.id}">修改</a>
					<a href="${ctx}/xiaodian/delivery/serviceSelection/delete?id=${serviceSelection.id}" onclick="return confirmx('确认要删除该服务选项吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>