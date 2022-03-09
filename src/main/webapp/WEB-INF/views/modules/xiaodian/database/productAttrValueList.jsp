<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品库属性值管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/database/productAttrValue/">商品库属性值列表</a></li>
		<shiro:hasPermission name="xiaodian:database:productAttrValue:edit"><li><a href="${ctx}/xiaodian/database/productAttrValue/form">商品库属性值添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productAttrValue" action="${ctx}/xiaodian/database/productAttrValue/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>属性值：</label>
				<form:input path="attrValue" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>属性名id：</label>
				<form:input path="attrName" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>标准商品id：</label>
				<form:input path="spuId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>属性值</th>
				<th>属性名</th>
				<th>属性名id</th>
				<th>标准商品id</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:database:productAttrValue:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="productAttrValue">
			<tr>
				<td><a href="${ctx}/xiaodian/database/productAttrValue/form?id=${productAttrValue.id}">
					${productAttrValue.attrValue}
				</a></td>
				<td>
					${productAttrValue.attrName}
				</td>
				<td>
					${productAttrValue.attrNameId}
				</td>
				<td>
					${productAttrValue.spuId}
				</td>
				<td>
					${productAttrValue.sort}
				</td>
				<td>
					<fmt:formatDate value="${productAttrValue.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${productAttrValue.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:database:productAttrValue:edit"><td>
    				<a href="${ctx}/xiaodian/database/productAttrValue/form?id=${productAttrValue.id}">修改</a>
					<a href="${ctx}/xiaodian/database/productAttrValue/delete?id=${productAttrValue.id}" onclick="return confirmx('确认要删除该商品库属性值吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>