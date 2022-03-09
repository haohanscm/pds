<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

        var goodsModelList = ${fns:toJson(goodsInfoExt.goodsModelList)};
        var serviceSelectionList = ${fns:toJson(goodsInfoExt.serviceSelectionList)};


        if (!Array.isArray(goodsModelList)) {
            goodsModelList = []
        }
        if (!Array.isArray(serviceSelectionList)) {
            serviceSelectionList = []
        }
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
            // 公共函数
            // 渲染字符串模版, 挂载到String函数原型链上
            String.prototype.render = function (context) {
                return this.replace(/\{\{(.*?)\}\}/g, function(match, key) {
                    return context[key.trim()];
                })
            };

			$("#inputForm").validate({
                rules: {
                    shopId: "required",
                    goodsCategoryName: "required",
                    goodsName: "required",
                    storage: "digits"
                },
                messages:{
                    goodsCategoryName: "请选择分类"
                },
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				// errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					// $("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				},
			});

            $("#merchantId").change(function () {
                var merchantId = $("#merchantId").find("option:selected").val();
                jQuery.ajax({
                    url: '${ctx}/xiaodian/merchant/shop/fetchShops',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        merchantId: merchantId,
                    },
                    success: function (result) {
                        var shop = $("#shopId");
                        if (result.code == 200) {
                            var _data = result.data;
                            _data = $.parseJSON(_data);
                            if (null == _data || ( 0 == _data.length)) {
                                shop.empty();
                                shop.append($("<option>").attr({"value": ""}).text(""));
                                shop.change();
                                alertx("商家没有店铺");
                                return;
                            }
                            shop.empty();
                            $.each(_data, function (n, entity) {
                                shop.append($("<option>").attr({"value": entity.id}).text(entity.name));
                            });
                            shop.change();
                        }
                    }
                });

            });

            // shopId 变化影响商品分类选择
            $("#shopId").change(function () {
                var oldContent = $("#choiceCategory").children("script").html();
                // 空格为替换的结束标识
                var shopId = $("#shopId").val() || "failed";
                var newContent = oldContent.replace(/limitShopId=\S*/, "limitShopId=" + shopId);
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#choiceCategory").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#goodsCategoryButton, #goodsCategoryName").unbind();
                document.getElementById("choiceCategory").appendChild(script);
            });
            // 初始化
            $("#merchantId").change();
            if ($("#id").val() != "") {
                $(".idDiv").show();
            }
            if($("[name='isMarketable']:checked").val()!= "0"){
                $("[name='isMarketable'][value='1']").attr('checked','true');
            }
            // 附加选项初始化
            if($("[name='saleRule']:checked").val()!= "1"){
                $("[name='saleRule'][value='0']").attr('checked','true');
            }else{
                $(".saleRuleDiv").show();
            }
            if($("[name='serviceSelection']:checked").val()!= "1"){
                $("[name='serviceSelection'][value='0']").attr('checked','true');
            }else{
                $(".serviceSelectionDiv").show();
            }
            if($("[name='deliveryRule']:checked").val()!= "1"){
                $("[name='deliveryRule'][value='0']").attr('checked','true');
            }else{
                $(".deliveryRuleDiv").show();
            }
            if($("[name='goodsGift']:checked").val()!= "1"){
                $("[name='goodsGift'][value='0']").attr('checked','true');
            }else{
                $(".goodsGiftDiv").show();
            }
            if($("[name='goodsModel']:checked").val()!= "1"){
                $("[name='goodsModel'][value='0']").attr('checked','true');
            }else{
                $(".goodsModelDiv").show();
            }

            // 初始化处理选项
            initGoodsModel()
            initGoodsService()
            initDeliveryRules()

            // 折叠变换
            $('#collapseLabel').on('click', function() {
                var status = $('#moreOptions').hasClass('in')
                if (status) {
                    $('#collapseLabel').html('高级选项 <i class="icon-angle-down">')
                } else {
                    $('#collapseLabel').html('收起 <i class="icon-angle-up"></i>')
                }
            })

            /**
             * 配送方式管理
             */
            // 送货上门类型
            $('#deliveryPlanType').on('change', function(e){ handleDeliveryRules(e.val)})

		});
        function initDeliveryRules() {
            var goodsInfoExt = ${fns:toJson(goodsInfoExt)};
            $('#deliveryType').select2('val', goodsInfoExt.deliveryType.split(','))
            $('#deliveryPlanType').select2('val',  goodsInfoExt.deliveryPlanType)

            handleDeliveryRules($('#deliveryPlanType').select2('val'))

            $('#s2id_arriveType').select2('val', goodsInfoExt.arriveType)
            $('#s2id_deliverySchedule').select2('val', null)
        }
        function handleDeliveryRules(val) {
            if (val == '0') {
                $('#arriveTypeDiv').show()
                // 清空数据 并 隐藏
                $('#deliveryScheduleDiv').hide().find('input').each(function() {
                    $(this).val(' ')
                })
                $('#s2id_deliverySchedule').select2('val', null)
                $('#specificDateDiv').hide()
                $('#specificDate').val('')
            }
            if (val == '1') {
                $('#deliveryScheduleDiv').show()
                // 清空数据 并 隐藏
                $('#specificDateDiv').hide()
                $('#arriveTypeDiv').hide()
                $('#s2id_arriveType').select2('val', null)
                $('#specificDate').val('')

            }
            if (val == '2') {
                $('#specificDateDiv').show()

                // 清空数据 并 隐藏
                $('#deliveryScheduleDiv').hide().find('input').each(function() {
                    $(this).val('')
                })
                $('#arriveTypeDiv').hide()
                $('#s2id_arriveType').select2('val', null)
                $('#s2id_deliverySchedule').select2('val', null)
            }
            if (val == -1) {
                $('#deliveryScheduleDiv').find('input').each(function() {
                    $(this).val('')
                })
                $('#specificDate').val('')
                $('#s2id_arriveType').select2('val', null)
                $('#s2id_deliverySchedule').select2('val', null)
                $('#s2id_deliveryPlanType').select2('val', null)
            }
        }
        // 选项启用时变化
        function divChange(div){
            if($("[name='" + div + "']:checked").val()!= "1"){
                $("." + div + "Div").hide();
            }else{
                $("." + div + "Div").show();
            }
        }

        // 初始化处理服务选项
        function initGoodsService() {
            var oldName = ''
            if (!Array.isArray(serviceSelectionList)) {
                serviceSelectionList = []
            }
            serviceSelectionList = serviceSelectionList.map(function(item, index) {
                oldName = item.serviceName
                return {
                    index: index,
                    id: item.id,
                    serviceName: item.serviceName || '',
                    serviceDetail: item.serviceDetail || '',
                    servicePrice: item.servicePrice || '',
                    serviceNum: item.serviceNum || '',
                    serviceSchedule: item.serviceSchedule || '',
                }
            })
            $('#goodsServiceOptions').empty().append(genHtml('goodsServiceTmpl', serviceSelectionList))
            bindChangeInput('#goodsServiceOptions', serviceSelectionList)
            $('#goodsServiceName').val(oldName)
            renderCheckedOption('#goodsServiceOptions')
        }
        // 渲染选项值
        function renderCheckedOption(selecter) {
            $(selecter).find('select').each(function () {
                var value = $(this).attr("data-value")
                $(this).find('option').each(function() {
                    if ($(this).val() == value) {
                        $(this).attr("selected","selected");
                    }
                })
            })
        }
        // 更改服务选项名称
        function inputServiceName() {
            var name = $('#goodsServiceName').val() || ''
            if (Array.isArray(serviceSelectionList)) {
                serviceSelectionList.forEach(function(item, index) {
                    item.serviceName = name
                })
            }
            $('#goodsServiceOptions').empty().append(genHtml('goodsServiceTmpl', serviceSelectionList))
        }
        // 添加服务选项
        function addServiceOption(){
            if (!Array.isArray(serviceSelectionList)) {
                serviceSelectionList = []
            }
            var length = serviceSelectionList.length

            serviceSelectionList.push({
                index: length,
                id: '',
                serviceName: '',
                serviceDetail: '',
                servicePrice: '',
                serviceNum: '',
                serviceSchedule: ''
            })
            inputServiceName()
            renderCheckedOption('#goodsServiceOptions')
        }
        // 删除服务选项
        function delServiceOption(index) {
            serviceSelectionList.splice(index, 1)
            serviceSelectionList = serviceSelectionList.map(function(item, index) {
                return {
                    index: index,
                    id: item.id,
                    serviceName: item.serviceName || '',
                    serviceDetail: item.serviceDetail || '',
                    servicePrice: item.servicePrice || '',
                    serviceNum: item.serviceNum || '',
                    serviceSchedule: item.serviceSchedule || ''
                }
            })
            $('#goodsServiceOptions').empty().append(genHtml('goodsServiceTmpl', serviceSelectionList))
            renderCheckedOption('#goodsServiceOptions')
        }


        // 初始化处理规格选项
        function initGoodsModel() {
            if (!Array.isArray(goodsModelList)) {
                goodsModelList = []
            }
            goodsModelList = goodsModelList.map(function(item, index) {
                return {
                    index: index,
                    id: item.id,
                    modelName: item.modelName || '',
                    modelPrice: item.modelPrice || '',
                    modelStorage: item.modelStorage || '',
                    modelUnit: item.modelUnit || '',
                    modelUrl: item.modelUrl || ''
                }
            })
            bindChangeInput('#goodsModelRows', goodsModelList)
            $('#goodsModelRows').empty().append(genHtml('goodsModelTmpl', goodsModelList))
        }
        // 绑定change事件
        function bindChangeInput(selecter, list) {
            $(selecter).on('change', function(e){
                var path = e.target.name
                var index = path.replace(/[^0-9]/ig,"")
                var name = path.split('.')[1]
                list[index][name] = e.target.value
            })

        }
        // 生成规格的HTMl
        function genHtml(templateId, list){
            var html = ""
            list.forEach(function(item) {
                html +=$('#' + templateId).html().render(item)
            })
            return html
        }
        // 添加规格
        function addModelRow(){
            if (!Array.isArray(goodsModelList)) {
                goodsModelList = []
            }
            var length = goodsModelList.length

            goodsModelList.push({
                index: length,
                id: '',
                modelName: '',
                modelPrice: '',
                modelStorage: '',
                modelUnit: '',
                modelUrl: ''
            })
            $('#goodsModelRows').empty().append(genHtml('goodsModelTmpl', goodsModelList))
        }
        // 删除规格行
        function delModelRow(index) {
            goodsModelList.splice(index, 1)
            goodsModelList = goodsModelList.map(function(item, index) {
                return {
                    index: index,
                    id: item.id,
                    modelName: item.modelName || '',
                    modelPrice: item.modelPrice || '',
                    modelStorage: item.modelStorage || '',
                    modelUnit: item.modelUnit || '',
                    modelUrl: item.modelUrl || ''
                }
            })
            $('#goodsModelRows').empty().append(genHtml('goodsModelTmpl', goodsModelList))
        }
	</script>
    <style>
        .msg {
            color:#FF0000 ;
        }
        .control-group {
            border-bottom: none;
        }
        .more-btn-box {
            text-align: center;
            border-top: 1px solid #EEEEEE;
            margin-bottom: 20px;
        }
        .more-btn-box a {
            display: inline-block;
            width: 80px;
            height: 40px;
        }
        .line {
            border-bottom: 1px solid #EEEEEE;
            margin: 10px 30px 20px;
        }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/manage/merchant/product/list">商品列表</a></li>
		<li class="active">
            <a href="${ctx}/xiaodian/manage/merchant/product/form?id=${goods.id}">商品
                <shiro:hasPermission name="xiaodian:manage:merchant:product:edit">${not empty goods.id?'修改':'添加'}</shiro:hasPermission>
                <shiro:lacksPermission name="xiaodian:manage:merchant:product:edit">查看</shiro:lacksPermission>
            </a>
        </li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsInfoExt" action="${ctx}/xiaodian/manage/merchant/product/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group idDiv" style="display: none">
			<label class="control-label">商品ID：</label>
			<div class="controls">
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
                <form:select id="merchantId" path="merchantId" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <c:forEach items="${merchantList}" var="merchant">
                        <form:option  value="${merchant.id}" label="${merchant.merchantName}" />
                    </c:forEach>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>店铺：</label>
			<div class="controls">
                <form:select path="shopId" class="input-xlarge required">
                    <form:option value="${goodsInfoExt.shopId}" label=""/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>商品分类：</label>
			<div class="controls" id="choiceCategory">
				<sys:treeselect id="goodsCategory" name="goodsCategoryId" value="${goodsInfoExt.goodsCategoryId}" labelName="categoryName"
								labelValue="${goodsInfoExt.categoryName}"
								title="数据分类" url="/xiaodian/retail/goodsCategory/treeData?limitShopId= " cssClass="" allowClear="true"
								notAllowSelectParent="true" checked="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品唯一编号：</label>
			<div class="controls">
				<form:input path="goodsSn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="msg">* </span>商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">图片：</label>
            <div class="controls">
                <form:hidden id="thumbUrl" path="thumbUrl" htmlEscape="false" class="input-xlarge"/>
                <sys:ckfinder input="thumbUrl" type="images" uploadPath="/retail/goods" selectMultiple="false"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">价格：</label>
            <div class="controls">
                <form:input path="marketPrice" htmlEscape="false" maxlength="10" class="input-xlarge number"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">计量单位：</label>
            <div class="controls">
                <form:input path="unit" htmlEscape="false" maxlength="10" class="input-xlarge"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">商品描述：</label>
			<div class="controls">
				<%--<form:textarea path="detailDesc" rows="3" htmlEscape="false" class="input-xxlarge "/>--%>
                <form:textarea path="detailDesc" htmlEscape="false" maxlength="60000" class="input-xlarge"/>
                <sys:ckeditor  replace="detailDesc" uploadPath="/retail/goods/detailDesc" />
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">概要描述：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="simpleDesc" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">商品品牌ID：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="brandId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
        <div class="control-group">
            <div class="line"></div>
            <label class="control-label">是否上架：</label>
            <div class="controls">
                <form:radiobuttons path="isMarketable" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                   itemValue="value" htmlEscape="false" class=""/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">库存数量：</label>
            <div class="controls">
                <form:input path="storage" htmlEscape="false" maxlength="5" class="input-xlarge " onchange="checkNum('storage')"/>&nbsp;&nbsp;&nbsp;
                <span id="storageCheck" class="msg"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注信息：</label>
            <div class="controls">
                <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
            </div>
        </div>
        <div class="options-wrapper">
            <div class="more-btn-box">
                <a id="collapseLabel" data-toggle="collapse" href="#moreOptions" >高级选项 <i class="icon-angle-down"></i>
                </a>
                <a style="display:none" >收起 <i class="icon-angle-up"></i></a>
            </div>
        </div>
        <div class="collapse" id="moreOptions">
            <div class="control-group">
                <label class="control-label">启用售卖规则：</label>
                <div class="controls">
                    <form:radiobuttons path="saleRule" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('saleRule')"/>
                </div>
            </div>
            <div class="saleRuleDiv" style="display:none;">
                <div class="control-group">
                    <label class="control-label">售卖区域：</label>
                    <div class="controls">
                        <form:input path="saleAreas" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">售卖时效：</label>
                    <div class="controls">
                        <form:input path="saleArriveType" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">起售数量：</label>
                    <div class="controls">
                        <form:input path="minSaleNum" htmlEscape="false" maxlength="5" class="input-xlarge digits"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">限制购买次数：</label>
                    <div class="controls">
                        <form:input path="limitBuyTimes" htmlEscape="false" maxlength="5" class="input-xlarge digits"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">配送类型限制：</label>
                    <div class="controls">
                        <form:select path="saleDeliveryType" class="input-xlarge ">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">起售时间：</label>
                    <div class="controls">
                        <input name="beginSaleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.beginSaleDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">结束售卖时间：</label>
                    <div class="controls">
                        <input name="endSaleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.endSaleDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用服务选项：</label>
                <div class="controls">
                    <form:radiobuttons path="serviceSelection" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('serviceSelection')"/>
                </div>
            </div>
            <div class="serviceSelectionDiv" style="display:none;">

                <div class="control-group">
                    <label class="control-label">服务名称：</label>
                    <div class="controls">
                        <input id="goodsServiceName" class="input-xlarge" type="text" onchange="inputServiceName()" value="" required/>
                    </div>
                </div>
                <div class="controls">
                    <table id="goodsServiceTable" class="table table-striped table-bordered table-condensed" style="width: 50%">
                        <thead>
                        <tr id="goodsServiceTitle">
                            <th style="min-width: 135px">服务内容</th>
                            <th style="min-width: 135px">价格</th>
                            <th style="min-width: 135px">次数</th>
                            <th style="min-width: 135px">周期</th>
                            <th style="min-width: 120px">操作</th>
                        </tr>
                        </thead>
                        <script id="goodsServiceTmpl" type="text/x-template">
                            <tr>
                                <td class="hide"><input class="input-small" type="hidden" name="serviceSelectionList[{{index}}].id" value="{{id}}" /></td>
                                <td class="hide"><input class="input-small" type="hidden" name="serviceSelectionList[{{index}}].serviceName" value="{{serviceName}}" required/></td>
                                <td><input class="input-small" type="text" name="serviceSelectionList[{{index}}].serviceDetail" value="{{serviceDetail}}" required/></td>
                                <td><input class="input-small" type="number" name="serviceSelectionList[{{index}}].servicePrice" value="{{servicePrice}}" required/></td>
                                <td><input class="input-small" type="number" name="serviceSelectionList[{{index}}].serviceNum" value="{{serviceNum}}" required/></td>
                                <td>
                                    <select name="serviceSelectionList[{{index}}].serviceSchedule" data-value="{{serviceSchedule}}">
                                        <option value="">请选择</option>
                                        <c:forEach items="${fns:getDictList('delivery_plan_type')}" var="schedule">
                                            <option value="${schedule.value}" >${schedule}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <%--<td><input class="input-small" type="text" name="serviceSelectionList[{{index}}].serviceSchedule" value="{{schedule}}" required/></td>--%>
                                <td><a  onclick="delServiceOption({{index}})" >删除<a/></td>
                            </tr>
                        </script>
                        <tbody id="goodsServiceOptions">
                        </tbody>
                    </table>
                    <input type="button" id="addServiceOptionBtn" value="添加内容选项" class="btn btn-primary" onclick="addServiceOption()" style="margin-bottom: 20px;" />
                </div>
            </div>
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用配送规则：</label>
                <div class="controls">
                    <form:radiobuttons path="deliveryRule" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('deliveryRule')" />
                </div>
            </div>
            <div class="deliveryRuleDiv" style="display:none;">
                <div class="control-group">
                    <label class="control-label">配送方式选项：</label>
                    <div class="controls">
                        <form:select path="deliveryType" class="input-xlarge " id="deliveryType" multiple="multiple" placeholder="选择支持的配送方式">
                            <form:options items="${fns:getDictList('delivery_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                        <span class="msg">*可多选</span>
                    </div>
                </div>
                <div class="control-group" id="deliveryPlanDiv" style="display: block;">
                    <label class="control-label">配送计划类型：</label>
                    <div class="controls">
                        <form:select path="deliveryPlanType" class="input-xlarge" id="deliveryPlanType" required="true" placeholder="请选择配送类型">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('delivery_plan_type')}" itemLabel="label" itemValue="value" onchange="changeDeliveryPlan()" htmlEscape="false"/>
                        </form:select>
                    </div>
                </div>
                <div id="deliveryScheduleDiv" >
                    <div class="control-group">
                        <label class="control-label">配送周期选项：</label>
                        <div class="controls">
                            <form:select path="deliverySchedule" class="input-xlarge " multiple="multiple">
                                <form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                            <span class="msg">*可多选</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">配送总数量：</label>
                        <div class="controls">
                            <form:input path="deliveryTotalNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">每次配送数量：</label>
                        <div class="controls">
                            <form:input path="deliveryNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">起送数量：</label>
                        <div class="controls">
                            <form:input path="minNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">规则描述：</label>
                        <div class="controls">
                            <form:input path="rulesDesc" htmlEscape="false" maxlength="500" class="input-xlarge "/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">第一次配送间隔：</label>
                        <div class="controls">
                            <form:input path="startDayNum" htmlEscape="false" maxlength="20" class="input-xlarge digits"/>
                        </div>
                    </div>
                </div>
                <div id="arriveTypeDiv" style="display: none;">
                    <div class="control-group">
                        <label class="control-label">配送时效：</label>
                        <div class="controls">
                            <form:select path="arriveType" class="input-xlarge ">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('delivery_arrive')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div id="specificDateDiv" style="display: none;">
                    <div class="control-group">
                        <label class="control-label">指定时间：</label>
                        <div class="controls">
                            <input id="specificDate" name="specificDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                   value="<fmt:formatDate value="${goodsInfoExt.specificDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用赠品：</label>
                <div class="controls">
                        <form:radiobuttons path="goodsGift" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                           itemValue="value" htmlEscape="false" class="" onchange="divChange('goodsGift')"/>
                </div>
            </div>
            <div class="goodsGiftDiv" style="display:none;">
                <div class="control-group">
                    <label class="control-label">赠品id：</label>
                    <div class="controls">
                        <form:input path="giftId" htmlEscape="false" maxlength="64" class="input-xlarge" readonly="true"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠品名称：</label>
                    <div class="controls">
                        <form:input path="giftName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠送规则：</label>
                    <div class="controls">
                        <form:input path="giftRule" htmlEscape="false" maxlength="64" class="input-xlarge "/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">起始日期：</label>
                    <div class="controls">
                        <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">结束日期：</label>
                    <div class="controls">
                        <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${goodsInfoExt.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠送周期选项：</label>
                    <div class="controls">
                        <form:select path="giftSchedule" class="input-xlarge " multiple="multiple">
                            <form:options items="${fns:getDictList('delivery_schedule')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                        <span class="msg">*可多选</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠品数量：</label>
                    <div class="controls">
                        <form:input path="giftNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">赠品图片：</label>
                    <div class="controls">
                        <form:hidden id="giftUrl" path="giftUrl" htmlEscape="false" class="input-xlarge"/>
                        <sys:ckfinder input="giftUrl" type="images" uploadPath="/goods/goodsGifts" selectMultiple="false"/>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="line"></div>
                <label class="control-label">启用商品规格：</label>
                <div class="controls">
                    <form:radiobuttons path="goodsModel" items="${fns:getDictList('yes_no')}" itemLabel="label"
                                       itemValue="value" htmlEscape="false" class="" onchange="divChange('goodsModel')"/>
                </div>
            </div>
            <div class="goodsModelDiv" style="display:none;">
                <div class="controls">
                    <div id="goodsModelTable">
                        <table id="goodsModelList" class="table table-striped table-bordered table-condensed" style="width: 50%">
                            <thead>
                                <tr id="goodsModelTitle">
                                    <th style="display: none;">规格Id</th>
                                    <th style="min-width: 135px">规格名称</th>
                                    <th style="min-width: 135px">价格</th>
                                    <th style="min-width: 135px">单位</th>
                                    <th style="min-width: 135px">库存</th>
                                    <th style="min-width: 135px">图片地址</th>
                                    <th style="min-width: 120px">操作</th>
                                </tr>
                            </thead>
                            <script id="goodsModelTmpl" type="text/x-template">
                                <tr>
                                    <td style="display: none;"><input class="input-small" type="text" name="goodsModelList[{{index}}].id" value="{{id}}"/></td>
                                    <td><input class="input-small" type="text" name="goodsModelList[{{index}}].modelName" value="{{modelName}}" required/></td>
                                    <td><input class="input-small" type="number" name="goodsModelList[{{index}}].modelPrice" value="{{modelPrice}}" required/></td>
                                    <td><input class="input-small" type="text" name="goodsModelList[{{index}}].modelUnit" value="{{modelUnit}}" required/></td>
                                    <td><input class="input-small" type="number" name="goodsModelList[{{index}}].modelStorage" value="{{modelStorage}}" required/></td>
                                    <td><input class="input-small" type="text" name="goodsModelList[{{index}}].modelUrl" value="{{modelUrl}}"/></td>
                                    <td><a  onclick="delModelRow({{index}})" >删除<a/></td>
                                </tr>
                            </script>
                            <tbody id="goodsModelRows">
                            </tbody>
                        </table>
                        <input type="button" id="addRowBtn" value="添加规格" class="btn btn-primary" onclick="addModelRow()" style="margin-bottom: 20px;" />
                    </div>
                </div>
            </div>
        </div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">图片组编号：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="photoGroupNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:manage:merchant:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>