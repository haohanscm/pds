<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                rules: {
                    realName: "required",
                    mobile: "required",
					status: "required",
                    merchantId: "required",
                    shopId: "required"
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

            $("#merchantId").change(function () {
                var merchantId = $("#merchantId").find("option:selected").attr("merchantId");
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
                                alert("商家没有店铺");
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


            $("#shopId").change(function () {
                var shopName = this.options[this.options.selectedIndex].text;
                $("#shopName").val(shopName);
            });
            // 初始化
            var merchantId = $("#merchantId").val();
            if(merchantId){
                $("#merchantId").change();
            }
        });
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/delivery/deliverManManage/">配送员列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/deliverManManage/form?id=${deliverManManage.id}">配送员<shiro:hasPermission name="xiaodian:delivery:deliverManManage:edit">${not empty deliverManManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:deliverManManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deliverManManage" action="${ctx}/xiaodian/delivery/deliverManManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="realName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">昵称：</label>
			<div class="controls">
				<form:input path="nickName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头像：</label>
			<div class="controls">
				<form:hidden id="avatar" path="avatar" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="avatar" type="images" uploadPath="/deliveryMan/avatar" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">照片：</label>
			<div class="controls">
				<form:hidden id="photo" path="photo" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="photo" type="images" uploadPath="/deliveryMan/photos" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select id="merchantId" path="merchantId" class="input-xlarge required">
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
				<form:select id="shopId" path="shopId" class="input-xlarge required">
					<form:option value="${deliverManManage.shopId}" label="${deliverManManage.shopName}"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">星级：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总配送次数：</label>
			<div class="controls">
				<form:input path="totalDeliveryTimes" htmlEscape="false" maxlength="8" class="input-xlarge  digits"/>
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
			<label class="control-label">加入日期：</label>
			<div class="controls">
				<input name="joinDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${deliverManManage.joinDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送员通行证id：</label>
			<div class="controls">
				<form:input path="uid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:delivery:deliverManManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>