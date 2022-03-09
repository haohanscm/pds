<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算记录管理</title>
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
			// 结算公司类型 影响 采购商/供应商选择
            $("#companyType").change(function(){
                var pmId = $("#pmId").val();
                var companyType = $("#companyType").val();
                if(!companyType || !pmId){
                    return;
                }
                // 选择结算类型
                $("#settlementType").select2("val", companyType);
                jQuery.ajax({
                    url: '${ctx}/pds/cost/settlementRecord/companyList',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                        companyType: companyType
                    },
                    success: function (result) {
                        var companyId = $("#companyId");
                        var idValue = companyId.val();
                        if (result.code == 200) {
                            var _data = result.ext;
                            // console.log("_data", _data);
                            if (null == _data || ( 0 == _data.length)) {
                                companyId.empty();
                                companyId.append($("<option>").attr({"value": ""}).text("找不到对应结算公司"));
                                companyId.change();
                                alertx("找不到对应结算公司");
                                return;
                            }
                            companyId.empty();
                            $.each(_data, function (n, entity) {
                                companyId.append($("<option>").attr({"value": entity.merchantId}).text(entity.merchantName));
                            });
                            companyId.select2("val", idValue);
                            companyId.change();
                        }else{
                            companyId.empty();
                            companyId.append($("<option>").attr({"value": ""}).text("找不到对应结算公司"));
                        }
                    }
                });
            });

			// 公司id/名称关联
            $("#companyId").change(function () {
                var companyName = this.options[this.options.selectedIndex].text;
                $("#companyName").val(companyName);
            });

            // 平台商家变化 已选公司时刷新
            $("#pmId").change(function () {
                var companyId = $("#companyId").val();
                if(companyId){
                    $("#companyType").change();
                }
            });
            // 有修改时重置数据
            $("#companyId").change(cleanInfo);
            // 图片初始化
            photoInit('settlementImg', '${settlementRecord.settlementImg}');
		});

        // 清空 货款单号及金额
        function cleanInfo(){
            $("#paymentSn").val("");
            $("#settlementAmount").val("");
        }

		// 计算结算金额
        function countAmount(){
            var pmId =  $("#pmId").val();
            var companyType =  $("#companyType").val();
            var companyId =  $("#companyId").val();
            var settlementBeginDate =  $("[name='settlementBeginDate']").val();
            var settlementEndDate =  $("[name='settlementEndDate']").val();
            // console.log("result", (companyType && companyId && settlementBeginDate && settlementEndDate),settlementBeginDate,settlementEndDate);
            if(companyType && companyId && settlementBeginDate && settlementEndDate){
                jQuery.ajax({
                    url: '${ctx}/pds/cost/settlementRecord/countPayment',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        pmId: pmId,
                        companyType: companyType,
                        companyId: companyId,
                        settlementBeginDate: settlementBeginDate,
                        settlementEndDate: settlementEndDate
                    },
                    success: function (result) {
                        // console.log("result", result);
                        if (result.code == 200) {
                            var _data = result.ext;
                            // console.log("_data", _data);
                            $("#settlementAmount").val(_data.settlementAmount);
                            $("#paymentSn").val(_data.paymentSn);
                            alertx("操作成功");
                        }else{
                            alertx("找不到对应信息");
                        }
                    }
                });
            }else{
                alertx("请选择结算公司类型/结算公司/结算的开始和结束时间");
            }
        }

        function photoInit(select, photoUrl) {
            photoPreview(select);
            if(photoUrl){
                $("#"+ select +"-image-preview").css("background-image", "url("+ photoUrl +")");
                btnMsg(select);
            }
        }

        // 图片预览
        function photoPreview(select) {
            var
                fileInput = document.getElementById(select + '-file'),
                info = document.getElementById(select + '-file-info'),
                preview = document.getElementById(select + '-image-preview'),
                fileValue = document.getElementById(select);
            // 监听change事件:
            fileInput.addEventListener('change', function () {
                // 清除背景图片:
                preview.style.backgroundImage = '';
                // 清除图片资源库id
                fileValue.value = '';
                btnMsg(select);
                // 检查文件是否选择:
                if (!fileInput.value) {
                    info.innerHTML = '没有选择文件';
                    return;
                }
                // 获取File引用:
                var file = fileInput.files[0];
                if (file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif'
                    && file.type !== 'image/jpg' && file.type !== 'image/bmp') {
                    alert('不是有效的图片文件!');
                    fileInput.value = '';
                    return;
                }
                // 获取File信息:
                info.innerHTML = '文件: ' + file.name + ' ' +
                    '大小: ' + Math.round(file.size/1000) + ' ' + ' KB'
                // 读取文件:
                var reader = new FileReader();
                reader.onload = function(e) {
                    var data = e.target.result;
                    preview.style.backgroundImage = 'url(' + data + ')';
                };
                // 以DataURL的形式读取文件:
                reader.readAsDataURL(file);
            });
        }

        // 图片上传
        function fileUpload(select){
            var file = document.getElementById(select+"-file").files[0];
            if(!file){
                alertx("未选择图片");
                return;
            }
            var selectValue = $("#"+select).val();
            if(selectValue){
                alertx("图片已上传");
                return;
            }
            // 不允许连续点击
            $("#"+select+"-file-upload").attr("disabled","disabled");
            // 平台商家 账单图片
            var
                merchantId = $("#pmId").val() || "commonPhotos",
                type = "08",
                formData = new FormData();
            formData.append("file", file);

            jQuery.ajax({
                url: '${ctx}/xiaodian/common/photoGallery/uploadPhoto/'+ merchantId + '/' + type,
                type: 'POST',
                dataType: "json",
                contentType: false,
                processData: false,
                data: formData,
                success: function (result) {
                    if(result.code == 200){
                        var photoGallery = JSON.parse(result.ext);
                        console.log("photoGallery", photoGallery.src);
                        $("#"+select).val(photoGallery.src);
                        btnMsg(select);
                    }else{
                        console.log(result.msg);
                        alertx(result.msg);
                    }
                    $("#"+select+"-file-upload").removeAttr("disabled");
                }
            });
        }
        // 图片上传按钮 显示内容
        function btnMsg(select) {
            var photoId = $("#"+select).val();
            if(photoId){
                $("#"+select+"-file-upload").text("图片已上传");
            }else{
                $("#"+select+"-file-upload").text("上传图片");
            }
        }
    </script>
    <style>
        .photo-style{
            border: 1px solid rgb(204, 204, 204);
            width: 150px;
            height: 150px;
            background: center/contain no-repeat;
        }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pds/cost/settlementRecord/">结算记录列表</a></li>
		<li class="active"><a href="${ctx}/pds/cost/settlementRecord/form?id=${settlementRecord.id}">结算记录<shiro:hasPermission name="pds:cost:settlementRecord:edit">${not empty settlementRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pds:cost:settlementRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settlementRecord" action="${ctx}/pds/cost/settlementRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">结算单号：</label>
            <div class="controls">
                <form:input path="settlementId" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">平台商家：</label>
            <div class="controls">
                <form:select path="pmId" class="input-xlarge required" >
                    <form:options items="${pmList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">结算公司类型：</label>
            <div class="controls">
                <form:select path="companyType" class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('pds_company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">结算类型：</label>
			<div class="controls">
				<form:select path="settlementType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('pds_settlement_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">结算公司：</label>
            <div class="controls">
                <form:select path="companyId" class="input-xlarge required">
                    <form:option value="${settlementRecord.companyId}" label="${settlementRecord.companyName}"/>
                </form:select>
                <span>该商家下的所有采购商/供应商货款</span>
            </div>
        </div>
        <div class="control-group" style="display:none;">
            <label class="control-label">结算公司名称：</label>
            <div class="controls">
                <form:input path="companyName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">结算开始日期：</label>
			<div class="controls">
				<input name="settlementBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${settlementRecord.settlementBeginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, onpicked:cleanInfo});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算结束日期：</label>
			<div class="controls">
				<input name="settlementEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${settlementRecord.settlementEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false, onpicked:cleanInfo});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款日期：</label>
			<div class="controls">
				<input name="payDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${settlementRecord.payDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">货款单号列表：</label>
            <div class="controls">
                <form:textarea path="paymentSn" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge required" readonly="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">结算金额：</label>
            <div class="controls">
                <form:input path="settlementAmount" htmlEscape="false" class="input-xlarge required" readonly="true"/>
                <input id="btnAmount" class="btn btn-primary" type="button" value="获取结算金额" onclick="countAmount()"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">结款人名称：</label>
			<div class="controls">
				<form:input path="companyOperator" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算凭证图片：</label>
			<%--<div class="controls">--%>
				<%--<form:hidden id="settlementImg" path="settlementImg" htmlEscape="false" maxlength="500" class="input-xlarge"/>--%>
				<%--<sys:ckfinder input="settlementImg" type="images" uploadPath="/pds/cost/settlementRecord"  selectMultiple="true"/>--%>
			<%--</div>--%>
            <div class="controls">
                <form:textarea path="settlementImg" htmlEscape="false" maxlength="255" rows="3" class="input-xxlarge " readonly="true"/>
                <p><input type="file" id="settlementImg-file" name="settlementImg-file" class="input-medium " />
                    <button type="button" id="settlementImg-file-upload" onclick="fileUpload('settlementImg')" >上传图片</button>
                </p>
                <div id="settlementImg-image-preview" class="photo-style" ></div>
                <p id="settlementImg-file-info">没有选择文件</p>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label">结算说明：</label>
			<div class="controls">
                <form:textarea path="settlementDesc" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办人名称：</label>
			<div class="controls">
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="pds:cost:settlementRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>