<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接口日志管理管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/common/logApiRecord/">接口日志管理列表</a></li>
		<shiro:hasPermission name="xiaodian:common:logApiRecord:edit"><li><a href="${ctx}/xiaodian/common/logApiRecord/form">接口日志管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="logApiRecord" action="${ctx}/xiaodian/common/logApiRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台端：</label>
				<form:input path="platform" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>接口名称：</label>
				<form:select path="apiName" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('api_list')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>请求参数：</label>
				<form:input path="reqParams" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>返回参数：</label>
				<form:input path="respParams" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>请求时间：</label>
				<input name="beginReqTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${logApiRecord.beginReqTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endReqTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${logApiRecord.endReqTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>平台端</th>
				<th>产品</th>
				<th>版本</th>
				<th>请求ID</th>
				<th>请求时间</th>
				<th>接口名称</th>
				<th>HTTP方法</th>
				<th>耗时毫秒</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:common:logApiRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="logApiRecord">
			<tr>
				<td>
					${logApiRecord.platform}
				</td>
				<td>
					${logApiRecord.product}
				</td>
				<td>
					${logApiRecord.version}
				</td>
				<td><a href="${ctx}/xiaodian/common/logApiRecord/form?id=${logApiRecord.id}">
					${logApiRecord.reqId}
				</a></td>
				<td>
					<fmt:formatDate value="${logApiRecord.reqTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(logApiRecord.apiName, 'api_list', '')}
				</td>
				<td>
					${fns:getDictLabel(logApiRecord.httpMethod, 'http_method', '')}
				</td>
				<td>
					${logApiRecord.costTime}
				</td>
				<td>
					<fmt:formatDate value="${logApiRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${logApiRecord.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:common:logApiRecord:edit"><td>
    				<a href="${ctx}/xiaodian/common/logApiRecord/form?id=${logApiRecord.id}">修改</a>
					<a href="${ctx}/xiaodian/common/logApiRecord/delete?id=${logApiRecord.id}" onclick="return confirmx('确认要删除该接口日志管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>