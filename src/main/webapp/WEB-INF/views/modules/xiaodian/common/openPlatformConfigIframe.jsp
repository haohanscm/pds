<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>开放平台管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        //iframe高度适应
        function changeFrameHeight(){
            var ifm= document.getElementById("ald-iframe");
            ifm.height=document.documentElement.clientHeight-80;
        }
        function delApp() {
            var appKey = $("#appKey").val();
            $("#delBtn").attr("disabled","disabled");
            if(appKey){
                jQuery.ajax({
                    url: '${ctx}/xiaodian/common/openPlatformConfig/ald/delApp',
                    type: 'POST',
                    dataType: "json",
                    data: {
                        appKey: appKey,
                    },
                    success: function (result) {
                        if(result.code == 200){
                            alert(result.msg);
                        }else {
                            alert(JSON.stringify(result));
                        }
                        $("#delBtn").removeAttr("disabled");
                    }
                });
            }else{
                alert("appKey不能为空！");
                $("#delBtn").removeAttr("disabled");
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/common/openPlatformConfig/">开放平台列表</a></li>
		<li><a href="${ctx}/xiaodian/common/openPlatformConfig/form?id=${openPlatformConfig.id}">开放平台<shiro:hasPermission name="xiaodian:common:openPlatformConfig:edit">${not empty openPlatformConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:common:openPlatformConfig:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/xiaodian/common/openPlatformConfig/iframe?id=${openPlatformConfig.appId}">数据看板</a></li>
	</ul><br/>

    <shiro:hasPermission name="xiaodian:common:openPlatformConfig:edit">
    <div class="control-group">
        删除ald应用appKey：
        <input id="appKey" type="text" class="input-xlarge" style="margin-bottom: 0px"/>&nbsp;&nbsp;
        <input id="delBtn" type="button" onclick="delApp()" value="确认删除" class="btn btn-primary"/><br/>
        </div>
    </div>
    </shiro:hasPermission>
	<div class="iframe-box">
		<iframe id="ald-iframe" width="100%" height="1000" src="http://statistic.plugin.aldwx.com/assets?app_key=${openPlatformConfig.appId}&access_token=${openPlatformConfig.accessToken}" onload="changeFrameHeight()" frameborder="0" scrolling="auto"></iframe>
		<%--<iframe id="ald-iframe" width="100%" height="1000" src="http://statistic.plugin.aldwx.com/assets?app_key=065c28efb38028d37903eed3e87eae83&access_token=57a2065dd76eddeec99041d86379f00d0d9aae4a" onload="changeFrameHeight()" frameborder="0" scrolling="auto"></iframe>--%>
	</div>

</body>
</html>