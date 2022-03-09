<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家管理</title>
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
        // 清空查询条件
        function toReset(){
            $("[name='upassport.id']").val("");
            $("#merchantName").val("");
            $("#areaId").val("");
            $("#areaName").val("");
            $("#contact").val("");
            $("#userId").val("");
            $("#userName").val("");
            $("[name='status']").removeAttr("checked");
            $("#pdsType").select2("val","");
            $("#productLine").select2("val","");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/merchant/">商家列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:edit"><li><a href="${ctx}/xiaodian/merchant/form">商家添加</a></li></shiro:hasPermission>
		<%--<shiro:hasPermission name="xiaodian:merchant:edit"><li><a href="${ctx}/xiaodian/merchant/photoManage">商家资料添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="merchant" action="${ctx}/xiaodian/merchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>归属用户ID：</label>
				<form:input path="upassport.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="50" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>区域：</label>
				<sys:treeselect id="area" name="area.id" value="${merchant.area.id}" labelName="area.name" labelValue="${merchant.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
            <li><label style="width: 100px">采购配送类型：</label>
                <form:select path="pdsType" class="input-medium ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pds_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label style="width: 100px">产品线类型：</label>
                <form:select path="productLine" class="input-medium ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('product_line_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label style="width: 100px">归属商家：</label>
                <form:select path="parentId" class="input-medium ">
                    <form:option value="" label=""/>
                    <form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>联系人：</label>
				<form:input path="contact" htmlEscape="false" maxlength="50" class="input-medium" placeholder="可模糊查询"/>
			</li>
        </ul>
        <ul class="ul-form">
			<li><label>业务专管员：</label>
				<sys:treeselect id="user" name="user.id" value="${merchant.user.id}" labelName="user.name" labelValue="${merchant.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>状态：</label>
				<form:radiobuttons path="status" items="${fns:getDictList('status_merchant')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>序号</th>
				<th>商家名称</th>
				<th>渠道来源</th>
				<th>区域</th>
				<th>行业名称</th>
				<th>联系人</th>
				<th>业务专管员</th>
				<th>状态</th>
				<th>采购配送类型</th>
				<th>产品线类型</th>
				<th>归属商家</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:merchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchant" varStatus="index">
			<tr>
                <td>${index.count}</td>
				<td><a href="${ctx}/xiaodian/merchant/form?id=${merchant.id}">
						${merchant.merchantName}
				</a></td>
				<td>
						${merchant.partnerName}
				</td>
				<td>
					${merchant.area.name}
				</td>
				<td>
					${merchant.industry}
				</td>
                <td>
                    ${merchant.contact}
                </td>
				<td>
					${merchant.user.name}
				</td>
				<td>
					${fns:getDictLabel(merchant.status, 'status_merchant', '')}
				</td>
				<td>
					${fns:getDictLabel(merchant.pdsType, 'pds_type', '')}
				</td>
				<td>
					${fns:getDictLabel(merchant.productLine, 'product_line_type', '')}
				</td>
				<td>
					${merchant.parentName}
				</td>
				<td>
					<fmt:formatDate value="${merchant.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${merchant.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${merchant.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:merchant:edit"><td>
    				<a href="${ctx}/xiaodian/merchant/form?id=${merchant.id}">修改</a>
					<a href="${ctx}/xiaodian/merchant/delete?id=${merchant.id}" onclick="return confirmx('确认要删除该商家吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>