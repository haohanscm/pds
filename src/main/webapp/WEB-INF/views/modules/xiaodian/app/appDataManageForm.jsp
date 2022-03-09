<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据对象管理</title>
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
        // 若输入的不是json对象或""，表单不可提交
        function checkJSON(elem) {
            $("#btnSubmit").attr("disabled","disabled");
            var str = $(elem).val();
            if ("" == str || isJSON(str)) {
                $(elem).next("span").text("");
                $("#btnSubmit").removeAttr("disabled");
            } else {
                $(elem).next("span").text("请输入一个JSON对象！或空置");
                $(elem).focus();
            }
        }
        function isJSON(str){
            try {
                if (typeof JSON.parse(str) == 'object') {
                    return true;
                }
            } catch (e) {
                console.log("to JSON error:", e);
            }
            return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/app/appDataManage/">数据对象列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/app/appDataManage/form?id=${appDataManage.id}">数据对象<shiro:hasPermission name="xiaodian:app:appDataManage:edit">${not empty appDataManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:app:appDataManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appDataManage" action="${ctx}/xiaodian/app/appDataManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据对象ID：</label>
			<div class="controls">
				<form:input path="objId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据对象明细ID：</label>
			<div class="controls">
				<form:input path="objDetailId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">字段类型：</label>
            <div class="controls">
                <form:select path="fieldType" class="input-medium" disabled="true">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('app_field_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">字段值：</label>
            <div class="controls">
                <div id="showFieldValue"></div>
                <c:if test="${0 == appDataManage.fieldType}">
                    <form:textarea path="fieldValue" htmlEscape="false" maxlength="5000" class="input-xlarge "/>
                </c:if>
                <c:if test="${1 == appDataManage.fieldType}">
                    <form:hidden path="fieldValue" htmlEscape="false" maxlength="5000" class="input-xlarge"/>
                    <sys:ckfinder input="fieldValue" type="images" uploadPath="/appData/category"/>
                </c:if>
                <c:if test="${2 == appDataManage.fieldType}">
                    <form:textarea path="fieldValue" htmlEscape="false" maxlength="5000" class="input-xlarge " onblur="checkJSON(this)"/>&nbsp;&nbsp;<span></span>
                </c:if>
                <c:if test="${3 == appDataManage.fieldType}">
                    <form:textarea path="editValue" htmlEscape="false" maxlength="60000" class="input-xlarge"/>
                    <sys:ckeditor  replace="editValue" uploadPath="/appData/category" />
                </c:if>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:app:appDataManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>