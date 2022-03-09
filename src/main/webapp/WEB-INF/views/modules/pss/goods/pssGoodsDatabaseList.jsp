<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品数据库管理</title>
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
		<li class="active"><a href="${ctx}/pss/goods/pssGoodsDatabase/">商品数据库列表</a></li>
		<shiro:hasPermission name="pss:goods:pssGoodsDatabase:edit"><li><a href="${ctx}/pss/goods/pssGoodsDatabase/form">商品数据库添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pssGoodsDatabase" action="${ctx}/pss/goods/pssGoodsDatabase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品类型：</label>
			</li>
			<li><label>单位：</label>
				<form:select path="unit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('measure_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>品牌：</label>
				<form:input path="brand" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>商家ID</th>
				<th>商品名称</th>
				<th>商品编码</th>
				<th>商品类型</th>
				<th>参考进价</th>
				<th>单位</th>
				<th>生产地</th>
				<th>图片样例</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:goods:pssGoodsDatabase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pssGoodsDatabase">
			<tr>
				<td><a href="${ctx}/pss/goods/pssGoodsDatabase/form?id=${pssGoodsDatabase.id}">
					${pssGoodsDatabase.merchantId}
				</a></td>
				<td>
					${pssGoodsDatabase.goodsName}
				</td>
				<td>
					${pssGoodsDatabase.goodsCode}
				</td>
				<td>
					${pssGoodsDatabase.goodsCategory}
				</td>
				<td>
					${pssGoodsDatabase.advicePrice}
				</td>
				<td>
					${fns:getDictLabel(pssGoodsDatabase.unit, 'measure_unit', '')}
				</td>
				<td>
					${pssGoodsDatabase.yieldly}
				</td>
				<td>
					${pssGoodsDatabase.photos}
				</td>
				<td>
					<fmt:formatDate value="${pssGoodsDatabase.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${pssGoodsDatabase.remarks}
				</td>
				<shiro:hasPermission name="pss:goods:pssGoodsDatabase:edit"><td>
    				<a href="${ctx}/pss/goods/pssGoodsDatabase/form?id=${pssGoodsDatabase.id}">修改</a>
					<a href="${ctx}/pss/goods/pssGoodsDatabase/delete?id=${pssGoodsDatabase.id}" onclick="return confirmx('确认要删除该商品数据库吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>