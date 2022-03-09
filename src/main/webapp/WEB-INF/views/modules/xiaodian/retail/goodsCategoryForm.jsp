<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				rules:{
				    shopId:"required",
                    name:"required",
                    // categoryType:"required",
                    sort:"digits"
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
            // shopId 变化影响商品分类选择
            $("#shopId").change(function () {
                var oldContent = $("#choiceCategory").children("script").html();
                // 空格为替换的结束标识
                var newContent = oldContent.replace(/limitShopId=\S*/,"limitShopId="+$("#shopId").val());
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#choiceCategory").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#parentButton, #parentName").unbind();
                document.getElementById("choiceCategory").appendChild(script);
            });
            // 初始化
            $("#merchantId").change();
            // 图片初始化
            photoInit('logo', '${goodsCategory.logo}');
		});

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
    <style>
        .msg {
            color:#FF0000 ;
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
		<li><a href="${ctx}/xiaodian/retail/goodsCategory/">商品分类列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/retail/goodsCategory/form?id=${goodsCategory.id}">商品分类<shiro:hasPermission name="xiaodian:retail:goodsCategory:edit">${not empty goodsCategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:retail:goodsCategory:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsCategory" action="${ctx}/xiaodian/retail/goodsCategory/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商家ID：</label>
			<div class="controls">
				<form:select path="merchantId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${merchantList}" itemValue="id" itemLabel="merchantName" />
				</form:select>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label"><span class="msg">* </span>店铺：</label>
            <div class="controls">
				<form:select path="shopId" class="input-xlarge required">
					<form:option value="${goodsCategory.shopId}" label=""/>
				</form:select>
            </div>
        </div>
		<div class="control-group" style="display: none">
			<label class="control-label">行业名称：</label>
			<div class="controls">
				<form:input path="industry" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签：</label>
			<div class="controls">
				<form:input path="keywords" htmlEscape="false" maxlength="100" class="input-xlarge "/> &nbsp;&nbsp;&nbsp;<span class="msg">英文逗号分隔</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级父级编号:</label>
			<div class="controls" id="choiceCategory">
				<sys:treeselect id="parent" name="parent.id" value="${goodsCategory.parent.id}" labelName="parent.name" labelValue="${goodsCategory.parent.name}"
								title="父级编号" url="/xiaodian/retail/goodsCategory/treeData?limitShopId= " extId="${goodsCategory.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">商品分类通用编号：</label>
            <div class="controls">
                <form:input path="generalCategorySn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">分类类型：</label>
			<div class="controls">
				<%--<form:input path="categoryType" htmlEscape="false" maxlength="50" class="input-xlarge "/>--%>
				<form:select path="categoryType"  class="input-medium " >
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('retail_category_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">分类图标：</label>
            <div class="controls">
                <form:textarea path="logo" htmlEscape="false" maxlength="255" rows="3" class="input-xlarge" readonly="true"/>
                <%--<sys:ckfinder input="logo" type="images" uploadPath="/retail/goodsCategory" selectMultiple="false"/>--%>
                <p><input type="file" id="logo-file" name="logo-file" class="input-medium " />
                    <button type="button" id="logo-file-upload" onclick="fileUpload('logo')" >上传图片</button>
                </p>
                <div id="logo-image-preview" class="photo-style" ></div>
                <p id="logo-file-info">没有选择文件</p>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" maxlength="4" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">第三方编号(即速)：</label>
			<div class="controls">
				<form:input path="categorySn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父分类编号(即速)：</label>
			<div class="controls">
				<form:input path="parentSn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
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
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:retail:goodsCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>