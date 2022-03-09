<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务流程管理</title>
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
		<li class="active"><a href="${ctx}/pds/business/businessProcess/">业务流程列表</a></li>
		<shiro:hasPermission name="pds:business:businessProcess:edit"><li><a href="${ctx}/pds/business/businessProcess/form">业务流程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="businessProcess" action="${ctx}/pds/business/businessProcess/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易单号：</label>
				<form:input path="tradeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>流程阶段：</label>
				<form:select path="processStage" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('process_stage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>操作员：</label>
				<form:select path="operator" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易单号</th>
				<th>流程阶段</th>
				<th>图片组</th>
				<th>操作员</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:business:businessProcess:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="businessProcess">
			<tr>
				<td><a href="${ctx}/pds/business/businessProcess/form?id=${businessProcess.id}">
					${businessProcess.tradeId}
				</a></td>
				<td>
					${fns:getDictLabel(businessProcess.processStage, 'process_stage', '')}
				</td>
				<td>
					${businessProcess.images}
				</td>
				<td>
					${fns:getDictLabel(businessProcess.operator, '', '')}
				</td>
				<td>
					${businessProcess.status}
				</td>
				<td>
					<fmt:formatDate value="${businessProcess.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${businessProcess.remarks}
				</td>
				<shiro:hasPermission name="pds:business:businessProcess:edit"><td>
    				<a href="${ctx}/pds/business/businessProcess/form?id=${businessProcess.id}">修改</a>
					<a href="${ctx}/pds/business/businessProcess/delete?id=${businessProcess.id}" onclick="return confirmx('确认要删除该业务流程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>