<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车辆管理管理</title>
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
		<li class="active"><a href="${ctx}/pds/delivery/truckManage/">车辆管理列表</a></li>
		<shiro:hasPermission name="pds:delivery:truckManage:edit"><li><a href="${ctx}/pds/delivery/truckManage/form">车辆管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="truckManage" action="${ctx}/pds/delivery/truckManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>车辆编号：</label>
				<form:input path="truckNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>车辆品牌：</label>
				<form:input path="brand" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="truckName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>负责人：</label>
				<form:input path="principal" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>司机：</label>
				<form:input path="driver" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_truck_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>车辆编号</th>
				<th>平台商家</th>
				<th>车辆品牌</th>
				<th>名称</th>
				<th>司机</th>
				<th>联系电话</th>
				<th>车辆载重Kg</th>
				<th>车辆容积m3</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:delivery:truckManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="truckManage">
			<tr>
				<td><a href="${ctx}/pds/delivery/truckManage/form?id=${truckManage.id}">
					${truckManage.truckNo}
				</a></td>
				<td>
					${truckManage.pmName}
				</td>
				<td>
					${truckManage.brand}
				</td>
				<td>
					${truckManage.truckName}
				</td>
				<td>
					${truckManage.driver}
				</td>
				<td>
					${truckManage.telephone}
				</td>
				<td>
					${truckManage.carryWeight}
				</td>
				<td>
					${truckManage.carryVolume}
				</td>
				<td>
					${fns:getDictLabel(truckManage.status, 'pds_truck_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${truckManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${truckManage.remarks}
				</td>
				<shiro:hasPermission name="pds:delivery:truckManage:edit"><td>
    				<a href="${ctx}/pds/delivery/truckManage/form?id=${truckManage.id}">修改</a>
					<a href="${ctx}/pds/delivery/truckManage/delete?id=${truckManage.id}" onclick="return confirmx('确认要删除该车辆管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>