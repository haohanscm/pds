<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片管理</title>
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
		<li><a href="${ctx}/xiaodian/common/photoGallery/">图片列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/common/photoGallery/form?id=${photoGallery.id}">图片<shiro:hasPermission name="xiaodian:common:photoGallery:edit">${not empty photoGallery.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:common:photoGallery:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="photoGallery" action="${ctx}/xiaodian/common/photoGallery/save" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/>--%>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">图片库id：</label>
			<div class="controls">
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片名称：</label>
			<div class="controls">
				<form:input path="picName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片预览：</label>
			<div class="controls">
				<img src="${photoGallery.picUrl}" style="height: 150px;width: 150px"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片地址：</label>
			<div class="controls">
				<form:input path="picUrl" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片类型：</label>
			<div class="controls">
				<form:input path="picType" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片大小：</label>
			<div class="controls">
				<form:input path="picSize" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片来源：</label>
			<div class="controls">
				<form:input path="picFrom" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">存储云服务：</label>
			<div class="controls">
				<form:select path="ossType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oss_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:common:photoGallery:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>