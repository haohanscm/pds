<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理</title>
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
		<li class="active"><a href="${ctx}/pds/business/messageManage/">消息列表</a></li>
		<shiro:hasPermission name="pds:business:messageManage:edit"><li><a href="${ctx}/pds/business/messageManage/form">消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="messageManage" action="${ctx}/pds/business/messageManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家ID：</label>
				<form:input path="pmId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>消息编号：</label>
				<form:input path="messageNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>消息类型：</label>
				<form:input path="messageType" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>平台商家ID</th>
				<th>消息编号</th>
				<th>消息类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:business:messageManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageManage">
			<tr>
				<td><a href="${ctx}/pds/business/messageManage/form?id=${messageManage.id}">
					${messageManage.pmId}
				</a></td>
				<td>
					${messageManage.messageNo}
				</td>
				<td>
					${messageManage.messageType}
				</td>
				<td>
					<fmt:formatDate value="${messageManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${messageManage.remarks}
				</td>
				<shiro:hasPermission name="pds:business:messageManage:edit"><td>
    				<a href="${ctx}/pds/business/messageManage/form?id=${messageManage.id}">修改</a>
					<a href="${ctx}/pds/business/messageManage/delete?id=${messageManage.id}" onclick="return confirmx('确认要删除该消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>