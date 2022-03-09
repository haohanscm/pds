<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务选项管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
                rules:{
                    goodsId:"required",
                    serviceName:"required",
                    serviceDetail:"required"
                },
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
		<li><a href="${ctx}/xiaodian/delivery/serviceSelection/">服务选项列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/serviceSelection/form?id=${serviceSelection.id}">服务选项<shiro:hasPermission name="xiaodian:delivery:serviceSelection:edit">${not empty serviceSelection.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:serviceSelection:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="serviceSelection" action="${ctx}/xiaodian/delivery/serviceSelection/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品id：</label>
			<div class="controls">
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务名称：</label>
			<div class="controls">
				<form:input path="serviceName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务内容：</label>
			<div class="controls">
				<form:input path="serviceDetail" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务价格：</label>
			<div class="controls">
				<form:input path="servicePrice" htmlEscape="false" class="input-xlarge number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务次数：</label>
			<div class="controls">
				<form:input path="serviceNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务周期：</label>
			<div class="controls">
                <form:select path="serviceSchedule" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="xiaodian:delivery:serviceSelection:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>