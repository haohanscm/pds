<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板扩展信息管理</title>
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
							fieldType: getDictLabel(${fns:toJson(fns:getDictList('app_field_type'))}, row.fieldType),
							isNeed: getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, row.isNeed),
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        // 清空查询条件
        function toReset(){
            $("#rootNodeNum").val("");
            $("#name").val("");
            $("#templateId").val("");
            $("#fieldType").select2('val',"");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/">模板扩展信息列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:shopTemplateExtInfo:edit"><li><a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/form">模板扩展信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shopTemplateExtInfo" action="${ctx}/xiaodian/merchant/shopTemplateExtInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>根节点数：</label>
				<form:input path="rootNodeNum" htmlEscape="false" maxlength="64" class="input-small" placeholder="默认5个"/>
			</li>
			<li><label>根节点名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>模板ID：</label>
				<form:input path="templateId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<%--<li><label>字段名称：</label>--%>  <%-- 不是根节点 目前不能查询到 --%>
				<%--<form:input path="fieldName" htmlEscape="false" maxlength="64" class="input-medium"/>--%>
			<%--</li>--%>
			<%--<li><label>字段Code：</label>--%>
				<%--<form:input path="fieldCode" htmlEscape="false" maxlength="64" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>类型：</label>
				<form:select path="fieldType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_field_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>字段名称</th>
				<th>字段Code</th>
				<th>类型</th>
				<th>是否必填</th>
				<th>排序</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:merchant:shopTemplateExtInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.fieldName}}
			</td>
			<td>
				{{row.fieldCode}}
			</td>
			<td>
				{{dict.fieldType}}
			</td>
			<td>
				{{dict.isNeed}}
			</td>
			<td>
				{{row.sort}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<shiro:hasPermission name="xiaodian:merchant:shopTemplateExtInfo:edit"><td>
   				<a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/form?id={{row.id}}">修改</a>
				<a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/delete?id={{row.id}}" onclick="return confirmx('确认要删除该模板扩展信息及所有子信息吗？', this.href)">删除</a>
				<a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/form?parent.id={{row.id}}">添加下级信息</a>
			</td></shiro:hasPermission>
		</tr>
	</script>
	<div class="pagination">${page}</div>
</body>
</html>