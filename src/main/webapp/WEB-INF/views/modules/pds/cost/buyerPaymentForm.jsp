<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购商货款管理</title>
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
		<li><a href="${ctx}/pds/cost/buyerPayment/">采购商货款列表</a></li>
		<li class="active"><a href="${ctx}/pds/cost/buyerPayment/form?id=${buyerPayment.id}">采购商货款<shiro:hasPermission name="pds:cost:buyerPayment:edit">${not empty buyerPayment.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:cost:buyerPayment:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="buyerPayment" action="${ctx}/pds/cost/buyerPayment/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">货款编号：</label>
            <div class="controls">
                <form:input path="buyerPaymentId" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">平台商家：</label>
            <div class="controls">
                <form:select path="pmId" class="input-xlarge required" >
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">采购编号：</label>
			<div class="controls">
				<form:input path="buyId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购商：</label>
			<div class="controls">
				<form:select path="buyerId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${buyerList}" itemValue="id" itemLabel="buyerName" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送货日期：</label>
			<div class="controls">
				<input name="buyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${buyerPayment.buyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品数量：</label>
			<div class="controls">
				<form:input path="goodsNum" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购货款：</label>
			<div class="controls">
				<form:input path="buyerPayment" maxlength="10" htmlEscape="false" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">售后货款：</label>
			<div class="controls">
				<form:input path="afterSalePayment" maxlength="10" htmlEscape="false" class="input-xlarge number"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">售后单：</label>
            <div class="controls">
                <form:textarea path="serviceId" htmlEscape="false" rows="2" maxlength="128" class="input-xxlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">是否结算：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="pds:cost:buyerPayment:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>