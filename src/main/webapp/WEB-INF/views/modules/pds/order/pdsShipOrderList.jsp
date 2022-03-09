<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>送货单管理</title>
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
		<li class="active"><a href="${ctx}/pds/order/pdsShipOrder/">送货单列表</a></li>
		<shiro:hasPermission name="pds:order:pdsShipOrder:edit"><li><a href="${ctx}/pds/order/pdsShipOrder/form">送货单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pdsShipOrder" action="${ctx}/pds/order/pdsShipOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>送货单号：</label>
				<form:input path="shipId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购商ID：</label>
				<form:input path="buyerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>配送编号：</label>
				<form:input path="deliveryId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_delivery_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>序号</th>
				<th>送货单号</th>
				<th>采购商</th>
				<th>平台商家</th>
				<th>配送编号</th>
				<th>途径点</th>
				<th>送达时间</th>
				<th>验收时间</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:pdsShipOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pdsShipOrder" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/pdsShipOrder/form?id=${pdsShipOrder.id}">
					${pdsShipOrder.shipId}
				</a></td>
				<td>
					${pdsShipOrder.buyerName}
				</td>
				<td>
					${pdsShipOrder.pmName}
				</td>
				<td>
					${pdsShipOrder.deliveryId}
				</td>
				<td>
					${pdsShipOrder.pathPoint}
				</td>
				<td>
					<fmt:formatDate value="${pdsShipOrder.arriveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${pdsShipOrder.acceptTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(pdsShipOrder.status, 'pds_delivery_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${pdsShipOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pdsShipOrder.remarks}
				</td>
				<shiro:hasPermission name="pds:order:pdsShipOrder:edit"><td>
    				<a href="${ctx}/pds/order/pdsShipOrder/form?id=${pdsShipOrder.id}">修改</a>
					<a href="${ctx}/pds/order/pdsShipOrder/delete?id=${pdsShipOrder.id}" onclick="return confirmx('确认要删除该送货单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>