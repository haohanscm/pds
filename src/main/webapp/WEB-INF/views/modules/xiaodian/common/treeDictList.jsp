<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>字典信息管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
            var data = ${fns:toJson(list)}, ids = [], rootIds = [];
            for (var i = 0; i < data.length; i++) {
                ids.push(data[i].id);
            }
            ids = ',' + ids.join(',') + ',';
            for (var i = 0; i < data.length; i++) {
                if (ids.indexOf(',' + data[i].parentId + ',') == -1) {
                    if ((',' + rootIds.join(',') + ',').indexOf(',' + data[i].parentId + ',') == -1) {
                        rootIds.push(data[i].parentId);
                    }
                }
            }
            for (var i = 0; i < rootIds.length; i++) {
                addRow("#treeTableList", tpl, data, rootIds[i], true);
            }
            $("#treeTable").treeTable({expandLevel: 5});
        });
        function addRow(list, tpl, data, pid, root) {
           var op= ${fns:toJson(fns:getDictList('dict_category_op'))};
            var area = ${fns:toJson(fns:getDictList('sys_area_type'))};
            for (var i = 0; i < data.length; i++) {
                var row = data[i];
                if ((${fns:jsGetVal('row.parentId')}) == pid) {
                    $(list).append(Mustache.render(tpl, {
                        dict: {
                            type: getDictLabel(${fns:toJson(fns:getDictList('dict_category'))}, row.type),
                            label: getDictLabel(((row.type == '00')?op:area), row.attr),
                            blank123: 0
                        }, pid: (root ? 0 : pid), row: row
                    }));
                    addRow(list, tpl, data, row.id);
                }
            }
        }
        function toReset() {
            $("#name").val("");
            $("#type").select2("val","");
            $("#attr").val("");
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/xiaodian/common/treeDict/">字典信息列表</a></li>
    <shiro:hasPermission name="xiaodian:common:treeDict:edit">
        <li><a href="${ctx}/xiaodian/common/treeDict/form">字典信息添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="treeDict" action="${ctx}/xiaodian/common/treeDict/" method="post"
           class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li><label>类别：</label>
            <form:select path="type" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('dict_category')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>属性：</label>
            <form:input path="attr" htmlEscape="false" maxlength="10" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="toReset()"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="treeTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>名称</th>
        <th>编码</th>
        <th>类别</th>
        <th>属性</th>
        <th>更新时间</th>
        <th>备注信息</th>
        <shiro:hasPermission name="xiaodian:common:treeDict:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody id="treeTableList"></tbody>
</table>
<script type="text/template" id="treeTableTpl">
    <tr id="{{row.id}}" pId="{{pid}}">
        <td><a href="${ctx}/xiaodian/common/treeDict/form?id={{row.id}}">
            {{row.name}}
        </a></td>
        <td>
            {{row.code}}
        </td>
        <td>
            {{row.type}}
            {{dict.type}}
        </td>
        <td>
            {{row.attr}}
            {{dict.label}}
        </td>
        <td>
            {{row.updateDate}}
        </td>
        <td>
            {{row.remarks}}
        </td>
        <shiro:hasPermission name="xiaodian:common:treeDict:edit">
            <td>
                <a href="${ctx}/xiaodian/common/treeDict/form?id={{row.id}}">修改</a>
                <a href="${ctx}/xiaodian/common/treeDict/delete?id={{row.id}}"
                   onclick="return confirmx('确认要删除该字典信息及所有子字典信息吗？', this.href)">删除</a>
                <a href="${ctx}/xiaodian/common/treeDict/form?parent.id={{row.id}}">添加下级字典信息</a>
            </td>
        </shiro:hasPermission>
    </tr>
</script>
</body>
</html>