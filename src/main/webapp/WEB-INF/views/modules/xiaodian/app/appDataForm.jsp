<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据对象管理</title>
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
            // appId 变化影响商品分类选择
            $("#appId").change(function () {
                var oldContent = $("#choiceCategory").children("script").html();
                // 空格为替换的结束标识
                var newContent = oldContent.replace(/limitAppId=\S*/,"limitAppId="+$("#appId").val());
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#choiceCategory").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#categoryButton, #categoryName").unbind();
                document.getElementById("choiceCategory").appendChild(script);
            });
            // 初始化
            $("#appId").change();
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
	<li><a href="${ctx}/xiaodian/app/appData/">数据对象列表</a></li>
	<li class="active"><a href="${ctx}/xiaodian/app/appData/form?id=${appData.id}">数据对象<shiro:hasPermission name="xiaodian:app:appData:edit">${not empty appData.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:app:appData:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="appData" action="${ctx}/xiaodian/app/appData/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">应用ID：</label>
		<div class="controls">
			<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">对象名称：</label>
		<div class="controls">
			<form:input path="objName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">对象描述：</label>
		<div class="controls">
			<form:input path="objDesc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">分类：</label>
		<div class="controls" id="choiceCategory">
			<sys:treeselect id="category" name="category" value="${category.id}" labelName="category.name"
							labelValue="${category.name}"
							title="数据分类" url="/xiaodian/app/appDataCategory/treeData?limitAppId= " cssClass="" allowClear="true"
							notAllowSelectParent="true"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">备注信息：</label>
		<div class="controls">
			<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">数据对象明细：</label>
		<div class="controls">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th class="hide"></th>
					<th>字段名称</th>
					<th>字段类型</th>
					<th>是否必填</th>
					<th>排序</th>
					<th>备注信息</th>
					<shiro:hasPermission name="xiaodian:app:appData:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
				</tr>
				</thead>
				<tbody id="appDataDetailList">
				</tbody>
				<shiro:hasPermission name="xiaodian:app:appData:edit"><tfoot>
				<tr><td colspan="7"><a href="javascript:" onclick="addRow('#appDataDetailList', appDataDetailRowIdx, appDataDetailTpl);appDataDetailRowIdx = appDataDetailRowIdx + 1;" class="btn">新增</a></td></tr>
				</tfoot></shiro:hasPermission>
			</table>
			<script type="text/template" id="appDataDetailTpl">//<!--
						<tr id="appDataDetailList{{idx}}">
							<td class="hide">
								<input id="appDataDetailList{{idx}}_id" name="appDataDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="appDataDetailList{{idx}}_delFlag" name="appDataDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="appDataDetailList{{idx}}_fieldName" name="appDataDetailList[{{idx}}].fieldName" type="text" value="{{row.fieldName}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<select id="appDataDetailList{{idx}}_fieldType" name="appDataDetailList[{{idx}}].fieldType" data-value="{{row.fieldType}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('app_field_type')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="appDataDetailList{{idx}}_isNeed" name="appDataDetailList[{{idx}}].isNeed" data-value="{{row.isNeed}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
								</select>
							</td>
							<td>
								<input id="appDataDetailList{{idx}}_sort" name="appDataDetailList[{{idx}}].sort" type="text" value="{{row.sort}}" maxlength="5" class="input-small "/>
							</td>
							<td>
								<textarea id="appDataDetailList{{idx}}_remarks" name="appDataDetailList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="xiaodian:app:appData:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#appDataDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
			</script>
			<script type="text/javascript">
                var appDataDetailRowIdx = 0, appDataDetailTpl = $("#appDataDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
                $(document).ready(function() {
                    var data = ${fns:toJson(appData.appDataDetailList)};
                    for (var i=0; i<data.length; i++){
                        addRow('#appDataDetailList', appDataDetailRowIdx, appDataDetailTpl, data[i]);
                        appDataDetailRowIdx = appDataDetailRowIdx + 1;
                    }
                });
			</script>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="xiaodian:app:appData:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>