<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家照片管理</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxStatic}/xiaodian/css/upload.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/xiaodian/js/upload.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
    <li><a href="${ctx}/xiaodian/merchant/">商家列表</a></li>
    <shiro:hasPermission name="xiaodian:merchant:edit">
        <li><a href="${ctx}/xiaodian/merchant/form?id=${merchant.id}">商家资料</a></li>
    </shiro:hasPermission>
    <li class="active"><a href="${ctx}/xiaodian/merchant/photoManage?id=${merchant.id}">商家资料<shiro:hasPermission
            name="xiaodian:merchant:edit">${not empty merchant.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:merchant:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="merchantFiles" action="${ctx}/xiaodian/merchant/saveMerchantFiles"
           method="post" class="form-horizontal">
    <form:hidden id="merchantId" path="merchantId"/>
    <sys:message content="${message}"/>

    <div class="upload-box" id="shopPhotos_uploadbox">
        <p class="upload-tip">商家门店照片(5张):</p>
        <form:hidden path="shopPhotos.groupName" value="门店照片" />
        <form:hidden id="shopPhotos_groupNum" path="shopPhotos.groupNum" value="${merchantFiles.merchantId}-00"/>
        <div class="image-box clear" id="shopPhotos_imagebox">
                <%--<c:out value="${fn:length(shopPhotos.photoManageList)}" />--%>
            <c:forEach items="${shopPhotos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="shopPhotos_photoManage_${vs.index}" name="shopPhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="shopPhotos_photoGallery_${vs.index}" path="shopPhotos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="shopPhotos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="shopPhotos_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(shopPhotos.photoManageList) lt 5 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="shopPhotos"/>
                </section>
            </c:if>

        </div>
            <%--<form:hidden id="shopPhotos_photoGalleryIds" path="shopPhotos.photoGalleryIds" value=""/>--%>
    </div>
    <div class="upload-box" id="merchantPhotos_uploadbox">
        <p class="upload-tip">商户照片(4张):营业执照、身份证-正反面、结算卡</p>
        <form:hidden path="merchantPhotos.groupName" value="商户照片" />
        <form:hidden id="merchantPhotos_groupNum" path="merchantPhotos.groupNum" value="${merchantFiles.merchantId}-01"/>
        <div class="image-box clear" id="merchantPhotos_imagebox">
            <c:forEach items="${merchantPhotos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="merchantPhotos_photoManage_${vs.index}" name="merchantPhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="merchantPhotos_photoGallery_${vs.index}" path="merchantPhotos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="merchantPhotos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="merchantPhotos_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(merchantPhotos.photoManageList) lt 5 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="merchantPhotos"/>
                </section>
            </c:if>

        </div>
    </div>
    <div class="upload-box" id="protocolFiles_uploadbox">
        <p class="upload-tip">协议扫描件(8张):</p>
        <form:hidden path="protocolFiles.groupName" value="协议文件" />
        <form:hidden id="protocolFiles_groupNum" path="protocolFiles.groupNum" value="${merchantFiles.merchantId}-02"/>
        <div class="image-box clear" id="protocolFiles_imagebox">
            <c:forEach items="${protocolFiles.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="protocolFiles_photoManage_${vs.index}" name="protocolFiles.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="protocolFiles_photoGallery_${vs.index}" path="protocolFiles.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="protocolFiles.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="protocolFiles_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(protocolFiles.photoManageList) lt 8 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="protocolFiles"/>
                </section>
            </c:if>

        </div>
    </div>
    <!--
    <div class="upload-box" id="cateringPhotos_uploadbox">
        <p class="upload-tip">餐饮许可证(餐饮商家必传)</p>
        <form:hidden path="cateringPhotos.groupName" value="餐饮许可证" />
        <form:hidden id="cateringPhotos" path="cateringPhotos.groupNum" value="${merchantFiles.merchantId}-04"/>
        <div class="image-box clear" id="cateringPhotos_imagebox">
            <c:forEach items="${cateringPhotos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="cateringPhotos_photoManage_${vs.index}" name="cateringPhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="cateringPhotos_photoGallery_${vs.index}" path="cateringPhotos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="cateringPhotos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="cateringPhotos_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(cateringPhotos.photoManageList) lt 5 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="cateringPhotos"/>
                </section>
            </c:if>
        </div>
    </div>
    <div class="upload-box" id="licensePhotos_uploadbox">
        <p class="upload-tip">营业执照</p>
        <form:hidden path="licensePhotos.groupName" value="营业执照" />
        <form:hidden id="licensePhotos" path="licensePhotos.groupNum" value="${merchantFiles.merchantId}-05"/>
        <div class="image-box clear" id="licensePhotos_imagebox">
            <c:forEach items="${licensePhotos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="licensePhotos_photoManage_${vs.index}" name="licensePhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="licensePhotos_photoGallery_${vs.index}" path="licensePhotos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="licensePhotos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="licensePhotos_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(licensePhotos.photoManageList) lt 5 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="licensePhotos"/>
                </section>
            </c:if>
        </div>
    </div>
    <div class="upload-box" id="idCardsPhotos_uploadbox">
        <p class="upload-tip">身份证正反面</p>
        <form:hidden path="idCardsPhotos.groupName" value="餐饮许可证" />
        <form:hidden id="idCardsPhotos" path="idCardsPhotos.groupNum" value="${merchantFiles.merchantId}-07"/>
        <div class="image-box clear" id="idCardsPhotos_imagebox">
            <c:forEach items="${idCardsPhotos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="idCardsPhotos_photoManage_${vs.index}" name="idCardsPhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="idCardsPhotos_photoGallery_${vs.index}" path="idCardsPhotos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="idCardsPhotos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="idCardsPhotos_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(idCardsPhotos.photoManageList) lt 5 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="idCardsPhotos"/>
                </section>
            </c:if>
        </div>
    </div>
    <div class="upload-box" id="bankCardsPhotos_uploadbox">
        <p class="upload-tip">结算卡或对公账户</p>
        <form:hidden path="bankCardsPhotos.groupName" value="餐饮许可证" />
        <form:hidden id="bankCardsPhotos" path="bankCardsPhotos.groupNum" value="${merchantFiles.merchantId}-06"/>
        <div class="image-box clear" id="bankCardsPhotos_imagebox">
            <c:forEach items="${bankCardsPhotos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="bankCardsPhotos_photoManage_${vs.index}" name="bankCardsPhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="bankCardsPhotos_photoGallery_${vs.index}" path="bankCardsPhotos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="bankCardsPhotos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="bankCardsPhotos_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(bankCardsPhotos.photoManageList) lt 5 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="bankCardsPhotos"/>
                </section>
            </c:if>
        </div>
    </div>
    -->
    <div class="upload-box" id="productPhotos_uploadbox">
        <p class="upload-tip">商品照片:</p>
        <form:hidden path="productPhotos.groupName" value="商品照片" />
        <form:hidden id="productPhotos_groupNum" path="productPhotos.groupNum" value="${merchantFiles.merchantId}-03"/>
        <div class="image-box clear" id="productPhotos_imagebox">
            <c:forEach items="${productPhotos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="productPhotos_photoManage_${vs.index}" name="productPhotos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="productPhotos_photoGallery_${vs.index}" path="productPhotos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="productPhotos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="productPhotos_index">第 ${vs.count} 张</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(productPhotos.photoManageList) lt 5 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="productPhotos"/>
                </section>
            </c:if>

        </div>
    </div>


    <div class="form-actions">
        <shiro:hasPermission name="xiaodian:merchant:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                                  value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script type="text/javascript">

    $(document).ready(function() {
        upload("shopPhotos",5,"00");
        upload("merchantPhotos",5,"01");
        upload("protocolFiles",8,"02");
        upload("productPhotos",5,"03");

        // upload("cateringPhotos",3,"04");
        // upload("licensePhotos",2,"05");
        // upload("idCardsPhotos",2,"07");
        // upload("bankCardsPhotos",3,"06");
        var reqData = {};
        function upload (id,maxNum,type) {

            $("#"+id).ajaxImageUpload({
                    staticPath:"${ctxStatic}",
                    id:id,
                    url: "${ctx}/xiaodian/common/photoGallery/uploadPhoto/${merchantFiles.merchantId}/"+type, //上传的服务器地址
                    data: reqData,
                    maxNum: maxNum, //允许上传图片数量
                    zoom: true, //允许放大
                    allowType: ["gif", "jpeg", "jpg", "bmp",'png'], //允许上传图片的类型
                    maxSize :3, //允许上传图片的最大尺寸，单位M
                    before: function () {
//                alert('上传前回调函数');
                        reqData = {
                            "fileName" : "门店照片",
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