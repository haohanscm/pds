<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成本管控管理</title>
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
		<li class="active"><a href="${ctx}/pds/cost/costControl/">成本管控列表</a></li>
		<shiro:hasPermission name="pds:cost:costControl:edit"><li><a href="${ctx}/pds/cost/costControl/form">成本管控添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="costControl" action="${ctx}/pds/cost/costControl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家ID：</label>
				<form:input path="pmId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>成本编号：</label>
				<form:input path="costId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>成本名称：</label>
				<form:input path="costName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>成本分类：</label>
				<form:input path="costType" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>平台商家ID</th>
				<th>成本编号</th>
				<th>成本名称</th>
				<th>成本分类</th>
				<th>计算单位</th>
				<th>管控阀值</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:cost:costControl:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="costControl">
			<tr>
				<td><a href="${ctx}/pds/cost/costControl/form?id=${costControl.id}">
					${costControl.pmId}
				</a></td>
				<td>
					${costControl.costId}
				</td>
				<td>
					${costControl.costName}
				</td>
				<td>
					${costControl.costType}
				</td>
				<td>
					${costControl.countUnit}
				</td>
				<td>
					${costControl.controlLimit}
				</td>
				<td>
					${costControl.status}
				</td>
				<td>
					<fmt:formatDate value="${costControl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${costControl.remarks}
				</td>
				<shiro:hasPermission name="pds:cost:costControl:edit"><td>
    				<a href="${ctx}/pds/cost/costControl/form?id=${costControl.id}">修改</a>
					<a href="${ctx}/pds/cost/costControl/delete?id=${costControl.id}" onclick="return confirmx('确认要删除该成本管控吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>