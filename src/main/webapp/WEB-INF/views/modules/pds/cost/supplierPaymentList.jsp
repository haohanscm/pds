<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商货款管理</title>
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
		<li class="active"><a href="${ctx}/pds/cost/supplierPayment/">供应商货款列表</a></li>
		<shiro:hasPermission name="pds:cost:supplierPayment:edit"><li><a href="${ctx}/pds/cost/supplierPayment/form">供应商货款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplierPayment" action="${ctx}/pds/cost/supplierPayment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>货款编号：</label>
                <form:input path="supplierPaymentId" htmlEscape="false" maxlength="10" class="input-medium " />
            </li>
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>询价单号：</label>
				<form:input path="askOrderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label>商家：</label>
                <form:input path="merchantName" htmlEscape="false" maxlength="10" class="input-medium " placeholder="可模糊查询"/>
            </li>
			<li><label>供应商：</label>
                <form:select path="supplierId" class="input-medium ">
                    <form:option value="" label="" />
                    <form:options items="${supplierList}" itemLabel="supplierName" itemValue="id"/>
                </form:select>
			</li>
            <li><label>售后单：</label>
                <form:input path="serviceId" htmlEscape="false" maxlength="10" class="input-medium " />
            </li>
			<li><label>供应日期：</label>
				<input name="beginSupplyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${supplierPayment.beginSupplyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endSupplyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${supplierPayment.endSupplyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
            <li><label>是否结算：</label>
                <form:select path="status" class="input-small ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input id="btnPaymentRecord" class="btn btn-primary" data-toggle="modal" data-target="#paymentRecord" type="button" value="生成货款" onclick=""/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
    <form action="${ctx}/pds/cost/supplierPayment/paymentRecord" method="post">
        <div class="modal hide fade" id="paymentRecord" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel2">按日生成货款记录</h4>
                    </div>
                    <div class="modal-body">
                        <div class="center">
                            <div id="pmId-div">
                                <div class="center">
                                    <b>平台商家&nbsp;&nbsp;&nbsp;</b>
                                    <select id="payment-select-pmId" name="pmId" style="width: 180px;">
                                        <option></option>
                                        <c:forEach items="${pmList}" var="pm">
                                            <option value="${pm.id}">${pm.merchantName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div id="supplierId-div">
                                <div class="center">
                                    <hr>
                                    <b>供应商&nbsp;&nbsp;&nbsp;</b>
                                    <select id="payment-select-supplierId" name="supplierId" style="width: 180px;">
                                        <option></option>
                                        <c:forEach items="${supplierList}" var="supplier">
                                            <option value="${supplier.id}">${supplier.supplierName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div id="supplyDate-div">
                                <div class="center">
                                    <hr>
                                    <b>送货日期&nbsp;&nbsp;</b>
                                    <input name="supplyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                                           value=""
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
                <th>货款编号</th>
				<th>供应商</th>
				<th>供应日期</th>
				<th>商家</th>
				<th>平台商家</th>
				<th>询价单号</th>
				<th>商品数量</th>
				<th>供应货款</th>
				<th>售后货款</th>
                <th>售后单</th>
				<th>是否结算</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:cost:supplierPayment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplierPayment" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/cost/supplierPayment/form?id=${supplierPayment.id}">
					${supplierPayment.supplierPaymentId}
				</a></td>
                <td>
                        ${supplierPayment.supplierName}
                </td>
				<td>
					<fmt:formatDate value="${supplierPayment.supplyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${supplierPayment.merchantName}
				</td>
				<td>
					${supplierPayment.pmName}
				</td>
				<td>
					${supplierPayment.askOrderId}
				</td>
				<td>
					${supplierPayment.goodsNum}
				</td>
				<td>
					${supplierPayment.supplierPayment}
				</td>
				<td>
					${supplierPayment.afterSalePayment}
				</td>
				<td>
					${supplierPayment.serviceId}
				</td>
				<td>
					${fns:getDictLabel(supplierPayment.status, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${supplierPayment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${supplierPayment.remarks}
				</td>
				<shiro:hasPermission name="pds:cost:supplierPayment:edit"><td>
    				<a href="${ctx}/pds/cost/supplierPayment/form?id=${supplierPayment.id}">修改</a>
					<a href="${ctx}/pds/cost/supplierPayment/delete?id=${supplierPayment.id}" onclick="return confirmx('确认要删除该供应商货款吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>