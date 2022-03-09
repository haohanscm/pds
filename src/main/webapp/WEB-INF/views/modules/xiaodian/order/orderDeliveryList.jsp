<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单配送信息管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/order/orderDelivery/">订单配送信息列表</a></li>
		<shiro:hasPermission name="xiaodian:order:orderDelivery:edit"><li><a href="${ctx}/xiaodian/order/orderDelivery/form">订单配送信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderDelivery" action="${ctx}/xiaodian/order/orderDelivery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺ID：</label>
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>订单来源：</label>
				<form:select path="orderFrom" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_from')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单类型：</label>
				<form:select path="orderType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单状态：</label>
				<form:select path="orderStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>支付状态：</label>
				<form:select path="payStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送状态：</label>
				<form:select path="deliveryStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_delivery_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送员：</label>
				<form:input path="deliveryManName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户名称</th>
				<th>店铺</th>
				<th>订单类型</th>
				<th>订单状态</th>
				<th>订单号</th>
				<th>已付款金额</th>
				<th>支付状态</th>
				<th>配送方式</th>
				<th>配送状态</th>
				<th>计划生成</th>
				<th>配送员</th>
				<th>收货人</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:order:orderDelivery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderDelivery">
			<tr>
				<td><a href="${ctx}/xiaodian/order/orderDelivery/form?id=${orderDelivery.id}">
					${orderDelivery.merchantName}
				</a></td>
				<td>
					${orderDelivery.shopName}
				</td>
				<td>
					${fns:getDictLabel(orderDelivery.orderType, 'order_type', '')}
				</td>
				<td>
					${fns:getDictLabel(orderDelivery.orderStatus, 'order_status', '')}
				</td>
				<td>
					${orderDelivery.orderId}
				</td>
				<td>
					${orderDelivery.moneyPaid}
				</td>
				<td>
					${fns:getDictLabel(orderDelivery.payStatus, 'pay_status', '')}
				</td>
				<td>
						${fns:getDictLabel(orderDelivery.deliveryType, 'delivery_type', '')}
				</td>
				<td>
					${fns:getDictLabel(orderDelivery.deliveryStatus, 'order_delivery_status', '')}
				</td>
				<td>
						${fns:getDictLabel(orderDelivery.planGenStatus, 'plan_gen_status', '')}
				</td>
				<td>
					${orderDelivery.deliveryManName}
				</td>
				<td>
					${orderDelivery.receiver}
				</td>
				<td>
					<fmt:formatDate value="${orderDelivery.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:order:orderDelivery:edit"><td>
    				<a href="${ctx}/xiaodian/order/orderDelivery/form?id=${orderDelivery.id}">修改</a>
					<a href="${ctx}/xiaodian/order/orderDelivery/delete?id=${orderDelivery.id}" onclick="return confirmx('确认要删除该订单配送信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>