<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送服务区域管理</title>
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
            $("#deliverManName").val("");
            $("#communityName").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/delivery/deliverServiceArea/">配送服务区域列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:deliverServiceArea:edit"><li><a href="${ctx}/xiaodian/delivery/deliverServiceArea/form">配送服务区域添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deliverServiceArea" action="${ctx}/xiaodian/delivery/deliverServiceArea/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家id：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺id：</label>
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>配送员ID：</label>
				<form:input path="deliverManId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>服务小区ID：</label>
				<form:input path="communityId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺名称：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>配送员名称：</label>
				<form:input path="deliverManName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
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
				<th>配送员名称</th>
				<th>店铺</th>
				<th>商家</th>
				<th>服务小区名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:deliverServiceArea:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deliverServiceArea">
			<tr>
				<td><a href="${ctx}/xiaodian/delivery/deliverServiceArea/form?id=${deliverServiceArea.id}">
					${deliverServiceArea.deliverManName}
				</a></td>
				<td>
					${deliverServiceArea.shopName}
				</td>
				<td>
					${deliverServiceArea.merchantName}
				</td>
				<td>
					${deliverServiceArea.communityName}
				</td>
				<td>
					<fmt:formatDate value="${deliverServiceArea.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${deliverServiceArea.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:delivery:deliverServiceArea:edit"><td>
    				<a href="${ctx}/xiaodian/delivery/deliverServiceArea/form?id=${deliverServiceArea.id}">修改</a>
					<a href="${ctx}/xiaodian/delivery/deliverServiceArea/delete?id=${deliverServiceArea.id}" onclick="return confirmx('确认要删除该配送服务区域吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>