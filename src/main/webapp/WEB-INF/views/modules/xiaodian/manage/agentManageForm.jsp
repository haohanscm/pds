<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合作商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/manage/agentManage/">合作商列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/manage/agentManage/form?id=${agentManage.id}&parent.id=${agentManageparent.id}">合作商<shiro:hasPermission name="xiaodian:manage:agentManage:edit">${not empty agentManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:manage:agentManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="agentManage" action="${ctx}/xiaodian/manage/agentManage/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<sys:message content="${message}"/>
        <c:if test="${ not empty agentManage.id}">
            <div class="control-group">
                <label class="control-label">合作商ID：</label>
                <div class="controls">
                    <form:input path="id" readonly="true" class="input-xlarge"/>
                </div>
            </div>
        </c:if>
		<div class="control-group">
			<label class="control-label">上级父级编号:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${agentManage.parent.id}" labelName="parent.name" labelValue="${agentManage.parent.name}"
					title="父级编号" url="/xiaodian/manage/agentManage/treeData" extId="${agentManage.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">合作类型：</label>
			<div class="controls">
				<form:select path="agentType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('agent_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合作商编号：</label>
			<div class="controls">
				<form:input path="agentSn" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在地：</label>
			<div class="controls">
				<form:input path="addr" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同签署日期：</label>
			<div class="controls">
				<input name="contractDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${agentManage.contractDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('agent_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分润比例：</label>
			<div class="controls">
				<form:input path="profit" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合作状态：</label>
			<div class="controls">
				<form:select path="power" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('agent_power')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">排序：</label>
            <div class="controls">
                <form:input path="sort" htmlEscape="false" class="input-xlarge  digits"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:manage:agentManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>