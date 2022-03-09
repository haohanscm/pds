<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
			//$("#name").focus();
			$("#inputForm").validate({
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
			// 属性名变化 加载属性值
            $("#attrNameIds").change(function () {
                var nameIds = $("#attrNameIds").val();
                nameIds = (nameIds) ? nameIds.join() : "";
                // console.log("nameIds", nameIds);
                findAttrValueList(nameIds);
            });
            // 属性值变化 修改规格详情
            $("#attrValueIds").change(function () {
                var values = new Array();
                var select = $("#attrValueIds").find("option:selected");
                // console.log("select", select);
                $.each(select, function (n, entity) {
                    values.push(entity.text);
                });
                // console.log("values", values);
                values = (values) ? values.join() : "";
                $("#attrDetail").val(values);
            });
            // spuId关联商品名称
            $("#spuId").change(function () {
                var name = $("#spuId").find("option:selected").text();
                $("#goodsName").val(name);
            });

            // 属性名初始化
            findAttrNameList();
            // 图片初始化
            photoInit('attrPhoto', '${stockKeepingUnit.attrPhoto}');
		});
        function findAttrNameList() {
            var spuId = $("#spuId").val() || "0";
            var name = $("#attrNameIds").val();
            name = name[0].split(",");
            console.log("name ", name);
            jQuery.ajax({
                url: '${ctx}/xiaodian/database/productAttrName/findAttrList',
                type: 'POST',
                dataType: "json",
                data: {
                    spuId:spuId
                },
                success: function (result) {
                    // console.log("name result", result);
                    var attrNameIds = $("#attrNameIds");
                    attrNameIds.empty();
                    if (null == result || ( 0 == result.length)) {
                        attrNameIds.append($("<option>").attr({"value": ""}).text(""));
                        attrNameIds.change();
                        alertx("没有找到相关属性");
                        return;
                    }
                    $.each(result, function (n, entity) {
                        attrNameIds.append($("<option>").attr({"value": entity.id}).text(entity.attrName));
                    });
                    attrNameIds.val(name).trigger("change");
                    // attrNameIds.change();
                },
                error: function (jqXHR){
                    alertx(jqXHR.responseText);
                }
            });

        }

        function findAttrValueList(attrNameId) {
            if(!attrNameId){
                $("#attrValueIds").empty();
                $("#attrValueIds").append($("<option>").attr({"value": ""}).text("请选择规格属性名"));
                $("#attrValueIds").change();
                return;
            }
            var spuId = $("#spuId").val();
            var value = $("#attrValueIds").val();
            console.log(value);
            if(value){
                value = value[0].split(",");
            }
            console.log("value ", value);
            jQuery.ajax({
                url: '${ctx}/xiaodian/database/productAttrValue/findAttrList',
                type: 'POST',
                dataType: "json",
                data: {
                    attrNameId:attrNameId,
                    spuId:spuId
                },
                success: function (result) {
                    // console.log("result", result);
                    var attrValueIds = $("#attrValueIds");
                    attrValueIds.empty();
                    if (null == result || ( 0 == result.length)) {
                        attrValueIds.append($("<option>").attr({"value": ""}).text(""));
                        attrValueIds.change();
                        alertx("没有找到相关属性");
                        return;
                    }
                    $.each(result, function (n, entity) {
                        attrValueIds.append($("<option>").attr({"value": entity.id}).text(entity.attrValue));
                    });
                    attrValueIds.val(value).trigger("change");
                    // attrValueIds.change();
                },
                error: function (jqXHR){
                    alertx(jqXHR.responseText);
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
            // 公共库 商品照片
            var
                merchantId = "commonPhotos",
                type = "03",
                formData = new FormData();
            formData.append("file", file);

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
		<li><a href="${ctx}/xiaodian/database/stockKeepingUnit/">库存商品列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/database/stockKeepingUnit/form?id=${stockKeepingUnit.id}">库存商品<shiro:hasPermission name="xiaodian:database:stockKeepingUnit:edit">${not empty stockKeepingUnit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:database:stockKeepingUnit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="stockKeepingUnit" action="${ctx}/xiaodian/database/stockKeepingUnit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标准商品id：</label>
			<div class="controls">
				<%--<form:input path="spuId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
                <form:select path="spuId" class="input-xlarge" >
                    <form:option value="" label=""/>
                    <form:options items="${spuList}" itemValue="id" itemLabel="goodsName" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格属性名：</label>
			<div class="controls">
                <form:select path="attrNameIds" class="input-xlarge" multiple="true">
                    <form:option value="${stockKeepingUnit.attrNameIds}" label=""/>
                </form:select>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">规格属性值：</label>
            <div class="controls">
                <form:select path="attrValueIds" class="input-xlarge" multiple="true">
                    <form:option value="${stockKeepingUnit.attrValueIds}" label=""/>
                </form:select>
            </div>
        </div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">规格属性名id集：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="attrNameIds" htmlEscape="false" maxlength="500" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">规格属性值id集：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="attrValueIds" htmlEscape="false" maxlength="500" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group" style="display: none">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sku商品编号：</label>
			<div class="controls">
				<form:input path="stockGoodsSn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">售价：</label>
			<div class="controls">
				<form:input path="salePrice" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">库存：</label>
			<div class="controls">
				<form:input path="stock" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格详情,拼接所有属性值：</label>
			<div class="controls">
				<form:input path="attrDetail" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扫码条码：</label>
			<div class="controls">
				<form:input path="scanCode" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格图片：</label>
			<div class="controls">
				<form:textarea path="attrPhoto" htmlEscape="false" maxlength="255" rows="4" class="input-xxlarge " readonly="true"/>
                <p><input type="file" id="attrPhoto-file" name="attrPhoto-file" class="input-medium " />
                    <button type="button" id="attrPhoto-file-upload" onclick="fileUpload('attrPhoto')" >上传图片</button>
                </p>
                <div id="attrPhoto-image-preview" class="photo-style" ></div>
                <p id="attrPhoto-file-info">没有选择文件</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重量(kg)：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">体积(m3)：</label>
			<div class="controls">
				<form:input path="volume" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:database:stockKeepingUnit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>