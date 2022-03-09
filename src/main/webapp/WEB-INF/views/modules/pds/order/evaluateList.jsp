<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评价管理管理</title>
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
		<li class="active"><a href="${ctx}/pds/order/evaluate/">评价管理列表</a></li>
		<shiro:hasPermission name="pds:order:evaluate:edit"><li><a href="${ctx}/pds/order/evaluate/form">评价管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="evaluate" action="${ctx}/pds/order/evaluate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家ID：</label>
				<form:input path="pmId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>交易单号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购单号：</label>
				<form:input path="buyOrderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>配送编号：</label>
				<form:input path="deliveryNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>供应商：</label>
				<form:input path="supplierId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购商：</label>
				<form:input path="buyerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品ID：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>评价说明：</label>
				<form:input path="evaluationDesc" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>平台商家ID</th>
				<th>交易单号</th>
				<th>供应商</th>
				<th>采购商</th>
				<th>评价说明</th>
				<th>评价时间</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:evaluate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="evaluate">
			<tr>
				<td><a href="${ctx}/pds/order/evaluate/form?id=${evaluate.id}">
					${evaluate.pmId}
				</a></td>
				<td>
					${evaluate.orderId}
				</td>
				<td>
					${evaluate.supplierId}
				</td>
				<td>
					${evaluate.buyerId}
				</td>
				<td>
					${evaluate.evaluationDesc}
				</td>
				<td>
					${evaluate.evaluationDate}
				</td>
				<td>
					${evaluate.status}
				</td>
				<td>
					<fmt:formatDate value="${evaluate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${evaluate.remarks}
				</td>
				<shiro:hasPermission name="pds:order:evaluate:edit"><td>
    				<a href="${ctx}/pds/order/evaluate/form?id=${evaluate.id}">修改</a>
					<a href="${ctx}/pds/order/evaluate/delete?id=${evaluate.id}" onclick="return confirmx('确认要删除该评价管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>