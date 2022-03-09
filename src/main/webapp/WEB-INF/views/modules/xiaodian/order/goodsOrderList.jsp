<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品订单管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/order/goodsOrder/">商品订单列表</a></li>
		<shiro:hasPermission name="xiaodian:order:goodsOrder:edit"><li><a href="${ctx}/xiaodian/order/goodsOrder/form">商品订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsOrder" action="${ctx}/xiaodian/order/goodsOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>渠道：</label>
				<form:input path="partnerAppName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>订单编号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>支付流水号：</label>
				<form:input path="payId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>支付状态：</label>
				<form:select path="payStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>商户名称</th>
				<th>渠道名称</th>
				<th>订单编号</th>
				<th>支付流水号</th>
				<th>店铺名称</th>
				<th>订单状态</th>
				<th>用户ID</th>
				<th>用户名称</th>
				<th>订单金额</th>
				<th>支付状态</th>
				<th>下单时间</th>
				<shiro:hasPermission name="xiaodian:order:goodsOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsOrder">
			<tr>
				<td>
					${goodsOrder.merchantName}
				</td>
				<td>
					${goodsOrder.partnerAppName}
				</td>

				<td><a href="${ctx}/xiaodian/order/goodsOrder/form?id=${goodsOrder.id}">
					${goodsOrder.orderId}
				</a></td>
				<td>
					${goodsOrder.payId}
				</td>
				<td>
					${goodsOrder.shopName}
				</td>
				<td>
					${fns:getDictLabel(goodsOrder.orderStatus, 'order_status', '')}
				</td>
				<td>
					${goodsOrder.uid}
				</td>
				<td>
					${goodsOrder.userName}
				</td>
				<th>
					${goodsOrder.orderAmount}
				</th>
				<td>
					${fns:getDictLabel(goodsOrder.payStatus, 'pay_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${goodsOrder.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:order:goodsOrder:edit"><td>
    				<a href="${ctx}/xiaodian/order/goodsOrder/form?id=${goodsOrder.id}">修改</a>
					<a href="${ctx}/xiaodian/order/goodsOrder/delete?id=${goodsOrder.id}" onclick="return confirmx('确认要删除该商品订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>