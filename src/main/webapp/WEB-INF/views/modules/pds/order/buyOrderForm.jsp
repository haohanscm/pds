<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购单管理</title>
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
            var buyerList = ${fns:toJson(buyerList)};
			// 采购商选择
            $("#buyerId").change(function () {
                var buyerId = $("#buyerId").val();
                for (var i = 0; i < buyerList.length; i++) {
                    if(buyerList[i].id == buyerId){
                        $("#buyerUid").val(buyerList[i].uid);
                        $("#contact").val(buyerList[i].contact);
                        $("#telephone").val(buyerList[i].telephone);
                        $("#address").val(buyerList[i].address);
                        $("#buyerName").val(buyerList[i].buyerName);
                        break;
                    }
                }
            });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pds/order/buyOrder/">采购单列表</a></li>
		<li class="active"><a href="${ctx}/pds/order/buyOrder/form?id=${buyOrder.id}">采购单<shiro:hasPermission name="pds:order:buyOrder:edit">${not empty buyOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:order:buyOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="buyOrder" action="${ctx}/pds/order/buyOrder/save" method="post" class="form-horizontal">
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
			<label class="control-label">采购编号：</label>
			<div class="controls">
				<form:input path="buyId" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">零售单号：</label>
			<div class="controls">
				<form:input path="goodsOrderId" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购用户：</label>
			<div class="controls">
				<form:input path="buyerUid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购商：</label>
			<div class="controls">
				<%--<form:input path="buyerId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
				<form:select path="buyerId" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${buyerList}" itemLabel="buyerName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
        <div class="control-group" style="display: none">
            <label class="control-label">采购商名称：</label>
            <div class="controls">
                <form:input path="buyerName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">采购时间：</label>
			<div class="controls">
				<input name="buyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${buyOrder.buyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送货日期：</label>
			<div class="controls">
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${buyOrder.deliveryTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购批次：</label>
			<div class="controls">
				<form:select path="buySeq" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_seq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购需求：</label>
			<div class="controls">
				<form:input path="needNote" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总价预估：</label>
			<div class="controls">
				<form:input path="genPrice" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购总价：</label>
			<div class="controls">
				<form:input path="totalPrice" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="contact" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运费：</label>
			<div class="controls">
				<form:input path="shipFee" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送方式：</label>
			<div class="controls">
				<form:select path="deliveryType" class="input-xlarge required">
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
			<shiro:hasPermission name="pds:order:buyOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>