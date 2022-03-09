<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核管理管理</title>
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
        // 清空查询条件
        function toReset(){
            $("#evaluateTime").val("");
            $("#officeId").val("");
            $("#officeName").val("");
            $("#position").select2('val',"");
            $("#userId").val("");
            $("#userName").val("");
            $("#evaluateLevel").select2('val',"");
            $("#createById").val("");
            $("#createByName").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/office/companyPerformance/">绩效考核列表</a></li>
		<shiro:hasPermission name="xiaodian:office:companyPerformance:edit"><li><a href="${ctx}/xiaodian/office/companyPerformance/form">绩效考核添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="companyPerformance" action="${ctx}/xiaodian/office/companyPerformance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>考核时间：</label>
				<form:input path="evaluateTime" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>考核评级：</label>
                <form:select path="evaluateLevel" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('evaluate_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>岗位：</label>
				<form:select path="position" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('job_list')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
        </ul>
        <ul class="ul-form">
			<li><label>姓名：</label>
				<sys:treeselect id="user" name="user.id" value="${companyPerformance.user.id}" labelName="user.name" labelValue="${companyPerformance.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
            <li><label>部门：</label>
                <sys:treeselect id="office" name="office.id" value="${companyPerformance.office.id}" labelName="office.name" labelValue="${companyPerformance.office.name}"
                                title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
            </li>
            <li><label>考评人姓名：</label>
                <sys:treeselect id="createBy" name="createBy.id" value="${companyPerformance.createBy.id}" labelName="createBy.name" labelValue="${companyPerformance.createBy.name}"
                     title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>考核时间</th>
				<th>考核区间</th>
				<th>部门</th>
				<th>岗位</th>
				<th>姓名</th>
				<th>考核评级</th>
                <th>考评创建人</th>
                <th>最后修改人</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:office:companyPerformance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="companyPerformance">
			<tr>
				<td><a href="${ctx}/xiaodian/office/companyPerformance/form?id=${companyPerformance.id}">
					${companyPerformance.evaluateTime}
				</a></td>
				<td>
					${companyPerformance.evaluateWeek}
				</td>
				<td>
					${companyPerformance.office.name}
				</td>
				<td>
					${fns:getDictLabel(companyPerformance.position, 'job_list', '')}
				</td>
				<td>
					${companyPerformance.user.name}
				</td>
				<td>
					${fns:getDictLabel(companyPerformance.evaluateLevel, 'evaluate_level', '')}
				</td>
                <td>
                    ${companyPerformance.createBy.name}
                </td>
				<td>
					${companyPerformance.updateBy.name}
				</td>
				<td>
					<fmt:formatDate value="${companyPerformance.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:office:companyPerformance:edit"><td>
    				<a href="${ctx}/xiaodian/office/companyPerformance/form?id=${companyPerformance.id}">修改</a>
					<a href="${ctx}/xiaodian/office/companyPerformance/delete?id=${companyPerformance.id}" onclick="return confirmx('确认要删除该绩效考核管理吗？', this.href)">删除</a>
					<a href="${ctx}/xiaodian/office/companyPerformance/add?id=${companyPerformance.id}">新增考核</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>