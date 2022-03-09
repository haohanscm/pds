<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台商家和供应商商品关联管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
            // 平台商家 联动 商品 / 供应商
            $("#pmId").change(function () {
                // console.log("pmId change");
                var pmId = $("#pmId").val();
                var goodsId = $("#goodsId").val();
                // 加载商品
                goodsSelectLoad("goodsId", pmId, goodsId);
                // 供应商
                var supplierId = $("#supplierId").val();
                jQuery.ajax({
                    url: '${ctx}/pds/business/supplierGoods/supplierList',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                    },
                    success: function (result) {
                        var supplier = $("#supplierId");
                        if (result.code == 200) {
                            var _data = result.ext;
                            supplier.empty();
                            supplier.append($("<option>").attr({"value": ""}).text(""));
                            $.each(_data, function (n, entity) {
                                supplier.append($("<option>").attr({"value": entity.id}).text(entity.supplierName).attr({"data-merchant": entity.merchantId}).attr({"data-merchant-name": entity.merchantName}));
                            });
                            if(supplierId){
                                supplier.select2("val", supplierId);
                                supplier.change();
                            }
                        }else{
                            supplier.empty();
                            supplier.append($("<option>").attr({"value": ""}).text(""));
                            supplier.change();
                            alertx(result.msg);
                        }
                    }
                });
            });
            // 平台商品 联动 商品规格
            $("#goodsId").change(function () {
                // console.log("goodsId change");
                var goodsId = $("#goodsId").val();
                var goodsModelId = $("#goodsModelId").val();
                goodsModelSelectLoad("goodsModelId", goodsId, goodsModelId)
            });
            // 商品规格信息展示
            $("#goodsModelId").change(function () {
                var goodsModel = $("#goodsModelId option:checked");
                $("#goodsModelUnit").text(goodsModel.data("unit"));
                $("#goodsModelPrice").text(goodsModel.data("price"));
                $("#goodsModelUrl").attr('src', goodsModel.data("url"));
            })

            // 供应商 关联merchantId/merchantName
            $("#supplierId").change(function () {
                // 清除 供应商商品/规格
                $("#supplierGoodsId").select2("val", "");
                $("#supplierModelId").select2("val", "");
                var merchant = $("#supplierId option:checked");
                $("#supplierMerchantId").val(merchant.data("merchant")).change();
                $("#supplierMerchantName").val(merchant.data("merchant-name"));
            })

            var existSupplierModelId = "";
            // 供应商商家 联动 商品
            $("#supplierMerchantId").change(function () {
                // console.log("supplierMerchantId change");
                var supplierMerchantId = $("#supplierMerchantId").val();
                var supplierGoodsId = $("#supplierGoodsId").val();

                // 已有关联信息查询
                var pmId = $("#pmId").val();
                var supplierId = $("#supplierId").val();
                var goodsId = $("#goodsId").val();
                var goodsModelId = $("#goodsModelId").val();
                if(!(pmId && supplierId && goodsId && goodsModelId)){
                    $("#relationInfo").hide();
                    // 加载供应商商品
                    goodsSelectLoad("supplierGoodsId", supplierMerchantId, supplierGoodsId);
                }else{
                    $("#relationInfo").show();
                    // 已有关联信息查询
                    jQuery.ajax({
                        url: '${ctx}/pds/business/supplierGoods/relationQuery',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            pmId :pmId,
                            supplierId :supplierId,
                            goodsId :goodsId,
                            goodsModelId :goodsModelId
                        },
                        success: function (result) {
                            var relationStatus =  $("#relationStatus");
                            $("#relationGoodsName").text($("#goodsId option:checked").text());
                            if (result.code == 200) {
                                var _data = result.ext;
                                relationStatus.text("已");
                                $("#relationGoodsName2").text(_data.supplierGoodsName);
                                $("#relationGoodsName3").text(_data.supplierModelName);
                                supplierGoodsId = _data.supplierGoodsId;
                                existSupplierModelId = _data.supplierModelId;
                                // console.log("supplierModelId", $("#supplierModelId").val());
                                // console.log("existSupplierModelId", existSupplierModelId);
                            }else{
                                relationStatus.text("不");
                            }
                            // 加载供应商商品
                            goodsSelectLoad("supplierGoodsId", supplierMerchantId, supplierGoodsId);
                        }
                    });
                }
            });

            // 供应商商品 联动 商品规格
            $("#supplierGoodsId").change(function () {
                var goodsId = $("#supplierGoodsId").val();
                var goodsModelId = $("#supplierModelId").val() || existSupplierModelId;
                goodsModelSelectLoad("supplierModelId", goodsId, goodsModelId)
            });

            // 供应商 商品规格信息展示
            $("#supplierModelId").change(function () {
                var goodsModel = $("#supplierModelId option:checked");
                $("#supplierModelUnit").text(goodsModel.data("unit"));
                $("#supplierModelPrice").text(goodsModel.data("price"));
                $("#supplierModelUrl").attr('src', goodsModel.data("url"));
            })

            // 关联商品 提交
            $("#btnSubmit").click(function () {
                // 不允许连续点击
                $("#btnSubmit").attr("disabled","disabled");
                var pmId = $("#pmId").val();
                var supplierId = $("#supplierId").val();
                var supplierMerchantId = $("#supplierMerchantId").val();
                var goodsId = $("#goodsId").val();
                var goodsModelId = $("#goodsModelId").val();
                var supplierModelId = $("#supplierModelId").val();
                var status = $("#status").val();
                if(!(pmId && supplierId && supplierMerchantId && goodsId && goodsModelId && supplierModelId && status)){
                    alertx("信息不完整");
                    $("#btnSubmit").removeAttr("disabled");
                    return ;
                }
                loading("数据保存中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/pds/business/supplierGoods/relationSave',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId :pmId,
                        supplierId :supplierId,
                        supplierMerchantId :supplierMerchantId,
                        goodsId :goodsId,
                        goodsModelId :goodsModelId,
                        supplierModelId :supplierModelId,
                        status :status
                    },
                    success: function (result) {
                        $("#btnSubmit").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                    }
                });
            });
            // 初始化
            var pmId = $("#pmId").val();
            if(pmId){
                // console.log("init");
                $("#pmId").change();
            }

		});

		// 商品加载
        function goodsSelectLoad(selectId, merchantId, goodsId){
            jQuery.ajax({
                url: '${ctx}/pds/business/supplierGoods/goodsList',
                type: 'POST',
                dataType: "json",
                data: {
                    pmId: merchantId,
                },
                success: function (result) {
                    var goods = $("#"+selectId);
                    if (result.code == 200) {
                        var _data = result.ext;
                        goods.empty();
                        goods.append($("<option>").attr({"value": ""}).text(""));
                        $.each(_data, function (n, entity) {
                            goods.append($("<option>").attr({"value": entity.id}).text(entity.goodsName));
                        });
                        if(goodsId){
                            goods.select2("val", goodsId);
                            goods.change();
                        }
                    }else{
                        goods.empty();
                        goods.append($("<option>").attr({"value": ""}).text(""));
                        goods.change();
                        alertx(result.msg);
                    }
                }
            });
        }
        // 商品规格加载
        function goodsModelSelectLoad(selectId, goodsId, goodsModelId) {
            jQuery.ajax({
                url: '${ctx}/pds/business/supplierGoods/goodsModelList',
                type: 'POST',
                dataType: "json",
                data: {
                    goodsId: goodsId,
                },
                success: function (result) {
                    var goodsModel = $("#"+selectId);
                    if (result.code == 200) {
                        var _data = result.ext;
                        goodsModel.empty();
                        goodsModel.append($("<option>").attr({"value": ""}).text(""));
                        $.each(_data, function (n, entity) {
                            goodsModel.append($("<option>")
                                .text(entity.modelName)
                                .attr({"value": entity.id})
                                .attr({"data-unit": entity.modelUnit})
                                .attr({"data-url": entity.modelUrl})
                                .attr({"data-price": entity.modelPrice}));
                        });
                        goodsModel.select2("val", goodsModelId);
                        goodsModel.change();
                    }else{
                        goodsModel.empty();
                        goodsModel.append($("<option>").attr({"value": ""}).text(""));
                        goodsModel.change();
                        alertx(result.msg);
                    }
                }
            });
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pds/business/supplierGoods/">供应商货物列表</a></li>
		<li class="active"><a href="${ctx}/pds/business/supplierGoods/relation">平台商家和供应商商品关联管理</a></li>
	</ul><br/>
    <form:form id="inputForm" modelAttribute="supplierGoods" action="${ctx}/pds/business/supplierGoods/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">平台商家：</label>
			<div class="controls">
                <form:select path="pmId" class="input-xlarge" >
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
                </form:select>
            </div>
		</div>
        <div class="control-group">
            <label class="control-label">商家商品(spu)：</label>
            <div class="controls">
                <form:select path="goodsId" class="input-xlarge" >
                    <form:option value="" label=""/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商家商品规格(sku)：</label>
            <div class="controls">
                <form:select path="goodsModelId" class="input-xlarge" >
                    <form:option value="" label=""/>
                </form:select><br/>
                单位：<span id="goodsModelUnit"></span><br/>
                价格：<span id="goodsModelPrice"></span> 元<br/>
                <img src="" alt="" id="goodsModelUrl" style="height: 100px;"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">供应商：</label>
			<div class="controls">
                <form:select path="supplierId" class="input-xlarge" >
                    <form:option value="" label=""/>
                </form:select>
			</div>
		</div>
        <div class="control-group" style="display: none;">
            <label class="control-label">供应商商家id：</label>
            <div class="controls">
                <form:input path="supplierMerchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group" >
            <label class="control-label">供应商商家：</label>
            <div class="controls">
                <form:input path="supplierMerchantName" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
                <br/>
                <div id="relationInfo" style="display: none">
                    供应商<span id="relationStatus" style="color: #FF0000"></span>存在商品(<span id="relationGoodsName"></span>)的关联商品(<span id="relationGoodsName2"></span>:<span id="relationGoodsName3"></span>)
                </div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">供应商商品(spu)：</label>
            <div class="controls">
                <form:select path="supplierGoodsId" class="input-xlarge" >
                    <form:option value="" label=""/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">供应商商品规格(sku)：</label>
            <div class="controls">
                <form:select path="supplierModelId" class="input-xlarge" >
                    <form:option value="" label=""/>
                </form:select><br/>
                单位：<span id="supplierModelUnit"></span><br/>
                价格：<span id="supplierModelPrice"></span> 元<br/>
                <img src="" alt="" id="supplierModelUrl" style="height: 100px;"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="pds:business:supplierGoods:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保存当前商品关联信息"/>&nbsp;</shiro:hasPermission>
			<%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
		</div>
    </form:form>
</body>
</html>