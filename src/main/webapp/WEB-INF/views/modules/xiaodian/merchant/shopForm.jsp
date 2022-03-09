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

    <title>店铺管理</title>
    <%--<meta name="decorator" content="default"/>--%>
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
            $("#btnSubmit").click(function () {
                //设置地址
                $("#addressVal").val($("#address").html());
                //设置经纬度
                var lnglatVal ;
                var longitude = $("#mapLongitude").val();
                var latitude = $("#mapLatitude").val();
                // 是否手动输入经纬度
                if(""!=longitude && ""!=latitude){
                    lnglatVal = longitude+","+latitude;
                }else{
                    lnglatVal = $("#lnglat").html();
                }

                if (lnglatVal) {
                    var v1 = lnglatVal.split(",");
                    $("#mapLongitude").val(v1[0]);
                    $("#mapLatitude").val(v1[1]);
                    //设置店铺位置信息
                    $("#provinceVal").val(fetchSelectVal("province"));
                    $("#cityVal").val(fetchSelectVal("city"));
                    $("#districtVal").val(fetchSelectVal("district"));
                    $("#streetVal").val(fetchSelectVal("street"));
                    $("#lnglatVal").val(lnglatVal);
                }
            })
            // 店铺交易类型处理
            var tradeType = "${shopInfo.shop.tradeType}";
            if(tradeType){
                tradeType = tradeType.split(",");
                for(var i =0, len = tradeType.length;i<len;i++){
                    $(":checkbox[value='"+tradeType[i]+"']").attr('checked','true');
                }
            }
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
    <li><a href="${ctx}/xiaodian/merchant/shop/">店铺列表</a></li>
    <li class="active"><a href="${ctx}/xiaodian/merchant/shop/form?id=${shop.id}">店铺<shiro:hasPermission
            name="xiaodian:merchant:shop:edit">${not empty shop.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:merchant:shop:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="shopInfo" action="${ctx}/xiaodian/merchant/shop/save" method="post"
           class="form-horizontal">
    <form:hidden path="shop.id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">shopID：</label>
        <div class="controls">${shop.id}</div>
    </div>
    <div class="control-group">
        <label class="control-label">商家名称：</label>
        <div class="controls">
            <form:select id="merchantId" path="shop.merchantId" class="input-medium required">
                <form:option value="" label=""/>
                <form:options items="${merchantList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">即速应用ID：</label>
        <div class="controls">
            <form:input path="shop.code" htmlEscape="false" maxlength="50" class="input-medium "/>
            &nbsp;&nbsp;&nbsp;<span>设置店铺和即速应用关联</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否更新即速商品：</label>
        <div class="controls">
            <form:select path="shop.isUpdateJisu" class="input-xshort ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            &nbsp;&nbsp;&nbsp;<span>设置店铺零售商品编辑后自动同步至即速云,启用时还需打开即速云应用的自动刷新开关</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺名称：</label>
        <div class="controls">
            <form:input path="shop.name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺服务模式：</label>
        <div class="controls">
            <form:select path="shop.serviceType" class="input-xshort required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('shop_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            &nbsp;&nbsp;&nbsp;<span>区别独立店铺和连锁店铺</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺类型：</label>
        <div class="controls">
            <form:select path="shop.shopType" class="input-xshort ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            &nbsp;&nbsp;&nbsp;<span>用于智能终端区别获取零售/餐饮商品</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺级别：</label>
        <div class="controls">
            <form:select path="shop.shopLevel" class="input-medium ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('shop_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            &nbsp;&nbsp;&nbsp;<span>区别连锁店铺的总子店和用于采购配送的店铺</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺区域：</label>
        <div class="controls">
            <sys:treeselect id="area" name="shop.area.id" value="${shop.area.id}" labelName="shop.area.name"
                            labelValue="${shop.area.name}"
                            title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"
                            notAllowSelectParent="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">行业：</label>
        <div class="controls">
            <form:input path="shop.industry" htmlEscape="false" maxlength="100" class="input-xlarge "/>
            &nbsp;&nbsp;&nbsp;<span>目前仅展示</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">配送距离：</label>
        <div class="controls">
            <form:input path="shop.deliverDistence" htmlEscape="false" maxlength="64" class="input-large "/>
            &nbsp;&nbsp;&nbsp;<span>用于设置送货上门时,店铺覆盖范围</span>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">店铺地址：</label>
        <div class="controls">
            <form:input id="addressVal" path="shop.address" htmlEscape="false" maxlength="100"
                        class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺负责人：</label>
        <div class="controls">
            <form:input path="shop.manager" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺电话：</label>
        <div class="controls">
            <form:input path="shop.telephone" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺位置：</label>
        <div class="controls">
            <div id="tip" class="form-item">
                <div class="cont" style="margin-left: -10px;">
                    <form:hidden id="provinceVal" path="shopLocation.province"/>
                    <form:hidden id="cityVal" path="shopLocation.city"/>
                    <form:hidden id="districtVal" path="shopLocation.district"/>
                    省 <select id='province' style="width:100px" onchange="search(this)">
                    <option selected="true">${shopLocation.province}</option>
                </select>
                    市 <select id='city' style="width:100px" onchange='search(this)'>
                    <option selected="true">${shopLocation.city}</option>
                </select>
                    区 <select id='district' style="width:100px" onchange='search(this)'>
                    <option selected="true">${shopLocation.district}</option>
                </select>
                </div>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">街道：</label>
        <div class="controls">
            <form:hidden id="streetVal" path="shopLocation.street"/>
            <select id="street" style="width:200px" onchange='setCenter(this)'>
                <option>${shopLocation.street}</option>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">地图选址：</label>
        <div class="controls">
            <div id="mapContainer"></div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">模式：</label>
        <div class="controls">
            <input type='radio' name='mode' value='dragMap' checked>拖拽地图模式</input>
            </br>
            <input type='radio' name='mode' value='dragMarker'>拖拽Marker模式</input>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">选点：</label>
        <div class="controls">
            <button id='start'>开始选点</button>
            <button id='stop'>关闭选点</button>
        </div>
    </div>
    <form:hidden id="lnglatVal" path="shopLocation.lnglat"/>
    <div class="control-group">
        <label class="control-label">经纬度：</label>
        <div class="controls">
            <div id="lnglat"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">地址：</label>
        <div class="controls">
            <div id="address"></div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">最近的路口：</label>
        <div class="controls">
            <div id='nearestJunction'></div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">最近的路：</label>
        <div class="controls">
            <div id="nearestRoad"></div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">最近的POI：</label>
        <div class="controls">
            <div id="nearestPOI"></div>
        </div>
    </div>


    <div class="control-group" >
        <label class="control-label">经度：</label>
        <div class="controls">
            <form:input id="mapLongitude" path="shop.mapLongitude" htmlEscape="false" maxlength="64" class="input-medium "/>
            &nbsp;&nbsp;&nbsp;<span>当此处经纬度有值时,以填入值为准,若要使用地图选择值,需清空此处经纬度</span>
        </div>
    </div>
    <div class="control-group" >
        <label class="control-label">纬度：</label>
        <div class="controls">
            <form:input id="mapLatitude" path="shop.mapLatitude" htmlEscape="false" maxlength="64" class="input-medium "/>
        </div>
    </div>


    <div class="control-group">
        <label class="control-label">营业时间：</label>
        <div class="controls">
            <form:textarea path="shop.onlineTime" htmlEscape="false" maxlength="100" rows="3" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺经营范围：</label>
        <div class="controls">
            <form:textarea path="shop.shopService" htmlEscape="false" maxlength="200" rows="3" class="input-xlarge required" placeholder="必填项"/>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">店铺模板ID：</label>
        <div class="controls">
            <form:input path="shop.templateId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">店铺简介：</label>
        <div class="controls">
            <form:textarea path="shop.shopDesc" htmlEscape="false" maxlength="500" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">聚合平台类型：</label>
        <div class="controls">
            <form:select path="shop.aggregationType" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('aggregation_shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            &nbsp;&nbsp;&nbsp;<span>用于区别店铺所属的聚合平台</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺认证类型：</label>
        <div class="controls">
            <form:select path="shop.authType" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('shop_auth_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            &nbsp;&nbsp;&nbsp;<span>用于标记认证状态(商家电子名片)</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺交易类型：</label>
        <div class="controls">
            <form:checkboxes id="tradeType" path="shop.tradeType" items="${fns:getDictList('shop_trade_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
            &nbsp;&nbsp;&nbsp;<span>用于标记店铺支持交易类型(商家电子名片)</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺分类：</label>
        <div class="controls" id="choiceCategory">
            <sys:treeselect id="shopCategory" name="shop.shopCategory" value="${shop.shopCategory}" labelName="categoryName"
                            labelValue="${shop.shopCategoryName}"
                            title="数据分类" url="/xiaodian/merchant/shopCategory/treeData?limitType= " cssClass="" allowClear="true"
                            notAllowSelectParent="true" />
            &nbsp;&nbsp;&nbsp;<span>用于聚合平台,划分店铺分类类型</span>
        </div>
    </div>
<%--    <div class="control-group" style="display: none">--%>
<%--        <label class="control-label">店铺照片：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="shop.photoGroupNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div class="upload-box" id="shopLogos_uploadbox">--%>
<%--        <p class="upload-tip">店铺Logo:</p>--%>
<%--        <form:hidden path="shopLogos.groupName" value="店铺Logo" />--%>
<%--        <form:hidden id="shopLogos_groupNum" path="shop.shopLogo" value="${shop.id}-10"/>--%>
<%--        <div class="image-box clear" id="shopLogos_imagebox">--%>
<%--            <c:forEach items="${shopLogos.photoManageList}" var="photoManage" varStatus="vs">--%>
<%--                <section class="image-section">--%>
<%--                    <input id="shopLogos_photoManage_${vs.index}" name="shopLogos.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>--%>
<%--                    <form:hidden id="shopLogos_photoGallery_${vs.index}" path="shopLogos.photoManageList[${vs.index}].photoGallery.id"/>--%>
<%--                    <form:hidden path="shopLogos.photoManageList[${vs.index}].delFlag" value="0" />--%>
<%--                    <div class="image-shade"></div>--%>
<%--                    <div class="image-delete" id="${photoManage.id}"></div>--%>
<%--                    <div class="image-zoom"></div>--%>
<%--                    <img class="image-show"--%>
<%--                         src="${photoManage.picUrl}"/>--%>
<%--                    <input--%>
<%--                            class="file" name="file[]"--%>
<%--                            value="${photoManage.picUrl}"--%>
<%--                            type="hidden"/>--%>
<%--                    <div align="center" id="shopLogo_index">第 ${vs.count} 张</div>--%>
<%--                </section>--%>
<%--            </c:forEach>--%>
<%--            <c:if test="${fn:length(shopLogo.photoManageList) lt 2 }">--%>
<%--                <section class="upload-section">--%>
<%--                    <div class="upload-btn"></div>--%>
<%--                    <input type="file" name="file" class="upload-input" value="" id="shopLogos"/>--%>
<%--                </section>--%>
<%--            </c:if>--%>
<%--        </div>--%>
<%--    </div>--%>


<%--    <div class="upload-box" id="shopPayCodes_uploadbox">--%>
<%--        <p class="upload-tip">店铺收款二维码:</p>--%>
<%--        <form:hidden path="shopPayCodes.groupName" value="店铺收款二维码" />--%>
<%--        <form:hidden id="shopPayCodes_groupNum" path="shop.payCode" value="${shop.id}-11"/>--%>
<%--        <div class="image-box clear" id="shopPayCodes_imagebox">--%>
<%--            <c:forEach items="${shopPayCodes.photoManageList}" var="photoManage" varStatus="vs">--%>
<%--                <section class="image-section">--%>
<%--                    <input id="shopPayCodes_photoManage_${vs.index}" name="shopPayCodes.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>--%>
<%--                    <form:hidden id="shopPayCodes_photoGallery_${vs.index}" path="shopPayCodes.photoManageList[${vs.index}].photoGallery.id"/>--%>
<%--                    <form:hidden path="shopPayCodes.photoManageList[${vs.index}].delFlag" value="0" />--%>
<%--                    <div class="image-shade"></div>--%>
<%--                    <div class="image-delete" id="${photoManage.id}"></div>--%>
<%--                    <div class="image-zoom"></div>--%>
<%--                    <img class="image-show"--%>
<%--                         src="${photoManage.picUrl}"/>--%>
<%--                    <input--%>
<%--                            class="file" name="file[]"--%>
<%--                            value="${photoManage.picUrl}"--%>
<%--                            type="hidden"/>--%>
<%--                    <div align="center" id="shopLogo_index">第 ${vs.count} 张</div>--%>
<%--                </section>--%>
<%--            </c:forEach>--%>
<%--            <c:if test="${fn:length(shopPayCodes.photoManageList) lt 2 }">--%>
<%--                <section class="upload-section">--%>
<%--                    <div class="upload-btn"></div>--%>
<%--                    <input type="file" name="file" class="upload-input" value="" id="shopPayCodes"/>--%>
<%--                </section>--%>
<%--            </c:if>--%>
<%--        </div>--%>
<%--    </div>--%>


<%--    <div class="upload-box" id="shopQrCodes_uploadbox">--%>
<%--        <p class="upload-tip">店铺二维码:</p>--%>
<%--        <form:hidden path="shopQrCodes.groupName" value="店铺二维码" />--%>
<%--        <form:hidden id="shopQrCodes_groupNum" path="shop.qrcode" value="${shop.id}-12"/>--%>
<%--        <div class="image-box clear" id="shopQrCodes_imagebox">--%>
<%--            <c:forEach items="${shopQrCodes.photoManageList}" var="photoManage" varStatus="vs">--%>
<%--                <section class="image-section">--%>
<%--                    <input id="shopQrCodes_photoManage_${vs.index}" name="shopQrCodes.photoManageList[${vs.index}].id" value="${photoManage.id}" type="hidden"/>--%>
<%--                    <form:hidden id="shopQrCodes_photoGallery_${vs.index}" path="shopQrCodes.photoManageList[${vs.index}].photoGallery.id"/>--%>
<%--                    <form:hidden path="shopQrCodes.photoManageList[${vs.index}].delFlag" value="0" />--%>
<%--                    <div class="image-shade"></div>--%>
<%--                    <div class="image-delete" id="${photoManage.id}"></div>--%>
<%--                    <div class="image-zoom"></div>--%>
<%--                    <img class="image-show"--%>
<%--                         src="${photoManage.picUrl}"/>--%>
<%--                    <input--%>
<%--                            class="file" name="file[]"--%>
<%--                            value="${photoManage.picUrl}"--%>
<%--                            type="hidden"/>--%>
<%--                    <div align="center" id="shopLogo_index">第 ${vs.count} 张</div>--%>
<%--                </section>--%>
<%--            </c:forEach>--%>
<%--            <c:if test="${fn:length(shopQrCodes.photoManageList) lt 2 }">--%>
<%--                <section class="upload-section">--%>
<%--                    <div class="upload-btn"></div>--%>
<%--                    <input type="file" name="file" class="upload-input" value="" id="shopQrCodes"/>--%>
<%--                </section>--%>
<%--            </c:if>--%>
<%--        </div>--%>
<%--    </div>--%>
    


    <div class="control-group">
        <label class="control-label">状态：</label>
        <div class="controls">
            <form:select path="shop.status" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('status_merchant')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="shop.remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="xiaodian:merchant:shop:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                       type="submit"
                                                                       value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
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
                    url: "${ctx}/xiaodian/common/photoGallery/uploadPhoto/${shop.merchantId}-10/"+type, //上传的服务器地址
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
//        106.26164,29.279368 重庆江津区
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

    //行政区划查询
    var opts = {
        subdistrict: 1,   //返回下一级行政区
        showbiz: false  //最后一级返回街道信息
    };
    district = new AMap.DistrictSearch(opts);//注意：需要使用插件同步下发功能才能这样直接使用
    district.search('中国', function (status, result) {
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
            map.setFitView();//地图自适应
        }


        //清空下一级别的下拉列表
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
            var contentSub = new Option('--请选择--');
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
        //清除地图上所有覆盖物
        for (var i = 0, l = polygons.length; i < l; i++) {
            polygons[i].setMap(null);
        }
        var option = obj[obj.options.selectedIndex];
//        console.log(option);
        var keyword = option.text; //关键字
        var adcode = option.adcode;
        district.setLevel(option.value); //行政区级别
        district.setExtensions('all');
        console.log(option, keyword, adcode, obj.id);
        //行政区查询
        //按照adcode进行查询可以保证数据返回的唯一性
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