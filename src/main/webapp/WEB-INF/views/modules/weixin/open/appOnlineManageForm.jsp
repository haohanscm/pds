<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用上线记录管理</title>
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
		<li><a href="${ctx}/weixin/open/appOnlineManage/">应用上线记录</a></li>
		<li class="active"><a href="${ctx}/weixin/open/appOnlineManage/form?id=${appOnlineManage.id}">应用上线记录<shiro:hasPermission name="weixin:open:appOnlineManage:edit">${not empty appOnlineManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:open:appOnlineManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appOnlineManage" action="${ctx}/weixin/open/appOnlineManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用名称：</label>
			<div class="controls">
				<form:input path="appName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家ID：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家名称：</label>
			<div class="controls">
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">步骤名称：</label>
			<div class="controls">
				<form:input path="stepName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">步骤序号：</label>
			<div class="controls">
				<form:input path="stepNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请求值：</label>
			<div class="controls">
				<form:textarea path="reqParams" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回值：</label>
			<div class="controls">
				<form:textarea path="respParams" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<c:if test="${appOnlineManage.status == 200 && not empty appOnlineManage.respParams && appOnlineManage.stepNo == 4}">
			<div class="control-group">
				<label class="control-label">二维码：</label>
				<div class="controls">
					<img src="${appOnlineManage.respParams}" style="height: 150px;width: 150px;">
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_online_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">渠道：</label>
			<div class="controls">
				<form:select path="channel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作类型：</label>
			<div class="controls">
				<form:select path="opType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_op_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请求方法：</label>
			<div class="controls">
				<form:select path="reqMethod" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('http_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请求地址：</label>
			<div class="controls">
				<form:input path="reqUrl" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请求时间：</label>
			<div class="controls">
				<input name="reqTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${appOnlineManage.reqTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回时间：</label>
			<div class="controls">
				<input name="respTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${appOnlineManage.respTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="weixin:open:appOnlineManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>