<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单配送信息管理</title>
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

		function genDeliveryPlan(orderId) {
		    if (confirm("如果计划已存在,将覆盖原有记录,确认继续吗?")){
                $.ajax({
                    url:"${ctx}/xiaodian/order/orderDelivery/genDeliveryPlan",
                    type:"POST",
                    data:{"orderId":orderId},
                    success:function () {
                        window.location.href="${ctx}/xiaodian/order/orderDelivery/list?message=${message}";
                    }
                });
			}
        }

        function emptyDeliveryPlan(orderId) {
            if (confirm("将清空该订单的所有配送计划,确认继续吗?")){
                $.ajax({
                    url:"${ctx}/xiaodian/order/orderDelivery/emptyDeliveryPlan",
                    type:"POST",
                    data:{"orderId":orderId},
                    success:function () {
                        window.location.href="${ctx}/xiaodian/order/orderDelivery/list?message=${message}";
                    }
                });
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/order/orderDelivery/">订单配送信息列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/order/orderDelivery/form?id=${orderDelivery.id}">订单配送信息<shiro:hasPermission name="xiaodian:order:orderDelivery:edit">${not empty orderDelivery.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:order:orderDelivery:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderDelivery" action="${ctx}/xiaodian/order/orderDelivery/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户名称：</label>
			<div class="controls">
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺ID：</label>
			<div class="controls">
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户ID：</label>
			<div class="controls">
				<form:input path="userId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单来源：</label>
			<div class="controls">
				<form:select path="orderFrom" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_from')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单类型：</label>
			<div class="controls">
				<form:select path="orderType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:select path="orderStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单号：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送费用：</label>
			<div class="controls">
				<form:input path="deliveryFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保价费用：</label>
			<div class="controls">
				<form:input path="insureFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付费用：</label>
			<div class="controls">
				<form:input path="payFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费用：</label>
			<div class="controls">
				<form:input path="serviceFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已付款金额：</label>
			<div class="controls">
				<form:input path="moneyPaid" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应付金额：</label>
			<div class="controls">
				<form:input path="orderAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款备注：</label>
			<div class="controls">
				<form:input path="payMark" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<%--<form:select path="payType" class="input-xlarge ">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
				<form:input path="payType" htmlEscape="false" maxlength="64" class="input-xlarge "/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				<form:select path="payStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<%--<div class="control-group">--%>
			<%--<label class="control-label">配送地址ID:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="memberAddressId" htmlEscape="false" maxlength="64" class="input-xlarge" />--%>
			<%--</div>--%>
		<%--</div>--%>
        <div class="control-group">
            <label class="control-label">省份:</label>
            <div class="controls">
                <form:input path="province" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">城市:</label>
            <div class="controls">
                <form:input path="city" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">地区:</label>
            <div class="controls">
                <form:input path="region" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">街道:</label>
            <div class="controls">
                <form:input path="street" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">所属片区:</label>
            <div class="controls">
                <form:input path="districtArea" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">小区名称:</label>
            <div class="controls">
                <form:input path="communityName" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">小区id:</label>
            <div class="controls">
                <form:input path="communityId" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">楼栋:</label>
            <div class="controls">
                <form:input path="buildingsNum" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">层数:</label>
            <div class="controls">
                <form:input path="floor" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">房号:</label>
            <div class="controls">
                <form:input path="houseNum" htmlEscape="false" maxlength="50" class="input-xlarge" />
            </div>
        </div>  
		<div class="control-group">
			<label class="control-label">详细地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮编：</label>
			<div class="controls">
				<form:input path="zipcode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">快递单号：</label>
			<div class="controls">
				<form:input path="expressOrder" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">快递公司：</label>
			<div class="controls">
				<form:input path="expressCompany" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">配送状态：</label>
			<div class="controls">
				<form:select path="deliveryStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_delivery_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送员ID：</label>
			<div class="controls">
				<form:input path="deliveryManId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送员姓名：</label>
			<div class="controls">
				<form:input path="deliveryManName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送员联系电话：</label>
			<div class="controls">
				<form:input path="deliveryManTel" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人：</label>
			<div class="controls">
				<form:input path="receiver" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="receiverMobile" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单留言：</label>
			<div class="controls">
				<form:input path="orderMark" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起送日期：</label>
			<div class="controls">
				<input name="startDeliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderDelivery.startDeliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预约配送时间：</label>
			<div class="controls">
				<form:input path="deliveryOntime" htmlEscape="false" maxlength="100" class="input-xlarge "/>
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
			<label class="control-label">计划生成状态：</label>
			<div class="controls">
				<form:select path="planGenStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('plan_gen_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成结果描述：</label>
			<div class="controls">
				<form:input path="planGenDesc" htmlEscape="false" maxlength="64" class="input-xlarge"/>
				<c:if test="${'2' eq orderDelivery.planGenStatus}">
					<button class="btn btn-primary" type="button" onclick="emptyDeliveryPlan('${orderDelivery.orderId}')">清空计划</button>
				</c:if>
				<c:if test="${'3' eq orderDelivery.planGenStatus}">
					<span class="help-inline"><font color="red">*已修改?&nbsp;点击重新生成配送计划&nbsp;&nbsp;</font> </span><button class="btn btn-primary" type="button" onclick="genDeliveryPlan('${orderDelivery.orderId}')">重新生成</button>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送方式：</label>
			<div class="controls">
				<form:select path="deliveryType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="xiaodian:order:orderDelivery:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>