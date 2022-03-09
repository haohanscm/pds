<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
        $(document).ready(function() {
            var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
            var data = ${fns:toJson(page.list)}, ids = [], rootIds = [] ;
            var parentIds = [];
            for (var i=0; i<data.length; i++){
                ids.push(data[i].id);
            }
            ids = ',' + ids.join(',') + ',';
            for (var i=0; i<data.length; i++){
                if (ids.indexOf(','+data[i].parentId+',') == -1){
                    if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
                        rootIds.push(data[i].parentId);
                        parentIds.push(data[i].parentId);
                    }
                }else{
                    // 添加父节点
                    if ((','+parentIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
                        parentIds.push(data[i].parentId);
                    }
                }
            }
            parentIds = ',' + parentIds.join(',') + ',';
            for (var i=0; i<rootIds.length; i++){
                addRow("#treeTableList", tpl, data, rootIds[i], true);
            }
            $("#treeTable").treeTable({expandLevel : 5});
            // 对最底层叶子节点处理
            for (var i=0; i<data.length; i++){
                if(parentIds.indexOf(','+data[i].id+',') == -1){
                    $("#"+data[i].id).find(".addOne").show();
                }
            }

            // 同步分类至即速
            $("#syncJisuSumbit").click(function () {
                // 不允许连续点击
                $("#syncJisuSumbit").attr("disabled","disabled");
                var shopId = $("#syncShopId").val();
                if(!(shopId)){
                    alertx("没有选择店铺");
                    $("#syncJisuSumbit").removeAttr("disabled");
                    return ;
                }
                top.$.jBox.confirm('同步所选店铺的所有分类至即速应用，是否确认？','系统提示',function(v,h,f){
                    if(v=='ok'){
                        loading("系统操作中,请稍后!");
                        jQuery.ajax({
                            url: '${ctx}/xiaodian/retail/goodsCategory/syncJisuCategory',
                            type: 'POST',
                            dataType: "json",
                            data: {
                                shopId: shopId
                            },
                            success: function (result) {
                                $("#syncJisuSumbit").removeAttr("disabled");
                                closeLoading();
                                alertx(result.msg);
                                window.location.reload();
                            }
                        });
                    }
                });
            });

        });
        function addRow(list, tpl, data, pid, root){
            for (var i=0; i<data.length; i++){
                var row = data[i];
                if ((${fns:jsGetVal('row.parentId')}) == pid){
                    $(list).append(Mustache.render(tpl, {
                        dict: {
                            categoryType: getDictLabel(${fns:toJson(fns:getDictList('retail_category_type'))}, row.categoryType),
                            shopName: ( row.shopName || row.shopId ),
                            merchantName: ( row.merchantName || row.merchantId ),
                            blank123:0
                        }, pid: (root?0:pid), row: row, index: i+1
                    }));
                    addRow(list, tpl, data, row.id);
                }
            }
        }
        function toReset() {
            $("#rootNodeNum").val("");
            $("#shopId").val("");
            $("#shopName").val("");
            $("#merchantName").val("");
            $("#keywords").val("");
            $("#name").val("");
        }
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/retail/goodsCategory/">商品分类列表</a></li>
		<shiro:hasPermission name="xiaodian:retail:goodsCategory:edit"><li><a href="${ctx}/xiaodian/retail/goodsCategory/form">商品分类添加</a></li></shiro:hasPermission>
        <%--<shiro:hasPermission name="xiaodian:retail:goodsCategory:edit"><li><a href="${ctx}/xiaodian/retail/goodsCategory/addShopForm">按模板添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsCategory" action="${ctx}/xiaodian/retail/goodsCategory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: 100px">每页根节点数：</label>
				<form:input path="rootNodeNum" htmlEscape="false" maxlength="2" class="input-medium" placeholder="默认5个"/>
			</li>
            <li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium" placeholder="可模糊查询"/>
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
            <li><label>通用编号：</label>
                <form:input path="generalCategorySn" htmlEscape="false" maxlength="20" class="input-medium"/>
            </li>
            <li><label>第三方编号：</label>
                <form:input path="categorySn" htmlEscape="false" maxlength="20" class="input-medium"/>
            </li>
			<li><label>标签：</label>
				<form:input path="keywords" htmlEscape="false" maxlength="20" class="input-medium" placeholder="可模糊查询"/>
			</li>
            <li><label>分类类型：</label>
                <form:select path="categoryType" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('retail_category_type')}" itemLabel="label" itemValue="value"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
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
        <li class="btns"><input id="syncJisuSumbit" class="btn btn-primary" type="button" value="分类同步至即速"/></li>
    </ul>
	<sys:message content="${message}"/>
    <table id="treeTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>名称</th>
            <th>序号</th>
            <th>店铺</th>
            <th>商家</th>
            <th>商品数量</th>
            <%--<th>行业名称</th>--%>
            <%--<th>描述</th>--%>
            <th>标签</th>
            <th>排序</th>
            <th>通用编号</th>
            <th>分类类型</th>
            <th>第三方编号</th>
            <th>更新时间</th>
            <shiro:hasPermission name="xiaodian:retail:goodsCategory:edit"><th style="width: 200px;">操作</th></shiro:hasPermission>
        </tr>
        </thead>
        <tbody id="treeTableList"></tbody>
    </table>
    <div class="pagination">${page}</div>
    <script type="text/template" id="treeTableTpl">
        <tr id="{{row.id}}" pId="{{pid}}">
            <td><a href="${ctx}/xiaodian/retail/goodsCategory/form?id={{row.id}}">
                {{row.name}}
            </a></td>
            <td>
                {{index}}
            </td>
            <td><a href="${ctx}/xiaodian/retail/goodsCategory/list?shopId={{row.shopId}}">
                {{dict.shopName}}
            </a></td>
            <td>
                {{dict.merchantName}}
            </td>
            <td>
                {{row.goodsCount}}
            </td>
            <%--<td>--%>
                <%--{{row.description}}--%>
            <%--</td>--%>
            <td>
                {{row.keywords}}
            </td>
            <td>
                {{row.sort}}
            </td>
            <td>
                {{row.generalCategorySn}}
            </td>
            <td>
                {{dict.categoryType}}
            </td>
            <td>
                {{row.categorySn}}
            </td>
            <td>
                {{row.updateDate}}
            </td>
            <shiro:hasPermission name="xiaodian:retail:goodsCategory:edit"><td>
                <a href="${ctx}/xiaodian/retail/goodsCategory/form?id={{row.id}}">修改</a>
                <a href="${ctx}/xiaodian/retail/goodsCategory/delete?id={{row.id}}" onclick="return confirmx('确认要删除该分类及所有子分类吗？', this.href)">删除</a>
                <a href="${ctx}/xiaodian/retail/goodsCategory/form?parent.id={{row.id}}&shopId={{row.shopId}}&merchantId={{row.merchantId}}">添加下级分类</a>
                <a class="addOne" style="display: none" href="${ctx}/xiaodian/retail/goods/form?shopId={{row.shopId}}&goodsCategoryId={{row.id}}">添加商品</a>
            </td></shiro:hasPermission>
        </tr>
    </script>
</body>
</html>