<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易信息管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/pay/orderQuery/">交易信息列表</a></li>
		<shiro:hasPermission name="xiaodian:pay:orderQuery:edit"><li><a href="${ctx}/xiaodian/pay/orderQuery/form">交易信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderQuery" action="${ctx}/xiaodian/pay/orderQuery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户编号：</label>
				<form:input path="partnerId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>流水号：</label>
				<form:input path="requestId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>订单号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>交易类型：</label>
				<form:select path="transType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('trans_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="transType" htmlEscape="false" maxlength="5" class="input-medium"/>--%>
			</li>
			<li><label>下单时间：</label>
				<input name="beginOrderTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderQuery.beginOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOrderTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderQuery.endOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>支付结果：</label>
				<form:select path="payResult" class="input-medium">
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
				<th>订单号</th>
				<th>流水号</th>
				<th>商户编号</th>
				<th>交易类型</th>
				<th>下单时间</th>
				<%--<th>支付时间</th>--%>
				<th>返回码</th>
				<th>返回描述</th>
				<th>交易结果</th>
				<shiro:hasPermission name="xiaodian:pay:orderQuery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderQuery">
			<tr>
				<td><a href="${ctx}/xiaodian/pay/orderQuery/form?id=${orderQuery.id}">
						${orderQuery.orderId}
				</a></td>
				<td>
					${orderQuery.requestId}
				</td>
				<td>
				    ${orderQuery.partnerId}
				</td>
				<td>
				   ${fns:getDictLabel(orderQuery.transType, 'trans_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${orderQuery.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--&lt;%&ndash;<fmt:formatDate value="${orderQuery.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&ndash;%&gt;--%>
				<%--</td>--%>
				<td>
					${orderQuery.respCode}
				</td>
				<td>
						${orderQuery.respMsg}
				</td>
				<td>
					${fns:getDictLabel(orderQuery.payResult, 'trans_status', '')}
				</td>
				<shiro:hasPermission name="xiaodian:pay:orderQuery:edit"><td>
    				<a href="${ctx}/xiaodian/pay/orderQuery/form?id=${orderQuery.id}">修改</a>
					<a href="${ctx}/xiaodian/pay/orderQuery/delete?id=${orderQuery.id}" onclick="return confirmx('确认要删除该交易信息吗？', this.href)">删除</a>
					<a href="${ctx}/xiaodian/pay/orderQuery/api?id=${orderQuery.id}">查询支付状态</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>