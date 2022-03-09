<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/app/appSlideShowGroup/">轮播图列表</a></li>
		<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit"><li><a href="${ctx}/xiaodian/app/appSlideShowGroup/form">轮播图添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appSlideShowGroup" action="${ctx}/xiaodian/app/appSlideShowGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>分组名称：</label>
				<form:input path="groupName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>应用ID</th>
				<th>分组名称</th>
				<th>分组描述</th>
				<th>分组排序</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appSlideShowGroup">
			<tr>
				<td><a href="${ctx}/xiaodian/app/appSlideShowGroup/form?id=${appSlideShowGroup.id}">
					${appSlideShowGroup.appId}
				</a></td>
				<td>
					${appSlideShowGroup.groupName}
				</td>
				<td>
					${appSlideShowGroup.groupDesc}
				</td>
				<td>
					${appSlideShowGroup.groupSort}
				</td>
				<td>
					${fns:getDictLabel(appSlideShowGroup.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${appSlideShowGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appSlideShowGroup.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit"><td>
    				<a href="${ctx}/xiaodian/app/appSlideShowGroup/form?id=${appSlideShowGroup.id}">修改</a>
					<a href="${ctx}/xiaodian/app/appSlideShowGroup/delete?id=${appSlideShowGroup.id}" onclick="return confirmx('确认要删除该轮播图吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>