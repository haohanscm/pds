<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>渠道费率管理</title>
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
            $("#merchantId").change(function () {
                $("#partnerId").val(this.options[this.options.selectedIndex].value);
            })

        });

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/pay/merchantPayInfo/">商家账户列表</a></li>
    <shiro:hasPermission name="xiaodian:pay:merchantPayInfo:edit">
        <li><a href="${ctx}/xiaodian/pay/merchantPayInfo/form?id=${merchantPayInfo.id}">商家账户查看</a></li>
    </shiro:hasPermission>
    <li class="active"><a
            href="${ctx}/xiaodian/pay/merchantPayInfo/payConfig?id=${merchantPayInfo.id}">渠道费率<shiro:hasPermission
            name="xiaodian:pay:channelRateManage:edit">${not empty merchantPayInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:pay:channelRateManage:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="merchantPayConfig" action="${ctx}/xiaodian/pay/merchantPayInfo/savePayConfig"
           method="post" class="form-horizontal">
    <form:hidden path="merchantPayInfo.id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">商家名称：</label>
        <div class="controls">
            <label>${merchantPayInfo.mercNm}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户编号：</label>
        <div class="controls">
            <label>${merchantPayInfo.partnerId}</label>
        </div>
    </div>
    <c:forEach items="${merchantPayConfig.channelRateManageList}" var="rateManage" varStatus="vs">
        <form:hidden path="channelRateManageList[${vs.index}].id"/>
        <form:hidden path="channelRateManageList[${vs.index}].channel" value="${rateManage.channel}"/>
        <form:hidden path="channelRateManageList[${vs.index}].merchantId" value="${rateManage.merchantId}"/>
        <form:hidden path="channelRateManageList[${vs.index}].payInfoId" value="${rateManage.payInfoId}"/>
        <div class="control-group">
            <label class="control-label" style="font-size: large">${fns:getDictLabel(rateManage.channel, 'pay_channel', '')}费率：</label>
            <div class="controls">
                <form:input path="channelRateManageList[${vs.index}].rate" htmlEscape="false" maxlength="64"
                            class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${fns:getDictLabel(rateManage.channel, 'pay_channel', '')}类目：</label>
            <div class="controls">
                <form:input path="channelRateManageList[${vs.index}].category" htmlEscape="false" maxlength="64"
                            class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${fns:getDictLabel(rateManage.channel, 'pay_channel', '')}状态：</label>
            <div class="controls">
                <%--<label>${fns:getDictLabel(rateManage.status, 'bank_reg_status', '')}</label>--%>

                <form:select path="channelRateManageList[${vs.index}].status" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('bank_reg_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>

            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${fns:getDictLabel(rateManage.channel, 'pay_channel', '')}渠道ID：</label>
            <div class="controls">
                <form:input path="channelRateManageList[${vs.index}].custId" htmlEscape="false" maxlength="64"
                            class="input-xlarge "/>
            </div>
        </div>
        <div class="control-group" style="display: none">
            <label class="control-label">${fns:getDictLabel(rateManage.channel, 'pay_channel', '')}备注信息：</label>
            <div class="controls">
                <form:textarea path="channelRateManageList[${vs.index}].remarks" htmlEscape="false" rows="4"
                               maxlength="255" class="input-xxlarge "/>
            </div>
        </div>

    </c:forEach>

    <div class="form-actions">
        <shiro:hasPermission name="xiaodian:pay:channelRateManage:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                               type="submit"
                                                                               value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>