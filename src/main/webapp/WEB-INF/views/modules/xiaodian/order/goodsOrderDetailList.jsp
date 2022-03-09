<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品订单明细管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/order/goodsOrderDetail/">商品订单明细列表</a></li>
		<shiro:hasPermission name="xiaodian:order:goodsOrderDetail:edit"><li><a href="${ctx}/xiaodian/order/goodsOrderDetail/form">商品订单明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsOrderDetail" action="${ctx}/xiaodian/order/goodsOrderDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品编号：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>订单类型：</label>
				<form:select path="cartGoodsType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送周期：</label>
				<form:select path="deliverySchedule" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送时效：</label>
				<form:select path="arriveType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_arrive')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>计划类型：</label>
				<form:select path="deliveryPlanType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送方式：</label>
				<form:select path="deliveryType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>订单编号</th>
				<th>商品编号</th>
				<th>商品名称</th>
				<th>实际售价</th>
				<th>市场售价</th>
				<th>商品数量</th>
				<th>商品单位</th>
				<th>订单类型</th>
				<th>配送方式</th>
				<th>计划类型</th>
				<th>配送周期</th>
				<th>配送时效</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:order:goodsOrderDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsOrderDetail">
			<tr>
				<td><a href="${ctx}/xiaodian/order/goodsOrderDetail/form?id=${goodsOrderDetail.id}">
					${goodsOrderDetail.orderId}
				</a></td>
				<td>
					${goodsOrderDetail.goodsId}
				</td>
				<td>
					${goodsOrderDetail.goodsName}
				</td>
				<td>
					${goodsOrderDetail.goodsPrice}
				</td>
				<td>
					${goodsOrderDetail.marketPrice}
				</td>
				<td>
					${goodsOrderDetail.goodsNum}
				</td>
				<td>
					${goodsOrderDetail.goodsUnit}
				</td>
				<td>
					${fns:getDictLabel(goodsOrderDetail.cartGoodsType, 'goods_type', '')}
				</td>
				<td>
						${fns:getDictLabel(goodsOrderDetail.deliveryType, 'delivery_type', '')}
				</td>
				<td>
						${fns:getDictLabel(goodsOrderDetail.deliveryPlanType, 'delivery_plan_type', '')}
				</td>
				<td>
					${fns:getDictLabel(goodsOrderDetail.deliverySchedule, 'delivery_schedule', '')}
				</td>
				<td>
					${fns:getDictLabel(goodsOrderDetail.arriveType, 'delivery_arrive', '')}
				</td>
				<td>
					<fmt:formatDate value="${goodsOrderDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:order:goodsOrderDetail:edit"><td>
    				<a href="${ctx}/xiaodian/order/goodsOrderDetail/form?id=${goodsOrderDetail.id}">修改</a>
					<a href="${ctx}/xiaodian/order/goodsOrderDetail/delete?id=${goodsOrderDetail.id}" onclick="return confirmx('确认要删除该商品订单明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>