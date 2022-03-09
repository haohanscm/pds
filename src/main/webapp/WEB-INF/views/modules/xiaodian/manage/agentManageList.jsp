<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合作商管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
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
							agentType: getDictLabel(${fns:toJson(fns:getDictList('agent_type'))}, row.agentType),
							status: getDictLabel(${fns:toJson(fns:getDictList('agent_status'))}, row.status),
							power: getDictLabel(${fns:toJson(fns:getDictList('agent_power'))}, row.power),
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
		<li class="active"><a href="${ctx}/xiaodian/manage/agentManage/">合作商列表</a></li>
		<shiro:hasPermission name="xiaodian:manage:agentManage:edit"><li><a href="${ctx}/xiaodian/manage/agentManage/form">合作商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="agentManage" action="${ctx}/xiaodian/manage/agentManage/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>合作类型：</label>
				<form:select path="agentType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('agent_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>审核状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('agent_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>合作状态：</label>
				<form:select path="power" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('agent_power')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>名称</th>
				<th>合作类型</th>
				<th>合作商编号</th>
				<th>所在地</th>
				<th>合同签署日期</th>
				<th>审核状态</th>
				<th>分润比例</th>
				<th>合作状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:manage:agentManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/xiaodian/manage/agentManage/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{dict.agentType}}
			</td>
			<td>
				{{row.agentSn}}
			</td>
			<td>
				{{row.addr}}
			</td>
			<td>
				{{row.contractDate}}
			</td>
			<td>
				{{dict.status}}
			</td>
			<td>
				{{row.profit}}
			</td>
			<td>
				{{dict.power}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="xiaodian:manage:agentManage:edit"><td>
   				<a href="${ctx}/xiaodian/manage/agentManage/form?id={{row.id}}">修改</a>
				<a href="${ctx}/xiaodian/manage/agentManage/delete?id={{row.id}}" onclick="return confirmx('确认要删除该合作商及所有子合作商吗？', this.href)">删除</a>
				<a href="${ctx}/xiaodian/manage/agentManage/form?parent.id={{row.id}}">添加下级合作商</a>
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>