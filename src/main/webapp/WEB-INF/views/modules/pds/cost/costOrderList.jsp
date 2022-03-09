<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成本核算管理</title>
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
		<li class="active"><a href="${ctx}/pds/cost/costOrder/">成本核算列表</a></li>
		<shiro:hasPermission name="pds:cost:costOrder:edit"><li><a href="${ctx}/pds/cost/costOrder/form">成本核算添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="costOrder" action="${ctx}/pds/cost/costOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易日：</label>
				<input name="dealDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${costOrder.dealDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>平台商家ID：</label>
				<form:input path="pmId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易日</th>
				<th>平台商家ID</th>
				<th>总订单数</th>
				<th>商品SPU数</th>
				<th>商品SKU数</th>
				<th>供应商货款</th>
				<th>采购商货款</th>
				<th>售后货款</th>
				<th>成本合计</th>
				<th>毛利润</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:cost:costOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="costOrder">
			<tr>
				<td><a href="${ctx}/pds/cost/costOrder/form?id=${costOrder.id}">
					<fmt:formatDate value="${costOrder.dealDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${costOrder.pmId}
				</td>
				<td>
					${costOrder.totalOrderNum}
				</td>
				<td>
					${costOrder.spuNum}
				</td>
				<td>
					${costOrder.skuNum}
				</td>
				<td>
					${costOrder.supplierPayment}
				</td>
				<td>
					${costOrder.buyerPayment}
				</td>
				<td>
					${costOrder.afterSalePayment}
				</td>
				<td>
					${costOrder.costTotal}
				</td>
				<td>
					${costOrder.grossProfit}
				</td>
				<td>
					<fmt:formatDate value="${costOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${costOrder.remarks}
				</td>
				<shiro:hasPermission name="pds:cost:costOrder:edit"><td>
    				<a href="${ctx}/pds/cost/costOrder/form?id=${costOrder.id}">修改</a>
					<a href="${ctx}/pds/cost/costOrder/delete?id=${costOrder.id}" onclick="return confirmx('确认要删除该成本核算吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>