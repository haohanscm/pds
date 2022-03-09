<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();

			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
            // appId 变化影响 分类选择 动态加载
            $("#appId").change(function () {
                var oldContent = $("#choiceCategory").children("script").html();
                // 空格为替换的结束标识
                var newContent = oldContent.replace(/limitAppId=\S*/,"limitAppId="+$("#appId").val());
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.innerHTML = newContent;
                $("#choiceCategory").children("script").remove();
                // treeselect元素的id + Button Name 解除绑定的点击事件
                $("#parentButton, parentName").unbind();
                document.getElementById("choiceCategory").appendChild(script);
            });
            // 初始化
            $("#appId").change();
		});

        // 按按单一层级保存 提交表单
        function save(){
            $("#inputForm").attr("action","${ctx}/xiaodian/app/appDataCategory/save");
            $("#inputForm").submit();
        }
		// 按模板添加树 提交表单
		function shopModle(){
			$("#inputForm").attr("action","${ctx}/xiaodian/app/appDataCategory/shopModelSave");
			$("#inputForm").submit();
		}
        // 父级未填写时，判断AppId是否存在
        function toJudge(){
		    var parent = $("#parentId").val();
		    var appId = $("#appId").val();
			if (parent==null || parent=="" || parent =="0"){
				$.get("${ctx}/xiaodian/app/appDataCategory/checkAppId",{"appId":appId},function(data){
					if(data=="success"){
						$("#appIdMsg").text("该appId已使用，不可再作为根节点使用");
						// $("#appId").focus();
					}else{
						if(appId==null||appId==""){
							$("#appIdMsg").text("appId不能未空");
							// $("#appId").focus();
						}else{
							$("#appIdMsg").text("该appId可作根节点使用");
						}
					}
				});
			}
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/app/appDataCategory/">数据分类列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/app/appDataCategory/form?id=${appDataCategory.id}&parent.id=${appDataCategoryparent.id}">数据分类<shiro:hasPermission name="xiaodian:app:appDataCategory:edit">${not empty appDataCategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:app:appDataCategory:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appDataCategory" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" value="${appDataCategory.parent.appId}" htmlEscape="false" maxlength="64" class="input-xlarge " onblur="toJudge()"/>&nbsp;&nbsp;<span id="appIdMsg"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级父级编号:</label>
			<div class="controls" id="choiceCategory">
				<sys:treeselect id="parent" name="parent.id" value="${appDataCategory.parent.id}" labelName="parent.name" labelValue="${appDataCategory.parent.name}"
					title="父级编号" url="/xiaodian/app/appDataCategory/treeData?limitAppId= " extId="${appDataCategory.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类图标：</label>
			<div class="controls">
				<form:hidden  path="logo" htmlEscape="false" maxlength="200" class="input-xlarge "/>
				<sys:ckfinder input="logo" type="thumb" uploadPath="/appData/category/icon"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:app:appDataCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="按单一层级保存" onclick="save()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<br/>
		<c:if test="${empty appDataCategory.id}">
		<div class="addButton">
			<div class="control-group">
				<label class="control-label">模板应用ID：</label>
				<div class="controls">
					<form:select path="modelAppId" class="input-xlarge " >
						<form:option value="" label=""/>
						<form:options items="${shopModels}" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
			<div class="form-actions">
				<shiro:hasPermission name="xiaodian:app:appDataCategory:edit"><input id="btnModel" class="btn btn-primary" type="button" value="按商户模板添加" onclick="shopModle()"/>&nbsp;<span>会同时复制应用数据</span></shiro:hasPermission>
			</div>
		</div>
        </c:if>
	</form:form>
</body>

</html>