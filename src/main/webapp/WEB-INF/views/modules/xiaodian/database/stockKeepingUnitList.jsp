<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存商品管理</title>
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
                        $("#searchForm").attr("action", "${ctx}/xiaodian/database/stockKeepingUnit/export?flag="+flag);
                        $("#searchForm").submit();
                        $("#searchForm").attr("action",url);
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
            // 生成所有库存商品的商品编号
            $("#btnSkuSn").click(function () {
                // 不允许连续点击
                $("#btnSkuSn").attr("disabled","disabled");
                loading("数据计算中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/xiaodian/database/stockKeepingUnit/genSkuSn',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        stockGoodsSn: "all",
                    },
                    success: function (result) {
                        $("#btnSkuSn").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                    }
                });
            })
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
		<form id="importForm" action="${ctx}/xiaodian/database/stockKeepingUnit/import" method="post" enctype="multipart/form-data"
			  class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/xiaodian/database/stockKeepingUnit/export?flag=true">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/database/stockKeepingUnit/">库存商品列表</a></li>
		<shiro:hasPermission name="xiaodian:database:stockKeepingUnit:edit"><li><a href="${ctx}/xiaodian/database/stockKeepingUnit/form">库存商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="stockKeepingUnit" action="${ctx}/xiaodian/database/stockKeepingUnit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标准商品id：</label>
				<form:input path="spuId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>sku编号：</label>
				<form:input path="stockGoodsSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>扫码条码：</label>
				<form:input path="scanCode" htmlEscape="false" maxlength="20" class="input-medium"/>
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
            <li><label style="width: 120px;">生成sku商品编号：</label>
                <input id="btnSkuSn" class="btn btn-primary" type="button" value="开始"/>
            </li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>标准商品(spu)id</th>
				<th>sku商品编号</th>
				<th>售价</th>
				<th>库存</th>
				<th>单位</th>
				<th>规格详情,拼接所有属性值</th>
				<th>扫码条码</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:database:stockKeepingUnit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="stockKeepingUnit">
			<tr>
				<td><a href="${ctx}/xiaodian/database/stockKeepingUnit/form?id=${stockKeepingUnit.id}">
					${stockKeepingUnit.goodsName}
				</a></td>
				<td>
					${stockKeepingUnit.spuId}
				</td>
				<td>
					${stockKeepingUnit.stockGoodsSn}
				</td>
				<td>
					${stockKeepingUnit.salePrice}
				</td>
				<td>
					${stockKeepingUnit.stock}
				</td>
				<td>
					${stockKeepingUnit.unit}
				</td>
				<td>
					${stockKeepingUnit.attrDetail}
				</td>
				<td>
					${stockKeepingUnit.scanCode}
				</td>
				<td>
					${stockKeepingUnit.sort}
				</td>
				<td>
					<fmt:formatDate value="${stockKeepingUnit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${stockKeepingUnit.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:database:stockKeepingUnit:edit"><td>
    				<a href="${ctx}/xiaodian/database/stockKeepingUnit/form?id=${stockKeepingUnit.id}">修改</a>
					<a href="${ctx}/xiaodian/database/stockKeepingUnit/delete?id=${stockKeepingUnit.id}" onclick="return confirmx('确认要删除该库存商品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>