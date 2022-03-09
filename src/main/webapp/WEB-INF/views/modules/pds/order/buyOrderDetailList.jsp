<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购单详情管理</title>
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
		<li class="active"><a href="${ctx}/pds/order/buyOrderDetail/">采购单详情列表</a></li>
		<shiro:hasPermission name="pds:order:buyOrderDetail:edit"><li><a href="${ctx}/pds/order/buyOrderDetail/form">采购单详情添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="buyOrderDetail" action="${ctx}/pds/order/buyOrderDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>汇总单号：</label>
				<form:input path="smmaryBuyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购编号：</label>
				<form:input path="buyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label>送货日期：</label>
                <input name="deliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${buyOrderDetail.deliveryDate}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </li>
            <li><label>采购批次：</label>
                <form:select path="buySeq" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pds_buy_seq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label>采购商：</label>
                <form:select path="buyerId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${buyerList}" itemLabel="buyerName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>商品ID：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>采购编号</th>
				<th>采购明细编号</th>
                <th>平台商家</th>
                <th>汇总编号</th>
				<th>采购商</th>
				<%--<th>商品图片</th>--%>
				<th>商品名称</th>
				<th>商品规格</th>
				<th>下单数量</th>
				<th>采购数量</th>
				<th>单位</th>
				<th>市场价格</th>
				<th>采购价格</th>
				<th>状态</th>
                <th>送货日期</th>
                <th>采购批次</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:buyOrderDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="buyOrderDetail" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/buyOrderDetail/form?id=${buyOrderDetail.id}">
					${buyOrderDetail.buyId}
				</a></td>
                <td>
                    ${buyOrderDetail.buyDetailSn}
                </td>
                <td>
                    ${buyOrderDetail.pmName}
                </td>
                <td>
                    ${buyOrderDetail.smmaryBuyId}
                </td>
				<td>
					${buyOrderDetail.buyerName}
				</td>
				<%--<td>--%>
					<%--${buyOrderDetail.goodsImg}--%>
				<%--</td>--%>
				<td>
					${buyOrderDetail.goodsName}
				</td>
				<td>
					${buyOrderDetail.goodsModel}
				</td>
				<td>
					${buyOrderDetail.orderGoodsNum}
				</td>
				<td>
					${buyOrderDetail.goodsNum}
				</td>
				<td>
						${buyOrderDetail.unit}
				</td>
				<td>
					${buyOrderDetail.marketPrice}
				</td>
				<td>
					${buyOrderDetail.buyPrice}
				</td>
				<td>
					${fns:getDictLabel(buyOrderDetail.status, 'pds_buy_status', '')}
				</td>
                <td>
                    <fmt:formatDate value="${buyOrderDetail.deliveryDate}" pattern="yyyy-MM-dd"/>
                </td>
                <td>
                        ${fns:getDictLabel(buyOrderDetail.buySeq, 'pds_buy_seq', '')}
                </td>
				<td>
					<fmt:formatDate value="${buyOrderDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${buyOrderDetail.remarks}
				</td>
				<shiro:hasPermission name="pds:order:buyOrderDetail:edit"><td>
    				<a href="${ctx}/pds/order/buyOrderDetail/form?id=${buyOrderDetail.id}">修改</a>
					<%--<a href="${ctx}/pds/order/buyOrderDetail/delete?id=${buyOrderDetail.id}" onclick="return confirmx('确认要删除该采购单详情吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>