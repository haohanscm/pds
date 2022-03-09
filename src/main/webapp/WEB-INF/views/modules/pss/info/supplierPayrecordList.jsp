<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商结款记录管理</title>
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
		<li class="active"><a href="${ctx}/pss/info/supplierPayrecord/">供应商结款记录列表</a></li>
		<shiro:hasPermission name="pss:info:supplierPayrecord:edit"><li><a href="${ctx}/pss/info/supplierPayrecord/form">供应商结款记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplierPayrecord" action="${ctx}/pss/info/supplierPayrecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>供应商ID：</label>
				<form:input path="supplierId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>还款时间：</label>
				<input name="payTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${supplierPayrecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商家ID</th>
				<th>供应商ID</th>
				<th>结算款金额</th>
				<th>还款时间</th>
				<th>备注信息</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:info:supplierPayrecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplierPayrecord">
			<tr>
				<td><a href="${ctx}/pss/info/supplierPayrecord/form?id=${supplierPayrecord.id}">
					${supplierPayrecord.merchantId}
				</a></td>
				<td>
					${supplierPayrecord.supplierId}
				</td>
				<td>
					${supplierPayrecord.payAmount}
				</td>
				<td>
					<fmt:formatDate value="${supplierPayrecord.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${supplierPayrecord.payNote}
				</td>
				<td>
					<fmt:formatDate value="${supplierPayrecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${supplierPayrecord.remarks}
				</td>
				<shiro:hasPermission name="pss:info:supplierPayrecord:edit"><td>
    				<a href="${ctx}/pss/info/supplierPayrecord/form?id=${supplierPayrecord.id}">修改</a>
					<a href="${ctx}/pss/info/supplierPayrecord/delete?id=${supplierPayrecord.id}" onclick="return confirmx('确认要删除该供应商结款记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>