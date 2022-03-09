<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理管理</title>
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
            $("[name='beginSignTime']").val("");
            $("[name='endSignTime']").val("");
            $("#merchantName").val("");

            $("#areaId").val("");
            $("#areaName").val("");

            $("#serviceProduct").select2('val',"");
            $("#serviceType").select2('val',"");
            $("#payType").select2('val',"");

            $("#bizUserId").val("");
            $("#bizUserName").val("");
            $("#opUserId").val("");
            $("#opUserName").val("");

            $("#projectStep").select2('val',"");
            $("#onlineStatus").select2('val',"");

            $("[name='beginOnlineTime']").val("");
            $("[name='endOnlineTime']").val("");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/customer/customerProjectManage/">项目管理列表</a></li>
		<shiro:hasPermission name="xiaodian:customer:customerProjectManage:edit"><li><a href="${ctx}/xiaodian/customer/customerProjectManage/form">项目管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customerProjectManage" action="${ctx}/xiaodian/customer/customerProjectManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>签约时间：</label>
				<input name="beginSignTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerProjectManage.beginSignTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endSignTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerProjectManage.endSignTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>商家名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>区域：</label>
				<sys:treeselect id="area" name="area.id" value="${customerProjectManage.area.id}" labelName="area.name" labelValue="${customerProjectManage.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>产品：</label>
				<form:select path="serviceProduct" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('service_product')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>服务版本：</label>
				<form:select path="serviceType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>支付方式：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>业务专管员：</label>
				<sys:treeselect id="bizUser" name="bizUser.id" value="${customerProjectManage.bizUser.id}" labelName="bizUser.name" labelValue="${customerProjectManage.bizUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>运营专管员：</label>
				<sys:treeselect id="opUser" name="opUser.id" value="${customerProjectManage.opUser.id}" labelName="opUser.name" labelValue="${customerProjectManage.opUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>项目阶段：</label>
				<form:select path="projectStep" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_step')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>上线状态：</label>
				<form:select path="onlineStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>上线时间：</label>
				<input name="beginOnlineTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerProjectManage.beginOnlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOnlineTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${customerProjectManage.endOnlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>商家名称</th>
				<th>签约时间</th>
				<th>区域</th>
				<th>产品</th>
				<th>服务版本</th>
				<th>支付方式</th>
				<th>业务专管员</th>
				<th>项目阶段</th>
				<th>上线状态</th>
				<th>上线时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:customer:customerProjectManage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customerProjectManage">
			<tr>
				<td><a href="${ctx}/xiaodian/customer/customerProjectManage/form?id=${customerProjectManage.id}">
						${customerProjectManage.merchantName}
				</a></td>
				<td>
					<fmt:formatDate value="${customerProjectManage.signTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerProjectManage.area.name}
				</td>
				<td>
					${fns:getDictLabel(customerProjectManage.serviceProduct, 'service_product', '')}
				</td>
				<td>
					${fns:getDictLabel(customerProjectManage.serviceType, 'product_service_type', '')}
				</td>
				<td>
					${fns:getDictLabel(customerProjectManage.payType, 'product_pay_type', '')}
				</td>
				<td>
					${customerProjectManage.bizUser.name}
				</td>
				<td>
					${fns:getDictLabel(customerProjectManage.projectStep, 'project_step', '')}
				</td>
				<td>
					${fns:getDictLabel(customerProjectManage.onlineStatus, 'project_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${customerProjectManage.onlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${customerProjectManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${customerProjectManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:customer:customerProjectManage:edit"><td>
    				<a href="${ctx}/xiaodian/customer/customerProjectManage/form?id=${customerProjectManage.id}">修改</a>
					<a href="${ctx}/xiaodian/customer/customerProjectManage/delete?id=${customerProjectManage.id}" onclick="return confirmx('确认要删除该项目管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>