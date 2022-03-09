<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家应用管理</title>
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
            $("#appName").val("");
            $("#merchantName").val("");
            $("#jisuAppId").val("");
            $("#adminName").val("");
            $("#templateName").val("");
            $("#ext").val("");
            $("#status").select2('val',"");
            $("[name='beginOnlineTime']").val("");
            $("[name='endOnlineTime']").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/merchant/merchantAppManage/">商家应用列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit"><li><a href="${ctx}/xiaodian/merchant/merchantAppManage/form">商家应用添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantAppManage" action="${ctx}/xiaodian/merchant/merchantAppManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用appid：</label>
				<form:input path="appId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>应用名称：</label>
				<form:input path="appName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>即速应用ID：</label>
				<form:input path="jisuAppId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>管理员名称：</label>
				<form:input path="adminName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询" />
			</li>
			<li><label>模板名称：</label>
				<form:input path="templateName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>扩展信息：</label>
				<form:input path="ext" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('merchant_app_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>上线时间：</label>
				<input name="beginOnlineTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantAppManage.beginOnlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOnlineTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantAppManage.endOnlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>应用appid</th>
				<th>应用名称</th>
				<th>商家名称</th>
				<th>管理员名称</th>
				<th>即速应用ID</th>
				<th>模板名称</th>
				<th>状态</th>
				<th>上线时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantAppManage">
			<tr>
				<td><a href="${ctx}/xiaodian/merchant/merchantAppManage/form?id=${merchantAppManage.id}">
					${merchantAppManage.appId}
				</a></td>
				<td>
					${merchantAppManage.appName}
				</td>
				<td>
					${merchantAppManage.merchantName}
				</td>
				<td>
					${merchantAppManage.adminName}
				</td>
				<td>
				    ${merchantAppManage.jisuAppId}
				</td>
				<td>
					${merchantAppManage.templateName}
				</td>
				<td>
					${fns:getDictLabel(merchantAppManage.status, 'merchant_app_status', merchantAppManage.status)}
				</td>
				<td>
					<fmt:formatDate value="${merchantAppManage.onlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${merchantAppManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit"><td>
    				<a href="${ctx}/xiaodian/merchant/merchantAppManage/form?id=${merchantAppManage.id}">修改</a>
					<a href="${ctx}/xiaodian/merchant/merchantAppManage/delete?id=${merchantAppManage.id}" onclick="return confirmx('确认要删除该商家应用吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>