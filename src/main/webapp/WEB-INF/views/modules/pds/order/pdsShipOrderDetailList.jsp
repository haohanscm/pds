<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>送货单明细管理</title>
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
		<li class="active"><a href="${ctx}/pds/order/pdsShipOrderDetail/">送货单明细列表</a></li>
		<shiro:hasPermission name="pds:order:pdsShipOrderDetail:edit"><li><a href="${ctx}/pds/order/pdsShipOrderDetail/form">送货单明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pdsShipOrderDetail" action="${ctx}/pds/order/pdsShipOrderDetail/" method="post" class="breadcrumb form-search">
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
			<li><label>交易单号：</label>
				<form:input path="tradeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
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
                <th>平台商家</th>
				<th>交易单号</th>
				<th>商品名称</th>
				<th>商品数量</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:pdsShipOrderDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pdsShipOrderDetail" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/pdsShipOrderDetail/form?id=${pdsShipOrderDetail.id}">
					${pdsShipOrderDetail.shipId}
				</a></td>
                <td>
                        ${pdsShipOrderDetail.pmName}
                </td>
				<td>
					${pdsShipOrderDetail.tradeId}
				</td>
				<td>
					${pdsShipOrderDetail.goodsName}
				</td>
				<td>
					${pdsShipOrderDetail.goodsNum}
				</td>
				<td>
					<fmt:formatDate value="${pdsShipOrderDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pdsShipOrderDetail.remarks}
				</td>
				<shiro:hasPermission name="pds:order:pdsShipOrderDetail:edit"><td>
    				<a href="${ctx}/pds/order/pdsShipOrderDetail/form?id=${pdsShipOrderDetail.id}">修改</a>
					<a href="${ctx}/pds/order/pdsShipOrderDetail/delete?id=${pdsShipOrderDetail.id}" onclick="return confirmx('确认要删除该送货单明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>