<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺管理</title>
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
        // 清空查询条件
        function toReset(){
            $("#code").val("");
            $("#name").val("");
            $("#manager").val("");
            $("#merchantId").val("");
            $("#status").select2('val',"");
            $("#shopLevel").select2('val',"");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/merchant/shop/">店铺列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:shop:edit"><li><a href="${ctx}/xiaodian/merchant/shop/form">店铺添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shop" action="${ctx}/xiaodian/merchant/shop/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>即速应用ID：</label>
				<form:input path="code" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>店铺名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>店铺负责人：</label>
				<form:input path="manager" htmlEscape="false" maxlength="100" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>商家：</label>
                <form:select path="merchantId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('status_merchant')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>店铺级别：</label>
				<form:select path="shopLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('shop_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width: 100px;">聚合平台类型：</label>
                <form:select path="aggregationType" class="input-medium ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('aggregation_shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
            <li><label >认证类型：</label>
                <form:select path="authType" class="input-medium ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('shop_auth_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>店铺名称</th>
				<th>商家名称</th>
				<th>店铺服务模式</th>
				<th>店铺级别</th>
				<th>聚合平台类型</th>
				<th>即速应用ID</th>
				<th>店铺负责人</th>
				<th>店铺ID</th>
				<th>商家ID</th>
				<th>状态</th>
				<th>认证类型</th>
				<th>更新即速商品</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:merchant:shop:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shop">
			<tr>
				<td><a href="${ctx}/xiaodian/merchant/shop/form?id=${shop.id}">
					${shop.name}
				</a></td>
                <td>
                        ${shop.merchantName}
                </td>
				<td>
					${fns:getDictLabel(shop.serviceType, 'shop_service_type','')}
				</td>
				<td>
					${fns:getDictLabel(shop.shopLevel,'shop_level','')}
				</td>
				<td>
					${fns:getDictLabel(shop.aggregationType,'aggregation_shop_type','')}
				</td>
				<td>
					${shop.code}
				</td>
				<td>
					${shop.manager}
				</td>
				<td>
					${shop.id}
				</td>
				<td>
					${shop.merchantId}
				</td>
				<td>
					${fns:getDictLabel(shop.status, 'status_merchant', '')}
				</td>
				<td>
					${fns:getDictLabel(shop.authType, 'shop_auth_type', '')}
				</td>
				<td>
					${fns:getDictLabel(shop.isUpdateJisu, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${shop.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shop.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:merchant:shop:edit"><td>
    				<a href="${ctx}/xiaodian/merchant/shop/form?id=${shop.id}">修改</a>
					<a href="${ctx}/xiaodian/merchant/shop/delete?id=${shop.id}" onclick="return confirmx('确认要删除该店铺吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>