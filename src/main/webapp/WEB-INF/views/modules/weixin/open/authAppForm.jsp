<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用管理</title>
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

		$("#flush").click(

		    function () {
                var id = $("#id").val();
                jQuery.ajax({
                    url: '${ctx}/weixin/open/authApp/flushToken',
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

        // 获取小程序码：
        function fetchQrcode() {
            // 不允许连续点击
            $("#fetchQrcodeBtn").attr("disabled", "disabled");
            $("#fetchQrcodeBtn").text("请等待");
            var page = $("#miniQrcodePage").val();
            var appId = $("#appId").val();
            var qrcodeUrl;
            $.getJSON("${ctx}/weixin/open/authApp/fetchQrcode/"+appId, {
                "page": page
            }, function (data) {
                if (data.code == 200) {
                    qrcodeUrl = data.ext;
                    $("#appQrcodeUrlMsg").text("");
                    $("#appQrcodeErrorMsg").text("");
                } else {
                    qrcodeUrl = "";
                    $("#appQrcodeUrlMsg").text("获取小程序码失败");
                    $("#appQrcodeErrorMsg").text(JSON.stringify(data));
                }
                $("#qrcode").val(qrcodeUrl);
                $("#imgQrcodeUrl").attr("src", qrcodeUrl);
                $("#fetchQrcodeBtn").text("获取小程序码");
                $("#fetchQrcodeBtn").removeAttrs("disabled");
            });
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/weixin/open/authApp/">应用列表</a></li>
		<li class="active"><a href="${ctx}/weixin/open/authApp/form?id=${authApp.id}">应用<shiro:hasPermission name="weixin:open:authApp:edit">${not empty authApp.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="weixin:open:authApp:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="authApp" action="${ctx}/weixin/open/authApp/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">应用ID：</label>
			<div class="controls">
				<form:input path="appId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权应用ID：</label>
			<div class="controls">
				<form:input path="authAppId" htmlEscape="false" maxlength="64" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权应用名称：</label>
			<div class="controls">
				<form:input path="authAppName" htmlEscape="false" maxlength="64" class="input-xlarge" disabled="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">应用秘钥：</label>
			<div class="controls">
				<form:input path="appSecret" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">访问token：</label>
			<div class="controls">
				<form:input path="accessToken" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">刷新token：</label>
			<div class="controls">
				<form:input path="flushToken" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
				<input type="button" value="刷新Token" id="flush"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权值列表：</label>
			<div class="controls">
				<form:input path="authCode" htmlEscape="false" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用名称：</label>
			<div class="controls">
				<form:input path="appName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用头像地址：</label>
			<div class="controls">
				<form:input path="appIcon" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务类型：</label>
			<div class="controls">
				<form:select path="serviceType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_service_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="serviceType" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">效验类型：</label>
			<div class="controls">

				<form:select path="verifyType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_verify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="verifyType" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原appId：</label>
			<div class="controls">
				<form:input path="originalAppid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主体名称：</label>
			<div class="controls">
				<form:input path="principalName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<c:if test="${authApp.serviceType eq 2}">
		<div class="control-group">
			<label class="control-label">微信号：</label>
			<div class="controls">
				<form:input path="weixinId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">二维码地址：</label>
			<div class="controls">
				<form:input path="qrcode" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>

		<c:if test="${authApp.serviceType eq 0}">
	<div class="control-group">
		<label class="control-label">二维码地址：</label>
		<div class="controls">
		<img src="${authApp.qrcode} " id="imgQrcodeUrl" style="height: 150px;width: 150px;">
		</div>

		<label class="control-label">小程序页面路径：</label>
		<div class="controls">
			<input type="text" id="miniQrcodePage" />
			<button id="fetchQrcodeBtn" class="btn btn-primary" type="button" onclick="fetchQrcode()">获取小程序码</button>&nbsp;&nbsp;&nbsp;<span id="appQrcodeUrlMsg"></span>&nbsp;&nbsp;&nbsp;<span id="appQrcodeErrorMsg" style="display: none"></span>
		</div>
	</div>
		</c:if>

		<c:if test="${authApp.serviceType eq 2}">
		<div class="control-group">
			<label class="control-label">二维码地址：</label>
			<div class="controls">
				<img src="https://open.weixin.qq.com/qr/code?username=${authApp.originalAppid}" style="height: 150px;width: 150px;"/>
			</div>
		</div>
	</c:if>

		<div class="control-group">
			<label class="control-label">有效期：</label>
			<div class="controls">
				<form:input path="expiresin" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权时间：</label>
			<div class="controls">
				<input name="authTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${authApp.authTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权信息：</label>
			<div class="controls">
				<form:textarea path="authInfo" htmlEscape="false" maxlength="20000" class="input-xlarge "/>
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
		<div class="form-actions">
			<shiro:hasPermission name="weixin:open:authApp:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>