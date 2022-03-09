<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算记录管理</title>
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
		<li class="active"><a href="${ctx}/pds/cost/settlementRecord/">结算记录列表</a></li>
		<shiro:hasPermission name="pds:cost:settlementRecord:edit"><li><a href="${ctx}/pds/cost/settlementRecord/form">结算记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="settlementRecord" action="${ctx}/pds/cost/settlementRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label style="width: 100px">结算单号：</label>
                <form:input path="settlementId" htmlEscape="false" maxlength="64" class="input-medium"/>
            </li>
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>结算类型：</label>
				<form:select path="settlementType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_settlement_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>付款日期：</label>
				<input name="beginPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settlementRecord.beginPayDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settlementRecord.endPayDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label style="width: 100px">结算公司类型：</label>
				<form:select path="companyType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width: 100px">结算公司名称：</label>
				<form:input path="companyName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>结款人名称：</label>
				<form:input path="companyOperator" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>经办人名称：</label>
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>结算单号</th>
				<th>结算公司名称</th>
				<th>平台商家</th>
				<th>结算公司类型</th>
				<th>结算类型</th>
				<th>结算金额</th>
				<th>结算开始日期</th>
				<th>结算结束日期</th>
				<th>付款日期</th>
				<th>结款人名称</th>
				<%--<th>结算说明</th>--%>
				<th>经办人名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:cost:settlementRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settlementRecord" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/cost/settlementRecord/form?id=${settlementRecord.id}">
					${settlementRecord.settlementId}
				</a></td>
                <td>
                        ${settlementRecord.companyName}
                </td>
                <td>
                        ${settlementRecord.pmName}
                </td>
				<td>
					${fns:getDictLabel(settlementRecord.companyType, 'pds_company_type', '')}
				</td>
				<td>
					${fns:getDictLabel(settlementRecord.settlementType, 'pds_settlement_type', '')}
				</td>
				<td>
					${settlementRecord.settlementAmount}
				</td>
				<td>
					<fmt:formatDate value="${settlementRecord.settlementBeginDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${settlementRecord.settlementEndDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${settlementRecord.payDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${settlementRecord.companyOperator}
				</td>
				<%--<td>--%>
					<%--${settlementRecord.settlementDesc}--%>
				<%--</td>--%>
				<td>
					${settlementRecord.operator}
				</td>
				<td>
					<fmt:formatDate value="${settlementRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${settlementRecord.remarks}
				</td>
				<shiro:hasPermission name="pds:cost:settlementRecord:edit"><td>
    				<a href="${ctx}/pds/cost/settlementRecord/form?id=${settlementRecord.id}">修改</a>
					<a href="${ctx}/pds/cost/settlementRecord/delete?id=${settlementRecord.id}" onclick="return confirmx('确认要删除该结算记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>