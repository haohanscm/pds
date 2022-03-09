<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>片区管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();

            var treeDictType = {
                wxop: "00",
                area: "01",
            };

            // 地址
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
            }else {
                L1.prepend($("<option>").attr({"value": ""}).text(""));
                L1.select2("val","");
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

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function toReset() {
            $("#name").val("");
            $("#province").prepend($("<option>").attr({"value": ""}).text(""));
            $("#province").select2("val","");
            $("#city").prepend($("<option>").attr({"value": ""}).text(""));
            $("#city").select2("val","");
            $("#region").prepend($("<option>").attr({"value": ""}).text(""));
            $("#region").select2("val","");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/manage/merchant/delivery/districtManage/list">片区管理列表</a></li>
		<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><li><a href="${ctx}/xiaodian/manage/merchant/delivery/districtManage/form">片区管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="districtManage" action="${ctx}/xiaodian/manage/merchant/delivery/districtManage/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>片区名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-small" placeholder="可模糊查询"/>
			</li>
			<li><label>省：</label>
				<form:select path="province" class="input-small">
                    <form:option value="${districtManage.province}" label=""/>
				</form:select>
			</li>
			<li><label>市：</label>
				<form:select path="city" class="input-small">
                    <form:option value="${districtManage.city}" label=""/>
				</form:select>
			</li>
			<li><label>区县：</label>
				<form:select path="region" class="input-small">
                    <form:option value="${districtManage.region}" label=""/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th>主键</th>--%>
				<th>片区名称</th>
				<th>商家</th>
				<th>省</th>
				<th>市</th>
				<th>区县</th>
				<th>拥有小区</th>
				<th>小区数量</th>
				<th>住户数量</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="districtManage">
			<tr>
				<%--<td>--%>
					<%--${districtManage.id}--%>
				<%--</td>--%>
				<td><a href="${ctx}/xiaodian/manage/merchant/delivery/districtManage/form?id=${districtManage.id}">
					${districtManage.name}
				</a></td>
				<td>
					${districtManage.merchantName}
				</td>
				<td>
					${fns:getTreeDictName("01", districtManage.province)}
				</td>
				<td>
					${fns:getTreeDictName("01", districtManage.city)}
				</td>
				<td>
					${fns:getTreeDictName("01", districtManage.region)}
				</td>
				<td>
					${districtManage.communityNames}
				</td>
                <td>
                    ${districtManage.communityNum}
                </td>
				<td>
					${districtManage.residents}
				</td>
				<td>
					<fmt:formatDate value="${districtManage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${districtManage.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:manage:merchant:delivery:edit"><td>
    				<a href="${ctx}/xiaodian/manage/merchant/delivery/districtManage/form?id=${districtManage.id}">修改</a>
					<a href="${ctx}/xiaodian/manage/merchant/delivery/districtManage/delete?id=${districtManage.id}" onclick="return confirmx('确认要删除该片区管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>