<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片管理</title>
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
		<li class="active"><a href="${ctx}/xiaodian/common/photoGallery/">图片列表</a></li>
		<shiro:hasPermission name="xiaodian:common:photoGallery:edit">
			<%--<li><a href="${ctx}/xiaodian/common/photoGallery/form">图片添加</a></li>--%>
			<li><a href="${ctx}/xiaodian/common/photoGallery/batchUpload">图片批量上传</a></li>
		</shiro:hasPermission>

	</ul>
	<form:form id="searchForm" modelAttribute="photoGallery" action="${ctx}/xiaodian/common/photoGallery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>图片名称：</label>
				<form:input path="picName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>存储云服务：</label>
				<form:select path="ossType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oss_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${photoGallery.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${photoGallery.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>图片资源id</th>
				<th>图片名称</th>
				<th>图片地址</th>
				<th>图片</th>
				<th>图片类型</th>
				<th>图片大小</th>
				<th>图片来源</th>
				<th>存储云服务</th>
				<th>状态</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="xiaodian:common:photoGallery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="photoGallery">
			<tr>
				<td><a href="${ctx}/xiaodian/common/photoGallery/form?id=${photoGallery.id}">
					${photoGallery.id}
				</a></td>
				<td>
					${photoGallery.picName}
				</td>
				<td>
					${photoGallery.picUrl}
				</td>
				<td style="min-width: 100px">
					<img src="${photoGallery.picUrl}" alt="找不到图片" style="width: 150px"/>
				</td>
				<td>
					${photoGallery.picType}
				</td>
				<td>
					${photoGallery.picSize}
				</td>
				<td>
					${photoGallery.picFrom}
				</td>
				<td>
					${fns:getDictLabel(photoGallery.ossType, 'oss_type', '')}
				</td>
				<td>
					${fns:getDictLabel(photoGallery.status, 'common_status', '')}
				</td>
				<td>
					${photoGallery.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${photoGallery.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${photoGallery.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${photoGallery.remarks}
				</td>
				<shiro:hasPermission name="xiaodian:common:photoGallery:edit"><td>
    				<a href="${ctx}/xiaodian/common/photoGallery/form?id=${photoGallery.id}">修改</a>
					<a href="${ctx}/xiaodian/common/photoGallery/delete?id=${photoGallery.id}" onclick="return confirmx('确认要删除该图片吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>