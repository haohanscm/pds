<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报价单管理</title>
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
		<li class="active"><a href="${ctx}/pds/order/offerOrder/">报价单列表</a></li>
		<shiro:hasPermission name="pds:order:offerOrder:edit"><li><a href="${ctx}/pds/order/offerOrder/form">报价单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="offerOrder" action="${ctx}/pds/order/offerOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>报价用户：</label>
				<form:input path="offerUid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>报价单号：</label>
				<form:input path="offerOrderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>询价单号：</label>
				<form:input path="askOrderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>报价类型：</label>
				<form:select path="offerType" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_offer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
            <li><label>供应商：</label>
                <form:select path="supplierId" class="input-medium ">
                    <form:option value="" label="" />
                    <form:options items="${supplierList}" itemLabel="supplierName" itemValue="id"/>
                </form:select>
            </li>
			<li><label>状态：</label>
				<form:select path="status" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_offer_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发货状态：</label>
                <form:select path="shipStatus" class="input-small ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pds_supplier_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
            <li><label>备货日期：</label>
                <input name="prepareDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${offerOrder.prepareDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
				<th>报价单号</th>
                <th>平台商家</th>
				<th>询价单号</th>
				<%--<th>报价用户</th>--%>
				<th>报价类型</th>
				<th>供应商</th>
				<th>商品名称</th>
				<th>起售数量</th>
				<th>最大供应数量</th>
				<th>向供应商采购</th>
				<th>供应商报价</th>
				<th>成交价</th>
				<%--<th>实物图片</th>--%>
				<th>订单状态</th>
				<th>发货状态</th>
				<th>报价时间</th>
				<th>备货时间</th>
				<th>成交时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="pds:order:offerOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="offerOrder" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/offerOrder/form?id=${offerOrder.id}">
					${offerOrder.offerOrderId}
				</a></td>
                <td>
                        ${offerOrder.pmName}
                </td>
				<td>
					${offerOrder.askOrderId}
				</td>
				<%--<td>--%>
					<%--${offerOrder.offerUid}--%>
				<%--</td>--%>
				<td>
					${fns:getDictLabel(offerOrder.offerType, 'pds_offer_type', '')}
				</td>
				<td>
					${offerOrder.supplierName}
				</td>
				<td>
					${offerOrder.goodsName}
				</td>
				<td>
					${offerOrder.minSaleNum}
				</td>
				<td>
					${offerOrder.supplyNum}
				</td>
				<td>
					${offerOrder.buyNum}
				</td>
				<td>
					${offerOrder.supplyPrice}
				</td>
				<td>
					${offerOrder.dealPrice}
				</td>
				<%--<td>--%>
					<%--${offerOrder.supplyImg}--%>
				<%--</td>--%>
				<td>
					${fns:getDictLabel(offerOrder.status, 'pds_offer_status', '')}
				</td>
				<td>
					${fns:getDictLabel(offerOrder.shipStatus, 'pds_supplier_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${offerOrder.offerPriceTime}" pattern="yyyy-MM-dd"/>
				</td>
                <td>
                    <fmt:formatDate value="${offerOrder.prepareDate}" pattern="yyyy-MM-dd"/>
                </td>
				<td>
					<fmt:formatDate value="${offerOrder.dealTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${offerOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="pds:order:offerOrder:edit"><td>
    				<a href="${ctx}/pds/order/offerOrder/form?id=${offerOrder.id}">修改</a>
					<a href="${ctx}/pds/order/offerOrder/delete?id=${offerOrder.id}" onclick="return confirmx('确认要删除该报价单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>