<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>路线管理管理</title>
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
		<li class="active"><a href="${ctx}/pds/delivery/routeManage/">路线管理列表</a></li>
		<shiro:hasPermission name="pds:delivery:routeManage:edit"><li><a href="${ctx}/pds/delivery/routeManage/form">路线管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="routeManage" action="${ctx}/pds/delivery/routeManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>路线编号：</label>
				<form:input path="lineNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>路线名称：</label>
				<form:input path="routeName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>出发地：</label>
				<form:input path="start" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>平台商家</th>
				<th>路线编号</th>
				<th>路线名称</th>
				<th>目的地</th>
				<th>出发地</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:delivery:routeManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="routeManage">
			<tr>
				<td><a href="${ctx}/pds/delivery/routeManage/form?id=${routeManage.id}">
					${routeManage.pmName}
				</a></td>
				<td>
					${routeManage.lineNo}
				</td>
				<td>
					${routeManage.routeName}
				</td>
				<td>
					${routeManage.destination}
				</td>
				<td>
					${routeManage.start}
				</td>
				<td>
					${fns:getDictLabel(routeManage.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${routeManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${routeManage.remarks}
				</td>
				<shiro:hasPermission name="pds:delivery:routeManage:edit"><td>
    				<a href="${ctx}/pds/delivery/routeManage/form?id=${routeManage.id}">修改</a>
					<a href="${ctx}/pds/delivery/routeManage/delete?id=${routeManage.id}" onclick="return confirmx('确认要删除该路线管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>