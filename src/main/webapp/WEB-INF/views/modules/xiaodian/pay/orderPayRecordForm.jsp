<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>支付信息管理</title>
    <meta name="decorator" content="default"/>
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

            $("#partnerId").change(function () {
                $("#merchantName").val(this.options[this.options.selectedIndex].text);
                var merchantId = $("#partnerId").find("option:selected").attr("merchantId");
                // console.log("merchantId",merchantId);
                jQuery.ajax({
                    url: '${ctx}/xiaodian/merchant/shop/fetchShops',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        merchantId: merchantId,
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            var _data = result.data;
                            _data = $.parseJSON(_data);
                            if (null == _data || ( 0 == _data.length)) {
                                alert("商家没有店铺");
                                return;
                            }
                            // console.log(_data);
                            var shop = $("#shopId");
                            shop.empty();
                            $.each(_data, function (n, entity) {
                                shop.append($("<option>").attr({"value": entity.id}).text(entity.name));
                            });
                            shop.change();
                        }
                    }
                });

            });

            $("#shopId").change(function () {
                var shopName = this.options[this.options.selectedIndex].text;
//                console.log(shopName);
                $("#shopName").val(shopName);
            });


            function randomString(len) {
                len = len || 32;
                var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
                /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
                var maxPos = $chars.length;
                var pwd = '';
                for (i = 0; i < len; i++) {
                    pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
                }
                return pwd;
            }

            function ajaxGetId(id) {
                jQuery.ajax({
                    url: '${ctx}/xiaodian/order/orderAction/genOrderId',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        opId: id,
                    },
                    success: function (result) {
                        if (result.code == 200) {
//                            console.log(result.data);
                            result.data.toString();
                        }
                    }
                });
            }

            function genId(id) {
                return id +
                ${fns:getDate('yyyyMMddHHmmss')}.
                concat(randomString(3));

            }

            $("#genOrderId").click(function () {
                //订单号生成规则 类目(0~3)+日期12(yyyymmdd hh:ss) +随机字符串(3) 合计18位
                <%--${}--%>
                var oid = genId("000");
//                alert(oid);
                $("#orderId").val(oid);

            });

            $("#genPayId").click(function () {

                var payType = $("#payType option:selected").val();
                $("#requestId").val(genId(payType));
            });




        });
    </script>

</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/pay/orderPayRecord/">支付信息列表</a></li>
    <li class="active"><a
            href="${ctx}/xiaodian/pay/orderPayRecord/form?id=${orderPayRecord.id}">支付信息<shiro:hasPermission
            name="xiaodian:pay:orderPayRecord:edit">${not empty orderPayRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:pay:orderPayRecord:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="orderPayRecord" action="${ctx}/xiaodian/pay/orderPayRecord/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>

    <div class="control-group">
        <label class="control-label">商家账户：</label>
        <div class="controls">
                <%--<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
            <form:select id="partnerId" path="partnerId" class="input-medium">
                <form:option value="" label=""/>
                <c:forEach items="${merchantPayInfoList}" var="payInfo">
                    <form:option  value="${payInfo.partnerId}" label="${payInfo.mercAbbr}" merchantId="${payInfo.merchantId}"/>
                </c:forEach>
                <%--<form:options items="${merchantPayInfoList}" itemValue="partnerId" itemLabel="mercNm" merchantId="merchantId"  htmlEscape="false"/>--%>
                <%--<form:options items="${merchantPayInfoList}" itemValue="partnerId" itemLabel="mercNm" merchantId=""--%>
                              <%--htmlEscape="false"/>--%>
            </form:select>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">商户名称：</label>
        <div class="controls">
            <form:input id="merchantName" path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">店铺:</label>
        <div class="controls">
            <form:select id="shopId" path="shopId" class="input-medium">
                <form:option value="${orderPayRecord.shopId}" label="${orderPayRecord.shopName}"/>
            </form:select>
                <%--<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">店铺名称：</label>
        <div class="controls">
            <form:input path="shopName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单号：</label>
        <div class="controls">
            <form:input path="orderId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            <input type="button" value="生成订单号" id="genOrderId"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单类型：</label>
        <div class="controls">
            <form:select path="orderType" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户请求IP：</label>
        <div class="controls">
            <form:input path="clientIp" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付渠道：</label>
        <div class="controls">
            <form:select path="payChannel" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('pay_channel')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付方式：</label>
        <div class="controls">
            <form:select path="payType" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('pay_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付流水号：</label>
        <div class="controls">
            <form:input path="requestId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
            <input type="button" value="生成支付流水号" id="genPayId"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">第三方订单编号：</label>
        <div class="controls">
            <form:input path="transId" htmlEscape="false" maxlength="64" class="input-xlarge " disabled="true"/>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">商户编号：</label>
        <div class="controls">
            <form:input path="partnerId" htmlEscape="false" maxlength="32" class="input-xlarge " disabled="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商品名称：</label>
        <div class="controls">
            <form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单提交日期：</label>
        <div class="controls">
            <input name="orderTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${orderPayRecord.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单金额,单位(元)：</label>
        <div class="controls">
            <form:input path="orderAmount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">授权码：</label>
        <div class="controls">
            <form:input path="authCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">设备编号：</label>
        <div class="controls">
            <form:input path="deviceId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">渠道商回调地址：</label>
        <div class="controls">
            <form:input path="partnerNotifyUrl" htmlEscape="false" maxlength="64" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否支持信用卡：</label>
        <div class="controls">
            <form:select path="limitPay" class="input-xlarge ">
            <c:if test="${empty orderPayRecord.limitPay}">
                <form:option value="1" label="是"/>
            </c:if>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">返回码：</label>
        <div class="controls">
            <form:input path="respCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">返回码信息描述：</label>
        <div class="controls">
            <form:textarea path="respDesc" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">返回时间：</label>
        <div class="controls">
            <input name="respTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${orderPayRecord.respTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户订单二维码：</label>
        <div class="controls">
            <form:input path="orderQrcode" htmlEscape="false" maxlength="500" class="input-xlarge "/>
        </div>
    </div>
        <c:if test="${(orderPayRecord.payType eq '01' || orderPayRecord.payType eq '05') && ( not empty orderPayRecord.orderQrcode) }" >
            <div class="control-group">
            <label class="control-label">商户订单二维码：</label>
            <div id="qrcode" class="controls">
                <script type="text/javascript">
                    $("#qrcode").qrcode(
                        {
                            render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
                            text : "${orderPayRecord.orderQrcode}",    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
                            width : "200",            // //二维码的宽度
                            height : "200",              //二维码的高度
                            background : "#ffffff",       //二维码的后景色
                            foreground : "#000000",        //二维码的前景色
//                            src: '../img/pm.jpg'             //二维码中间的图片
                        }
                    );
                </script>
            </div>
            </div>
        </c:if>
    <div class="control-group">
        <label class="control-label">用户标示openid：</label>
        <div class="controls">
            <form:input path="openid" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">微信公众帐号ID：</label>
        <div class="controls">
            <form:input path="appid" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付信息：</label>
        <div class="controls">
            <form:textarea path="payInfo" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">预支付订单号：</label>
        <div class="controls">
            <form:input path="prepayId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <c:if test="${(orderPayRecord.payType eq '03' || orderPayRecord.payType eq '07') && ( not empty orderPayRecord.prepayId) }" >
<div class="control-group">
    <label class="control-label">公众号小程序支付调用：</label>
    <div class="controls">
        http://localhost:8080/service-sys/f/xiaodian/api/bank/mppay?orderId=${orderPayRecord.orderId}
    </div>
</div>
     </c:if>
    <div class="control-group">
        <label class="control-label">签名：</label>
        <div class="controls">
            <form:input path="paySign" htmlEscape="false" maxlength="500" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">时间戳：</label>
        <div class="controls">
            <form:input path="timeStamp" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">随机字符串：</label>
        <div class="controls">
            <form:input path="noncestr" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付状态：</label>
        <div class="controls">
            <form:select path="payStatus" class="input-xlarge ">
                <c:if test="${empty orderPayRecord.payStatus}">
                <form:option value="0" label="未付款"/>
                </c:if>
                <form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">费率：</label>
        <div class="controls">
            <form:input path="rate" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">服务费：</label>
        <div class="controls">
            <form:input path="fee" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户UID：</label>
        <div class="controls">
            ${orderPayParamsExt.uid}
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户姓名：</label>
        <div class="controls">
                ${orderPayParamsExt.userName}
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户电话：</label>
        <div class="controls">
                ${orderPayParamsExt.telephone}
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户地址：</label>
        <div class="controls">
                ${orderPayParamsExt.address}
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户备注：</label>
        <div class="controls">
                ${orderPayParamsExt.remark}
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">商品属性：</label>
        <div class="controls">
            <c:if test="${not empty attrMap}">
                <c:forEach items="${attrMap}" var="attr">
                   <c:out value="${attr.key}" /> ：  <c:out value="${attr.value}" /> <br/>
                </c:forEach>
            </c:if>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="xiaodian:pay:orderPayRecord:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                            type="submit"
                                                                            value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>

</body>
</html>