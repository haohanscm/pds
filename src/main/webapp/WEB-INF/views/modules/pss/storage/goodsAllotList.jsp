<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品调拨管理</title>
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
		<li class="active"><a href="${ctx}/pss/storage/goodsAllot/">商品调拨列表</a></li>
		<shiro:hasPermission name="pss:storage:goodsAllot:edit"><li><a href="${ctx}/pss/storage/goodsAllot/form">商品调拨添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsAllot" action="${ctx}/pss/storage/goodsAllot/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>调拨编号：</label>
				<form:input path="allotNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>调出类型：</label>
				<form:select path="allotoutType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('store_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>调出方ID：</label>
				<form:select path="allotoutId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>调入方ID：</label>
				<form:select path="allotinId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>调入类型：</label>
				<form:select path="allotinType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('store_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>操作员：</label>
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>审核状态：</label>
				<form:select path="auditStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单状态：</label>
				<form:select path="orderStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<%--<th>商家ID</th>--%>
				<th>调拨编号</th>
				<th>商品数量</th>
				<th>总金额</th>
				<th>调出类型</th>
				<th>调出ID</th>
				<th>调入类型</th>
				<th>调入ID</th>
				<th>操作备注</th>
				<th>操作员</th>
				<th>审核状态</th>
				<th>订单状态</th>
				<th>审核人</th>
				<th>制单人</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="pss:storage:goodsAllot:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsAllot">
			<tr>
				<%--<td><a href="${ctx}/pss/storage/goodsAllot/form?id=${goodsAllot.id}">--%>
					<%--${goodsAllot.merchantId}--%>
				<%--</a></td>--%>
				<td><a href="${ctx}/pss/storage/goodsAllot/form?id=${goodsAllot.id}">
					${goodsAllot.allotNum}
				</a></td>
				<td>
					${goodsAllot.num}
				</td>
				<td>
					${goodsAllot.totalAmount}
				</td>
				<td>
					${fns:getDictLabel(goodsAllot.allotoutType, 'store_type', '')}
				</td>
				<td>
					${goodsAllot.allotoutId}
				</td>
				<td>
					${fns:getDictLabel(goodsAllot.allotinType, 'store_type', '')}
				</td>
				<td>
					${goodsAllot.allotinId}
				</td>
				<td>
					${goodsAllot.oprateNode}
				</td>
				<td>
					${goodsAllot.operator}
				</td>
				<td>
					${fns:getDictLabel(goodsAllot.auditStatus, 'audit_status', '')}
				</td>
				<td>
					${fns:getDictLabel(goodsAllot.orderStatus, 'order_status', '')}
				</td>
				<td>
					${goodsAllot.auditMan}
				</td>
				<td>
					${goodsAllot.bizMan}
				</td>
				<td>
					<fmt:formatDate value="${goodsAllot.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${goodsAllot.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="pss:storage:goodsAllot:edit"><td>
					<a href="${ctx}/pss/storage/goodsAllot/form?id=${goodsAllot.id}">修改</a>
					<a href="${ctx}/pss/storage/goodsAllot/delete?id=${goodsAllot.id}" onclick="return confirmx('确认要删除该商品订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>