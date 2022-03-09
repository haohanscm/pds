
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
            $("#appId").val("");
            $("#objId").val("");
            $("#seqNum").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/app/appDataManage/">数据对象列表</a></li>
		<shiro:hasPermission name="xiaodian:app:appDataManage:edit"><li><a href="${ctx}/xiaodian/app/appDataManage/form">数据对象添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appDataManage" action="${ctx}/xiaodian/app/appDataManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>数据对象ID：</label>
				<form:input path="objId" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</li>
			<li><label>组编号：</label>
				<form:input path="seqNum" htmlEscape="false" maxlength="64" class="input-xlarge"/>
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
				<th>组编号(行号)</th>
				<th>应用ID</th>
				<th>数据对象ID</th>
				<th>数据对象明细ID</th>
				<th>字段名称</th>
				<th>字段值</th>
				<th>字段类型</th>
				<th style="min-width: 140px;">更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:app:appDataManage:edit"><th style="min-width: 60px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appDataManage">
			<tr>
				<td><a href="${ctx}/xiaodian/app/appData/editData/${appDataManage.seqNum}?id=${appDataManage.objId}">
					${appDataManage.seqNum}
				</a></td>
				<td><a href="${ctx}/xiaodian/app/appDataManage/list?appId=${appDataManage.appId}">
					${appDataManage.appId}
				</a></td>
				<td><a href="${ctx}/xiaodian/app/appDataManage/list?objId=${appDataManage.objId}">
					${appDataManage.objId}
				</a></td>
				<td>
					${appDataManage.objDetailId}
				</td>
				<td>
					${appDataManage.fieldName}
				</td>
				<td style="max-width: 300px;overflow: hidden">
				<c:if test="${1 == appDataManage.fieldType && not empty appDataManage.fieldValue}">
					<img src="${appDataManage.fieldValue}" alt="图片-${appDataManage.fieldName}" style="height: 150px;width: 150px;">
				</c:if>
				<c:if test="${2 == appDataManage.fieldType || 0 == appDataManage.fieldType}">
							${appDataManage.fieldValue}
				</c:if>
				<c:if test="${3 == appDataManage.fieldType}">
						${fns:unescapeHtml(appDataManage.editValue)}
				</c:if>
				</td>
				<td>
					${appDataManage.fieldType}
				</td>
				<td>
					<fmt:formatDate value="${appDataManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appDataManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:app:appDataManage:edit"><td>
    				<a href="${ctx}/xiaodian/app/appDataManage/form?id=${appDataManage.id}">修改</a>
					<a href="${ctx}/xiaodian/app/appDataManage/delete?id=${appDataManage.id}" onclick="return confirmx('确认要删除该数据对象吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>