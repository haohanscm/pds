<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息模板管理管理</title>
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
		<li><a href="${ctx}/xiaodian/message/wechatMessageTemplate/">消息模板管理列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/message/wechatMessageTemplate/form?id=${wechatMessageTemplate.id}">消息模板管理<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit">${not empty wechatMessageTemplate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:message:wechatMessageTemplate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wechatMessageTemplate" action="${ctx}/xiaodian/message/wechatMessageTemplate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">模板ID：</label>
			<div class="controls">
				<form:input path="templateId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息类型：</label>
			<div class="controls">
				<form:select path="msgType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('wechat_msg_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跳转页面：</label>
			<div class="controls">
				<form:textarea path="goPage" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">说明：</label>
			<div class="controls">
				<form:input path="msgDesc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="" htmlEscape="false" maxlength="5" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">消息模板明细：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>字段名称</th>
								<th>字段值</th>
								<th>字段code</th>
								<th>字段颜色</th>
								<th>是否加粗</th>
								<th>排序</th>
								<th>备注信息</th>
								<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="wechatMessageDetailList">
						</tbody>
						<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit"><tfoot>
							<tr><td colspan="9"><a href="javascript:" onclick="addRow('#wechatMessageDetailList', wechatMessageDetailRowIdx, wechatMessageDetailTpl);wechatMessageDetailRowIdx = wechatMessageDetailRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="wechatMessageDetailTpl">//<!--
						<tr id="wechatMessageDetailList{{idx}}">
							<td class="hide">
								<input id="wechatMessageDetailList{{idx}}_id" name="wechatMessageDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="wechatMessageDetailList{{idx}}_delFlag" name="wechatMessageDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="wechatMessageDetailList{{idx}}_fieldName" name="wechatMessageDetailList[{{idx}}].fieldName" type="text" value="{{row.fieldName}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="wechatMessageDetailList{{idx}}_fieldValue" name="wechatMessageDetailList[{{idx}}].fieldValue" type="text" value="{{row.fieldValue}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="wechatMessageDetailList{{idx}}_fileldCode" name="wechatMessageDetailList[{{idx}}].fileldCode" type="text" value="{{row.fileldCode}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="wechatMessageDetailList{{idx}}_fieldColor" name="wechatMessageDetailList[{{idx}}].fieldColor" type="text" value="{{row.fieldColor}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="wechatMessageDetailList{{idx}}_isBold" name="wechatMessageDetailList[{{idx}}].isBold" type="text" value="{{row.isBold}}" maxlength="5" class="input-small "/>
							</td>
							<td>
								<input id="wechatMessageDetailList{{idx}}_sort" name="wechatMessageDetailList[{{idx}}].sort" type="text" value="{{row.sort}}" maxlength="32" class="input-small "/>
							</td>
							<td>
								<textarea id="wechatMessageDetailList{{idx}}_remarks" name="wechatMessageDetailList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#wechatMessageDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var wechatMessageDetailRowIdx = 0, wechatMessageDetailTpl = $("#wechatMessageDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(wechatMessageTemplate.wechatMessageDetailList)};
							for (var i=0; i<data.length; i++){
								addRow('#wechatMessageDetailList', wechatMessageDetailRowIdx, wechatMessageDetailTpl, data[i]);
								wechatMessageDetailRowIdx = wechatMessageDetailRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:message:wechatMessageTemplate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>