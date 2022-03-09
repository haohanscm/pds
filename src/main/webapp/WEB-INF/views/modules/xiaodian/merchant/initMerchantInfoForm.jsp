<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>聚合平台商家店铺初始化</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

		$(document).ready(function() {
		    resetTip();
		    // 图片预览
            photoPreview('shopLogo');
            photoPreview('shopPhotos_index');

            // 信息 提交
            $("#btnSubmit").click(function () {
                // 不允许连续点击
                $("#btnSubmit").attr("disabled","disabled");
                // 必需参数
                var aggregationType = $("#aggregationType").val();
                var shopName = $("#shopName").val();
                var telephone = $("#telephone").val();
                var shopAddress = $("#shopAddress").val();
                var shopService = $("#shopService").val();
                var shopLogo = $("#shopLogo").val();
                // 可选参数
                var shopCategoryId = $("#shopCategoryId").val();
                var goodsCategoryId = $("#goodsCategoryId").val();
                var contact = $("#contact").val();
                var onlineTime = $("#onlineTime").val();
                var shopDesc = $("#shopDesc").val();
                var mapLongitude = $("#mapLongitude").val();
                var mapLatitude = $("#mapLatitude").val();
                var merchantName = $("#merchantName").val();
                var goodsId = $("#goodsId").val();
                var shopPhotos = $("[name='shopPhotos']");
                var tradeType = $("[name='tradeType']:checked");

                if(!(aggregationType && shopName && telephone && shopAddress && shopService && shopLogo)){
                    alertx("必填项未填写!");
                    $("#btnSubmit").removeAttr("disabled");
                    return ;
                }
                // 多选项处理
                if(goodsId){
                    goodsId = goodsId.join(',');
                }
                if(shopPhotos){
                    var shopPhotosArray = new Array();
                    $.each(shopPhotos, function (n, entity) {
                        shopPhotosArray.push(entity.value);
                    });
                    shopPhotos = shopPhotosArray.join(',');
                }else{
                    shopPhotos = '';
                }
                if(tradeType){
                    var tradeTypeArray = new Array();
                    $.each(tradeType, function (n, entity) {
                        tradeTypeArray.push(entity.value);
                    });
                    tradeType = tradeTypeArray.join(',');
                }else{
                    tradeType = '';
                }
                console.log("tradeType", tradeType);
                loading("数据保存中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/xiaodian/merchant/shopCategory/initMerchantInfoSave',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        aggregationType : aggregationType,
                        shopName : shopName,
                        telephone : telephone,
                        shopAddress : shopAddress,
                        shopService : shopService,
                        shopLogo : shopLogo,
                        tradeType : tradeType,
                        shopCategoryId : shopCategoryId,
                        goodsCategoryId : goodsCategoryId,
                        goodsId : goodsId,
                        contact : contact,
                        onlineTime : onlineTime,
                        shopDesc : shopDesc,
                        mapLongitude : mapLongitude,
                        mapLatitude : mapLatitude,
                        merchantName : merchantName,
                        shopPhotos : shopPhotos
                    },
                    success: function (result) {
                        $("#btnSubmit").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                        if(result.code == 200){
                            // 清除门店照片
                            photosReset();
                            // 商品及分类清除
                            $("#goodsId").select2("val","");
                            $("#goodsCategoryId").val("");
                            $("#goodsCategoryName").val("");
                            $("#shopCategoryId").val("");
                            $("#shopCategoryName").val("");
                        }
                    }
                });
            });

            // 平台类型 变化影响商品分类选择
            $("#aggregationType").change(function () {
                var oldContent = $("#choiceCategory").children("script").html();
                // 空格为替换的结束标识
                var aggregationType = $("#aggregationType").val() || "failed";
                var newContent = oldContent.replace(/limitType=\S*/, "limitType=" + aggregationType);
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#choiceCategory").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#goodsCategoryButton, #goodsCategoryName").unbind();
                document.getElementById("choiceCategory").appendChild(script);
                // 清除 分类
                $("#goodsCategoryId").val("");
                $("#goodsCategoryName").val("");
            });

            // 平台类型 变化影响店铺分类选择
            $("#aggregationType").change(function () {
                var oldContent = $("#choiceShopCategory").children("script").html();
                // 空格为替换的结束标识
                var aggregationType = $("#aggregationType").val() || "failed";
                var newContent = oldContent.replace(/limitType=\S*/, "limitType=" + aggregationType);
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#choiceShopCategory").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#shopCategoryButton, #shopCategoryName").unbind();
                document.getElementById("choiceShopCategory").appendChild(script);
                // 清除 分类
                $("#shopCategoryId").val("");
                $("#shopCategoryName").val("");
            });

            // 平台类型 变化影响商品选择
            $("#aggregationType").change(function () {
                // 商品加载
                goodsSelectLoad('goodsId', $("#aggregationType").val(), '');
            });

            // 门店照片设置
            var index = 0;
            $("#addPhoto").click(function () {
                addPhoto(index, replaceIndex(index));
                index = index + 1;
            });

            function addPhoto(index, html) {
                $("#addDiv").append(html);
                photoPreview('shopPhotos_'+index);
                // 清除背景
                $("#addDiv").children("div").eq(index).css("background-image","");
            }
            // 门店照片项重置
            function photosReset(){
                index = 0;
                $("#addDiv").empty();
                $("#shopPhotos_index").val("");
                $("#shopPhotos_index-file").val("");
                $("#shopPhotos_index-file-info").text("没有选择文件");
                $("#shopPhotos_index-image-preview").css("background-image","");
            }

            // 初始化
            $("#aggregationType").select2('val','1').change();

		});

        // 图片上传
        function fileUpload(select){
            var file = document.getElementById(select+"-file").files[0];
            if(!file){
                alertx("未选择图片");
                return;
            }
            var selectValue = $("#"+select).val();
            if(selectValue){
                alertx("图片已上传");
                return;
            }
            // 不允许连续点击
            $("#"+select+"-file-upload").attr("disabled","disabled");
            var formData = new FormData();
            formData.append("file", file);
            jQuery.ajax({
                url: '${ctx}/xiaodian/merchant/shopCategory/photoUpload',
                type: 'POST',
                dataType: "json",
                contentType: false,
                processData: false,
                data: formData,
                success: function (result) {
                    if(result.code == 200){
                        var photoGalleryId = result.ext.photoGalleryId;
                        console.log("photoGalleryId", photoGalleryId);
                        $("#"+select).val(photoGalleryId);
                        btnMsg(select);
                    }else{
                        console.log(result.msg);
                        alertx(result.msg);
                    }
                    $("#"+select+"-file-upload").removeAttr("disabled");
                }
            });
        }
        // 图片上传按钮 显示内容
        function btnMsg(select) {
            var photoId = $("#"+select).val();
            if(photoId){
                $("#"+select+"-file-upload").text("图片已上传");
            }else{
                $("#"+select+"-file-upload").text("上传图片");
            }
        }

        // 替换门店照片div的index
        function replaceIndex(index) {
            var oldContent = $("#photosDiv").children("div:first").html();
            // console.log("replace", oldContent);
            // 空格为替换的结束标识
            var newContent = oldContent.replace(new RegExp('index', 'g'), index);
            // console.log("replace - new ", newContent);
            return newContent;
        }

        // 图片预览
        function photoPreview(select) {
            var
                fileInput = document.getElementById(select + '-file'),
                info = document.getElementById(select + '-file-info'),
                preview = document.getElementById(select + '-image-preview'),
                fileValue = document.getElementById(select);
            // 监听change事件:
            fileInput.addEventListener('change', function () {
                // 清除背景图片:
                preview.style.backgroundImage = '';
                // 清除图片资源库id
                fileValue.value = '';
                btnMsg(select);
                // 检查文件是否选择:
                if (!fileInput.value) {
                    info.innerHTML = '没有选择文件';
                    return;
                }
                // 获取File引用:
                var file = fileInput.files[0];
                if (file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif'
                    && file.type !== 'image/jpg' && file.type !== 'image/bmp') {
                    alert('不是有效的图片文件!');
                    fileInput.value = '';
                    return;
                }
                // 获取File信息:
                info.innerHTML = '文件: ' + file.name + ' ' +
                    '大小: ' + Math.round(file.size/1000) + ' ' + ' KB'
                // 读取文件:
                var reader = new FileReader();
                reader.onload = function(e) {
                    var data = e.target.result;
                    preview.style.backgroundImage = 'url(' + data + ')';
                };
                // 以DataURL的形式读取文件:
                reader.readAsDataURL(file);
            });
        }

        // 商品加载
        function goodsSelectLoad(selectId, type, goodsId){
            console.log("商品加载");
            jQuery.ajax({
                url: '${ctx}/xiaodian/database/standardProductUnit/goodsList',
                type: 'POST',
                dataType: "json",
                data: {
                    aggregationType: type,
                },
                success: function (result) {
                    var goods = $("#"+selectId);
                    if (result.code == 200) {
                        var _data = result.ext;
                        goods.empty();
                        $.each(_data, function (n, entity) {
                            goods.append($("<option>").attr({"value": entity.id}).text(entity.goodsName));
                        });
                    }else{
                        goods.empty();
                        goods.change();
                        alertx(result.msg);
                    }
                    goods.val(null).trigger('change');
                }
            });
        }

	</script>
    <style>
        .photo-style{
            border: 1px solid rgb(204, 204, 204);
            width: 150px;
            height: 150px;
            background: center/contain no-repeat;
        }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/merchant/shopCategory/">店铺分类列表</a></li>
		<li class="active">聚合平台商家店铺初始化</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantInfo" action="${ctx}/xiaodian/merchant/shopCategory/initMerchantInfoForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">以下信息必填：</label>
        </div>
		<div class="control-group">
			<label class="control-label">聚合平台类型：</label>
			<div class="controls">
				<form:select path="aggregationType" class="input-xlarge ">
					<form:options items="${fns:getDictList('aggregation_shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺名称：</label>
			<div class="controls">
				<form:input path="shopName" htmlEscape="false" maxlength="20" class="input-xlarge " placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="20"  class="input-xlarge " placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺地址：</label>
			<div class="controls">
				<form:textarea path="shopAddress" htmlEscape="false" maxlength="64" rows="2" class="input-xlarge " placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺经营范围：</label>
			<div class="controls">
				<form:textarea path="shopService" htmlEscape="false" maxlength="200" rows="4" class="input-xlarge " placeholder="必填项"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺logo/门头照：</label>
			<div class="controls">
                <form:input path="shopLogo"  htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true" />
				<p><input type="file" id="shopLogo-file" name="shopLogo-file" class="input-xlarge " />
                    <button type="button" id="shopLogo-file-upload" onclick="fileUpload('shopLogo')">上传图片</button>
                    <span id="shop-file-status"></span>
                </p>
                <div id="shopLogo-image-preview" class="photo-style"></div>
                <p id="shopLogo-file-info">没有选择文件</p>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">以下信息可选填：</label>
        </div>
        <div class="control-group">
            <label class="control-label">店铺交易类型：</label>
            <div class="controls">
                <form:checkboxes path="tradeType" items="${fns:getDictList('shop_trade_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">联系人：</label>
            <div class="controls">
                <form:input path="contact" htmlEscape="false" maxlength="10" class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商家名称：</label>
            <div class="controls">
                <form:input path="merchantName" htmlEscape="false" maxlength="20" class="input-xlarge " placeholder="未填时同店铺名称"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">营业时间：</label>
            <div class="controls">
                <form:textarea path="onlineTime" htmlEscape="false" maxlength="64" rows="3" class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">店铺简介：</label>
            <div class="controls">
                <form:textarea path="shopDesc" htmlEscape="false" maxlength="500" rows="3" class="input-xlarge " placeholder="未填时同店铺经营范围"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">经度：</label>
            <div class="controls">
                <form:input path="mapLongitude" htmlEscape="false" maxlength="32" class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">纬度：</label>
            <div class="controls">
                <form:input path="mapLatitude" htmlEscape="false" maxlength="32" class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">店铺分类：</label>
            <div class="controls"  id="choiceShopCategory">
                <sys:treeselect id="shopCategory" name="shopCategoryId"
                                value="" labelName=""
                                labelValue=""
                                title="父级编号" url="/xiaodian/merchant/shopCategory/treeData?limitType= " cssClass=""
                                allowClear="true" notAllowSelectParent="true" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品分类：</label>
            <div class="controls"  id="choiceCategory">
                <sys:treeselect id="goodsCategory" name="goodsCategoryId"
                                value="" labelName=""
                                labelValue=""
                                title="父级编号" url="/xiaodian/database/productCategory/treeData?limitType= " cssClass=""
                                allowClear="true" notAllowSelectParent="true" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品：</label>
            <div class="controls">
                <form:select path="goodsId" class="input-xxlarge" multiple="true" placeholder="可多选">
                    <form:option value="" label=""/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">门店照片：</label>
            <div class="controls" id="photosDiv">
                <div id="shopPhotos">
                    <input id="shopPhotos_index" name="shopPhotos" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="readonly" />
                    <p><input type="file" id="shopPhotos_index-file" name="shopPhotos_index-file" class="input-medium " />
                        <button type="button" id="shopPhotos_index-file-upload" onclick="fileUpload('shopPhotos_index')" >上传图片</button>
                    </p>
                    <div id="shopPhotos_index-image-preview" class="photo-style" ></div>
                    <p id="shopPhotos_index-file-info">没有选择文件</p>
                </div>
                <div id="addDiv"></div>
                <button id="addPhoto" type="button" class="btn btn-primary submit" >添加图片</button>
            </div>
        </div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary submit" type="button" value="初始化当前信息"/>
		</div>
	</form:form>
</body>
</html>