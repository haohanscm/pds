<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品库分类管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(page.list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});

			// excel 导入导出
            $("#btnImport").click(function(){
                $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
            });
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        var url = $("#searchForm").attr("action");
                        var flag = $("#flag").val() == 0 ? "false" : "true";
                        $("#searchForm").attr("action", "${ctx}/xiaodian/database/productCategory/export?flag="+flag);
                        $("#searchForm").submit();
                        $("#searchForm").attr("action",url);
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });

            // 生成所有的商品分类编号
            $("#btnCategorySn").click(function () {
                // 不允许连续点击
                $("#btnCategorySn").attr("disabled","disabled");
                loading("数据计算中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/xiaodian/database/productCategory/genCategorySn',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        generalCategorySn: "all",
                    },
                    success: function (result) {
                        $("#btnCategorySn").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                    }
                });
            })
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
                            aggregationType: getDictLabel(${fns:toJson(fns:getDictList('aggregation_shop_type'))}, row.aggregationType),
						blank123:0}, pid: (root?0:pid), row: row, index: i+1
					}));
					addRow(list, tpl, data, row.id);
				}
			}
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
    <div id="importBox" class="hide">
        <form id="importForm" action="${ctx}/xiaodian/database/productCategory/import" method="post" enctype="multipart/form-data"
              class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
            <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
            <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
            <a href="${ctx}/xiaodian/database/productCategory/export?flag=true">下载模板</a>
        </form>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/database/productCategory/">商品库分类列表</a></li>
		<shiro:hasPermission name="xiaodian:database:productCategory:edit"><li><a href="${ctx}/xiaodian/database/productCategory/form">商品库分类添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="xiaodian:database:productCategory:edit"><li><a href="${ctx}/xiaodian/database/productCategory/importRetailGoods">分类商品导入至零售商品</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productCategory" action="${ctx}/xiaodian/database/productCategory/" method="post" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
            <li><label>分类编号：</label>
                <form:input path="generalCategorySn" htmlEscape="false" maxlength="100" class="input-medium"/>
            </li>
            <li><label>类型：</label>
                <form:select path="aggregationType" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('aggregation_shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
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
            <li><label style="width: 120px;">生成商品分类编号：</label>
                <input id="btnCategorySn" class="btn btn-primary" type="button" value="开始"/>
            </li>
        </ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>序号</th>
				<th>分类编号</th>
				<th>分类商品数</th>
				<th>类型</th>
				<th>分类描述</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:database:productCategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
    <div class="pagination">${page}</div>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/xiaodian/database/productCategory/form?id={{row.id}}">
				{{row.name}}
			</a></td>
            <td>
                {{index}}
            </td>
            <td>
                {{row.generalCategorySn}}
            </td>
			<td>
				{{row.goodsCount}}
			</td>
            <td>
                {{dict.aggregationType}}
            </td>
			<td>
				{{row.categoryDesc}}
			</td>
			<td>
				{{row.sort}}
			</td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="xiaodian:database:productCategory:edit"><td>
   				<a href="${ctx}/xiaodian/database/productCategory/form?id={{row.id}}">修改</a>
				<a href="${ctx}/xiaodian/database/productCategory/delete?id={{row.id}}" onclick="return confirmx('确认要删除该商品库分类及所有子商品库分类吗？', this.href)">删除</a>
				<a href="${ctx}/xiaodian/database/productCategory/form?parent.id={{row.id}}">添加下级商品库分类</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>