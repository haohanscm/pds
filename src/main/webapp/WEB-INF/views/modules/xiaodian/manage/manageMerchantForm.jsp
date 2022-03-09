<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/manage/merchant/">商家列表</a></li>
    <li class="active"><a href="${ctx}/xiaodian/manage/merchant/form?id=${merchant.id}">商家<shiro:hasPermission
            name="xiaodian:manage:merchant:edit">${not empty merchant.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:manage:merchant:edit">查看</shiro:lacksPermission></a></li>
    <shiro:hasPermission name="xiaodian:manage:merchant:edit"><li><a href="${ctx}/xiaodian/merchant/photoManage">商家资料添加</a></li></shiro:hasPermission>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="merchant" action="${ctx}/xiaodian/merchant/save" method="post"
<form:form id="inputForm" modelAttribute="merchant" action="${ctx}/xiaodian/merchant/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>

    <div class="control-group">
        <label class="control-label">merchantID：</label>
        <div class="controls">${merchant.id}</div>
    </div>
    <div class="control-group">
        <label class="control-label">归属用户信息：</label>
        <div class="controls">
            用户ID: <form:input path="upassport.id" maxlength="100" class="input-xlarge "/>
            <c:if test="${not empty upassport.id}" >
                <br/>
                用户名：   <c:out value="${merchant.upassport.loginName}"/>
            </c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">名称：</label>
        <div class="controls">
            <form:input path="merchantName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
            <shiro:hasPermission name="xiaodian:manage:merchant:edit">
                <td>
                    <c:if test="${not empty merchant.id}">
                        <a href="${ctx}/xiaodian/merchant/photoManage?id=${merchant.id}">商家资料<shiro:lacksPermission
                                name="xiaodian:merchant:edit">查看</shiro:lacksPermission></a>
                    </c:if>
                </td>
            </shiro:hasPermission>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">厂商渠道：</label>
        <div class="controls">
            <form:select id="partnerNum" path="partnerNum" class="input-medium">
                <form:option value="" label=""/>
                <c:forEach items="${partnerAppList}" var="partnerApp">
                    <form:option  value="${partnerApp.partnerNum}" label="${partnerApp.partnerName}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">区域：</label>
        <div class="controls">
            <sys:treeselect id="area" name="area.id" value="${merchant.area.id}" labelName="area.name"
                            labelValue="${merchant.area.name}"
                            title="区域" url="/sys/area/treeData" cssClass="" allowClear="true"
                            notAllowSelectParent="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商家地址：</label>
        <div class="controls">
            <form:textarea path="address" htmlEscape="false" maxlength="500" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商家类型：</label>
        <div class="controls">
            <form:select path="merchantType" class="input-medium ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">联系人：</label>
        <div class="controls">
            <form:input path="contact" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电话：</label>
        <div class="controls">
            <form:input path="telephone" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">行业名称：</label>
        <div class="controls">
            <form:input path="industry" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">规模：</label>
        <div class="controls">
            <form:input path="scale" htmlEscape="false" maxlength="50" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">业务介绍：</label>
        <div class="controls">
            <form:textarea path="bizDesc" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">业务专管员：</label>
        <div class="controls">
            <sys:treeselect id="user" name="user.id" value="${merchant.user.id}" labelName="user.name"
                            labelValue="${merchant.user.name}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true"
                            notAllowSelectParent="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">状态：</label>
        <div class="controls">
            <form:radiobuttons path="status" items="${fns:getDictList('status_merchant')}" itemLabel="label"
                               itemValue="value" htmlEscape="false" class=""/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="xiaodian:merchant:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                                  value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>