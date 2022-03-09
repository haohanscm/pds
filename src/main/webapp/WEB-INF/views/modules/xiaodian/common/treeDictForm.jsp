<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>字典信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#name").focus();
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

//            alert($("#category").options.selectedIndex.value);

        });

        //级联
        function changeAttr(e) {
            var categroyId = e.options[e.options.selectedIndex].value;
            var data;
            if (categroyId == '00') {
                data = ${fns:toJson(fns:getDictList('dict_category_op'))};
            }
            if (categroyId == '01') {
                data = ${fns:toJson(fns:getDictList('sys_area_type'))};
            }
            var attr = $("#attr");
            attr.empty();
            for (var i = 0; i < data.length; i++) {
                var option = $("<option>").attr({"value" : data[i].value}).text(data[i].label);
                attr.append(option);
//                alert(data[i].value + data[i].label);
            }
            attr.change();

        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/common/treeDict/">字典信息列表</a></li>
    <li class="active"><a href="${ctx}/xiaodian/common/treeDict/form?id=${treeDict.id}&parent.id=${treeDictparent.id}">字典信息<shiro:hasPermission
            name="xiaodian:common:treeDict:edit">${not empty treeDict.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:common:treeDict:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="treeDict" action="${ctx}/xiaodian/common/treeDict/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">上级父级编号:</label>
        <div class="controls">
            <sys:treeselect id="parent" name="parent.id" value="${treeDict.parent.id}" labelName="parent.name"
                            labelValue="${treeDict.parent.name}"
                            title="父级编号" url="/xiaodian/common/treeDict/treeData" extId="${treeDict.id}" cssClass=""
                            allowClear="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">编码：</label>
        <div class="controls">
            <form:input path="code" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">类别：</label>
        <div class="controls">
            <form:select id="category" path="type" class="input-xlarge" onchange="changeAttr(this)">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('dict_category')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">属性：</label>
        <div class="controls">
            <form:select id="attr" path="attr" class="input-xlarge">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('dict_category_op')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
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
        <shiro:hasPermission name="xiaodian:common:treeDict:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                         type="submit"
                                                                         value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>