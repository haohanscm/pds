<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家支付信息管理</title>
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

            var _message = "${tip_message}".trim();
            if (_message){
                alertx(_message);
            };

            var status = $("#regStatus");
            status = status.find("option:selected").val();
            if (status == "1") {
                $("#configAgrNo").attr("disabled", true).css("display", "none");
            }
            $("#configAgrNo").click(function () {
                    var agrNo = randomString(16);
                    $("#agrNo").val(agrNo);
                }
            );

            var treeDictType = {
                wxop: "00",
                area: "01",
            };


            var opData;
            //微信经营类目三级联
            var L1 = $("#mercTrdCls");
            var L2 = $("#mercSubCls");
            var L3 = $("#mercThirdCls");

            jQuery.ajax({
                url: '${ctx}/xiaodian/common/treeDict/fetchChildrens',
                type: 'POST',
                dataType: "json",
                data: {
                    type: treeDictType.wxop,
                },
                success: function (result) {
                    if (result.code == 200) {
                        var _data = result.data;
                        _data = $.parseJSON(_data);
                        opData = transData(_data, 'id', 'parentId', 'children');
                        // console.log(opData);
                        initForm(opData, L1, L2, L3);
                        selectByTree(opData, L1, L2, L3);

                        // console.log(L1.find("option[value='" + L1.val() + "']"));
                        // console.log(L2.find("option[value='" + L2.val() + "']"));
                        // console.log(L3.find("option[value='" + L3.val() + "']"));
                    }
                }
            });


            //经营地址归属地二级联
            var area_prov = $("#mercProv");
            var area_city = $("#mercCity");
            var area = $("#mercArea");
            var areaDb;

            //银行归属地二级联
            var bnk_prov = $("#opnBnkProv");
            var bnk_city = $("#opnBnkCity");
            var bnk_area = $("#opnBnkArea");

            // 后台查询 并初始化三级联动
            jQuery.ajax({
                url: '${ctx}/xiaodian/common/treeDict/fetchChildrens',
                type: 'POST',
                dataType: "json",
                data: {
                    type: treeDictType.area,
                },
                success: function (result) {
                    if (result.code == 200) {
                        var _data = result.data;
                        _data = $.parseJSON(_data);

                        areaDb = transData(_data, 'id', 'parentId', 'children');
                        initForm(areaDb, area_prov, area_city, area);
                        selectByTree(areaDb, area_prov, area_city, area);
                        initForm(areaDb, bnk_prov, bnk_city, bnk_area);
                        selectByTree(areaDb, bnk_prov, bnk_city, bnk_area);
                    }
                }
            });

        })
        ;


        function randomString(len) {
            len = len || 32;
            var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
            /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
            var maxPos = $chars.length;
            var pwd = '';
            for (i = 0; i < len; i++) {
                pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
            }
            return pwd;
        }
        // 数据层级 转换为数组
        function transData(a, idStr, pidStr, chindrenStr) {
            var r = [], hash = {}, id = idStr, pid = pidStr, children = chindrenStr, i = 0, j = 0, len = a.length;
            for (; i < len; i++) {
                hash[a[i][id]] = a[i];
            }
            for (; j < len; j++) {
                var aVal = a[j], hashVP = hash[aVal[pid]];
                if (hashVP) {
                    !hashVP[children] && (hashVP[children] = []);
                    hashVP[children].push(aVal);
                } else {
                    r.push(aVal);
                }
            }
            return r;
        }

        function fetchTreeData(type) {
            jQuery.ajax({
                url: '${ctx}/xiaodian/common/treeDict/fetchChildrens',
                type: 'POST',
                dataType: "json",
                data: {
                    type: type,
                },
                success: function (result) {
                    if (result.code == 200) {
                        var _data = result.data;
                        _data = $.parseJSON(_data);
                        _data = transData(_data, 'id', 'parentId', 'children');
                        console.log("2");
                        console.log(_data);
                        return _data;
//                        console.log(db);
//                        return db;
                    }
                }
            });
        }


        function initForm(data, L1, L2, L3) {
//            alert(L1.val() +"---"+L2.val()+"----"+L3.val());
            var L1v = L1.val();
            var L2v = L2.val();
            var L3v;
            L1.empty();
            L2.empty();
            if (null != L3) {
                L3v = L3.val();
                L3.empty();
            }
            //初始化菜单 从地区字典(data[0])取值 若有地址信息则选中
            $.each(data[0].children, function (n, entity) {
                L1.append($("<option>").attr({"value": entity.code}).text(entity.name));
                if (L1v == entity.code) {
                    L1.find("option[value='" + L1v + "']").attr("selected", true);
                    $.each(entity.children, function (n1, entity1) {
                        L2.append($("<option>").attr({"value": entity1.code}).text(entity1.name));
                        if (L2v == entity1.code) {
                            L2.find("option[value='" + L2v + "']").attr("selected", true);
                            if (null != L3 && null != entity1.children) {
                                $.each(entity1.children, function (n2, entity2) {
                                    L3.append($("<option>").attr({"value": entity2.code}).text(entity2.name));
                                    if (L3v == entity2.code) {
                                        L3.find("option[value='" + L3v + "']").attr("selected", true);
                                    }
                                });
                            }
                        }
                    });
                }
            });
            // 初始无值
            if(L1v!=""){
                L1.change();
                L2.change();
                if (null != L3) {
                    L3.change();
                }
            }
        }


        function configMerchant(e) {
            $("#mercNm").val(e.options[e.options.selectedIndex].text);
        }


        // 三级联动
        function selectByTree(data, L1, L2, L3) {
            // 地区字典对象
            var L0Entity = data[0].children;
            // 省级对象
            var L1Entity;

            if(L1.find("option:selected").text() != ""){
                L1Entity = L0Entity.find(function (currentEntity) {
                    return currentEntity.name == L1.find("option:selected").text();
                })
            }

            //一级变动
            L1.change(function () {
                //清空二三级
                L2.empty();
                if (null != L3) {
                    L3.empty();
                }
                $.each(L0Entity, function (n, entity) {
                    if (L1.find("option:selected").text() == entity.name) {
                        L1Entity = entity;
                        // 加入二级选项
                        $.each(entity.children, function (n1, entity1) {
                            L2.append($("<option>").attr({"value": entity1.code}).text(entity1.name));
                        });
                        // 触发二级变动
                        L2.change();
                        // 一级完成
                        return false;
                    }
                })
            });

            // 二级变动
            L2.change(function () {
                if (null != L3) {
                    L3.empty();
                    $.each(L1Entity.children, function (n2, entity2) {
                        if (L2.find("option:selected").text() == entity2.name) {
                            // 加入三级选项
                            $.each(entity2.children, function (n3, entity3) {
                                L3.append($("<option>").attr({"value": entity3.code}).text(entity3.name));
                            })
                            // 触发三级
                            L3.change();
                            // 二级完成
                            return false;
                        }
                    })
                }
            })

        }


        function setOrgCode(e) {
//            alert(e.val());
            $("#organizationCode").val(e.val());
        }

    </script>
    <style type="text/css">
        .center {
            vertical-align: middle;
            text-align: center;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/xiaodian/pay/merchantPayInfo/">商家账户列表</a></li>
    <li class="active"><a
            href="${ctx}/xiaodian/pay/merchantPayInfo/form?id=${merchantPayInfo.id}">商家账户信息<shiro:hasPermission
            name="xiaodian:pay:merchantPayInfo:edit">${not empty merchantPayInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="xiaodian:pay:merchantPayInfo:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form action="${ctx}/xiaodian/pay/merchantPayInfo/bindApp?partnerId=${merchantPayInfo.partnerId}" method="post">
    <div class="modal hide fade" id="bindApp" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="bindAppModal">绑定微信APP</h4>
                </div>
                <div class="modal-body">
                    <div class="center">
                        <div class="select-content">
                            <div class="center">
                                <b>请输入appId</b>
                                <input name="appId" type="text" maxlength="64" class="input-medium"/>
                            </div>
                        </div>
                    </div>
                    <input style="display: none" name="id" value="${merchantPayInfo.id}">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-warning">确认</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</form>
<form:form id="inputForm" modelAttribute="merchantPayInfo" action="${ctx}/xiaodian/pay/merchantPayInfo/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>

    <div class="control-group">
        <label class="control-label">商家名称：</label>
        <div class="controls">
                <%--<form:input path="merchantId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
            <form:select id="merchantId" path="merchantId" class="input-medium" onchange="configMerchant(this)">
                <form:option value="" label=""/>
                <form:options items="${merchantList}" itemValue="id" itemLabel="merchantName" htmlEscape="false"/>
            </form:select>
            <shiro:hasPermission name="xiaodian:pay:merchantPayInfo:edit">
                <td>
                    <c:if test="${not empty merchantPayInfo.id}">
                        <a href="${ctx}/xiaodian/pay/merchantPayInfo/payConfig?id=${merchantPayInfo.id}">商家支付渠道<shiro:lacksPermission
                                name="xiaodian:merchant:edit">查看</shiro:lacksPermission></a>
                    </c:if>
                </td>
            </shiro:hasPermission>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户简称：</label>
        <div class="controls">
            <form:input path="mercAbbr" htmlEscape="false" maxlength="128" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">接入行：</label>
        <div class="controls">
            <form:select path="bankChannel" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('bank_channel')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
                <%--<form:input path="mercTyp" itemValue="3" disabled="true" htmlEscape="false" maxlength="5"--%>
                <%--class="input-xlarge "/>--%>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">商家名称：</label>
        <div class="controls">
            <form:input id="mercNm" path="mercNm" htmlEscape="false" maxlength="128" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">协议编号：</label>
        <div class="controls">
            <form:input id="agrNo" path="agrNo" htmlEscape="false" maxlength="16" class="input-xlarge "/>
            <input type="button" id="configAgrNo" value="获取协议编号"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户编号：</label>
        <div class="controls">
            <form:input path="partnerId" disabled="false" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">商户授权码：</label>
        <div class="controls">
            <form:input path="authCode" htmlEscape="false" maxlength="128" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">商户类型：</label>
        <div class="controls">
            <form:select path="mercTyp" class="input-xlarge">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('merchant_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
                <%--<form:input path="mercTyp" itemValue="3" disabled="true" htmlEscape="false" maxlength="5"--%>
                <%--class="input-xlarge "/>--%>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">微信AppId：</label>
        <div class="controls">
            <form:input path="mercTrdCls"   htmlEscape="false" maxlength="64" class="input-xlarge "/>
            <input id="btnBindApp" class="btn btn-primary" data-toggle="modal" data-target="#bindApp" type="button" value="绑定APP" onclick=""/>
        </div>
        <div class="controls">
            <c:if test="${not empty appList}">
                <table id="contentTable" class="table table-striped table-bordered table-condensed" style="max-width: 400px">
                    <caption><h4>已绑定app列表</h4></caption>
                    <thead>
                    <tr>
                        <th>app名称</th>
                        <th>appId</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${appList}" var="app">
                        <tr>
                            <td>${app.appName}</td>
                            <td>${app.appId}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>

    <%--
    <div class="control-group">
    <label class="control-label">微信经营类目：</label>
    <div class="controls">
       <sys:treeselect id="opId" name="opId" value="${treeDict.parent.id}" labelName="treeDict.name"
                       labelValue="${treeDict.parent.name}/${treeDict.name}"
                       title="微信经营类目" url="/xiaodian/common/treeDict/treeData?type=00" extId="${treeDict.id}" cssClass=""
                       allowClear="true" notAllowSelectRoot="true" notAllowSelectParent="true" isAll="true"/>
    </div>
    </div>


    <div class="control-group">
    <label class="control-label">微信经营一级类目：</label>
    <div class="controls">
        <form:select id="mercTrdCls" path="mercTrdCls" class="input-xlarge">
            <form:option value="${merchantPayInfo.mercTrdCls}" label=""/>
        </form:select>
    </div>
    </div>

    <div class="control-group">
    <label class="control-label">微信经营二级类目：</label>
    <div class="controls">
        <form:select id="mercSubCls" path="mercSubCls" class="input-xlarge">
            <form:option value="${merchantPayInfo.mercSubCls}" label=""/>
        </form:select>
    </div>
    </div>
    <div class="control-group">
    <label class="control-label">微信经营三级类目：</label>
    <div class="controls">
        <form:select id="mercThirdCls" path="mercThirdCls" class="input-xlarge">
            <form:option value="${merchantPayInfo.mercThirdCls}" label=""/>
        </form:select>
    </div>
    </div>
    <div class="control-group">
    <label class="control-label">支付宝经营类目：</label>
    <div class="controls">
        <form:select path="mercThirdClsAll" class="input-xlarge ">
            <form:option value="" label=""/>
            <form:options items="${fns:getDictList('alipay_op_category')}" itemLabel="label" itemValue="value"
                          htmlEscape="false"/>
        </form:select>
    </div>
    </div>
    --%>
    <div class="control-group">
        <label class="control-label">商户法人姓名：</label>
        <div class="controls">
            <form:input path="crpNm" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户法人身份证号：</label>
        <div class="controls">
            <form:input path="crpIdNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户法人身份证过期日期：</label>
        <div class="controls">
            <form:input path="crpIdExpireDay" htmlEscape="false" maxlength="32" class="input-xlarge " />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">营业执照号：</label>
        <div class="controls">
            <form:input path="regId" htmlEscape="false" maxlength="32" class="input-xlarge"
                        onchange="setOrgCode(this)"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">营业执照有效期：</label>
        <div class="controls">
            <form:input path="idExpireTime" htmlEscape="false" maxlength="32" class="input-xlarge"/>
        </div>
    </div>


    <div class="control-group" style="display: none">
        <label class="control-label">组织机构代码：</label>
        <div class="controls">
            <form:input id="organizationCode" path="organizationCode" htmlEscape="false" maxlength="32"
                        class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">归属省：</label>
        <div class="controls">
                <%--<form:input path="mercProv" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
            <form:select id="mercProv" path="mercProv" class="input-xlarge">
                <form:option value="${merchantPayInfo.mercProv}" label=""/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">归属市：</label>
        <div class="controls">
            <form:select id="mercCity" path="mercCity" class="input-xlarge">
                <form:option value="${merchantPayInfo.mercCity}" label=""/>
            </form:select>
                <%--<form:input path="mercCity" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">归属区：</label>
        <div class="controls">
            <form:select id="mercArea" path="mercArea" class="input-xlarge">
                <form:option value="${merchantPayInfo.mercArea}" label=""/>
            </form:select>
                <%--<form:input path="mercCity" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">经营地址：</label>
        <div class="controls">
            <form:input path="busAddr" htmlEscape="false" maxlength="128" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">商户管理员：</label>
        <div class="controls">
            <form:input path="usrOprNm" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">管理员手机号码：</label>
        <div class="controls">
            <form:input path="usrOprMbl" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">管理员邮箱：</label>
        <div class="controls">
            <form:input path="contactEmail" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group" style="display: none">
        <label class="control-label">结算周期固定值2：</label>
        <div class="controls">
            <form:input path="stlPerd" itemValue="2" disabled="true" htmlEscape="false" maxlength="32"
                        class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group" style="display: none">
        <label class="control-label">结算工作标志固定值T：</label>
        <div class="controls">
            <form:input path="stlDayFlg" itemValue="T" disabled="true" htmlEscape="false" maxlength="32"
                        class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">结算账号公私标志：</label>
        <div class="controls">
            <form:select path="busPsnFlg" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('pay_bus_psn_flg')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商户结算卡号：</label>
        <div class="controls">
            <form:input path="stlOac" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">结算卡户主：</label>
        <div class="controls">
            <form:input path="bnkAcnm" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">结算卡开户行：</label>
        <div class="controls">
            <form:select path="wcBank" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('bank_list')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
                <%--<form:input path="wcBank" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">开户行省：</label>
        <div class="controls">
                <%--<form:input path="opnBnkProv" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
            <form:select id="opnBnkProv" path="opnBnkProv" class="input-xlarge">
                <form:option value="${merchantPayInfo.opnBnkProv}" label=""/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">开户行市：</label>
        <div class="controls">
                <%--<form:input path="opnBnkCity" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
            <form:select id="opnBnkCity" path="opnBnkCity" class="input-xlarge">
                <form:option value="${merchantPayInfo.opnBnkCity}" label=""/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">开户行区：</label>
        <div class="controls">
                <%--<form:input path="opnBnkCity" htmlEscape="false" maxlength="32" class="input-xlarge "/>--%>
            <form:select id="opnBnkArea" path="opnBnkArea" class="input-xlarge">
                <form:option value="${merchantPayInfo.opnBnkArea}" label=""/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支行名称：</label>
        <div class="controls">
            <form:input path="lbnkNm" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支行行号：</label>
        <div class="controls">
            <form:input path="bankBranchNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">清算行号：</label>
        <div class="controls">
            <form:input path="wcLbnkNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>


    <div class="control-group" style="display: none">
        <label class="control-label">客户经理登录ID：</label>
        <div class="controls">
            <form:input path="usrOprLogId" value="m91900000060" htmlEscape="false" maxlength="32" class="input-xlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">返回状态码：</label>
        <div class="controls">
            <form:input path="respCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">返回信息：</label>
        <div class="controls">
            <form:textarea path="respDesc" htmlEscape="false" maxlength="500" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">开户时间：</label>
        <div class="controls">
            <input name="regTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${merchantPayInfo.regTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">开户状态：</label>
        <div class="controls">
            <form:select id="regStatus" path="regStatus" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('bank_reg_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否启用：</label>
        <div class="controls">
            <form:select id="isEnable" path="isEnable" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
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
        <shiro:hasPermission name="xiaodian:pay:merchantPayInfo:edit">
            <input id="btnSubmit" class="btn btn-primary"
                   type="submit"
                   value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>