<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品采购管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            var _message = "${tip_message}".trim();
            if (_message){
                alertx(_message);
            }
            modelInit();
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
		<li class="active"><a href="${ctx}/pss/procure/procurement/">商品采购列表</a></li>
		<shiro:hasPermission name="pss:procure:procurement:edit"><li><a href="${ctx}/pss/procure/procurement/form">商品采购添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="procurement" action="${ctx}/pss/procure/procurement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家：</label>
				<form:select path="merchantId" class="input-medium">
					<form:option value="" label="查看所有"/>
					<form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>供应商：</label>
				<form:select path="supplierId" class="input-medium">
					<form:option value="" label="查看所有"/>
					<form:options items="${pssSupplierList}" itemLabel="supplierName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>采购编号：</label>
				<form:input path="procureNum" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>采购编号</th>
				<th>商品数量</th>
				<th>总计金额</th>
				<th>实付金额</th>
				<th>合计金额</th>
				<th>其他费用</th>
				<th>结账方式</th>
				<th>业务备注</th>
				<th>操作员</th>
				<th>状态</th>
				<th>操作时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:procure:procurement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="procurement">
			<tr>
				<%--<td><a href="${ctx}/pss/procure/procurement/form?id=${procurement.id}">--%>
					<%--${procurement.merchantId}--%>
				<%--</a></td>--%>
				<td><a href="${ctx}/pss/procure/procurement/form?id=${procurement.id}">
				${procurement.procureNum}
				<a/></td>
				<td>
					${procurement.num}
				</td>
				<td>
					${procurement.totalAmount}
				</td>
				<td>
					${procurement.payAmount}
				</td>
				<td>
					${procurement.sumAmount}
				</td>
				<td>
					${procurement.otherAmount}
				</td>
				<td>
					${procurement.payType}
				</td>
				<td>
					${procurement.bizNote}
				</td>
				<td>
					${procurement.operator}
				</td>
				<td>
					${fns:getDictLabel(procurement.stockStatus, 'stock_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${procurement.opTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${procurement.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${procurement.remarks}
				</td>
				<shiro:hasPermission name="pss:procure:procurement:edit"><td>
					<a href="${ctx}/pss/procure/procurement/enterStock?id=${procurement.id}">入库</a>
    				<a href="${ctx}/pss/procure/procurement/form?id=${procurement.id}">修改</a>
					<a href="${ctx}/pss/procure/procurement/delete?id=${procurement.id}" onclick="return confirmx('确认要删除该商品采购吗？', this.href)">删除</a>
					<a href="${ctx}/pss/procure/procurementDetail/list?procureNum=${procurement.procureNum}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>