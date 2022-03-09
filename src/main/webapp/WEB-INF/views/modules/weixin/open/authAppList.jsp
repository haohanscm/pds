<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用管理</title>
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
		<li class="active"><a href="${ctx}/weixin/open/authApp/">应用列表</a></li>
		<%--<shiro:hasPermission name="weixin:open:authApp:edit"><li><a href="${ctx}/weixin/open/authApp/form">应用添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="authApp" action="${ctx}/weixin/open/authApp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>应用名称：</label>
				<form:input path="appName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>应用类型：</label>
				<form:select path="serviceType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>主体名称：</label>
				<form:input path="principalName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>微信号：</label>
				<form:input path="weixinId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>授权时间：</label>
				<input name="beginAuthTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${authApp.beginAuthTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endAuthTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${authApp.endAuthTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>应用名称</th>
				<th>主体名称</th>
				<th>微信号</th>
				<th>应用类型</th>
				<th>授权应用名称</th>
				<th>有效期</th>
				<th>授权时间</th>
				<th>状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="weixin:open:authApp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="authApp">
			<tr>
				<td><a href="${ctx}/weixin/open/authApp/form?id=${authApp.id}">
					${authApp.appId}
				</a></td>
				<td>
					${authApp.appName}
				</td>
				<td>
					${authApp.principalName}
				</td>
				<td>
					${authApp.weixinId}
				</td>
				<td>
				   ${fns:getDictLabel(authApp.serviceType, 'app_service_type', authApp.serviceType)}
					<%--<img src="${authApp.qrcode}"/>--%>
				</td>
				<td>
					${authApp.authAppName}
				</td>
				<td>
					${authApp.expiresin}
				</td>
				<td>
					<fmt:formatDate value="${authApp.authTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(authApp.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${authApp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="weixin:open:authApp:edit"><td>
    				<a href="${ctx}/weixin/open/authApp/form?id=${authApp.id}">修改</a>
					<a href="${ctx}/weixin/open/authApp/delete?id=${authApp.id}" onclick="return confirmx('确认要删除该应用吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>