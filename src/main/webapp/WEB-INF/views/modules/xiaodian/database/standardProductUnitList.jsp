<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>标准商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            // 恢复提示框显示
            resetTip();
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
                        $("#searchForm").attr("action", "${ctx}/xiaodian/database/standardProductUnit/export?flag="+flag);
                        $("#searchForm").submit();
                        $("#searchForm").attr("action",url);
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });

		});
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
        <form id="importForm" action="${ctx}/xiaodian/database/standardProductUnit/import" method="post" enctype="multipart/form-data"
              class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
            <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
            <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
            <a href="${ctx}/xiaodian/database/standardProductUnit/export?flag=true">下载模板</a>
        </form>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/database/standardProductUnit/">标准商品列表</a></li>
		<shiro:hasPermission name="xiaodian:database:standardProductUnit:edit"><li><a href="${ctx}/xiaodian/database/standardProductUnit/form">标准商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="standardProductUnit" action="${ctx}/xiaodian/database/standardProductUnit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品分类：</label>
					<sys:treeselect id="goodsCategory" name="goodsCategoryId"
									value="${standardProductUnit.goodsCategoryId}" labelName="categoryName"
									labelValue="${standardProductUnit.categoryName}"
									title="父级编号" url="/xiaodian/database/productCategory/treeData" cssClass=""
									allowClear="true" notAllowSelectParent="true" />
			</li>
			<li><label>通用编号：</label>
				<form:input path="generalSn" htmlEscape="false" maxlength="64" class="input-medium"/>
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
        </ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>商品通用编号(spu)</th>
				<th>商品id(spu)</th>
				<th>商品分类</th>
				<th>类型</th>
				<th>行业</th>
				<th>品牌</th>
				<th>厂家/制造商</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:database:standardProductUnit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="standardProductUnit">
			<tr>
				<td><a href="${ctx}/xiaodian/database/standardProductUnit/form?id=${standardProductUnit.id}">
					${standardProductUnit.goodsName}
				</a></td>
				<td>
					${standardProductUnit.generalSn}
				</td>
				<td>
					${standardProductUnit.id}
				</td>
				<td>
					${standardProductUnit.categoryName}
				</td>
				<td>
					${fns:getDictLabel(standardProductUnit.aggregationType, "aggregation_shop_type","")}
				</td>
				<td>
					${standardProductUnit.industry}
				</td>
				<td>
					${standardProductUnit.brand}
				</td>
				<td>
					${standardProductUnit.manufacturer}
				</td>
				<td>
					${standardProductUnit.sort}
				</td>
				<td>
					<fmt:formatDate value="${standardProductUnit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${standardProductUnit.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:database:standardProductUnit:edit"><td>
    				<a href="${ctx}/xiaodian/database/standardProductUnit/form?id=${standardProductUnit.id}">修改</a>
					<a href="${ctx}/xiaodian/database/standardProductUnit/delete?id=${standardProductUnit.id}" onclick="return confirmx('确认要删除该标准商品及所属库存商品吗？', this.href)">删除</a>
    				<a href="${ctx}/xiaodian/database/standardProductUnit/trans?id=${standardProductUnit.id}">导出至零售商品</a>
    				<a href="${ctx}/xiaodian/database/stockKeepingUnit/list?spuId=${standardProductUnit.id}">sku明细</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>