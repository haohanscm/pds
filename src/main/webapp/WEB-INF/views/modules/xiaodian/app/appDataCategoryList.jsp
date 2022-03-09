<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据分类管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		function toReset(){
		    $("#appId").val("");
		    $("#parentIds").val("");
		    $("#name").val("");
		}
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
		<li class="active"><a href="${ctx}/xiaodian/app/appDataCategory/">数据分类列表</a></li>
		<shiro:hasPermission name="xiaodian:app:appDataCategory:edit"><li><a href="${ctx}/xiaodian/app/appDataCategory/form">数据分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appDataCategory" action="${ctx}/xiaodian/app/appDataCategory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>根节点数：</label>
				<form:input path="rootNodeNum" htmlEscape="false" maxlength="64" class="input-small"/>
			</li>
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>父分类ID：</label>
				<form:input path="parentIds" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li ><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>应用ID</th>
				<th>分类图标</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:app:appDataCategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/xiaodian/app/appDataCategory/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td><a href="${ctx}/xiaodian/app/appDataCategory/list?appId={{row.appId}}">
				{{row.appId}}
			</a></td>
			<td>
				<c:set  value="{{row.logo}}" var="imgstr"/>
				<%--<c:out value="${imgstr}"/>--%>
				<c:if test="${not empty imgstr}">
				<img src="${imgstr}" alt="logo" onerror=this.style.display="none" />
				</c:if>
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="xiaodian:app:appDataCategory:edit"><td>
   				<a href="${ctx}/xiaodian/app/appDataCategory/form?id={{row.id}}">修改</a>
				<a href="${ctx}/xiaodian/app/appDataCategory/delete?id={{row.id}}" onclick="return confirmx('确认要删除该数据分类及所有子数据分类吗？', this.href)">删除</a>
				<a href="${ctx}/xiaodian/app/appDataCategory/form?parent.id={{row.id}}">添加下级数据分类</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
	<div class="pagination">${page}</div>
</body>
</html>