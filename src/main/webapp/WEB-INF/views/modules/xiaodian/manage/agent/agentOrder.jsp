<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>商家订单</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            resetTip();
        });

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function toReset() {
            $("#merchantId").select2("val","");
            $("#merchantName").val("");
            $("#orderId").val("");
            $("#payType").select2("val","");
            $("#payStatus").select2("val","");
            $("#diffDays").val("");
            $("[name='beginPayDate']").val("");
            $("[name='endPayDate']").val("");
        }
    </script>
    <style type="text/css">
        .well{
            margin-bottom: 10px;
            background-color:#fff;
        }
        body {
            background-color:#f3f3f4;
        }
        .itembox>ul{

            display: flex;
            justify-content: space-between;
            margin: 0 auto;
        }
        .itembox>ul>li{
            flex: 1;
            border: 1px solid #f5f5f5;
            list-style: none;
        }
        .itembox>ul>li+li{
            margin-left: 20px;
        }
        .item-title{
            height: 38px;
            background: #00bfff;
            font-size: 14px;
            color: #fff;
            line-height: 38px;
            text-align: center;
        }
        .item-cont{
            height: 60px;
            font-size: 20px;
            color: #333;
            text-align: center;
            line-height: 60px;
            border: 1px solid #e7e7eb;
        }
    </style>
</head>
<body>
<form:form id="searchForm" modelAttribute="merchantTurnover" action="${ctx}/xiaodian/manage/agent/orderQuery" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${orderPage.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${orderPage.pageSize}"/>
    <ul class="ul-form">
        <li><label>商户：</label>
            <form:select path="merchantId" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${merchantList}" itemLabel="name" itemValue="agentSn" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>商户名称：</label>
            <form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
        </li>
        <li><label>订单ID：</label>
            <form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
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
        <li><label>距今天天数：</label>
            <form:input path="diffDays" htmlEscape="false" maxlength="2" class="input-medium digits"  placeholder="默认查询今天"/>
        </li>
        <li><label>订单日期：</label>
            <input name="beginPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${merchantTurnover.beginPayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
            <input name="endPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${merchantTurnover.endPayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置筛选条件" onclick="toReset()"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div class="well">
    <div class="itembox">
        <h4 style="margin-bottom: 10px;">整体看板</h4>
        <ul>
            <li class="item">
                <div class="item-title">交易总净笔数</div>
                <div class="item-cont">${merchantPay.orderCount}</div>
            </li>
            <li class="item">
                <div class="item-title">退款总笔数</div>
                <div class="item-cont">${merchantPay.refundCount}</div>
            </li>
            <li class="item">
                <div class="item-title">退款总金额</div>
                <div class="item-cont">${merchantPay.refundAmount}</div>
            </li>
            <li class="item">
                <div class="item-title">商家优惠</div>
                <div class="item-cont">${merchantPay.discountAmount}</div>
            </li>
            <li class="item">
                <div class="item-title">商家收入</div>
                <div class="item-cont">${merchantPay.totalPayAmount}</div>
            </li>
        </ul>
    </div>
</div>
<div class="well">
    <h4 style="margin-bottom: 10px;">订单列表</h4>
    <%--<table class="table table-striped">--%>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>序号</th>
            <th>订单号</th>
            <th>商户名称</th>
            <th>店铺名称</th>
            <th>订单金额</th>
            <th>订单提交日期</th>
            <th>支付渠道</th>
            <th>支付方式</th>
            <th>支付状态</th>
            <%--<th>手续费</th>--%>
            <%--<th>费率</th>--%>
            <%--<th>操作</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderPage.list}" var="order" varStatus="obj">
            <tr>
                <td>${obj.count}</td>
                <td>${order.orderId}</td>
                <td>${order.merchantName}</td>
                <td>${order.shopName}</td>
                <td>${order.orderAmount}</td>
                <td><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${fns:getDictLabel(order.payChannel, 'pay_channel', '')}</td>
                <td>${fns:getDictLabel(order.payType, 'pay_type', '')}</td>
                <td>${fns:getDictLabel(order.payStatus, 'pay_status', '')}</td>
                <%--<td>${order.fee}</td>--%>
                <%--<td>${order.rate}</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination">${orderPage}</div>
</div>
</body>
</html>