<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购物车管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/order/cart/">购物车列表</a></li>
		<shiro:hasPermission name="xiaodian:order:cart:edit"><li><a href="${ctx}/xiaodian/order/cart/form">购物车添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cart" action="${ctx}/xiaodian/order/cart/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="userId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺ID：</label>
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>店铺ID</th>
				<th>商品ID</th>
				<th>商品编号</th>
				<th>商品名称</th>
				<th>商品市场价</th>
				<th>商品销售价</th>
				<th>商品单位</th>
				<th>购买数量</th>
				<th>购物类型</th>
				<th>是否需要配送</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:order:cart:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cart">
			<tr>
				<td><a href="${ctx}/xiaodian/order/cart/form?id=${cart.id}">
					${cart.shopId}
				</a></td>
				<td>
					${cart.goodsId}
				</td>
				<td>
					${cart.goodsSn}
				</td>
				<td>
					${cart.goodsName}
				</td>
				<td>
					${cart.marketPrice}
				</td>
				<td>
					${cart.goodsPrice}
				</td>
				<td>
					${cart.goodsUnit}
				</td>
				<td>
					${cart.goodsNum}
				</td>
				<td>
					${fns:getDictLabel(cart.cartGoodsType, 'goods_type', '')}
				</td>
				<td>
					${fns:getDictLabel(cart.isShipping, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${cart.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cart.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:order:cart:edit"><td>
    				<a href="${ctx}/xiaodian/order/cart/form?id=${cart.id}">修改</a>
					<a href="${ctx}/xiaodian/order/cart/delete?id=${cart.id}" onclick="return confirmx('确认要删除该购物车吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>