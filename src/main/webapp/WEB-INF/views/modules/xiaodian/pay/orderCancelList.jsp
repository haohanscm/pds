<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单撤销管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/pay/orderCancel/">订单撤销列表</a></li>
		<shiro:hasPermission name="xiaodian:pay:orderCancel:edit"><li><a href="${ctx}/xiaodian/pay/orderCancel/form">订单撤销添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderCancel" action="${ctx}/xiaodian/pay/orderCancel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>流水号：</label>
				<form:input path="requestId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>撤销时间：</label>
				<input name="beginReTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderCancel.beginReTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endReTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderCancel.endReTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>返回码：</label>
				<form:select path="respCode" class="input-medium">
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
				<th>流水号</th>
				<th>商家名称</th>
				<th>商户账号</th>
				<th>商户订单号</th>
				<th>撤销时间</th>
				<th>状态</th>
				<th>返回码</th>
				<th>返回信息描述</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:pay:orderCancel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderCancel">
			<tr>
				<td><a href="${ctx}/xiaodian/pay/orderCancel/form?id=${orderCancel.id}">
					${orderCancel.requestId}
				</a></td>
				<td>
					${orderCancel.merchantName}
				</td>
				<td>
					${orderCancel.partnerId}
				</td>
				<td>
					${orderCancel.orderId}
				</td>
				<td>
					<fmt:formatDate value="${orderCancel.reTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(orderCancel.status, 'pay_cancel_status', '')}
				</td>
				<td>
					${orderCancel.respCode}
				</td>
				<td>
					${orderCancel.respDesc}
				</td>
				<td>
					<fmt:formatDate value="${orderCancel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:pay:orderCancel:edit"><td>
    				<a href="${ctx}/xiaodian/pay/orderCancel/form?id=${orderCancel.id}">修改</a>
					<a href="${ctx}/xiaodian/pay/orderCancel/delete?id=${orderCancel.id}" onclick="return confirmx('确认要删除该订单撤销吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>