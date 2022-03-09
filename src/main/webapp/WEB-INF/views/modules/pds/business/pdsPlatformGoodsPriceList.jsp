<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>平台商品定价管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            // 恢复提示框显示
            resetTip();

            // 平台商家所属店铺
            $("#select-pmId").change(function () {
                var merchantId = $("#select-pmId").val();
                jQuery.ajax({
                    url: '${ctx}/pds/business/pdsPlatformGoodsPrice/shopList',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        merchantId: merchantId,
                    },
                    success: function (result) {
                        var shop = $("#select-shopId");
                        if (result.code == 200) {
                            var _data = result.ext;
                            shop.empty();
                            $.each(_data, function (n, entity) {
                                shop.append($("<option>").attr({"value": entity.id}).text(entity.name));
                            });
                            shop.change();
                        } else {
                            shop.empty();
                            shop.append($("<option>").attr({"value": ""}).text(""));
                            shop.change();
                            alertx("商家没有店铺");
                        }
                    }
                });
            });

            // 采购商所属商家
            $("#select-pmId").change(function () {
                var pmId = $("#select-pmId").val();
                jQuery.ajax({
                    url: '${ctx}/pds/business/pdsPlatformGoodsPrice/buyerMerchantList',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                    },
                    success: function (result) {
                        var merchant = $("#select-merchantId");
                        if (result.code == 200) {
                            var _data = result.ext;
                            merchant.empty();
                            $.each(_data, function (n, entity) {
                                merchant.append($("<option>").attr({"value": entity.merchantId}).text(entity.merchantName));
                            });
                            merchant.change();
                        } else {
                            merchant.empty();
                            merchant.append($("<option>").attr({"value": ""}).text(""));
                            merchant.change();
                            alertx("找不到平台商家所属采购商的商家");
                        }
                        $("#source-merchantId").html(merchant.html());
                        $("#target-merchantId").html(merchant.html());
                    }
                });
            });

            // 模态框提交
            $("#initPriceBtnSubmit").click(function () {
                // 不允许连续点击
                $("#initPriceBtnSubmit").attr("disabled", "disabled");
                var pmId = $("#select-pmId").val();
                var shopId = $("#select-shopId").val();
                var buyerMerchantId = $("#select-merchantId").val();
                var rate = $("#select-rate").val();
                var startDate = $("#select-startDate").val();
                var endDate = $("#select-endDate").val();
                if (!(pmId && shopId && buyerMerchantId && startDate && endDate)) {
                    alertx("没有选择所需选项");
                    $("#initPriceBtnSubmit").removeAttr("disabled");
                    return;
                }
                loading("系统操作中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/pds/business/pdsPlatformGoodsPrice/initPrice',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                        shopId: shopId,
                        buyerMerchantId: buyerMerchantId,
                        rate: rate,
                        startDate: startDate,
                        endDate: endDate
                    },
                    success: function (result) {
                        $("#initPriceBtnSubmit").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                    }
                });
            });

            // 模态框提交
            $("#copyPriceBtnSubmit").click(function () {
                // 不允许连续点击
                $("#copyPriceBtnSubmit").attr("disabled", "disabled");
                var pmId = $("#select-pmId").val();
                var merchantId = $("#source-merchantId").val();
                var newMerchantId = $("#target-merchantId").val();
                var rate = $("#select-rate").val();
                var queryDate = $("#select-queryDate").val();
                if (!(pmId && merchantId && newMerchantId && queryDate)) {
                    alertx("没有选择所需选项");
                    $("#copyPriceBtnSubmit").removeAttr("disabled");
                    return;
                }
                loading("系统操作中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/pds/business/pdsPlatformGoodsPrice/copyPrice',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                        merchantId: merchantId,
                        newMerchantId: newMerchantId,
                        rate: rate,
                        queryDate: queryDate
                    },
                    success: function (result) {
                        $("#copyPriceBtnSubmit").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                    }
                });
            });

            // 模态框提交
            $("#deletePriceBtnSubmit").click(function () {
                // 不允许连续点击
                $("#deletePriceBtnSubmit").attr("disabled", "disabled");
                var pmId = $("#select-pmId").val();
                var buyerMerchantId = $("#select-merchantId").val();
                var queryDate = $("#select-queryDate").val();
                if (!(pmId && buyerMerchantId && queryDate)) {
                    alertx("没有选择所需选项");
                    $("#deletePriceBtnSubmit").removeAttr("disabled");
                    return;
                }
                loading("系统操作中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/pds/business/pdsPlatformGoodsPrice/deletePrice',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                        buyerMerchantId: buyerMerchantId,
                        queryDate: queryDate
                    },
                    success: function (result) {
                        $("#deletePriceBtnSubmit").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                        window.location.reload();
                    }
                });
            });

            // 模态框提交
            $("#updateShopPriceBtnSubmit").click(function () {
                // 不允许连续点击
                $("#updateShopPriceBtnSubmit").attr("disabled", "disabled");
                var pmId = $("#select-pmId").val();
                var buyerMerchantId = $("#select-merchantId").val();
                var shopId = $("#select-shopId").val();
                var startDate = $("#select-startDate").val();
                var endDate = $("#select-endDate").val();
                if (!(pmId && buyerMerchantId && shopId && startDate && endDate)) {
                    alertx("没有选择所需选项");
                    $("#updateShopPriceBtnSubmit").removeAttr("disabled");
                    return;
                }
                loading("系统操作中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/pds/business/pdsPlatformGoodsPrice/updateShopPrice',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                        buyerMerchantId: buyerMerchantId,
                        shopId: shopId,
                        startDate: startDate,
                        endDate: endDate
                    },
                    success: function (result) {
                        $("#updateShopPriceBtnSubmit").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                    }
                });
            });
        });

        // 商品初始化模态框展示
        function initPriceClick() {
            $(".copy-price,.delete-price,.update-shop-price").hide();
            $(".init-price").show();
        }

        // 商品初始化模态框展示
        function copyPriceClick() {
            $(".init-price,.delete-price,.update-shop-price").hide();
            $(".copy-price").show();
        }

        // 商品初始化模态框展示
        function deletePriceClick() {
            $(".init-price,.copy-price,.update-shop-price").hide();
            $(".delete-price").show();
        }

        // 商品初始化模态框展示
        function updateShopPriceClick() {
            $(".init-price,.copy-price,.delete-price").hide();
            $(".update-shop-price").show();
        }

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style type="text/css">
        .center {
            vertical-align: middle;
            text-align: center;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/pds/business/pdsPlatformGoodsPrice/">平台商品定价列表</a></li>
    <shiro:hasPermission name="pds:business:pdsPlatformGoodsPrice:edit">
        <li><a href="${ctx}/pds/business/pdsPlatformGoodsPrice/form">平台商品定价添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="pdsPlatformGoodsPrice" action="${ctx}/pds/business/pdsPlatformGoodsPrice/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>平台商家：</label>
            <form:select path="pmId" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>采购商商家：</label>
            <form:select path="merchantId" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${merchantList}" itemLabel="merchantName" itemValue="merchantId"
                              htmlEscape="false"/>
            </form:select>
        </li>
            <%--<li><label>采购商：</label>--%>
            <%--<form:select path="buyerId" class="input-medium">--%>
            <%--<form:option value="" label=""/>--%>
            <%--<form:options items="${buyerList}" itemLabel="buyerName" itemValue="id" htmlEscape="false"/>--%>
            <%--</form:select>--%>
            <%--</li>--%>
        <li><label style="width: 100px">商品分类名称：</label>
            <form:input path="categoryName" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>商品名称：</label>
            <form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>规格名称：</label>
            <form:input path="modelName" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>起始时间：</label>
            <input name="beginStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${pdsPlatformGoodsPrice.beginStartDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

        </li>
        <li><label>截止时间：</label>
            <input name="endEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${pdsPlatformGoodsPrice.endEndDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>是否上架：</label>
            <form:select path="status" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
    <ul class="ul-form">
        <li class="btns"><input id="btnInitPrice" class="btn btn-primary" data-toggle="modal" data-target="#price-modal"
                                type="button" value="采购价格初始化" onclick="initPriceClick()"/></li>
        <li class="btns"><input id="btnCopyPrice" class="btn btn-primary" data-toggle="modal" data-target="#price-modal"
                                type="button" value="采购价格复制" onclick="copyPriceClick()"/></li>
        <li class="btns"><input id="btnDeletePrice" class="btn btn-primary" data-toggle="modal"
                                data-target="#price-modal" type="button" value="采购价格删除" onclick="deletePriceClick()"/>
        </li>
        <li class="btns"><input id="btnUpdateShopPrice" class="btn btn-primary" data-toggle="modal"
                                data-target="#price-modal" type="button" value="更新零售店铺价格"
                                onclick="updateShopPriceClick()"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div class="modal hide fade" id="price-modal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">
                    <span class="init-price">初始化 采购商商家 商品采购价</span>
                    <span class="copy-price">复制 采购商商家 商品采购价</span>
                    <span class="delete-price">删除 采购商商家 商品采购价</span>
                    <span class="update-shop-price">更新零售店铺价格</span>
                </h4>
            </div>
            <div class="modal-body">
                <div class="center">
                    <div class="center">
                        <span class="init-price">采购商商家的商品周期采购价设置(根据平台商家商品);<br/>注意起止时间不能有重合</span>
                        <span class="copy-price">复制采购商商家商品采购价库,若选择日期已有该时段的数据会重新生成</span>
                        <span class="delete-price">删除采购商商家商品采购价库,删除选择日期所处的时间段的所有记录</span>
                        <span class="update-shop-price">使用选择日期所处的时间段的采购价格,更新零售店铺价格</span>
                    </div>
                    <div id="pmIdDiv" class="center">
                        <hr>
                        <b>平台商家&nbsp;&nbsp;&nbsp;</b>
                        <select id="select-pmId" name="pmId" style="width: 180px;">
                            <option></option>
                            <c:forEach items="${pmList}" var="pm">
                                <option value="${pm.id}">${pm.merchantName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="shopIdDiv" class="center init-price update-shop-price">
                        <hr>
                        <b>平台商家店铺</b>
                        <select id="select-shopId" name="shopId" style="width: 180px;">
                        </select>
                    </div>
                    <div id="buyerMerchantIdDiv" class="center init-price delete-price update-shop-price">
                        <hr>
                        <b>采购商商家&nbsp;&nbsp;&nbsp;</b>
                        <select id="select-merchantId" name="buyerMerchantId" style="width: 180px;">
                        </select>
                    </div>
                    <div id="sourceMerchantIdDiv" class="center copy-price">
                        <hr>
                        <b>商品来源商家&nbsp;&nbsp;&nbsp;</b>
                        <select id="source-merchantId" name="merchantId" style="width: 180px;">
                        </select>
                    </div>
                    <div id="targetMerchantIdDiv" class="center copy-price">
                        <hr>
                        <b>目标商家&nbsp;&nbsp;&nbsp;</b>
                        <select id="target-merchantId" name="newMerchantId" style="width: 180px;">
                        </select>
                    </div>
                    <div id="select-content" class="init-price update-shop-price">
                        <div class="center">
                            <hr>
                            <b>定价起止日期&nbsp;&nbsp;</b>
                            <input id="select-startDate" name="startDate" type="text" readonly="readonly" maxlength="20"
                                   class="input-medium Wdate"
                                   value=""
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
                            <input id="select-endDate" name="endDate" type="text" readonly="readonly" maxlength="20"
                                   class="input-medium Wdate"
                                   value=""
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                        </div>
                    </div>
                    <div class="copy-price delete-price">
                        <div class="center">
                            <hr>
                            <b>选择日期&nbsp;&nbsp;</b>
                            <input id="select-queryDate" name="queryDate" type="text" readonly="readonly" maxlength="20"
                                   class="input-medium Wdate"
                                   value=""
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                        </div>
                    </div>
                    <div id="price-rate" class="center init-price copy-price">
                        <hr>
                        <b>定价上浮比例&nbsp;&nbsp;</b>
                        <input id="select-rate" type="text" name="rate" maxlength="5" class="input-medium number">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="initPriceBtnSubmit" type="button" class="btn btn-warning init-price">开始初始化</button>
                <button id="copyPriceBtnSubmit" type="button" class="btn btn-warning copy-price">开始复制</button>
                <button id="deletePriceBtnSubmit" type="button" class="btn btn-warning delete-price">开始删除</button>
                <button id="updateShopPriceBtnSubmit" type="button" class="btn btn-warning update-shop-price">开始更新
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>平台商家</th>
        <th>采购商商家</th>
        <%--<th>采购商</th>--%>
        <th>商品分类名称</th>
        <th>商品名称</th>
        <th>规格名称</th>
        <th>单位</th>
        <th>采购价</th>
        <th>起始时间</th>
        <th>截止时间</th>
        <th>是否上架</th>
        <th>更新时间</th>
        <th>备注信息</th>
        <shiro:hasPermission name="pds:business:pdsPlatformGoodsPrice:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="pdsPlatformGoodsPrice" varStatus="index">
        <tr>
            <td>
                    ${index.count}
            </td>
            <td><a href="${ctx}/pds/business/pdsPlatformGoodsPrice/form?id=${pdsPlatformGoodsPrice.id}">
                    ${pdsPlatformGoodsPrice.pmName}
            </a></td>
            <td>
                    ${pdsPlatformGoodsPrice.merchantName}
            </td>
                <%--<td>--%>
                <%--${pdsPlatformGoodsPrice.buyerName}--%>
                <%--</td>--%>
            <td>
                    ${pdsPlatformGoodsPrice.categoryName}
            </td>
            <td>
                    ${pdsPlatformGoodsPrice.goodsName}
            </td>
            <td>
                    ${pdsPlatformGoodsPrice.modelName}
            </td>
            <td>
                    ${pdsPlatformGoodsPrice.unit}
            </td>
            <td>
                    ${pdsPlatformGoodsPrice.price}
            </td>
            <td>
                <fmt:formatDate value="${pdsPlatformGoodsPrice.startDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                <fmt:formatDate value="${pdsPlatformGoodsPrice.endDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${fns:getDictLabel(pdsPlatformGoodsPrice.status, 'yes_no', '')}
            </td>
            <td>
                <fmt:formatDate value="${pdsPlatformGoodsPrice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${pdsPlatformGoodsPrice.remarks}
            </td>
            <shiro:hasPermission name="pds:business:pdsPlatformGoodsPrice:edit">
                <td>
                    <a href="${ctx}/pds/business/pdsPlatformGoodsPrice/form?id=${pdsPlatformGoodsPrice.id}">修改</a>
                    <a href="${ctx}/pds/business/pdsPlatformGoodsPrice/delete?id=${pdsPlatformGoodsPrice.id}"
                       onclick="return confirmx('确认要删除该平台商品定价吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>