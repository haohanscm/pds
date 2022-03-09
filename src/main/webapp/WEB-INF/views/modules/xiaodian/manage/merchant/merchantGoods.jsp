<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家订单</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            resetTip();

            var selectNode = $('#shopId');
            var id = selectNode.val()
            var shopName = $('option[value=' + id + ']').text()
            if (shopName) {
                $('#shops').text(shopName + ' - ')
            } else {
                $('#shops').text('所有店铺 - ')
            }
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function toReset() {
            $("#topNum").val("");
            $("#shopId").select2("val", "");
            $("#shopName").val("");
            $("#orderId").val("");
            $("#payType").select2("val", "");
            $("#diffDays").val("");
            $("[name='beginPayDate']").val("");
            $("[name='endPayDate']").val("");
        }
    </script>
    <style type="text/css">
        .well {
            background-color: #fff;
        }

        body {
            background-color: #f3f3f4;
        }

        .itembox > ul {
            width: 95%;
            display: flex;
            margin: 0 auto;
        }

        .itembox > ul > li {
            flex: 1;
            margin-left: 1%;
            border: 1px solid #f5f5f5;
            list-style: none;
        }
    </style>
</head>
<body>
<form:form id="searchForm" modelAttribute="merchantTurnover" action="${ctx}/xiaodian/manage/merchant/goodsSale"
           method="post" class="breadcrumb form-search">
    <%--<input id="pageNo" name="pageNo" type="hidden" value="${orderPage.pageNo}"/>--%>
    <%--<input id="pageSize" name="pageSize" type="hidden" value="${orderPage.pageSize}"/>--%>
    <ul class="ul-form">
        <li><label>显示商品数：</label>
            <form:input path="topNum" htmlEscape="false" maxlength="2" class="input-medium digits" placeholder="默认最多20条"/>
        </li>
        <li><label>店铺：</label>
            <form:select path="shopId" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${shopList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>店铺名称：</label>
            <form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
        </li>
        <li><label>支付方式：</label>
            <form:select path="payType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('pay_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>距今天天数：</label>
            <form:input path="diffDays" htmlEscape="false" maxlength="2" class="input-medium digits" placeholder="默认查询所有"/>
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
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置筛选条件" onclick="toReset()"/>
        </li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div class="well">
    <%--<table class="table table-striped">--%>
    <h4 style="margin-bottom: 10px;"><span id="shops"></span>销量列表</h4>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>序号</th>
            <th>商品名称</th>
            <%--<th>商品id</th>--%>
            <th>商品价格</th>
            <th>商品销售数量</th>
            <th>商品销售总金额</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${goodsSale}" var="goods" varStatus="item">
            <tr>
                <td>${item.count}</td>
                <td>${goods.goodsName}</td>
                    <%--<td>${goods.goodsId}</td>--%>
                <td>${goods.goodsPrice}</td>
                <td>${goods.goodsSaleNum}</td>
                <td>${goods.goodsSaleAmount}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty goodsSale}">
            <p class="clearfix">没有数据!</p>
        </c:if>
        </tbody>
    </table>
    <%--<div class="pagination">${orderPage}</div>--%>
</div>
</body>
</html>