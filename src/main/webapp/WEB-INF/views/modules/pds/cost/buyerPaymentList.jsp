<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购商货款管理</title>
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
		<li class="active"><a href="${ctx}/pds/cost/buyerPayment/">采购商货款列表</a></li>
		<shiro:hasPermission name="pds:cost:buyerPayment:edit"><li><a href="${ctx}/pds/cost/buyerPayment/form">采购商货款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="buyerPayment" action="${ctx}/pds/cost/buyerPayment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>货款编号：</label>
                <form:input path="buyerPaymentId" htmlEscape="false" maxlength="10" class="input-medium " />
            </li>
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>商家：</label>
                <form:input path="merchantName" htmlEscape="false" maxlength="10" class="input-medium " placeholder="可模糊查询"/>
			</li>
			<li><label>采购商：</label>
				<form:select path="buyerId" class="input-medium">
					<form:option value="" label=""/>
                    <form:options items="${buyerList}" itemValue="id" itemLabel="buyerName" />
				</form:select>
			</li>
            <li><label>售后单：</label>
                <form:input path="serviceId" htmlEscape="false" maxlength="10" class="input-medium " />
            </li>
            <li><label>采购编号：</label>
                <form:input path="buyId" htmlEscape="false" maxlength="10" class="input-medium " />
            </li>
			<li><label>送货日期：</label>
				<input name="beginBuyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${buyerPayment.beginBuyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endBuyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${buyerPayment.endBuyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
            <li><label>是否结算：</label>
                <form:select path="status" class="input-small ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>货款编号</th>
				<th>采购编号</th>
				<th>采购商</th>
				<th>商家</th>
				<th>平台商家</th>
				<th>送货日期</th>
				<th>商品数量</th>
				<th>采购货款</th>
				<th>售后货款</th>
				<th>售后单</th>
				<th>是否结算</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:cost:buyerPayment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="buyerPayment" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/cost/buyerPayment/form?id=${buyerPayment.id}">
					${buyerPayment.buyerPaymentId}
				</a></td>
                <td>
                        ${buyerPayment.buyId}
                </td>
				<td>
					${buyerPayment.buyerName}
				</td>
				<td>
					${buyerPayment.merchantName}
				</td>
				<td>
					${buyerPayment.pmName}
				</td>
				<td>
					<fmt:formatDate value="${buyerPayment.buyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${buyerPayment.goodsNum}
				</td>
				<td>
					${buyerPayment.buyerPayment}
				</td>
				<td>
					${buyerPayment.afterSalePayment}
				</td>
				<td>
					${buyerPayment.serviceId}
				</td>
				<td>
					${fns:getDictLabel(buyerPayment.status, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${buyerPayment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${buyerPayment.remarks}
				</td>
				<shiro:hasPermission name="pds:cost:buyerPayment:edit"><td>
    				<a href="${ctx}/pds/cost/buyerPayment/form?id=${buyerPayment.id}">修改</a>
					<a href="${ctx}/pds/cost/buyerPayment/delete?id=${buyerPayment.id}" onclick="return confirmx('确认要删除该采购商货款吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>