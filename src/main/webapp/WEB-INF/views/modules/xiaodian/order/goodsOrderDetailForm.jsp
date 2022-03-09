<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品订单明细管理</title>
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
		<li><a href="${ctx}/xiaodian/order/goodsOrderDetail/">商品订单明细列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/order/goodsOrderDetail/form?id=${goodsOrderDetail.id}">商品订单明细<shiro:hasPermission name="xiaodian:order:goodsOrderDetail:edit">${not empty goodsOrderDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:order:goodsOrderDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsOrderDetail" action="${ctx}/xiaodian/order/goodsOrderDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家id：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品编号：</label>
			<div class="controls">
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SKU ID：</label>
			<div class="controls">
				<form:input path="modelId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际售价：</label>
			<div class="controls">
				<form:input path="goodsPrice" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场售价：</label>
			<div class="controls">
				<form:input path="marketPrice" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品数量：</label>
			<div class="controls">
				<form:input path="goodsNum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品单位：</label>
			<div class="controls">
				<form:input path="goodsUnit" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品属性集合：</label>
			<div class="controls">
				<form:input path="goodsAttrIds" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展属性：</label>
			<div class="controls">
				<form:input path="extAttr" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否实物：</label>
			<div class="controls">
				<form:radiobuttons path="isReal" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单类型：</label>
			<div class="controls">
				<form:select path="cartGoodsType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('goods_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动编号：</label>
			<div class="controls">
				<form:input path="activityId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠品名称：</label>
			<div class="controls">
				<form:input path="giftName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠品id：</label>
			<div class="controls">
				<form:input path="giftId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送周期：</label>
			<div class="controls">
				<form:input path="giftSchedule" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送数量：</label>
			<div class="controls">
				<form:input path="giftNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送周期：</label>
			<div class="controls">
				<form:radiobuttons path="deliverySchedule" items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送起始时间：</label>
			<div class="controls">
				<input name="deliveryStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${goodsOrderDetail.deliveryStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送时效：</label>
			<div class="controls">
				<form:radiobuttons path="arriveType" items="${fns:getDictList('delivery_arrive')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">每次配送数量：</label>
			<div class="controls">
				<form:input path="deliveryNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">起送数量：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="minDeliveryNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">配送计划类型：</label>
			<div class="controls">
				<form:radiobuttons path="deliveryPlanType" items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送方式：</label>
			<div class="controls">
				<form:radiobuttons path="deliveryType" items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送总数量：</label>
			<div class="controls">
				<form:input path="deliveryTotalNum" htmlEscape="false" maxlength="8" class="input-xlarge digist"/>
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
				<form:input path="servicePrice" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务周期：</label>
			<div class="controls">
				<form:input path="serviceSchedule" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务次数：</label>
			<div class="controls">
				<form:input path="serviceNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:order:goodsOrderDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>