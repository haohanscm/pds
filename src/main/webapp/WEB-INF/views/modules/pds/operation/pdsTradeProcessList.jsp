<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台交易流程管理</title>
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
		<li class="active"><a href="${ctx}/pds/operation/pdsTradeProcess/">平台交易流程列表</a></li>
		<shiro:hasPermission name="pds:operation:pdsTradeProcess:edit"><li><a href="${ctx}/pds/operation/pdsTradeProcess/form">平台交易流程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pdsTradeProcess" action="${ctx}/pds/operation/pdsTradeProcess/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:input path="pmId" htmlEscape="false" maxlength="10" class="input-medium"/>
            </li>
			<li><label>交易单号：</label>
				<form:input path="processSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购批次：</label>
				<form:select path="buySeq" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_seq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>送货日期：</label>
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${pdsTradeProcess.deliveryTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_trade_process_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>交易单号</th>
				<th>采购批次</th>
				<th>送货日期</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:operation:pdsTradeProcess:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pdsTradeProcess">
			<tr>
				<td><a href="${ctx}/pds/operation/pdsTradeProcess/form?id=${pdsTradeProcess.id}">
					${pdsTradeProcess.processSn}
				</a></td>
				<td>
					${fns:getDictLabel(pdsTradeProcess.buySeq, 'pds_buy_seq', '')}
				</td>
				<td>
					<fmt:formatDate value="${pdsTradeProcess.deliveryTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(pdsTradeProcess.status, 'pds_trade_process_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${pdsTradeProcess.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pdsTradeProcess.remarks}
				</td>
				<shiro:hasPermission name="pds:operation:pdsTradeProcess:edit"><td>
    				<a href="${ctx}/pds/operation/pdsTradeProcess/form?id=${pdsTradeProcess.id}">修改</a>
					<a href="${ctx}/pds/operation/pdsTradeProcess/delete?id=${pdsTradeProcess.id}" onclick="return confirmx('确认要删除该平台交易流程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>