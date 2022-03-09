<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送服务区域管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
                rules: {
                    merchantId: "required",
                    shopId: "required",
                    deliverManName: "required",
                    communityName: "required"
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

            });

            // shopId 变化影响 选择
            $("#shopId").change(function () {
                // 配送员
                var oldContent = $("#deliverManQuery").children("script").html();
                // 空格为替换的结束标识
                var newContent = oldContent.replace(/merchantId=\S*/,"merchantId="+$("#merchantId").val()+"&shopId="+$("#shopId").val());
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#deliverManQuery").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#deliverManButton, #deliverManName").unbind();
                document.getElementById("deliverManQuery").appendChild(script);
                $("#deliverManId").val("");
                $("#deliverManName").val("");

                // 小区
                var oldContent = $("#communityQuery").children("script").html();
                // 空格为替换的结束标识
                var newContent = oldContent.replace(/merchantId=\S*/,"merchantId="+$("#merchantId").val()+"&shopId="+$("#shopId").val());
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#communityQuery").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#communityButton, #communityName").unbind();
                document.getElementById("communityQuery").appendChild(script);
                $("#communityId").val("");
                $("#communityName").val("");
            });

            $("#shopId").change(function () {
                var shopName = this.options[this.options.selectedIndex].text;
                $("#shopName").val(shopName);
            });

            $("#merchantId").change(function () {
                var merchantName = this.options[this.options.selectedIndex].text;
                $("#merchantName").val(merchantName);
            });
            // 初始化  有值时 商家店铺 不可改
            var merchantId = $("#merchantId").val();
            if(merchantId){
                $("#merchantId").attr("readonly","readonly");
                $("#shopId").attr("readonly","readonly");
                $(".showMsg").show();
            }
        });

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/delivery/deliverServiceArea/">配送服务区域列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/deliverServiceArea/form?id=${deliverServiceArea.id}">配送服务区域<shiro:hasPermission name="xiaodian:delivery:deliverServiceArea:edit">${not empty deliverServiceArea.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:deliverServiceArea:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deliverServiceArea" action="${ctx}/xiaodian/delivery/deliverServiceArea/save" method="post" class="form-horizontal">
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
			<label class="control-label">店铺：</label>
			<div class="controls">
				<form:select id="shopId" path="shopId" class="input-xlarge">
					<form:option value="${deliverServiceArea.shopId}" label="${deliverServiceArea.shopName}"/>
				</form:select>
                <span class="showMsg" style="display: none">不可更改</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送员：</label>
			<div class="controls" id="deliverManQuery">
                <sys:treeselect id="deliverMan" name="deliverManId" value="${deliverServiceArea.deliverManId}" labelName="deliverManName"
                                labelValue="${deliverServiceArea.deliverManName}"
                                title="配送员" url="/xiaodian/delivery/deliverManManage/query?merchantId=${deliverServiceArea.merchantId}&shopId=${deliverServiceArea.shopId} " cssClass="" allowClear="true"
                                notAllowSelectParent="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务小区：</label>
			<div class="controls" id="communityQuery">
				<%-- 新增时 可多选 --%>
                <c:if test="${empty deliverServiceArea.communityId}" >
                    <sys:treeselect id="community" name="communityId" value="${deliverServiceArea.communityId}" labelName="communityName"
                                    labelValue="${deliverServiceArea.communityName}"
                                    title="服务小区" url="/xiaodian/delivery/shopServiceDistrict/query?merchantId=${deliverServiceArea.merchantId}&shopId=${deliverServiceArea.shopId} " cssClass="" allowClear="true"
                                    notAllowSelectParent="true" checked="true" />
                    <span >服务小区可多选</span>
                </c:if>
                <c:if test="${not empty deliverServiceArea.communityId}" >
                    <sys:treeselect id="community" name="communityId" value="${deliverServiceArea.communityId}" labelName="communityName"
                                    labelValue="${deliverServiceArea.communityName}"
                                    title="服务小区" url="/xiaodian/delivery/shopServiceDistrict/query?merchantId=${deliverServiceArea.merchantId}&shopId=${deliverServiceArea.shopId} " cssClass="" allowClear="true"
                                    notAllowSelectParent="true" />
                </c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:delivery:deliverServiceArea:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>