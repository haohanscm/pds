webpackJsonp([51],{ZUEk:function(e,t){},cEM3:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("4YfN"),i=a.n(l),s=a("U3y0"),r=a("a3Yh"),o={data:function(){return{request:new s.a,supList:[]}},props:{form:{type:Object,default:{}}},created:function(){this.getSupplierList()},methods:a.n(r)()({onSubmit:function(){console.log("submit!")},getSupplierList:function(){var e=this;this.request.getSupplierList({pageSize:1e3}).then(function(t){200==t.code?e.supList=t.ext.list:e.$message.warning("未查询到采购商列表："+t.msg)})}},"getSupplierList",function(){var e=this;this.request.getSupplierList({pageSize:1e3}).then(function(t){200==t.code?e.supList=t.ext.list:e.$message.warning("未查询到采购商列表："+t.msg)})})},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"供应日期"}},[a("el-date-picker",{attrs:{"value-format":"yyyy-MM-dd",type:"date",placeholder:"选择日期"},model:{value:e.form.supplyDate,callback:function(t){e.$set(e.form,"supplyDate",t)},expression:"form.supplyDate"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"供应商"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.form.supplierId,callback:function(t){e.$set(e.form,"supplierId",t)},expression:"form.supplierId"}},e._l(e.supList,function(e){return a("el-option",{key:e.id,attrs:{label:e.supplierName,value:e.id}})}),1)],1)],1)],1)},staticRenderFns:[]},p={data:function(){return{pageSize:20,pageNo:1,total:0,request:new s.a,editValue:{},dialogFormVisible:!1,list:[]}},watch:{},components:{editTemp:a("C7Lr")(o,n,!1,null,null,null).exports},created:function(){this.getSupamountList()},methods:{handlePageChange:function(e){this.pageSize=e.pageSize,this.pageNo=e.pageNo,this.getSupamountList()},generate:function(){this.dialogFormVisible=!0},creatOrder:function(){this.dialogFormVisible=!1,this.batchCreateSup()},getSupamountList:function(){var e=this;this.request.getSupamountList({pageSize:this.pageSize,pageNo:this.pageNo}).then(function(t){200==t.code?(e.list=t.ext.list,e.total=t.ext.count):e.$message.warning("未查询到结算记录："+t.msg)})},batchCreateSup:function(){var e=this;this.request.batchCreateSup(i()({},this.editValue)).then(function(t){200==t.code?e.$message.success(t.msg):e.$message.warning(t.msg)})}}},u={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"title"},[a("div",{staticStyle:{display:"flex"}},[e._v("供应商名称: "),a("el-input",{staticStyle:{flex:"1"}})],1),e._v(" "),a("div",[a("el-button",{attrs:{type:"primary"}},[e._v("查询")])],1),e._v(" "),a("div",[a("el-button",{attrs:{type:"primary"},on:{click:e.generate}},[e._v("生成供应商货款")])],1)]),e._v(" "),a("div",[a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.list,border:""}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"supplierPaymentId",label:"货款编号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"supplierName",label:"供应商名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"merchantName",label:"商家名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"supplyDate",label:"供应日期"}}),e._v(" "),a("el-table-column",{attrs:{prop:"supplierPayment",label:"供应货款"}}),e._v(" "),a("el-table-column",{attrs:{prop:"goodsNum",label:"商品数量"}}),e._v(" "),a("el-table-column",{attrs:{prop:"status",label:"结算状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("div",[e._v("\n                        "+e._s("1"===t.row.status?"已结算":"未结算")+"\n                    ")])]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"operator",label:"经办人名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"askOrderId",label:"对应货款单编号"}})],1)],1),e._v(" "),a("div",{staticClass:"page-box"},[a("div"),e._v(" "),a("my-pagination",{attrs:{"page-size":e.pageSize,"current-page":e.pageNo,total:e.total,"page-sizes":[20,30,40,50]},on:{pagechange:e.handlePageChange}})],1),e._v(" "),a("el-dialog",{attrs:{title:"商家编辑",visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("edit-temp",{attrs:{form:e.editValue}}),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.creatOrder}},[e._v("生成货款")])],1)],1)],1)},staticRenderFns:[]};var c=a("C7Lr")(p,u,!1,function(e){a("ZUEk")},"data-v-2243a05b",null);t.default=c.exports}});