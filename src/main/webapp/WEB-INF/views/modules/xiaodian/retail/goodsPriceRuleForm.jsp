<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>定价规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
            $("#inputForm").validate({
                rules:{
                    shopId:"required",
                    goodsId:"required",
                    wholesalePrice:"number",
                    vipPrice:"number",
                    marketPrice: {
                        required: true,
                        number: true
                    }
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
                        // 移除select下拉框验证时产生的label
                        $(element).siblings("label").remove();
                        error.insertAfter(element);
                    }
                }
            });

			// 默认规则名称
			$("#btnSubmit").click(function () {
			    var name = $("#ruleName").val() || "简单价格" ;
                $("#ruleName").val(name);
				$("#inputForm").submit();
            });
			// shopId变化时重载
            $("#shopId").change(function () {
                selectOptions();
            });

            if ("${goodsPriceRule.goodsId}" != "") {
                selectOptions();
            }

		});
        // 填充下拉选项 商品
        function selectOptions() {
            var merchantId = $("#merchantId").val();
            var shopId = $("#shopId").val();
            var params = {
                "merchantId": merchantId,
                "shopId": shopId
            };
            if (merchantId != "" || shopId != ""){
                $.getJSON("${ctx}/xiaodian/retail/goods/fetchGoodsList",params,function (options) {
                    $("#goodsId").blur();
                    $("#goodsId").select2({
                        data: options,
                        multiple:false,
                        placeholder:'请选择',//默认文字提示
                        language: "zh-CN",//汉化
                        allowClear: true//允许清空
                    });
                });
            }else{
                alertx("请输入商家ID和店铺ID!");
            }
        }

	</script>
	<style>
		.msg {
			color:#FF0000 ;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/retail/goodsPriceRule/">定价规则列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/retail/goodsPriceRule/form?id=${goodsPriceRule.id}">定价规则<shiro:hasPermission name="xiaodian:retail:goodsPriceRule:edit">${not empty goodsPriceRule.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:retail:goodsPriceRule:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsPriceRule" action="${ctx}/xiaodian/retail/goodsPriceRule/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">规则名称：</label>
			<div class="controls">
				<form:input path="ruleName" htmlEscape="false" maxlength="64" class="input-xlarge " placeholder="默认：简单价格" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>店铺ID：</label>
			<div class="controls">
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家ID：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>商品：</label>
			<div class="controls">
                <form:input path="goodsId" class="input-xlarge " onclick="selectOptions()" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场价/销售价：</label>
			<div class="controls">
				<form:input path="ruleDesc" htmlEscape="false" maxlength="64" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批发定价(元)：</label>
			<div class="controls">
				<form:input path="wholesalePrice" htmlEscape="false" class="input-xlarge " />&nbsp;&nbsp;&nbsp;
                <span id="wholesalePriceCheck" class="msg"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">vip定价(元)：</label>
			<div class="controls">
				<form:input path="vipPrice" htmlEscape="false" class="input-xlarge "/>&nbsp;&nbsp;&nbsp;
                <span id="vipPriceCheck" class="msg"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>零售定价(元)：</label>
			<div class="controls">
				<form:input path="marketPrice" htmlEscape="false" class="input-xlarge " />&nbsp;&nbsp;&nbsp;
                <span id="marketPriceCheck" class="msg"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">虚拟价格(元)：</label>
			<div class="controls">
				<form:input path="virtualPrice" htmlEscape="false" class="input-xlarge number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计量单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="xiaodian:retail:goodsPriceRule:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>