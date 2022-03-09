<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺服务小区管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
                rules: {
                    merchantId: "required",
                    shopId: "required",
                    districtAreaId: "required",
                    communityId: "required"
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
                            shop.change();
                        }
                    }
                });
                // 获取对应片区
                fetchDistrict(merchantId);
            });

            $("#shopId").change(function () {
                var shopName = this.options[this.options.selectedIndex].text;
                $("#shopName").val(shopName);
            });

            $("#merchantId").change(function () {
                var merchantName = this.options[this.options.selectedIndex].text;
                $("#merchantName").val(merchantName);
            });

            // 片区变化 加载 对应 小区
            $("#districtAreaId").change(function () {
                var districtId = $("#districtAreaId").val();
                fetchCommunity(districtId);
            });
            // 初始化  有值时 商家店铺 不可改
            var merchantId = $("#merchantId").val();
            if(merchantId){
                $("#merchantId").attr("readonly","readonly");
                $("#shopId").attr("readonly","readonly");
                $(".showMsg").show();
                fetchDistrict(merchantId);
            }

		});

        // 获取片区 并加载
        function fetchDistrict(merchantId) {
            jQuery.ajax({
                url: '${ctx}/xiaodian/delivery/districtManage/query',
                type: 'POST',
                dataType: "json",
                data: {
                    merchantId: merchantId,
                },
                success: function (result) {
                    // console.log("district", result);
                    if (result.length > 0) {
                        var district = $("#districtAreaId");
                        district.empty();
                        $.each(result, function (n, entity) {
                            district.append($("<option>").attr({"value": entity.id}).text(entity.name));
                        });
                        district.change();
                    }else {
                        alertx("商家还没有创建服务片区");
                        return;
                    }
                }
            });
        }

        // 获取片区下的小区 并加载
        function fetchCommunity(districtId) {
            jQuery.ajax({
                url: '${ctx}/xiaodian/delivery/communityManage/query',
                type: 'POST',
                dataType: "json",
                data: {
                    districtId: districtId,
                },
                success: function (result) {
                    // console.log("district", result);
                    if (result.length > 0) {
                        var community = $("#communityId");
                        community.empty();
                        $.each(result, function (n, entity) {
                            community.append($("<option>").attr({"value": entity.id}).text(entity.name));
                        });
                        community.change();
                    }else {
                        alertx("商家还没有选择服务片区的小区");
                        return;
                    }
                }
            });

        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/delivery/shopServiceDistrict/">店铺服务小区列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/shopServiceDistrict/form?id=${shopServiceDistrict.id}">店铺服务小区<shiro:hasPermission name="xiaodian:delivery:shopServiceDistrict:edit">${not empty shopServiceDistrict.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:shopServiceDistrict:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopServiceDistrict" action="${ctx}/xiaodian/delivery/shopServiceDistrict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group" style="display: none">
			<label class="control-label">商家名称：</label>
			<div class="controls">
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select id="merchantId" path="merchantId" class="input-xlarge">
					<form:option value="" label=""/>
					<c:forEach items="${merchantList}" var="merchant">
						<form:option  value="${merchant.id}" label="${merchant.merchantName}" merchantId="${merchant.id}"/>
					</c:forEach>
				</form:select>
                <span class="showMsg" style="display: none">不可更改</span>
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
					<form:option value="${shopServiceDistrict.shopId}" label="${shopServiceDistrict.shopName}"/>
				</form:select>
                <span class="showMsg" style="display: none">不可更改</span>
			</div>
		</div>
		<%-- 后台通过片区获取省市区 --%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">省份：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select id="province" path="province" class="input-xlarge">--%>
					<%--<form:option value="${shopServiceDistrict.province}" label=""/>--%>
				<%--</form:select>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">城市：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select id="city" path="city" class="input-xlarge">--%>
					<%--<form:option value="${shopServiceDistrict.city}" label=""/>--%>
				<%--</form:select>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">区县：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select id="region" path="region" class="input-xlarge">--%>
					<%--<form:option value="${shopServiceDistrict.region}" label=""/>--%>
				<%--</form:select>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">服务片区：</label>
			<div class="controls">
				<%--<form:input path="districtAreaId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
                <form:select path="districtAreaId" class="input-xlarge">
                    <form:option value="${shopServiceDistrict.districtAreaId}" label="${shopServiceDistrict.districtAreaName}"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务小区：</label>
			<div class="controls">
                <form:select path="communityId" class="input-xlarge">
                    <form:option value="${shopServiceDistrict.communityId}" label="${shopServiceDistrict.communityName}"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务类型：</label>
			<div class="controls">
				<form:select path="serviceType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('xd_merchant_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
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
			<shiro:hasPermission name="xiaodian:delivery:shopServiceDistrict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>