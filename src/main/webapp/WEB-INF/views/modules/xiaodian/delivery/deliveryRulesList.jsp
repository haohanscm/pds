<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/xiaodian/delivery/deliveryRules/">配送规则列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:deliveryRules:edit"><li><a href="${ctx}/xiaodian/delivery/deliveryRules/form">配送规则添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deliveryRules" action="${ctx}/xiaodian/delivery/deliveryRules/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品id：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>配送周期：</label>
				<form:select path="deliverySchedule" class="input-small ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送时效：</label>
				<form:select path="arriveType" class="input-small ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_arrive')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
            <li><label>配送类型：</label>
                <form:select path="deliveryPlanType" class="input-small ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
            <li><label>配送方式：</label>
                <form:select path="deliveryType" class="input-small ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
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
				<th>配送周期</th>
				<th>配送时效</th>
				<th>每次配送数量</th>
				<th>起送数量</th>
				<th>配送计划类型</th>
				<th>配送方式</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:deliveryRules:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deliveryRules">
			<tr>
				<td><a href="${ctx}/xiaodian/delivery/deliveryRules/form?id=${deliveryRules.id}">
					${deliveryRules.goodsId}
				</a></td>
				<td id="scheduleNames">
					<%--${fns:getDictLabel(deliveryRules.deliverySchedule, 'delivery_schedule', '')}--%>
					${deliveryRules.deliverySchedule}
				</td>
				<td>
					${fns:getDictLabel(deliveryRules.arriveType, 'delivery_arrive', '')}
				</td>
				<td>
					${deliveryRules.deliveryNum}
				</td>
				<td>
					${deliveryRules.minNum}
				</td>
				<td>
					${fns:getDictLabel(deliveryRules.deliveryPlanType,'delivery_plan_type' ,'' )}
				</td>
				<td>
					<%--${fns:getDictLabel(deliveryRules.deliveryType,'delivery_type' ,'' )}--%>
					${deliveryRules.deliveryType}
				</td>
				<td>
					<fmt:formatDate value="${deliveryRules.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${deliveryRules.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:delivery:deliveryRules:edit"><td>
    				<a href="${ctx}/xiaodian/delivery/deliveryRules/form?id=${deliveryRules.id}">修改</a>
					<a href="${ctx}/xiaodian/delivery/deliveryRules/delete?id=${deliveryRules.id}" onclick="return confirmx('确认要删除该配送规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>