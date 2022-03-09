<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配送信息管理</title>
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
                wxop: "00",
                area: "01",
            };

            jQuery.ajax({
                url: '${ctx}/xiaodian/common/treeDict/fetchChildrens',
                type: 'POST',
                dataType: "json",
                data: {
                    type: treeDictType.wxop,
                },
                success: function (result) {
                    if (result.code == 200) {
                        var _data = result.data;
                        _data = $.parseJSON(_data);
                        opData = transData(_data, 'id', 'parentId', 'children');
                        // console.log(opData);
                        initForm(opData, L1, L2, L3);
                        selectByTree(opData, L1, L2, L3);

                        // console.log(L1.find("option[value='" + L1.val() + "']"));
                        // console.log(L2.find("option[value='" + L2.val() + "']"));
                        // console.log(L3.find("option[value='" + L3.val() + "']"));
                    }
                }
            });


            //经营地址归属地二级联
            var area_prov = $("#province");
            var area_city = $("#city");
            var area = $("#region");
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
//            alert(L1.val() +"---"+L2.val()+"----"+L3.val());
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
                        $('#provinceName').val(entity.name);
                        L1Entity = entity;
                        // 加入二级选项
                        $('#cityName').val(entity.children[0].name);
                        $.each(entity.children, function (n1, entity1) {
                            // console.log(entity1)
                            L2.append($("<option>").attr({"value": entity1.code}).text(entity1.name));
                        });
                        // 监听二级变动
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
                            $('#cityName').val(entity2.name);
                            // 加入三级选项
                            // $('#regionName').val(entity2.children[0].name)
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

            L3.change(function () {
                var L3Selected = L3.find("option:selected");
                console.log(L3Selected[0]);
                $('#regionName').val(L3Selected[0].text);
                return false;
            })

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/delivery/deliveryAddress/">配送信息列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/delivery/deliveryAddress/form?id=${deliveryAddress.id}">配送信息<shiro:hasPermission name="xiaodian:delivery:deliveryAddress:edit">${not empty deliveryAddress.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:delivery:deliveryAddress:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deliveryAddress" action="${ctx}/xiaodian/delivery/deliveryAddress/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select path="merchantId" class="input-xlarge">
					<form:option value="" label="" />
					<form:options items="${merchantList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通行证id：</label>
			<div class="controls">
				<form:input path="uuid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人：</label>
			<div class="controls">
				<form:input path="receiver" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人手机：</label>
			<div class="controls">
				<form:input path="receiverMobile" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份：</label>
			<div class="controls">
				<form:select id="province" path="province" class="input-xlarge">
					<form:option value="${deliveryAddress.province}" label=""/>
				</form:select>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">省份名称：</label>
			<div class="controls">
				<form:input id="provinceName" path="provinceName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市：</label>
			<div class="controls">
				<form:select id="city" path="city" class="input-xlarge">
					<form:option value="${deliveryAddress.city}" label=""/>
				</form:select>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">城市名称：</label>
			<div class="controls">
				<form:input id="cityName" path="cityName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地区：</label>
			<div class="controls">
				<form:select id="region" path="region" class="input-xlarge">
					<form:option value="${deliveryAddress.region}" label=""/>
				</form:select>
			</div>
		</div>
		<div class="control-group" style="display: none">
			<label class="control-label">区县名称：</label>
			<div class="controls">
				<form:input id="regionName" path="regionName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">街道：</label>
			<div class="controls">
				<form:input path="street" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">片区ID：</label>
			<div class="controls">
				<form:input path="districtArea" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">片区：</label>
			<div class="controls">
				<form:input path="districtAreaName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">收货小区ID：</label>
			<div class="controls">
				<form:input path="communityId" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小区名称：</label>
			<div class="controls">
				<form:input path="communityName" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">楼栋id：</label>
			<div class="controls">
				<form:input path="buildingId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补充信息：</label>
			<div class="controls">
				<form:input path="extAddress" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否默认：</label>
			<div class="controls">
				<form:select path="isDefault" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签：</label>
			<div class="controls">
				<form:input path="tag" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">即速应用ID：</label>
			<div class="controls">
				<form:input path="jsAppId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">即速地址ID：</label>
			<div class="controls">
				<form:input path="jsAddressId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址：</label>
			<div class="controls">
				<form:input path="receiveAddress" readonly="true" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:delivery:deliveryAddress:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

</body>
</html>