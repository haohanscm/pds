<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板管理</title>
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
            $("#appId").val("");
            $("#templateName").val("");
            $("#templateType").select2("val","");
            $("[name='beginUploadTime']").val("");
            $("[name='endUploadTime']").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/merchant/shopTemplate/">模板列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:shopTemplate:edit"><li><a href="${ctx}/xiaodian/merchant/shopTemplate/form">模板添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopTemplate" action="${ctx}/xiaodian/merchant/shopTemplate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>模板名称：</label>
				<form:input path="templateName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>模板类型：</label>
                <form:select path="templateType" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('shop_template_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>上传时间：</label>
				<input name="beginUploadTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${shopTemplate.beginUploadTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endUploadTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${shopTemplate.endUploadTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>模板名称</th>
				<th>微信模板ID</th>
				<th>应用ID</th>
				<th>模板ID</th>
				<th>模板类型</th>
				<th>版本号</th>
				<th>上传时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:merchant:shopTemplate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shopTemplate">
			<tr>
				<td><a href="${ctx}/xiaodian/merchant/shopTemplate/form?id=${shopTemplate.id}">
				   ${shopTemplate.templateName}
				</a></td>
				<td>
					${shopTemplate.wxModelId}
				</td>
				<td>
					${shopTemplate.appId}
				</td>
				<td>
					${shopTemplate.id}
				</td>
                <td>
                    ${fns:getDictLabel(shopTemplate.templateType, 'shop_template_type', shopTemplate.templateType)}
                </td>
				<td>
					${shopTemplate.versionNo}
				</td>
				<td>
					<fmt:formatDate value="${shopTemplate.uploadTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${shopTemplate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shopTemplate.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:merchant:shopTemplate:edit"><td>
    				<a href="${ctx}/xiaodian/merchant/shopTemplate/form?id=${shopTemplate.id}">修改</a>
					<a href="${ctx}/xiaodian/merchant/shopTemplate/delete?id=${shopTemplate.id}" onclick="return confirmx('确认要删除该模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>