<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品库属性名管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/database/productAttrName/">商品库属性名列表</a></li>
		<shiro:hasPermission name="xiaodian:database:productAttrName:edit"><li><a href="${ctx}/xiaodian/database/productAttrName/form">商品库属性名添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productAttrName" action="${ctx}/xiaodian/database/productAttrName/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>属性名：</label>
				<form:input path="attrName" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>属性名</th>
				<th>属性名id</th>
				<th>标准商品id</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:database:productAttrName:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="productAttrName">
			<tr>
				<td><a href="${ctx}/xiaodian/database/productAttrName/form?id=${productAttrName.id}">
					${productAttrName.attrName}
				</a></td>
				<td>
					${productAttrName.id}
				</td>
				<td>
					${productAttrName.spuId}
				</td>
				<td>
					${productAttrName.sort}
				</td>
				<td>
					<fmt:formatDate value="${productAttrName.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${productAttrName.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:database:productAttrName:edit"><td>
    				<a href="${ctx}/xiaodian/database/productAttrName/form?id=${productAttrName.id}">修改</a>
					<a href="${ctx}/xiaodian/database/productAttrName/delete?id=${productAttrName.id}" onclick="return confirmx('确认要删除该商品库属性名吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>