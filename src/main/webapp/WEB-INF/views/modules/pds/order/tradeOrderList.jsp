<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            var _message = "${tip_message}".trim();
            if (_message){
                alertx(_message);
            }
            // pmid
            var pmList = ${fns:toJson(pmList)};
            var arrivedPmId = $("#arrived-select-pmId");
            var truckPmId = $("#truck-select-pmId");
            var supplierPmId = $("#supplier-select-pmId");
            var preparePmId = $("#prepare-notify-pmId");
            if(pmList){
                arrivedPmId.empty();
                truckPmId.empty();
                $.each(pmList, function (n, entity) {
                    arrivedPmId.append($("<option>").attr({"value": entity.id}).text(entity.merchantName));
                    truckPmId.append($("<option>").attr({"value": entity.id}).text(entity.merchantName));
                    supplierPmId.append($("<option>").attr({"value": entity.id}).text(entity.merchantName));
                    preparePmId.append($("<option>").attr({"value": entity.id}).text(entity.merchantName));
                });
            }
            arrivedPmId.change();
            truckPmId.change();
           	modelInit();
		});
        function modelInit() {
            var $contentBody = $(".select-content");
            $(".by-time-type").click(function () {
                $contentBody.show();
            });
            $(".default-Type").click(function () {
                $contentBody.hide();
                $('.deliveryDate').val('');
                seqSelectionsInit();
            });
            seqSelectionsInit();
        }
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function seqSelectionsInit() {
            var $seqSelections = $(".buy-seq");
            $seqSelections.empty();
            $seqSelections.html("<hr>" + "<b>送货批次  </b>" + "<select name=\"buySeq\" style=\"width: 177px\">" + "<option value=\"\" selected=\"selected\">请选择</option>" + "<option value=\"0\">第一批</option>" + "<option value=\"1\">第二批</option>");
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
		<li class="active"><a href="${ctx}/pds/order/tradeOrder/">交易订单列表</a></li>
		<shiro:hasPermission name="pds:order:tradeOrder:edit"><li><a href="${ctx}/pds/order/tradeOrder/form">交易订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tradeOrder" action="${ctx}/pds/order/tradeOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>送货日期：</label>
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${tradeOrder.deliveryTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>采购批次：</label>
				<form:select path="buySeq" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_seq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易单号：</label>
				<form:input path="tradeId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>汇总单号：</label>
				<form:input path="summaryBuyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购编号：</label>
				<form:input path="buyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>配送方式：</label>
				<form:select path="deliveryType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>报价单号：</label>
				<form:input path="offerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购商：</label>
				<form:input path="buyerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>供应商：</label>
                <form:select path="supplierId" class="input-medium" >
                    <form:option value="" label=""/>
                    <form:options items="${supplierList}" itemValue="id" itemLabel="supplierName" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>商品ID：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>采购时间：</label>
				<input name="beginBuyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tradeOrder.beginBuyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endBuyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tradeOrder.endBuyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>供应商状态：</label>
				<form:select path="supplierStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_supplier_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易状态：</label>
				<form:select path="transStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_trade_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>采购商状态：</label>
				<form:select path="buyerStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buyer_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>运营状态：</label>
				<form:select path="opStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_op_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>配送状态：</label>
				<form:select path="deliveryStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_delivery_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnPrepareNotify" class="btn btn-primary" data-toggle="modal" data-target="#prepareNotify" type="button" value="发送备货通知" onclick=""/></li>
			<li class="btns"><input id="btnSummary" class="btn btn-primary" data-toggle="modal" data-target="#truckLoad" type="button" value="一键装车" onclick=""/></li>
			<li class="btns"><input id="btnSummary2" class="btn btn-primary" data-toggle="modal" data-target="#selfOrderArrived" type="button" value="自提单送达" onclick=""/></li>
			<li class="btns"><input id="btnSummary3" class="btn btn-primary" data-toggle="modal" data-target="#supplierFreight" type="button" value="供应商揽货" onclick=""/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/pds/order/tradeOrder/truckLoad" method="post">
		<div class="modal hide fade" id="truckLoad" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">一键装车</h4>
					</div>
					<div class="modal-body">
						<div class="center">
							<div class="center type-choose">
								<label>
									<input type="radio" name="typeChoose" class="by-time-type" value="1" checked><b>自定义</b>
								</label>
								<label>
									<input type="radio" name="typeChoose" class="default-Type" value="0"> <b>默认(今日)</b>
								</label>
							</div>
                            <div class="center">
                                <hr>
                                <b>平台商家</b>
                                <select id="truck-select-pmId" name="pmId" style="width: 177px"></select>
                            </div>
							<div class="select-content">
								<div class="center">
									<hr>
									<b>送货日期</b>
									<input name="deliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate deliveryDate"
										   value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
								</div>
								<div class="center buy-seq">
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
	<form action="${ctx}/pds/order/tradeOrder/selfOrderArrived" method="post">
		<div class="modal hide fade" id="selfOrderArrived" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel2">自提订单送达</h4>
					</div>
					<div class="modal-body">
						<div class="center">
							<div class="center type-choose">
								<label>
									<input type="radio" name="typeChoose" class="by-time-type" value="1" checked><b>自定义</b>
								</label>
								<label>
									<input type="radio" name="typeChoose" class="default-Type" value="0"> <b>默认(今日)</b>
								</label>
							</div>
                            <div class="center">
                                <hr>
                                <b>平台商家</b>
                                <select id="arrived-select-pmId" name="pmId" style="width: 177px"></select>
                            </div>
							<div class="select-content">
								<div class="center">
									<hr>
									<b>送货日期</b>
									<input name="deliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate deliveryDate"
										   value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
								</div>
								<div class="center buy-seq">
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
	<form action="${ctx}/pds/order/tradeOrder/prepareNotify" method="post">
		<div class="modal hide fade" id="prepareNotify" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel3">发送备货通知</h4>
					</div>
					<div class="modal-body">
						<div class="center">
							<div class="select-content">
								<div class="center">
									<b>送货日期</b>
									<input name="deliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate deliveryDate"
										   value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
								</div>
								<div class="center">
									<hr>
									<b>平台商家</b>
									<select id="prepare-notify-pmId" name="pmId" style="width: 177px"></select>
								</div>
								<div class="center buy-seq">
								</div>
								<hr>
								<div id="supplierList">
									<b>供应商&nbsp;&nbsp;&nbsp;</b>
									<select name="supplier" style="width: 180px;">
										<option></option>
										<c:forEach items="${supplierList}" var="supplier">
											<option value="${supplier.id}">${supplier.supplierName}</option>
										</c:forEach>
									</select>
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
    <form action="${ctx}/pds/order/tradeOrder/supplierFreight" method="post">
        <div class="modal hide fade" id="supplierFreight" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel4">供应商揽货</h4>
                    </div>
                    <div class="modal-body">
                        <div class="center">
                            <div class="center">
                                <hr>
                                <b>平台商家</b>
                                <select id="supplier-select-pmId" name="pmId" style="width: 177px"></select>
                            </div>
                            <div class="center">
                                <hr>
                                <b>供应商&nbsp;&nbsp;&nbsp;</b>
                                <select name="supplierId" style="width: 180px;">
                                    <option></option>
                                    <c:forEach items="${supplierList}" var="supplier">
                                        <option value="${supplier.id}">${supplier.supplierName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="select-content">
                                <div class="center">
                                    <hr>
                                    <b>送货日期</b>
                                    <input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate deliveryDate"
                                           value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                </div>
                                <div class="center buy-seq">
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
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>交易单号</th>
                <th>平台商家</th>
				<th>报价类型</th>
				<th>采购商</th>
				<th>供应商</th>
				<th>商品名称</th>
				<th>商品图片</th>
				<th>规格</th>
				<th>单位</th>
				<th>采购数量</th>
				<th>分拣数量</th>
				<th>采购时间</th>
				<th>供应单价</th>
				<th>采购单价</th>
				<th>成交时间</th>
				<th>配送方式</th>
				<th>配送日期</th>
				<th>供应商状态</th>
				<th>交易状态</th>
				<th>采购商状态</th>
				<th>运营状态</th>
				<th>配送状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:tradeOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tradeOrder" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/tradeOrder/form?id=${tradeOrder.id}">
					${tradeOrder.tradeId}
				</a></td>
                <td>
                    ${tradeOrder.pmName}
                </td>
				<td>
					${fns:getDictLabel(tradeOrder.offerType, 'pds_offer_type', '')}
				</td>
				<td>
					${tradeOrder.buyerName}
				</td>
				<td>
					${tradeOrder.supplierName}
				</td>
				<td>
					${tradeOrder.goodsName}
				</td>
				<td>
					<img src="${tradeOrder.goodsImg}" alt="" width="200px">
				</td>
				<td>
					${tradeOrder.goodsModel}
				</td>
				<td>
					${tradeOrder.unit}
				</td>
				<td>
					${tradeOrder.buyNum}
				</td>
				<td>
					${tradeOrder.sortOutNum}
				</td>
				<td>
					<fmt:formatDate value="${tradeOrder.buyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tradeOrder.supplyPrice}
				</td>
				<td>
					${tradeOrder.buyPrice}
				</td>
				<td>
					<fmt:formatDate value="${tradeOrder.dealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(tradeOrder.deliveryType, 'delivery_type', '')}
				</td>
				<td>
                    <fmt:formatDate value="${tradeOrder.deliveryTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(tradeOrder.supplierStatus, 'pds_supplier_status', '')}
				</td>
				<td>
					${fns:getDictLabel(tradeOrder.transStatus, 'pds_trade_type', '')}
				</td>
				<td>
					${fns:getDictLabel(tradeOrder.buyerStatus, 'pds_buyer_status', '')}
				</td>
				<td>
					${fns:getDictLabel(tradeOrder.opStatus, 'pds_op_status', '')}
				</td>
				<td>
					${fns:getDictLabel(tradeOrder.deliveryStatus, 'pds_delivery_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${tradeOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tradeOrder.remarks}
				</td>
				<shiro:hasPermission name="pds:order:tradeOrder:edit"><td>
    				<a href="${ctx}/pds/order/tradeOrder/form?id=${tradeOrder.id}">修改</a>
					<a href="${ctx}/pds/order/tradeOrder/delete?id=${tradeOrder.id}" onclick="return confirmx('确认要删除该交易订单吗？', this.href)">删除</a>
    				<a href="${ctx}/pds/order/serviceOrder/addByTradeOrder?id=${tradeOrder.id}" onclick="return confirmx('确认要添加售后单吗？', this.href)">添加售后单</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>