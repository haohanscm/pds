<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家账户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function toReset(){
            $("#partnerId").val("");
            $("#mercAbbr").val("");
            $("#crpNm").val("");
            $("#mercProv").val("");
            $("#usrOprMbl").val("");
            $("#createById").val("");
            $("#createByName").val("");
            $("#regStatus").select2('val',"");
            $("[name='beginCreateDate']").val("");
            $("[name='endCreateDate']").val("");
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/pay/merchantPayInfo/">商家账户列表</a></li>
		<shiro:hasPermission name="xiaodian:pay:merchantPayInfo:edit"><li><a href="${ctx}/xiaodian/pay/merchantPayInfo/form">商家账户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantPayInfo" action="${ctx}/xiaodian/pay/merchantPayInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家id：</label>
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商户编号：</label>
				<form:input path="partnerId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>商户简称：</label>
				<form:input path="mercAbbr" htmlEscape="false" maxlength="128" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>法人姓名：</label>
				<form:input path="crpNm" htmlEscape="false" maxlength="32" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>归属省：</label>
				<form:input path="mercProv" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="usrOprMbl" htmlEscape="false" maxlength="32" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>创建者：</label>
				<sys:treeselect id="createBy" name="createBy.id" value="${merchantPayInfo.createBy.id}" labelName="createBy.name" labelValue="${merchantPayInfo.createBy.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>开户状态：</label>
				<form:select path="regStatus" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bank_reg_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantPayInfo.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${merchantPayInfo.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li ><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商户编号</th>
				<th>商家名称</th>
				<th>商户法人姓名</th>
				<th>归属省</th>
				<th>商户管理员</th>
				<th>管理员手机号码</th>
				<th>支行名称</th>
				<th>创建者</th>
				<th>开户状态</th>
				<th>是否启用</th>
				<th>创建时间</th>
				<shiro:hasPermission name="xiaodian:pay:merchantPayInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantPayInfo">
			<tr>
				<td><a href="${ctx}/xiaodian/pay/merchantPayInfo/form?id=${merchantPayInfo.id}">
					${merchantPayInfo.partnerId}
				</a></td>
				<td>
					${merchantPayInfo.mercNm}
				</td>
				<td>
					${merchantPayInfo.crpNm}
				</td>
				<td>
					${merchantPayInfo.mercProv}
				</td>
				<td>
					${merchantPayInfo.usrOprNm}
				</td>
				<td>
					${merchantPayInfo.usrOprMbl}
				</td>
				<td>
					${merchantPayInfo.lbnkNm}
				</td>
				<td>
					${merchantPayInfo.createBy.name}
				</td>
				<td>
					${fns:getDictLabel(merchantPayInfo.regStatus, 'bank_reg_status', '')}
				</td>
				<td>
						${fns:getDictLabel(merchantPayInfo.isEnable, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${merchantPayInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:pay:merchantPayInfo:edit"><td>
    				<a href="${ctx}/xiaodian/pay/merchantPayInfo/form?id=${merchantPayInfo.id}">修改</a>
					<a href="${ctx}/xiaodian/pay/merchantPayInfo/delete?id=${merchantPayInfo.id}" onclick="return confirmx('确认要删除该商家账户信息吗？', this.href)">删除</a>
					<c:if test="${ 0 eq merchantPayInfo.regStatus }">
						<a href="${ctx}/xiaodian/pay/merchantPayInfo/queryStatus?id=${merchantPayInfo.id}">状态查询</a>
					</c:if>
					<c:if test="${ -1 eq merchantPayInfo.regStatus }">
					<a href="${ctx}/xiaodian/pay/merchantPayInfo/reg?id=${merchantPayInfo.id}">商家开户</a>
					</c:if>
					<a href="${ctx}/xiaodian/pay/merchantPayInfo/copy?id=${merchantPayInfo.id}">复制</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>