<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>易联云管理</title>
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

            $("#bindOpenPlatformApp").click(
                function () {
                    var id = $("#id").val();
                    jQuery.ajax({
                        url: '${ctx}/xiaodian/printer/cloudPrintTerminal/bindApp',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            id: id,
                        },
                        success: function (result) {
                            if (result.code == 200) {
                                $("#messageBox").text(result.msg);
                                alert(result.msg);
                                window.location.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            )

            // 商家改变联动店铺改变
            $("#merchantId").change(function () {
                var merchantId = $("#merchantId").find("option:selected").val();
                if(!merchantId){
                    return;
                }
                var shopId = $("#shopId").val();
                jQuery.ajax({
                    url: '${ctx}/xiaodian/merchant/shop/fetchShops',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        merchantId: merchantId,
                    },
                    success: function (result) {
                        var shop = $("#shopId");
                        if (result.code == 200) {
                            var _data = result.data;
                            _data = $.parseJSON(_data);
                            if (null == _data || ( 0 == _data.length)) {
                                shop.empty();
                                shop.append($("<option>").attr({"value": ""}).text(""));
                                shop.change();
                                alertx("商家没有店铺");
                                return;
                            }
                            shop.empty();
                            $.each(_data, function (n, entity) {
                                shop.append($("<option>").attr({"value": entity.id}).text(entity.name));
                            });
                            shop.select2("val", shopId);
                            shop.change();
                        }
                    }
                });

            });

            // 初始化
            $("#merchantId").change();
		});


	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/printer/cloudPrintTerminal/">易联云列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/printer/cloudPrintTerminal/form?id=${cloudPrintTerminal.id}">易联云<shiro:hasPermission name="xiaodian:printer:cloudPrintTerminal:edit">${not empty cloudPrintTerminal.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:printer:cloudPrintTerminal:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cloudPrintTerminal" action="${ctx}/xiaodian/printer/cloudPrintTerminal/save" method="post" class="form-horizontal">
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
		<div class="control-group">
			<label class="control-label">店铺：</label>
			<div class="controls">
                <form:select path="shopId" class="input-xlarge required">
                    <form:option value="${cloudPrintTerminal.shopId}" label=""/>
                </form:select>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">应用ID：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="clientId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">应用：</label>
			<div class="controls">
				<form:select id="clientId" path="clientId" class="input-medium">
					<form:option value="" label=""/>
					<c:forEach items="${openPlatformConfigList}" var="app">
						<form:option value="${app.appId}" label="${app.appName}"/>
					</c:forEach>
				</form:select>
				<input type="button" value="绑定应用" id="bindOpenPlatformApp" class="btn btn-primary"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印机编号：</label>
			<div class="controls">
				<form:input path="machineCode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密钥：</label>
			<div class="controls">
				<form:input path="secret" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印机名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_is_enable')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
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
			<shiro:hasPermission name="xiaodian:printer:cloudPrintTerminal:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>