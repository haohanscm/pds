<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送信息管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/delivery/deliveryAddress/">配送信息列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:deliveryAddress:edit"><li><a href="${ctx}/xiaodian/delivery/deliveryAddress/form">配送信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deliveryAddress" action="${ctx}/xiaodian/delivery/deliveryAddress/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>通行证id：</label>
				<form:input path="uuid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>收货人：</label>
				<form:input path="receiver" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>收货人手机：</label>
				<form:input path="receiverMobile" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>通行证id</th>
				<th>即速应用ID</th>
				<th>即速地址ID</th>
				<th>省份</th>
				<th>城市</th>
				<th>区县</th>
				<th>片区</th>
				<th>收货小区</th>
				<th>补充地址</th>
				<th>收货人</th>
				<th>收货人手机</th>
				<th>是否默认</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:deliveryAddress:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deliveryAddress">
			<tr>
				<td><a href="${ctx}/xiaodian/delivery/deliveryAddress/form?id=${deliveryAddress.id}">
					${deliveryAddress.uuid}
				</a></td>
				<td>
					${deliveryAddress.jsAppId}
				</td>
				<td>
					${deliveryAddress.jsAddressId}
				</td>
				<td>
						${deliveryAddress.provinceName}
				</td>
				<td>
						${deliveryAddress.cityName}
				</td>
				<td>
						${deliveryAddress.regionName}
				</td>
				<%--<td>--%>
						<%--${fns:getTreeDictName("01", deliveryAddress.province)}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${fns:getTreeDictName("01", deliveryAddress.city)}--%>
				<%--</td>--%>
				<%--<td>--%>
						<%--${fns:getTreeDictName("01", deliveryAddress.region)}--%>
				<%--</td>--%>
				<td>
						${deliveryAddress.districtAreaName}
				</td>
				<td>
						${deliveryAddress.communityName}
				</td>
				<td>
						${deliveryAddress.extAddress}
				</td>
				<td>
					${deliveryAddress.receiver}
				</td>
				<td>
					${deliveryAddress.receiverMobile}
				</td>
				<td>
					${fns:getDictLabel(deliveryAddress.isDefault, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${deliveryAddress.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${deliveryAddress.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:delivery:deliveryAddress:edit"><td>
    				<a href="${ctx}/xiaodian/delivery/deliveryAddress/form?id=${deliveryAddress.id}">修改</a>
					<a href="${ctx}/xiaodian/delivery/deliveryAddress/delete?id=${deliveryAddress.id}" onclick="return confirmx('确认要删除该配送信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>