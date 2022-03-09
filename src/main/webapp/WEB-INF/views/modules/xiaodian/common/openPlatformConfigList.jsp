<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>开放平台管理</title>
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
        function toReset(){
		    $("#appId").val("");
		    $("#appType").select2("val","");
		    $("#status").select2("val","");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/common/openPlatformConfig/">开放平台列表</a></li>
		<shiro:hasPermission name="xiaodian:common:openPlatformConfig:edit"><li><a href="${ctx}/xiaodian/common/openPlatformConfig/form">开放平台添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="xiaodian:common:openPlatformConfig:view"><li><a href="${ctx}/xiaodian/common/openPlatformConfig/iframe">数据看板</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="openPlatformConfig" action="${ctx}/xiaodian/common/openPlatformConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>应用类型：</label>
				<form:select path="appType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>应用ID</th>
				<th>应用名称</th>
				<th>应用类型</th>
				<th>访问token</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:common:openPlatformConfig:edit"><th style="width: 150px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="openPlatformConfig">
			<tr>
				<td><a href="${ctx}/xiaodian/common/openPlatformConfig/form?id=${openPlatformConfig.id}">
					${openPlatformConfig.appId}
				</a></td>
				<td>
					${openPlatformConfig.appName}
				</td>
				<td>
					${fns:getDictLabel(openPlatformConfig.appType, 'app_type', '')}
				</td>
				<td>
					${openPlatformConfig.accessToken}
				</td>
				<td>
					${fns:getDictLabel(openPlatformConfig.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${openPlatformConfig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="max-width: 200px;overflow: hidden;">
					${openPlatformConfig.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:common:openPlatformConfig:edit"><td>
    				<a href="${ctx}/xiaodian/common/openPlatformConfig/form?id=${openPlatformConfig.id}">修改</a>
					<a href="${ctx}/xiaodian/common/openPlatformConfig/delete?id=${openPlatformConfig.id}" onclick="return confirmx('确认要删除该开放平台吗？', this.href)">删除</a>
    				<a href="${ctx}/xiaodian/common/openPlatformConfig/copy?id=${openPlatformConfig.id}">复制</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>