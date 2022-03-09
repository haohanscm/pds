<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>图片组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/common/photoGroupManage/">图片组列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/common/photoGroupManage/form?id=${photoGroupManage.id}">图片组<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit">${not empty photoGroupManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:common:photoGroupManage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="photoGroupManage" action="${ctx}/xiaodian/common/photoGroupManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商家ID：</label>
			<div class="controls">
				<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片组编号：</label>
			<div class="controls">
				<form:input path="groupNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片组名称：</label>
			<div class="controls">
				<form:input path="groupName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类别标签：</label>
			<div class="controls">
				<form:input path="categroyTag" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">图片管理：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>图片库ID</th>
								<th>图片地址</th>
								<th>图片名称</th>
								<th>备注信息</th>
								<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="photoManageList">
						</tbody>
						<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#photoManageList', photoManageRowIdx, photoManageTpl);photoManageRowIdx = photoManageRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="photoManageTpl">//<!--
						<tr id="photoManageList{{idx}}">
							<td class="hide">
								<input id="photoManageList{{idx}}_id" name="photoManageList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="photoManageList{{idx}}_delFlag" name="photoManageList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
							{{row.photoGallery.id}}
								<%--<input id="photoManageList{{idx}}_photoGallery" name="photoManageList[{{idx}}].photoGallery.id" type="text" value="{{row.photoGallery.id}}" maxlength="64" class="input-small "/>--%>
							</td>
							<td>
								<img id="mmPictureDetailList{{idx}}_originUrl" name="mmPictureDetailList[{{idx}}].originUrl" src="{{row.picUrl}}" style="width:100px;height:100px"/>
								<%--<input id="photoManageList{{idx}}_picUrl" name="photoManageList[{{idx}}].picUrl" type="text" value="{{row.picUrl}}" maxlength="100" class="input-small "/>--%>
							</td>
							<td>
								<input id="photoManageList{{idx}}_picName" name="photoManageList[{{idx}}].picName" type="text" value="{{row.picName}}" maxlength="50" class="input-small "/>
							</td>
							<td>
								<textarea id="photoManageList{{idx}}_remarks" name="photoManageList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#photoManageList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var photoManageRowIdx = 0, photoManageTpl = $("#photoManageTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(photoGroupManage.photoManageList)};
							for (var i=0; i<data.length; i++){
								addRow('#photoManageList', photoManageRowIdx, photoManageTpl, data[i]);
								photoManageRowIdx = photoManageRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:common:photoGroupManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>