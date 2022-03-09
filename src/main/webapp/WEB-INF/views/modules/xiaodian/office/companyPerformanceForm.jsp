<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核管理管理</title>
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

            // 工作岗位变化时改变考核项
            $("#position").change(function (){
                // console.log("change");
                // 加载时的岗位
                var position = "${companyPerformance.position}";
                var curPosition =$("#position").select2("val");
                var count;
                // console.log("pre position",position);
                // console.log("cur position",curPosition);
                // 岗位变化
                if(position!=curPosition){
                    // 所有岗位的信息
                    var jobList = JSON.parse('${fns:getDictListJson("job_list")}');
                    // console.log("jobList",jobList);
                    // 当前岗位的考核项
                    var itemsList ="";
                    for (var i=0;i<jobList.length;i++){
                        if(jobList[i].value==curPosition){
                            itemsList = jobList[i].remarks.split(",");
                            break;
                        }
                    }
                    // console.log("itemsList",itemsList);
                    // 考核项 键值对
                    var items = new Object();
                    if(itemsList!=""){
                        for(var i=0;i<itemsList.length;i++){
                            items[itemsList[i]] = "";
                        }
                    }else{
                        items["请通知管理员"] ="该岗位还未添加考核项目";
                    }
                    // console.log("itemsMap",items);
                    // 改变考核项数量
                    count = Object.getOwnPropertyNames(items).length;
                    $("#itemsCount").val(count);
                    showItems(items,false);
                }else{
                    // 改变考核项数量
                    count = "${fn:length(companyPerformance.evaluateItems)}";
                    $("#itemsCount").val(count);
                    showItems("",true);
                }
            });

            // 考核时间设置
            var evaluateWeek = "${companyPerformance.evaluateWeek}";
            var evaluateTime = "${companyPerformance.evaluateTime}";
            // console.log("evaluateWeek", evaluateWeek, "evaluateTime", evaluateTime);
            // 当前设置时间
            var currentDate;
            if (evaluateTime != "" && evaluateWeek != "") {
                var weekArray = evaluateWeek.match(/\d+/g);
                var timeArray = evaluateTime.match(/\d+/g);
                var year = "20" + timeArray[0] - 0;
                var month = timeArray[1] - 0 ;
                var month1 = weekArray[0] - 0 ;
                var month2 = weekArray[2] - 0 ;
                if (month == month1) {
                    currentDate = setWeekDay(new Date(year, month - 1, weekArray[1] - 0));
                } else {
                    currentDate = setWeekDay(new Date(year, month2 - 1, weekArray[3] - 0))
                }
            } else {
                currentDate = setWeekDay(new Date());
            }
            console.log("currentDate", currentDate);

            // 返回考核时间 按 "x年x月第x周" 格式
            function evaluateTimeFormat(date) {
                var year = date.getFullYear() % 100;
                var month = date.getMonth() + 1;
                var week = weekNum(date);
                return year + '年' + month + '月' + '第' + week + '周';
            }

            // 返回考核时间的区间  "x月x日-x月x日"
            function evaluateWeekFormat(date) {
                var week = date.getDay() > 0 ? date.getDay() : 7;
                var beginDate = new Date(date);
                beginDate.setDate(date.getDate() - week + 1);
                var month1 = (beginDate.getMonth() + 1) + '月';
                var day1 = beginDate.getDate() + '日';
                var endDate = new Date(date);
                endDate.setDate(date.getDate() - week + 7);
                var month2 = (endDate.getMonth() + 1) + '月';
                var day2 = endDate.getDate() + '日';
                return month1 + day1 + '-' + month2 + day2;
            }

            // 每月1日是周一，就是第一周的第一天；不是周一，第一个周一所在周是第二周
            function weekNum(date) {
                var day = date.getDate();
                var firstDate = new Date(date);
                firstDate.setDate(1);
                var week = firstDate.getDay() > 0 ? firstDate.getDay() : 7;
                return Math.ceil((day - 1 + week) / 7);
            }

            // 把时间设置为当前周的星期一 ，若为上个月设为星期日
            function setWeekDay(date) {
                var week = date.getDay() > 0 ? date.getDay() : 7;
                var cur = date.getDate() + 1 - week;
                if (cur > 1) {
                    date.setDate(cur);
                } else {
                    date.setDate(cur + 6);
                }
                return date;
            }

            //  显示 上一周 时间
            $("#lastWeekBtn").click(function () {
                var week = currentDate.getDay();
                var temp = currentDate.getDate();
                if (week == 1) {
                    currentDate.setDate(temp - 1);
                    setWeekDay(currentDate);
                } else {
                    currentDate.setDate(temp - 7);
                    setWeekDay(currentDate);
                }
                $("#evaluateTime").val(evaluateTimeFormat(currentDate));
                $("#evaluateWeek").val(evaluateWeekFormat(currentDate));
            })
            //  显示 下一周 时间
            $("#nextWeekBtn").click(function () {
                var temp = currentDate.getDate();
                currentDate.setDate(temp + 7);
                setWeekDay(currentDate);
                $("#evaluateTime").val(evaluateTimeFormat(currentDate));
                $("#evaluateWeek").val(evaluateWeekFormat(currentDate));
            });

            // 初始化考核项
            showItems("", true);
            if (evaluateTime != "" && evaluateWeek != "") {
                $("#evaluateTime").val(evaluateTime);
                $("#evaluateWeek").val(evaluateWeek);
            } else {
                // $("#lastWeekBtn").click();
                $("#evaluateTime").val(evaluateTimeFormat(currentDate));
                $("#evaluateWeek").val(evaluateWeekFormat(currentDate));
            }

		});

        // 生成考核项 flag=true代表按后台数据生成
        function showItems(itemsMap,flag){
            if(flag){
                var content ="${companyPerformance.evaluateItems}";
                // console.log("evaluateItems",content,"flag",flag);
                if (content != "") {
                    $("#firstTr").html('<c:forEach items="${companyPerformance.evaluateItems}" varStatus="curInfo" >' +
                        '<td id="title_${curInfo.count}"><c:out value="${curInfo.current.key}"></c:out></td>' +
                        '</c:forEach>');
                    $("#secondTr").html('<c:forEach items="${companyPerformance.evaluateItems}" varStatus="curInfo" >' +
                        '<td><input id="item_${curInfo.count}" type="text" value="${curInfo.current.value}"  class="input-small"/></td>' +
                        '</c:forEach>');
                    $("#showTable").show();
                    $("#showMsg").hide();
                }else{
                    $("#showTable").hide();
                    $("#showMsg").show();
                }
            }else{
                // console.log("itemsMap",itemsMap,"end",flag);
                if(itemsMap!=""){
                    var firstTr = "";
                    var secondTr = "";
                    var i=1;
                    for (var key in itemsMap){
                        firstTr += '<td id="title_'+i+'">'+key+'</td>';
                        secondTr += '<td><input id="item_' + i + '" type="text" class="input-small" /></td>';
                        i++;
                    }
                    $("#firstTr").html(firstTr);
                    $("#secondTr").html(secondTr);
                    $("#showTable").show();
                    $("#showMsg").hide();
                }
            }
        }
        // 提交表单
        function toSubmit() {
            var count = $("#itemsCount").val();
            var title;
            var item;
            var info = new Object();
            for (var i = 1; i <= count; i++) {
                title = $("#title_" + i).text();
                item = $("#item_" + i).val();
                info[title] = item;
            }
            // 生成考核项目信息
            $("#evaluateInfo").val(JSON.stringify(info));
			$("#inputForm").submit();
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/xiaodian/office/companyPerformance/">绩效考核列表</a></li>
		<li class="active"><a href="${ctx}/xiaodian/office/companyPerformance/form?id=${companyPerformance.id}">绩效考核<shiro:hasPermission name="xiaodian:office:companyPerformance:edit">${not empty companyPerformance.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="xiaodian:office:companyPerformance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="companyPerformance" action="${ctx}/xiaodian/office/companyPerformance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">考核时间：</label>
			<div class="controls">
				<form:input path="evaluateTime" class="input-xlarge " placeholder="x年x月第x周" />
                <input id="lastWeekBtn" class="btn btn-primary" type="button" value="上一周" />
                <input id="nextWeekBtn" class="btn btn-primary" type="button" value="下一周" />
                <span style="color: #FF0000;">  提示：当月1号所在周为第1周，可点击(上/下一周)切换</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核区间：</label>
			<div class="controls">
				<form:input path="evaluateWeek" class="input-xlarge " placeholder="x月x日-x月x日" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${companyPerformance.office.id}" labelName="office.name" labelValue="${companyPerformance.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位：</label>
			<div class="controls">
				<form:select path="position" class="input-xlarge " >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('job_list')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${companyPerformance.user.id}" labelName="user.name" labelValue="${companyPerformance.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核项目：</label>
            <form:hidden path="evaluateInfo"/>
			<div class="controls">
                <div id="showTable" style="display: none;">
                    <table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 50%">
                        <input id="itemsCount" type="hidden" value="${fn:length(companyPerformance.evaluateItems)}"/>
                        <tr id="firstTr"></tr>
                        <tr id="secondTr"></tr>
                    </table>
                </div>
                <div id="showMsg" style="display: none;">
				    请先选择工作岗位
                </div>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周报：</label>
			<div class="controls">
                <form:textarea path="weekReport" htmlEscape="false" class="input-xlarge"/>
                <sys:ckeditor  replace="weekReport" uploadPath="company/weekReport"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级点评：</label>
			<div class="controls">
				<form:textarea path="superiorEvaluate" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核评级：</label>
			<div class="controls">
				<form:select path="evaluateLevel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluate_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
        <c:if test="${not empty companyPerformance.id}">
            <div class="control-group">
                <label class="control-label">创建时间：</label>
                <div class="controls">
                    <fmt:formatDate value="${companyPerformance.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </div>
            </div>
        </c:if>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="xiaodian:office:companyPerformance:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="toSubmit()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>