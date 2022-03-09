<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购商管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pds/business/pdsBuyer/">采购商列表</a></li>
		<shiro:hasPermission name="pds:business:pdsBuyer:edit"><li><a href="${ctx}/pds/business/pdsBuyer/form">采购商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pdsBuyer" action="${ctx}/pds/business/pdsBuyer/" method="post" class="breadcrumb form-search">
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
                <form:select path="merchantId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${merchantList}" itemLabel="merchantName" itemValue="merchantId" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>采购商全称：</label>
				<form:input path="buyerName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>采购商简称：</label>
				<form:input path="shortName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>通行证ID：</label>
				<form:input path="passportId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="13" class="input-medium"/>
			</li>
            <li><label>采购商类型：</label>
                <form:select path="buyerType" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pds_buyer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label>需确认订单：</label>
                <form:select path="needConfirmation" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label style="width: 100px">是否绑定微信：</label>
                <form:select path="bindStatus" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label style="width: 100px">需推送消息：</label>
                <form:select path="needPush" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label style="width: 100px">启用状态：</label>
                <form:select path="status" class="input-small">
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
				<th>采购商全称</th>
				<th>采购商简称</th>
				<th>商家</th>
				<th>平台商家</th>
				<th>采购商类型</th>
				<th>联系人</th>
				<th>联系电话</th>
				<th>账期</th>
				<th>需确认订单</th>
				<th>绑定微信</th>
				<th>推送消息</th>
				<th>启用状态</th>
				<th>排序值</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:business:pdsBuyer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pdsBuyer" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/business/pdsBuyer/form?id=${pdsBuyer.id}">
					${pdsBuyer.buyerName}
				</a></td>
				<td>
					${pdsBuyer.shortName}
				</td>
				<td>
					${pdsBuyer.merchantName}
				</td>
				<td>
					${pdsBuyer.pmName}
				</td>
				<td>
                        ${fns:getDictLabel(pdsBuyer.buyerType, 'pds_buyer_type', '')}
				</td>
				<td>
					${pdsBuyer.contact}
				</td>
				<td>
					${pdsBuyer.telephone}
				</td>
				<td>
                    ${fns:getDictLabel(pdsBuyer.payPeriod, 'pds_pay_period', '')}
				</td>
				<td>
                    ${fns:getDictLabel(pdsBuyer.needConfirmation, 'yes_no', '')}
				</td>
				<td>
					<c:if test="${empty pdsBuyer.passportId}"	>否</c:if>
					<c:if test="${not empty pdsBuyer.passportId}">是</c:if>
				</td>
                <td>
                        ${fns:getDictLabel(pdsBuyer.needPush, 'yes_no', '')}
                </td>
                <td>
                        ${fns:getDictLabel(pdsBuyer.status, 'yes_no', '')}
                </td>
                <td>
                        ${pdsBuyer.sort}
                </td>
				<td>
					<fmt:formatDate value="${pdsBuyer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pdsBuyer.remarks}
				</td>
				<shiro:hasPermission name="pds:business:pdsBuyer:edit"><td>
    				<a href="${ctx}/pds/business/pdsBuyer/form?id=${pdsBuyer.id}">修改</a>
					<a href="${ctx}/pds/business/pdsBuyer/delete?id=${pdsBuyer.id}" onclick="return confirmx('确认要删除该采购商吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>