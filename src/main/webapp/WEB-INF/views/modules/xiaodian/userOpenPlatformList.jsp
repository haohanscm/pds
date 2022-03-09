<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function () {
            //hover某处显示悬浮框
            $(".showQrcode").mouseenter(function (elem) {
                var fetchDiv = $(this).find("div");
                // 获取应用的二维码 没有图片地址时去后台查询
                var qrcodeUrl = fetchDiv.find("img");
                var appId = fetchDiv.find("input").val();
                if(qrcodeUrl!=null && qrcodeUrl.attr("src")==""){
                    qrcodeUrl.attr("src",$("#qrCode").val());
                    <%--$.getJSON("${ctxFront}/xiaodian/api/wx/miniapp/fetchQrCode",{"appId":appId},function(data){--%>
                        <%--if(data!=null && data.ext!=""){--%>
                            <%--qrcodeUrl.attr("src",data.ext);--%>
                        <%--}--%>
                        <%--qrcodeUrl.attr("alt","该应用二维码未生成，请去生成：应用管理=》应用列表=》修改=》获取小程序码=》保存");--%>
                    <%--});--%>
                }
                //获取鼠标位置函数
                var mousePos = mousePosition(elem);
                var xOffset = 200;
                var yOffset = 100;
                fetchDiv.css("display", "block").css("position", "absolute").css("top", (mousePos.y - yOffset) + "px").css("left", (mousePos.x + xOffset) + "px");
            });
            //鼠标离开表格隐藏悬浮框
            $(".showQrcode").mouseleave(function () {
                $(this).find("div").css("display", "none");
            });


            //同步微信公众号用户
            $("#syncMpUser").click(function () {
                // 不允许连续点击
                $("#syncMpUser").attr("disabled","disabled");
                var appid = $("#appId").val();
                if(null == appid || '' == appid){
                    alertx("没有填写AppId");
                    $("#syncMpUser").removeAttr("disabled");
                    return ;
                }
                loading("信息同步中,请稍后!");
                jQuery.ajax({
                    url: '${ctx}/xiaodian/userOpenPlatform/syncMpUser',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        appId: appid,
                    },
                    success: function (result) {
                        $("#syncMpUser").removeAttr("disabled");
                        closeLoading();
                        alertx(result.msg);
                    }
                });
            })

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        // 清空查询条件
        function toReset() {
            $("#uid").val("");
            $("#appId").select2("val", "");
            $("#appType").select2("val", "");
            $("#status").select2("val", "");
            $("#openId").val("");
            $("#nickName").val("");
            $("[name='beginCreateTime']").val("");
            $("[name='endCreateTime']").val("");
            $("[name='beginUpdateDate']").val("");
            $("[name='endUpdateDate']").val("");
        }

        //获取鼠标坐标
        function mousePosition(ev) {
            ev = ev || window.event;
            if (ev.pageX || ev.pageY) {
                return {x: ev.pageX, y: ev.pageY};
            }
            return {
                x: ev.clientX + document.body.scrollLeft - document.body.clientLeft,
                y: ev.clientY + document.body.scrollTop - document.body.clientTop
            };
        }

    </script>
    <style type="text/css">
        .hoverDiv {width:200px;height:250px;display:none;background-color:#f0f0f0;color:#FF0000;line-height:20px;border:2px solid #000000;padding:5px;}
        .showAppName {color:#000000;font-size:18px;}
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/xiaodian/userOpenPlatform/">用户列表</a></li>
		<shiro:hasPermission name="xiaodian:userOpenPlatform:edit"><li><a href="${ctx}/xiaodian/userOpenPlatform/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userOpenPlatform" action="${ctx}/xiaodian/userOpenPlatform/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="uid" htmlEscape="false" maxlength="60" class="input-medium"/>
			</li>
			<li><label>应用名称：</label>
				<form:select path="appId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${appList}" itemValue="appId" itemLabel="appName" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>应用类型：</label>
				<form:select path="appType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_channel')}" itemValue="value" itemLabel="label" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>用户状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('open_user_status')}" itemValue="value" itemLabel="label" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>第三方标识：</label>
				<form:input path="openId" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>用户昵称：</label>
				<form:input path="nickName" htmlEscape="false" maxlength="500" class="input-medium" placeholder="可模糊查询"/>
			</li>
			<li><label>注册时间：</label>
                <input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                    value="<fmt:formatDate value="${userOpenPlatform.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
                <input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                    value="<fmt:formatDate value="${userOpenPlatform.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
			<li><label>更新时间：</label>
				<input name="beginUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userOpenPlatform.beginUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endUpdateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${userOpenPlatform.endUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                <input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/>
                <input id="syncMpUser" class="btn btn-primary" type="button" value="同步微信用户" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户ID</th>
				<th>用户昵称</th>
				<th style="width: 50px">头像</th>
				<th style="width: 50px">性别</th>
				<th>应用ID</th>
				<th>应用名称</th>
				<th>应用类型</th>
				<th>用户状态</th>
				<th>注册时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="xiaodian:userOpenPlatform:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userOpenPlatform" varStatus="temp">
			<tr>
				<td><a href="${ctx}/xiaodian/userOpenPlatform/form?id=${userOpenPlatform.id}">
					${userOpenPlatform.uid}
				</a></td>
				<td>
					${userOpenPlatform.nickName}
				</td>
                <td>
                    <img src="${userOpenPlatform.albumUrl}" style="height: 50px;width: 50px;" />
                </td>
                <td>
                     ${fns:getDictLabel(userOpenPlatform.sex, 'sex', '')}
                </td>
				<td>
					${userOpenPlatform.appId}
				</td>
				<td style="display: none">
					<input type="hidden" id="qrCode" value="${userOpenPlatform.qrcode}">
				</td>
				<td class="showQrcode">
                    ${userOpenPlatform.appName}
                    <div class="hoverDiv" >
                        <input type="hidden" value="${userOpenPlatform.appId}"/>
						扫描下方二维码进入小程序:
                        <p class="showAppName">${userOpenPlatform.appName}</p>
                        <img src="" width="200px">
                    </div>
				</td>
				<td>
				    ${fns:getDictLabel(userOpenPlatform.appType, 'app_channel', '')}
				</td>
				<td>
						${fns:getDictLabel(userOpenPlatform.status, 'open_user_status','使用中')}
				</td>
				<td>
					<fmt:formatDate value="${userOpenPlatform.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${userOpenPlatform.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="xiaodian:userOpenPlatform:edit"><td>
    				<a href="${ctx}/xiaodian/userOpenPlatform/form?id=${userOpenPlatform.id}">修改</a>
					<a href="${ctx}/xiaodian/userOpenPlatform/delete?id=${userOpenPlatform.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>