<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家应用管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            // 恢复提示框显示
            resetTip();
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

            $("#submitAudit").click(
                function () {
                    alert("开始提交啦");
                    var form = $("#inputForm");
                    form.attr("action","${ctx}/xiaodian/merchant/merchantAppManage/online");
                    form.submit();
                }
            )

            $("#aldAppReg").click(
                function () {
                    $("#aldAppReg").attr("disabled","disabled");
                    var appId = $("#appId").val();
                    jQuery.ajax({
                        url: '${ctx}/xiaodian/merchant/merchantAppManage/aldAppReg',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            appId: appId,
                        },
                        success: function (result) {
                            if (result.code == 200) {
                                $("#aldId").val(result.ext);
                            } else {
                                $("#aldAppReg").removeAttr("disabled");
                                alertx(result.msg);
                            }
                        }
                    });
                }
            );
        })

        function config(id, e) {
            var name = e.options[e.options.selectedIndex].text;
            $("#" + id).val(name);
        }

        function  configMerchant(id,e){
            var name = e.options[e.options.selectedIndex].text;
            $("#" + id).val(name);

            var merchantId = e.options[e.options.selectedIndex].value;
            jQuery.ajax({
                url: '${ctx}/xiaodian/merchant/shop/fetchShops',
                type: 'POST',
                dataType: "json",
                data: {
                    merchantId: merchantId,
                    status: "2"
                },
                success: function (result) {
                    if (result.code == 200) {
                        var _data = result.data;
                       _data = $.parseJSON(_data);
                        if (null == _data || ( 0 == _data.length)) {
                            alert("商家没有店铺");
                            return;
                        }
//                        console.log(_data);
                        var shop = $("#shopId");
                        shop.empty();
                        $.each(_data, function (n, entity) {
                            shop.append($("<option>").attr({"value": entity.id}).text(entity.name));
                        });
                        shop.change();
                    }
                     else {
                        alert(result.msg);
                    }
                }
            });
        }

        // 发布测试环境 绑定多个测试用户，根据","分割
        function toOnlineBeta() {
            var testers = $("#testers").val().trim();
            window.location.href = "${ctx}/xiaodian/merchant/merchantAppManage/onlineBeta?id=${merchantAppManage.id}&testers="+testers;
        }

        function jisuAppReg() {
            var appId = $("[name='merchantAppManage.jisuAppId']").val();
            var url = "http://www.jisuapp.cn/index.php?r=pc/OpenPlugin/UsePlugin&plugin_name=haohanwangluo&app_id=" + appId;
            $.getJSON(url, function (data) {
                console.log("data", data);
                if (data.status == 0) {
                    alertx("操作成功  ," + data.data);
                } else {
                    alertx("操作失败  ," + data.data);
                }
            });
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/merchant/merchantAppManage/">商家应用列表</a></li>
    <li class="active"><a
            href="${ctx}/xiaodian/merchant/merchantAppManage/form?id=${merchantAppManage.id}">商家应用<shiro:hasPermission
            name="xiaodian:merchant:merchantAppManage:edit">${not empty merchantAppManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:merchant:merchantAppManage:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="merchantApp" action="${ctx}/xiaodian/merchant/merchantAppManage/save"
           method="post" class="form-horizontal">
    <form:hidden path="merchantAppManage.id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">应用：</label>
        <div class="controls">
                <%--<form:input path="appId" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
            <form:select id="appId" path="merchantAppManage.appId" class="input-xlarge " onchange="config('appName',this)">
                <%--<form:option value="${merchantAppManage.appId}" label="${merchantAppManage.appName}"/>--%>
                <form:options items="${authApps}" itemLabel="appName" itemValue="appId" htmlEscape="false"/>
            </form:select>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">应用名称：</label>
        <div class="controls">
            <form:input id="appName" path="merchantAppManage.appName" htmlEscape="false" maxlength="64"
                        class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">管理员微信ID：</label>
        <div class="controls">
            <form:input path="merchantAppManage.adminId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">管理员名称：</label>
        <div class="controls">
            <form:input path="merchantAppManage.adminName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商家：</label>
        <div class="controls">
                <%--<form:input path="merchantId" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
            <form:select path="merchantAppManage.merchantId" class="input-xlarge "
                         onchange="configMerchant('merchantName',this)">
                <form:option value="${merchantAppManage.merchantId}" label="${merchantAppManage.merchantName}"/>
                <form:options items="${merchants}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
            </form:select>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">商家名称：</label>
        <div class="controls">
            <form:input id="merchantName" path="merchantAppManage.merchantName" htmlEscape="false" maxlength="64"
                        class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">商家店铺：</label>
        <div class="controls">
            <form:select id="shopId" path="merchantAppExtInfo.shopId" class="input-xlarge " onchange="config('shopName',this)">
                <form:option value="${merchantAppExtInfo.shopId}" label="${merchantAppExtInfo.shopName}" />
                <form:options items="${shops}" itemLabel="name" itemValue="id" htmlEscape="false"/>
            </form:select>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">店铺名称：</label>
        <div class="controls">
            <form:input id="shopName" path="merchantAppExtInfo.shopName" htmlEscape="false" maxlength="64"
                        class="input-xlarge "/>
        </div>
    </div>


    <div class="control-group">
        <label class="control-label">店铺模板：</label>
        <div class="controls">
                <%--<form:input path="templateId" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
            <form:select path="merchantAppManage.templateId" class="input-xlarge "
                         onchange="config('templateName',this)">
                <form:options items="${shopTemplates}" itemLabel="templateName" itemValue="id" htmlEscape="false"/>
            </form:select>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">店铺模板名称：</label>
        <div class="controls">
            <form:input id="templateName" path="merchantAppManage.templateName" htmlEscape="false" maxlength="64"
                        class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">即速应用ID：</label>
        <div class="controls">
            <form:input path="merchantAppManage.jisuAppId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                <shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit">
                    &nbsp;&nbsp;&nbsp;<button type="button" id="jisuAppRegBtn" class="btn btn-primary" onclick="jisuAppReg()">应用即速插件</button>
                </shiro:hasPermission>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">商家账号：</label>
        <div class="controls">
            <form:input path="merchantAppExtInfo.partnerId" htmlEscape="false" maxlength="64" class="input-xlarge "
                       />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">阿拉丁AppKey：</label>
        <div class="controls">
            <form:input id="aldId" path="merchantAppExtInfo.aladId" htmlEscape="false" maxlength="64" class="input-xlarge"/>
            <c:if test="${empty merchantApp.merchantAppExtInfo.aladId}">
                <shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit">
                    &nbsp;&nbsp;&nbsp;<input type="button" value="阿拉丁应用注册" id="aldAppReg" class="btn btn-primary"/>
                </shiro:hasPermission>
            </c:if>
        </div>
    </div>


    <div class="control-group" style="display: none">
        <label class="control-label">扩展信息：</label>
        <div class="controls">
            <form:textarea path="merchantAppManage.ext" htmlEscape="false" maxlength="64" class="input-xlarge "
                           disabled="true"/>
        </div>
    </div>

    <shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit">
        <c:if test="${merchantAppManage.status eq 2}">
            <div class="control-group">
                <label class="control-label">服务类目：</label>
                <div class="controls">
                    <c:forEach items="${categoryList}" var="category" varStatus="vs">
                        <c:out value="${category.firstClass}"></c:out> ————
                        <c:out value="${category.secondClass}"></c:out>
                        <c:if test="${not empty category.thirdClass}">
                            ———— <c:out value="${category.thirdClass}"></c:out>
                        </c:if>
                        <form:checkbox path="categoryLists[${vs.index}].firstClass" value="${category.firstClass}" />
                        <form:hidden path="categoryLists[${vs.index}].secondClass" value="${category.secondClass}"/>
                        <form:hidden path="categoryLists[${vs.index}].thirdClass" value="${category.thirdClass}"/>
                        <form:hidden path="categoryLists[${vs.index}].firstId" value="${category.firstId}"/>
                        <form:hidden path="categoryLists[${vs.index}].secondId" value="${category.secondId}"/>
                        <form:hidden path="categoryLists[${vs.index}].thirdId" value="${category.thirdId}"/>
                        <br/>
                    </c:forEach>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">应用标签<br/>标签中间使用空格分隔：</label>
                <div class="controls">
                    <form:textarea path="tags" htmlEscape="false" maxlength="200" class="input-xlarge"/>
                </div>
            </div>

        </c:if>
    </shiro:hasPermission>

    <div class="control-group">
        <label class="control-label">发布版本号：</label>
        <div class="controls">
            <form:input path="merchantAppExtInfo.versionNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">发布版本说明：</label>
        <div class="controls">
            <form:textarea path="merchantAppExtInfo.versionDesc" htmlEscape="false" maxlength="100" class="input-xlarge"/>
        </div>
    </div>


    <div class="control-group appQrcode-hide" style="display: none">
        <label class="control-label">小程序码地址：</label>
        <div class="controls">
            <img src="${merchantAppExtInfo.qrcode}" style="width:150px;height:150px;">
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">扩展参数：</label>
        <div id="appExtInfo" class="controls">
           ${merchantAppExtJson}
        </div>
    </div>


    <div class="control-group">
        <label class="control-label">状态：</label>
        <div class="controls">
            <form:select path="merchantAppManage.status" class="input-xlarge ">
                <form:options items="${fns:getDictList('merchant_app_status')}" itemLabel="label"
                              itemValue="value" htmlEscape="false"/>
            </form:select>
            <shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit">
                <c:if test="${merchantAppManage.status eq 2}">
                    <input id="submitAudit" class="btn btn-primary" type="submit" value="提交微信审核"/>
                    <%--<a style="margin-left:5px;font-size: large" href="${ctx}/xiaodian/merchant/merchantAppManage/online?id=${merchantAppManage.id}">提交微信审核</a>--%>
                </c:if>

                <c:if test="${merchantAppManage.status eq 3}">
                    <a style="margin-left:5px;font-size: large"
                       href="${ctx}/xiaodian/merchant/merchantAppManage/checkStatus?id=${merchantAppManage.id}">审核状态查询</a>
                </c:if>
                <c:if test="${merchantAppManage.status eq 4}">
                    <a style="margin-left:5px;font-size: large"
                       href="${ctx}/xiaodian/merchant/merchantAppManage/publish?id=${merchantAppManage.id}">发布上线</a>
                </c:if>
            </shiro:hasPermission>
        </div>
    </div>

    <shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit">
        <c:if test="${merchantAppManage.status eq 1}">
        <div class="control-group">
            <label class="control-label">绑定多个测试用户：</label>
            <div class="controls">
                <input  id="testers"  type="text" /> (英文逗号分隔)
                <button type="button" id="onlineBetaBtn" onclick="toOnlineBeta()" class="btn btn-primary" >测试环境发布</button>
            </div>
        </div>
        </c:if>
    </shiro:hasPermission>

    <div id="choiceUpdateExt" class="control-group" >
        <label class="control-label">更新商家应用扩展信息：</label>
        <div class="controls">
            <form:select path="isUpdateExt" class="input-small " >
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">上线时间：</label>
        <div class="controls">
            <input name="merchantAppManage.onlineTime" type="text" readonly="readonly" maxlength="20"
                   class="input-medium Wdate "
                   value="<fmt:formatDate value="${merchantAppManage.onlineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="merchantAppManage.remarks" htmlEscape="false" rows="4" maxlength="255"
                           class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="xiaodian:merchant:merchantAppManage:edit"><input id="btnSubmit"
                                                                                    class="btn btn-primary"
                                                                                    type="submit"
                                                                                    value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>