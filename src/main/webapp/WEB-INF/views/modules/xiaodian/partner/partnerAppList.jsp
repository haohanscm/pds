<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>厂商应用管理管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/partner/partnerApp/">厂商应用管理列表</a></li>
		<shiro:hasPermission name="xiaodian:partner:partnerApp:edit"><li><a href="${ctx}/xiaodian/partner/partnerApp/form">厂商应用管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="partnerApp" action="${ctx}/xiaodian/partner/partnerApp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>厂商名称：</label>
				<form:input path="partnerName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>应用KEY：</label>
				<form:input path="appKey" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>厂商类型：</label>
				<form:select path="partnerType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('partner_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>厂商名称</th>
				<th>渠道编号</th>
				<th>应用KEY</th>
				<th>厂商联系人</th>
				<th>厂商类型</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:partner:partnerApp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="partnerApp">
			<tr>
				<td><a href="${ctx}/xiaodian/partner/partnerApp/form?id=${partnerApp.id}">
					${partnerApp.partnerName}
				</a></td>
				<td>
					${partnerApp.partnerNum}
				</td>
				<td>
					${partnerApp.appKey}
				</td>
				<td>
					${partnerApp.contactUser}
				</td>
				<td>
					${fns:getDictLabel(partnerApp.partnerType, 'partner_type', '')}
				</td>
				<td>
					${fns:getDictLabel(partnerApp.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${partnerApp.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${partnerApp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${partnerApp.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:partner:partnerApp:edit"><td>
    				<a href="${ctx}/xiaodian/partner/partnerApp/form?id=${partnerApp.id}">修改</a>
					<a href="${ctx}/xiaodian/partner/partnerApp/delete?id=${partnerApp.id}" onclick="return confirmx('确认要删除该厂商应用管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>