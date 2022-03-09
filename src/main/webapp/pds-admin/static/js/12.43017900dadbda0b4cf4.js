webpackJsonp([12],{"79TC":function(e,t){},IENK:function(e,t){},X8rD:function(e,t){},rHSL:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=o("4YfN"),r=o.n(s),i=o("aA9S"),a=o.n(i),n=o("5JDp"),d={customerRules:{telephone:[{required:!0,message:"请输入采购商信息"}],deliveryTime:[{required:!0,message:"请选择配送时间"}],buySeq:[{required:!0,message:"请选择配送时段"}],deliveryType:[{required:!0,message:"请选择配送方式"}]},orderDetailRules:{goodsName:[{required:!0,message:"请输入商品信息"}],goodsId:[{required:!0,message:"请输入商品规格"}],goodsNum:[{required:!0,message:"请输入商品数量"},{pattern:/^[0-9]+(\.[0-9]{1,2})?$/,message:"商品数量必须为数字,最多2位小数",trigger:"blur"},{max:10,message:"长度必需小于10",trigger:"blur"}],buyPrice:[{required:!1,pattern:/^[0-9]+(\.[0-9]{1,2})?$/,message:"价格必须为数字,最多2位小数"},{max:10,message:"长度必需小于10",trigger:"blur"}]}},l={props:{goods:Object,height:Number,width:Number,inCart:{type:Boolean,default:!1}},data:function(){return{imgSrc:"http://img.1000.com/qm-a-img/prod/000000/34927617f8d8317ecffcca63c8360135.jpg@160w_160h.jpg"}},computed:{itemStyle:function(){return"height:"+this.height+"px;width:"+this.width+"px;"},imgStyle:function(){return"width:"+(this.height-10)+"px;height:"+(this.height-10)+"px;"},computedPrice:function(){return(this.goods.goodsPrice||this.goods.marketPrice)*this.goods.goodsNum}},watch:{},mounted:function(){},methods:{}},c={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"goods-item",style:e.itemStyle},[o("div",{staticClass:"goods-cover"},[e.goods.thumbUrl?o("img",{style:e.imgStyle,attrs:{src:e.goods.thumbUrl+"?x-oss-process=image/resize,w_200"}}):e._e()]),e._v(" "),o("div",{staticClass:"goods-info-wrap"},[o("div",{staticClass:"goods-info"},[o("div",{staticClass:"goods-name"},[e._v(e._s(e.goods.goodsName))]),e._v(" "),e.inCart?o("div",{staticClass:"goods-num"},[o("span",[e._v("x ")]),e._v(e._s(e.goods.goodsNum))]):e._e()]),e._v(" "),o("div",{staticClass:"goods-model"},[e._v("\n            "+e._s(e.goods.goodsModel>0?"多规格":"")+"\n        ")]),e._v(" "),e.inCart?o("div",{staticClass:"goods-price"},[e.goods.goodsPrice?o("div",[o("span",{staticClass:"fs14"},[o("span",[e._v("¥ ")]),e._v(e._s(e.goods.goodsPrice))]),e._v(" "),o("span",{staticClass:"fs14 txt-del txt-999"},[o("span",[e._v("¥ ")]),e._v(e._s(e.goods.marketPrice))])]):o("div",{staticClass:"fs14"},[o("span",[e._v("¥ ")]),e._v(e._s(e.goods.marketPrice))]),e._v(" "),o("div",{staticClass:"fs16 txt-red"},[o("span",{staticClass:"fs14"},[e._v("¥ ")]),e._v(e._s(e.computedPrice))])]):e._e(),e._v(" "),e.inCart?e._e():o("div",{staticClass:"goods-price"},[o("div",{staticClass:"fs16 txt-red"},[o("span",{staticClass:"fs14"},[e._v("¥ ")]),e._v(e._s(e.goods.goodsModelList[0].purchasePrice||e.goods.goodsModelList[0].modelPrice))])])])])},staticRenderFns:[]};var u=o("C7Lr")(l,c,!1,function(e){o("w3w5"),o("IENK")},"data-v-b30b5e4a",null).exports,m={data:function(){return{request:new n.a,visibleGoodsModel:!1,goodsList:[],goodsCategories:[],currentPage:1,pageSize:15,categoryId:"",totalCount:0,selectGoodsItem:{},modelId:"",primaryCateId:"",secondCateId:"",loading:!1}},props:{buyerId:String},computed:{primaryCate:function(){return this.goodsCategories.filter(function(e){return 0==e.parentId})},secondCate:function(){var e=this;if(this.primaryCateId)return this.goodsCategories.filter(function(t){return t.parentId==e.primaryCateId})}},components:{goodsItem:u},created:function(){this.fetchCategoryList()},mounted:function(){this.getGoodsList()},methods:{getGoodsList:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,t.buyerId=this.buyerId,t.shopId=$constant.shopId,t.pageNo=this.currentPage,t.pageSize=this.pageSize,this.request.getGoodsList(t).then(function(t){e.loading=!1,200==t.code?(e.goodsList=t.ext.list,e.currentPage=t.ext.pageNo,e.totalCount=t.ext.count):"983"==t.code?e.goodsList=[]:e.$message.error("请求失败")})},selectGoods:function(e){this.selectGoodsItem=e,this.$emit("select",e)},handleCurrentChange:function(e){this.currentPage=e,this.getGoodsList({categoryId:this.secondCateId})},fetchCategoryList:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,t.shopId=$constant.shopId,this.request.fetchCategoryList(t).then(function(t){"200"==t.code?(e.goodsCategories=t.ext,e.loading=!1):e.$message.error("分类数据, 请求失败")})},selectPrimaryCate:function(e){this.categoryId=e,this.primaryCateId=e;var t=this.secondCate[0].id;this.currentPage=1,this.getGoodsList({categoryId:t})},selectSecondCate:function(e){this.secondCateId=e,this.categoryId=e,this.currentPage=1,this.getGoodsList({categoryId:e})}}},g={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{class:["goods-list-wrap",e.categoryId?"wrap-with-cate":""]},[o("div",{staticClass:"goods-list"},[o("div",{staticClass:"goods-aside"},[o("el-menu",{attrs:{"default-active":"","active-text-color":"#235073","text-color":"#999"},on:{select:e.selectPrimaryCate}},[o("el-menu-item",{attrs:{index:""}},[e._v("全部")]),e._v(" "),e._l(e.primaryCate,function(t,s){return o("el-menu-item",{key:s,attrs:{index:t.id}},[e._v("\n                    "+e._s(t.name)+"\n                ")])})],2)],1),e._v(" "),o("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"goods-wrap"},[e.categoryId?o("div",{staticClass:"goods-header"},[o("div",{staticStyle:{width:"600px"}},[o("el-menu",{attrs:{"default-active":e.secondCate[0].id,mode:"horizontal","active-text-color":"#235073","text-color":"#999"},on:{select:e.selectSecondCate}},e._l(e.secondCate,function(t,s){return o("el-menu-item",{key:s,attrs:{index:t.id}},[e._v("\n                            "+e._s(t.name)+"\n                        ")])}),1)],1)]):e._e(),e._v(" "),o("div",{staticClass:"goods-body"},[e._l(e.goodsList,function(t,s){return o("div",{key:s,staticClass:"goods-item",on:{click:function(o){return e.selectGoods(t)}}},[o("el-card",{attrs:{shadow:"hover"}},[o("goods-item",{attrs:{height:80,width:300,goods:t}})],1)],1)}),e._v(" "),o("el-pagination",{directives:[{name:"show",rawName:"v-show",value:e.totalCount,expression:"totalCount"}],attrs:{small:"",layout:"prev, pager, next","page-size":15,"current-page":e.currentPage,total:e.totalCount},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"current-change":e.handleCurrentChange}})],2)])])])},staticRenderFns:[]};var h={data:function(){return{request:new n.a,customerRules:d.customerRules,orderDetailRules:d.orderDetailRules,disabled:!0,customerOrder:this.customerOrderInfo(),orderList:this.orderListInfo(),selectedRowIndex:"",buyerList:[],buySeqs:this.$dic.buySeqOptions,deliveryTypeOptions:this.$dic.deliveryTypeOptions,dialogVisible:!1,printDialogVisible:!1,printTitle:{},printTable:[]}},computed:{},components:{goodsList:o("C7Lr")(m,g,!1,function(e){o("X8rD")},"data-v-2f86f285",null).exports},mounted:function(){this.fetchBuyerList()},methods:{getGoodsList:function(e){return e.pageSize=20,e.buyerId=this.customerOrder.buyerId,this.request.getGoodsList(e).then(function(e){if(200==e.code)return e.ext.list;console.log("商品列表获取错误",e.msg)})},fetchBuyerList:function(){var e=this;this.request.getBuyerList().then(function(t){200==t.code&&(e.buyerList=t.ext)})},addBuyOrder:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.request.addBuyOrder(t).then(function(t){if(200==t.code)return t.ext;e.$alert("请重新下单","提交失败",{confirmButtonText:"确定"})})},onBuyerChange:function(e,t){this.disabled=!1;var o=this.buyerList.map(function(e){return e[t]}).indexOf(e);this.setCustomerForm(o)},setCustomerForm:function(e){var t=this.buyerList[e];this.$set(this.customerOrder,"buyerUid",t.uid),this.$set(this.customerOrder,"buyerId",t.id),this.$set(this.customerOrder,"buyerName",t.buyerName),this.$set(this.customerOrder,"contact",t.contact),this.$set(this.customerOrder,"telephone",t.telephone),this.$set(this.customerOrder,"address",t.address)},searchGoodsByName:function(e){var t=this;return function(o){if(""!==o){e.loading=!0,console.log("time",o);var s={goodsName:o};t.getGoodsList(s).then(function(o){t.$set(e,"goodsList",o),t.$set(e,"loading",!1)})}}},onGoodsSelectChange:function(e,t){if(e){var o=t.goodsList.filter(function(t){return t.id==e})[0];t.goodsModelList=o.goodsModelList,this.$set(t,"goodsName",o.goodsName),this.$set(t,"thumbUrl",o.thumbUrl),this.onModelSelectChange(o.goodsModelList[0].id,t),t.goodsModel=""}else this.$set(t,"goodsId",""),t.goodsId=""},onModelSelectChange:function(e,t){var o=t.goodsModelList.filter(function(t){return t.id===e})[0];this.$set(t,"goodsId",o.id),this.$set(t,"purchasePrice",o.purchasePrice||o.modelPrice),this.$set(t,"goodsImg",o.modelUrl),this.$set(t,"goodsModel",o.modelName),this.$set(t,"unit",o.modelUnit),this.$set(t,"buyPrice",o.buyPrice)},showGoodsDialog:function(e,t){this.dialogVisible=!0,this.selectedRowIndex=t},onSelectGoods:function(e){this.dialogVisible=!1;var t=this.selectedRowIndex,o={goodsName:e.goodsName,goodsModelList:e.goodsModelList,goodsNum:"",remarks:"",thumbUrl:e.thumbUrl};this.$set(this.orderList,t,o),this.onModelSelectChange(o.goodsModelList[0].id,o)},addOrderItem:function(){this.orderList.push({goodsName:"",goodsId:"",goodsImg:"",goodsModel:"",goodsNum:"",purchasePrice:"",buyPrice:"",unit:"",remarks:""})},delOrderItem:function(e,t){this.orderList.splice(t,1)},resetForm:function(){this.disabled=!0,this.customerOrder=this.customerOrderInfo()},submitBuyOrder:function(){var e=this;this.$confirm("您现在已经完成了"+this.orderList.length+"个商品的录入,确认下单？").then(function(t){var o=e.customerOrder,s=e.genOrderDetail();a()(o,r()({},s)),e.checkCustomerRule()&&e.checkOrderRule()&&e.addBuyOrder(o).then(function(t){var o="订单号: "+t.buyId;e.$alert(o,"提交成功",{confirmButtonText:"确定"})}).then(function(){var t=e.customerOrderInfo();t.deliveryTime=o.deliveryTime,t.buySeq=o.buySeq,t.deliveryType=o.deliveryType,e.customerOrder=t,e.disabled=!0,e.orderList=e.orderListInfo()})})},genOrderDetail:function(){var e=0;return{buyOrderDetailList:this.orderList.map(function(t,o){return e+=+t.goodsNum*+t.purchasePrice,{goodsId:t.goodsId,goodsImg:t.goodsImg,goodsName:t.goodsName,goodsModel:t.goodsModel,goodsNum:t.goodsNum,purchasePrice:t.purchasePrice,buyPrice:t.buyPrice,unit:t.unit,remarks:t.remarks}}),genPrice:e.toFixed(2)}},checkCustomerRule:function(){var e=!1;return this.$refs.customerForm.validate(function(t){t?e=t:console.log("客户信息校验错误!!")}),e},checkOrderRule:function(){var e=this,t=!1;return this.orderList.forEach(function(o,s){e.$refs["orderDetail"+s].validate(function(e){e?t=e:console.log("订单信息校验信息!!")})}),t},thStyle:function(){return{background:"#494949",fontSize:"18px",color:"#fff"}},tomorrow:function(){return this.$utils.getDiffDate(1)},customerOrderInfo:function(){return{buyerUid:"",buyerId:"",buyerName:"",contact:"",telephone:"",address:"",needNote:"",deliveryTime:this.tomorrow(),buySeq:"0",genPrice:0,totalNum:0,deliveryType:"9"}},orderListInfo:function(){return[{goodsId:"",goodsImg:"",goodsName:"",goodsModel:"",goodsNum:"",purchasePrice:"",buyPrice:"",unit:"",remarks:"",loading:!1,goodsList:[],goodsModelList:[]}]}}},f={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"page-wrap"},[o("div",{staticClass:"container buyer-info"},[o("h4",{staticClass:"title"},[e._v("基本信息")]),e._v(" "),o("el-form",{ref:"customerForm",attrs:{rules:e.customerRules,model:e.customerOrder,inline:!0,"label-width":"100px"}},[o("el-form-item",{attrs:{label:"手机:",prop:"telephone"}},[o("el-select",{staticClass:"form-field",attrs:{filterable:"",placeholder:"请选择"},on:{change:function(t){return e.onBuyerChange(t,"telephone")}},model:{value:e.customerOrder.telephone,callback:function(t){e.$set(e.customerOrder,"telephone",t)},expression:"customerOrder.telephone"}},e._l(e.buyerList,function(e){return o("el-option",{key:e.telephone,attrs:{label:e.telephone,value:e.telephone}})}),1)],1),e._v(" "),o("el-form-item",{attrs:{label:"联系人:"}},[o("el-select",{staticClass:"form-field",attrs:{filterable:"",placeholder:"请选择"},on:{change:function(t){return e.onBuyerChange(t,"contact")}},model:{value:e.customerOrder.contact,callback:function(t){e.$set(e.customerOrder,"contact",t)},expression:"customerOrder.contact"}},e._l(e.buyerList,function(e){return o("el-option",{key:e.contact,attrs:{label:e.contact,value:e.contact}})}),1)],1),e._v(" "),o("el-form-item",{attrs:{label:"采购商名称:"}},[o("el-select",{staticClass:"form-field",attrs:{filterable:"",placeholder:"请选择"},on:{change:function(t){return e.onBuyerChange(t,"buyerName")}},model:{value:e.customerOrder.buyerName,callback:function(t){e.$set(e.customerOrder,"buyerName",t)},expression:"customerOrder.buyerName"}},e._l(e.buyerList,function(e){return o("el-option",{key:e.buyerName,attrs:{label:e.buyerName,value:e.buyerName}})}),1)],1),e._v(" "),o("el-form-item",{attrs:{label:"配送时间:",prop:"deliveryTime"}},[o("el-date-picker",{staticClass:"form-field",attrs:{editable:!1,type:"date",placeholder:"选择日期","value-format":"yyyy-MM-dd"},model:{value:e.customerOrder.deliveryTime,callback:function(t){e.$set(e.customerOrder,"deliveryTime",t)},expression:"customerOrder.deliveryTime"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"配送时段:",prop:"buySeq"}},[o("el-select",{staticClass:"form-field",attrs:{placeholder:"请选择"},model:{value:e.customerOrder.buySeq,callback:function(t){e.$set(e.customerOrder,"buySeq",t)},expression:"customerOrder.buySeq"}},e._l(e.buySeqs,function(e){return o("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1),e._v(" "),o("el-form-item",{attrs:{label:"配送方式:",prop:"deliveryType"}},[o("el-select",{staticClass:"form-field",attrs:{placeholder:"请选择"},model:{value:e.customerOrder.deliveryType,callback:function(t){e.$set(e.customerOrder,"deliveryType",t)},expression:"customerOrder.deliveryType"}},e._l(e.deliveryTypeOptions,function(e){return o("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1),e._v(" "),o("el-form-item",{attrs:{label:"备注:",prop:"needNote"}},[o("el-input",{staticClass:"form-field",attrs:{placeholder:""},model:{value:e.customerOrder.needNote,callback:function(t){e.$set(e.customerOrder,"needNote",t)},expression:"customerOrder.needNote"}})],1),e._v(" "),o("el-form-item",{attrs:{label:" "}},[o("el-button",{attrs:{type:"primary"},on:{click:e.resetForm}},[e._v("重置")])],1)],1)],1),e._v(" "),o("div",{staticClass:"container body"},[o("h4",{staticClass:"title"},[e._v("采购列表")]),e._v(" "),o("el-table",{staticClass:"goods-list",attrs:{"max-height":"450","show-header":!1,data:e.orderList,border:"","header-cell-style":e.thStyle}},[o("el-table-column",{attrs:{width:"50"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("div",{staticStyle:{"text-align":"center"}},[e._v(e._s(t.$index+1))])]}}])}),e._v(" "),o("el-table-column",{attrs:{type:"index",width:"100"},scopedSlots:e._u([{key:"default",fn:function(e){return[o("img",{staticStyle:{display:"block",width:"50px",height:"50px"},attrs:{src:e.row.thumbUrl+"?x-oss-process=image/resize,w_200",alt:""}})]}}])}),e._v(" "),o("el-table-column",{attrs:{prop:"buyerName"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("el-form",{ref:"orderDetail"+t.$index,staticClass:"demo-form-inline",attrs:{rules:e.orderDetailRules,inline:!0,model:t.row,"label-width":"60px"}},[o("el-row",{staticClass:"row"},[o("el-form-item",{attrs:{label:"商品名称:",prop:"goodsName","label-width":"100px"}},[o("el-select",{staticClass:"form-field--large",attrs:{clearable:"",filterable:"","default-first-option":"",disabled:e.disabled,remote:"","remote-method":e.searchGoodsByName(t.row),loading:t.row.loading,placeholder:"请输入商品名称"},on:{change:function(o){return e.onGoodsSelectChange(o,t.row)}},model:{value:t.row.goodsName,callback:function(o){e.$set(t.row,"goodsName",o)},expression:"scope.row.goodsName"}},e._l(t.row.goodsList,function(e){return o("el-option",{key:e.id,attrs:{label:e.goodsName,value:e.id}})}),1),e._v(" "),o("el-button",{attrs:{type:"text",disabled:e.disabled},on:{click:function(o){return e.showGoodsDialog(o,t.$index)}}},[e._v("所有商品\n                                ")])],1),e._v(" "),o("el-form-item",{attrs:{label:"规格:",prop:"goodsId"}},[o("el-select",{staticClass:"form-field",attrs:{placeholder:"请选择"},on:{change:function(o){return e.onModelSelectChange(o,t.row)}},model:{value:t.row.goodsId,callback:function(o){e.$set(t.row,"goodsId",o)},expression:"scope.row.goodsId"}},e._l(t.row.goodsModelList,function(e){return o("el-option",{key:e.id,attrs:{label:e.modelName+" /单位:"+e.modelUnit,value:e.id}})}),1)],1),e._v(" "),o("el-form-item",{directives:[{name:"show",rawName:"v-show",value:t.row.purchasePrice,expression:"scope.row.purchasePrice"}],attrs:{label:"市场价:"}},[e._v("\n                                "+e._s(t.row.purchasePrice)+"元\n                            ")]),e._v(" "),o("el-form-item",{attrs:{label:"采购价:",prop:"buyPrice"}},[o("el-input",{staticClass:"form-field--small",attrs:{maxlength:"10",placeholder:"采购价"},model:{value:t.row.buyPrice,callback:function(o){e.$set(t.row,"buyPrice",o)},expression:"scope.row.buyPrice"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"数量:",prop:"goodsNum"}},[o("el-input",{staticClass:"form-field--small",attrs:{maxlength:"10",placeholder:"采购数量"},model:{value:t.row.goodsNum,callback:function(o){e.$set(t.row,"goodsNum",o)},expression:"scope.row.goodsNum"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"备注:",prop:"remarks"}},[o("el-input",{staticClass:"form-field--small",attrs:{placeholder:"备注"},model:{value:t.row.remarks,callback:function(o){e.$set(t.row,"remarks",o)},expression:"scope.row.remarks"}})],1)],1)],1)]}}])}),e._v(" "),o("el-table-column",{attrs:{width:"120",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("el-button",{attrs:{type:"danger",plain:"",icon:"el-icon-hh-delete"},on:{click:function(o){return e.delOrderItem(t.row,t.$index)}}},[e._v("删除\n                    ")])]}}])})],1),e._v(" "),o("div",{staticStyle:{"margin-top":"10px"}},[o("el-button",{attrs:{type:"primary",plain:"",icon:"el-icon-hh-add1"},on:{click:e.addOrderItem}},[e._v("增加\n            ")]),e._v(" "),o("el-button",{attrs:{type:"primary"},on:{click:e.submitBuyOrder}},[e._v("提交订单\n            ")])],1),e._v(" "),o("el-dialog",{attrs:{title:"选择商品",visible:e.dialogVisible},on:{"update:visible":function(t){e.dialogVisible=t}}},[o("goods-list",{attrs:{"buyer-id":e.customerOrder.buyerId},on:{select:e.onSelectGoods}})],1)],1)])},staticRenderFns:[]};var p=o("C7Lr")(h,f,!1,function(e){o("79TC")},"data-v-d22e4d4c",null);t.default=p.exports},w3w5:function(e,t){}});