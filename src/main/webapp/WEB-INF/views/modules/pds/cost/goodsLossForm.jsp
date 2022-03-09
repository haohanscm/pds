<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品损耗管理</title>
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
		<li><a href="${ctx}/pds/cost/goodsLoss/">商品损耗列表</a></li>
		<li class="active"><a href="${ctx}/pds/cost/goodsLoss/form?id=${goodsLoss.id}">商品损耗<shiro:hasPermission name="pds:cost:goodsLoss:edit">${not empty goodsLoss.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:cost:goodsLoss:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsLoss" action="${ctx}/pds/cost/goodsLoss/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
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
			<label class="control-label">商品ID：</label>
			<div class="controls">
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">商品规格：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="goodsModel" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">商品规格：</label>
			<div class="controls">
				<form:input path="goodsModel" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">损耗百分比：</label>
			<div class="controls">
				<form:input path="lossPercent" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">损耗阀值：</label>
			<div class="controls">
				<form:input path="lossLimit" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">损耗说明：</label>
			<div class="controls">
				<form:input path="lossExplain" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="pds:cost:goodsLoss:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>