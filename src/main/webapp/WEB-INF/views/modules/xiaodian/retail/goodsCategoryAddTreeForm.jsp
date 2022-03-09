<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零售商品分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        var shopMap;
		$(document).ready(function() {
			$("#inputForm").validate({
                rules: {
                    merchantId: "required",
                    shopId: "required",
                    remarks: "required"
                },
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			shopMap = ${shopMapJson};
            // 验证shopId是否已存在
            $("#shopId").change(function () {
                $("#btnSubmit").attr("disabled","disabled");
                var shopId = $("#shopId").val();
                var flag = false;
                if(shopId!=""){
                    if (shopMap.hasOwnProperty(shopId)) {
                        flag = true;
                    }
                    if (flag) {
                        $("#showMsg").text("该店铺已存在");
                    } else {
                        $("#showMsg").text("该店铺可添加");
                        $("#btnSubmit").removeAttr("disabled");
                    }
                }else{
                    $("#showMsg").text("");
                }
            });
            $("#btnSubmit").click(function () {
                var name = $("#name").val() || "默认小店";
                $("#name").val(name);
                $("#inputForm").submit();
            });
		});
	</script>
	<style>
		.msg {
			color:#FF0000 ;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/restaurant/restaurantCategory/">商品分类列表</a></li>
		<li class="active"><a href="#">按模板添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsCategory" action="${ctx}/xiaodian/retail/goodsCategory/addShop" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>商家ID：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>店铺ID：</label>
			<div class="controls">
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-xlarge "/>&nbsp;&nbsp;&nbsp;
				<span id="showMsg" class="msg"></span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label"><span class="msg">* </span>商品分类名称：</label>
            <div class="controls">
                <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge " placeholder="默认名称为:默认小店"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="msg">* </span>模板店铺：</label>
            <div class="controls">
                <form:select path="remarks" class="input-xlarge" >
                    <form:options items="${shopMap}" />
                </form:select>
            </div>
        </div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:retail:goodsCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>