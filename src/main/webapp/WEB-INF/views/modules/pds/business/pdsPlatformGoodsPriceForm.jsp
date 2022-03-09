<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台商品定价管理</title>
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
		<li><a href="${ctx}/pds/business/pdsPlatformGoodsPrice/">平台商品定价列表</a></li>
		<li class="active"><a href="${ctx}/pds/business/pdsPlatformGoodsPrice/form?id=${pdsPlatformGoodsPrice.id}">平台商品定价<shiro:hasPermission name="pds:business:pdsPlatformGoodsPrice:edit">${not empty pdsPlatformGoodsPrice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:business:pdsPlatformGoodsPrice:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="pdsPlatformGoodsPrice" action="${ctx}/pds/business/pdsPlatformGoodsPrice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">平台商家：</label>
			<div class="controls">
				<form:select path="pmId" class="input-xlarge required">
					<form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购商商家：</label>
			<div class="controls">
				<form:select path="merchantId" class="input-xlarge required">
					<form:option value="" label=""/>
                    <form:options items="${merchantList}" itemLabel="merchantName" itemValue="merchantId" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">采购商：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select path="buyerId" class="input-xlarge ">--%>
					<%--<form:option value="" label=""/>--%>
                    <%--<form:options items="${buyerList}" itemLabel="buyerName" itemValue="id" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">商品分类ID：</label>
			<div class="controls">
				<form:input path="categoryId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品spuID：</label>
			<div class="controls">
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品skuID：</label>
			<div class="controls">
				<form:input path="modelId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品分类名称：</label>
			<div class="controls">
				<form:input path="categoryName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格名称：</label>
			<div class="controls">
				<form:input path="modelName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购价：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="10" class="input-xlarge number required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${pdsPlatformGoodsPrice.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${pdsPlatformGoodsPrice.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
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
			<shiro:hasPermission name="pds:business:pdsPlatformGoodsPrice:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>