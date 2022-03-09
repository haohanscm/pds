<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
                rules:{
                    goodsId:"required",
                    deliverySchedule:"required",
                    arriveType:"required",
                    deliveryNum:"required"
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

            if($("#deliverySchedule option").length > 0){
                initMultiple("deliverySchedule", "${deliveryRules.deliverySchedule}");
            }
            if($("#deliveryType option").length > 0){
                initMultiple("deliveryType", "${deliveryRules.deliveryType}");
            }

		});

        function initMultiple(selectId, value){
            var valArray = value.split(",");
            $("#"+selectId).val(valArray).trigger("change");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/delivery/deliveryRules/">配送规则列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/deliveryRules/form?id=${deliveryRules.id}">配送规则<shiro:hasPermission name="xiaodian:delivery:deliveryRules:edit">${not empty deliveryRules.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:deliveryRules:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deliveryRules" action="${ctx}/xiaodian/delivery/deliveryRules/save" method="post" class="form-horizontal">
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
			<label class="control-label">配送周期选项：</label>
			<div class="controls">
				<form:select path="deliverySchedule" class="input-xlarge " multiple="multiple">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*可多选</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送时效：</label>
			<div class="controls">
                <form:select path="arriveType" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('delivery_arrive')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">每次配送数量：</label>
			<div class="controls">
				<form:input path="deliveryNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起送数量：</label>
			<div class="controls">
				<form:input path="minNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送计划类型：</label>
			<div class="controls">
                <form:select path="deliveryPlanType" class="input-xlarge ">
                    <form:options items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送方式选项：</label>
			<div class="controls">
				<%--<form:input path="deliveryType" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
				<form:select path="deliveryType" class="input-xlarge " multiple="multiple">
					<form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*可多选</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规则描述：</label>
			<div class="controls">
				<form:input path="rulesDesc" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">指定时间：</label>
			<div class="controls">
				<input name="specificDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${deliveryRules.specificDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">第一次配送间隔：</label>
			<div class="controls">
				<form:input path="startDayNum" htmlEscape="false" maxlength="20" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送总数量：</label>
			<div class="controls">
				<form:input path="deliveryTotalNum" htmlEscape="false" maxlength="20" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:delivery:deliveryRules:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>