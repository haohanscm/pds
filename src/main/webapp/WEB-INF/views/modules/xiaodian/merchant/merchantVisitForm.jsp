<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家拜访记录管理</title>
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
	<style>
		.msg {
			color:#FF0000 ;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/merchant/merchantVisit/">商家拜访记录列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/merchant/merchantVisit/form?id=${merchantVisit.id}">商家拜访记录<shiro:hasPermission name="xiaodian:merchant:merchantVisit:edit">${not empty merchantVisit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:merchant:merchantVisit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantVisit" action="${ctx}/xiaodian/merchant/merchantVisit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>商家：</label>
			<div class="controls">
				<form:select path="merchantDatabase.id" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${merchantDatabaseList}" itemLabel="regName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="merchantDatabase.id" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拜访地址：</label>
			<div class="controls">
				<form:input path="visitAddress" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="contact" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phoneNumber" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拜访时间：</label>
			<div class="controls">
				<input name="visitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${merchantVisit.visitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拜访内容：</label>
			<div class="controls">
				<form:textarea path="visitContent" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进展阶段：</label>
			<div class="controls">
				<form:select path="visitStep" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('merchant_visit_step')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拜访照片：</label>
			<div class="controls">
				<form:hidden id="visitPic" path="visitPic" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<sys:ckfinder input="visitPic" type="thumb"  uploadPath="/xiaodian/merchant/merchantVisit" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户经理：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${merchantVisit.user.id}" labelName="user.name" labelValue="${merchantVisit.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:merchant:merchantVisit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>