<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品库分类商品导入至零售商品</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
                rules: {
                    categoryName: "required"
                },
                messages:{
                    categoryName: "请选择分类"
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/database/productCategory/">商品库分类列表</a></li>
		<li class="active"><a href="#">分类商品导入至零售商品</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsCategory" action="${ctx}/xiaodian/database/productCategory/transToRetailGoods" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">说明：</label>
            <div class="controls">
                选中的分类下的所有商品(spu/sku); 新增到零售商品库中
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">公共库商品分类：</label>
			<div class="controls">
				<sys:treeselect id="category" name="id"
								value="" labelName="categoryName"
								labelValue=""
								title="父级编号" url="/xiaodian/database/productCategory/treeData" cssClass=""
								allowClear="true" notAllowSelectParent="true" checked="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select path="merchantId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${merchantList}" itemValue="id" itemLabel="merchantName" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺：</label>
			<div class="controls">
				<form:select path="shopId" class="input-xlarge required">
					<form:option value="" label=""/>
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label"><span class="msg">* </span>零售商品分类：</label>--%>
			<%--<div class="controls" id="choiceCategory">--%>
				<%--<sys:treeselect id="retailCategory" name="retailCategoryId" value="" labelName="categoryName"--%>
								<%--labelValue=""--%>
								<%--title="数据分类" url="/xiaodian/retail/goodsCategory/treeData " cssClass="" allowClear="true"--%>
								<%--notAllowSelectParent="true" />--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:database:productCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="开始转换"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>