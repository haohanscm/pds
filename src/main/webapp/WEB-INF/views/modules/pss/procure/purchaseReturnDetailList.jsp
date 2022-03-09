<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购退货明细管理</title>
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
		<li class="active"><a href="${ctx}/pss/procure/purchaseReturnDetail/">采购退货明细列表</a></li>
		<shiro:hasPermission name="pss:procure:purchaseReturnDetail:edit"><li><a href="${ctx}/pss/procure/purchaseReturnDetail/form">采购退货明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="purchaseReturnDetail" action="${ctx}/pss/procure/purchaseReturnDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>退货ID：</label>
				<form:input path="returnId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>退货ID</th>
				<th>条形码</th>
				<th>商品编码</th>
				<th>商品名称</th>
				<th>单位</th>
				<th>规格</th>
				<th>数量</th>
				<th>单价</th>
				<th>金额</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:procure:purchaseReturnDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="purchaseReturnDetail">
			<tr>
				<td><a href="${ctx}/pss/procure/purchaseReturnDetail/form?id=${purchaseReturnDetail.id}">
					${purchaseReturnDetail.returnId}
				</a></td>
				<td>
					${purchaseReturnDetail.barCode}
				</td>
				<td>
					${purchaseReturnDetail.goodsCode}
				</td>
				<td>
					${purchaseReturnDetail.goodsName}
				</td>
				<td>
					${purchaseReturnDetail.unit}
				</td>
				<td>
					${purchaseReturnDetail.attr}
				</td>
				<td>
					${purchaseReturnDetail.num}
				</td>
				<td>
					${purchaseReturnDetail.price}
				</td>
				<td>
					${purchaseReturnDetail.amount}
				</td>
				<td>
					<fmt:formatDate value="${purchaseReturnDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${purchaseReturnDetail.remarks}
				</td>
				<shiro:hasPermission name="pss:procure:purchaseReturnDetail:edit"><td>
    				<a href="${ctx}/pss/procure/purchaseReturnDetail/form?id=${purchaseReturnDetail.id}">修改</a>
					<a href="${ctx}/pss/procure/purchaseReturnDetail/delete?id=${purchaseReturnDetail.id}" onclick="return confirmx('确认要删除该采购退货明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>