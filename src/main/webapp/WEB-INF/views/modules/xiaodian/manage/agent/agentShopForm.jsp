<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="author" content="http://www.haohanwork.com/"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10"/>
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-store">
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery/jquery.qrcode.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css"
          type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet"/>
    <!--[if lte IE 7]>
    <link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet"/>
    <![endif]-->
    <!--[if lte IE 6]>
    <link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
    <%--<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />--%>
    <%--<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>--%>
    <link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet"/>
    <script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/common/haohan.css" type="text/css" rel="stylesheet"/>
    <%--<script src="${ctxStatic}/common/haohan.js" type="text/javascript"></script>--%>
    <script type="text/javascript">var ctx = '${ctx}', ctxStatic = '${ctxStatic}';</script>
    <script type="text/javascript"
            src="http://webapi.amap.com/maps?v=1.4.2&key=a78d2ac80cf686c0e5715c1a756b84d0&plugin=AMap.DistrictSearch,AMap.ToolBar"></script>
    <script src="http://webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
    <link href="${ctxStatic}/xiaodian/css/upload.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/xiaodian/js/upload.js"></script>

    <title>????????????</title>
    <%--<meta name="decorator" content="default"/>--%>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('????????????????????????...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("??????????????????????????????");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            $("#btnSubmit").click(function () {
                //????????????
                $("#addressVal").val($("#address").html());
                //???????????????
                var lnglatVal ;
                var longitude = $("#mapLongitude").val();
                var latitude = $("#mapLatitude").val();
                // ???????????????????????????
                if(""!=longitude && ""!=latitude){
                    lnglatVal = longitude+","+latitude;
                }else{
                    lnglatVal = $("#lnglat").html();
                }

                if (lnglatVal) {
                    var v1 = lnglatVal.split(",");
                    $("#mapLongitude").val(v1[0]);
                    $("#mapLatitude").val(v1[1]);
                    //????????????????????????
                    $("#provinceVal").val(fetchSelectVal("province"));
                    $("#cityVal").val(fetchSelectVal("city"));
                    $("#districtVal").val(fetchSelectVal("district"));
                    $("#streetVal").val(fetchSelectVal("street"));
                    $("#lnglatVal").val(lnglatVal);
                }
            })
        });

        function fetchSelectVal(obj) {
            return $("#" + obj).find("option:selected").text();
        }
    </script>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        #mapContainer {
            height: 500px;
            width: 700px;
            position: relative;
            float: none;
        }

        #tip {
            position: relative;
            right: 0;
            top: 0;
            border: 0;
            font-size: 16px;
            padding: 0;
        }

        .form-item {
            margin-bottom: 10px;
            padding-bottom: 10px;
            overflow: auto;
            clear: both;
            font-size: 16px;
        }

        .form-item .title {
            float: left;
            width: 160px;
            text-align: right;
        }

        .form-item .cont {
            padding-left: 10px;
            overflow: auto;
            font-size: 14px;
        }

        .form-item input[type='radio'] {
            width: 20px;
            height: 20px;
            margin-right: 4px;
            vertical-align: -4px;
        }

        .form-item select {
            height: 30px;
        }

        .form-horizontal .controls input {
            height: 30px;
        }

    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/merchant/shop/">????????????</a></li>
    <li class="active"><a href="${ctx}/xiaodian/merchant/shop/form?id=${shop.id}">??????<shiro:hasPermission
            name="xiaodian:merchant:shop:edit">${not empty shop.id?'??????':'??????'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:merchant:shop:edit">??????</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="shopInfo" action="${ctx}/xiaodian/merchant/shop/save" method="post"
           class="form-horizontal">
    <form:hidden path="shop.id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">shopID???</label>
        <div class="controls">${shop.id}</div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:select id="merchantId" path="shop.merchantId" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${merchantList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">????????????ID???</label>
        <div class="controls">
            <form:input path="shop.code" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:input path="shop.name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:select path="shop.shopType" class="input-xshort ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <sys:treeselect id="area" name="shop.area.id" value="${shop.area.id}" labelName="shop.area.name"
                            labelValue="${shop.area.name}"
                            title="??????" url="/sys/area/treeData" cssClass="" allowClear="true"
                            notAllowSelectParent="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:input path="shop.deliverDistence" htmlEscape="false" maxlength="64" class="input-large "/>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:input id="shop.addressVal" path="shop.address" htmlEscape="false" maxlength="100"
                        class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">??????????????????</label>
        <div class="controls">
            <form:input path="shop.manager" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:input path="shop.telephone" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <div id="tip" class="form-item">
                <div class="cont" style="margin-left: -10px;">
                    <form:hidden id="provinceVal" path="shopLocation.province"/>
                    <form:hidden id="cityVal" path="shopLocation.city"/>
                    <form:hidden id="districtVal" path="shopLocation.district"/>
                    ??? <select id='province' style="width:100px" onchange="search(this)">
                    <option selected="true">${shopLocation.province}</option>
                </select>
                    ??? <select id='city' style="width:100px" onchange='search(this)'>
                    <option selected="true">${shopLocation.city}</option>
                </select>
                    ??? <select id='district' style="width:100px" onchange='search(this)'>
                    <option selected="true">${shopLocation.district}</option>
                </select>
                </div>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">?????????</label>
        <div class="controls">
            <form:hidden id="streetVal" path="shopLocation.street"/>
            <select id="street" style="width:200px" onchange='setCenter(this)'>
                <option>${shopLocation.street}</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <div id="mapContainer"></div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">?????????</label>
        <div class="controls">
            <input type='radio' name='mode' value='dragMap' checked>??????????????????</input>
            </br>
            <input type='radio' name='mode' value='dragMarker'>??????Marker??????</input>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">?????????</label>
        <div class="controls">
            <button id='start'>????????????</button>
            <button id='stop'>????????????</button>
        </div>
    </div>
    <form:hidden id="lnglatVal" path="shopLocation.lnglat"/>
    <div class="control-group">
        <label class="control-label">????????????</label>
        <div class="controls">
            <div id="lnglat"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">?????????</label>
        <div class="controls">
            <div id="address"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">??????????????????</label>
        <div class="controls">
            <div id='nearestJunction'></div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <div id="nearestRoad"></div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">?????????POI???</label>
        <div class="controls">
            <div id="nearestPOI"></div>
        </div>
    </div>


    <div class="control-group" >
        <label class="control-label">?????????</label>
        <div class="controls">
            <form:input id="mapLongitude" path="shop.mapLongitude" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group" >
        <label class="control-label">?????????</label>
        <div class="controls">
            <form:input id="mapLatitude" path="shop.mapLatitude" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>


    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:input path="shop.onlineTime" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:textarea path="shop.shopService" htmlEscape="false" maxlength="200" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">????????????ID???</label>
        <div class="controls">
            <form:input path="shop.templateId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:textarea path="shop.shopDesc" htmlEscape="false" maxlength="500" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:input path="shop.photoGroupNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>

    <div class="upload-box" id="shopLogos_uploadbox">
        <p class="upload-tip">??????Logo:</p>
        <form:hidden path="shopLogos.groupName" value="??????Logo" />
        <form:hidden id="shopLogos_groupNum" path="shop.shopLogo" value="${shop.merchantId}-10"/>
        <div class="image-box clear" id="shopLogos_imagebox">
            <c:forEach items="${shopLogos.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="shopLogos_photoManage_${vs.index}" name="shopLogos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="shopLogos_photoGallery_${vs.index}" path="shopLogos.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="shopLogos.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="shopLogo_index">??? ${vs.count} ???</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(shopLogo.photoManageList) lt 2 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="shopLogos"/>
                </section>
            </c:if>
        </div>
    </div>


    <div class="upload-box" id="shopPayCodes_uploadbox">
        <p class="upload-tip">?????????????????????:</p>
        <form:hidden path="shopPayCodes.groupName" value="?????????????????????" />
        <form:hidden id="shopPayCodes_groupNum" path="shop.payCode" value="${shop.merchantId}-11"/>
        <div class="image-box clear" id="shopPayCodes_imagebox">
            <c:forEach items="${shopPayCodes.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="shopPayCodes_photoManage_${vs.index}" name="shopPayCodes.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="shopPayCodes_photoGallery_${vs.index}" path="shopPayCodes.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="shopPayCodes.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="shopLogo_index">??? ${vs.count} ???</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(shopPayCodes.photoManageList) lt 2 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="shopPayCodes"/>
                </section>
            </c:if>
        </div>
    </div>


    <div class="upload-box" id="shopQrCodes_uploadbox">
        <p class="upload-tip">???????????????:</p>
        <form:hidden path="shopQrCodes.groupName" value="???????????????" />
        <form:hidden id="shopQrCodes_groupNum" path="shop.qrcode" value="${shop.merchantId}-12"/>
        <div class="image-box clear" id="shopQrCodes_imagebox">
            <c:forEach items="${shopQrCodes.photoManageList}" var="photoManage" varStatus="vs">
                <section class="image-section">
                    <input id="shopQrCodes_photoManage_${vs.index}" name="shopQrCodes.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>
                    <form:hidden id="shopQrCodes_photoGallery_${vs.index}" path="shopQrCodes.photoManageList[${vs.index}].photoGallery.id"/>
                    <form:hidden path="shopQrCodes.photoManageList[${vs.index}].delFlag" value="0" />
                    <div class="image-shade"></div>
                    <div class="image-delete" id="${photoManage.id}"></div>
                    <div class="image-zoom"></div>
                    <img class="image-show"
                         src="${photoManage.picUrl}"/>
                    <input
                            class="file" name="file[]"
                            value="${photoManage.picUrl}"
                            type="hidden"/>
                    <div align="center" id="shopLogo_index">??? ${vs.count} ???</div>
                </section>
            </c:forEach>
            <c:if test="${fn:length(shopQrCodes.photoManageList) lt 2 }">
                <section class="upload-section">
                    <div class="upload-btn"></div>
                    <input type="file" name="file" class="upload-input" value="" id="shopQrCodes"/>
                </section>
            </c:if>
        </div>
    </div>
    


    <div class="control-group">
        <label class="control-label">?????????</label>
        <div class="controls">
            <form:select path="shop.status" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('status_merchant')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">???????????????</label>
        <div class="controls">
            <form:textarea path="shop.remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="xiaodian:merchant:shop:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                       type="submit"
                                                                       value="??? ???"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="??? ???" onclick="history.go(-1)"/>
    </div>

</form:form>
<script type="text/javascript">

    $(document).ready(function() {
        upload("shopLogos",1,"10");
        upload("shopPayCodes",1,"11");
        upload("shopQrCodes",1,"12");


        var reqData = {};
        function upload (id,maxNum,type) {

            $("#"+id).ajaxImageUpload({
                    staticPath:"${ctxStatic}",
                    id:id,
                    url: "${ctx}/xiaodian/common/photoGallery/uploadPhoto/${shop.merchantId}-10/"+type, //????????????????????????
                    data: reqData,
                    maxNum: maxNum, //????????????????????????
                    zoom: true, //????????????
                    allowType: ["gif", "jpeg", "jpg", "bmp",'png'], //???????????????????????????
                    maxSize :2, //??????????????????????????????????????????M
                    before: function () {
//                alert('?????????????????????');
                       reqData = {
                            "fileName" : "????????????",
                            "id" : "2131",
                            "keepName" : true
                        };
                    },
                    success:function(data){
//                alert('????????????????????????');
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
                        var manageId = "<input id='"+id+"_photoManage_"+index+ "'  name='"+id+".photoManageList["+index+"].id' value=' ' type='hidden' />";
                        var photoGallery ="<input id='"+id+"_photoGallery_"+index+ "'  name='"+id+".photoManageList["+index+"].photoGallery.id' value='"+data.id+"' type='hidden' />";

                        var  showMessage =  "<div align='center'>?????????</div>";
//               console.log("manageId",manageId);
//                console.log("photoGalleryId",photoGallery);
                        imageSection.append(manageId);
                        imageSection.append(photoGallery);
                        imageSection.append(showMessage);
                    },
                    error:function (e) {
                        alert('????????????????????????');
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
                                    console.log("????????????",e);
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
<script type="text/javascript">
    var map, district, polygons = [], citycode;

    var citySelect = document.getElementById('city');
    var districtSelect = document.getElementById('district');
    var areaSelect = document.getElementById('street');

    if (typeof map !== 'undefined') {
        map.on('complete', function () {
            if (location.href.indexOf('guide=1') !== -1) {
                map.setStatus({
                    scrollWheel: false
                });
                if (location.href.indexOf('litebar=0') === -1) {
                    map.plugin(["AMap.ToolBar"], function () {
                        var options = {
                            liteStyle: true
                        }
                        if (location.href.indexOf('litebar=1') !== -1) {
                            options.position = 'LT';
                            options.offset = new AMap.Pixel(10, 40);
                        } else if (location.href.indexOf('litebar=2') !== -1) {
                            options.position = 'RT';
                            options.offset = new AMap.Pixel(20, 40);
                        } else if (location.href.indexOf('litebar=3') !== -1) {
                            options.position = 'LB';
                        } else if (location.href.indexOf('litebar=4') !== -1) {
                            options.position = 'RB';
                        }
                        map.addControl(new AMap.ToolBar(options));
                    });
                }
            }
        });
    }
    ;
    var v1 = $("#mapLongitude").val();
    var v2 = $("#mapLatitude").val();
    if (!v1 || !v2) {
//        106.26164,29.279368 ???????????????
        v1 = "106.26164";
        v2 = "29.279368";
    }
    map = new AMap.Map('mapContainer', {
        resizeEnable: true,
        center: [v1, v2],
        zoom: 16,
        scrollWheel: false
    });
    AMapUI.loadUI(['misc/PositionPicker'], function (PositionPicker) {
        var positionPicker = new PositionPicker({
            mode: 'dragMap',
            map: map
        });

        positionPicker.on('success', function (positionResult) {
            document.getElementById('lnglat').innerHTML = positionResult.position;
            document.getElementById('address').innerHTML = positionResult.address;
            document.getElementById('nearestJunction').innerHTML = positionResult.nearestJunction;
            document.getElementById('nearestRoad').innerHTML = positionResult.nearestRoad;
            document.getElementById('nearestPOI').innerHTML = positionResult.nearestPOI;
        });
        positionPicker.on('fail', function (positionResult) {
            document.getElementById('lnglat').innerHTML = ' ';
            document.getElementById('address').innerHTML = ' ';
            document.getElementById('nearestJunction').innerHTML = ' ';
            document.getElementById('nearestRoad').innerHTML = ' ';
            document.getElementById('nearestPOI').innerHTML = ' ';
        });
        var onModeChange = function (e) {
            positionPicker.setMode(e.target.value)
        }
        var startButton = document.getElementById('start');
        var stopButton = document.getElementById('stop');
        var dragMapMode = document.getElementsByName('mode')[0];
        var dragMarkerMode = document.getElementsByName('mode')[1];
        AMap.event.addDomListener(startButton, 'click', function () {
            positionPicker.start(map.getBounds().getSouthWest())
        })
        AMap.event.addDomListener(stopButton, 'click', function () {
            positionPicker.stop();
        })
        AMap.event.addDomListener(dragMapMode, 'change', onModeChange)
        AMap.event.addDomListener(dragMarkerMode, 'change', onModeChange);
        positionPicker.start();
        map.panBy(0, 1);

        map.addControl(new AMap.ToolBar({
            liteStyle: true
        }))
    });

    //??????????????????
    var opts = {
        subdistrict: 1,   //????????????????????????
        showbiz: false  //??????????????????????????????
    };
    district = new AMap.DistrictSearch(opts);//?????????????????????????????????????????????????????????????????????
    district.search('??????', function (status, result) {
        if (status == 'complete') {
            console.log("data:" + result.districtList[0]);
            getData(result.districtList[0]);
        }
    });
    function getData(data, level) {
        var bounds = data.boundaries;
        if (bounds) {
            for (var i = 0, l = bounds.length; i < l; i++) {
                var polygon = new AMap.Polygon({
                    map: map,
                    strokeWeight: 1,
                    strokeColor: '#CC66CC',
                    fillColor: '#CCF3FF',
                    fillOpacity: 0,
                    path: bounds[i]
                });

                polygons.push(polygon);
            }
            map.setFitView();//???????????????
        }


        //?????????????????????????????????
        if (level === 'province') {
            citySelect.innerHTML = '';
            districtSelect.innerHTML = '';
            areaSelect.innerHTML = '';
        } else if (level === 'city') {
            districtSelect.innerHTML = '';
            areaSelect.innerHTML = '';
        } else if (level === 'district') {
            areaSelect.innerHTML = '';
        }

        var subList = data.districtList;
        if (subList) {
            var contentSub = new Option('--?????????--');
            var curlevel = subList[0].level;
            var curList = document.querySelector('#' + curlevel);
            curList.add(contentSub);
            for (var i = 0, l = subList.length; i < l; i++) {
                var name = subList[i].name;
                var levelSub = subList[i].level;
                var cityCode = subList[i].citycode;
                contentSub = new Option(name);
                contentSub.setAttribute("value", levelSub);
                contentSub.center = subList[i].center;
                contentSub.adcode = subList[i].adcode;
                curList.add(contentSub);
            }
        }

    }
    function search(obj) {
//        console.log("beigin:", obj);
        //??????????????????????????????
        for (var i = 0, l = polygons.length; i < l; i++) {
            polygons[i].setMap(null);
        }
        var option = obj[obj.options.selectedIndex];
//        console.log(option);
        var keyword = option.text; //?????????
        var adcode = option.adcode;
        district.setLevel(option.value); //???????????????
        district.setExtensions('all');
        console.log(option, keyword, adcode, obj.id);
        //???????????????
        //??????adcode????????????????????????????????????????????????
        district.search(adcode, function (status, result) {
            if (status === 'complete') {
                console.log("result", result)
                getData(result.districtList[0], obj.id);
            }
        });
    }
    function setCenter(obj) {
        map.setCenter(obj[obj.options.selectedIndex].center)
    }

</script>
</body>
</html>