<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品采购明细管理</title>
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
		<li class="active"><a href="${ctx}/pss/procure/procurementDetail/">商品采购明细列表</a></li>
		<shiro:hasPermission name="pss:procure:procurementDetail:edit"><li><a href="${ctx}/pss/procure/procurementDetail/form">商品采购明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="procurementDetail" action="${ctx}/pss/procure/procurementDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家：</label>
				<form:select path="merchantId" class="input-medium">
					<form:option value="" label="查看所有"/>
					<form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>商品ID：</label>
				<form:input path="goodsModelId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购编号：</label>
				<form:input path="procureNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>入库状态：</label>
				<form:select path="stockStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('stock_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<%--<th>商家ID</th>--%>
				<th>采购编号</th>
				<th>商品名称</th>
				<th>商品ID(SKU)</th>
				<th>单价</th>
				<th>数量</th>
				<th>规格</th>
				<th>合计金额</th>
				<th>入库状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="pss:procure:procurementDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="procurementDetail">
			<tr>
				<%--<td><a href="${ctx}/pss/procure/procurementDetail/form?id=${procurementDetail.id}">--%>
					<%--${procurementDetail.merchantId}--%>
				<%--</a></td>--%>
				<td><a href="${ctx}/pss/procure/procurementDetail/form?id=${procurementDetail.id}">
					${procurementDetail.procureNum}
				</a></td>
				<td>
					${procurementDetail.goodsName}
				</td>
				<td>
					${procurementDetail.goodsModelId}
				</td>
				<td>
					${procurementDetail.price}
				</td>
				<td>
					${procurementDetail.goodsNum}
				</td>
				<td>
					${procurementDetail.modelName}
				</td>
				<td>
					${procurementDetail.sumAmount}
				</td>
				<td>
					${fns:getDictLabel(procurementDetail.stockStatus, 'stock_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${procurementDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="pss:procure:procurementDetail:edit"><td>
    				<a href="${ctx}/pss/procure/procurementDetail/form?id=${procurementDetail.id}">修改</a>
					<a href="${ctx}/pss/procure/procurementDetail/delete?id=${procurementDetail.id}" onclick="return confirmx('确认要删除该商品采购明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>