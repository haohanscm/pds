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
            // console.log(str, str == "", str == null);
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
		<li><a href="${ctx}/xiaodian/app/appData/">数据对象列表</a></li>
		<li><a href="${ctx}/xiaodian/app/appData/form?id=${appDataManage.id}">数据对象<shiro:hasPermission name="xiaodian:app:appDataManage:edit">${not empty appDataManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:app:appDataManage:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/xiaodian/app/appData/editData/${appDataManageInfo.seqNum}?id=${appData.id}">数据对象修改</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appDataManageInfo" action="${ctx}/xiaodian/app/appDataManage/saveAppDatas" method="post" class="form-horizontal">
		<form:hidden path="appId" value="${appData.appId}"/>
		<form:hidden path="appDataId" value="${appData.id}"/>
		<div class="control-group">
			<label class="control-label">行号</label>
			<div class="controls">
			<form:input path="seqNum" value="${appDataManageInfo.seqNum}" cssStyle="width: 300px"/>
				<span style="color: #FF0000"> 控制行的排序，可修改首位为0-9，来调整所有行的相对顺序</span>
			</div>
		</div>
		<sys:message content="${message}"/>
		<c:if test="${not empty appDataManageList}">
			<c:forEach items="${appDataManageList}" var="manage" varStatus="i">
				<form:hidden path="appDataManageList[${i.index}].id" value="${manage.id}"/>
			</c:forEach>
		</c:if>
		<c:forEach items="${appData.appDataDetailList}" var="detail" varStatus="vs">

			<form:hidden path="appDataManageList[${vs.index}].fieldType" value="${detail.fieldType}"/>
			<form:hidden path="appDataManageList[${vs.index}].objDetailId" value="${detail.id}"/>

			<div class="control-group">
				<label class="control-label">${detail.fieldName}</label>
				<div class="controls">
					<c:if test="${0 == detail.fieldType}">
						<form:textarea path="appDataManageList[${vs.index}].fieldValue"  htmlEscape="false" maxlength="5000" class="input-xlarge "/>
					</c:if>

					<c:if test="${1 == detail.fieldType}">
						<form:hidden id="image_${vs.index}" path="appDataManageList[${vs.index}].fieldValue" htmlEscape="false" maxlength="5000" class="input-xlarge"/>
						<sys:ckfinder input="image_${vs.index}" type="images" uploadPath="/appData/category"/>
					</c:if>
					<c:if test="${2 == detail.fieldType}">
                        <form:textarea path="appDataManageList[${vs.index}].fieldValue"  htmlEscape="false" maxlength="5000" class="input-xlarge " onblur="checkJSON(this)"/>&nbsp;&nbsp;<span></span>
					</c:if>

					<c:if test="${3 == detail.fieldType}">
						<%--<c:set var="editValue" value="${appDataManageList[vs.index].editValue}"/>--%>
						<%--<c:set var="finalValue" value="${fns:unescapeHtml(fns:Base64Decode(editValue))}"/>--%>
						<%--<c:out value="${finalValue}"/>--%>
						<form:textarea id="editValue_${vs.index}" path="appDataManageList[${vs.index}].editValue"  htmlEscape="false" maxlength="60000" class="input-xlarge"/>
						<sys:ckeditor  replace="editValue_${vs.index}" uploadPath="/appData/category" />
					</c:if>
				</div>
				</div>


		</c:forEach>

<%--
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
			<label class="control-label">字段值：</label>
			<div class="controls">
				<form:input path="fieldValue" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">富文本：</label>
			<div class="controls">
				<form:input path="editValue" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段类型：</label>
			<div class="controls">
				<form:input path="fieldType" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">备注信息：</label>
				<div class="controls">
					<form:textarea path="appDataManageList[${vs.index}].remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				</div>
			</div>

		--%>

		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:app:appDataManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>