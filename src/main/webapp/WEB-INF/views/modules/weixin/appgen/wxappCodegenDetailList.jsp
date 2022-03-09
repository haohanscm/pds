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
		<li class="active"><a href="${ctx}/weixin/appgen/wxappCodegenDetail/">小程序代码构建列表</a></li>
		<shiro:hasPermission name="appgen:wxappCodegenDetail:edit"><li><a href="${ctx}/weixin/appgen/wxappCodegenDetail/form">小程序代码构建添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wxappCodegenDetail" action="${ctx}/weixin/appgen/wxappCodegenDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>构建ID：</label>
				<form:input path="wxappCodegen.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>构建名称</th>
				<th>操作说明</th>
				<th>操作类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="appgen:wxappCodegenDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxappCodegenDetail">
			<tr>
				<td><a href="${ctx}/appgen/wxappCodegenDetail/form?id=${wxappCodegenDetail.id}">
					${wxappCodegenDetail.wxappCodegen.opName}
				</a></td>
				<td>
					${wxappCodegenDetail.opDetailDesc}
				</td>
				<td>
					${fns:getDictLabel(wxappCodegenDetail.opType, 'codegen_optype', '')}
				</td>
				<td>
					<fmt:formatDate value="${wxappCodegenDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wxappCodegenDetail.remarks}
				</td>
				<shiro:hasPermission name="appgen:wxappCodegenDetail:edit"><td>
    				<a href="${ctx}/weixin/appgen/wxappCodegenDetail/form?id=${wxappCodegenDetail.id}">修改</a>
					<a href="${ctx}/weixin/appgen/wxappCodegenDetail/delete?id=${wxappCodegenDetail.id}" onclick="return confirmx('确认要删除该小程序代码构建吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>