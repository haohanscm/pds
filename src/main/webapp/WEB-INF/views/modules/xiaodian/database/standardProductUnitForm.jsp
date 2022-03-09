<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>标准商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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

            // 图片初始化
            photoInit('thumbUrl', '${standardProductUnit.thumbUrl}');
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
		<li><a href="${ctx}/xiaodian/database/standardProductUnit/">标准商品列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/database/standardProductUnit/form?id=${standardProductUnit.id}">标准商品<shiro:hasPermission name="xiaodian:database:standardProductUnit:edit">${not empty standardProductUnit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:database:standardProductUnit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="standardProductUnit" action="${ctx}/xiaodian/database/standardProductUnit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
        <!-- 分类不允许多选  -->
		<div class="control-group">
			<label class="control-label">商品分类：</label>
			<div class="controls">
                <sys:treeselect id="goodsCategory" name="goodsCategoryId"
                                value="${standardProductUnit.goodsCategoryId}" labelName="categoryName"
                                labelValue="${standardProductUnit.categoryName}"
                                title="父级编号" url="/xiaodian/database/productCategory/treeData" cssClass=""
                                allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品描述：</label>
			<div class="controls">
				<form:input path="detailDesc" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品通用编号：</label>
			<div class="controls">
				<form:input path="generalSn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">类型：</label>
            <div class="controls">
                <form:select path="aggregationType" class="input-medium required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('aggregation_shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">图片地址：</label>
			<div class="controls">
				<form:textarea path="thumbUrl" htmlEscape="false" maxlength="255" rows="3" class="input-xxlarge " readonly="true"/>
                <p><input type="file" id="thumbUrl-file" name="thumbUrl-file" class="input-medium " />
                    <button type="button" id="thumbUrl-file-upload" onclick="fileUpload('thumbUrl')" >上传图片</button>
                </p>
                <div id="thumbUrl-image-preview" class="photo-style" ></div>
                <p id="thumbUrl-file-info">没有选择文件</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业：</label>
			<div class="controls">
				<form:input path="industry" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">品牌：</label>
			<div class="controls">
				<form:input path="brand" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厂家/制造商：</label>
			<div class="controls">
				<form:input path="manufacturer" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片组编号：</label>
			<div class="controls">
				<form:input path="photoGroupNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:database:standardProductUnit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>