<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>售后单管理</title>
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
    <style>
        .msg {
            color:#FF0000 ;
        }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pds/order/serviceOrder/">售后单列表</a></li>
		<li class="active"><a href="${ctx}/pds/order/serviceOrder/form?id=${serviceOrder.id}">售后单<shiro:hasPermission name="pds:order:serviceOrder:edit">${not empty serviceOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:order:serviceOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="serviceOrder" action="${ctx}/pds/order/serviceOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">平台商家：</label>
            <div class="controls">
                <form:select path="pmId" class="input-xlarge required" >
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
                </form:select>
                <span class="msg">必选</span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">售后单号：</label>
			<div class="controls">
				<form:input path="serviceId" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易单号：</label>
			<div class="controls">
				<form:input path="tradeId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送编号：</label>
			<div class="controls">
				<form:input path="deliveryId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购单号：</label>
			<div class="controls">
				<form:input path="buyId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                <span class="msg">采购商售后必选</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购商：</label>
			<div class="controls">
				<form:select path="buyerId" class="input-xlarge " >
					<form:option value="" label="请选择"/>
					<form:options items="${buyerList}" itemValue="id" itemLabel="buyerName" htmlEscape="false"/>
				</form:select>
                <span class="msg">采购商售后必选</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供应商：</label>
			<div class="controls">
				<form:select path="supplierId" class="input-xlarge" >
					<form:option value="" label="请选择"/>
					<form:options items="${supplierList}" itemValue="id" itemLabel="supplierName" htmlEscape="false"/>
				</form:select>
                <span class="msg">供应商售后必选</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理结果：</label>
			<div class="controls">
				<form:input path="dealResult" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈图文：</label>
			<div class="controls">
				<c:forEach items="${serviceOrder.feedbackImgList}" var="imgUrl">
					<img src="${imgUrl}" class="img-rounded" width="200px" height=""/>
				</c:forEach>
				<br>
				<form:textarea path="feedbackNote" rows="3" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
			<%--<div class="controls">--%>
				<%--<form:input path="feedbackInfo" htmlEscape="false" class="input-xlarge "/>--%>
			<%--</div>--%>
		</div>
		<div class="control-group">
			<label class="control-label">售后单金额(元)：</label>
			<div class="controls">
				<form:input path="refundAmount"  maxlength="10" htmlEscape="false" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送时间：</label>
			<div class="controls">
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${serviceOrder.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">售后分类：</label>
			<div class="controls">
				<form:select path="serviceCategory" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_service_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
                <span class="msg">必选</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阶段：</label>
			<div class="controls">
				<form:select path="stage" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_process_stage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="linkMan" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_service_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
                <span class="msg">必选;状态为已解决时生成货款才会计入</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="linkPhone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="pds:order:serviceOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>