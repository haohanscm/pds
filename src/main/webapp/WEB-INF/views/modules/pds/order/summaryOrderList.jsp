<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购单汇总管理</title>
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
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
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
        function resetSummary() {
		    $("#resetSummaryBtn").attr("disabled","disabled");
            loading('正在提交，请稍等...');
            var pmId = $("#reset-select-pmId").val();
            var date = $("#reset-deliveryDate").val();
            var buySeq = $("#reset-buySeq").val();
            if(pmId && date && buySeq){
                $("#resetSummaryForm").submit();
            }else{
                alertx("选项未选择!");
                closeLoading();
                $("#resetSummaryBtn").removeAttr("disabled");
            }
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
		<li class="active"><a href="${ctx}/pds/order/summaryOrder/">采购单汇总列表</a></li>
		<shiro:hasPermission name="pds:order:summaryOrder:edit"><li><a href="${ctx}/pds/order/summaryOrder/form">采购单汇总添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="summaryOrder" action="${ctx}/pds/order/summaryOrder/" method="post" class="breadcrumb form-search">
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
					   value="<fmt:formatDate value="${summaryOrder.deliveryTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>采购批次：</label>
				<form:select path="buySeq" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_seq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>汇总单号：</label>
				<form:input path="summaryOrderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品ID：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_summary_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSummary" class="btn btn-primary" data-toggle="modal" data-target="#confirmOffer" type="button" value="确认报价" onclick=""/></li>
			<li class="btns"><input id="btnResetSummary" class="btn btn-primary" data-toggle="modal" data-target="#resetSummary" type="button" value="按批次重置数据" onclick=""/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/pds/order/summaryOrder/confirmOffer" method="post">
		<div class="modal hide fade" id="confirmOffer" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">确认报价</h4>
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
    <form id="resetSummaryForm" action="${ctx}/pds/order/summaryOrder/resetSummary" method="post">
        <div class="modal hide fade" id="resetSummary" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel2">重置所选时间批次数据至下单状态</h4>
                    </div>
                    <div class="modal-body">
                        <div class="center">
                            <div id="pmId-div" class="center">
                                <b>平台商家&nbsp;&nbsp;&nbsp;</b>
                                <select id="reset-select-pmId" name="pmId" style="width: 180px;">
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
                                    <input id="reset-deliveryDate" name="deliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                                           value=""
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                </div>
                            </div>
                            <div id="buySeq-div" class="center">
                                <hr>
                                <b>批次&nbsp;&nbsp;</b>
                                <select id="reset-buySeq" name="buySeq" style="width: 180px;">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('pds_buy_seq')}" var="seq">
                                        <option value="${seq.value}">${seq.label}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="resetSummaryBtn" type="button" class="btn btn-warning" onclick="resetSummary()">确认</button>
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
				<th>汇总单号</th>
                <th>平台商家</th>
				<th>商品ID</th>
				<th>商品名称</th>
				<th>商品规格</th>
				<th>单位</th>
				<th>市场价</th>
				<th>采购均价</th>
				<th>供应均价</th>
				<%--<th>最小供应量</th>--%>
				<th>需求采购数量</th>
				<th>实际采购数量</th>
				<th>送货日期</th>
				<th>采购批次</th>
				<%--<th>商家数量</th>--%>
				<th>状态</th>
				<th>已生成交易单</th>
				<th>更新时间</th>
				<shiro:hasPermission name="pds:order:summaryOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="summaryOrder" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/summaryOrder/form?id=${summaryOrder.id}">
					${summaryOrder.summaryOrderId}
				</a></td>
                <td>
                    ${summaryOrder.pmName}
                </td>
				<td>
					${summaryOrder.goodsId}
				</td>
				<td>
					${summaryOrder.goodsName}
				</td>
				<td>
					${summaryOrder.goodsModel}
				</td>
				<td>
					${summaryOrder.unit}
				</td>
				<td>
					${summaryOrder.marketPrice}
				</td>
				<td>
					${summaryOrder.buyAvgPrice}
				</td>
				<td>
					${summaryOrder.supplyAvgPrice}
				</td>
				<%--<td>--%>
					<%--${summaryOrder.limitSupplyNum}--%>
				<%--</td>--%>
				<td>
					${summaryOrder.needBuyNum}
				</td>
				<td>
					${summaryOrder.realBuyNum}
				</td>
				<td>
					<fmt:formatDate value="${summaryOrder.deliveryTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(summaryOrder.buySeq, 'pds_buy_seq', '')}
				</td>
				<%--<td>--%>
					<%--${summaryOrder.buyerNum}--%>
				<%--</td>--%>
				<td>
					${fns:getDictLabel(summaryOrder.status, 'pds_summary_status', '')}
				</td>
				<td>
					${fns:getDictLabel(summaryOrder.isGenTrade, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${summaryOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="pds:order:summaryOrder:edit"><td>
    				<a href="${ctx}/pds/order/summaryOrder/form?id=${summaryOrder.id}">修改</a>
					<a href="${ctx}/pds/order/summaryOrder/delete?id=${summaryOrder.id}" onclick="return confirmx('确认要删除该采购单汇总吗？', this.href)">删除</a>
					<a href="${ctx}/pds/order/buyOrderDetail/list?smmaryBuyId=${summaryOrder.summaryOrderId}">采购单</a>
					<a href="${ctx}/pds/order/offerOrder/list?askOrderId=${summaryOrder.summaryOrderId}">报价单</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>