<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台交易流程管理</title>
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
			// 状态改变时 提供操作
			$("#status").change(function () {
                var status = $("#status");
                var btnStatus = $("#btnStatus");
                console.log("status", status.val())
                // 在商品定价阶段 可修改采购单状态为待确认
			    if(status.val() == '4'){
                    btnStatus.attr("style","display: inline");
                    btnStatus.val("确认所有商品已完成报价,修改状态为待确认");
                }else{
                    btnStatus.attr("style","display: none");
                    btnStatus.val("无操作");
                }
            });

			// 初始化
            $("#status").change();
		});

		// 按钮操作
		function operation() {
            $("#btnStatus").attr("disabled","disabled");
		    var url = '';
            if($("#status").val() == '4'){
		        url = '${ctx}/pds/operation/pdsTradeProcess/statusWait';
            }else{
                alertx("状态有误");
                return;
            }
		    var deliveryTime = $("[name='deliveryTime']").val();
		    var buySeq = $("#buySeq").val();

		    // 平台流程操作
            jQuery.ajax({
                url: url,
                type: 'POST',
                dataType: "json",
                data: {
                    buySeq: buySeq,
                    deliveryTime:deliveryTime
                },
                success: function (result) {
                    if (result.code == 200) {
                        alertx(result.msg);
                        window.location.reload();
                    }else{
                        alertx(result.msg);
                    }
                }
            });
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pds/operation/pdsTradeProcess/">平台交易流程列表</a></li>
		<li class="active"><a href="${ctx}/pds/operation/pdsTradeProcess/form?id=${pdsTradeProcess.id}">平台交易流程<shiro:hasPermission name="pds:operation:pdsTradeProcess:edit">${not empty pdsTradeProcess.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:operation:pdsTradeProcess:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="pdsTradeProcess" action="${ctx}/pds/operation/pdsTradeProcess/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">平台商家id：</label>
            <div class="controls">
                <form:input path="pmId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">交易流程号：</label>
			<div class="controls">
				<form:input path="processSn" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购批次：</label>
			<div class="controls">
				<form:select path="buySeq" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_buy_seq')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送货日期：</label>
			<div class="controls">
				<input name="deliveryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${pdsTradeProcess.deliveryTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_trade_process_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>&nbsp;&nbsp;&nbsp;&nbsp;
                <input id="btnStatus" class="btn btn-primary" type="button" onclick="operation()" style="display: none"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="pds:operation:pdsTradeProcess:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>