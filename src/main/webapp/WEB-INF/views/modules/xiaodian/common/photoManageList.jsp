<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/common/photoManage/">图片列表</a></li>
		<shiro:hasPermission name="xiaodian:common:photoManage:edit"><li><a href="${ctx}/xiaodian/common/photoManage/form">图片添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="photoManage" action="${ctx}/xiaodian/common/photoManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>图片组编号：</label>
				<form:input path="photoGroupManage.groupNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>图片库ID：</label>
				<form:input path="photoGallery.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>图片名称：</label>
				<form:input path="picName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>图片资源ID</th>
				<th>图片组编号</th>
				<th>图片地址</th>
				<th>图片名称</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:common:photoManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="photoManage">
			<tr>
				<td><a href="${ctx}/xiaodian/common/photoManage/form?id=${photoManage.id}">
					${photoManage.id}
				</a></td>
				<td>
					${photoManage.photoGroupManage.groupNum}
				</td>
				<td>
					${photoManage.picUrl}
				</td>
				<td>
					${photoManage.picName}
				</td>
				<td>
					<fmt:formatDate value="${photoManage.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${photoManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${photoManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:common:photoManage:edit"><td>
    				<a href="${ctx}/xiaodian/common/photoManage/form?id=${photoManage.id}">修改</a>
					<a href="${ctx}/xiaodian/common/photoManage/delete?id=${photoManage.id}" onclick="return confirmx('确认要删除该图片吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>