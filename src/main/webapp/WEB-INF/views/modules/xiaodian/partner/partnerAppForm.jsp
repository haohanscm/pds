<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>厂商应用管理管理</title>
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
        // 获取随机key-secret
        function fetchKey(){
            $("#fetchKeyBtn").attr("disabled","disabled");
            var key = $("#appKey").val();
            var secret = $("#appSecret").val();
            if(key=="" || secret=="" ){
                $.getJSON("${ctx}/xiaodian/partner/partnerApp/fetchKey",function(data){
                    $("#appKey").val(data["key"]);
                    $("#appSecret").val(data["secret"]);
                    $("#fetchKeyBtn").removeAttr("disabled");
                });
            }else{
                alertx("已有KEY-秘钥");
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/partner/partnerApp/">厂商应用管理列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/partner/partnerApp/form?id=${partnerApp.id}">厂商应用管理<shiro:hasPermission name="xiaodian:partner:partnerApp:edit">${not empty partnerApp.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:partner:partnerApp:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="partnerApp" action="${ctx}/xiaodian/partner/partnerApp/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">厂商名称：</label>
			<div class="controls">
				<form:input path="partnerName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">渠道编号：</label>
			<div class="controls">
				<form:input path="partnerNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用KEY：</label>
			<div class="controls">
                <form:input path="appKey" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
                <c:if test="${empty partnerApp.appKey}">
                    <input id="fetchKeyBtn" class="btn btn-primary" type="button" value="生成KEY-秘钥" onclick="fetchKey()"/>
                </c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用秘钥：</label>
			<div class="controls">
				<form:input path="appSecret" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厂商说明：</label>
			<div class="controls">
				<form:textarea path="partnerDesc" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厂商联系人：</label>
			<div class="controls">
				<form:input path="contactUser" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="contactPhone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厂商地址：</label>
			<div class="controls">
				<form:textarea path="partnerAddress" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调地址：</label>
			<div class="controls">
				<form:textarea path="notifyUrl" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厂商类型：</label>
			<div class="controls">
				<form:select path="partnerType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('partner_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="xiaodian:partner:partnerApp:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>