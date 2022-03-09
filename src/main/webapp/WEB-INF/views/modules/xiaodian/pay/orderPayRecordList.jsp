<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>支付信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/xiaodian/pay/orderPayRecord/">支付信息列表</a></li>
    <shiro:hasPermission name="xiaodian:pay:orderPayRecord:edit">
        <li><a href="${ctx}/xiaodian/pay/orderPayRecord/form">支付信息添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="orderPayRecord" action="${ctx}/xiaodian/pay/orderPayRecord/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>订单号：</label>
            <form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>商户名称：</label>
            <form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>店铺ID：</label>
            <form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>支付流水号：</label>
            <form:input path="requestId" htmlEscape="false" maxlength="32" class="input-medium"/>
        </li>
        <li><label>商户编号：</label>
            <form:input path="partnerId" htmlEscape="false" maxlength="32" class="input-medium"/>
        </li>
        <li><label>订单日期：</label>
            <input name="beginOrderTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${orderPayRecord.beginOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
            <input name="endOrderTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${orderPayRecord.endOrderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>订单类型：</label>
            <form:select path="orderType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>支付方式：</label>
            <form:select path="payType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('pay_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>支付状态：</label>
            <form:select path="payStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单号</th>
        <th>商户名称</th>
        <th>订单类型</th>
        <th>支付流水号</th>
        <th>商户编号</th>
        <th>商品名称</th>
        <th>订单提交日期</th>
        <th>订单金额</th>
        <th>支付渠道</th>
        <th>支付方式</th>
        <th>支付状态</th>
            <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="orderPayRecord">
        <tr>
            <td><a href="${ctx}/xiaodian/pay/orderPayRecord/form?id=${orderPayRecord.id}">
                    ${orderPayRecord.orderId}
            </a></td>
            <td>
                    ${orderPayRecord.merchantName}
            </td>
            <td>
                    ${fns:getDictLabel(orderPayRecord.orderType, 'order_type', '')}
            </td>
            <td>
                    ${orderPayRecord.requestId}
            </td>
            <td>
                    ${orderPayRecord.partnerId}
            </td>
            <td>
                    ${orderPayRecord.goodsName}
            </td>
            <td>
                <fmt:formatDate value="${orderPayRecord.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${orderPayRecord.orderAmount}
            </td>
            <td>
                    ${fns:getDictLabel(orderPayRecord.payChannel, 'pay_channel', '')}
            </td>
            <td>
                    ${fns:getDictLabel(orderPayRecord.payType, 'pay_type', '')}
            </td>
            <td>
                    ${fns:getDictLabel(orderPayRecord.payStatus, 'pay_status', '')}
            </td>
                <td>
                    <ul>
                        <shiro:hasPermission name="xiaodian:pay:orderPayRecord:edit">
                        <li>
                            <a href="${ctx}/xiaodian/pay/orderPayRecord/form?id=${orderPayRecord.id}">修改</a>
                            <a href="${ctx}/xiaodian/pay/orderPayRecord/delete?id=${orderPayRecord.id}"
                               onclick="return confirmx('确认要删除该支付信息吗？', this.href)">删除</a>
                        </li>
                        <c:if test="${'0' eq orderPayRecord.payStatus}">
                            <li>
                                <a href="${ctx}/xiaodian/pay/orderPayRecord/api?id=${orderPayRecord.id}">支付请求</a>
                            </li>
                        </c:if>

                        <c:if test="${ '1' eq orderPayRecord.payStatus}">
                        <li>
                            <a href="${ctx}/xiaodian/pay/orderPayRecord/checkResult?id=${orderPayRecord.id}">支付查询</a>
                            </c:if>
                        </li>

                        <c:if test="${ '2' eq orderPayRecord.payStatus}">
                            <li>
                                <a href="${ctx}/xiaodian/pay/orderPayRecord/refundApply?id=${orderPayRecord.id}">退款申请</a>
                            </li>
                        </c:if>

                            <c:if test="${ '0' eq orderPayRecord.payStatus  && '即速应用' eq orderPayRecord.partnerAppName}">
                                <li>
                                    <a href="${ctx}/xiaodian/pay/orderPayRecord/jsHandPayNotify?id=${orderPayRecord.id}" onclick="return confirmx('确认手动支付吗？', this.href)">手动支付</a>
                                </li>
                            </c:if>


                    <%--<a href="${ctx}/xiaodian/pay/orderPayRecord/syncPayStatus?id=${orderPayRecord.id}">即速应用订单状态</a>--%>

                        <%--<c:if test="${(orderPayRecord.orderType ne '1' && orderPayRecord.orderType ne '0') && (orderPayRecord.payStatus eq '2' || orderPayRecord.payStatus eq 'PD')}">--%>
                            <%--<shiro:hasPermission name="xiaodian:pay:merchantPayInfo:edit">--%>
                                <%--<li>--%>
                                    <%--<a href="${ctx}/xiaodian/merchant/addMerchant/${orderPayRecord.id}">商家添加</a>--%>
                                <%--</li>--%>
                            <%--</shiro:hasPermission>--%>
                        <%--</c:if>--%>
                    </ul>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>