<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联银行号管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/common/unionBankNo/">联银行号列表</a></li>
		<shiro:hasPermission name="xiaodian:common:unionBankNo:edit"><li><a href="${ctx}/xiaodian/common/unionBankNo/form">联银行号添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="unionBankNo" action="${ctx}/xiaodian/common/unionBankNo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>行号：</label>
				<form:input path="bankNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>地区：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>银行名称：</label>
				<form:input path="bankName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>行号</th>
				<th>银行名称</th>
				<th>清算行号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:common:unionBankNo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="unionBankNo">
			<tr>
				<td><a href="${ctx}/xiaodian/common/unionBankNo/form?id=${unionBankNo.id}">
					${unionBankNo.bankNo}
				</a></td>
				<td>
					${unionBankNo.bankName}
				</td>
				<td>
					${unionBankNo.liquidationNo}
				</td>
				<td>
					<fmt:formatDate value="${unionBankNo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${unionBankNo.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:common:unionBankNo:edit"><td>
    				<a href="${ctx}/xiaodian/common/unionBankNo/form?id=${unionBankNo.id}">修改</a>
					<a href="${ctx}/xiaodian/common/unionBankNo/delete?id=${unionBankNo.id}" onclick="return confirmx('确认要删除该联银行号吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>