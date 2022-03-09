<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图管理</title>
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
		<li><a href="${ctx}/xiaodian/app/appSlideShowGroup/">轮播图列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/app/appSlideShowGroup/form?id=${appSlideShowGroup.id}">轮播图<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit">${not empty appSlideShowGroup.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:app:appSlideShowGroup:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appSlideShowGroup" action="${ctx}/xiaodian/app/appSlideShowGroup/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分组名称：</label>
			<div class="controls">
				<form:input path="groupName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分组描述：</label>
			<div class="controls">
				<form:input path="groupDesc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分组排序：</label>
			<div class="controls">
				<form:input path="groupSort" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">轮播图：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<%--<th>应用ID</th>--%>
								<th>资源类型</th>
								<th>资源地址</th>
								<th>跳转设置</th>
								<th>跳转定位</th>
								<th>是否显示</th>
								<th>权重</th>
								<th>备注信息</th>
								<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="appSlideShowList">
						</tbody>
						<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit"><tfoot>
							<tr><td colspan="10"><a href="javascript:" onclick="addRow('#appSlideShowList', appSlideShowRowIdx, appSlideShowTpl);appSlideShowRowIdx = appSlideShowRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="appSlideShowTpl">//<!--
						<tr id="appSlideShowList{{idx}}">
							<td class="hide">
								<input id="appSlideShowList{{idx}}_id" name="appSlideShowList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="appSlideShowList{{idx}}_delFlag" name="appSlideShowList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<%--
							<td>
								<input id="appSlideShowList{{idx}}_appId" name="appSlideShowList[{{idx}}].appId" type="text" value="{{row.appId}}" maxlength="64" class="input-small "/>
							</td>
							--%>
							<td>
								<select id="appSlideShowList{{idx}}_resType" name="appSlideShowList[{{idx}}].resType" data-value="{{row.resType}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('app_res_type')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="appSlideShowList{{idx}}_resUrl" name="appSlideShowList[{{idx}}].resUrl" type="text" value="{{row.resUrl}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="appSlideShowList{{idx}}_goConfig" name="appSlideShowList[{{idx}}].goConfig" type="text" value="{{row.goConfig}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="appSlideShowList{{idx}}_goTarget" name="appSlideShowList[{{idx}}].goTarget" type="text" value="{{row.goTarget}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<select id="appSlideShowList{{idx}}_isDisplay" name="appSlideShowList[{{idx}}].isDisplay" type="text" value="{{row.isDisplay}}" maxlength="64" class="input-small ">
								<option value=""></option>
									<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="appSlideShowList{{idx}}_weight" name="appSlideShowList[{{idx}}].weight" type="text" value="{{row.weight}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<textarea id="appSlideShowList{{idx}}_remarks" name="appSlideShowList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#appSlideShowList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var appSlideShowRowIdx = 0, appSlideShowTpl = $("#appSlideShowTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(appSlideShowGroup.appSlideShowList)};
							for (var i=0; i<data.length; i++){
								addRow('#appSlideShowList', appSlideShowRowIdx, appSlideShowTpl, data[i]);
								appSlideShowRowIdx = appSlideShowRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:app:appSlideShowGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>