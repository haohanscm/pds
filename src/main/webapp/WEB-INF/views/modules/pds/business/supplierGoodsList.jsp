<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商货物管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();

            // 平台商家所属供应商
            $("#select-pmId").change(function () {
                var pmId = $("#select-pmId").val();
                jQuery.ajax({
                    url: '${ctx}/pds/business/pdsSupplier/supplierList',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                    },
                    success: function (result) {
                        var supplier = $("#select-supplierId");
                        if (result.code == 200) {
                            var _data = result.ext;
                            supplier.empty();
                            $.each(_data, function (n, entity) {
                                supplier.append($("<option>").attr({"value": entity.id}).text(entity.supplierName));
                            });
                            supplier.change();
                        }else{
                            supplier.empty();
                            supplier.append($("<option>").attr({"value": ""}).text(""));
                            supplier.change();
                            alertx("商家没有供应商");
                        }
                    }
                });
            });

            // 模态框提交
            $("#relationBtnSubmit").click(function () {
                // 不允许连续点击
                $("#relationBtnSubmit").attr("disabled","disabled");
                var pmId = $("#select-pmId").val();
                var supplierId = $("#select-supplierId").val();
                if(!(supplierId && pmId)){
                    alertx("没有选择平台商家/供应商");
                    $("#relationBtnSubmit").removeAttr("disabled");
                    return ;
                }
                top.$.jBox.confirm('关联平台商家和供应商商家的店铺商品，是否确认？','系统提示',function(v,h,f){
                    if(v=='ok'){
                        loading("系统操作中,请稍后!");
                        jQuery.ajax({
                            url: '${ctx}/pds/business/supplierGoods/relationGoodsBatch',
                            type: 'POST',
                            dataType: "json",
                            data: {
                                pmId: pmId,
                                supplierId: supplierId
                            },
                            success: function (result) {
                                $("#relationBtnSubmit").removeAttr("disabled");
                                closeLoading();
                                alertx(result.msg);
                                window.location.reload();
                            }
                        });
                    }
                });
            });
			
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
		<li class="active"><a href="${ctx}/pds/business/supplierGoods/">供应商货物列表</a></li>
		<shiro:hasPermission name="pds:business:supplierGoods:edit"><li><a href="${ctx}/pds/business/supplierGoods/form">供应商货物添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="pds:business:supplierGoods:edit"><li><a href="${ctx}/pds/business/supplierGoods/relation">平台商家和供应商商品关联管理</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplierGoods" action="${ctx}/pds/business/supplierGoods/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>平台商家：</label>
                <form:select path="pmId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${pmList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>供应商：</label>
                <form:select path="supplierId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${supplierList}" itemLabel="supplierName" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label style="width: 100px">供应商商家：</label>
                <form:select path="supplierMerchantId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${supplierMerchantList}" itemLabel="merchantName" itemValue="merchantId" htmlEscape="false"/>
                </form:select>
			</li>
            <li><label>是否启用：</label>
                <form:select path="status" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li><label>商品id(spu)：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width: 120px">商品规格id(sku)：</label>
				<form:input path="goodsModelId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
            <li><label style="width: 120px">供应商商品id(spu)：</label>
                <form:input path="supplierGoodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
            </li>
			<li><label style="width: 150px">供应商商品规格id(sku)：</label>
				<form:input path="supplierModelId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width: 120px">商品名称(spu)：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label style="width: 120px">商品规格名称(sku)：</label>
				<form:input path="goodsModelName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label style="width: 150px">供应商商品名称(spu)：</label>
                <form:input path="supplierGoodsName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
            </li>
			<li><label style="width: 180px">供应商商品规格名称(sku)：</label>
				<form:input path="supplierModelName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input id="btnSummary" class="btn btn-primary" data-toggle="modal" data-target="#relationGoodsBatch" type="button" value="批量商品关联" onclick=""/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
        <div class="modal hide fade" id="relationGoodsBatch" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">关联平台商家和供应商的商品</h4>
                    </div>
                    <div class="modal-body">
                        <div class="center">
                            <div id="pmIdDiv" class="center">
                                <b>平台商家&nbsp;&nbsp;&nbsp;</b>
                                <select id="select-pmId" name="pmId" style="width: 180px;">
                                    <option></option>
                                    <c:forEach items="${pmList}" var="pm">
                                        <option value="${pm.id}">${pm.merchantName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div id="supplierDiv" class="center">
                                <hr>
                                <b>供应商</b>
                                <select id="select-supplierId" name="supplierId" style="width: 180px;">
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="relationBtnSubmit"  type="button" class="btn btn-warning">确认</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>供应商</th>
				<th>平台商家</th>
				<th>商品(spu)</th>
				<th>商品规格(sku)</th>
				<th>供应商商家</th>
				<th>供应商商品(spu)</th>
				<th>供应商商品规格(sku)</th>
				<th>是否启用</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pds:business:supplierGoods:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplierGoods" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
				<td><a href="${ctx}/pds/business/supplierGoods/form?id=${supplierGoods.id}">
					${supplierGoods.supplierName}
				</a></td>
				<td>
					${supplierGoods.pmName}
				</td>
				<td>
					${supplierGoods.goodsName}
				</td>
				<td>
					${supplierGoods.goodsModelName}
				</td>
				<td>
					${supplierGoods.supplierMerchantName}
				</td>
                <td>
                        ${supplierGoods.supplierGoodsName}
                </td>
				<td>
					${supplierGoods.supplierModelName}
				</td>
				<td>
					${fns:getDictLabel(supplierGoods.status, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${supplierGoods.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${supplierGoods.remarks}
				</td>
				<shiro:hasPermission name="pds:business:supplierGoods:edit"><td>
    				<a href="${ctx}/pds/business/supplierGoods/form?id=${supplierGoods.id}">修改</a>
					<a href="${ctx}/pds/business/supplierGoods/delete?id=${supplierGoods.id}" onclick="return confirmx('确认要删除该供应商货物吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
            <tr>
                <td></td>
                <td>
                        ${supplierGoods.supplierId}
                </td>
                <td>
                        ${supplierGoods.pmId}
                </td>
                <td>
                        ${supplierGoods.goodsId}
                </td>
                <td>
                        ${supplierGoods.goodsModelId}
                </td>
                <td>
                        ${supplierGoods.supplierMerchantId}
                </td>
                <td>
                        ${supplierGoods.supplierGoodsId}
                </td>
                <td>
                        ${supplierGoods.supplierModelId}
                </td>
                <td></td><td></td><td></td><td></td>
            </tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>