<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>物流配送管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        // 货物送达
        function goodsArrived(deliveryId) {
            top.$.jBox.confirm('确认货物都送达吗?','系统提示',function(v,h,f) {
                if (v == 'ok') {
                    jQuery.ajax({
                        url: '${ctx}/pds/delivery/deliveryFlow/goodsArrived',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            deliveryId: deliveryId
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pds/delivery/deliveryFlow/">物流配送列表</a></li>
		<shiro:hasPermission name="pds:delivery:deliveryFlow:edit"><li><a href="${ctx}/pds/delivery/deliveryFlow/form">物流配送添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deliveryFlow" action="${ctx}/pds/delivery/deliveryFlow/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>配送编号：</label>
				<form:input path="deliveryId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>配送日期：</label>
				<input name="beginDeliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${deliveryFlow.beginDeliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endDeliveryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${deliveryFlow.endDeliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>物流车号：</label>
				<form:input path="truckNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>线路编号：</label>
				<form:input path="lineNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_delivery_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>配送编号</th>
                <th>平台商家</th>
				<th>配送日期</th>
				<th>送货批次</th>
				<th>物流车号</th>
				<th>线路编号</th>
				<th>当日车次</th>
				<th>发车时间</th>
				<th>完成时间</th>
				<th>货物数量</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:delivery:deliveryFlow:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deliveryFlow">
			<tr>
				<td><a href="${ctx}/pds/delivery/deliveryFlow/form?id=${deliveryFlow.id}">
					${deliveryFlow.deliveryId}
				</a></td>
                <td>
                        ${deliveryFlow.pmName}
                </td>
				<td>
					<fmt:formatDate value="${deliveryFlow.deliveryDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(deliveryFlow.deliverySeq, 'pds_buy_seq', '')}
				</td>
				<td>
					${deliveryFlow.truckNo}
				</td>
				<td>
					${deliveryFlow.lineNo}
				</td>
				<td>
					${deliveryFlow.ondayTrains}
				</td>
				<td>
					<fmt:formatDate value="${deliveryFlow.departTruckTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${deliveryFlow.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${deliveryFlow.goodsNum}
				</td>
				<td>
					${fns:getDictLabel(deliveryFlow.status, 'pds_delivery_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${deliveryFlow.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${deliveryFlow.remarks}
				</td>
				<shiro:hasPermission name="pds:delivery:deliveryFlow:edit"><td>
    				<a href="${ctx}/pds/delivery/deliveryFlow/form?id=${deliveryFlow.id}">修改</a>
					<a href="${ctx}/pds/delivery/deliveryFlow/delete?id=${deliveryFlow.id}" onclick="return confirmx('确认要删除该物流配送吗？', this.href)">删除</a>
                    <a href="#" onclick="goodsArrived('${deliveryFlow.deliveryId}')">所有物品送达</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>