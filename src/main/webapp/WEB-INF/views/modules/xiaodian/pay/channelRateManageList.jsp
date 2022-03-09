<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>渠道费率管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/pay/channelRateManage/">渠道费率列表</a></li>
		<shiro:hasPermission name="xiaodian:pay:channelRateManage:edit"><li><a href="${ctx}/xiaodian/pay/channelRateManage/form">渠道费率添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="channelRateManage" action="${ctx}/xiaodian/pay/channelRateManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户ID：</label>
				<form:input path="payInfoId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商家ID</th>
				<th>商户ID</th>
				<th>渠道</th>
				<th>费率</th>
				<th>渠道标识</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:pay:channelRateManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="channelRateManage">
			<tr>
				<td><a href="${ctx}/xiaodian/pay/channelRateManage/form?id=${channelRateManage.id}">
					${channelRateManage.merchantId}
				</a></td>
				<td>
					${channelRateManage.payInfoId}
				</td>
				<td>
					${fns:getDictLabel(channelRateManage.channel, 'pay_channel', '')}
				</td>
				<td>
					${channelRateManage.rate}
				</td>
				<td>
					${channelRateManage.custId}
				</td>
				<td>
					${fns:getDictLabel(channelRateManage.status, 'bank_reg_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${channelRateManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:pay:channelRateManage:edit"><td>
    				<a href="${ctx}/xiaodian/pay/channelRateManage/form?id=${channelRateManage.id}">修改</a>
					<a href="${ctx}/xiaodian/pay/channelRateManage/delete?id=${channelRateManage.id}" onclick="return confirmx('确认要删除该渠道费率吗？', this.href)">删除</a>
					<a href="${ctx}/xiaodian/pay/channelRateManage/api?id=${channelRateManage.id}">开通支付</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>