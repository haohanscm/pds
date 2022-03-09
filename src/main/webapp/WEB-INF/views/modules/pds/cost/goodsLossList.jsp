<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品损耗管理</title>
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
		<li class="active"><a href="${ctx}/pds/cost/goodsLoss/">商品损耗列表</a></li>
		<shiro:hasPermission name="pds:cost:goodsLoss:edit"><li><a href="${ctx}/pds/cost/goodsLoss/form">商品损耗添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsLoss" action="${ctx}/pds/cost/goodsLoss/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>商品ID：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<%--<li><label>商品规格：</label>--%>
				<%--<form:input path="goodsModel" htmlEscape="false" maxlength="64" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>商品规格：</label>
				<form:input path="goodsModel" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>平台商家</th>
				<th>商品ID</th>
				<th>商品规格</th>
				<th>损耗百分比</th>
				<th>损耗阀值</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:cost:goodsLoss:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsLoss">
			<tr>
				<td><a href="${ctx}/pds/cost/goodsLoss/form?id=${goodsLoss.id}">
					${goodsLoss.pmName}
				</a></td>
				<td>
					${goodsLoss.goodsId}
				</td>
				<td>
					${goodsLoss.goodsModel}
				</td>
				<td>
					${goodsLoss.lossPercent}
				</td>
				<td>
					${goodsLoss.lossLimit}
				</td>
				<td>
					<fmt:formatDate value="${goodsLoss.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsLoss.remarks}
				</td>
				<shiro:hasPermission name="pds:cost:goodsLoss:edit"><td>
    				<a href="${ctx}/pds/cost/goodsLoss/form?id=${goodsLoss.id}">修改</a>
					<a href="${ctx}/pds/cost/goodsLoss/delete?id=${goodsLoss.id}" onclick="return confirmx('确认要删除该商品损耗吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>