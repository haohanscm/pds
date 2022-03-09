<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺服务小区管理</title>
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
            $("#shopName").val("");
            $("#districtAreaName").val("");
            $("#communityName").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/manage/merchant/delivery/serviceDistrict/list">店铺服务小区列表</a></li>
		<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><li><a href="${ctx}/xiaodian/manage/merchant/delivery/serviceDistrict/form">店铺服务小区添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopServiceDistrict" action="${ctx}/xiaodian/manage/merchant/delivery/serviceDistrict/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>店铺名称：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>片区名称：</label>
				<form:input path="districtAreaName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>小区名称：</label>
				<form:input path="communityName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
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
				<th>店铺名称</th>
				<th>商家名称</th>
				<th>服务片区名称</th>
				<th>服务小区名称</th>
				<%--<th>状态</th>--%>
				<th>更新时间</th>
				<%--<th>备注信息</th>--%>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopServiceDistrict">
			<tr>
				<td><a href="${ctx}/xiaodian/manage/merchant/delivery/serviceDistrict/form?id=${shopServiceDistrict.id}">
					${shopServiceDistrict.shopName}
				</a></td>
				<td>
					${shopServiceDistrict.merchantName}
				</td>
				<td>
					${shopServiceDistrict.districtAreaName}
				</td>
				<td>
					${shopServiceDistrict.communityName}
				</td>
				<%--<td>--%>
					<%--${fns:getDictLabel(shopServiceDistrict.status, 'common_status', '')}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${shopServiceDistrict.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${shopServiceDistrict.remarks}--%>
				<%--</td>--%>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><td>
    				<a href="${ctx}/xiaodian/manage/merchant/delivery/serviceDistrict/form?id=${shopServiceDistrict.id}">修改</a>
					<a href="${ctx}/xiaodian/manage/merchant/delivery/serviceDistrict/delete?id=${shopServiceDistrict.id}" onclick="return confirmx('确认要删除该店铺服务小区吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>