<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定价规则管理</title>
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
        function toReset() {
            $("#ruleName").val("");
            $("#shopId").val("");
            $("#shopName").val("");
            $("#merchantName").val("");
            $("#goodsId").val("");
            $("#goodsName").val("");
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/retail/goodsPriceRule/">定价规则列表</a></li>
		<shiro:hasPermission name="xiaodian:retail:goodsPriceRule:edit"><li><a href="${ctx}/xiaodian/retail/goodsPriceRule/form">定价规则添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsPriceRule" action="${ctx}/xiaodian/retail/goodsPriceRule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>规则名称：</label>
				<form:input path="ruleName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>店铺ID：</label>
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label>店铺名称：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>商品名称：</label>
                <form:input path="goodsName" htmlEscape="false" maxlength="10" class="input-medium"  placeholder="可模糊查询"/>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width: 150px;overflow: hidden;">商品名称</th>
				<th>规则名称</th>
				<th>店铺</th>
				<th>商家</th>
				<th>市场价/销售价</th>
				<th>批发定价(元)</th>
				<th>vip定价(元)</th>
				<th>零售定价(元)</th>
				<th>虚拟价格(元)</th>
				<th>计量单位</th>
				<th>更新时间</th>
				<%--<th>备注信息</th>--%>
				<shiro:hasPermission name="xiaodian:retail:goodsPriceRule:edit"><th style="width: 100px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsPriceRule">
			<tr>
				<td><a href="${ctx}/xiaodian/retail/goodsPriceRule/form?id=${goodsPriceRule.id}">
					${goodsPriceRule.goodsName}
				</a></td>
				<td>
					${goodsPriceRule.ruleName}
				</td>
				<td><a href="${ctx}/xiaodian/retail/goodsPriceRule/list?shopId=${goodsPriceRule.shopId}">
						${empty goodsPriceRule.shopName ? goodsPriceRule.shopId : goodsPriceRule.shopName}
				</a></td>
                <td>
						${empty goodsPriceRule.merchantName ? goodsPriceRule.merchantId : goodsPriceRule.merchantName}
				</td>
				<td>
					${goodsPriceRule.ruleDesc}
				</td>
                <td>
					${goodsPriceRule.wholesalePrice}
				</td>
				<td>
					${goodsPriceRule.vipPrice}
				</td>
				<td>
					${goodsPriceRule.marketPrice}
				</td>
				<td>
					${goodsPriceRule.virtualPrice}
				</td>
				<td>
					${goodsPriceRule.unit}
				</td>
				<td>
					<fmt:formatDate value="${goodsPriceRule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:retail:goodsPriceRule:edit"><td>
    				<a href="${ctx}/xiaodian/retail/goodsPriceRule/form?id=${goodsPriceRule.id}">修改</a>
					<a href="${ctx}/xiaodian/retail/goodsPriceRule/delete?id=${goodsPriceRule.id}" onclick="return confirmx('确认要删除该定价规则吗？', this.href)">删除</a>
    				<a href="${ctx}/xiaodian/retail/goodsPriceRule/copy?id=${goodsPriceRule.id}">复制</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>