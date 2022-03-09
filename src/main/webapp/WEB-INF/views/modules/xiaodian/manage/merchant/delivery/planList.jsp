<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送计划管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            // 恢复提示框显示
            resetTip();
            $("#orgDeliveryMan").change(function () {
                var selectedDeliverId = this.options[this.options.selectedIndex].value;
                this.selected = selectedDeliverId;
            });
            $("#newDeliveryMan").change(function () {
                var selectedDeliverId = this.options[this.options.selectedIndex].value;
                this.selected = selectedDeliverId;
            });
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };
        function toReset() {
            $("#shopName").val("");
            $("#orderId").val("");
            $("#goodsName").val("");
            $("#giftName").val("");
            $("#communityName").val("");
            $("#memberName").val("");
            $("#memberContact").val("");
            $("#deliveryManName").val("");
            $("#deliverManTel").val("");
            $("#status").select2("val","");
            $("[name='theDay']").val("");
            $("[name='deliveryTime']").val("");
        }

        function queryDeliverManList(){
            var orgDelivery = $("#orgDeliveryMan");
            var newDelivery = $("#newDeliveryMan");
            orgDelivery.empty();
            newDelivery.empty();
            $.ajax({url:"${ctx}/xiaodian/manage/merchant/delivery/fetchDeliverManList",success:function(result){
                    // console.log(result);
                    var obj = eval(result);
                    // console.log(obj);
                    $.each(obj,function(i,val){
                        orgDelivery.append("<option value='"+obj[i].id+"'>"+obj[i].realName+"</option>");
                        newDelivery.append("<option value='"+obj[i].id+"'>"+obj[i].realName+"</option>")
                    });
            }});
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/manage/merchant/delivery/plan/list">配送计划列表</a></li>
		<shiro:hasPermission name="xiaodian:delivery:deliveryPlan:edit"><li><a href="${ctx}/xiaodian/manage/merchant/delivery/plan/form">配送计划添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deliveryPlan" action="${ctx}/xiaodian/manage/merchant/delivery/plan/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>支付订单id：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺名称：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="10" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="10" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>赠品名称：</label>
				<form:input path="giftName" htmlEscape="false" maxlength="10" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>小区名称：</label>
				<form:input path="communityName" htmlEscape="false" maxlength="10" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>会员姓名：</label>
				<form:input path="memberName" htmlEscape="false" maxlength="10" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>会员手机：</label>
				<form:input path="memberContact" htmlEscape="false" maxlength="11" class="input-medium"/>
			<li><label>配送员姓名：</label>
				<form:input path="deliveryManName" htmlEscape="false" maxlength="10" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>配送员电话：</label>
				<form:input path="deliverManTel" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>配送状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_plan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			</li>
			<li><label>配送日期：</label>
				<input name="theDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${deliveryPlan.theDay}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>送达时间：</label>
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${deliveryPlan.deliveryTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置条件" onclick="toReset()"/></li>
			<li class="btns"><input id="replaceDeliverMan" class="btn btn-primary" data-toggle="modal" data-target="#replaceDeliver" type="button" value="变更配送员" onclick="queryDeliverManList()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

	<form action="${ctx}/xiaodian/manage/merchant/delivery/replaceDeliveryMan" method="post">
		<div class="modal fade" id="replaceDeliver" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">变更配送员</h4>
					</div>
					<div class="modal-body">
						<ul>
							<li><span>开始时间: </span>
								<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
														  value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
														  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
								<span>&nbsp;截止时间: </span>
								<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
																value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>"
																onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></li>
							<li><span>原配送员: </span>
								<select name="orgDeliveryManId" id="orgDeliveryMan" class="input-medium"></select>
								<span>&nbsp;新配送员: </span>
								<select name="newDeliveryManId" id="newDeliveryMan" class="input-medium"></select></li>
						</ul>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary">提交更改</button>
					</div>
				</div>
			</div>
		</div>
	</form>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>配送日期</th>
				<%--<th>商家名称</th>--%>
				<%--<th>支付订单ID</th>--%>
				<th>店铺名称</th>
				<th>商品名称</th>
				<th>商品数量</th>
                <th>赠品名称</th>
				<th>小区名称</th>
				<th>会员姓名</th>
				<th>配送员姓名</th>
				<th>配送状态</th>
				<th>送达时间</th>
				<th>修改时间</th>
				<%--<th>备注信息</th>--%>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deliveryPlan">
			<tr>
				<td><a href="${ctx}/xiaodian/manage/merchant/delivery/plan/form?id=${deliveryPlan.id}">
					<fmt:formatDate value="${deliveryPlan.theDay}" pattern="yyyy-MM-dd"/>
				</a></td>
				<%--<td>--%>
					<%--${deliveryPlan.merchantName}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${deliveryPlan.orderId}--%>
				<%--</td>--%>
				<td>
					${deliveryPlan.shopName}
				</td>
				<td>
					${deliveryPlan.goodsName}
				</td>
				<td>
					${deliveryPlan.goodsNum}
				</td>
                <td>
                    ${deliveryPlan.giftName}
                </td>
				<td>
					${deliveryPlan.communityName}
				</td>
				<td>
					${deliveryPlan.memberName}
				</td>
				<td>
					${deliveryPlan.deliveryManName}
				</td>
				<td>
					${fns:getDictLabel(deliveryPlan.status, 'delivery_plan_status', '')}
				</td>
                <td>
                    <fmt:formatDate value="${deliveryPlan.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
				<td>
					<fmt:formatDate value="${deliveryPlan.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${deliveryPlan.remarks}--%>
				<%--</td>--%>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><td>
    				<a href="${ctx}/xiaodian/manage/merchant/delivery/plan/form?id=${deliveryPlan.id}">查看详情</a>
    				<%--<a href="${ctx}/xiaodian/manage/merchant/delivery/plan/copy?id=${deliveryPlan.id}">复制</a>--%>
					<%--<a href="${ctx}/xiaodian/manage/merchant/delivery/plan/delete?id=${deliveryPlan.id}" onclick="return confirmx('确认要删除该配送计划吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>