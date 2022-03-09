<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小区信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function toReset() {
            $("#name").val("");
            $("#alias").val("");
            $("#street").val("");
            $("#status").select2("val","");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/manage/merchant/delivery/communityManage/list">小区信息列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:communityManage:edit"><li><a href="${ctx}/xiaodian/manage/merchant/delivery/communityManage/form">小区信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="communityManage" action="${ctx}/xiaodian/manage/merchant/delivery/communityManage/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>小区名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>别名：</label>
                <form:input path="alias" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
            </li>
            <li><label>所属街道：</label>
                <form:input path="street" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
            </li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th>主键</th>--%>
				<th>小区名称</th>
				<th>小区别名</th>
                <th>省</th>
                <th>市</th>
                <th>区县</th>
				<th>所属街道</th>
				<th>楼栋数量</th>
				<th>户数</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:communityManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="communityManage">
			<tr>
				<%--<td>--%>
					<%--${communityManage.id}--%>
				<%--</td>--%>
				<td><a href="${ctx}/xiaodian/manage/merchant/delivery/communityManage/form?id=${communityManage.id}">
						${communityManage.name}
				</a></td>
				<td>
					${communityManage.alias}
				</td>
                <td>
                        ${fns:getTreeDictName("01", communityManage.province)}
                </td>
                <td>
                        ${fns:getTreeDictName("01", communityManage.city)}
                </td>
                <td>
                        ${fns:getTreeDictName("01", communityManage.region)}
                </td>
                <td>
					${communityManage.street}
				</td>
				<td>
					${communityManage.buildings}
				</td>
				<td>
					${communityManage.residents}
				</td>
				<td>
					${fns:getDictLabel(communityManage.status, 'common_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${communityManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${communityManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:delivery:communityManage:edit"><td>
    				<a href="${ctx}/xiaodian/manage/merchant/delivery/communityManage/form?id=${communityManage.id}">修改</a>
					<a href="${ctx}/xiaodian/manage/merchant/delivery/communityManage/delete?id=${communityManage.id}" onclick="return confirmx('确认要删除该小区信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>