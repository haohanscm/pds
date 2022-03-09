<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>支付结果管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/pay/payNotify/">支付结果列表</a></li>
		<shiro:hasPermission name="xiaodian:pay:payNotify:edit"><li><a href="${ctx}/xiaodian/pay/payNotify/form">支付结果添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="payNotify" action="${ctx}/xiaodian/pay/payNotify/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>流水号：</label>
				<form:input path="requestId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>订单时间：</label>
				<input name="beginOrderTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${payNotify.beginOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOrderTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${payNotify.endOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户订单号</th>
				<th>订单时间</th>
				<th>订单金额</th>
				<th>支付时间</th>
				<th>实际支付</th>
				<th>支付结果</th>
				<th>是否同步即速</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:pay:payNotify:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="payNotify">
			<tr>
				<td><a href="${ctx}/xiaodian/pay/payNotify/form?id=${payNotify.id}">
					${payNotify.requestId}
				</a></td>
				<td>
					${payNotify.orderId}
				</td>
				<td>
					<fmt:formatDate value="${payNotify.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${payNotify.payAmount}
				</td>
				<td>
					<fmt:formatDate value="${payNotify.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${payNotify.fee}
				</td>
				<td>
					${fns:getDictLabel(payNotify.status, 'pay_status', '')}
				</td>
				<td>
					${fns:getDictLabel(payNotify.isNotifyJsapp, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${payNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:pay:payNotify:edit"><td>
    				<a href="${ctx}/xiaodian/pay/payNotify/form?id=${payNotify.id}">修改</a>
					<a href="${ctx}/xiaodian/pay/payNotify/delete?id=${payNotify.id}" onclick="return confirmx('确认要删除该支付结果吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>