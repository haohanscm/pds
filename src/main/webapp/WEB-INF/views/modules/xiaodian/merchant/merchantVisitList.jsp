<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家拜访记录管理</title>
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
                        $("#searchForm").attr("action", "${ctx}/xiaodian/merchant/merchantVisit/export/" + flag);
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
            $("#contact").val("");
            $("[name='beginVisitTime']").val("");
            $("[name='endVisitTime']").val("");
            $("#visitStep").select2("val","");
            $("#userId").val("");
            $("#userName").val("");
        }
	</script>
</head>
<body>
    <div id="importBox" class="hide">
        <form id="importForm" action="${ctx}/xiaodian/merchant/merchantVisit/import" method="post" enctype="multipart/form-data"
              class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
            <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
            <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
            <a href="${ctx}/xiaodian/merchant/merchantVisit/import/template">下载模板</a>
        </form>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/merchant/merchantVisit/">商家拜访记录列表</a></li>
		<shiro:hasPermission name="xiaodian:merchant:merchantVisit:edit"><li><a href="${ctx}/xiaodian/merchant/merchantVisit/form">商家拜访记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantVisit" action="${ctx}/xiaodian/merchant/merchantVisit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家名称：</label>
				<form:input id="regName" path="merchantDatabase.regName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>联系人：</label>
				<form:input path="contact" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>拜访时间：</label>
				<input name="beginVisitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantVisit.beginVisitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endVisitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantVisit.endVisitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>进展阶段：</label>
				<form:select path="visitStep" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('merchant_visit_step')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>拜访人员：</label>
				<sys:treeselect id="user" name="user.id" value="${merchantVisit.user.id}" labelName="user.name" labelValue="${merchantVisit.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
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
				<th>商家名称</th>
				<th>拜访地址</th>
				<th>联系人</th>
				<th>联系电话</th>
				<th>进展阶段</th>
				<th>客户经理</th>
				<th style="width: 130px;">拜访时间</th>
				<th style="width: 130px;">更新时间</th>
				<shiro:hasPermission name="xiaodian:merchant:merchantVisit:edit"><th style="width: 60px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantVisit">
			<tr>
				<td><a href="${ctx}/xiaodian/merchant/merchantVisit/form?id=${merchantVisit.id}">
					${merchantVisit.merchantDatabase.regName}
				</a></td>
				<td>
						${merchantVisit.visitAddress}
				</td>
				<td>
					${merchantVisit.contact}
				</td>
				<td>
					${merchantVisit.phoneNumber}
				</td>
				<td>
					${fns:getDictLabel(merchantVisit.visitStep, 'merchant_visit_step', '')}
				</td>
				<td>
					${merchantVisit.user.name}
				</td>
				<td>
					<fmt:formatDate value="${merchantVisit.visitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${merchantVisit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:merchant:merchantVisit:edit"><td>
    				<a href="${ctx}/xiaodian/merchant/merchantVisit/form?id=${merchantVisit.id}">修改</a>
					<a href="${ctx}/xiaodian/merchant/merchantVisit/delete?id=${merchantVisit.id}" onclick="return confirmx('确认要删除该商家拜访记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>