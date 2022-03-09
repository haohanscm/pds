<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺管理</title>
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
        // 清空查询条件
        function toReset(){
            $("#name").val("");
            $("#merchantName").val("");
            $("#status").select2('val', "");
            $("#agentStatus").select2('val', "");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/manage/platform/shop/list">店铺列表</a></li>
		<%--<shiro:hasPermission name="xiaodian:manage:shop:edit"><li><a href="${ctx}/xiaodian/manage/shop/form">店铺添加</a></li></shiro:hasPermission>--%>
	</ul>
<div <c:if test="${empty shopExt.agentId}">style="display: none"</c:if> >
	<form:form id="searchForm" modelAttribute="shopExt" action="${ctx}/xiaodian/manage/platform/shop/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>即速应用ID：</label>--%>
				<%--<form:input path="code" htmlEscape="false" maxlength="50" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>店铺名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<%--<li><label>店铺负责人：</label>--%>
				<%--<form:input path="manager" htmlEscape="false" maxlength="100" class="input-medium" placeholder="可模糊查询"/>--%>
			<%--</li>--%>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="50" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>启用状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('status_merchant')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
                <li><label>审核状态：</label>
                    <form:select path="agentStatus" class="input-medium">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('agent_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
</div>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>店铺名称</th>
				<th>商家名称</th>
                <th>店铺负责人</th>
                <th>店铺级别</th>
                <th>店铺启用状态</th>
                <th>审核状态</th>
				<th>更新时间</th>
				<%--<th>备注信息</th>--%>
				<%--<shiro:hasPermission name="xiaodian:manage:shop:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
        <c:if test="${empty page.list}">没有店铺数据</c:if>
		<c:forEach items="${page.list}" var="shop">
			<tr>
				<td>
					${shop.name}
				</td>
				<td>
					${shop.merchantName}
				</td>
				<td>
					${shop.manager}
				</td>
                <td>
                        ${fns:getDictLabel(shop.shopLevel,'shop_level','')}
                </td>
				<td>
					${fns:getDictLabel(shop.status, 'status_merchant', '')}
				</td>
                <td>
                        ${fns:getDictLabel(shop.agentStatus, 'agent_status', '')}
                </td>
				<td>
					<fmt:formatDate value="${shop.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<shiro:hasPermission name="xiaodian:manage:shop:edit"><td>--%>
    				<%--<a href="${ctx}/xiaodian/manage/shop/form?id=${shop.id}">修改</a>--%>
					<%--<a href="${ctx}/xiaodian/manage/shop/delete?id=${shop.id}" onclick="return confirmx('确认要删除该店铺吗？', this.href)">删除</a>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
    <div <c:if test="${empty shopExt.agentId}">style="display: none"</c:if> >
        <div class="pagination">${page}</div>
    </div>
</body>
</html>