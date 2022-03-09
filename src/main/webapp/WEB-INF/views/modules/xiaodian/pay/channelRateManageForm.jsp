<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>渠道费率管理</title>
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
			$("#merchantId").change(function () {
                $("#payInfoId").val(this.options[this.options.selectedIndex].value);
            })

		});

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/pay/channelRateManage/">渠道费率列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/pay/channelRateManage/form?id=${channelRateManage.id}">渠道费率<shiro:hasPermission name="xiaodian:pay:channelRateManage:edit">${not empty channelRateManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:pay:channelRateManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="channelRateManage" action="${ctx}/xiaodian/pay/channelRateManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商家名称：</label>
			<div class="controls">
				<%--<form:input path="merchatId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
				<form:select id="merchantId" path="merchantId" class="input-xlarge" >
					<form:option value="" label=""/>
					<form:options items="${merchantPayInfoList}" itemValue="merchantId" itemLabel="mercNm" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户ID：</label>
			<div class="controls">
				<form:input id="payInfoId" path="payInfoId" htmlEscape="false" maxlength="64" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付渠道：</label>
			<div class="controls">
				<form:select path="channel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费率：</label>
			<div class="controls">
				<form:input  path="rate" htmlEscape="false" maxlength="64" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">渠道标识：</label>
			<div class="controls">
				<form:input  path="custId" htmlEscape="false" maxlength="64" class="input-xlarge " />
			</div>
		</div>

<%--
		<div class="control-group">
			<label class="control-label">微信扫码：</label>
			<div class="controls">
				<form:input path="wxQrcode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信刷卡：</label>
			<div class="controls">
				<form:input path="wxPaycard" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信公众号：</label>
			<div class="controls">
				<form:input path="wxMp" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付宝扫码：</label>
			<div class="controls">
				<form:input path="alipayQrcode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付宝条码：</label>
			<div class="controls">
				<form:input path="alipayBarcode" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付宝服务窗：</label>
			<div class="controls">
				<form:input path="alipayService" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回状态码：</label>
			<div class="controls">
				<form:input path="respCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回描述：</label>
			<div class="controls">
				<form:textarea path="respMsg" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		--%>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bank_reg_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="xiaodian:pay:channelRateManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>