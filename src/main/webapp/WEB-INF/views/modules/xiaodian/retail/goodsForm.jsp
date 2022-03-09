<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        var num = 0; // 规格类型行数

        var goodsModelList = ${fns:toJson(goodsInfoExt.goodsModelList)};
        var infoList = ${fns:toJson(goodsInfoExt.infoList)};
        var serviceSelectionList = ${fns:toJson(goodsInfoExt.serviceSelectionList)};
        var initShopId = "${goodsInfoExt.shopId}";
        var initCategoryId = "${goodsInfoExt.goodsCategoryId}";
        var initCategoryName = "${goodsInfoExt.categoryName}";

        if (!Array.isArray(goodsModelList)) {
            goodsModelList = []
        }
        if (!Array.isArray(serviceSelectionList)) {
            serviceSelectionList = []
        }

        var modelHtml = "<div id=\"model-type-body\" class=\"modal-body\" >\n" +
            "        <div class=\"center\">\n" +
            "                <b>规格类型&nbsp;&nbsp;&nbsp;</b>\n" +
            "                <select id=\"modelType-select\" name=\"modelType\" style=\"width: 180px;\">\n" +
            "                    <option></option>\n" +
            <c:forEach items="${fns:getDictList('goods_model_type')}" var="type">
            "<option value=\"${type.value}\">${type.label}</option>" +
            </c:forEach>
            "                </select>\n" +
            "        </div>\n" +
            "    </div>";

		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
            // 公共函数
            // 渲染字符串模版, 挂载到String函数原型链上
            String.prototype.render = function (context) {
                return this.replace(/\{\{(.*?)\}\}/g, function(match, key) {
                    return context[key.trim()];
                })
            };

			$("#inputForm").validate({
                rules: {
                    shopId: "required",
                    categoryName: "required",
                    goodsName: "required",
                    storage: "digits"
                },
                messages:{
                    categoryName: "请选择分类"
                },
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				// errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					// $("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				},
			});

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

            // shopId 变化影响商品分类选择
            $("#shopId").change(function () {
                var oldContent = $("#choiceCategory").children("script").html();
                // 空格为替换的结束标识
                var shopId = $("#shopId").val() || "failed";
                var newContent = oldContent.replace(/limitShopId=\S*/, "limitShopId=" + shopId);
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#choiceCategory").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#goodsCategoryButton, #goodsCategoryName").unbind();
                document.getElementById("choiceCategory").appendChild(script);
                // 清除 分类
                var categoryId = "";
                var categoryName = "";
                if(shopId == initShopId){
                    categoryId = initCategoryId;
                    categoryName = initCategoryName;
                }
                $("#goodsCategoryId").val(categoryId);
                $("#goodsCategoryName").val(categoryName);
            });
            // 初始化
            $("#merchantId").change();
            if ($("#id").val() != "") {
                $(".idDiv").show();
            }
            if($("[name='isMarketable']:checked").val()!= "0"){
                $("[name='isMarketable'][value='1']").attr('checked','true');
            }
            // 附加选项初始化
            if($("[name='saleRule']:checked").val()!= "1"){
                $("[name='saleRule'][value='0']").attr('checked','true');
            }else{
                $(".saleRuleDiv").show();
            }
            if($("[name='serviceSelection']:checked").val()!= "1"){
                $("[name='serviceSelection'][value='0']").attr('checked','true');
            }else{
                $(".serviceSelectionDiv").show();
            }
            if($("[name='deliveryRule']:checked").val()!= "1"){
                $("[name='deliveryRule'][value='0']").attr('checked','true');
            }else{
                $(".deliveryRuleDiv").show();
            }
            if($("[name='goodsGift']:checked").val()!= "1"){
                $("[name='goodsGift'][value='0']").attr('checked','true');
            }else{
                $(".goodsGiftDiv").show();
            }
            if($("[name='goodsModel']:checked").val()!= "1"){
                $("[name='goodsModel'][value='0']").attr('checked','true');
            }else{
                $(".goodsModelDiv").show();
            }

            // 初始化处理选项
            initGoodsModel()
            initModelInfo(infoList)
            initGoodsService()
            initDeliveryRules()

            // 折叠变换
            $('#collapseLabel').on('click', function() {
                var status = $('#moreOptions').hasClass('in')
                if (status) {
                    $('#collapseLabel').html('高级选项 <i class="icon-angle-down">')
                } else {
                    $('#collapseLabel').html('收起 <i class="icon-angle-up"></i>')
                }
            })

            /**
             * 配送方式管理
             */
            // 送货上门类型
            $('#deliveryPlanType').on('change', function(e){ handleDeliveryRules(e.val)})

            // 图片初始化
            photoInit('thumbUrl', '${goodsInfoExt.thumbUrl}');
		});
        function initDeliveryRules() {
            var goodsInfoExt = ${fns:toJson(goodsInfoExt)};
            var deliveryType = goodsInfoExt.deliveryType
            if(deliveryType){
                deliveryType = deliveryType.split(',')
            }
            $('#deliveryType').select2('val', deliveryType)
            $('#deliveryPlanType').select2('val',  goodsInfoExt.deliveryPlanType)

            handleDeliveryRules($('#deliveryPlanType').select2('val'))

            $('#s2id_arriveType').select2('val', goodsInfoExt.arriveType)
            $('#s2id_deliverySchedule').select2('val', null)
        }
        function handleDeliveryRules(val) {
            if (val == '0') {
                $('#arriveTypeDiv').show()
                // 清空数据 并 隐藏
                $('#deliveryScheduleDiv').hide().find('input').each(function() {
                    $(this).val(' ')
                })
                $('#s2id_deliverySchedule').select2('val', null)
                $('#specificDateDiv').hide()
                $('#specificDate').val('')
            }
            if (val == '1') {
                $('#deliveryScheduleDiv').show()
                // 清空数据 并 隐藏
                $('#specificDateDiv').hide()
                $('#arriveTypeDiv').hide()
                $('#s2id_arriveType').select2('val', null)
                $('#specificDate').val('')

            }
            if (val == '2') {
                $('#specificDateDiv').show()

                // 清空数据 并 隐藏
                $('#deliveryScheduleDiv').hide().find('input').each(function() {
                    $(this).val('')
                })
                $('#arriveTypeDiv').hide()
                $('#s2id_arriveType').select2('val', null)
                $('#s2id_deliverySchedule').select2('val', null)
            }
            if (val == -1) {
                $('#deliveryScheduleDiv').find('input').each(function() {
                    $(this).val('')
                })
                $('#specificDate').val('')
                $('#s2id_arriveType').select2('val', null)
                $('#s2id_deliverySchedule').select2('val', null)
                $('#s2id_deliveryPlanType').select2('val', null)
            }
        }
        // 选项启用时变化
        function divChange(div){
            if($("[name='" + div + "']:checked").val()!= "1"){
                $("." + div + "Div").hide();
            }else{
                $("." + div + "Div").show();
            }
        }

        // 初始化处理服务选项
        function initGoodsService() {
            var oldName = ''
            if (!Array.isArray(serviceSelectionList)) {
                serviceSelectionList = []
            }
            serviceSelectionList = serviceSelectionList.map(function(item, index) {
                oldName = item.serviceName
                return {
                    index: index,
                    id: item.id,
                    serviceName: item.serviceName || '',
                    serviceDetail: item.serviceDetail || '',
                    servicePrice: item.servicePrice || '',
                    serviceNum: item.serviceNum || '',
                    serviceSchedule: item.serviceSchedule || '',
                }
            })
            $('#goodsServiceOptions').empty().append(genHtml('goodsServiceTmpl', serviceSelectionList))
            bindChangeInput('#goodsServiceOptions', serviceSelectionList)
            $('#goodsServiceName').val(oldName)
            renderCheckedOption('#goodsServiceOptions')
        }
        // 渲染选项值
        function renderCheckedOption(selecter) {
            $(selecter).find('select').each(function () {
                var value = $(this).attr("data-value")
                $(this).find('option').each(function() {
                    if ($(this).val() == value) {
                        $(this).attr("selected","selected");
                    }
                })
            })
        }
        // 更改服务选项名称
        function inputServiceName() {
            var name = $('#goodsServiceName').val() || ''
            if (Array.isArray(serviceSelectionList)) {
                serviceSelectionList.forEach(function(item, index) {
                    item.serviceName = name
                })
            }
            $('#goodsServiceOptions').empty().append(genHtml('goodsServiceTmpl', serviceSelectionList))
        }
        // 添加服务选项
        function addServiceOption(){
            if (!Array.isArray(serviceSelectionList)) {
                serviceSelectionList = []
            }
            var length = serviceSelectionList.length

            serviceSelectionList.push({
                index: length,
                id: '',
                serviceName: '',
                serviceDetail: '',
                servicePrice: '',
                serviceNum: '',
                serviceSchedule: ''
            })
            inputServiceName()
            renderCheckedOption('#goodsServiceOptions')
        }
        // 删除服务选项
        function delServiceOption(index) {
            serviceSelectionList.splice(index, 1)
            serviceSelectionList = serviceSelectionList.map(function(item, index) {
                return {
                    index: index,
                    id: item.id,
                    serviceName: item.serviceName || '',
                    serviceDetail: item.serviceDetail || '',
                    servicePrice: item.servicePrice || '',
                    serviceNum: item.serviceNum || '',
                    serviceSchedule: item.serviceSchedule || ''
                }
            })
            $('#goodsServiceOptions').empty().append(genHtml('goodsServiceTmpl', serviceSelectionList))
            renderCheckedOption('#goodsServiceOptions')
        }


        // 初始化处理规格选项
        function initGoodsModel() {
            if (!Array.isArray(goodsModelList)) {
                goodsModelList = []
            }
            goodsModelList = goodsModelList.map(function(item, index) {
                return {
                    index: index,
                    id: item.id,
                    goodsModelSn: item.goodsModelSn || '',
                    modelCode: item.modelCode || '',
                    modelName: item.modelName || '',
                    modelPrice: item.modelPrice || '',
                    virtualPrice: item.virtualPrice || '',
                    costPrice: item.costPrice || '',
                    modelStorage: item.modelStorage || '',
                    modelGeneralSn: item.modelGeneralSn || '',
                    thirdModelSn: item.thirdModelSn || '',
                    modelUnit: item.modelUnit || '',
                    model: item.model || '',
                    modelUrl: item.modelUrl || ''
                }
            })
            bindChangeInput('#goodsModelRows', goodsModelList)
            $('#goodsModelRows').empty().append(genHtml('goodsModelTmpl', goodsModelList))
        }
        // 绑定change事件
        function bindChangeInput(selecter, list) {
            $(selecter).on('change', function(e){
                var path = e.target.name
                var index = path.replace(/[^0-9]/ig,"")
                var name = path.split('.')[1]
                list[index][name] = e.target.value
            })

        }
        // 生成规格的HTMl
        function genHtml(templateId, list){
            var html = ""
            list.forEach(function(item) {
                html +=$('#' + templateId).html().render(item)
            })
            return html
        }
        // 添加规格
        function addModelRow(){
            if (!Array.isArray(goodsModelList)) {
                goodsModelList = []
            }
            var length = goodsModelList.length

            goodsModelList.push({
                index: length,
                id: '',
                goodsModelSn: '',
                modelCode: '',
                modelName: '',
                modelPrice: '',
                virtualPrice: '',
                costPrice: '',
                modelStorage: '',
                modelGeneralSn: '',
                thirdModelSn: '',
                modelUnit: '',
                model: '',
                modelUrl: ''
            })
            $('#goodsModelRows').empty().append(genHtml('goodsModelTmpl', goodsModelList))
        }
        // 删除规格行
        function delModelRow(index) {
            goodsModelList.splice(index, 1)
            goodsModelList = goodsModelList.map(function(item, index) {
                return {
                    index: index,
                    id: item.id,
                    goodsModelSn: item.goodsModelSn || '',
                    modelCode: item.modelCode || '',
                    modelName: item.modelName || '',
                    modelPrice: item.modelPrice || '',
                    virtualPrice: item.virtualPrice || '',
                    costPrice: item.costPrice || '',
                    modelStorage: item.modelStorage || '',
                    modelGeneralSn: item.modelGeneralSn || '',
                    thirdModelSn: item.thirdModelSn || '',
                    modelUnit: item.modelUnit || '',
                    model: item.model || '',
                    modelUrl: item.modelUrl || ''
                }
            })
            $('#goodsModelRows').empty().append(genHtml('goodsModelTmpl', goodsModelList))
        }

        // 规格类型相关
        // 初始化规格
        function initModelInfo(data){
            if(Array.isArray(data)){
                for (var i in data){
                    addRow(data[i]);
                }
            }
        }
        function addRow(obj){
            var existRows = $("#modelInfoRows tr").length;
            if (num - existRows > 10) {
                alertx('删除行数超过10条,此次不能新增行');
                return;
            }
            if(obj==""){
                obj = new Object();
                obj["id"] = "";
                obj["modelName"] = "";
                obj["modelId"] = "";
                obj["subModelId"] = num + 30;
                obj["subModelName"] = "";
            }
            var rowHtml = '<tr id="modelInfoRow' + num + '">\n' +
                '<td style="display:none;"><input type="text" class="input-small" name="infoList['+num+'].id" value="'+obj["id"]+'" id="row' + num + '-id" maxlength="64"/></td>\n' +
                '<td><input type="text" class="input-small required" name="infoList['+num+'].modelName" value="'+obj["modelName"]+'" readonly="readonly" id="row' + num + '-modelName" maxlength="32"/></td>\n' +
                '<td style="display:none;"><input type="text" class="input-small" name="infoList['+num+'].modelId" value="'+obj["modelId"]+'" id="row' + num + '-modelId" maxlength="10"/></td>\n' +
                '<td><input type="text" class="input-small required" name="infoList['+num+'].subModelId" value="'+obj["subModelId"]+'" maxlength="10"/></td>\n' +
                '<td><input type="text" class="input-small required" name="infoList['+num+'].subModelName" value="'+obj["subModelName"]+'" maxlength="10"/></td>\n' +
                '<td><input type="button" class="btn btn-primary" onclick="modelTypeChoice(' + num + ')" value="类型选择"/> \n' +
                '<input type="button" class="btn btn-primary" onclick="delRow(' + num + ')" value="删除"/></td>\n' +
                '</tr>';
            $("#modelInfoRows").append(rowHtml);
            num += 1;
        }

        function delRow(index){
            $("#modelInfoRow"+index).remove();
        }

        // 规格类型选择
        function modelTypeChoice(index){
            top.$.jBox(modelHtml, {
                title: "规格类型选择",
                submit: function(v, h, f){
                    var select = h.find("#modelType-select").find("option:selected");
                    if (select.val() == '') {
                        // f.modelType 或 h.find('#modelType').val() 等于 top.$('#modelType').val()
                        // top.$.jBox.tip("请输入点什么。", 'error', { focusId: "some" }); // 关闭设置 some 为焦点
                        top.$.jBox.info("未选择");
                        return true;
                    }
                    // top.$.jBox.info("你选择了：" + select.val());
                    $("#row"+index+"-modelName").val(select.text());
                    $("#row"+index+"-modelId").val(select.val());
                    return true;
                }
            });
        }

        function photoInit(select, photoUrl) {
            photoPreview(select);
            if(photoUrl){
                $("#"+ select +"-image-preview").css("background-image", "url("+ photoUrl +")");
                btnMsg(select);
            }
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
            // 商家 商品照片
            var
                merchantId = $("#merchantId").select2('val'),
                type = "03",
                formData = new FormData();
            formData.append("file", file);
            if(!merchantId){
                merchantId = "commonPhotos";
            }
            jQuery.ajax({
                url: '${ctx}/xiaodian/common/photoGallery/uploadPhoto/'+ merchantId + '/' + type,
                type: 'POST',
                dataType: "json",
                contentType: false,
                processData: false,
                data: formData,
                success: function (result) {
                    if(result.code == 200){
                        var photoGallery = JSON.parse(result.ext);
                        console.log("photoGallery", photoGallery.src);
                        $("#"+select).val(photoGallery.src);
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
	</script>
    <%--<link href="${ctxStatic}/image-upload/css/upload.css" type="text/css" rel="stylesheet"/>--%>
    <%--<script src="${ctxStatic}/image-upload/js/upload.js"></script>--%>
    <style>
        .msg {
            color:#FF0000 ;
        }
        .control-group {
            border-bottom: none;
        }
        .more-btn-box {
            text-align: center;
            border-top: 1px solid #EEEEEE;
            margin-bottom: 20px;
        }
        .more-btn-box a {
            display: inline-block;
            width: 80px;
            height: 40px;
        }
        .line {
            border-bottom: 1px solid #EEEEEE;
            margin: 10px 30px 20px;
        }
        .center {
            vertical-align: middle;
            text-align: center;
        }
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
		<li><a href="${ctx}/xiaodian/retail/goods/">商品列表</a></li>
		<li class="active">
            <a href="${ctx}/xiaodian/retail/goods/form?id=${goods.id}">商品
                <shiro:hasPermission name="xiaodian:retail:goods:edit">${not empty goods.id?'修改':'添加'}</shiro:hasPermission>
                <shiro:lacksPermission name="xiaodian:retail:goods:edit">查看</shiro:lacksPermission>
            </a>
        </li>
	</ul><br/>

	<form:form id="inputForm" modelAttribute="goodsInfoExt" action="${ctx}/xiaodian/retail/goods/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group idDiv" style="display: none">
			<label class="control-label">商品ID：</label>
			<div class="controls">
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
                <form:select id="merchantId" path="merchantId" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <c:forEach items="${merchantList}" var="merchant">
                        <form:option  value="${merchant.id}" label="${merchant.merchantName}" />
                    </c:forEach>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>店铺：</label>
			<div class="controls">
                <form:select path="shopId" class="input-xlarge required">
                    <form:option value="${goodsInfoExt.shopId}" label=""/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>商品分类：</label>
			<div class="controls" id="choiceCategory">
				<sys:treeselect id="goodsCategory" name="goodsCategoryId" value="${goodsInfoExt.goodsCategoryId}" labelName="categoryName"
								labelValue="${goodsInfoExt.categoryName}"
								title="数据分类" url="/xiaodian/retail/goodsCategory/treeData?limitShopId= " cssClass="" allowClear="true"
								notAllowSelectParent="true" checked="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品唯一编号：</label>
			<div class="controls">
				<form:input path="goodsSn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">第三方编号(即速)：</label>
            <div class="controls">
                <form:input path="thirdGoodsSn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">通用编号：</label>
            <div class="controls">
                <form:input path="generalSn" htmlEscape="false" maxlength="64" class="input-xlarge " />
            </div>
        </div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">图片：</label>
            <div class="controls">
                <form:textarea path="thumbUrl" htmlEscape="false" maxlength="255" rows="3" class="input-xxlarge" readonly="true"/>
                <p><input type="file" id="thumbUrl-file" name="thumbUrl-file" class="input-medium " />
                    <button type="button" id="thumbUrl-file-upload" onclick="fileUpload('thumbUrl')" >上传图片</button>
                </p>
                <div id="thumbUrl-image-preview" class="photo-style" ></div>
                <p id="thumbUrl-file-info">没有选择文件</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">价格：</label>
            <div class="controls">
                <form:input path="marketPrice" htmlEscape="false" maxlength="10" class="input-xlarge number required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">虚拟价格：</label>
            <div class="controls">
                <form:input path="virtualPrice" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">计量单位：</label>
            <div class="controls">
                <form:input path="unit" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">商品描述：</label>
			<div class="controls">
				<%--<form:textarea path="detailDesc" rows="3" htmlEscape="false" class="input-xxlarge "/>--%>
                <form:textarea path="detailDesc" htmlEscape="false" maxlength="60000" class="input-xlarge"/>
                <sys:ckeditor  replace="detailDesc" uploadPath="/retail/goods/detailDesc" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关键词：</label>
			<div class="controls">
				<form:textarea path="simpleDesc" htmlEscape="false" rows="2" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">商品品牌ID：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="brandId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
        <div class="control-group">
            <div class="line"></div>
            <label class="control-label">是否上架：</label>
            <div class="controls">
                <form:radiobuttons path="isMarketable" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                   itemValue="value" htmlEscape="false" class=""/>
            </div>
        </div>
        <div class="control-group">
            <div class="line"></div>
            <label class="control-label">商品状态：</label>
            <div class="controls">
                <form:select path="goodsStatus" class="input-small">
                    <form:option value="" label="" />
                    <form:options items="${fns:getDictList('goods_status')}" itemValue="value" itemLabel="label" />
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">库存数量：</label>
            <div class="controls">
                <form:input path="storage" htmlEscape="false" maxlength="5" class="input-xlarge required" onchange="checkNum('storage')"/>&nbsp;&nbsp;&nbsp;
                <span id="storageCheck" class="msg"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品来源：</label>
            <div class="controls">
                <form:select path="goodsFrom" class="input-small">
                    <form:option value="" label="" />
                    <form:options items="${fns:getDictList('goods_from')}" itemValue="value" itemLabel="label" />
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品排序：</label>
            <div class="controls">
                <form:input path="sort" htmlEscape="false" maxlength="4" class="input-xlarge digits" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">商品类型：</label>
            <div class="controls">
                <form:select path="goodsType" class="input-small">
                    <form:option value="" label="" />
                    <form:options items="${fns:getDictList('goods_style_type')}" itemValue="value" itemLabel="label" />
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"> 扫码购编码：</label>
            <div class="controls">
                <form:input path="scanCode" htmlEscape="false" maxlength="64" class="input-xlarge " />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注信息：</label>
            <div class="controls">
                <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
            </div>
        </div>
        <div class="options-wrapper">
            <div class="more-btn-box">
                <a id="collapseLabel" data-toggle="collapse" href="#moreOptions" >高级选项 <i class="icon-angle-down"></i>
                </a>
                <a style="display:none" >收起 <i class="icon-angle-up"></i></a>
            </div>
        </div>
        <div class="collapse" id="moreOptions">
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用商品规格：</label>
                <div class="controls">
                    <form:radiobuttons path="goodsModel" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('goodsModel')"/>
                </div>
            </div>
            <div class="goodsModelDiv" style="display:none;">
                <div class="controls">
                    <div id = "modelInfo">
                        <table id="modelInfoList" class="table table-striped table-bordered table-condensed" style="width: 50%">
                            <thead>
                            <tr id="modelInfoTitle">
                                <th style="min-width: 135px">规格类型</th>
                                <th style="display: none" >规格ID</th>
                                <th style="min-width: 135px">规格ID</th>
                                <th style="min-width: 135px">规格名称</th>
                                <th style="min-width: 150px">操作</th>
                            </tr>
                            </thead>
                            <tbody id="modelInfoRows">
                            </tbody>
                        </table>
                        <input type="button" id="addRowBtn-modelInfo" value="添加规格类型" class="btn btn-primary" onclick="addRow('')" style="margin-bottom: 20px;" />
                    </div><br/>
                    <div id="goodsModelTable">
                        <table id="goodsModelList" class="table table-striped table-bordered table-condensed" style="width: 50%">
                            <thead>
                            <tr id="goodsModelTitle">
                                <th style="display: none;">规格Id</th>
                                <th style="min-width: 135px">编号</th>
                                <th style="min-width: 135px">条形码</th>
                                <th style="min-width: 135px">规格名称</th>
                                <th style="min-width: 135px">售卖价格</th>
                                <th style="min-width: 135px">虚拟价格</th>
                                <th style="min-width: 135px">参考成本</th>
                                <th style="min-width: 135px">单位</th>
                                <th style="min-width: 135px">规格ID组合</th>
                                <th style="min-width: 135px">库存</th>
                                <th style="min-width: 135px">通用编号</th>
                                <th style="min-width: 135px">第三方编号</th>
                                <th style="min-width: 135px">图片地址</th>
                                <th style="min-width: 120px">操作</th>
                            </tr>
                            </thead>
                            <script id="goodsModelTmpl" type="text/x-template">
                                <tr>
                                    <td style="display: none;"><input class="input-small" type="text" name="goodsModelList[{{index}}].id" value="{{id}}" maxlength="64"/></td>
                                    <td><input class="input-small" type="text" name="goodsModelList[{{index}}].goodsModelSn" value="{{goodsModelSn}}" readonly="readonly" maxlength="32"/></td>
                                    <td><input class="input-small" type="text" name="goodsModelList[{{index}}].modelCode" value="{{modelCode}}" maxlength="32"/></td>
                                    <td><input class="input-small required" type="text" name="goodsModelList[{{index}}].modelName" value="{{modelName}}" maxlength="10"/></td>
                                    <td><input class="input-small number required" type="text" name="goodsModelList[{{index}}].modelPrice" value="{{modelPrice}}" maxlength="10"/></td>
                                    <td><input class="input-small number" type="text" name="goodsModelList[{{index}}].virtualPrice" value="{{virtualPrice}}" maxlength="10"/></td>
                                    <td><input class="input-small number" type="text" name="goodsModelList[{{index}}].costPrice" value="{{costPrice}}" maxlength="10"/></td>
                                    <td><input class="input-small required" type="text" name="goodsModelList[{{index}}].modelUnit" value="{{modelUnit}}" maxlength="10"/></td>
                                    <td><input class="input-small required" type="text" name="goodsModelList[{{index}}].model" value="{{model}}" maxlength="10"/></td>
                                    <td><input class="input-small digits required" type="text" name="goodsModelList[{{index}}].modelStorage" value="{{modelStorage}}" maxlength="5"/></td>
                                    <td><input class="input-small" type="text" name="goodsModelList[{{index}}].modelGeneralSn" value="{{modelGeneralSn}}" maxlength="20"/></td>
                                    <td><input class="input-small" type="text" name="goodsModelList[{{index}}].thirdModelSn" value="{{thirdModelSn}}" readonly="readonly" maxlength="20"/></td>
                                    <td><input class="input-small required" type="text" name="goodsModelList[{{index}}].modelUrl" value="{{modelUrl}}" maxlength="500"/>
                                        <%--<img src="{{modelUrl}}" alt=""/>--%>
                                    </td>
                                    <td><button class="btn btn-primary" onclick="delModelRow({{index}})" >删除</button></td>
                                </tr>
                            </script>
                            <tbody id="goodsModelRows">
                            </tbody>
                        </table>
                        <input type="button" id="addRowBtn" value="添加规格" class="btn btn-primary" onclick="addModelRow()" style="margin-bottom: 20px;" />
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">启用售卖规则：</label>
                <div class="controls">
                    <form:radiobuttons path="saleRule" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('saleRule')"/>
                </div>
            </div>
            <div class="saleRuleDiv" style="display:none;">
                <div class="control-group">
                    <label class="control-label">售卖区域：</label>
                    <div class="controls">
                        <form:input path="saleAreas" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">售卖时效：</label>
                    <div class="controls">
                        <form:input path="saleArriveType" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">起售数量：</label>
                    <div class="controls">
                        <form:input path="minSaleNum" htmlEscape="false" maxlength="5" class="input-xlarge digits"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">限制购买次数：</label>
                    <div class="controls">
                        <form:input path="limitBuyTimes" htmlEscape="false" maxlength="5" class="input-xlarge digits"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">配送类型限制：</label>
                    <div class="controls">
                        <form:select path="saleDeliveryType" class="input-xlarge ">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">起售时间：</label>
                    <div class="controls">
                        <input name="beginSaleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.beginSaleDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">结束售卖时间：</label>
                    <div class="controls">
                        <input name="endSaleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.endSaleDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用服务选项：</label>
                <div class="controls">
                    <form:radiobuttons path="serviceSelection" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('serviceSelection')"/>
                </div>
            </div>
            <div class="serviceSelectionDiv" style="display:none;">

                <div class="control-group">
                    <label class="control-label">服务名称：</label>
                    <div class="controls">
                        <input id="goodsServiceName" class="input-xlarge" type="text" onchange="inputServiceName()" value="" required/>
                    </div>
                </div>
                <div class="controls">
                    <table id="goodsServiceTable" class="table table-striped table-bordered table-condensed" style="width: 50%">
                        <thead>
                        <tr id="goodsServiceTitle">
                            <th style="min-width: 135px">服务内容</th>
                            <th style="min-width: 135px">价格</th>
                            <th style="min-width: 135px">次数</th>
                            <th style="min-width: 135px">周期</th>
                            <th style="min-width: 120px">操作</th>
                        </tr>
                        </thead>
                        <script id="goodsServiceTmpl" type="text/x-template">
                            <tr>
                                <td class="hide"><input class="input-small" type="hidden" name="serviceSelectionList[{{index}}].id" value="{{id}}" maxlength="64"/></td>
                                <td class="hide"><input class="input-small required" type="hidden" name="serviceSelectionList[{{index}}].serviceName" value="{{serviceName}}" maxlength="64"/></td>
                                <td><input class="input-xlarge required" type="text" name="serviceSelectionList[{{index}}].serviceDetail" value="{{serviceDetail}}" maxlength="64"/></td>
                                <td><input class="input-small number required" type="text" name="serviceSelectionList[{{index}}].servicePrice" value="{{servicePrice}}" maxlength="10"/></td>
                                <td><input class="input-small digits required" type="text" name="serviceSelectionList[{{index}}].serviceNum" value="{{serviceNum}}" maxlength="5"/></td>
                                <td>
                                    <select name="serviceSelectionList[{{index}}].serviceSchedule" data-value="{{serviceSchedule}}">
                                        <option value="">请选择</option>
                                        <c:forEach items="${fns:getDictList('delivery_plan_type')}" var="schedule">
                                            <option value="${schedule.value}" >${schedule}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <%--<td><input class="input-small" type="text" name="serviceSelectionList[{{index}}].serviceSchedule" value="{{schedule}}" required/></td>--%>
                                <td><a  onclick="delServiceOption({{index}})" >删除<a/></td>
                            </tr>
                        </script>
                        <tbody id="goodsServiceOptions">
                        </tbody>
                    </table>
                    <input type="button" id="addServiceOptionBtn" value="添加内容选项" class="btn btn-primary" onclick="addServiceOption()" style="margin-bottom: 20px;" />
                </div>
            </div>
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用配送规则：</label>
                <div class="controls">
                    <form:radiobuttons path="deliveryRule" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('deliveryRule')" />
                </div>
            </div>
            <div class="deliveryRuleDiv" style="display:none;">
                <div class="control-group">
                    <label class="control-label">配送方式选项：</label>
                    <div class="controls">
                        <form:select path="deliveryType" class="input-xlarge " id="deliveryType" multiple="multiple" placeholder="选择支持的配送方式">
                            <form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                        <span class="msg">*可多选</span>
                    </div>
                </div>
                <div class="control-group" id="deliveryPlanDiv" style="display: block;">
                    <label class="control-label">配送计划类型：</label>
                    <div class="controls">
                        <form:select path="deliveryPlanType" class="input-xlarge" id="deliveryPlanType" required="true" placeholder="请选择配送类型">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" onchange="changeDeliveryPlan()" htmlEscape="false"/>
                        </form:select>
                    </div>
                </div>
                <div id="deliveryScheduleDiv" >
                    <div class="control-group">
                        <label class="control-label">配送周期选项：</label>
                        <div class="controls">
                            <form:select path="deliverySchedule" class="input-xlarge " multiple="multiple">
                                <form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                            <span class="msg">*可多选</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">配送总数量：</label>
                        <div class="controls">
                            <form:input path="deliveryTotalNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">每次配送数量：</label>
                        <div class="controls">
                            <form:input path="deliveryNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">起送数量：</label>
                        <div class="controls">
                            <form:input path="minNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">规则描述：</label>
                        <div class="controls">
                            <form:input path="rulesDesc" htmlEscape="false" maxlength="500" class="input-xlarge "/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">第一次配送间隔：</label>
                        <div class="controls">
                            <form:input path="startDayNum" htmlEscape="false" maxlength="20" class="input-xlarge digits"/>
                        </div>
                    </div>
                </div>
                <div id="arriveTypeDiv" style="display: none;">
                    <div class="control-group">
                        <label class="control-label">配送时效：</label>
                        <div class="controls">
                            <form:select path="arriveType" class="input-xlarge ">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('delivery_arrive')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div id="specificDateDiv" style="display: none;">
                    <div class="control-group">
                        <label class="control-label">指定时间：</label>
                        <div class="controls">
                            <input id="specificDate" name="specificDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                   value="<fmt:formatDate value="${goodsInfoExt.specificDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用赠品：</label>
                <div class="controls">
                        <form:radiobuttons path="goodsGift" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                           itemValue="value" htmlEscape="false" class="" onchange="divChange('goodsGift')"/>
                </div>
            </div>
            <div class="goodsGiftDiv" style="display:none;">
                <div class="control-group">
                    <label class="control-label">赠品id：</label>
                    <div class="controls">
                        <form:input path="giftId" htmlEscape="false" maxlength="64" class="input-xlarge" readonly="true"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠品名称：</label>
                    <div class="controls">
                        <form:input path="giftName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠送规则：</label>
                    <div class="controls">
                        <form:input path="giftRule" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">起始日期：</label>
                    <div class="controls">
                        <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">结束日期：</label>
                    <div class="controls">
                        <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠送周期选项：</label>
                    <div class="controls">
                        <form:select path="giftSchedule" class="input-xlarge " multiple="multiple">
                            <form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                        <span class="msg">*可多选</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠品数量：</label>
                    <div class="controls">
                        <form:input path="giftNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠品图片：</label>
                    <div class="controls">
                        <form:hidden id="giftUrl" path="giftUrl" htmlEscape="false" class="input-xlarge"/>
                        <sys:ckfinder input="giftUrl" type="images" uploadPath="/goods/goodsGifts" selectMultiple="false"/>
                    </div>
                </div>
            </div>
        </div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">图片组编号：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="photoGroupNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:retail:goods:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

    <%--<script type="text/javascript">--%>

        <%--$(document).ready(function() {--%>

            <%--// 不关联商家id,类型为商品图片--%>
            <%--$("#upload-input").ajaxImageUpload({--%>
                <%--url: "${ctxFront}/xiaodian/api/upload/photo", //上传的服务器地址--%>
                <%--data: {--%>
                    <%--id: 'commonPhotos',--%>
                    <%--parent:'03'--%>
                <%--},--%>
                <%--maxNum: 10, //允许上传图片数量--%>
                <%--zoom: true, //允许放大--%>
                <%--allowType: ["gif", "jpeg", "jpg", "bmp",'png'], //允许上传图片的类型--%>
                <%--maxSize :5, //允许上传图片的最大尺寸，单位M--%>
                <%--before: function () {--%>
<%--//                alert('上传前回调函数');--%>
                <%--},--%>
                <%--success:function(data){--%>
<%--//                alert('上传成功回调函数');--%>
                    <%--console.log(data);--%>
                    <%--$("#thumbUrl").val(data.src);--%>
                <%--},--%>
                <%--error:function (e) {--%>
                    <%--alertx('上传失败回调函数');--%>
                    <%--console.log(e);--%>
                <%--}--%>
            <%--});--%>
        <%--});--%>

    <%--</script>--%>
</body>
</html>