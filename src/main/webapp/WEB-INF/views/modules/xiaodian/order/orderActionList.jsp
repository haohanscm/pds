<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单操作管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/order/orderAction/">订单操作列表</a></li>
		<shiro:hasPermission name="xiaodian:order:orderAction:edit"><li><a href="${ctx}/xiaodian/order/orderAction/form">订单操作添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderAction" action="${ctx}/xiaodian/order/orderAction/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>操作交易号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>订单状态：</label>
				<form:select path="orderStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>操作人员</th>
				<th>订单状态</th>
				<th>发货状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:order:orderAction:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderAction">
			<tr>
				<td><a href="${ctx}/xiaodian/order/orderAction/form?id=${orderAction.id}">
					${orderAction.actionUser}
				</a></td>
				<td>
					${fns:getDictLabel(orderAction.orderStatus, 'order_status', '')}
				</td>
				<td>
					${fns:getDictLabel(orderAction.shippingStatus, 'shipping_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${orderAction.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderAction.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:order:orderAction:edit"><td>
    				<a href="${ctx}/xiaodian/order/orderAction/form?id=${orderAction.id}">修改</a>
					<a href="${ctx}/xiaodian/order/orderAction/delete?id=${orderAction.id}" onclick="return confirmx('确认要删除该订单操作吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>