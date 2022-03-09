<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>赠品管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/delivery/goodsGifts/">赠品列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:goodsGifts:edit"><li><a href="${ctx}/xiaodian/delivery/goodsGifts/form">赠品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsGifts" action="${ctx}/xiaodian/delivery/goodsGifts/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品id：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>赠品名称：</label>
				<form:input path="giftName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品id</th>
				<th>赠品名称</th>
				<th>赠送规则</th>
				<th>起始日期</th>
				<th>结束日期</th>
				<th>赠送周期</th>
				<th>赠品数量</th>
				<%--<th>是否过期</th>--%>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:goodsGifts:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsGifts">
			<tr>
				<td><a href="${ctx}/xiaodian/delivery/goodsGifts/form?id=${goodsGifts.id}">
					${goodsGifts.goodsId}
				</a></td>
				<td>
					${goodsGifts.giftName}
				</td>
				<td>
					${goodsGifts.giftRule}
				</td>
				<td>
					<fmt:formatDate value="${goodsGifts.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${goodsGifts.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsGifts.giftSchedule}
				</td>
				<td>
					${goodsGifts.giftNum}
				</td>
				<%--<td>--%>
						<%--${fns:getDictLabel(goodsGifts.isExpires, "yes_no", "" )}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${goodsGifts.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsGifts.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:delivery:goodsGifts:edit"><td>
    				<a href="${ctx}/xiaodian/delivery/goodsGifts/form?id=${goodsGifts.id}">修改</a>
					<a href="${ctx}/xiaodian/delivery/goodsGifts/delete?id=${goodsGifts.id}" onclick="return confirmx('确认要删除该赠品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>