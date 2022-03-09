<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打印机管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
        // 从飞鹅云删除打印机
        function delPrinter(id) {
            $.getJSON("${ctx}/xiaodian/printer/feiePrinter/delYunPrinter", {id: id}, function (data) {
                if (data.code == 200) {
                    var result = JSON.parse(data.ext);
                    alertx("删除成功  " + result.ok.join());
                    window.location.reload();
                } else {
                    alertx("删除失败:" + data.msg + ",  " + data.ext);
                }
            })
        }
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        // 清空查询条件
        function toReset(){
            $("#shopId").select2('val',"");
            $("#merchantId").select2('val',"");
            $("#printerType").select2('val',"");
            $("#printerSn").val("");
            $("#status").select2('val',"");
            $("#useable").select2('val',"");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/printer/feiePrinter/">打印机列表</a></li>
		<shiro:hasPermission name="xiaodian:printer:feiePrinter:edit"><li><a href="${ctx}/xiaodian/printer/feiePrinter/form">打印机添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="feiePrinter" action="${ctx}/xiaodian/printer/feiePrinter/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>店铺名称：</label>
                <form:select path="shopId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${shopList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>商家名称：</label>
                <form:select path="merchantId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>打印机类型：</label>
				<form:select path="printerType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('printer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>打印机编号：</label>
				<form:input path="printerSn" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>添加至飞鹅云</label>
				<form:select path="status" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
            <li><label>启用状态</label>
                <form:select path="useable" class="input-small">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>打印机编号</th>
				<th>店铺名称</th>
				<th>商家名称</th>
				<th>打印机类型</th>
				<th>打印机名</th>
				<th>打印次数</th>
				<th>添加至飞鹅云</th>
				<th>启用状态</th>
				<th>更新时间</th>
				<%--<th>备注信息</th>--%>
				<shiro:hasPermission name="xiaodian:printer:feiePrinter:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="feiePrinter" varStatus="index">
			<tr>
                <td>
                        ${index.count}
                </td>
                <td><a href="${ctx}/xiaodian/printer/feiePrinter/form?id=${feiePrinter.id}">
                        ${feiePrinter.printerSn}
                </a></td>
				<td>
					${feiePrinter.shopName}
				</td>
				<td>
					${feiePrinter.merchantName}
				</td>
				<td>
					${fns:getDictLabel(feiePrinter.printerType, 'printer_type', '')}
				</td>
				<td>
					${feiePrinter.printerName}
				</td>
                <td>
                     ${feiePrinter.times}
                </td>
				<td>
					${fns:getDictLabel(feiePrinter.status, 'yes_no', '')}
				</td>
                <td>
                     ${fns:getDictLabel(feiePrinter.useable, 'yes_no', '')}
                </td>
				<td>
					<fmt:formatDate value="${feiePrinter.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>--%>
					<%--${feiePrinter.remarks}--%>
				<%--</td>--%>
				<shiro:hasPermission name="xiaodian:printer:feiePrinter:edit"><td>
    				<a href="${ctx}/xiaodian/printer/feiePrinter/form?id=${feiePrinter.id}">修改</a>
					<a href="${ctx}/xiaodian/printer/feiePrinter/delete?id=${feiePrinter.id}" onclick="return confirmx('确认要删除该打印机吗？', this.href)">删除</a>
    				<a href="#" onclick="delPrinter('${feiePrinter.id}')">从飞鹅云删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>