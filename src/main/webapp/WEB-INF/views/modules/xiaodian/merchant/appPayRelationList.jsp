<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>app支付账户管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/merchant/appPayRelation/">app支付账户列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:appPayRelation:edit"><li><a href="${ctx}/xiaodian/merchant/appPayRelation/form">app支付账户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appPayRelation" action="${ctx}/xiaodian/merchant/appPayRelation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家账户号：</label>
				<form:input path="partnerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>微信appid：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_is_enable')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
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
				<th>商家账户号</th>
				<th>微信appId</th>
				<th>app名称</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:merchant:appPayRelation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appPayRelation">
			<tr>
				<td><a href="${ctx}/xiaodian/merchant/appPayRelation/form?id=${appPayRelation.id}">
					${appPayRelation.partnerId}
				</a></td>
				<td>
					${appPayRelation.appId}
				</td>
				<td>
					${appPayRelation.appName}
				</td>
				<td>
						${fns:getDictLabel(appPayRelation.status, 'common_is_enable', '')}
				</td>
				<td>
					<fmt:formatDate value="${appPayRelation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appPayRelation.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:merchant:appPayRelation:edit"><td>
    				<a href="${ctx}/xiaodian/merchant/appPayRelation/form?id=${appPayRelation.id}">修改</a>
					<a href="${ctx}/xiaodian/merchant/appPayRelation/delete?id=${appPayRelation.id}" onclick="return confirmx('确认要删除该app支付账户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>