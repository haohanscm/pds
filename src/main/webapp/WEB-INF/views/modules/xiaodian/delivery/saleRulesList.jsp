<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>售卖规则管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/delivery/saleRules/">售卖规则列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:saleRules:edit"><li><a href="${ctx}/xiaodian/delivery/saleRules/form">售卖规则添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="saleRules" action="${ctx}/xiaodian/delivery/saleRules/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品id：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<%--<li><label>售卖时效：</label>--%>
				<%--<form:input path="arriveType" htmlEscape="false" maxlength="64" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>配送类型：</label>
				<form:select path="saleDeliveryType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>商品id</th>
				<th>售卖时效</th>
				<th>起售数量</th>
				<th>限制购买次数</th>
				<th>配送类型限制</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:saleRules:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="saleRules">
			<tr>
				<td><a href="${ctx}/xiaodian/delivery/saleRules/form?id=${saleRules.id}">
					${saleRules.goodsId}
				</a></td>
				<td>
					${saleRules.saleArriveType}
				</td>
				<td>
					${saleRules.minSaleNum}
				</td>
				<td>
					${saleRules.limitBuyTimes}
				</td>
				<td>
					${fns:getDictLabel(saleRules.saleDeliveryType, 'delivery_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${saleRules.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${saleRules.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:delivery:saleRules:edit"><td>
    				<a href="${ctx}/xiaodian/delivery/saleRules/form?id=${saleRules.id}">修改</a>
					<a href="${ctx}/xiaodian/delivery/saleRules/delete?id=${saleRules.id}" onclick="return confirmx('确认要删除该售卖规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>