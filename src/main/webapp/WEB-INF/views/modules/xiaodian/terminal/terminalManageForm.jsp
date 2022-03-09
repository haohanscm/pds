<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备管理</title>
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

            $("#merchantId").change(function () {
                // $("#merchantId").val(this.options[this.options.selectedIndex].text);
                var merchantId = $("#merchantId").find("option:selected").attr("merchantId");
                // alert(merchantId);
                jQuery.ajax({
                    url: '${ctx}/xiaodian/merchant/shop/fetchShops',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        merchantId: merchantId,
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            var _data = result.data;
                            _data = $.parseJSON(_data);
                            if (null == _data || ( 0 == _data.length)) {
                                alert("商家没有店铺");
                                return;
                            }
                            // console.log(_data);
                            var shop = $("#shopId");
                            shop.empty();
                            $.each(_data, function (n, entity) {
                                shop.append($("<option>").attr({"value": entity.id}).text(entity.name));
                            });
                            shop.change();
                        }
                    }
                });

            });


            $("#shopId").change(function () {
                var shopName = this.options[this.options.selectedIndex].text;
//                console.log(shopName);
                $("#shopName").val(shopName);
            });

		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/terminal/terminalManage/">设备列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/terminal/terminalManage/form?id=${terminalManage.id}">设备<shiro:hasPermission name="xiaodian:terminal:terminalManage:edit">${not empty terminalManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:terminal:terminalManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="terminalManage" action="${ctx}/xiaodian/terminal/terminalManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select id="merchantId" path="merchantId" class="input-xlarge">
					<form:option value="" label=""/>
					<c:forEach items="${merchantList}" var="merchant">
						<form:option  value="${merchant.id}" label="${merchant.merchantName}" merchantId="${merchant.id}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>

		<div class="control-group" style="display: none">
			<label class="control-label">店铺名称：</label>
			<div class="controls">
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺:</label>
			<div class="controls">
				<form:select id="shopId" path="shopId" class="input-xlarge">
					<form:option value="${terminalManage.shopId}" label="${terminalManage.shopName}"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备编号：</label>
			<div class="controls">
				<form:input path="terminalNo" disabled="true" htmlEscape="false" maxlength="24" class="input-xlarge"/>
				<span class="help-inline"><font color="red">*自增,非主键</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备类型：</label>
			<div class="controls">
				<form:select path="terminalType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('terminal_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备别名：</label>
			<div class="controls">
				<form:input path="alias" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SN码：</label>
			<div class="controls">
				<form:input path="snCode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">制造厂商：</label>
			<div class="controls">
				<form:input path="producer" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">IMEI：</label>
			<div class="controls">
				<form:input path="imeiCode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购货时间：</label>
			<div class="controls">
				<input name="purchaseTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${terminalManage.purchaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出库时间：</label>
			<div class="controls">
				<input name="sellTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${terminalManage.sellTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">设备状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:terminal:terminalManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>