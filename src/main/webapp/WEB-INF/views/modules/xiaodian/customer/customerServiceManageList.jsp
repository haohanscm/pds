<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务管理管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/customer/customerServiceManage/">服务管理列表</a></li>
		<shiro:hasPermission name="xiaodian:customer:customerServiceManage:edit"><li><a href="${ctx}/xiaodian/customer/customerServiceManage/form">服务管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerServiceManage" action="${ctx}/xiaodian/customer/customerServiceManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>商家名称：</label>
                <form:input path="merchant.merchantName" htmlEscape="false" maxlength="50" class="input-medium"/>
            </li>
			<li><label>服务类型：</label>
				<form:select path="serviceType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>服务时间：</label>
				<input name="beginServiceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerServiceManage.beginServiceTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endServiceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerServiceManage.endServiceTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>支付方式：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>业务专管员：</label>
				<sys:treeselect id="bizUser" name="bizUser.id" value="${customerProjectManage.bizUser.id}" labelName="bizUser.name" labelValue="${customerProjectManage.bizUser.name}"
								title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>运营专管员：</label>
				<sys:treeselect id="opUser" name="opUser.id" value="${customerProjectManage.opUser.id}" labelName="opUser.name" labelValue="${customerProjectManage.opUser.name}"
								title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>支付时间：</label>
				<input name="beginPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerServiceManage.beginPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerServiceManage.endPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商家名称</th>
				<th>服务类型</th>
				<th>服务时间</th>
				<th>支付方式</th>
				<th>业务专管员</th>
				<th>服务状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:customer:customerServiceManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerServiceManage">
			<tr>
				<td><a href="${ctx}/xiaodian/customer/customerServiceManage/form?id=${customerServiceManage.id}">
					${customerServiceManage.merchant.merchantName}
                </a></td>
				<td>
					${fns:getDictLabel(customerServiceManage.serviceType, 'customer_service_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerServiceManage.serviceTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(customerServiceManage.payType, 'product_pay_type', '')}
				</td>
				<td>
					${customerServiceManage.bizUser.name}
				</td>
				<td>
					${customerServiceManage.serviceStatus}
				</td>
				<td>
					<fmt:formatDate value="${customerServiceManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerServiceManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:customer:customerServiceManage:edit"><td>
    				<a href="${ctx}/xiaodian/customer/customerServiceManage/form?id=${customerServiceManage.id}">修改</a>
					<a href="${ctx}/xiaodian/customer/customerServiceManage/delete?id=${customerServiceManage.id}" onclick="return confirmx('确认要删除该服务管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>