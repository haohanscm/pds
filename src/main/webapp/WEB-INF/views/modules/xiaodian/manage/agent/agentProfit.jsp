<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>营业额</title>
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
            $("#countType").select2("val","");
            $("#diffMons").val("");
            $("[name='beginPayDate']").val("");
            $("[name='endPayDate']").val("");
        }
    </script>
    <script src="https://cdn.bootcss.com/echarts/4.1.0.rc2/echarts.min.js"></script>

    <style type="text/css">
        .well{
            background-color:#fff;
            padding: 30px;
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
            width: 24%;
            border: 1px solid #f5f5f5;
            list-style: none;
        }
        .item-title{
            height: 38px;
            background: #f4f5fa;
            font-size: 14px;
            color: #0e0e0e;
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
        .well>.container{
            font-size: 16px;
            font-weight: 700;
            margin-left: 30px;
        }
        .btn-group {
            margin: 15px 0;
        }
    </style>
</head>
<body>
<div class="well">
    <h4 style="margin-bottom:20px;">交易数据</h4>
    <div class="itembox">
        <ul>
            <li class="item">
                <div class="item-title" style="background:#21bfff;color:#fff;">商户数</div>
                <div class="item-cont">${fn:length(merchantList)}</div>
            </li>
            <li class="item">
                <div class="item-title" style="background:#f5a623;color:#fff;">交易总笔数</div>
                <div class="item-cont">${allCount.orderCount}</div>
            </li>
            <li class="item">
                <div class="item-title" style="background:#50e3c2;color:#fff;">交易总金额</div>
                <div class="item-cont">${allCount.totalPayAmount}</div>
            </li>
            <li class="item">
                <div class="item-title" style="background:#fc5555;color:#fff;">分润总金额</div>
                <div class="item-cont">${allCount.profit}</div>
            </li>
        </ul>
    </div>
</div>
<form:form id="searchForm" modelAttribute="merchantTurnover" action="${ctx}/xiaodian/manage/agent/profit" method="post" class="breadcrumb form-search">
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
        <li><label>距今天月数：</label>
            <form:input path="diffMons" htmlEscape="false" maxlength="2" class="input-medium digits" placeholder="默认查询当月"/>
        </li>
        <li><label>统计类型：</label>
            <form:select path="countType" class="input-medium">
                <form:option value="mon" label="按月统计"/>
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
    <h4 style="margin-bottom: 10px;">分润列表</h4>
    <%--<table class="table table-striped">--%>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>序号</th>
            <th>时间</th>
            <th>交易总笔数/笔</th>
            <th>交易总金额/元</th>
            <th>分润金额/元</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${profitPage.list}" var="profit" varStatus="obj">
            <tr>
                <td>${obj.count}</td>
                <td>${profit.countDate}</td>
                <td>${profit.orderCount}</td>
                <td>${profit.totalPayAmount}</td>
                <td>${profit.profit}</td>
            </tr>
        </c:forEach>
        </tbody>
        <c:if test="${empty profitPage.list}">
            <p class="clearfix">没有数据!</p>
        </c:if>
    </table>
    <div class="pagination">${orderPage}</div>
</div>
</body>
</html>