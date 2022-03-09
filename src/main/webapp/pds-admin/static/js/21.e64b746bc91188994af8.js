webpackJsonp([21],{DihN:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=s("8uth"),i=s.n(a),l=s("5JDp");var o={buyConstruct:function(){for(var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[],e={buyNum:"",buySeq:"",goodsModel:"",goodsName:"",goodsUnit:"",offerOrderId:"",prepareDate:"",supplyPrice:""},s=20-t.length,a=0;a<s;a++)t.push(e);return t}},n={data:function(){return{request:new l.a,pageSize:20,pageNo:1,total:0,totalPage:0,totalKinds:0,options:this.$dic.buySeqOptions,list:[],pmName:this.$utils.getCookie("pmName")}},props:{value:Object,date:String,buySeq:String},watch:{value:{handler:function(t,e){this.fetchOfferList(t.id)},immediate:!0}},computed:{totalPrice:function(){var t=0;return this.list.forEach(function(e){t+=+(e.buyNum*e.supplyPrice).toFixed(2)}),t.toFixed(2)},totalCharge:function(){return(+this.totalPrice+this.value.shipFee).toFixed(2)},totalNum:function(){var t=0;return this.list.forEach(function(e){t+=e.buyNum}),t}},methods:{exportExcel:function(){i.a.getExcel("ddd","","供应单.xls","bb")},checkSupplierList:function(){this.fetchBuyOrderList()},changeNo:function(t){console.log(t),this.pageNo=t,this.fetchOfferList()},printTable:function(){var t=document.getElementById("adasd").innerHTML;window.document.body.innerHTML=t,window.print()},fetchOfferList:function(t){var e=this;this.request.fetchOfferList({prepareDate:this.date,buySeq:this.buySeq,supplierId:t||this.value.id,pageSize:this.pageSize,pageNo:this.pageNo}).then(function(t){200==t.code?(e.totalKinds=t.ext.list.length,e.list=o.buyConstruct(t.ext.list),e.totalPage=t.ext.totalPage,e.total=t.ext.totalRows):(e.$message.warning(t.msg),e.list=o.buyConstruct([]))})}}},r={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("div",{staticStyle:{width:"840px"},attrs:{id:"adasd"}},[s("div",{staticStyle:{"text-align":"center","font-size":"14px",color:"#bbb","padding-bottom":"10px"}},[t._v(t._s(t.pmName))]),t._v(" "),s("div",{staticStyle:{display:"flex","align-items":"center"}},[s("table",{attrs:{border:"1",id:"ddd"}},[s("caption",{attrs:{colspan:"9"}},[t._v("供应单")]),t._v(" "),s("tbody",[s("tr",[s("th",{staticClass:"bc-yellow",attrs:{colspan:"2"}},[t._v("供应商名称")]),t._v(" "),s("td",{attrs:{colspan:"4"}},[t._v(t._s(t.value.supplierName))]),t._v(" "),s("td",{attrs:{colspan:"3",rowspan:"2"}},[t._v("重庆君磊农产品配送有限公司")])]),t._v(" "),s("tr",[s("th",{staticClass:"bc-yellow",attrs:{colspan:"2"}},[t._v("供应日期")]),t._v(" "),s("td",{attrs:{colspan:"1"}},[t._v(t._s(t.date))]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("批次")]),t._v(" "),s("td",{attrs:{colspan:"2"}},[t._v(t._s(t.options[t.buySeq].label))])]),t._v(" "),t._m(0),t._v(" "),s("tr",[s("td",{attrs:{colspan:"4"}},[t._v(t._s(t.value.address))]),t._v(" "),s("td",{attrs:{colspan:"3"}},[t._v(t._s(t.value.contact))]),t._v(" "),s("td",{attrs:{colspan:"2"}},[t._v(t._s(t.value.telephone))])]),t._v(" "),t._m(1),t._v(" "),t._m(2),t._v(" "),t._l(t.list,function(e,a){return s("tr",{key:a},[s("th",{staticClass:"bc-yellow"},[t._v(t._s(a+1))]),t._v(" "),s("td",[t._v(t._s(e.offerOrderId))]),t._v(" "),s("td",[t._v(t._s(e.goodsCategory))]),t._v(" "),s("td",[t._v(t._s(e.goodsName))]),t._v(" "),s("td",[t._v(t._s(e.goodsModel))]),t._v(" "),s("td",[t._v(t._s(e.goodsUnit))]),t._v(" "),s("td",[t._v(t._s(e.buyNum))]),t._v(" "),s("td",[t._v(t._s(e.supplyPrice))]),t._v(" "),s("td",[t._v(t._s(+(e.supplyPrice*e.buyNum).toFixed(2)||""))])])}),t._v(" "),t._m(3),t._v(" "),s("tr",[s("th",{staticClass:"total",attrs:{colspan:"2",rowspan:"2"}},[t._v("合计")]),t._v(" "),s("th",{staticClass:"bc-yellow",attrs:{colspan:"1"}},[t._v("商品种类")]),t._v(" "),s("td",{attrs:{colspan:"2"}},[t._v(t._s(t.totalKinds))]),t._v(" "),s("th",{staticClass:"bc-yellow",attrs:{colspan:"2",rowspan:"2"}},[t._v("账款合计（元）：")]),t._v(" "),s("td",{attrs:{colspan:"2",rowspan:"2"}},[t._v(t._s(t.totalPrice))])]),t._v(" "),s("tr",[s("th",{staticClass:"bc-yellow"},[t._v("采购数量")]),t._v(" "),s("td",{attrs:{colspan:"2"}},[t._v(t._s(t.totalNum))])])],2)]),t._v(" "),s("div",{staticClass:"padRight"},[t._v("第一联白（记账联） 第二联粉（供应商留存）")])]),t._v(" "),t._m(4),t._v(" "),s("div",{staticStyle:{"text-align":"center","font-size":"14px",color:"#bbb"}},[t._v("\n            第"+t._s(t.pageNo)+"页-共"+t._s(t.totalPage)+"页\n        ")])]),t._v(" "),s("div",[s("el-pagination",{attrs:{layout:"prev, pager, next","page-size":20,total:t.total},on:{"current-change":t.changeNo}})],1),t._v(" "),s("a",{attrs:{id:"bb"},on:{click:t.exportExcel}},[t._v("导出excel")]),t._v(" "),s("a",{attrs:{href:""},on:{click:t.printTable}},[t._v("打印")])])},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("tr",[e("th",{staticClass:"bc-yellow",attrs:{colspan:"4"}},[this._v("供应商地址")]),this._v(" "),e("th",{staticClass:"bc-yellow",attrs:{colspan:"3"}},[this._v("联系人")]),this._v(" "),e("th",{staticClass:"bc-yellow",attrs:{colspan:"2"}},[this._v("联系电话")])])},function(){var t=this.$createElement,e=this._self._c||t;return e("tr",[e("td",{attrs:{colspan:"9"}},[this._v(" ")])])},function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("tr",[s("th",{staticClass:"bc-yellow"},[t._v("序号")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("供应单编号")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("商品分类")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("商品名称")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("规格")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("单位")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("供应数量")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("单价")]),t._v(" "),s("th",{staticClass:"bc-yellow"},[t._v("应收款")])])},function(){var t=this.$createElement,e=this._self._c||t;return e("tr",[e("td",{attrs:{colspan:"9"}},[this._v(" ")])])},function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"footer"},[s("div",{staticClass:"footer-img"},[s("img",{attrs:{src:"http://haohanshop-file.oss-cn-beijing.aliyuncs.com/merchantFiles/df52d9b3b17643f6916c7d27279a268d/00/20181107/15415868614447adD.png",alt:""}}),t._v(" "),s("p",[t._v("关注公众号，自助下单")])]),t._v(" "),s("div",{staticClass:"right"},[s("div",[s("p",[t._v("♦ 请确保清单上物品信息与实际发出物品的信息相符合；")]),t._v(" "),s("p",[t._v("♦ 本人承诺所供应物品的质量完全合格，并愿意承担相应责任；")]),t._v(" "),s("p",[t._v("♦ 请在确认货物供应单内容均为正确且属实后，进行签字，服务热线：4000-562-180。")])]),t._v(" "),s("div",{staticClass:"footer-sign"},[s("div",[t._v("供应商签字：")]),t._v(" "),s("div",[t._v("日期：")])])])])}]};var c={components:{vDetail:s("C7Lr")(n,r,!1,function(t){s("i7h8")},"data-v-56adcfaa",null).exports},data:function(){return{request:new l.a,buySeq:"0",options:this.$dic.buySeqOptions,supplierList:[],dialogTableVisible:!1,deliveryTime:(new Date).getFullYear()+"-"+((new Date).getMonth()+1)+"-"+(new Date).getDate(),title:{},detail:{},goodsList:[]}},created:function(){this.getSupplierList()},methods:{handleTableClick:function(t,e){this.title=t,this.dialogTableVisible=!0},exportExcel:function(){i.a.getExcel("ddd","","供应单.xls","bb")},getSupplierList:function(){var t=this;this.request.getSupplierList().then(function(e){200==e.code?t.supplierList=e.ext:t.supplierList=[]})},fetchOfferList:function(t){var e=this;this.request.fetchOfferList({prepareDate:this.deliveryTime,buySeq:this.buySeq,supplierId:t}).then(function(t){200==t.code&&(e.goodsList=t.ext)})}}},v={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("div",{staticClass:"title"},[s("div",[s("div",{staticClass:"block"},[t._v("\n                备货日期："),s("el-date-picker",{attrs:{type:"date","value-format":"yyyy-MM-dd","default-value":new Date,placeholder:"选择日期"},model:{value:t.deliveryTime,callback:function(e){t.deliveryTime=e},expression:"deliveryTime"}})],1)]),t._v(" "),s("div",[t._v("批次:"),s("el-select",{attrs:{placeholder:"请选择"},model:{value:t.buySeq,callback:function(e){t.buySeq=e},expression:"buySeq"}},t._l(t.options,function(t){return s("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})}),1)],1),t._v(" "),s("div",[s("el-button",{attrs:{type:"primary"},on:{click:t.getSupplierList}},[t._v("查询")])],1)]),t._v(" "),s("div",[s("el-table",{staticStyle:{width:"100%"},attrs:{data:t.supplierList,border:""}},[s("el-table-column",{attrs:{prop:"supplierName",label:"供应商名称"}}),t._v(" "),s("el-table-column",{attrs:{prop:"address",label:"地址"}}),t._v(" "),s("el-table-column",{attrs:{prop:"contact",label:"联系人"}}),t._v(" "),s("el-table-column",{attrs:{prop:"telephone",label:"联系电话"}}),t._v(" "),s("el-table-column",{attrs:{prop:"",label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[s("el-button",{attrs:{type:"text"},on:{click:function(s){return t.handleTableClick(e.row,e.$index)}}},[t._v("查看供应单")])]}}])})],1),t._v(" "),s("el-dialog",{attrs:{title:"供应单",visible:t.dialogTableVisible,width:"900px"},on:{"update:visible":function(e){t.dialogTableVisible=e}}},[s("v-detail",{attrs:{date:t.deliveryTime,buySeq:t.buySeq},model:{value:t.title,callback:function(e){t.title=e},expression:"title"}})],1)],1)])},staticRenderFns:[]};var d=s("C7Lr")(c,v,!1,function(t){s("Mh+j")},"data-v-a8f0e822",null);e.default=d.exports},"Mh+j":function(t,e){},i7h8:function(t,e){}});