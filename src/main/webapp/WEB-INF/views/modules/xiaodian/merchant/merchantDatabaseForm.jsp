<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家资料管理</title>
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
            // 默认值为0 否
            var status = "${merchantDatabase.status}";
            if (!status){
                $("#status").val("0").trigger("change");
            }
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/merchant/merchantDatabase/">商家资料列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/merchant/merchantDatabase/form?id=${merchantDatabase.id}">商家资料<shiro:hasPermission name="xiaodian:merchant:merchantDatabase:edit">${not empty merchantDatabase.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:merchant:merchantDatabase:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantDatabase" action="${ctx}/xiaodian/merchant/merchantDatabase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商户注册全称：</label>
			<div class="controls">
				<form:input path="regName" htmlEscape="false" maxlength="64" class="input-xlarge "/> (必填)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营法人：</label>
			<div class="controls">
				<form:input path="regUser" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${merchantDatabase.area.id}" labelName="area.name" labelValue="${merchantDatabase.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营地址：</label>
			<div class="controls">
				<form:input path="opAddress" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家联系人：</label>
			<div class="controls">
				<form:input path="contact" htmlEscape="false" maxlength="64" class="input-xlarge "/> (必填)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系手机：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-xlarge "/> (必填)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机电话：</label>
			<div class="controls">
				<form:input path="phoneNumber" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商户类别：</label>
			<div class="controls">
				<form:select path="merchantType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('merchant_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业类别：</label>
			<div class="controls">
				<sys:treeselect id="industry" name="industry" value="${industry.id}" labelName="industry.name"
								labelValue="${industry.name}"
								title="行业分类" url="/xiaodian/industry/treeData" cssClass="" allowClear="true"
								notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网站名称：</label>
			<div class="controls">
				<form:input path="website" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">淘宝店名称：</label>
			<div class="controls">
				<form:input path="taobaoShop" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现有推广平台：</label>
			<div class="controls">
				<form:checkboxes path="marketPlatformMore" items="${fns:getDictList('market_platform')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现有支付工具：</label>
			<div class="controls">
				<form:checkboxes path="payToolsMore" items="${fns:getDictList('pay_tools')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺名称：</label>
			<div class="controls">
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营面积：</label>
			<div class="controls">
				<form:input path="operateArea" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">员工人数：</label>
			<div class="controls">
				<form:input path="workerNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺介绍：</label>
			<div class="controls">
				<form:textarea path="shopDesc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业时间：</label>
			<div class="controls">
				<form:input path="serviceTime" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务介绍：</label>
			<div class="controls">
				<form:textarea path="bizDesc" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺地址：</label>
			<div class="controls">
				<form:textarea path="shopAddress" htmlEscape="false" maxlength="500" class="input-xlarge "/> (必填)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照：</label>
			<div class="controls">
					<form:hidden id="bizLicense" path="bizLicense" htmlEscape="false" maxlength="200" class="input-xlarge"/>
					<sys:ckfinder input="bizLicense" type="thumb"  uploadPath="/xiaodian/merchant/merchantDataBaseFiles" selectMultiple="true"/>
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">周围环境：</label>
			<div class="controls">
				<form:textarea path="environment" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺服务：</label>
			<div class="controls">
				<form:input path="shopService" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年经营流水：</label>
			<div class="controls">
				<form:input path="shopSale" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">照片资料：</label>
			<div class="controls">
				<form:hidden id="pictureFile" path="pictureFile" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<sys:ckfinder input="pictureFile" type="thumb"  uploadPath="/xiaodian/merchant/merchantDataBaseFiles" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收录时间：</label>
			<div class="controls">
				<input name="initTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${merchantDatabase.initTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商机来源：</label>
			<div class="controls">
				<form:select path="bizfromType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bizfrom_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
        <div class="control-group">
			<label class="control-label">是否审核：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
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
			<shiro:hasPermission name="xiaodian:merchant:merchantDatabase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>