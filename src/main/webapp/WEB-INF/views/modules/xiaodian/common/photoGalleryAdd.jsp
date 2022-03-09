<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片管理</title>
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
		});
	</script>
	<link href="${ctxStatic}/image-upload/css/upload.css" type="text/css" rel="stylesheet"/>
	<script src="${ctxStatic}/image-upload/js/upload.js"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/common/photoGallery/">图片列表</a></li>
		<%--<li><a href="${ctx}/xiaodian/common/photoGallery/form?id=${photoGallery.id}">图片<shiro:hasPermission name="xiaodian:common:photoGallery:edit">${not empty photoGallery.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:common:photoGallery:edit">查看</shiro:lacksPermission></a></li>--%>
		<shiro:hasPermission name="xiaodian:common:photoGallery:edit">
		<li class="active"><a href="${ctx}/xiaodian/common/photoGallery/batchUpload">图片批量上传</a></li>
		</shiro:hasPermission>
	</ul><br/>
	<div class="upload-box">
		<p class="upload-tip">公共商品图片：图片类型(gif,jpeg,jpg,bmp,pn"); 图片小于5MB</p>
		<input type="button" value="完成" onclick="window.location.href='${ctx}/xiaodian/common/photoGallery/'"/>
		<div class="image-box clear">
			<%--<section class="image-section">--%>
				<%--<div class="image-shade"></div>--%>
				<%--<div class="image-delete"></div>--%>
				<%--<img class="image-show" src="https://upjuzi.oss-cn-beijing.aliyuncs.com/upjuzi/20170816/20170816916be9364eb6f0827adae01b5d1e2356.jpg" />--%>
				<%--<input id="src" name="src" value="" type="hidden" />--%>
			<%--</section>--%>
			<section class="upload-section">
				<div class="upload-btn"></div>
				<input type="file" name="file" id="upload-input" value="" accept="image/jpg,image/jpeg,image/png,image/bmp"/>
			</section>
		</div>
	</div>

	<script type="text/javascript">

        $(document).ready(function() {

            // 不关联商家id,类型为商品图片
            $("#upload-input").ajaxImageUpload({
                url: "${ctxFront}/xiaodian/api/upload/photo", //上传的服务器地址
                data: {
                    id: 'commonPhotos',
					parent:'03'
				},
                maxNum: 10, //允许上传图片数量
                zoom: true, //允许放大
                allowType: ["gif", "jpeg", "jpg", "bmp",'png'], //允许上传图片的类型
                maxSize :5, //允许上传图片的最大尺寸，单位M
                before: function () {
//                alert('上传前回调函数');
                },
                success:function(data){
//                alert('上传成功回调函数');
                    console.log(data);
                },
                error:function (e) {
                    alertx('上传失败回调函数');
                    console.log(e);
                }
            });
        });

	</script>
</body>
</html>