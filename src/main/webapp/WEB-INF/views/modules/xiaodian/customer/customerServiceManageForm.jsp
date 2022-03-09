<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务管理管理</title>
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
		<li><a href="${ctx}/xiaodian/customer/customerServiceManage/">服务管理列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/customer/customerServiceManage/form?id=${customerServiceManage.id}">服务管理<shiro:hasPermission name="xiaodian:customer:customerServiceManage:edit">${not empty customerServiceManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:customer:customerServiceManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerServiceManage" action="${ctx}/xiaodian/customer/customerServiceManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select path="merchant.id" class="input-xlarge">
					<form:option value="${merchant.id}" label="${merchant.merchantName}"/>
					<form:options items="${merchants}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="merchant" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
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
			<label class="control-label">服务内容：</label>
			<div class="controls">
				<form:checkboxes path="serviceContents" items="${fns:getDictList('customer_service_list')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务描述：</label>
			<div class="controls">
				<form:textarea path="serviceDesc" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务时间：</label>
			<div class="controls">
				<input name="serviceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerServiceManage.serviceTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<form:select path="payType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收费：</label>
			<div class="controls">
				<form:input path="payAmount" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付时间：</label>
			<div class="controls">
				<input name="payTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerServiceManage.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务专管员：</label>
			<div class="controls">
				<sys:treeselect id="bizUser" name="bizUser.id" value="${customerServiceManage.bizUser.id}" labelName="bizUser.name" labelValue="${customerServiceManage.bizUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运营专管员：</label>
			<div class="controls">
				<sys:treeselect id="opUser" name="opUser.id" value="${customerServiceManage.opUser.id}" labelName="opUser.name" labelValue="${customerServiceManage.opUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">技术负责人：</label>
			<div class="controls">
				<sys:treeselect id="techUser" name="techUser.id" value="${customerServiceManage.techUser.id}" labelName="techUser.name" labelValue="${customerServiceManage.techUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">财务核对人：</label>
			<div class="controls">
				<sys:treeselect id="financeUser" name="financeUser.id" value="${customerServiceManage.financeUser.id}" labelName="financeUser.name" labelValue="${customerServiceManage.financeUser.name}"
								title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
					<%--<form:input path=".id" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务情况：</label>
			<div class="controls">
				<form:textarea path="serviceInfo" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务状态：</label>
			<div class="controls">
				<form:input path="serviceStatus" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户评分：</label>
			<div class="controls">
				<form:input path="custormEvaluate" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:customer:customerServiceManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>