<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>赠品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
                rules:{
                    goodsId:"required",
                    giftId:"required",
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

            if($("#giftSchedule option").length > 0){
                initMultiple("giftSchedule", "${goodsGifts.giftSchedule}");
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
		<li><a href="${ctx}/xiaodian/delivery/goodsGifts/">赠品列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/goodsGifts/form?id=${goodsGifts.id}">赠品<shiro:hasPermission name="xiaodian:delivery:goodsGifts:edit">${not empty goodsGifts.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:goodsGifts:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsGifts" action="${ctx}/xiaodian/delivery/goodsGifts/save" method="post" class="form-horizontal">
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
			<label class="control-label">赠品id：</label>
			<div class="controls">
				<form:input path="giftId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠品名称：</label>
			<div class="controls">
				<form:input path="giftName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送规则：</label>
			<div class="controls">
				<form:input path="giftRule" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起始日期：</label>
			<div class="controls">
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${goodsGifts.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${goodsGifts.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送周期选项：</label>
			<div class="controls">
				<%--<form:input path="giftSchedule" htmlEscape="false" maxlength="64" class="input-xlarge"/>--%>
				<form:select path="giftSchedule" class="input-xlarge " multiple="multiple">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*可多选</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠品数量：</label>
			<div class="controls">
				<form:input path="giftNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">是否过期：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select path="isExpires" class="input-xlarge ">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">赠品图片：</label>
			<div class="controls">
				<form:hidden id="giftUrl" path="giftUrl" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="giftUrl" type="images" uploadPath="/goods/goodsGifts" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:delivery:goodsGifts:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>