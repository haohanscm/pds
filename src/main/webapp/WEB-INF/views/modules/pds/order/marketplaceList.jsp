<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>市场报价管理</title>
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
		<li class="active"><a href="${ctx}/pds/order/marketplace/">市场报价列表</a></li>
		<shiro:hasPermission name="pds:order:marketplace:edit"><li><a href="${ctx}/pds/order/marketplace/form">市场报价添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="marketplace" action="${ctx}/pds/order/marketplace/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家ID：</label>
				<form:input path="pmId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:input path="marketplaceType" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品ID：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>支付方式：</label>
				<form:input path="payType" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>配送方式：</label>
				<form:input path="deliveryType" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>平台商家ID</th>
				<th>类型</th>
				<th>商家</th>
				<th>商品ID</th>
				<th>商品规格</th>
				<th>状态</th>
				<th>支付方式</th>
				<th>配送方式</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:marketplace:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="marketplace">
			<tr>
				<td><a href="${ctx}/pds/order/marketplace/form?id=${marketplace.id}">
					${marketplace.marketplaceNo}
				</a></td>
				<td>
					${marketplace.pmId}
				</td>
				<td>
					${marketplace.marketplaceType}
				</td>
				<td>
					${marketplace.merchantId}
				</td>
				<td>
					${marketplace.goodsId}
				</td>
				<td>
					${marketplace.goodsModel}
				</td>
				<td>
					${marketplace.status}
				</td>
				<td>
					${marketplace.payType}
				</td>
				<td>
					${marketplace.deliveryType}
				</td>
				<td>
					<fmt:formatDate value="${marketplace.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${marketplace.remarks}
				</td>
				<shiro:hasPermission name="pds:order:marketplace:edit"><td>
    				<a href="${ctx}/pds/order/marketplace/form?id=${marketplace.id}">修改</a>
					<a href="${ctx}/pds/order/marketplace/delete?id=${marketplace.id}" onclick="return confirmx('确认要删除该市场报价吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>