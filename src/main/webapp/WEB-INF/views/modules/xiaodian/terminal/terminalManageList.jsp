<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/terminal/terminalManage/">设备列表</a></li>
		<shiro:hasPermission name="xiaodian:terminal:terminalManage:edit"><li><a href="${ctx}/xiaodian/terminal/terminalManage/form">设备添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="terminalManage" action="${ctx}/xiaodian/terminal/terminalManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>设备编号：</label>
				<form:input path="terminalNo" htmlEscape="false" maxlength="24" class="input-medium"/>
			</li>
			<li><label>设备类型：</label>
				<form:select path="terminalType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('terminal_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>设备名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>设备别名：</label>
				<form:input path="alias" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>SN码：</label>
				<form:input path="snCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家id：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺id：</label>
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>设备编号</th>
				<th>设备类型</th>
				<th>设备名称</th>
				<th>设备别名</th>
				<th>所属店铺</th>
				<th>SN码</th>
				<th>制造厂商</th>
				<th>设备状态</th>
				<th>设备备注</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:terminal:terminalManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="terminalManage">
			<tr>
				<td><a href="${ctx}/xiaodian/terminal/terminalManage/form?id=${terminalManage.id}">
					${terminalManage.terminalNo}
				</a></td>
				<td>
					${fns:getDictLabel(terminalManage.terminalType, 'terminal_type', '')}
				</td>
				<td>
					${terminalManage.name}
				</td>
				<td>
					${terminalManage.alias}
				</td>
				<td>
					${terminalManage.shopName}
				</td>
				<td>
					${terminalManage.snCode}
				</td>
				<td>
					${terminalManage.producer}
				</td>
				<td>
					${fns:getDictLabel(terminalManage.status, 'common_status', '')}
				</td>
				<td>
					${terminalManage.remark}
				</td>
				<td>
					<fmt:formatDate value="${terminalManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${terminalManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:terminal:terminalManage:edit"><td>
    				<a href="${ctx}/xiaodian/terminal/terminalManage/form?id=${terminalManage.id}">修改</a>
					<a href="${ctx}/xiaodian/terminal/terminalManage/delete?id=${terminalManage.id}" onclick="return confirmx('确认要删除该设备吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>