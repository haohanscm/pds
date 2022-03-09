<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品规格管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/retail/goodsModel/">商品规格列表</a></li>
		<shiro:hasPermission name="xiaodian:retail:goodsModel:edit"><li><a href="${ctx}/xiaodian/retail/goodsModel/form">商品规格添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsModel" action="${ctx}/xiaodian/retail/goodsModel/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>店铺id：</label>
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label>商品id：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label style="width: 100px">商品规格编号：</label>
				<form:input path="goodsModelSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label style="width: 120px">商品规格通用编号：</label>
				<form:input path="modelGeneralSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label style="width: 120px">第三方规格编号：</label>
				<form:input path="thirdModelSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>规格名称：</label>
				<form:input path="modelName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品规格id</th>
				<th>商品名称</th>
				<th>商品id</th>
				<th>店铺id</th>
				<th>规格价格</th>
				<th>商品规格编号</th>
				<th>商品规格通用编号</th>
				<th>第三方规格编号</th>
				<th>规格名称</th>
				<th>规格单位</th>
				<th>规格库存</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:retail:goodsModel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsModel">
			<tr>
				<td><a href="${ctx}/xiaodian/retail/goodsModel/form?id=${goodsModel.id}">
					${goodsModel.id}
				</a></td>
				<td>
					${goodsModel.goodsName}
				</td>
				<td>
					${goodsModel.goodsId}
				</td>
				<td>
					${goodsModel.shopId}
				</td>
				<td>
					${goodsModel.modelPrice}
				</td>
				<td>
					${goodsModel.goodsModelSn}
				</td>
				<td>
					${goodsModel.modelGeneralSn}
				</td>
				<td>
					${goodsModel.thirdModelSn}
				</td>
				<td>
					${goodsModel.modelName}
				</td>
				<td>
					${goodsModel.modelUnit}
				</td>
				<td>
					${goodsModel.modelStorage}
				</td>
				<td>
					<fmt:formatDate value="${goodsModel.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsModel.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:retail:goodsModel:edit"><td>
    				<a href="${ctx}/xiaodian/retail/goodsModel/form?id=${goodsModel.id}">修改</a>
					<a href="${ctx}/xiaodian/retail/goodsModel/delete?id=${goodsModel.id}" onclick="return confirmx('确认要删除该商品规格吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>