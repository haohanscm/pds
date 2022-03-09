<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
		    resetTip();
            var tipMessage = "${tip_message}".trim();
            if(tipMessage){
                alertx(tipMessage);
            }
            // pmid
            var pmList = ${fns:toJson(pmList)};
            var pmId = $("#select-pmId");
            if(pmList){
                pmId.empty();
                $.each(pmList, function (n, entity) {
                    pmId.append($("<option>").attr({"value": entity.id}).text(entity.merchantName));
                });
            }
            pmId.change();
            modelInit();
        });
		function modelInit() {
            var $contentBody = $("#select-content");
            $contentBody.show();
            $("#by-time-type").click(function () {
                $contentBody.show();
            });
            $("#default-Type").click(function () {
                $contentBody.hide();
                $('#deliveryDate').val('');
                seqSelectionsInit();
            });
            seqSelectionsInit();
        }
        function seqSelectionsInit() {
            var $seqSelections = $("#buy-seq");
            $seqSelections.empty();
            $seqSelections.html("<hr>" + "<b>送货批次  </b>" + "<select name=\"buySeq\" style=\"width: 177px\">" + "<option value=\"\" selected=\"selected\">请选择</option>" + "<option value=\"0\">第一批</option>" + "<option value=\"1\">第二批</option>");
        }
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function paymentRecord(buyerId,buyId) {
            top.$.jBox.confirm('确认生成货款记录吗?','系统提示',function(v,h,f) {
                if (v == 'ok') {
                    jQuery.ajax({
                        url: '${ctx}/pds/order/buyOrder/paymentRecord',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            buyerId: buyerId,
                            buyId: buyId
                        },
                        success: function (result) {
                            alertx(result.msg);
                            window.location.reload();
                        },
                        error: function (jqXHR) {
                            alertx(JSON.stringify(jqXHR));
                            console.log("error", jqXHR);
                        }
                    });
                }
            });
        }

        // 订单 确认收货
        function confirmAllGoods(buyerId, buyId) {
            top.$.jBox.confirm('确认该采购单所有货物已接收吗?','系统提示',function(v,h,f) {
                if (v == 'ok') {
                    jQuery.ajax({
                        url: '${ctx}/pds/order/buyOrder/confirmAllGoods',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            buyerId: buyerId,
                            buyId: buyId
                        },
                        success: function (result) {
                            alertx(result.msg);
                            window.location.reload();
                        },
                        error: function (jqXHR) {
                            alertx(JSON.stringify(jqXHR));
                            console.log("error", jqXHR);
                        }
                    });
                }
            });
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
		<li class="active"><a href="${ctx}/pds/order/buyOrder/">采购单列表</a></li>
		<shiro:hasPermission name="pds:order:buyOrder:edit"><li><a href="${ctx}/pds/order/buyOrder/form">采购单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="buyOrder" action="${ctx}/pds/order/buyOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>采购商名称：</label>
				<form:input path="buyerName" htmlEscape="false" maxlength="64" class="input-medium" placehoder="模糊查询"/>
			</li>
			<li><label>采购编号：</label>
				<form:input path="buyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>送货日期：</label>
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${buyOrder.deliveryTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>采购批次：</label>
				<form:select path="buySeq" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_seq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>采购商：</label>
                <form:select path="buyerId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${buyerList}" itemLabel="buyerName" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>采购时间：</label>
				<input name="beginBuyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${buyOrder.beginBuyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endBuyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${buyOrder.endBuyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>采购状态：</label>
				<form:select path="status" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width: 100px">货款结算状态：</label>
				<form:select path="paymentStatus" class="input-small">
					<form:option value="" label=""/>
					<form:option value="0" label="否"/>
					<form:option value="1" label="是"/>
					<form:option value="-1" label="未生成"/>
				</form:select>
			</li>
            <li><label>收货状态：</label>
                <form:select path="buyerStatus" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pds_buyer_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSummary" class="btn btn-primary" data-toggle="modal" data-target="#summary" type="button" value="采购单汇总" onclick=""/></li>
            <li class="btns"><input id="btnResetSummary" class="btn btn-primary" data-toggle="modal" data-target="#confirmAllGoodsBatch" type="button" value="生成货款" onclick=""/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/pds/order/buyOrder/summary" method="post">
		<div class="modal hide fade" id="summary" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">采购单汇总</h4>
					</div>
					<div class="modal-body">
						<div class="center">
							<div id="type-choose" class="center">
								<label>
									<input type="radio" name="typeChoose" id="by-time-type" value="1" checked><b>自定义</b>
								</label>
								<label>
									<input type="radio" name="typeChoose" id="default-Type" value="0"> <b>默认(今日)</b>
								</label>
							</div>
                            <div id="pmIdDiv" class="center">
                                <hr>
                                <b>平台商家</b>
                                <select id="select-pmId" name="pmId" style="width: 177px"></select>
                            </div>
							<div id="select-content">
								<div class="center">
									<hr>
									<b>送货日期</b>
									<input id="deliveryDate" name="deliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
										   value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
								</div>
								<div id="buy-seq" class="center">
								</div>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-warning">确认</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</form>
    <form action="${ctx}/pds/order/buyOrder/confirmAllGoodsBatch" method="post">
        <div class="modal hide fade" id="confirmAllGoodsBatch" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel2">按批次生成货款记录</h4>
                    </div>
                    <div class="modal-body">
                        <div class="center">
                            <div id="pmId-div" class="center">
                                <b>平台商家&nbsp;&nbsp;&nbsp;</b>
                                <select id="payment-select-pmId" name="pmId" style="width: 180px;">
                                    <option></option>
                                    <c:forEach items="${pmList}" var="pm">
                                        <option value="${pm.id}">${pm.merchantName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div id="deliveryDate-div">
                                <div class="center">
                                    <hr>
                                    <b>送货日期&nbsp;&nbsp;</b>
                                    <input name="deliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                                           value=""
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                </div>
                            </div>
                            <div id="buySeq-div" class="center">
                                <hr>
                                <b>批次&nbsp;&nbsp;</b>
                                <select id="payment-buySeq" name="buySeq" style="width: 180px;">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('pds_buy_seq')}" var="seq">
                                        <option value="${seq.value}">${seq.label}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-warning">确认</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>采购编号</th>
                <th>平台商家</th>
				<th>采购商</th>
				<th>采购时间</th>
				<th>送货日期</th>
				<th>采购批次</th>
				<th>总价预估</th>
				<th>采购总价</th>
				<th>采购状态</th>
				<th>配送方式</th>
				<th>货款结算状态</th>
				<th>收货状态</th>
				<th>更新时间</th>
				<th>需求信息</th>
				<th>备注</th>
				<shiro:hasPermission name="pds:order:buyOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="buyOrder" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/buyOrder/form?id=${buyOrder.id}">
					${buyOrder.buyId}
				</a></td>
                <td>
                        ${buyOrder.pmName}
                </td>
				<td>
					${buyOrder.buyerName}
				</td>
				<td>
					<fmt:formatDate value="${buyOrder.buyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${buyOrder.deliveryTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(buyOrder.buySeq, 'pds_buy_seq', '')}
				</td>
				<td>
					${buyOrder.genPrice}
				</td>
				<td>
					${buyOrder.totalPrice}
				</td>
				<td>
					${fns:getDictLabel(buyOrder.status, 'pds_buy_status', '')}
				</td>
				<td>
					${fns:getDictLabel(buyOrder.deliveryType, 'delivery_type', '')}
				</td>
                <td>
                        ${fns:getDictLabel(buyOrder.paymentStatus, 'yes_no', '未生成')}
                </td>
                <td>
                        ${fns:getDictLabel(buyOrder.buyerStatus, 'pds_buyer_status', '未交易')}
                </td>
				<td>
					<fmt:formatDate value="${buyOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
                <td>
                        ${buyOrder.needNote}
                </td>
                <td>
                        ${buyOrder.remarks}
                </td>
				<shiro:hasPermission name="pds:order:buyOrder:edit"><td>
    				<a href="${ctx}/pds/order/buyOrder/form?id=${buyOrder.id}">修改</a>
					<%--<a href="${ctx}/pds/order/buyOrder/delete?id=${buyOrder.id}" onclick="return confirmx('确认要删除该采购单吗？', this.href)">删除</a>--%>
					<a href="${ctx}/pds/order/buyOrderDetail/list?buyId=${buyOrder.buyId}">详情</a>
                    <c:if test="${buyOrder.status eq '2'}" >
                        <a href="${ctx}/pds/order/buyOrder/confirmBuyOrder?buyId=${buyOrder.buyId}&buyerId=${buyOrder.buyerId}" onclick="return confirmx('确认该采购单报价吗？', this.href)">确认报价</a>
                    </c:if>
                    <c:if test="${buyOrder.paymentStatus eq '-1'}" >
                        <a href="#" onclick="paymentRecord('${buyOrder.buyerId}','${buyOrder.buyId}')">生成货款</a>
                    </c:if>
                    <c:if test="${buyOrder.paymentStatus eq '0'}" >
                        <a href="#" onclick="paymentRecord('${buyOrder.buyerId}','${buyOrder.buyId}')">货款重算</a>
                    </c:if>
					<c:if test="${buyOrder.buyerStatus eq '1'}" >
                        <a href="#" onclick="confirmAllGoods('${buyOrder.buyerId}','${buyOrder.buyId}')">一键收货</a>
                    </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>