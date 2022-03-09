<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小程序代码构建管理</title>
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
		<li class="active"><a href="${ctx}/weixin/appgen/wxappCodegen/">小程序代码构建列表</a></li>
		<shiro:hasPermission name="appgen:wxappCodegen:edit">
			<li><a href="${ctx}/weixin/appgen/wxappCodegen/form">小程序代码构建添加</a></li>
			<li><a href="${ctx}/weixin/appgen/wxappCodegen/codeGen">代码构建</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxappCodegen" action="${ctx}/weixin/appgen/wxappCodegen/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>操作名称：</label>
				<form:input path="opName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>操作模块：</label>
				<form:select path="opModel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('codegen_model')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>操作名称</th>
				<th>操作模块</th>
				<th>操作对象</th>
				<th>操作编号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="appgen:wxappCodegen:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxappCodegen">
			<tr>
				<td><a href="${ctx}/weixin/appgen/wxappCodegen/form?id=${wxappCodegen.id}">
					${wxappCodegen.opName}
				</a></td>
				<td>
					${fns:getDictLabel(wxappCodegen.opModel, 'codegen_model', '')}
				</td>
				<td>
					${wxappCodegen.opObj}
				</td>
				<td>
					${wxappCodegen.opNum}
				</td>
				<td>
					<fmt:formatDate value="${wxappCodegen.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxappCodegen.remarks}
				</td>
				<shiro:hasPermission name="appgen:wxappCodegen:edit"><td>
    				<a href="${ctx}/weixin/appgen/wxappCodegen/form?id=${wxappCodegen.id}">修改</a>
					<a href="${ctx}/weixin/appgen/wxappCodegen/delete?id=${wxappCodegen.id}" onclick="return confirmx('确认要删除该小程序代码构建吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>