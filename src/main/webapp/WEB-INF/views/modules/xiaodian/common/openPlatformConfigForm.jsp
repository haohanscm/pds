<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>开放平台管理</title>
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

            $("#flushAldToken").click(

                function () {
                    var id = $("#id").val();
                    jQuery.ajax({
                        url: '${ctx}/xiaodian/common/openPlatformConfig/ald/flushToken',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            id: id,
                        },
                        success: function (result) {
                            if (result.code == 200) {
                                $("#messageBox").text(result.msg);
                                alert(result.msg);
                                window.location.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            )

            $("#yCloudFlushToken").click(
                function () {
                    var id = $("#id").val();
                    jQuery.ajax({
                        url: '${ctx}/xiaodian/common/openPlatformConfig/ycloud/flushToken',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            id: id,
                        },
                        success: function (result) {
                            if (result.code == 200) {
                                $("#messageBox").text(result.msg);
                                alert(result.msg);
                                window.location.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            )

            $("#yCloudRefreshToken").click(
                function () {
                    var id = $("#id").val();
                    jQuery.ajax({
                        url: '${ctx}/xiaodian/common/openPlatformConfig/ycloud/refreshToken',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            id: id,
                        },
                        success: function (result) {
                            if (result.code == 200) {
                                $("#messageBox").text(result.msg);
                                alert(result.msg);
                                window.location.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            )

            $("#setCallBackUrl").click(
                function () {
                    var id = $("#id").val();
                    jQuery.ajax({
                        url: '${ctx}/xiaodian/common/openPlatformConfig/ycloud/setCallBackUrl',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            id: id,
                        },
                        success: function (result) {
                            if (result.code == 200) {
                                $("#messageBox").text(result.msg);
                                alert(result.msg);
                                window.location.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            )

            $("#cancelCallBackUrl").click(
                function () {
                    var id = $("#id").val();
                    jQuery.ajax({
                        url: '${ctx}/xiaodian/common/openPlatformConfig/ycloud/cancelCallBackUrl',
                        type: 'POST',
                        dataType: "json",
                        data: {
                            id: id,
                        },
                        success: function (result) {
                            if (result.code == 200) {
                                $("#messageBox").text(result.msg);
                                alert(result.msg);
                                window.location.reload();
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            )

		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/common/openPlatformConfig/">开放平台列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/common/openPlatformConfig/form?id=${openPlatformConfig.id}">开放平台<shiro:hasPermission name="xiaodian:common:openPlatformConfig:edit">${not empty openPlatformConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:common:openPlatformConfig:edit">查看</shiro:lacksPermission></a></li>
		<shiro:hasPermission name="xiaodian:common:openPlatformConfig:view"><li><a href="${ctx}/xiaodian/common/openPlatformConfig/iframe?appKey=${openPlatformConfig.appId}">数据看板</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="openPlatformConfig" action="${ctx}/xiaodian/common/openPlatformConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				<c:if test="${openPlatformConfig.appType eq '3' }">
					<input type="button" value="刷新Token" id="flushAldToken" class="btn btn-primary"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用名称：</label>
			<div class="controls">
				<form:input path="appName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用类型：</label>
			<div class="controls">
				<form:select path="appType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用密钥：</label>
			<div class="controls">
				<form:input path="appSecret" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调地址：</label>
			<div class="controls">
				<form:textarea path="callbackUrl" htmlEscape="false" maxlength="300" class="input-xlarge "/>
				<c:if test="${openPlatformConfig.appType eq '4'}">
					<input type="button" value="设置回调地址" id="setCallBackUrl" class="btn btn-primary"/>
				</c:if>
				<c:if test="${openPlatformConfig.appType eq '4'}">
					<input type="button" value="取消回调地址" id="cancelCallBackUrl" class="btn btn-primary"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">访问token：</label>
			<div class="controls">
				<form:textarea path="accessToken" htmlEscape="false" maxlength="100" class="input-xlarge "/>
				<c:if test="${openPlatformConfig.appType eq '4' and empty openPlatformConfig.accessToken}">
					<input type="button" value="获取Token" id="yCloudFlushToken" class="btn btn-primary"/>
				</c:if>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getDictList('common_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">刷新token：</label>
			<div class="controls">
				<form:input path="flushToken" htmlEscape="false" maxlength="50" class="input-xlarge "/>
				<c:if test="${openPlatformConfig.appType eq '4' and not empty openPlatformConfig.flushToken}">
					<input type="button" value="刷新Token" id="yCloudRefreshToken" class="btn btn-primary"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自动账号生成：</label>
			<div class="controls">
				<form:radiobuttons path="autoCreate" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${openPlatformConfig.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${openPlatformConfig.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="memo" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:common:openPlatformConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>