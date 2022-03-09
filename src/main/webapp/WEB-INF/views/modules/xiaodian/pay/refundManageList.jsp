<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退款管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/pay/refundManage/">退款列表</a></li>
		<shiro:hasPermission name="xiaodian:pay:refundManage:edit"><li><a href="${ctx}/xiaodian/pay/refundManage/form">退款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="refundManage" action="${ctx}/xiaodian/pay/refundManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li>
				<label>流水号：</label>
				<form:input path="requestId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户订单号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>订单号</th>
				<th>商户名称</th>
				<th>商户编号</th>
				<th>订单金额</th>
				<th>支付金额</th>
				<th>退款金额</th>
				<th>状态</th>
				<th>返回描述</th>
				<th>返回码</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:pay:refundManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="refundManage">
			<tr>
				<td><a href="${ctx}/xiaodian/pay/refundManage/form?id=${refundManage.id}">
						${refundManage.requestId}
				</a></td>
				<td>
					${refundManage.orderId}
				</td>
				<td>
					${refundManage.merchantName}
				</td>
				<td>
					${refundManage.partnerId}
				</td>
				<td>
					${refundManage.orderAmount}
				</td>
				<td>
					${refundManage.payAmount}
				</td>
				<td>
					${refundManage.refundAmount}
				</td>
				<td>
						${fns:getDictLabel(refundManage.status, 'refund_status', '')}
				</td>
				<td>
						${refundManage.respDesc}
				</td>
				<td>
					${refundManage.respCode}
				</td>
				<td>
					<fmt:formatDate value="${refundManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>

				<shiro:hasPermission name="xiaodian:pay:refundManage:edit"><td>
    				<a href="${ctx}/xiaodian/pay/refundManage/form?id=${refundManage.id}">修改</a>
					<a href="${ctx}/xiaodian/pay/refundManage/delete?id=${refundManage.id}" onclick="return confirmx('确认要删除该退款吗？', this.href)">删除</a>
					<c:if test="${refundManage.status eq '1'}">
					<a href="${ctx}/xiaodian/pay/refundManage/refund?id=${refundManage.id}">申请退款</a>
					</c:if>
					<c:if test="${refundManage.status eq '0'}">
					<a href="${ctx}/xiaodian/pay/refundManage/checkResult?id=${refundManage.id}">查询退款结果</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>