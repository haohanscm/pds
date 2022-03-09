<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品采购管理</title>
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/pss/procure/procurement/">商品采购列表</a></li>
		<li class="active"><a href="${ctx}/pss/procure/procurement/form?id=${procurement.id}">商品采购<shiro:hasPermission name="pss:procure:procurement:edit">${not empty procurement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pss:procure:procurement:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="procurement" action="${ctx}/pss/procure/procurement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商家：</label>
			<div class="controls">
				<form:select id="merchantId" path="merchantId" class="input-large">
					<form:option value="" label=""/>
					<c:forEach items="${merchantList}" var="merchant">
						<form:option  value="${merchant.id}" label="${merchant.merchantName}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购编号：</label>
			<div class="controls">
				<form:input path="procureNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供应商：</label>
			<div class="controls">
				<form:select id="supplierId" path="supplierId" class="input-large">
					<form:option value="" label=""/>
					<c:forEach items="${pssSupplierList}" var="supplier">
						<form:option  value="${supplier.id}" label="${supplier.supplierName}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">仓库：</label>
			<div class="controls">
				<form:select id="warehouseId" path="warehouseId" class="input-xlarge">
					<form:option value="" label=""/>
					<c:forEach items="${warehouseList}" var="warehouse">
						<form:option  value="${warehouse.id}" label="${warehouse.name}"/>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择商品：</label>
			<div class="controls">
				<button type="button" class="btn btn-primary"  data-toggle="modal"  data-target=".bs-example-modal-lg-goods" onclick="getGoods()">选择商品</button>
			</div>
		</div>
		<div class="control-group" id="tableBox" style="width:60%;display: none">
			<table id="contentTable" class="table ">
				<thead>
				<tr>
					<th>商品编码</th>
					<th>商品名</th>
					<th>参考进价</th>
					<th>单位</th>
					<th>规格</th>
					<th>数量</th>
					<th>单价</th>
					<th>总金额</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="5" var="goodsAllot">
					<tr>

						<td>
							哈哈1
						</td>
						<td>
							哈哈2
						</td>
						<td>
							哈哈3
						</td>
						<td>
							哈哈5
						</td>
						<td>
							哈哈4
						</td>
						<td>
							<input type="text" style="width: 50px;" value="123">
						</td>
						<td>
							哈哈2
						</td>
						<td>
							哈哈3
						</td>
						<td>
							<div>删除</div>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="control-group">
			<label class="control-label">采购数量：</label>
			<div class="controls">
				<form:input path="num" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总计金额：</label>
			<div class="controls">
				<form:input path="totalAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实付金额：</label>
			<div class="controls">
				<form:input path="payAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合计金额：</label>
			<div class="controls">
				<form:input path="sumAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他费用：</label>
			<div class="controls">
				<form:input path="otherAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结账方式：</label>
			<div class="controls">
				<form:input path="payType" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务备注：</label>
			<div class="controls">
				<form:input path="bizNote" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
				<form:input path="operator" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作时间：</label>
			<div class="controls">
				<input name="opTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${procurement.opTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="pss:procure:procurement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

	<div class="modal fade bs-example-modal-lg bs-example-modal-lg-goods" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myLargeModalLabel">供应记录</h4>
				</div>
				<div class="modal-body">
					<div class="control-group">

						<label class="control-label">选择商品分类：</label>
						<div class="controls">
								<select id="goodsselect" class="input-medium" onchange="gradeChange()">

								</select>

						</div>
					</div>
					<sys:message content="${message}"/>
					<table id="contentTable2" class="table">
						<thead>
						<tr>
							<th>商品名</th>
							<th>参考进价</th>
							<th>单位</th>
							<th>规格</th>
							<th>选择</th>
						</tr>
						</thead>
						<tbody id="tableBody">
							<tr onclick="getProduct(this)">
								<td>
									水果
								</td>
								<td>
									200
								</td>
								<td>
									斤
								</td>
								<td>
									五个/斤
								</td>
								<td class="selected">
									<input type="checkbox">
								</td>
							</tr>
						</tbody>
					</table>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="renderList()">确定</button>
					</div>
				</div>

			</div>
		</div>
	</div>
	<script>
		var goodsList = [];//已选取商品列表
        var checkList = [];//待选取商品列表
		// 将花括号变为对应值
        Array.prototype.indexOf = function(val) {
            for (var i = 0; i < this.length; i++) {
                if (this[i].goodsId == val) return i;
            }
            return -1;
        };
        Array.prototype.remove = function(val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };
		//获取商品分类
		function getGoods(){
            var goodsObj = $.ajax({url:"/jquery/test1.txt",async:false});
            var goods = [
                {
                    goodsClassification:"豆类",
                    goodList:[{
                        goodsName:"大豆",
                        goodsPrice:"80",
                        goodsUnit:"袋",
                        goodsSpec:"20斤一袋",
						goodsId:"123"
                    }]
                },{
                    goodsClassification:"豆类",
                    goodList:[{
                        goodsName:"大豆",
                        goodsPrice:"80",
                        goodsUnit:"袋",
                        goodsSpec:"20斤一袋",
                        goodsId:"123"
                    }]
                },{
                    goodsClassification:"豆类",
                    goodList:[{
                        goodsName:"大豆",
                        goodsPrice:"80",
                        goodsUnit:"袋",
                        goodsSpec:"20斤一袋",
                        goodsId:"123"
                    }]
                }
            ];
            var goodsCategory = [{goodsCategoryName:"豆类",goodsCategoryId:"123"},{goodsCategoryName:"蔬菜类",goodsCategoryId:"121"},{goodsCategoryName:"肉类",goodsCategoryId:"1223"}];
            var str = "";
            goodsCategory.forEach(ele=>{
                str += "<option value=" + ele.goodsCategoryId + ">" + ele.goodsCategoryName + "</option>"
			})
			$("#goodsselect").html(str)
            gradeChange()
		}
		//获取分类商品列表
		function gradeChange(){
            var goodsObj = $.ajax({url:"/jquery/test1.txt",async:false});
            checkList = [
                {
					goodsName:"毛豆",
					goodsPrice:"80",
					goodsUnit:"袋",
					goodsSpec:"20斤一袋",
					goodsId:"123"
				},{
					goodsName:"大豆",
					goodsPrice:"80",
					goodsUnit:"袋",
					goodsSpec:"20斤一袋",
                    goodsId:"125"
				},{
					goodsName:"小豆",
					goodsPrice:"80",
					goodsUnit:"袋",
					goodsSpec:"20斤一袋",
                    goodsId:"103"
				},{
					goodsName:"中豆",
					goodsPrice:"80",
					goodsUnit:"袋",
					goodsSpec:"20斤一袋",
                    goodsId:"300"
				},{
					goodsName:"豆豆",
					goodsPrice:"80",
					goodsUnit:"袋",
					goodsSpec:"20斤一袋",
                    goodsId:"200"
            	}
			];
            var str = "";
            checkList.forEach(ele=>{
                if(goodsList.indexOf(ele.goodsId) > -1){
                    str += "<tr ><td>" + ele.goodsName + "</td><td>" + ele.goodsPrice + "</td><td>" + ele.goodsSpec + "</td><td>" + ele.goodsUnit +"</td><td ><input onclick=\"getProduct(this)\"  type=\"checkbox\" id=\"che\" checked=\"checked\"></td></tr>"
                }else{
                    str += "<tr ><td>" + ele.goodsName + "</td><td>" + ele.goodsPrice + "</td><td>" + ele.goodsSpec + "</td><td>" + ele.goodsUnit +"</td><td ><input onclick=\"getProduct(this)\"  type=\"checkbox\" id=\"che\"></td></tr>"
                }
			})
			$("#tableBody").html(str)
		}
		//选择商品
        function getProduct(node){
            var index = node.parentNode.parentNode.sectionRowIndex;
            if(node.checked){
                goodsList.push(objConstructor (checkList[index]))
			}else{
				goodsList.remove(checkList[index].goodsId)
			}
			console.log(goodsList)
		}
		//分类构造函数
		function objConstructor(obj){
		    var _this = {};
		    _this.goodsName = obj.goodsName || "";
		    _this.goodsPrice = obj.goodsPrice || "";
		    _this.goodsSpec = obj.goodsSpec || "";
		    _this.goodsUnit = obj.goodsUnit || "";
		    _this.goodsNum = obj.goodsNum || "";
		    _this.goodsId = obj.goodsId || "";
            _this.goodsRealPrice = obj.goodsRealPrice || "";
            return _this;
		}
		//渲染已选择商品列表
		function renderList(){
		    if(goodsList[0]){
		        $("#tableBox").css({display:'block'})
				console.log(0)
			}else{
		        console.log(1)
                $("#tableBox").css({display:'none'})
				return;
			}
			var str = "";
            console.log(goodsList)
			goodsList.forEach(ele=>{
                str +="<tr><td>" + ele.goodsId + "</td><td>" + ele.goodsName + "</td><td>" + ele.goodsPrice + "</td><td>" + ele.goodsUnit
                    +"</td><td>" + ele.goodsSpec + "</td><td><input onchange='changeNum(this)' type=\"text\" style=\"width: 50px;\" value=" + (ele.goodsNum || 1) +
                    "></td><td><input type=\"text\" onchange='changePrice(this)' style=\"width: 50px;\" value=" + (ele.goodsRealPrice || ele.goodsPrice) +"></td><td>" + ((ele.goodsRealPrice || ele.goodsPrice)*(ele.goodsNum || 1))
                    + "</td><td><div style='cursor: pointer;color: red' onclick='deleteGoods(this)'>删除</div></td></tr>"
			})
			$("#contentTable").children("tbody").html(str)
		}
		//删除某个已选中商品
		function deleteGoods(node){
            var index = node.parentNode.parentNode.sectionRowIndex;
            goodsList.splice(index, 1);
            console.log(goodsList);
            renderList();
		}
		//修改价格
        function changePrice(node){
		    console.log(node.value);
            var index = node.parentNode.parentNode.sectionRowIndex;
		    goodsList[index].goodsRealPrice = + node.value;
            renderList();
		}
		//修改数量
		function changeNum(node){
            var index = node.parentNode.parentNode.sectionRowIndex;
            goodsList[index].goodsNum = + node.value;
            renderList();
		}

	</script>
</body>
</html>