<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息模板管理管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/message/wechatMessageTemplate/">消息模板管理列表</a></li>
		<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit"><li><a href="${ctx}/xiaodian/message/wechatMessageTemplate/form">消息模板管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wechatMessageTemplate" action="${ctx}/xiaodian/message/wechatMessageTemplate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模板ID：</label>
				<form:input path="templateId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>消息类型：</label>
				<form:select path="msgType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('wechat_msg_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="5" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>模板ID</th>
				<th>应用ID</th>
				<th>消息类型</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wechatMessageTemplate">
			<tr>
				<td><a href="${ctx}/xiaodian/message/wechatMessageTemplate/form?id=${wechatMessageTemplate.id}">
					${wechatMessageTemplate.templateId}
				</a></td>
				<td>
					${wechatMessageTemplate.appId}
				</td>
				<td>
					${fns:getDictLabel(wechatMessageTemplate.msgType, 'wechat_msg_type', '')}
				</td>
				<td>
						${fns:getDictLabel(wechatMessageTemplate.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${wechatMessageTemplate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wechatMessageTemplate.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit"><td>
    				<a href="${ctx}/xiaodian/message/wechatMessageTemplate/form?id=${wechatMessageTemplate.id}">修改</a>
					<a href="${ctx}/xiaodian/message/wechatMessageTemplate/delete?id=${wechatMessageTemplate.id}" onclick="return confirmx('确认要删除该消息模板管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>