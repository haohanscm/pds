<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家资料管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnImport").click(function(){
                $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
            });
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        var url = $("#searchForm").attr("action");
                        var flag = $("#flag").val() == 0 ? "false" : "true";
                        $("#searchForm").attr("action", "${ctx}/xiaodian/merchant/merchantDatabase/export/" + flag);
                        $("#searchForm").submit();
                        $("#searchForm").attr("action",url);
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
            // 恢复提示框显示
            resetTip();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function toReset() {
            $("#regName").val("");
            $("#areaId").val("");
            $("#areaName").val("");
            $("#contact").val("");
            $("#telephone").val("");
            $("#shopName").val("");
            $("#merchantType").select2("val","");
            $("#industryId").val("");
            $("#industryName").val("");
            $("#bizfromType").select2("val","");
            $("#status").select2("val","");
            $("[name='beginInitTime']").val("");
            $("[name='endInitTime']").val("");
        }
	</script>
</head>
<body>
    <div id="importBox" class="hide">
        <form id="importForm" action="${ctx}/xiaodian/merchant/merchantDatabase/import" method="post" enctype="multipart/form-data"
              class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
            <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
            <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
            <a href="${ctx}/xiaodian/merchant/merchantDatabase/import/template">下载模板</a>
        </form>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/merchant/merchantDatabase/">商家资料列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:merchantDatabase:edit"><li><a href="${ctx}/xiaodian/merchant/merchantDatabase/form">商家资料添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantDatabase" action="${ctx}/xiaodian/merchant/merchantDatabase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商户名称：</label>
				<form:input path="regName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<%--<li><label>经营法人：</label>--%>
				<%--<form:input path="regUser" htmlEscape="false" maxlength="64" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>所属区域：</label>
				<sys:treeselect id="area" name="area.id" value="${merchantDatabase.area.id}" labelName="area.name" labelValue="${merchantDatabase.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>商家联系人：</label>
				<form:input path="contact" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>联系手机：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺名称	：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>商户类别：</label>
				<form:select path="merchantType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('merchant_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>行业类别：</label>
				<sys:treeselect id="industry" name="industry" value="${merchantDatabase.industry}" labelName="industry.name"
								labelValue="${industryMap[merchantDatabase.industry]}"
								title="行业分类" url="/xiaodian/industry/treeData" cssClass="" allowClear="true"
								notAllowSelectParent="true"/>
			</li>
            <li><label>商机来源：</label>
				<form:select path="bizfromType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bizfrom_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否审核：</label>
			<form:select path="status" class="input-small">
                <form:option value="" label=""/>
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			</li>
			<li><label>收录时间：</label>
				<input name="beginInitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantDatabase.beginInitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endInitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantDatabase.endInitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                <input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
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
				<th>商户注册名称</th>
				<th>所属区域</th>
				<th>商家联系人</th>
				<th>联系手机</th>
				<th>店铺名称</th>
				<th>店铺地址</th>
				<th>行业类别</th>
				<th>商机来源</th>
				<th>是否审核</th>
				<th style="min-width:130px;">收录时间</th>
				<th style="min-width:130px;">更新时间</th>
				<%--<th>备注信息</th>--%>
				<shiro:hasPermission name="xiaodian:merchant:merchantDatabase:edit"><th style="min-width:150px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantDatabase">
			<tr>
				<td><a href="${ctx}/xiaodian/merchant/merchantDatabase/form?id=${merchantDatabase.id}">
					${merchantDatabase.regName}
				</a></td>
				<td>
					${merchantDatabase.area.name}
				</td>
				<td>
					${merchantDatabase.contact}
				</td>
				<td>
					${merchantDatabase.telephone}
				</td>
				<td>
					${merchantDatabase.shopName}
				</td>
				<td>
					${merchantDatabase.shopAddress}
				</td>
                <td>
					${industryMap[merchantDatabase.industry]}
				</td>
				<td>
					${fns:getDictLabel(merchantDatabase.bizfromType,"bizfrom_type",merchantDatabase.bizfromType)}
				</td>
                <td>
                    ${fns:getDictLabel(merchantDatabase.status,"yes_no" , "")}
                </td>
				<td>
					<fmt:formatDate value="${merchantDatabase.initTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${merchantDatabase.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${merchantDatabase.remarks}--%>
				<%--</td>--%>
				<shiro:hasPermission name="xiaodian:merchant:merchantDatabase:edit"><td>
    				<a href="${ctx}/xiaodian/merchant/merchantDatabase/form?id=${merchantDatabase.id}">修改</a>
					<a href="${ctx}/xiaodian/merchant/merchantDatabase/delete?id=${merchantDatabase.id}" onclick="return confirmx('确认要删除该商家资料吗？', this.href)">删除</a>
					<a href="${ctx}/xiaodian/merchant/merchantDatabase/addVisit?id=${merchantDatabase.id}" >添加拜访记录</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>