<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送员管理</title>
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
            $("#realName").val("");
            $("#mobile").val("");
            $("#shopName").val("");
            $("#level").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/manage/merchant/delivery/man/list">配送员列表</a></li>
		<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><li><a href="${ctx}/xiaodian/manage/merchant/delivery/man/form">配送员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deliverManManage" action="${ctx}/xiaodian/manage/merchant/delivery/man/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="realName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>店铺ID：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>星级：</label>
				<form:input path="level" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<%--<li><label>状态：</label>--%>
				<%--<form:select path="status" class="input-medium">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th>编号</th>--%>
				<th>姓名</th>
				<th>手机号</th>
				<th>昵称</th>
				<th>所属店铺</th>
				<th>星级</th>
				<th>总配送次数</th>
				<th>状态</th>
				<th>绑定微信</th>
				<th>更新时间</th>
				<%--<th>备注信息</th>--%>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deliverManManage">
			<tr>
				<%--<td>--%>
					<%--${deliverManManage.id}--%>
				<%--</td>--%>
				<td><a href="${ctx}/xiaodian/manage/merchant/delivery/man/form?id=${deliverManManage.id}">
					${deliverManManage.realName}
				</a></td>
				<td>
					${deliverManManage.mobile}
				</td>
				<td>
					${deliverManManage.nickName}
				</td>
				<td>
					${deliverManManage.shopName}
				</td>
				<td>
					${deliverManManage.level}
				</td>
				<td>
					${deliverManManage.totalDeliveryTimes}
				</td>
				<td>
					${fns:getDictLabel(deliverManManage.status, 'common_status', '')}
				</td>
                <td>
                    <c:if test="${empty deliverManManage.uid}"	>否</c:if>
                    <c:if test="${not empty deliverManManage.uid}">是</c:if>
                </td>
				<td>
					<fmt:formatDate value="${deliverManManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${deliverManManage.remarks}--%>
				<%--</td>--%>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><td>
    				<a href="${ctx}/xiaodian/manage/merchant/delivery/man/form?id=${deliverManManage.id}">修改</a>
					<%--<a href="${ctx}/xiaodian/manage/merchant/delivery/man/delete?id=${deliverManManage.id}" onclick="return confirmx('确认要删除该配送员吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>