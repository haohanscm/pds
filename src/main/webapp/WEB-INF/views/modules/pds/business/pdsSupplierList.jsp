<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
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
		<li class="active"><a href="${ctx}/pds/business/pdsSupplier/">供应商列表</a></li>
		<shiro:hasPermission name="pds:business:pdsSupplier:edit"><li><a href="${ctx}/pds/business/pdsSupplier/form">供应商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pdsSupplier" action="${ctx}/pds/business/pdsSupplier/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>商家：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>供应商全称：</label>
				<form:input path="supplierName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>供应商简称：</label>
				<form:input path="shortName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>联系电话：</label>
                <form:input path="telephone" htmlEscape="false" maxlength="13" class="input-medium"/>
            </li>
            <li><label>供应商类型：</label>
                <form:select path="supplierType" class="input-small ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pds_supplier_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>是否启用：</label>
				<form:select path="status" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width: 100px;">是否绑定微信：</label>
				<form:select path="bindStatus" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
            <li><label style="width: 100px;">是否推送消息：</label>
                <form:select path="needPush" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>序号</th>
				<th>供应商全称</th>
				<th>供应商简称</th>
				<th>商家</th>
                <th>平台商家</th>
                <th>供应商类型</th>
				<th>联系人</th>
				<th>联系电话</th>
				<th>操作员</th>
				<th>账期</th>
				<th>是否启用</th>
                <th>绑定微信</th>
                <th>消息推送</th>
                <th>排序值</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:business:pdsSupplier:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pdsSupplier" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/business/pdsSupplier/form?id=${pdsSupplier.id}">
					${pdsSupplier.supplierName}
				</a></td>
				<td>
					${pdsSupplier.shortName}
				</td>
				<td>
					${pdsSupplier.merchantName}
				</td>
                <td>
                    ${pdsSupplier.pmName}
                </td>
                <td>
                    ${fns:getDictLabel(pdsSupplier.supplierType, 'pds_supplier_type', '')}
                </td>
				<td>
					${pdsSupplier.contact}
				</td>
				<td>
					${pdsSupplier.telephone}
				</td>
				<td>
					${pdsSupplier.operator}
				</td>
				<td>
					${fns:getDictLabel(pdsSupplier.payPeriod, 'pds_pay_period', '')}
				</td>
				<td>
					${fns:getDictLabel(pdsSupplier.status, 'yes_no', '')}
				</td>
                <td>
                    <c:if test="${empty pdsSupplier.passportId}"	>否</c:if>
                    <c:if test="${not empty pdsSupplier.passportId}">是</c:if>
                </td>
                <td>
                        ${fns:getDictLabel(pdsSupplier.needPush, 'yes_no', '')}
                </td>
                <td>
                        ${pdsSupplier.sort}
                </td>
				<td>
					<fmt:formatDate value="${pdsSupplier.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pdsSupplier.remarks}
				</td>
				<shiro:hasPermission name="pds:business:pdsSupplier:edit"><td>
    				<a href="${ctx}/pds/business/pdsSupplier/form?id=${pdsSupplier.id}">修改</a>
					<a href="${ctx}/pds/business/pdsSupplier/delete?id=${pdsSupplier.id}" onclick="return confirmx('确认要删除该供应商吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>