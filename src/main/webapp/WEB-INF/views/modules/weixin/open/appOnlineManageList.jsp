<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用上线记录管理</title>
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
        function toReset(){
            $("#appId").val("");
            $("#appName").val("");
            $("#merchantId").val("");
            $("#merchantName").val("");
            $("#status").select2('val',"");
            $("#stepNo").val("");
            $("#channel").select2('val',"");
            $("#opType").select2('val',"");
            $("[name='beginReqTime']").val("");
            $("[name='endReqTime']").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/weixin/open/appOnlineManage/">应用上线记录</a></li>
		<%--<shiro:hasPermission name="weixin:open:appOnlineManage:edit"><li><a href="${ctx}/weixin/open/appOnlineManage/form">应用上线记录添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="appOnlineManage" action="${ctx}/weixin/open/appOnlineManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用ID：</label>
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>应用名称：</label>
				<form:input path="appName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家ID：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_online_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>步骤：</label>
				<form:input path="stepNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>渠道：</label>
				<form:select path="channel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>操作类型：</label>
				<form:select path="opType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_op_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>请求时间：</label>
				<input name="beginReqTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${appOnlineManage.beginReqTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endReqTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${appOnlineManage.endReqTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li ><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>应用ID</th>
				<th>应用名称</th>
				<th>商家名称</th>
				<th>步骤名称</th>
				<th>步骤序号</th>
				<th>状态</th>
				<th>渠道</th>
				<th>操作类型</th>
				<th>请求时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="weixin:open:appOnlineManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appOnlineManage">
			<tr>
				<td><a href="${ctx}/weixin/open/appOnlineManage/form?id=${appOnlineManage.id}">
					${appOnlineManage.appId}
				</a></td>
				<td>
					${appOnlineManage.appName}
				</td>
				<td>
					${appOnlineManage.merchantName}
				</td>
				<td>
					${appOnlineManage.stepName}
				</td>
				<td>
					${appOnlineManage.stepNo}
				</td>
				<td>
					${fns:getDictLabel(appOnlineManage.status, 'app_online_status', '')}
				</td>
				<td>
					${fns:getDictLabel(appOnlineManage.channel, 'app_channel', '')}
				</td>
				<td>
					${fns:getDictLabel(appOnlineManage.opType, 'app_op_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${appOnlineManage.reqTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${appOnlineManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="weixin:open:appOnlineManage:edit"><td>
    				<a href="${ctx}/weixin/open/appOnlineManage/form?id=${appOnlineManage.id}">修改</a>
					<a href="${ctx}/weixin/open/appOnlineManage/delete?id=${appOnlineManage.id}" onclick="return confirmx('确认要删除该应用吗？', this.href)">删除</a>
					<c:if test="${200 != appOnlineManage.status}">
						<a href="${ctx}/weixin/open/appOnlineManage/callApi?id=${appOnlineManage.id}">重新提交</a>
					</c:if>

				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>