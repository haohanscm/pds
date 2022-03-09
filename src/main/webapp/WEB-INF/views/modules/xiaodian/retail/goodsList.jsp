<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<%--详情模态框--%>
	<style>
		.goods-image, .goods-detail, .goods-rules {
			float: left;
		}
		.goods-detail, .goods-rules {
			margin-left: 10px;
		}
		.goods-image>img{
			width:100px;
			height: 100px;
			margin-top: 20px;
		}
		.goods-detail {
			float: left;
		}
		.modal-body dd{
			padding: 5px 20px;
		}
		.goods-image {
			margin-left: 20px;
		}
		.goods-image>image{
			margin-top: 20px;
		}
		.modal {
			width: 800px;
			margin-left: -400px;
		}
	</style>
	<script type="text/javascript">
		var deliveryTypeDict = ${fns:getDictListJson('delivery_type')}
		var deliveryPlanTypeDict = ${fns:getDictListJson('delivery_plan_type')}

		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
            $("#syncGoodsData").click(function () {
                // 不允许连续点击
                $("#syncGoodsData").attr("disabled","disabled");
                var shopId = $("#syncShopId").val();
                if(null == shopId || '' == shopId){
                    alertx("没有填写shopId");
                    $("#syncGoodsData").removeAttr("disabled");
                    return ;
                }
                top.$.jBox.confirm('该功能从即速商品库同步商品信息到零售商品库，会覆盖当前数据，是否确认？','系统提示',function(v,h,f){
                    if(v=='ok'){
                        loading("信息同步中,请稍后!");
                        jQuery.ajax({
                            url: '${ctx}/xiaodian/retail/goods/syncGoodsData',
                            type: 'POST',
                            dataType: "json",
                            data: {
                                shopId: shopId,
                            },
                            success: function (result) {
                                $("#syncGoodsData").removeAttr("disabled");
                                closeLoading();
                                alertx(result.msg);
                                window.location.reload();
                            }
                        });
                    }
                });
            })
            $("#btnImport").click(function(){
                $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
            });
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        var url = $("#searchForm").attr("action");
                        var flag = $("#flag").val() == 0 ? "false" : "true";
                        $("#searchForm").attr("action", "${ctx}/xiaodian/retail/goods/export/" + flag);
                        $("#searchForm").submit();
                        $("#searchForm").attr("action",url);
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });

            // 同步商品至即速
            $("#syncJisuSumbit").click(function () {
                // 不允许连续点击
                $("#syncJisuSumbit").attr("disabled","disabled");
                var shopId = $("#syncShopId").val();
                if(!(shopId)){
                    alertx("没有选择店铺");
                    $("#syncJisuSumbit").removeAttr("disabled");
                    return ;
                }
                top.$.jBox.confirm('同步所选店铺的所有商品至即速应用,需等待一段时间,是否确认？','系统提示',function(v,h,f){
                    if(v=='ok'){
                        loading("系统操作中,请稍后!");
                        jQuery.ajax({
                            url: '${ctx}//xiaodian/retail/goods/syncJisuGoods',
                            type: 'POST',
                            dataType: "json",
                            data: {
                                shopId: shopId
                            },
                            success: function (result) {
                                $("#syncJisuSumbit").removeAttr("disabled");
                                closeLoading();
                                alertx(result.msg);
                                // window.location.reload();
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
        function toReset() {
            $("#goodsName").val("");
            $("#goodsSn").val("");
            $("#shopId").val("");
            $("#shopName").val("");
            $("#merchantName").val("");
            $("#goodsCategoryId").val("");
            $("#goodsCategoryName").val("");
            $("#storage").val("");
            $("#isMarketable").val("").trigger("change");
        }
        $('#moreOperations').collapse({
            toggle: false
        })
		function openGoodsModal(goodsId) {
            $.getJSON('${ctx}/xiaodian/retail/goods/fetchGoodsInfoExt', {goodsId:goodsId},function(data) {
                var result = data
				var serviceSelection, deliveryRules = {}, saleRules, goodsModel
				if (result.serviceSelection > 0) {
					serviceSelection = result.serviceSelectionList.map(function(item) {
						return item.serviceDetail
					}).join(', ')
				} else {
                    serviceSelection = '无'
				}

				if (result.deliveryRule > 0) {
                    // console.log(result, result.deliveryType)
                    deliveryRules["deliveryType"] = result.deliveryType.split(',').map(function(item) {
                        var result = ''
                        deliveryTypeDict.map(function(i) {
                            if (i.value == item ) {
                                result = i.label
							}
						})
						return result
					}).join(', ')

                    deliveryRules["deliveryPlanType"] = result.deliveryPlanType.split(',').map(function(item) {
                        var result = ''
                        deliveryPlanTypeDict.map(function(i) {
                            if (i.value == item ) {
                                result = i.label
                            }
                        })
                        return result
                    }).join(', ')
				}
				if (goodsModel > 0) {
                    goodsModel = result.goodsModelList.map(function (item) {
                        return item.modelName + '(¥' + item.modelPrice + ')'
                    }).join(', ')
                }


				// console.log(serviceSelection)
                var info = '<dd>商品唯一编号： ' +  (result.goodsSn || '') + '</dd>' +
						      '<dd>店铺： ' + (result.shopName || '') + '</dd>' +
						      '<dd>商品分类： ' + (result.categoryName || '') + '</dd>' +
						      '<dd>价格： ' + (result.marketPrice || '') + '</dd>' +
						      '<dd>单位： ' + (result.unit || '') + '</dd>' +
						      '<dd>库存数量(： ' + (result.storage || '') + '</dd>' +
						      '<dd>是否上架： ' + (result.isMarketable ? '是' : '否') + '</dd>'

                var rules = '<dd>服务选项： ' +  serviceSelection + '</dd>' +
						      '<dd>规格： ' + (goodsModel || '无') + '</dd>' +
						      '<dd>赠品： ' + (result.giftName || '无') + '</dd>' +
						      '<dd>支持配送方式： ' + (deliveryRules.deliveryType || '无') + '</dd>' +
						      '<dd>配送类型： ' + (deliveryRules.deliveryPlanType  || '无') + '</dd>' +
						      '<dd>限购数量： ' + (result.limitBuyTimes || '无') + '</dd>' +
						      '<dd>起购数量： ' + (result.minSaleNum || '无') + '</dd>'

				$('#goodsModalLabel').text(result.goodsName)
				$('#goodsImage').attr('src', result.thumbUrl)
				$('#goodsInfo>dl').html(info)
				$('#goodsRules>dl').html(rules)
				$('#modals').modal('show')
            })

		}
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/xiaodian/retail/goods/import" method="post" enctype="multipart/form-data"
			  class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/xiaodian/retail/goods/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/retail/goods/">商品列表</a></li>
		<shiro:hasPermission name="xiaodian:retail:goods:edit"><li><a href="${ctx}/xiaodian/retail/goods/form">商品添加</a></li></shiro:hasPermission>
	</ul>

	<%--<div style="text-align: right;padding: 10px 20px;">--%>
		<%--<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseOperations" aria-expanded="false" aria-controls="collapseOperations">更多操作</button>--%>
	<%--</div>--%>

	<%--<div class="collapse" id="collapseOperations">--%>
	<div >
		<form:form id="searchForm" modelAttribute="goods" action="${ctx}/xiaodian/retail/goods/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>商品编号：</label>
				<form:input path="goodsSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>通用编号：</label>
				<form:input path="generalSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺ID：</label>
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺名称：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>商家名称：</label>
                <form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
            </li>
            <li><label>商品分类：</label>
                <sys:treeselect id="goodsCategory" name="goodsCategoryId" value="${goods.goodsCategoryId}" labelName="categoryName"
                                labelValue="${goods.categoryName}"
                                title="数据分类" url="/xiaodian/retail/goodsCategory/treeData?limitMerchantId=${goods.merchantId}" cssClass="" allowClear="true"
                                notAllowSelectParent="true"/>
            </li>
            <li><label>库存数量：</label>
                <form:input path="storage" htmlEscape="false" maxlength="64" class="input-small"/>
            </li>
            <li><label>是否上架：</label>
                <form:select path="isMarketable" class="input-small">
                    <form:option value="" label="" />
                    <form:options items="${fns:getDictList('yes_no')}" itemValue="value" itemLabel="label" />
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
        <ul class="ul-form">
            <li class="btns">
                按分页结果导出：
                <select id="flag" class="input-small">
                    <option value="1">是</option>
                    <option value="0">否</option>
                </select>
                <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                <input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
            </li>
        </ul>
	</form:form>
    <c:if test="${not empty jisuAppList}">
        <ul class="ul-form breadcrumb">
            <li class="btns">
                店铺：
                <select id="syncShopId" class="input-large">
                    <option value=""></option>
                    <c:forEach items="${jisuAppList}" var="shop">
                        <option value="${shop.id}">${shop.name}</option>
                    </c:forEach>
                </select>
            </li>
            <li class="btns"><input id="syncGoodsData" class="btn btn-primary" type="button" value="从即速商品库获取店铺商品"/></li>
            <li class="btns"><input id="syncJisuSumbit" class="btn btn-primary" type="button" value="商品同步至即速"/></li>
        </ul>
    </c:if>
	</div>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-hover">
		<thead>
			<tr>
				<th>序号</th>
				<th style="width: 200px;overflow: hidden;">商品名称</th>
				<th>商品唯一编号</th>
				<th style="width: 100px;">店铺</th>
				<th style="width: 150px;">商家</th>
				<%--<th>商家</th>--%>
				<th style="width: 150px;">商品分类</th>
				<th>价格</th>
				<th>单位</th>
				<th>库存数量</th>
				<th>是否上架</th>
				<th>排序</th>
				<th>第三方编号</th>
				<th>通用编号</th>
				<%--<th>启用售卖规则</th>--%>
				<%--<th>启用服务选项</th>--%>
				<%--<th>启用配送规则</th>--%>
				<%--<th>启用赠品</th>--%>
				<%--<th>启用规格</th>--%>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:retail:goods:edit"><th style="width: 150px">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goods" varStatus="goodsStatus">
			<tr>
                <td>
                        ${goodsStatus.count}
                </td>
				<%--<td><a href="${ctx}/xiaodian/retail/goods/form?id=${goods.id}">--%>
				<td><a data-toggle="modal" data-target="#goodsModal" onclick="openGoodsModal('${goods.id}')">
                     ${goods.goodsName}
                </a></td>
				<td>
					${goods.goodsSn}
				</td>
                <td><a href="${ctx}/xiaodian/retail/goods/list?shopId=${goods.shopId}">
                    ${empty goods.shopName ? goods.shopId : goods.shopName}
				</a></td>
                <td><a href="${ctx}/xiaodian/retail/goods/list?merchantId=${goods.merchantId}">
                        ${empty goods.merchantName ? goods.merchantId : goods.merchantName}
                </a></td>
                <%--<td>--%>
                    <%--${empty goods.merchantName ? goods.merchantId : goods.merchantName}--%>
                <%--</td>--%>
                <td style="max-width: 200px;overflow: hidden;">
					${goods.categoryName}
                </td>
				<td>
					${goods.marketPrice}
				</td>
				<td>
					${goods.unit}
				</td>
				<td>
					${goods.storage}
				</td>
				<td>
					${fns:getDictLabel(goods.isMarketable, 'yes_no', '')}
				</td>
                <td>
                        ${goods.sort}
                </td>
				<td>
						${goods.thirdGoodsSn}
				</td>
                    <td>
						${goods.generalSn}
				</td>
				<%--<td>--%>
					<%--${fns:getDictLabel(goods.saleRule, 'yes_no', '')}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(goods.serviceSelection, 'yes_no', '')}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(goods.deliveryRule, 'yes_no', '')}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(goods.goodsGift, 'yes_no', '')}--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--${fns:getDictLabel(goods.goodsModel, 'yes_no', '')}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${goods.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:retail:goods:edit"><td>
    				<a href="${ctx}/xiaodian/retail/goods/form?id=${goods.id}">修改</a>
					<a href="${ctx}/xiaodian/retail/goods/delete?id=${goods.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
                    <a href="${ctx}/xiaodian/retail/goods/copy?id=${goods.id}">复制</a>
                    <%--<a href="${ctx}/xiaodian/retail/goodsPriceRule/form?shopId=${goods.shopId}&merchantId=${goods.shopId}&goodsId=${goods.id}">添加价格</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>

	</table>
	<div class="modal hide fade" id="goodsModal" role="dialog" aria-labelledby="goodsModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="goodsModalLabel">${goods.goodsName}</h4>
				</div>
				<div class="modal-body">
					<div class="goods-image col-md-4 col-xs-4">
						<img id="goodsImage" src=""/>
					</div>
					<div id="goodsInfo" class="goods-detail col-md-4 col-xs-4">
						<dl class="list-inline"></dl>
					</div>
					<div id="goodsRules" class="goods-rules col-md-4 col-xs-4">
						<dl class="list-inline"></dl>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>