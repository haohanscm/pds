<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易成本明细管理</title>
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
		<li class="active"><a href="${ctx}/pds/cost/dealCostDetail/">交易成本明细列表</a></li>
		<shiro:hasPermission name="pds:cost:dealCostDetail:edit"><li><a href="${ctx}/pds/cost/dealCostDetail/form">交易成本明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dealCostDetail" action="${ctx}/pds/cost/dealCostDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易日：</label>
				<form:input path="dealDate" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>平台商家ID：</label>
				<form:input path="pmId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>成本编号：</label>
				<form:input path="costNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易日</th>
				<th>平台商家ID</th>
				<th>成本编号</th>
				<th>名称</th>
				<th>数量</th>
				<th>金额</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:cost:dealCostDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dealCostDetail">
			<tr>
				<td><a href="${ctx}/pds/cost/dealCostDetail/form?id=${dealCostDetail.id}">
					${dealCostDetail.dealDate}
				</a></td>
				<td>
					${dealCostDetail.pmId}
				</td>
				<td>
					${dealCostDetail.costNo}
				</td>
				<td>
					${dealCostDetail.name}
				</td>
				<td>
					${dealCostDetail.number}
				</td>
				<td>
					${dealCostDetail.amount}
				</td>
				<td>
					<fmt:formatDate value="${dealCostDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${dealCostDetail.remarks}
				</td>
				<shiro:hasPermission name="pds:cost:dealCostDetail:edit"><td>
    				<a href="${ctx}/pds/cost/dealCostDetail/form?id=${dealCostDetail.id}">修改</a>
					<a href="${ctx}/pds/cost/dealCostDetail/delete?id=${dealCostDetail.id}" onclick="return confirmx('确认要删除该交易成本明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>