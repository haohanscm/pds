<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>易联云管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/printer/cloudPrintTerminal/">易联云列表</a></li>
		<shiro:hasPermission name="xiaodian:printer:cloudPrintTerminal:edit"><li><a href="${ctx}/xiaodian/printer/cloudPrintTerminal/form">易联云添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cloudPrintTerminal" action="${ctx}/xiaodian/printer/cloudPrintTerminal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>打印机编号：</label>
				<form:input path="machineCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家：</label>
				<form:select path="merchantId" class="input-medium">
					<form:option value="" label="查看所有"/>
					<form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>店铺：</label>
                <form:select path="shopId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${shopList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>应用ID：</label>
				<form:input path="clientId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>打印机名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_is_enable')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
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
				<th>序号</th>
				<th>打印机编号</th>
				<th>商家</th>
				<th>店铺</th>
				<th>应用ID</th>
				<th>密钥</th>
				<th>打印机名称</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:printer:cloudPrintTerminal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cloudPrintTerminal" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/xiaodian/printer/cloudPrintTerminal/form?id=${cloudPrintTerminal.id}">
					${cloudPrintTerminal.machineCode}
				</a></td>
				<td>
					${cloudPrintTerminal.merchantName}
				</td>
				<td>
					${cloudPrintTerminal.shopName}
				</td>
				<td>
					${cloudPrintTerminal.clientId}
				</td>

				<td>
					${cloudPrintTerminal.secret}
				</td>
				<td>
					${cloudPrintTerminal.name}
				</td>
				<td>
					${fns:getDictLabel(cloudPrintTerminal.status, 'common_is_enable', '')}
				</td>
				<td>
					<fmt:formatDate value="${cloudPrintTerminal.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cloudPrintTerminal.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:printer:cloudPrintTerminal:edit"><td>
    				<a href="${ctx}/xiaodian/printer/cloudPrintTerminal/form?id=${cloudPrintTerminal.id}">修改</a>
					<a href="${ctx}/xiaodian/printer/cloudPrintTerminal/delete?id=${cloudPrintTerminal.id}" onclick="return confirmx('确认要删除该易联云吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>