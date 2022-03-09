<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送计划管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            var treeDictType = {
                // wxop: "00",
                area: "01"
            };

            //经营地址归属地二级联
            var area_prov = $("#province");
            var area_city = $("#city");
            var area = $("#area");
            var areaDb;

            // 后台查询 并初始化三级联动
            jQuery.ajax({
                url: '${ctx}/xiaodian/common/treeDict/fetchChildrens',
                type: 'POST',
                dataType: "json",
                data: {
                    type: treeDictType.area,
                },
                success: function (result) {
                    if (result.code == 200) {
                        var _data = result.data;
                        _data = $.parseJSON(_data);

                        areaDb = transData(_data, 'id', 'parentId', 'children');
                        initForm(areaDb, area_prov, area_city, area);
                        selectByTree(areaDb, area_prov, area_city, area);
                    }
                }
            });

        });

        // 数据层级 转换为数组
        function transData(a, idStr, pidStr, chindrenStr) {
            var r = [], hash = {}, id = idStr, pid = pidStr, children = chindrenStr, i = 0, j = 0, len = a.length;
            for (; i < len; i++) {
                hash[a[i][id]] = a[i];
            }
            for (; j < len; j++) {
                var aVal = a[j], hashVP = hash[aVal[pid]];
                if (hashVP) {
                    !hashVP[children] && (hashVP[children] = []);
                    hashVP[children].push(aVal);
                } else {
                    r.push(aVal);
                }
            }
            return r;
        }

        function initForm(data, L1, L2, L3) {
            var L1v = L1.val();
            var L2v = L2.val();
            var L3v;
            L1.empty();
            L2.empty();
            if (null != L3) {
                L3v = L3.val();
                L3.empty();
            }
            //初始化菜单 从地区字典(data[0])取值 若有地址信息则选中
            $.each(data[0].children, function (n, entity) {
                L1.append($("<option>").attr({"value": entity.code}).text(entity.name));
                if (L1v == entity.code) {
                    L1.find("option[value='" + L1v + "']").attr("selected", true);
                    $.each(entity.children, function (n1, entity1) {
                        L2.append($("<option>").attr({"value": entity1.code}).text(entity1.name));
                        if (L2v == entity1.code) {
                            L2.find("option[value='" + L2v + "']").attr("selected", true);
                            if (null != L3 && null != entity1.children) {
                                $.each(entity1.children, function (n2, entity2) {
                                    L3.append($("<option>").attr({"value": entity2.code}).text(entity2.name));
                                    if (L3v == entity2.code) {
                                        L3.find("option[value='" + L3v + "']").attr("selected", true);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            // 初始无值
            if(L1v!=""){
                L1.change();
                L2.change();
                if (null != L3) {
                    L3.change();
                }
            }
        }

        // 三级联动
        function selectByTree(data, L1, L2, L3) {
            // 地区字典对象
            var L0Entity = data[0].children;
            // 省级对象
            var L1Entity;

            if(L1.find("option:selected").text() != ""){
                L1Entity = L0Entity.find(function (currentEntity) {
                    return currentEntity.name == L1.find("option:selected").text();
                })
            }

            //一级变动
            L1.change(function () {
                //清空二三级
                L2.empty();
                if (null != L3) {
                    L3.empty();
                }
                $.each(L0Entity, function (n, entity) {
                    if (L1.find("option:selected").text() == entity.name) {
                        L1Entity = entity;
                        // 加入二级选项
                        $.each(entity.children, function (n1, entity1) {
                            L2.append($("<option>").attr({"value": entity1.code}).text(entity1.name));
                        });
                        // 触发二级变动
                        L2.change();
                        // 一级完成
                        return false;
                    }
                })
            });

            // 二级变动
            L2.change(function () {
                if (null != L3) {
                    L3.empty();
                    $.each(L1Entity.children, function (n2, entity2) {
                        if (L2.find("option:selected").text() == entity2.name) {
                            // 加入三级选项
                            $.each(entity2.children, function (n3, entity3) {
                                L3.append($("<option>").attr({"value": entity3.code}).text(entity3.name));
                            })
                            // 触发三级
                            L3.change();
                            // 二级完成
                            return false;
                        }
                    })
                }
            })

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/manage/merchant/delivery/plan/list">配送计划列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/manage/merchant/delivery/plan/form?id=${deliveryPlan.id}">配送计划<shiro:hasPermission name="xiaodian:delivery:deliveryPlan:edit">${not empty deliveryPlan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:manage:merchant:delivery:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deliveryPlan" action="${ctx}/xiaodian/manage/merchant/delivery/delivery/plan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">支付订单ID：</label>
			<div class="controls">
				<form:input path="orderId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">订单备注：</label>
            <div class="controls">
                <form:input path="orderRemark" htmlEscape="false" maxlength="200" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">配送日期：</label>
			<div class="controls">
				<input name="theDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${deliveryPlan.theDay}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">商家id：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家名称：</label>
			<div class="controls">
				<form:input path="merchantName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group"  style="display: none">
			<label class="control-label">店铺id：</label>
			<div class="controls">
				<form:input path="shopId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店铺名称：</label>
			<div class="controls">
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付状态：</label>
			<div class="controls">
				<form:select path="payStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pay_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group"  style="display: none">
			<label class="control-label">配送订单id：</label>
			<div class="controls">
				<form:input path="deliveryOrderId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group"  style="display: none">
			<label class="control-label">配送商品id：</label>
			<div class="controls">
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送商品数量：</label>
			<div class="controls">
				<form:input path="goodsNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
        <div class="control-group">
			<label class="control-label">配送商品图片：</label>
			<div class="controls">
                <form:hidden id="goodsUrl" path="goodsUrl" htmlEscape="false" class="input-xlarge"/>
                <sys:ckfinder input="goodsUrl" type="images" uploadPath="/retail/goods" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送商品单位：</label>
			<div class="controls">
				<form:input path="goodsUnit" htmlEscape="false" maxlength="5" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送商品规格：</label>
			<div class="controls">
				<form:input path="goodsInfo" htmlEscape="false" maxlength="5" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展配送信息：</label>
			<div class="controls">
				<form:input path="extDeliveryInfo" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预约时间：</label>
			<div class="controls">
				<input name="reserveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${deliveryPlan.reserveTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送地址：</label>
			<div class="controls">
                <form:textarea path="address" htmlEscape="false" rows="3" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份：</label>
			<div class="controls">
				<form:select id="province" path="province" class="input-xlarge">
					<form:option value="${deliveryPlan.province}" label=""/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市：</label>
			<div class="controls">
				<form:select id="city" path="city" class="input-xlarge">
					<form:option value="${deliveryPlan.city}" label=""/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区县：</label>
			<div class="controls">
				<form:select id="area" path="area" class="input-xlarge">
					<form:option value="${deliveryPlan.area}" label=""/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">街道：</label>
			<div class="controls">
                <form:input path="street" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group"  style="display: none">
			<label class="control-label">所属片区：</label>
			<div class="controls">
				<form:input path="districtArea" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group"  style="display: none">
			<label class="control-label">配送小区id：</label>
			<div class="controls">
				<form:input path="communityId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小区名称：</label>
			<div class="controls">
				<form:input path="communityName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">楼栋：</label>
			<div class="controls">
				<form:input path="buildingsNum" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">层数：</label>
			<div class="controls">
				<form:input path="floor" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">房号：</label>
			<div class="controls">
				<form:input path="houseNum" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group"  style="display: none">
			<label class="control-label">会员id：</label>
			<div class="controls">
				<form:input path="memberId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员姓名：</label>
			<div class="controls">
				<form:input path="memberName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员联系方式：</label>
			<div class="controls">
				<form:input path="memberContact" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送达时间：</label>
			<div class="controls">
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${deliveryPlan.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('delivery_plan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group"  style="display: none">
			<label class="control-label">配送员id：</label>
			<div class="controls">
				<form:input path="deliveryManId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送员姓名：</label>
			<div class="controls">
				<form:input path="deliveryManName" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配送员联系方式：</label>
			<div class="controls">
				<form:input path="deliverManTel" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">赠品名称：</label>
            <div class="controls">
                <form:input path="giftName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
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
                <sys:ckfinder input="giftUrl" type="images" uploadPath="/retail/goods" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">赠品单位：</label>
            <div class="controls">
                <form:input path="giftUnit" htmlEscape="false" maxlength="5" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">赠品规格：</label>
            <div class="controls">
                <form:input path="giftInfo" htmlEscape="false" maxlength="5" class="input-xlarge"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">服务内容：</label>
			<div class="controls">
				<form:input path="serviceContent" htmlEscape="false" maxlength="5" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:delivery:deliveryPlan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>