<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品规格管理</title>
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
		<li><a href="${ctx}/xiaodian/retail/goodsModel/">商品规格列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/retail/goodsModel/form?id=${goodsModel.id}">商品规格<shiro:hasPermission name="xiaodian:retail:goodsModel:edit">${not empty goodsModel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:retail:goodsModel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsModel" action="${ctx}/xiaodian/retail/goodsModel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商品id：</label>
			<div class="controls">
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格名称：</label>
			<div class="controls">
				<form:input path="modelName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格价格：</label>
			<div class="controls">
				<form:input path="modelPrice" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">虚拟价格：</label>
			<div class="controls">
				<form:input path="virtualPrice" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参考成本价：</label>
			<div class="controls">
				<form:input path="costPrice" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格单位：</label>
			<div class="controls">
				<form:input path="modelUnit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格库存：</label>
			<div class="controls">
				<form:input path="modelStorage" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格商品图片地址：</label>
			<div class="controls">
				<form:hidden id="modelUrl" path="modelUrl" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="modelUrl" type="files" uploadPath="/xiaodian/retail/goodsModel" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格组合：</label>
			<div class="controls">
				<form:input path="model" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">即速应用规格ID：</label>
			<div class="controls">
				<form:input path="itemsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扫描购编码：</label>
			<div class="controls">
				<form:input path="modelCode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品规格编号：</label>
			<div class="controls">
				<form:input path="goodsModelSn" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品规格通用编号：</label>
			<div class="controls">
				<form:input path="modelGeneralSn" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                <span>&nbsp;&nbsp;&nbsp;公共商品库sku编号</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">第三方规格编号：</label>
			<div class="controls">
				<form:input path="thirdModelSn" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                <span>&nbsp;&nbsp;&nbsp;即速商品id</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格重量(kg)：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格体积(m3)：</label>
			<div class="controls">
				<form:input path="volume" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">扩展信息：</label>
			<div class="controls">
				<form:textarea path="modelInfo" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:retail:goodsModel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>