<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易匹配单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            var _message = "${tip_message}".trim();
            if (_message){
                alertx(_message);
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
		<li class="active"><a href="${ctx}/pds/order/tradeMatch/">交易匹配单列表</a></li>
		<shiro:hasPermission name="pds:order:tradeMatch:edit"><li><a href="${ctx}/pds/order/tradeMatch/form">交易匹配单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tradeMatch" action="${ctx}/pds/order/tradeMatch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>询价单号：</label>
				<form:input path="askOrderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>报价单号：</label>
				<form:input path="offerOrderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>供应商：</label>
				<form:input path="supplierId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>报价类型：</label>
				<form:select path="offerType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_offer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_trade_match_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btn-match" class="btn btn-primary" data-toggle="modal" data-target="#tradeMatchModal" type="button" value="交易匹配" onclick=""/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form action="${ctx}/pds/order/tradeMatch/match" class="form-control" method="post">
		<div class="modal hide fade" id="tradeMatchModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">交易匹配</h4>
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
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>询价单号</th>
                <th>平台商家</th>
				<th>报价单号</th>
				<th>供应商</th>
				<th>最大供应数量</th>
				<th>报价类型</th>
				<th>供应商报价</th>
				<th>成交价</th>
				<th>向供应商采购</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:order:tradeMatch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tradeMatch" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/order/tradeMatch/form?id=${tradeMatch.id}">
					${tradeMatch.askOrderId}
				</a></td>
                <td>
                        ${tradeMatch.pmName}
                </td>
				<td>
					${tradeMatch.offerOrderId}
				</td>
				<td>
					${tradeMatch.supplierName}
				</td>
				<td>
					${tradeMatch.supplyNum}
				</td>
				<td>
					${fns:getDictLabel(tradeMatch.offerType, 'pds_offer_type', '')}
				</td>
				<td>
					${tradeMatch.offerPrice}
				</td>
				<td>
					${tradeMatch.dealPrice}
				</td>
				<td>
					${tradeMatch.buyNum}
				</td>
				<td>
					${fns:getDictLabel(tradeMatch.status, 'pds_trade_match_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${tradeMatch.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tradeMatch.remarks}
				</td>
				<shiro:hasPermission name="pds:order:tradeMatch:edit"><td>
    				<a href="${ctx}/pds/order/tradeMatch/form?id=${tradeMatch.id}">修改</a>
					<a href="${ctx}/pds/order/tradeMatch/delete?id=${tradeMatch.id}" onclick="return confirmx('确认要删除该交易匹配单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>