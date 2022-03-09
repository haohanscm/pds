<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打印机管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
                rules: {
                    printerSn: "required",
                    printerKey: "required",
                    printerType: "required"
                },
                messages:{
                    printerSn: "请输入打印机编号",
                    printerKey: "请输入打印机秘钥",
                    printerType: "请选择打印机类型"
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
            if (!$("#status").val()) {
                $("#status").val("0").trigger("change");
            }
            $("#merchantId").change();
        });

        // 添加打印机至飞鹅云
        function addPrinter() {
            var printerSn = $("#printerSn").val();
            var printerKey = $("#printerKey").val();
            var printerName = $("#printerName").val();
            var printerContent = printerSn + "#" + printerKey;
            if (printerName != "") {
                printerContent += "#" + printerName;
            }
            if (printerSn != "" && printerKey != "") {
                // console.log("param", printerContent);
                $.getJSON("${ctxFront}/xiaodian/api/feieyun/addPrinter", {"printerContent": printerContent}, function (data) {
                    // console.log("data", data);
                    if (data.code == 200) {
                        var result = JSON.parse(data.ext);
                        // console.log("result", result);
                        if (result.ok.length>0) {
                            $("#status").val("1").trigger("change");
                            alertx("添加成功  " + result.ok.join());
                        } else {
                            $("#status").val("0").trigger("change");
                            alertx("添加失败  " + result.no.join());
                        }
                    } else {
                        $("#status").val("0").trigger("change");
                        alertx("结果信息:" + JSON.stringify(data));
                    }
                })
            } else{
                alertx("请添加打印机编码和秘钥！");
			}
        }

        function printOrder(){
            $("#printOrderBtn").attr("disabled","disabled");
            var orderId = $("#orderId").val();
            var id = $("#id").val();
            if(id){
                // console.log("id",id);
                $.get("${ctx}/xiaodian/printer/feiePrinter/printOrder",{id:id,orderId:orderId},function (data) {
                    try{
                        data = JSON.parse(data);
                    }catch (e) {
                    }
					console.log("data",data);
                    if (data.code == 200) {
                        alertx("打印成功  " + data.ext);
                    } else {
                        alertx("打印失败  "+ JSON.stringify(data));
                    }
                    $("#printOrderBtn").removeAttr("disabled");
                });
            }else {
                alertx("该打印机未保存!");
                $("#printOrderBtn").removeAttr("disabled");
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/printer/feiePrinter/">打印机列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/printer/feiePrinter/form?id=${feiePrinter.id}">打印机<shiro:hasPermission name="xiaodian:printer:feiePrinter:edit">${not empty feiePrinter.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:printer:feiePrinter:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="feiePrinter" action="${ctx}/xiaodian/printer/feiePrinter/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商家ID：</label>
			<div class="controls">
                <form:select path="merchantId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${merchantList}" itemLabel="merchantName" itemValue="id"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺：</label>
			<div class="controls">
				<form:select path="shopId" class="input-xlarge required">
					<form:option value="${feiePrinter.shopId}" label=""/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印机类型：</label>
			<div class="controls">
				<form:select path="printerType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('printer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印机编号：</label>
			<div class="controls">
				<form:input path="printerSn" htmlEscape="false" maxlength="64" class="input-xlarge " placeholder="必填"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印机秘钥：</label>
			<div class="controls">
				<form:input path="printerKey" htmlEscape="false" maxlength="64" class="input-xlarge " placeholder="必填"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印机名：</label>
			<div class="controls">
				<form:input path="printerName" htmlEscape="false" maxlength="64" class="input-xlarge " placeholder="必填"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印模板类型：</label>
			<div class="controls">
				<form:select path="templateType" class="input-xlarge ">
					<form:options items="${fns:getDictList('print_template_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打印模板,小票样式：</label>
			<div class="controls">
				<%--<form:input path="template" htmlEscape="false" class="input-xlarge "/>--%>
				<form:textarea path="template" htmlEscape="false" rows="4" class="input-xxlarge " placeholder="不填时使用默认"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">可打印分类：</label>
            <div class="controls">
                <form:textarea path="category" htmlEscape="false" rows="2" class="input-xxlarge " placeholder="可不填"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">打印次数：</label>
            <div class="controls">
                <form:select path="times" class="input-xlarge ">
                    <form:option value="1" label="1次"/>
                    <form:option value="2" label="2次"/>
                    <form:option value="3" label="3次"/>
                    <form:option value="4" label="4次"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">是否添加至飞鹅云：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
                    <form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
                &nbsp;&nbsp;&nbsp;<button id="addPrinterBtn" class="btn btn-primary" type="button" onclick="addPrinter()">添加该打印机至飞鹅云</button>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">启用状态：</label>
            <div class="controls">
                <form:select path="useable" class="input-xlarge ">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "  placeholder="使用默认样式时, 会在小票下方打印该项信息"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:printer:feiePrinter:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<div class="form-horizontal">
        <div class="control-group">
            <label class="control-label">使用该打印机打印订单：</label>
        </div>
		<div class="control-group">
			<label class="control-label">订单ID：</label>
			<div class="controls">
				<input id="orderId" type="text" class="input-xlarge" />
				<button id="printOrderBtn" type="button" class="btn btn-primary" onclick="printOrder()">使用打印机打印</button>
			</div>
		</div>
	</div>
</body>
</html>