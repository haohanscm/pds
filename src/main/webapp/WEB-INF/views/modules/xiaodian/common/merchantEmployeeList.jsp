<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工管理管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/common/merchantEmployee/">员工管理列表</a></li>
		<shiro:hasPermission name="xiaodian:common:merchantEmployee:edit"><li><a href="${ctx}/xiaodian/common/merchantEmployee/form">员工管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantEmployee" action="${ctx}/xiaodian/common/merchantEmployee/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="10" class="input-medium"  placeholder="可模糊查询"/>
			</li>
            <li><label>员工名称：</label>
                <form:input path="name" htmlEscape="false" maxlength="10" class="input-medium"  placeholder="可模糊查询"/>
            </li>
			<li><label>手机号：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>注册日期：</label>
				<input name="beginRegDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantEmployee.beginRegDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endRegDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantEmployee.endRegDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>角色：</label>
                <form:select path="role" class="input-medium ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('merchant_employee_role')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>是否启用：</label>
                <form:select path="status" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>手机号</th>
				<th>名称</th>
				<th>商家</th>
				<th>平台商家</th>
				<th>绑定微信</th>
				<th>角色</th>
				<th>注册日期</th>
				<th>来源</th>
				<th>是否启用</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:common:merchantEmployee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantEmployee" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/xiaodian/common/merchantEmployee/form?id=${merchantEmployee.id}">
					${merchantEmployee.telephone}
				</a></td>
				<td>
					${merchantEmployee.name}
				</td>
				<td>
					${merchantEmployee.merchantName}
				</td>
				<td>
					${merchantEmployee.pmName}
				</td>
				<td>
                    <c:if test="${empty merchantEmployee.passportId}"	>否</c:if>
                    <c:if test="${not empty merchantEmployee.passportId}">是</c:if>
				</td>
				<td>
					${fns:getDictLabel(merchantEmployee.role, 'merchant_employee_role', '')}
				</td>
				<td>
					<fmt:formatDate value="${merchantEmployee.regDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(merchantEmployee.origin, 'employee_reg_from', '')}
				</td>
				<td>
					${fns:getDictLabel(merchantEmployee.status, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${merchantEmployee.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${merchantEmployee.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:common:merchantEmployee:edit"><td>
    				<a href="${ctx}/xiaodian/common/merchantEmployee/form?id=${merchantEmployee.id}">修改</a>
					<a href="${ctx}/xiaodian/common/merchantEmployee/delete?id=${merchantEmployee.id}" onclick="return confirmx('确认要删除该员工管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>