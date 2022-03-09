<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据对象管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		    // 变更应用时,修改下拉框选项
		    $("#appId").change(function () {
		        var appId = $("#appId").select2("val");
                $("#appDataId").select2("val","");
                $.getJSON("${ctx}/xiaodian/app/appData/findAppData", {"appId": appId}, function (data) {
                    // 修改选项值
                    $("#appDataId").html("");
                    $("#appDataId").append('<option value=""></option>');
                    for (var key in data) {
                        $("#appDataId").append('<option value="' + key + '">'+ data[key] +'</option>');
                    }
                    $("#appDataId").select2().change();
                })
            });
		    $("#btnSubmit").click(function () {
		        var id = $("#appDataId").select2("val");
		        var appDataId = $("#originAppDataId").val();
		        if(id!=""){
                    $("#inputForm").attr("action","${ctx}/xiaodian/app/appData/copySeqSave?objId="+appDataId);
                    $("#inputForm").submit();
                }else{
		            alert("没有选择目标数据对象!");
                }
            });
			
		});
	</script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/xiaodian/app/appData/">数据对象列表</a></li>
        <li class="active"><a href="${ctx}/xiaodian/app/appData/copySeq?seqNum=${appDataManageInfo.seqNum}&id=${appDataManageInfo.appDataId}">数据对象复制</a></li>
    </ul>
    <br/>
    <form:form id="inputForm" modelAttribute="appDataManageInfo" action="" method="post" class="form-horizontal">
        <sys:message content="${message}"/>
        <input id="originAppDataId" type="hidden" value="${appDataManageInfo.appDataId}">
        <div class="control-group">
            <label class="control-label">应用名称：</label>
            <div class="controls">
                <form:select path="appId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${appMap}" htmlEscape="false"/>
                </form:select>
                &nbsp;&nbsp;&nbsp;<span style="color: #FF0000">点击选择数据复制后的目标</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">原数据对象名称：</label>
            <div class="controls">
                <input name="objName" type="text"  value="${appData.objName}" class="input-xlarge " readonly="readonly"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">目标数据对象：</label>
            <div class="controls">
                <form:select path="appDataId" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${appDataList}" itemLabel="objName" itemValue="id" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">行号：</label>
            <div class="controls">
                <form:input path="seqNum" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
            </div>
        </div>
        <div class="form-actions">
            <shiro:hasPermission name="xiaodian:app:appDataManage:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>

    </form:form>

</body>
</html>