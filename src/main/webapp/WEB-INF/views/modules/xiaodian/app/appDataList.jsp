<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据对象管理</title>
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

        function toReset() {
            $("#objName").val("");
            $("#appId").val("");
            $("#name").val("");
            $("#categoryId").val("");
            $("#categoryName").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/app/appData/">数据对象列表</a></li>
		<shiro:hasPermission name="xiaodian:app:appData:edit"><li><a href="${ctx}/xiaodian/app/appData/form">数据对象添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appData" action="${ctx}/xiaodian/app/appData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>对象名称：</label>
				<form:input path="objName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>AppId：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>数据分类：</label>
				<sys:treeselect id="category" name="category" value="${category.id}" labelName="category.name"
								labelValue="${category.name}"
								title="数据分类" url="/xiaodian/app/appDataCategory/treeData" cssClass="" allowClear="true"
								notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li ><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>对象名称</th>
				<th>应用ID</th>
				<th>应用名称</th>
				<th>对象描述</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:app:appData:edit"><th style="width: 280px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appData">
			<tr>
				<td><a href="${ctx}/xiaodian/app/appData/form?id=${appData.id}">
						${appData.objName}
				</a></td>
				<td><a href="${ctx}/xiaodian/app/appData/list?appId=${appData.appId}">
					${appData.appId}
				</a></td>
                <td>
                        ${appMap[appData.appId]}
                </td>
				<td>
					${appData.objDesc}
				</td>
				<td>
					<fmt:formatDate value="${appData.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appData.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:app:appData:edit"><td>
    				<a href="${ctx}/xiaodian/app/appData/form?id=${appData.id}">修改</a>
					<a href="${ctx}/xiaodian/app/appData/addData?id=${appData.id}">添加应用数据</a>
					<a href="${ctx}/xiaodian/app/appData/flushCache?id=${appData.id}">刷新缓存</a>
					<a href="${ctx}/xiaodian/app/appData/dataList?id=${appData.id}">应用数据列表</a>
					<a href="${ctx}/xiaodian/app/appData/delete?id=${appData.id}" onclick="return confirmx('确认要删除该数据对象吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>