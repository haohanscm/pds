<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片组管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/common/photoGroupManage/">图片组列表</a></li>
		<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit"><li><a href="${ctx}/xiaodian/common/photoGroupManage/form">图片组添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="photoGroupManage" action="${ctx}/xiaodian/common/photoGroupManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>图片组编号：</label>
				<form:input path="groupNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>图片组名称：</label>
				<form:input path="groupName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>类别标签：</label>
				<form:input path="categroyTag" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${photoGroupManage.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${photoGroupManage.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商家ID</th>
				<th>图片组编号</th>
				<th>图片组名称</th>
				<th>类别标签</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="photoGroupManage">
			<tr>
				<td><a href="${ctx}/xiaodian/common/photoGroupManage/form?id=${photoGroupManage.id}">
					${photoGroupManage.merchantId}
				</a></td>
				<td>
					${photoGroupManage.groupNum}
				</td>
				<td>
					${photoGroupManage.groupName}
				</td>
				<td>
					${photoGroupManage.categroyTag}
				</td>
				<td>
					<fmt:formatDate value="${photoGroupManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${photoGroupManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit"><td>
    				<a href="${ctx}/xiaodian/common/photoGroupManage/form?id=${photoGroupManage.id}">修改</a>
					<a href="${ctx}/xiaodian/common/photoGroupManage/delete?id=${photoGroupManage.id}" onclick="return confirmx('确认要删除该图片组吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>