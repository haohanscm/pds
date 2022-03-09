<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理管理</title>
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
		<li><a href="${ctx}/xiaodian/customer/customerProjectManage/">项目管理列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/customer/customerProjectManage/form?id=${customerProjectManage.id}">项目管理<shiro:hasPermission name="xiaodian:customer:customerProjectManage:edit">${not empty customerProjectManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:customer:customerProjectManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customerProjectManage" action="${ctx}/xiaodian/customer/customerProjectManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">季度：</label>
			<div class="controls">
				<label>${customerProjectManage.quarter}</label>
				<%--<label>${fns:getYearSeason(customerProjectManage.signTime)}</label>--%>
				<%--<form:input path="quarter" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签约时间：</label>
			<div class="controls">
				<input name="signTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerProjectManage.signTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家名称：</label>
			<div class="controls">
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${customerProjectManage.area.id}" labelName="area.name" labelValue="${customerProjectManage.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品：</label>
			<div class="controls">
				<form:select path="serviceProduct" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('service_product')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务版本：</label>
			<div class="controls">
				<form:select path="serviceType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开通功能：</label>
			<div class="controls">
				<form:checkboxes path="serviceLists" items="${fns:getDictList('product_service_list')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
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
			<label class="control-label">支付时间：</label>
			<div class="controls">
				<input name="payTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerProjectManage.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付金额：</label>
			<div class="controls">
				<form:input path="payAmount" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务专管员：</label>
			<div class="controls">
				<sys:treeselect id="bizUser" name="bizUser.id" value="${customerProjectManage.bizUser.id}" labelName="bizUser.name" labelValue="${customerProjectManage.bizUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运营专管员：</label>
			<div class="controls">
				<sys:treeselect id="opUser" name="opUser.id" value="${customerProjectManage.opUser.id}" labelName="opUser.name" labelValue="${customerProjectManage.opUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">技术负责人：</label>
			<div class="controls">
				<sys:treeselect id="techUser" name="techUser.id" value="${customerProjectManage.techUser.id}" labelName="techUser.name" labelValue="${customerProjectManage.techUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">财务核对人：</label>
			<div class="controls">
				<sys:treeselect id="financeUser" name="financeUser.id" value="${customerProjectManage.financeUser.id}" labelName="financeUser.name" labelValue="${customerProjectManage.financeUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目情况：</label>
			<div class="controls">
				<form:checkboxes path="projectInfos" items="${fns:getDictList('project_info_list')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他说明：</label>
			<div class="controls">
				<form:textarea path="projectDesc" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">项目阶段：</label>
			<div class="controls">
				<form:select path="projectStep" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_step')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上线状态：</label>
			<div class="controls">
				<form:select path="onlineStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('project_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上线时间：</label>
			<div class="controls">
				<input name="onlineTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${customerProjectManage.onlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家资源库ID：</label>
			<div class="controls">
				<form:input path="merchantDatabase" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家ID：</label>
			<div class="controls">
				<form:input path="merchant" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家应用ID：</label>
			<div class="controls">
				<form:input path="merchantApp" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:customer:customerProjectManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>