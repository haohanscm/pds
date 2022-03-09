<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通行证管理</title>
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
        // 清空查询条件
        function toReset(){
            $("#id").val("");
            $("#loginName").val("");
            $("#telephone").val("");
            $("[name=beginRegTime]").val("");
            $("[name=endRegTime]").val("");
            $("#regType").select2("val","");
            $("#regFrom").select2("val","");
            $("#status").select2("val","");
            $("#isTest").select2("val","");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/uPassport/">通行证列表</a></li>
		<shiro:hasPermission name="xiaodian:uPassport:edit"><li><a href="${ctx}/xiaodian/uPassport/form">通行证添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="UPassport" action="${ctx}/xiaodian/uPassport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>登录名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>手机：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>注册时间：</label>
				<input name="beginRegTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${UPassport.beginRegTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endRegTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${UPassport.endRegTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>注册方式：</label>
				<form:select path="regType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reg_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>注册类型：</label>
				<form:select path="regFrom" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reg_from')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否测试：</label>
				<form:select path="isTest" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>邮箱</th>
				<th>手机</th>
				<th>注册时间</th>
				<th>注册IP</th>
				<th>注册方式</th>
				<th>注册类型</th>
				<th>唯一ID</th>
				<th style="width: 55px">状态</th>
				<th style="width: 55px">是否测试</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:uPassport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="uPassport">
			<tr>
				<td><a href="${ctx}/xiaodian/uPassport/form?id=${uPassport.id}">
					${uPassport.loginName}
				</a></td>
				<td>
					${uPassport.email}
				</td>
				<td>
					${uPassport.telephone}
				</td>
				<td>
					<fmt:formatDate value="${uPassport.regTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${uPassport.regIp}
				</td>
				<td>
					${fns:getDictLabel(uPassport.regType, 'reg_type', '')}
				</td>
				<td>
					${fns:getDictLabel(uPassport.regFrom, 'reg_from', '')}
				</td>
                <td>
                    ${uPassport.unionId}
                </td>
				<td>
                    ${fns:getDictLabel(uPassport.status,'common_status','')}
                </td>
                <td>
                    ${fns:getDictLabel(uPassport.isTest,'yes_no','')}
                </td>
				<td>
					<fmt:formatDate value="${uPassport.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${uPassport.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:uPassport:edit"><td>
    				<a href="${ctx}/xiaodian/uPassport/form?id=${uPassport.id}">修改</a>
					<a href="${ctx}/xiaodian/uPassport/delete?id=${uPassport.id}" onclick="return confirmx('确认要删除该通行证吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>