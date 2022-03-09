<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品调拨明细管理</title>
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
		<li class="active"><a href="${ctx}/pss/storage/goodsAllotDetail/">商品调拨明细列表</a></li>
		<shiro:hasPermission name="pss:storage:goodsAllotDetail:edit"><li><a href="${ctx}/pss/storage/goodsAllotDetail/form">商品调拨明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsAllotDetail" action="${ctx}/pss/storage/goodsAllotDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>调拨ID：</label>
				<form:input path="allotId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品编码：</label>
				<form:input path="goodsCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>调拨ID</th>
				<th>商品编码</th>
				<th>商品名称</th>
				<th>规格</th>
				<th>单位</th>
				<th>价格</th>
				<th>调拨数量</th>
				<th>调出原库存</th>
				<th>调出后库存</th>
				<th>调入原库存</th>
				<th>调入后库存</th>
				<th>操作人</th>
				<th>操作时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:storage:goodsAllotDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsAllotDetail">
			<tr>
				<td><a href="${ctx}/pss/storage/goodsAllotDetail/form?id=${goodsAllotDetail.id}">
					${goodsAllotDetail.allotId}
				</a></td>
				<td>
					${goodsAllotDetail.goodsCode}
				</td>
				<td>
					${goodsAllotDetail.goodsName}
				</td>
				<td>
					${goodsAllotDetail.modelName}
				</td>
				<td>
					${goodsAllotDetail.unit}
				</td>
				<td>
					${goodsAllotDetail.price}
				</td>
				<td>
					${goodsAllotDetail.goodsNum}
				</td>
				<td>
					${goodsAllotDetail.outorigStock}
				</td>
				<td>
					${goodsAllotDetail.outStock}
				</td>
				<td>
					${goodsAllotDetail.inorigStock}
				</td>
				<td>
					${goodsAllotDetail.inStock}
				</td>
				<td>
					${goodsAllotDetail.operator}
				</td>
				<td>
					<fmt:formatDate value="${goodsAllotDetail.oprateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${goodsAllotDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsAllotDetail.remarks}
				</td>
				<shiro:hasPermission name="pss:storage:goodsAllotDetail:edit"><td>
    				<a href="${ctx}/pss/storage/goodsAllotDetail/form?id=${goodsAllotDetail.id}">修改</a>
					<a href="${ctx}/pss/storage/goodsAllotDetail/delete?id=${goodsAllotDetail.id}" onclick="return confirmx('确认要删除该商品调拨明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>