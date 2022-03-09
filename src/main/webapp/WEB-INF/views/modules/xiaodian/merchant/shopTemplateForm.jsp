<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/xiaodian/css/upload.css" type="text/css" rel="stylesheet"/>
	<script src="${ctxStatic}/xiaodian/js/upload.js"></script>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/merchant/shopTemplate/">模板列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/merchant/shopTemplate/form?id=${shopTemplate.id}">模板<shiro:hasPermission name="xiaodian:merchant:shopTemplate:edit">${not empty shopTemplate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:merchant:shopTemplate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shopTemplate" action="${ctx}/xiaodian/merchant/shopTemplate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">行业类型：</label>
			<div class="controls">
				<sys:treeselect id="industry" name="industryCategory" value="${industry.id}" labelName="industry.name"
								labelValue="${industry.name}"
								title="行业分类" url="/xiaodian/industry/treeData" cssClass="" allowClear="true"
								notAllowSelectParent="true"/>
				<%--<form:input path="industryCategory" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">微信模板ID：</label>
			<div class="controls">
				<form:input path="wxModelId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小程序APPid：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板类型：</label>
			<div class="controls">
				<%--<form:input path="templateType" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
					<form:select path="templateType" class="input-xlarge ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('shop_template_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板名称：</label>
			<div class="controls">
				<form:input path="templateName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模板描述：</label>
			<div class="controls">
				<form:textarea path="templateDesc" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
				<form:input path="versionNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本描述：</label>
			<div class="controls">
				<form:textarea path="versionDesc" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>

		<div class="upload-box" id="shopTemplatePhotos_uploadbox">
			<p class="upload-tip">模板图片(5张):</p>
			<form:hidden path="shopTemplatePhotos.groupName" value="模板图片" />
			<form:hidden id="shopTemplatePhotos_groupNum" path="templatePic" value="${shopTemplate.id}-05"/>
			<div class="image-box clear" id="shopTemplatePhotos_imagebox">
				<c:forEach items="${shopTemplatePhotos.photoManageList}" var="photoManage" varStatus="vs">
					<section class="image-section">
						<input id="shopTemplatePhotos_photoManage_${vs.index}" name="shopTemplatePhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
						<form:hidden id="shopTemplatePhotos_photoGallery_${vs.index}" path="shopTemplatePhotos.photoManageList[${vs.index}].photoGallery.id"/>
						<form:hidden path="shopTemplatePhotos.photoManageList[${vs.index}].delFlag" value="0" />
						<div class="image-shade"></div>
						<div class="image-delete" id="${photoManage.id}"></div>
						<div class="image-zoom"></div>
						<img class="image-show"
							 src="${photoManage.picUrl}"/>
						<input
								class="file" name="file[]"
								value="${photoManage.picUrl}"
								type="hidden"/>
						<div align="center" id="shopTemplatePhotos_index">第 ${vs.count} 张</div>
					</section>
				</c:forEach>
				<c:if test="${fn:length(shopTemplatePhotos.photoManageList) lt 5 }">
					<section class="upload-section">
						<div class="upload-btn"></div>
						<input type="file" name="file" class="upload-input" value="" id="shopTemplatePhotos"/>
					</section>
				</c:if>

			</div>
		</div>


		<div class="control-group">
			<label class="control-label">上传时间：</label>
			<div class="controls">
				<input name="uploadTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${shopTemplate.uploadTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回信息：</label>
			<div class="controls">
				<form:input path="respDesc" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:merchant:shopTemplate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">

        $(document).ready(function() {
            upload("shopTemplatePhotos",5,"0");

            var reqData = {};
            function upload (id,maxNum,type) {

                $("#"+id).ajaxImageUpload({
                        staticPath:"${ctxStatic}",
                        id:id,
                        url: "${ctx}/xiaodian/common/photoGallery/uploadPhoto/${shopTemplate.id}/"+type, //上传的服务器地址
                        data: reqData,
                        maxNum: maxNum, //允许上传图片数量
                        zoom: true, //允许放大
                        allowType: ["gif", "jpeg", "jpg", "bmp",'png'], //允许上传图片的类型
                        maxSize :2, //允许上传图片的最大尺寸，单位M
                        before: function () {
//                alert('上传前回调函数');
                            reqData = {
                                "fileName" : "模板图片",
                                "id" : "2131",
                                "keepName" : true
                            };
                        },
                        success:function(data){
//                alert('上传成功回调函数');
//                console.log(data);
                            var imageBoxId = $("#"+(id+"_imagebox"));
                            var imageSection = imageBoxId.children('.image-section:first');
                            var index = imageBoxId.children('.image-section').length;
                            if(index > 0){
                                index = index -1;
                            }
//                  console.log("section",imageSection);
//               var groupNumId = id +"_groupNum";
//                var groupNum = $("#"+groupNumId).val();
                            console.log("index",index);
                            var manageId = "<input id='"+id+"_photoManage_"+index+ "'  name='"+id+".photoManageList["+index+"].id' value=' ' type='hidden' />";
                            var photoGallery ="<input id='"+id+"_photoGallery_"+index+ "'  name='"+id+".photoManageList["+index+"].photoGallery.id' value='"+data.id+"' type='hidden' />";

                            var  showMessage =  "<div align='center'>未保存</div>";
//               console.log("manageId",manageId);
//                console.log("photoGalleryId",photoGallery);
                            imageSection.append(manageId);
                            imageSection.append(photoGallery);
                            imageSection.append(showMessage);
                        },
                        error:function (e) {
                            alert('上传失败回调函数');
                            console.log(e);
                        },

                        delete : function (deleteId) {
                            $.ajax({
                                    type: "POST",
                                    url: "${ctx}/xiaodian/common/photoManage/deleteById?id="+deleteId,
                                    dataType: "json",
                                    contentType: "application/json",
                                    data:{id:deleteId},
                                    success:function (result) {
                                        if(200 == result.code){
                                            console.log(result);
                                            return true;
                                        }else{
                                            alert(result.msg);
                                            return false;
                                        }
                                    },
                                    error:function (e) {
                                        console.log("删除失败",e);
                                        return false;
                                    }

                                }

                            )

                        }
                    }

                );

            }
        });
	</script>
</body>
</html>