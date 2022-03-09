<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pss/info/supplier/">供应商列表</a></li>
		<shiro:hasPermission name="pss:info:supplier:edit"><li><a href="${ctx}/pss/info/supplier/form">供应商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplier" action="${ctx}/pss/info/supplier/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台商家：</label>
				<form:select path="merchantId" class="input-medium">
					<form:option value="" label="所有商家"/>
					<form:options items="${merchantList}" itemLabel="merchantName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>供应商名称：</label>
				<form:input path="supplierName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<%--
			<li><label>操作员：</label>
				<sys:treeselect id="operator" name="operator" value="${supplier.operator}" labelName="" labelValue="${supplier.id}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			--%>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${supplier.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${supplier.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<%--<th>商家ID</th>--%>
				<th>供应商名称</th>
				<th>联系电话</th>
				<th>微信</th>
				<%--<th>操作员</th>--%>
				<th>创建时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="pss:info:supplier:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplier">
			<tr>
				<%--<td><a href="${ctx}/pss/info/supplier/form?id=${supplier.id}">--%>
					<%--${supplier.merchantId}--%>
				<%--</a></td>--%>
				<td>
					${supplier.supplierName}
				</td>
				<td>
					${supplier.telephone}
				</td>
				<td>
					${supplier.wechatId}
				</td>
			<%--
            <td>
                ${supplier.name}
            </td>
            --%>
            <td>
                <fmt:formatDate value="${supplier.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                ${supplier.remarks}
            </td>
            <shiro:hasPermission name="pss:info:supplier:edit"><td>
                <a href="${ctx}/pss/info/supplier/form?id=${supplier.id}">修改</a>
                <a href="${ctx}/pss/info/supplier/delete?id=${supplier.id}" onclick="return confirmx('确认要删除该供应商吗？', this.href)">删除</a>
				<a href data-toggle="modal" data-target=".bs-example-modal-lg-record">还款记录</a>
				<a href data-toggle="modal" data-target=".bs-example-modal-lg-repayment">还款</a>
				<a href data-toggle="modal" data-target=".bs-example-modal-lg-supply">供应记录</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
	<%--查看还款记录--%>
	<div class="modal fade bs-example-modal-lg bs-example-modal-lg-record" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myLargeModalLabel">还款记录</h4>
				</div>
				<div class="modal-body">
					<table id="contentTable" class="table">
						<thead>
						<tr>
							<th>序号</th>
							<th>金额</th>
							<th>操作人</th>
							<th>还款时间</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${page.list}" var="goodsAllot">
							<tr>

								<td>
									哈哈哈
								</td>
								<td>
									哈哈哈
								</td>
								<td>
									哈哈哈
								</td>
								<td>
									哈哈哈
								</td>
								<td>
									哈哈哈
								</td>
								<td>
									<a href="">删除</a>
								</td>

							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div style="display:flex;border-top: 1px solid #ccc;border-bottom: 1px solid #ccc;padding: 10px 0 10px 0">
						<div style="flex:1">供应商：农贸城</div>
						<div style="flex:1">联系人：隔壁老王</div>
						<div style="flex:1">电话：151452155</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
					</div>
				</div>

			</div>
		</div>
	</div>
	<%--查看供应记录--%>
	<div class="modal fade bs-example-modal-lg bs-example-modal-lg-supply" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myLargeModalLabel">供应记录</h4>
				</div>
				<div class="modal-body">
					<table id="contentTable" class="table">
						<thead>
						<tr>
							<th>序号</th>
							<th>商品名</th>
							<th>单位</th>
							<th>单价</th>
							<th>数量</th>
							<th>日期</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${page.list}" var="goodsAllot">
							<tr>

								<td>
									哈哈1
								</td>
								<td>
									哈哈2
								</td>
								<td>
									哈哈3
								</td>
								<td>
									哈哈5
								</td>
								<td>
									哈哈4
								</td>

							</tr>
						</c:forEach>
						</tbody>
					</table>
					<%--<div style="display:flex;border-top: 1px solid #ccc;border-bottom: 1px solid #ccc;padding: 10px 0 10px 0">--%>
						<%--<div style="flex:1">供应商：农贸城</div>--%>
						<%--<div style="flex:1">联系人：隔壁老王</div>--%>
						<%--<div style="flex:1">电话：151452155</div>--%>
					<%--</div>--%>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
					</div>
				</div>

			</div>
		</div>
	</div>

	<%--添加还款信息--%>
	<div class="modal fade bs-example-modal-lg bs-example-modal-lg-repayment" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myLargeModalLabel">还款信息添加</h4>
				</div>
				<div class="modal-body">
					<form:form id="inputForm" modelAttribute="supplier" action="${ctx}/pss/info/supplier/save" method="post" class="form-horizontal">
						<form:hidden path="id"/>
						<sys:message content="${message}"/>
						<div class="control-group">
							<label class="control-label">供应商名称</label>
							<div class="controls">
								双福农贸城
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">应付欠款</label>
							<div class="controls">
								1000元
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">还款金额</label>
							<div class="controls">
								<form:input path="contact" htmlEscape="false" maxlength="64" class="input-xlarge "/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">还款时间</label>
							<div class="controls">
								<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									   value="<fmt:formatDate value="${supplier.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">备注信息：</label>
							<div class="controls">
								<form:textarea path="remarks" htmlEscape="false" rows="1" maxlength="50" class="input-xlarge "/>
							</div>
						</div>
						<div class="form-actions">
							<shiro:hasPermission name="pss:info:supplier:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
							<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
						</div>
					</form:form>
				</div>

			</div>
		</div>
	</div>

</body>
</html>