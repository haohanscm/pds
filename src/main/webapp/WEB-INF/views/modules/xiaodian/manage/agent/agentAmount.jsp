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
            $("#diffDays").val("");
            $("#countType").select2("val","");
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
<form:form id="searchForm" modelAttribute="merchantTurnover" action="${ctx}/xiaodian/manage/agent/amount" method="post" class="breadcrumb form-search">
    <%--<input id="pageNo" name="pageNo" type="hidden" value="${orderPage.pageNo}"/>--%>
    <%--<input id="pageSize" name="pageSize" type="hidden" value="${orderPage.pageSize}"/>--%>
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
        <li><label>距今天天数：</label>
            <form:input path="diffDays" htmlEscape="false" maxlength="2" class="input-medium digits" placeholder="默认查询近30天"/>
        </li>
        <li><label>统计类型：</label>
            <form:select path="countType" class="input-medium">
                <form:option value="mon" label="按月统计"/>
                <form:option value="day" label="按日统计"/>
                <form:option value="year" label="按年统计"/>
            </form:select>
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
                <div class="item-cont">${totalOrderCount}</div>
            </li>
            <li class="item">
                <div class="item-title">交易总金额</div>
                <div class="item-cont">${totalAmount}</div>
            </li>
            <li class="item">
                <div class="item-title">预计分润金额</div>
                <div class="item-cont">${totalProfit}</div>
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
            <th>时间</th>
            <th>商户名称</th>
            <th>交易总笔数/笔</th>
            <th>交易总金额/元</th>
            <th>预计分润/元</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${amountList}" var="amount" varStatus="obj">
            <tr>
                <td>${obj.count}</td>
                <td>${amount.countDate}</td>
                <td>${amount.merchantName}</td>
                <td>${amount.orderCount}</td>
                <td>${amount.totalPayAmount}</td>
                <td>${amount.profit}</td>
            </tr>
        </c:forEach>
        </tbody>
        <c:if test="${empty amountList}">
            <p class="clearfix">没有数据!</p>
        </c:if>
    </table>
    <%--<div class="pagination">${orderPage}</div>--%>
</div>
</body>
</html>