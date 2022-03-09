<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
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

            // 商家id/名称关联
            $("#merchantId").change(function () {
                var merchantName = this.options[this.options.selectedIndex].text;
                $("#merchantName").val(merchantName);
            });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pds/business/pdsBuyer/">采购商列表</a></li>
		<li class="active"><a href="${ctx}/pds/business/pdsBuyer/form?id=${pdsBuyer.id}">采购商<shiro:hasPermission name="pds:business:pdsBuyer:edit">${not empty pdsBuyer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:business:pdsBuyer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="pdsBuyer" action="${ctx}/pds/business/pdsBuyer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:if test="${not empty pdsBuyer.id}">
			<div class="control-group">
				<label class="control-label">采购商ID：</label>
				<div class="controls">
					${pdsBuyer.id}
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">平台商家：</label>
			<div class="controls">
                <form:select path="pmId" class="input-xlarge required" >
                    <form:option value="" label="必选项"/>
                    <form:options items="${pmList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通行证ID：</label>
			<div class="controls">
				<form:input path="passportId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group" style="display:none;">
            <label class="control-label">商家名称：</label>
            <div class="controls">
                <form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select path="merchantId" class="input-xlarge required" >
					<form:option value="" label="必选项"/>
					<form:options items="${merchantList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购商全称：</label>
			<div class="controls">
				<form:input path="buyerName" htmlEscape="false" maxlength="64" class="input-xlarge required" placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购商简称：</label>
			<div class="controls">
				<form:input path="shortName" htmlEscape="false" maxlength="64" class="input-xlarge required" placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="contact" htmlEscape="false" maxlength="64" class="input-xlarge required" placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-xlarge required" placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信：</label>
			<div class="controls">
				<form:input path="wechatId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账期：</label>
			<div class="controls">
                <form:select path="payPeriod" class="input-xlarge">
                    <form:options items="${fns:getDictList('pds_pay_period')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购商地址：</label>
			<div class="controls">
				<form:textarea path="address" rows="4" htmlEscape="false" maxlength="200" class="input-xlarge required" placeholder="必填项"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">排序：</label>
            <div class="controls">
                <form:input path="sort" htmlEscape="false" maxlength="5" class="input-xlarge digits"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">采购商类型：</label>
            <div class="controls">
                <form:select path="buyerType" class="input-xlarge required">
                    <form:options items="${fns:getDictList('pds_buyer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">是否需确认订单：</label>
			<div class="controls">
                <form:select path="needConfirmation" class="input-xlarge">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">是否推送消息：</label>
			<div class="controls">
                <form:select path="needPush" class="input-xlarge">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
                <form:select path="status" class="input-xlarge">
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
			<shiro:hasPermission name="pds:business:pdsBuyer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>