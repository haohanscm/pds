<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>数据对象管理</title>
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
            // 富文本格式设置
            var fckvalue =  $("#fckvalue img");
            var oldWidth = fckvalue.width();
            var oldHeight = fckvalue.height();
            var proportion = oldWidth/oldHeight;
            fckvalue.height(150).width(150*proportion);
            // 恢复提示框显示
            resetTip();
        });
    </script>
    <style>
        #contentTable tbody pre{
            height:150px;
            overflow:scroll;
        }

    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/app/appData/">数据对象列表</a></li>
    <li><a href="${ctx}/xiaodian/app/appData/addData?id=${appData.id}">数据添加</a></li>
    <li class="active"><a href="${ctx}/xiaodian/app/appData/dataList?id=${appData.id}">数据管理列表</a></li>
</ul>
<sys:message content="${message}"/>
<div class="form-horizontal">
    <div class="control-group">
        <label class="control-label">应用名称：</label>
        <div class="controls">
            ${appMap[appData.appId]}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">应用ID：</label>
        <div class="controls">
            ${appData.appId}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">数据对象ID：</label>
        <div class="controls">
            ${appData.id}
            <input id="btnCancel" class="btn btn-primary" type="button" value="返回数据对象列表" style="float: right;margin-right:100px" onclick="window.location.href='${ctx}/xiaodian/app/appData/?appId=${appData.appId}'"/>
        </div>
    </div>
</div>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>行号 (控制行的排序可修改)</th>
        <th>对象名称</th>
        <c:forEach items="${mapDataList}" var="data" begin="0" end="0">
         <c:forEach items="${data.value}" var="manage">
            <th>${manage.fieldName}</th>
        </c:forEach>
        </c:forEach>
        <shiro:hasPermission name="xiaodian:app:appDataManage:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mapDataList}" var="data">
        <tr>
            <td style="width:250px">
                <a href="${ctx}/xiaodian/app/appData/editData/${data.key}?id=${appData.id}">${data.key}</a>
            </td>
            <td>
                    ${appData.objName}
            </td>

            <c:forEach items="${data.value}" var="appDataManage">
                    <c:if test="${1 == appDataManage.fieldType  && not empty appDataManage.fieldValue}">
                        <td>
                            <img src="${appDataManage.fieldValue}" alt="图片-${appDataManage.fieldName}" style="height: 150px;width: 150px;">
                        </td>
                    </c:if>
                <c:if test="${1 == appDataManage.fieldType  &&  empty appDataManage.fieldValue}">
                    <td style="max-width: 300px;overflow: hidden;">
                            ${appDataManage.fieldValue}
                    </td>
                </c:if>

                    <c:if test="${2 == appDataManage.fieldType || 0 == appDataManage.fieldType}">
                        <td style="max-width: 300px;overflow: hidden;">
                                ${appDataManage.fieldValue}
                        </td>
                    </c:if>

                    <c:if test="${3 == appDataManage.fieldType}">
                        <td id="fckvalue">
                            <p style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;width: 100px;"> ${fns:unescapeHtml(appDataManage.editValue)}</p>
                        </td>
                    </c:if>
            </c:forEach>

            <shiro:hasPermission name="xiaodian:app:appDataManage:edit">
                <td>
                    <a href="${ctx}/xiaodian/app/appData/editData/${data.key}?id=${appData.id}">修改</a>
                    <a href="${ctx}/xiaodian/app/appData/deleteData/${data.key}?id=${appData.id}"
                       onclick="return confirmx('确认要删除该数据对象吗？', this.href)">删除</a>
                    <a href="${ctx}/xiaodian/app/appData/copySeq?seqNum=${data.key}&id=${appData.id}">复制该行数据</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>