<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务评价管理</title>
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

		// change事件 根据merchantApps列表的索引关联
        function config(id, relativeId,e) {
            var index = e.options.selectedIndex;
            // console.log("index",index,e);
            var name = e.options[index].text;
            $("#" + id).val(name);
            var  relative = $("#" + relativeId);
            var relativeIndex = relative.get(0).options.selectedIndex;
            if(index!=relativeIndex){
                var rValue = relative.get(0).options[index].value;
                $("#" + relativeId).find("option[value='"+rValue+"']").attr("selected",true);
                $("#" + relativeId).select2().change();
                // console.log("rValue",rValue);
            }
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/customer/customerRevaluate/">服务评价列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/customer/customerRevaluate/form?id=${customerRevaluate.id}">服务评价<shiro:hasPermission name="xiaodian:customer:customerRevaluate:edit">${not empty customerRevaluate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:customer:customerRevaluate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerRevaluate" action="${ctx}/xiaodian/customer/customerRevaluate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--<form:hidden path="merchantApps" />--%>
        <div class="control-group">
            <label class="control-label">应用：</label>
            <div class="controls">
                <form:select path="appId" class="input-xlarge " onchange="config('appName','merchantId',this)">
                    <form:option value="" label=""/>
                    <form:options items="${merchantApps}" itemLabel="appName" itemValue="appId" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group" style="display: none">
            <label class="control-label">应用名称：</label>
            <div class="controls">
                <form:input path="appName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商家：</label>
            <div class="controls">
                <form:select path="merchantId" class="input-xlarge " onchange="config('merchantName','appId',this)">
                    <form:option value="" label=""/>
                    <form:options items="${merchantApps}" itemLabel="merchantName" itemValue="merchantId" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group" style="display: none">
            <label class="control-label">商家名称：</label>
            <div class="controls">
                <form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">服务类型：</label>
			<div class="controls">
				<form:select path="serviceType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评分人ID：</label>
			<div class="controls">
				<form:input path="evaluateUid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评分人姓名：</label>
			<div class="controls">
				<form:input path="evaluateName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:customer:customerRevaluate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>