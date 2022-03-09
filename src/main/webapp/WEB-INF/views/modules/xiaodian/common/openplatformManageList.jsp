<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用信息管理</title>
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
							appType: getDictLabel(${fns:toJson(fns:getDictList('app_service_type'))}, row.appType),
							status: getDictLabel(${fns:toJson(fns:getDictList('common_status'))}, row.status),
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
		<li class="active"><a href="${ctx}/xiaodian/common/openplatformManage/">应用信息列表</a></li>
		<shiro:hasPermission name="xiaodian:common:openplatformManage:edit"><li><a href="${ctx}/xiaodian/common/openplatformManage/form">应用信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="openplatformManage" action="${ctx}/xiaodian/common/openplatformManage/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>应用名称：</label>
				<form:input path="appName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>应用类型：</label>
				<form:select path="appType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>注册邮箱：</label>
				<form:input path="regEmail" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>注册手机号：</label>
				<form:input path="regTelephone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>开通时间：</label>
				<input name="beginOpenTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${openplatformManage.beginOpenTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOpenTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${openplatformManage.endOpenTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>应用名称</th>
				<th>应用类型</th>
				<th>应用ID</th>
				<th>注册邮箱</th>
				<th>密码</th>
				<th>注册手机号</th>
				<th>状态</th>
				<th>开通时间</th>
				<shiro:hasPermission name="xiaodian:common:openplatformManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/xiaodian/common/openplatformManage/form?id={{row.id}}">
				{{row.appName}}
			</a></td>
			<td>
				{{dict.appType}}
			</td>
			<td>
				{{row.appId}}
			</td>
			<td>
				{{row.regEmail}}
			</td>
			<td>
				{{row.regPassword}}
			</td>
			<td>
				{{row.regTelephone}}
			</td>
			<td>
				{{dict.status}}
			</td>
			<td>
				{{row.openTime}}
			</td>

			<shiro:hasPermission name="xiaodian:common:openplatformManage:edit"><td>
   				<a href="${ctx}/xiaodian/common/openplatformManage/form?id={{row.id}}">修改</a>
				<a href="${ctx}/xiaodian/common/openplatformManage/delete?id={{row.id}}" onclick="return confirmx('确认要删除该应用信息及所有子应用信息吗？', this.href)">删除</a>
				<a href="${ctx}/xiaodian/common/openplatformManage/form?parent.id={{row.id}}">添加下级应用信息</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>