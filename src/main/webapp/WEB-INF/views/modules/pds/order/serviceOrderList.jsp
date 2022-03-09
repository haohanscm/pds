<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>售后单管理</title>
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
		<li class="active"><a href="${ctx}/pds/order/serviceOrder/">售后单列表</a></li>
		<shiro:hasPermission name="pds:order:serviceOrder:edit"><li><a href="${ctx}/pds/order/serviceOrder/form">售后单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="serviceOrder" action="${ctx}/pds/order/serviceOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>售后单号：</label>
				<form:input path="serviceId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购单号：</label>
				<form:input path="buyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>交易单号：</label>
				<form:input path="tradeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>配送编号：</label>
				<form:input path="deliveryId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购商ID：</label>
				<form:input path="buyerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>供应商ID：</label>
				<form:input path="supplierId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>售后分类：</label>
				<form:select path="serviceCategory" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_service_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>阶段：</label>
				<form:select path="stage" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_process_stage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>联系人：</label>
				<form:input path="linkMan" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="linkPhone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>售后单号</th>
				<th>平台商家</th>
				<th>采购单号</th>
				<th>交易单号</th>
				<th>配送编号</th>
				<th>采购商</th>
				<th>供应商</th>
				<th>配送时间</th>
				<th>售后金额(元)</th>
				<th>售后分类</th>
				<%--<th>阶段</th>--%>
				<th>联系人</th>
				<th>联系电话</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:serviceOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serviceOrder" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/serviceOrder/form?id=${serviceOrder.id}">
					${serviceOrder.serviceId}
				</a></td>
				<td>
					${serviceOrder.pmName}
				</td>
				<td>
					${serviceOrder.buyId}
				</td>
				<td>
					${serviceOrder.tradeId}
				</td>
				<td>
					${serviceOrder.deliveryId}
				</td>
				<td>
					${serviceOrder.buyerName}
				</td>
				<td>
					${serviceOrder.supplierName}
				</td>
				<td>
					<fmt:formatDate value="${serviceOrder.deliveryTime}" pattern="yyyy-MM-dd"/>
				</td>
                <td>
                        ${serviceOrder.refundAmount}
                </td>
				<td>
					${fns:getDictLabel(serviceOrder.serviceCategory, 'pds_service_category', '')}
				</td>
				<%--<td>--%>
					<%--${fns:getDictLabel(serviceOrder.stage, 'pds_process_stage', '')}--%>
				<%--</td>--%>
				<td>
					${serviceOrder.linkMan}
				</td>
				<td>
					${serviceOrder.linkPhone}
				</td>
				<td>
					${fns:getDictLabel(serviceOrder.status, 'pds_service_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${serviceOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${serviceOrder.remarks}
				</td>
				<shiro:hasPermission name="pds:order:serviceOrder:edit"><td>
    				<a href="${ctx}/pds/order/serviceOrder/form?id=${serviceOrder.id}">修改</a>
					<a href="${ctx}/pds/order/serviceOrder/delete?id=${serviceOrder.id}" onclick="return confirmx('确认要删除该售后单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>