webpackJsonp([8],{"0Iti":function(t,e,o){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=o("w4pF"),i={data:function(){return{modelCode:"",goodsCategoryId:"",tagsList:[],chooseItems:[],checkStatusList:[],request:new s.a,loading:!1}},props:{categoryList:Array,value:Array},mounted:function(){console.log(534)},computed:{goodsModel:function(){return{modelCode:this.modelCode,goodsCategoryId:this.goodsCategoryId,pageNo:1,pageSize:500}}},methods:{handelInputChange:function(){this._goodsModelList()},handleSelect:function(t){this.goodsCategoryId=t,this._goodsModelList()},handleSelectionChange:function(t){var e=this;t.forEach(function(t){t.num=1,t.sumAmount=(+t.num*(100*+t.modelPrice)/100).toFixed(2),e.value.push(t)}),this.$utils.fliterItem(this.value,"id")},isSelect:function(t){var e=this;t.forEach(function(t,o){console.log(e.value),-1!==e.$utils.findItem(e.value,t,"id")&&e.$refs.multipleTable.toggleRowSelection(t,!0)})},_goodsModelList:function(){var t=this;this.loading=!0,this.request.goodsModelList(this.goodsModel).then(function(e){200==e.code?(t.chooseItems=e.ext.list,setTimeout(function(){t.isSelect(t.chooseItems)},100)):t.chooseItems=[],t.goodsCategoryId="",t.loading=!1})}}},a={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",[o("div",{staticClass:"block"},[o("div",{staticClass:"block-left"},[o("el-menu",{staticClass:"sidebar-el-menu",attrs:{"background-color":"#fff","text-color":"#333","active-text-color":"#0000ff","unique-opened":""},on:{select:t.handleSelect}},[t._l(t.categoryList,function(e){return[o("el-menu-item",{key:e.id,attrs:{index:e.id}},[o("span",{attrs:{slot:"title"},slot:"title"},[t._v(t._s(e.name))])])]})],2)],1),t._v(" "),o("div",{staticClass:"block-right"},[t.chooseItems.length?o("div",{staticClass:"block-item"},[o("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:t.chooseItems,"tooltip-effect":"dark"},on:{"selection-change":t.handleSelectionChange}},[o("el-table-column",{attrs:{type:"selection",width:"40"}}),t._v(" "),o("el-table-column",{attrs:{label:"商品编码",prop:"modelCode",width:"120","show-overflow-tooltip":""}}),t._v(" "),o("el-table-column",{attrs:{label:"商品名",prop:"goodsName",width:"250","show-overflow-tooltip":""}}),t._v(" "),o("el-table-column",{attrs:{prop:"modelPrice",label:"价格",width:"120"}}),t._v(" "),o("el-table-column",{attrs:{prop:"modelName",label:"规格"}})],1)],1):t._e(),t._v(" "),t.chooseItems.length?t._e():o("div",[t._v("暂无数据")])])])])},staticRenderFns:[]};var n=o("C7Lr")(i,a,!1,function(t){o("MGgk")},"data-v-a97e8356",null).exports,l={props:{goods:Object,height:Number,width:Number,inCart:{type:Boolean,default:!1}},data:function(){return{imgSrc:"http://img.1000.com/qm-a-img/prod/000000/34927617f8d8317ecffcca63c8360135.jpg@160w_160h.jpg"}},computed:{itemStyle:function(){return"height:"+this.height+"px;width:"+this.width+"px;"},imgStyle:function(){return"width:"+(this.height-10)+"px;height:"+(this.height-10)+"px;"},computedPrice:function(){return(this.goods.goodsPrice||this.goods.marketPrice)*this.goods.goodsNum}},watch:{},mounted:function(){},methods:{}},r={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{staticClass:"goods-item",style:t.itemStyle},[o("div",{staticClass:"goods-cover"},[t.goods.thumbUrl?o("img",{style:t.imgStyle,attrs:{src:t.goods.thumbUrl+"?x-oss-process=image/resize,w_200"}}):t._e()]),t._v(" "),o("div",{staticClass:"goods-info-wrap"},[o("div",{staticClass:"goods-info"},[o("div",{staticClass:"goods-name"},[t._v(t._s(t.goods.goodsName))]),t._v(" "),t.inCart?o("div",{staticClass:"goods-num"},[o("span",[t._v("x ")]),t._v(t._s(t.goods.goodsNum))]):t._e()]),t._v(" "),o("div",{staticClass:"goods-model"},[t._v("\n            "+t._s(t.goods.goodsModel>0?"多规格":"")+"\n        ")]),t._v(" "),t.inCart?o("div",{staticClass:"goods-price"},[t.goods.goodsPrice?o("div",[o("span",{staticClass:"fs14"},[o("span",[t._v("¥ ")]),t._v(t._s(t.goods.goodsPrice))]),t._v(" "),o("span",{staticClass:"fs14 txt-del txt-999"},[o("span",[t._v("¥ ")]),t._v(t._s(t.goods.marketPrice))])]):o("div",{staticClass:"fs14"},[o("span",[t._v("¥ ")]),t._v(t._s(t.goods.marketPrice))]),t._v(" "),o("div",{staticClass:"fs16 txt-red"},[o("span",{staticClass:"fs14"},[t._v("¥ ")]),t._v(t._s(t.computedPrice))])]):t._e(),t._v(" "),t.inCart?t._e():o("div",{staticClass:"goods-price"},[o("div",{staticClass:"fs16 txt-red"},[o("span",{staticClass:"fs14"},[t._v("¥ ")]),t._v(t._s(t.goods.marketPrice))])])])])},staticRenderFns:[]};var c={data:function(){return{request:new s.a,visibleGoodsModel:!1,goodsList:[],goodsCategories:[],currentPage:1,pageSize:15,categoryId:"",totalCount:0,selectGoodsItem:{},modelId:"",primaryCateId:"",secondCateId:"",loading:!1}},computed:{primaryCate:function(){return this.goodsCategories.filter(function(t){return 0==t.parentId})},secondCate:function(){var t=this;if(this.primaryCateId)return this.goodsCategories.filter(function(e){return e.parentId==t.primaryCateId})}},components:{goodsItem:o("C7Lr")(l,r,!1,function(t){o("dPFY"),o("rmC7")},"data-v-95e6ff4c",null).exports},created:function(){this.fetchCategoryList()},mounted:function(){this.getGoodsList()},methods:{getGoodsList:function(){var t=this,e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,e.shopId=$constant.shopId,e.pageNo=this.currentPage,e.pageSize=this.pageSize,this.request.getGoodsList(e).then(function(e){t.loading=!1,200==e.code?(t.goodsList=e.ext.list,t.currentPage=e.ext.pageNo,t.totalCount=e.ext.count):"983"==e.code?t.goodsList=[]:t.$message.error("请求失败")})},selectGoods:function(t){this.selectGoodsItem=t,this.$emit("select",t)},handleCurrentChange:function(t){this.currentPage=t,this.getGoodsList({categoryId:this.secondCateId})},fetchCategoryList:function(){var t=this,e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,e.shopId=$constant.shopId,this.request.fetchCategoryList(e).then(function(e){"200"==e.code?(t.goodsCategories=e.ext,t.loading=!1):t.$message.error("分类数据, 请求失败")})},selectPrimaryCate:function(t){this.categoryId=t,this.primaryCateId=t;var e=this.secondCate[0].id;this.currentPage=1,this.getGoodsList({categoryId:e})},selectSecondCate:function(t){this.secondCateId=t,this.categoryId=t,this.currentPage=1,this.getGoodsList({categoryId:t})}}},d={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{class:["goods-list-wrap",t.categoryId?"wrap-with-cate":""]},[o("div",{staticClass:"goods-list"},[o("div",{staticClass:"goods-aside"},[o("el-menu",{attrs:{"default-active":"","active-text-color":"#235073","text-color":"#999"},on:{select:t.selectPrimaryCate}},[o("el-menu-item",{attrs:{index:""}},[t._v("全部")]),t._v(" "),t._l(t.primaryCate,function(e,s){return o("el-menu-item",{key:s,attrs:{index:e.id}},[t._v("\n                    "+t._s(e.name)+"\n                ")])})],2)],1),t._v(" "),o("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"goods-wrap"},[t.categoryId?o("div",{staticClass:"goods-header"},[o("div",{staticStyle:{width:"600px"}},[o("el-menu",{attrs:{"default-active":t.secondCate[0].id,mode:"horizontal","active-text-color":"#235073","text-color":"#999"},on:{select:t.selectSecondCate}},t._l(t.secondCate,function(e,s){return o("el-menu-item",{key:s,attrs:{index:e.id}},[t._v("\n                            "+t._s(e.name)+"\n                        ")])}),1)],1)]):t._e(),t._v(" "),o("div",{staticClass:"goods-body"},[t._l(t.goodsList,function(e,s){return o("div",{key:s,staticClass:"goods-item",on:{click:function(o){return t.selectGoods(e)}}},[o("el-card",{attrs:{shadow:"hover"}},[o("goods-item",{attrs:{height:80,width:300,goods:e}})],1)],1)}),t._v(" "),o("el-pagination",{directives:[{name:"show",rawName:"v-show",value:t.totalCount,expression:"totalCount"}],attrs:{small:"",layout:"prev, pager, next","page-size":15,"current-page":t.currentPage,total:t.totalCount},on:{"update:currentPage":function(e){t.currentPage=e},"update:current-page":function(e){t.currentPage=e},"current-change":t.handleCurrentChange}})],2)])])])},staticRenderFns:[]};var u={name:"basetable",components:{vCheckGoods:n,goodsList:o("C7Lr")(c,d,!1,function(t){o("5zXW")},"data-v-25356abe",null).exports},data:function(){return{request:new s.a,input2:"",supplierList:[],warehouseList:[],checkedList:[],checked:{supplier:"",warehouse:""},payTypeOpt:this.$dic.payType,categoryList:[],totalCount:{},dialogVisible:!1,ctrl:{data:[{title:"1",params:{id:1223}}],request:"fun"},payAmount:0,otherAmount:0,bizNote:"",stockStatus:"",payType:"09"}},created:function(){this._fetchWarehouseList(),this._fetchSupplierList()},computed:{totalAmount:function(){var t=0;return this.checkedList.forEach(function(e){t+=+e.sumAmount}),t},sumAmount:function(){return+this.totalAmount+ +this.otherAmount},procurementModel:function(){var t=[];return this.checkedList.forEach(function(e){var o=e.goodsModelId;e.goodsModelList.forEach(function(t){t.id===o&&(e.modelName=t.modelName,e.goodsModelSn=t.goodsModelSn)}),t.push({goodsName:e.goodsName,goodsModelId:e.goodsModelId,price:e.modelPrice,goodsNum:e.num,modelName:e.modelName,sumAmount:e.sumAmount,goodsModelSn:e.goodsModelSn,unit:e.unit,category:e.goodsCategoryId})}),{warehouseId:this.checked.warehouse,supplierId:this.checked.supplier,stockStatus:this.stockStatus,totalAmount:this.totalAmount,payAmount:this.payAmount,sumAmount:this.sumAmount,otherAmount:this.otherAmount,payType:this.payType,bizNote:this.bizNote,detailList:t}}},methods:{handleSearchClick:function(){this.dialogVisible=!0,this._fetchCategoryList()},handleNumChange:function(t,e){t.sumAmount=(+t.num*(100*+t.modelPrice)/100).toFixed(2),this.$set(this.checkedList,e,t)},handleModelPriceChange:function(t,e){t.sumAmount=(+t.num*(100*+t.modelPrice)/100).toFixed(2),this.$set(this.checkedList,e,t)},removeItem:function(t){this.checkedList.splice(t,1)},handleSaveButton:function(){this.stockStatus=0,this._addProcurement()},handleCheckButton:function(){this.stockStatus=1,this._addProcurement()},onSelectGoods:function(t){this.checkedList.push(t)},_fetchSupplierList:function(){var t=this;this.request.fetchSupplierList().then(function(e){console.log(e),200==e.code&&(t.supplierList=e.ext)})},_fetchWarehouseList:function(){var t=this;this.request.fetchWarehouseList().then(function(e){200==e.code&&(t.warehouseList=e.list)})},_fetchCategoryList:function(){var t=this;this.request.fetchCategoryList().then(function(e){200==e.code&&(t.categoryList=e.ext)})},_addProcurement:function(){var t=this;this.request.addProcurement(this.procurementModel).then(function(e){200==e.code&&t.$message.success("新增采购成功！")})}}},h={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{staticClass:"table"},[o("div",{staticClass:"crumbs"},[o("el-breadcrumb",{attrs:{separator:"/"}},[o("el-breadcrumb-item",[o("i",{staticClass:"el-icon-tickets"}),t._v(" 新增采购")])],1)],1),t._v(" "),o("div",{staticClass:"container marginTop"},[o("div",{staticClass:"handle-box"},[t._v("\n            供应商："),o("el-select",{staticClass:"input",attrs:{placeholder:"选取供应商"},model:{value:t.checked.supplier,callback:function(e){t.$set(t.checked,"supplier",e)},expression:"checked.supplier"}},t._l(t.supplierList,function(t){return o("el-option",{key:t.id,attrs:{label:t.supplierName,value:t.id}})}),1)],1),t._v(" "),o("div",{staticClass:"handle-box"},[t._v("\n            仓库："),o("el-select",{staticClass:"input",attrs:{placeholder:"选择仓库"},model:{value:t.checked.warehouse,callback:function(e){t.$set(t.checked,"warehouse",e)},expression:"checked.warehouse"}},t._l(t.warehouseList,function(t){return o("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})}),1)],1),t._v(" "),o("div",{staticClass:"handle-box"},[o("el-input",{attrs:{placeholder:"输入商品条形码","suffix-icon":""},model:{value:t.input2,callback:function(e){t.input2=e},expression:"input2"}},[o("i",{staticClass:"el-input__icon el-icon-search",staticStyle:{"background-color":"#eaeaea","margin-right":"-10px"},attrs:{slot:"suffix"},on:{click:t.handleSearchClick},slot:"suffix"})])],1),t._v(" "),o("div",{staticClass:"handle-box"},[o("el-button",{attrs:{type:"primary"},on:{click:t.handleSearchClick}},[t._v("搜索商品")])],1),t._v(" "),o("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:t.checkedList,border:""}},[o("el-table-column",{attrs:{prop:"goodsSn",label:"商品编码"}}),t._v(" "),o("el-table-column",{attrs:{prop:"goodsName",label:"商品名称"}}),t._v(" "),o("el-table-column",{attrs:{prop:"modelPrice",label:"单价"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("el-input",{on:{change:function(o){return t.handleModelPriceChange(e.row,e.$index)}},model:{value:e.row.modelPrice,callback:function(o){t.$set(e.row,"modelPrice",o)},expression:"scope.row.modelPrice"}})]}}])}),t._v(" "),o("el-table-column",{attrs:{prop:"modelName",label:"规格"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("el-select",{staticClass:"input",attrs:{placeholder:"选择商品规格"},model:{value:e.row.goodsModelId,callback:function(o){t.$set(e.row,"goodsModelId",o)},expression:"scope.row.goodsModelId"}},t._l(e.row.goodsModelList,function(t){return o("el-option",{key:t.id,attrs:{label:t.modelName,value:t.id}})}),1)]}}])}),t._v(" "),o("el-table-column",{attrs:{prop:"num",label:"数量"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("el-input",{on:{change:function(o){return t.handleNumChange(e.row,e.$index)}},model:{value:e.row.num,callback:function(o){t.$set(e.row,"num",o)},expression:"scope.row.num"}})]}}])}),t._v(" "),o("el-table-column",{attrs:{prop:"unit",label:"单位"}}),t._v(" "),o("el-table-column",{attrs:{prop:"sumAmount",label:"合计金额"}}),t._v(" "),o("el-table-column",{attrs:{prop:"order_info",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("el-button",{attrs:{type:"text"},on:{click:function(o){return t.removeItem(e.$index)}}},[t._v("从列表移除")])]}}])})],1),t._v(" "),o("el-row",[o("el-col",{attrs:{span:5}},[t._v("\n                总计金额：￥"+t._s(t.totalAmount)+"\n            ")]),t._v(" "),o("el-col",{attrs:{span:5}},[t._v("\n                合计金额：￥"+t._s(t.sumAmount)+"\n            ")]),t._v(" "),o("el-col",{attrs:{span:5}},[t._v("\n                实付金额：￥"),o("el-input",{staticClass:"flex-1",model:{value:t.payAmount,callback:function(e){t.payAmount=e},expression:"payAmount"}})],1),t._v(" "),o("el-col",{attrs:{span:5}},[t._v("\n                其他费用：￥"),o("el-input",{staticClass:"flex-1",model:{value:t.otherAmount,callback:function(e){t.otherAmount=e},expression:"otherAmount"}})],1),t._v(" "),o("el-col",{attrs:{span:6}},[t._v("\n                支付方式："),o("el-select",{staticClass:"flex-1",attrs:{placeholder:"请选择你要查询的订单状态"},model:{value:t.payType,callback:function(e){t.payType=e},expression:"payType"}},t._l(t.payTypeOpt,function(t){return o("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})}),1)],1),t._v(" "),o("el-col",{attrs:{span:6}},[t._v("\n                备注："),o("el-input",{staticClass:"flex-1",model:{value:t.bizNote,callback:function(e){t.bizNote=e},expression:"bizNote"}})],1),t._v(" "),o("el-col",{attrs:{span:5}}),t._v(" "),o("el-col",{attrs:{span:5}},[o("el-button",{attrs:{type:"primary"},on:{click:t.handleSaveButton}},[t._v("保存")]),t._v(" "),o("el-button",{attrs:{type:"primary"},on:{click:t.handleCheckButton}},[t._v("入库并审核")]),t._v(" "),o("el-button",{attrs:{type:"success"}},[o("router-link",{attrs:{to:"../wareHouse/procurement"}},[o("p",{staticStyle:{color:"white"}},[t._v("返回")])])],1)],1)],1),t._v(" "),o("el-dialog",{attrs:{title:"选择你要采购的商品",visible:t.dialogVisible,width:"50%"},on:{"update:visible":function(e){t.dialogVisible=e}}},[o("goods-list",{on:{select:t.onSelectGoods}}),t._v(" "),o("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{on:{click:function(e){t.dialogVisible=!1}}},[t._v("取 消")]),t._v(" "),o("el-button",{attrs:{type:"primary"},on:{click:function(e){t.dialogVisible=!1}}},[t._v("确 定")])],1)],1)],1)])},staticRenderFns:[]};var p=o("C7Lr")(u,h,!1,function(t){o("QBsy")},"data-v-212a51bc",null);e.default=p.exports},"5zXW":function(t,e){},MGgk:function(t,e){},QBsy:function(t,e){},dPFY:function(t,e){},rmC7:function(t,e){}});