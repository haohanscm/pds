<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>售卖规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
                rules:{
                    goodsId:"required",
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/delivery/saleRules/">售卖规则列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/saleRules/form?id=${saleRules.id}">售卖规则<shiro:hasPermission name="xiaodian:delivery:saleRules:edit">${not empty saleRules.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:saleRules:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="saleRules" action="${ctx}/xiaodian/delivery/saleRules/save" method="post" class="form-horizontal">
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
			<label class="control-label">售卖区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${saleRules.area.id}" labelName="area.name"
								labelValue="${saleRules.area.name}"
								title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"
								notAllowSelectParent="false" notAllowSelectRoot="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">售卖时效：</label>
			<div class="controls">
				<form:input path="saleArriveType" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起售数量：</label>
			<div class="controls">
				<form:input path="minSaleNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">限制购买次数：</label>
			<div class="controls">
				<form:input path="limitBuyTimes" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送类型限制：</label>
			<div class="controls">
				<form:select path="saleDeliveryType" class="input-xlarge ">
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
			<shiro:hasPermission name="xiaodian:delivery:saleRules:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>