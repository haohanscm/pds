<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购退货管理</title>
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
		<li class="active"><a href="${ctx}/pss/procure/purchaseReturn/">采购退货列表</a></li>
		<shiro:hasPermission name="pss:procure:purchaseReturn:edit"><li><a href="${ctx}/pss/procure/purchaseReturn/form">采购退货添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="purchaseReturn" action="${ctx}/pss/procure/purchaseReturn/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家：</label>
				<form:select path="merchantId" class="input-medium">
					<form:option value="" label="查询所有"/>
					<form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>退货编号：</label>
				<form:input path="returnNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>商家ID</th>
				<th>退货编号</th>
				<th>商品数量</th>
				<th>总计金额</th>
				<th>合计金额</th>
				<th>其他费用</th>
				<th>实付金额</th>
				<th>结账方式</th>
				<th>供应商ID</th>
				<th>操作员</th>
				<th>业务时间</th>
				<th>退货状态</th>
				<th>退货备注</th>
				<th>更新时间</th>
				<shiro:hasPermission name="pss:procure:purchaseReturn:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="purchaseReturn">
			<tr>
				<td><a href="${ctx}/pss/procure/purchaseReturn/form?id=${purchaseReturn.id}">
					${purchaseReturn.merchantId}
				</a></td>
				<td>
					${purchaseReturn.returnNum}
				</td>
				<td>
					${purchaseReturn.goodsNum}
				</td>
				<td>
					${purchaseReturn.totalAmount}
				</td>
				<td>
					${purchaseReturn.sumAmount}
				</td>
				<td>
					${purchaseReturn.otherAmount}
				</td>
				<td>
					${purchaseReturn.payAmount}
				</td>
				<td>
					${fns:getDictLabel(purchaseReturn.payType, 'pay_type', '')}
				</td>
				<td>
					${purchaseReturn.supplierId}
				</td>
				<td>
					${purchaseReturn.operator}
				</td>
				<td>
					<fmt:formatDate value="${purchaseReturn.bizTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(purchaseReturn.returnStatus, 'return_status', '')}
				</td>
				<td>
					${purchaseReturn.returnNote}
				</td>
				<td>
					<fmt:formatDate value="${purchaseReturn.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="pss:procure:purchaseReturn:edit"><td>
    				<a href="${ctx}/pss/procure/purchaseReturn/form?id=${purchaseReturn.id}">修改</a>
					<a href="${ctx}/pss/procure/purchaseReturn/delete?id=${purchaseReturn.id}" onclick="return confirmx('确认要删除该采购退货吗？', this.href)">删除</a>
					<a href="${ctx}/pss/procure/purchaseReturnDetail/list?returnId=${purchaseReturn.id}">详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>


</body>
</html>