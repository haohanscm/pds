<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小程序代码构建管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
		<li><a href="${ctx}/weixin/appgen/wxappCodegenDetail/">小程序代码构建列表</a></li>
		<li class="active"><a href="${ctx}/weixin/appgen/wxappCodegenDetail/form?id=${wxappCodegenDetail.id}">小程序代码构建<shiro:hasPermission name="appgen:wxappCodegenDetail:edit">${not empty wxappCodegenDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="appgen:wxappCodegenDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wxappCodegenDetail" action="${ctx}/weixin/appgen/wxappCodegenDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">构建名称：</label>
		<div class="controls">
			<form:select path="wxappCodegen.id" class="input-xlarge ">
				<form:option value="" label=""/>
				<form:options items="${wxappCodegenList}" itemLabel="opName" itemValue="id" htmlEscape="false"/>
			</form:select>
				<%--<form:input path="wxappCodegen.id" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
		</div>
	</div>

		<div class="control-group">
			<label class="control-label">操作说明：</label>
			<div class="controls">
				<form:input path="opDetailDesc" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作类型：</label>
			<div class="controls">
				<form:select path="opType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('codegen_optype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">索引值：</label>
			<div class="controls">
				<form:textarea path="indexValue" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目标值：</label>
			<div class="controls">
				<form:textarea path="targetValue" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="appgen:wxappCodegenDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>