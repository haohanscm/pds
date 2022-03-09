<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>微信消息事件管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/weixin/mp/wechatUserMsgevent/">微信消息事件列表</a></li>
    <shiro:hasPermission name="mp:wechatUserMsgevent:edit">
        <li><a href="${ctx}/weixin/mp/wechatUserMsgevent/form">微信消息事件添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="wechatUserMsgevent" action="${ctx}/weixin/mp/wechatUserMsgevent/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>消息ID：</label>
            <form:input path="msgId" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>授权微信：</label>
            <form:input path="openWxName" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>授权微信ID：</label>
            <form:input path="openWxId" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>微信ID：</label>
            <form:input path="wxId" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>微信名称：</label>
            <form:input path="wxName" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>微信类型：</label>
            <form:select path="wxType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('app_service_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>通行证ID：</label>
            <form:input path="passportUid" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>开放用户ID：</label>
            <form:input path="openUid" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>用户昵称：</label>
            <form:input path="nickName" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>openid：</label>
            <form:input path="openId" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>unionid：</label>
            <form:input path="unionId" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>消息类型：</label>
            <form:select path="msgType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('weixin_msg_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>消息名称：</label>
            <form:select path="msgName" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('weixin_msg_name')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>发送时间：</label>
            <input name="beginSendTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${wechatUserMsgevent.beginSendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
            <input name="endSendTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${wechatUserMsgevent.endSendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>消息ID</th>
        <th>用户昵称</th>
        <th>用户头像</th>
        <th>微信名称</th>
        <th>微信类型</th>
        <th>通行证ID</th>
        <th>消息类型</th>
        <th>消息名称</th>
        <th>消息内容</th>
        <th>发送时间</th>
        <th>创建时间</th>
        <shiro:hasPermission name="mp:wechatUserMsgevent:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="wechatUserMsgevent">
        <tr>
            <td><a href="${ctx}/weixin/mp/wechatUserMsgevent/form?id=${wechatUserMsgevent.id}">
                    ${wechatUserMsgevent.msgId}
            </a></td>
            <td>
                    ${wechatUserMsgevent.nickName}
            </td>
            <td>
                <img src="${wechatUserMsgevent.albumUrl}" style="width:50px;height:50px"/>
            </td>
            <td>
                    ${wechatUserMsgevent.wxName}
            </td>
            <td>
                    ${fns:getDictLabel(wechatUserMsgevent.wxType,"app_service_type" ,wechatUserMsgevent.wxType)}
            </td>
            <td>
                    ${wechatUserMsgevent.passportUid}
            </td>
            <td>
                    ${fns:getDictLabel(wechatUserMsgevent.msgType, 'weixin_msg_type', wechatUserMsgevent.msgType)}
            </td>
            <td>
                    ${fns:getDictLabel(wechatUserMsgevent.msgName, 'weixin_msg_name', wechatUserMsgevent.msgName)}
            </td>
            <td>
                <c:if test="${wechatUserMsgevent.msgName eq 'image'}">
                    <img src="${wechatUserMsgevent.msgContent}" style="width: 50px;height: 50px">
                </c:if>
                <c:if test="${wechatUserMsgevent.msgName ne 'image'}">
                    ${wechatUserMsgevent.msgContent}
                </c:if>
            </td>
            <td>
                <fmt:formatDate value="${wechatUserMsgevent.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${wechatUserMsgevent.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <shiro:hasPermission name="mp:wechatUserMsgevent:edit">
                <td>
                    <a href="${ctx}/weixin/mp/wechatUserMsgevent/form?id=${wechatUserMsgevent.id}">修改</a>
                    <a href="${ctx}/weixin/mp/wechatUserMsgevent/delete?id=${wechatUserMsgevent.id}"
                       onclick="return confirmx('确认要删除该微信消息事件吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>