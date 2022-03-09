<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>楼栋信息管理</title>
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
							placeType: getDictLabel(${fns:toJson(fns:getDictList('buildings_place_type'))}, row.placeType),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/delivery/buildingsManage/">楼栋信息列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:buildingsManage:edit"><li><a href="${ctx}/xiaodian/delivery/buildingsManage/form">楼栋信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="buildingsManage" action="${ctx}/xiaodian/delivery/buildingsManage/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>位置名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>小区名称：</label>
				<form:input path="communityName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>位置类型：</label>
				<form:select path="placeType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('buildings_place_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>位置名称</th>
				<th>小区名称</th>
				<th>位置类型</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:delivery:buildingsManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/xiaodian/delivery/buildingsManage/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.communityName}}
			</td>
			<td>
				{{dict.placeType}}
			</td>
			<td>
				{{row.status}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="xiaodian:delivery:buildingsManage:edit"><td>
   				<a href="${ctx}/xiaodian/delivery/buildingsManage/form?id={{row.id}}">修改</a>
				<a href="${ctx}/xiaodian/delivery/buildingsManage/delete?id={{row.id}}" onclick="return confirmx('确认要删除该楼栋信息及所有子楼栋信息吗？', this.href)">删除</a>
				<a href="${ctx}/xiaodian/delivery/buildingsManage/form?parent.id={{row.id}}">添加下级楼栋信息</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>