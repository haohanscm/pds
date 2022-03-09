<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板扩展信息管理</title>
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
		<li><a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/">模板扩展信息列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/merchant/shopTemplateExtInfo/form?id=${shopTemplateExtInfo.id}&parent.id=${shopTemplateExtInfoparent.id}">模板扩展信息<shiro:hasPermission name="xiaodian:merchant:shopTemplateExtInfo:edit">${not empty shopTemplateExtInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:merchant:shopTemplateExtInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopTemplateExtInfo" action="${ctx}/xiaodian/merchant/shopTemplateExtInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">上级父级编号:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${shopTemplateExtInfo.parent.id}" labelName="parent.name" labelValue="${shopTemplateExtInfo.parent.name}"
					title="父级编号" url="/xiaodian/merchant/shopTemplateExtInfo/treeData" extId="${shopTemplateExtInfo.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板ID：</label>
			<div class="controls">
				<form:select path="templateId" class="input-xlarge">
					<form:options items="${shopTemplates}" itemLabel="templateName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="templateId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段名称：</label>
			<div class="controls">
				<form:input path="fieldName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段Code：</label>
			<div class="controls">
				<form:input path="fieldCode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="fieldType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_field_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否必填：</label>
			<div class="controls">
				<form:select path="isNeed" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">默认值：</label>
			<div class="controls">
				<form:input path="defaultValue" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">功能说明：</label>
			<div class="controls">
				<form:input path="fieldDesc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:merchant:shopTemplateExtInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>