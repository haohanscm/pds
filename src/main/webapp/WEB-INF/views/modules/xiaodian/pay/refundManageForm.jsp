<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退款管理</title>
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

            $("#merchantName").change(function () {
                var merchantId = $("#merchantName").find("option:selected").attr("merchantId");
               // console.log(merchantId);
                $("#merchantId").val(merchantId);
            });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/pay/refundManage/">退款列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/pay/refundManage/form?id=${refundManage.id}">退款<shiro:hasPermission name="xiaodian:pay:refundManage:edit">${not empty refundManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:pay:refundManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="refundManage" action="${ctx}/xiaodian/pay/refundManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">商家名称：</label>
            <div class="controls">
                <form:select id="merchantName" path="merchantName" class="input-large">
                    <form:option value="" label=""/>
                    <c:forEach items="${merchantList}" var="merchant">
                        <form:option  value="${merchant.merchantName}" label="${merchant.merchantName}" merchantId="${merchant.id}"/>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商家ID：</label>
            <div class="controls">
                <form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge" readonly="true"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">请求流水号：</label>
			<div class="controls">
				<form:input path="requestId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户编号：</label>
			<div class="controls">
				<form:input path="partnerId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原交易流水号：</label>
			<div class="controls">
				<form:input path="orgReqId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原平台交易单号：</label>
			<div class="controls">
				<form:input path="orgTransId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<form:input path="orderAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付金额：</label>
			<div class="controls">
				<form:input path="payAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款金额：</label>
			<div class="controls">
				<form:input path="refundAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易类型：</label>
			<div class="controls">
				<form:select path="busType" class="input-large">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('trans_type')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请退款时间：</label>
			<div class="controls">
				<input name="refundApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${refundManage.refundApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款结果：</label>
			<div class="controls">
				<form:select path="status" class="input-large">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('refund_status')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回码：</label>
			<div class="controls">
				<form:input path="respCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回信息描述：</label>
			<div class="controls">
				<form:input path="respDesc" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款平台流水号：</label>
			<div class="controls">
				<form:input path="tradeNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退款返回时间：</label>
			<div class="controls">
				<input name="respTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${refundManage.respTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:pay:refundManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>