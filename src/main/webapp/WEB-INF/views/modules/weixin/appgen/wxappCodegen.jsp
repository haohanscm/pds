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
	<li><a href="${ctx}/weixin/appgen/wxappCodegen/">小程序代码构建列表</a></li>
	<shiro:hasPermission name="appgen:wxappCodegen:edit">
		<li class="active"><a href="${ctx}/weixin/appgen/wxappCodegen/codeGen">代码构建</a></li>
	</shiro:hasPermission>
</ul>
	<form:form id="inputForm" modelAttribute="wxappCode" action="${ctx}/weixin/appgen/wxappCodegen/codeBuild" method="post" enctype="multipart/form-data" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上传文件：</label>
			<div class="controls">
				<input type="file" name="zipFile" id="zipFile"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">构建模块：</label>
			<div class="controls">
				<form:checkboxes path="opModels" items="${codegenList}" itemLabel="opName" itemValue="id" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="appgen:wxappCodegen:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>