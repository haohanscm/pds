webpackJsonp([31],{yVQK:function(e,t){},zf6Y:function(e,t,l){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=l("4YfN"),o=l.n(a),i=l("64PB"),r={data:function(){return{}},props:{form:{type:Object,default:{}}},methods:{onSubmit:function(){console.log("submit!")}}},s={render:function(){var e=this,t=e.$createElement,l=e._self._c||t;return l("div",[l("el-form",{ref:"form",attrs:{model:e.form,"label-width":"80px"}},[l("el-form-item",{attrs:{label:"供应商名称"}},[l("el-input",{model:{value:e.form.supplierName,callback:function(t){e.$set(e.form,"supplierName",t)},expression:"form.supplierName"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"标签"}},[l("el-input",{model:{value:e.form.tags,callback:function(t){e.$set(e.form,"tags",t)},expression:"form.tags"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"简称"}},[l("el-input",{model:{value:e.form.shortName,callback:function(t){e.$set(e.form,"shortName",t)},expression:"form.shortName"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"联系人"}},[l("el-input",{model:{value:e.form.contact,callback:function(t){e.$set(e.form,"contact",t)},expression:"form.contact"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"电话"}},[l("el-input",{model:{value:e.form.telephone,callback:function(t){e.$set(e.form,"telephone",t)},expression:"form.telephone"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"账期"}},[l("el-input",{model:{value:e.form.payPeriod,callback:function(t){e.$set(e.form,"payPeriod",t)},expression:"form.payPeriod"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"供应商地址"}},[l("el-input",{model:{value:e.form.address,callback:function(t){e.$set(e.form,"address",t)},expression:"form.address"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"商家名称"}},[l("el-input",{model:{value:e.form.merchantName,callback:function(t){e.$set(e.form,"merchantName",t)},expression:"form.merchantName"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"类型"}},[l("el-select",{attrs:{placeholder:"请选择认证状态"},model:{value:e.form.supplierType,callback:function(t){e.$set(e.form,"supplierType",t)},expression:"form.supplierType"}},[l("el-option",{attrs:{label:"库存供应商",value:"1"}}),e._v(" "),l("el-option",{attrs:{label:"一般供应商",value:"0"}})],1)],1),e._v(" "),l("el-form-item",{attrs:{label:"消息推送"}},[l("el-select",{attrs:{placeholder:"请选择认证状态"},model:{value:e.form.authType,callback:function(t){e.$set(e.form,"authType",t)},expression:"form.authType"}},[l("el-option",{attrs:{label:"是",value:"1"}}),e._v(" "),l("el-option",{attrs:{label:"否",value:"0"}})],1)],1)],1)],1)},staticRenderFns:[]},n={data:function(){return{pageSize:20,pageNo:1,total:0,layout:"total, sizes, prev, pager, next, jumper",request:new i.a,editValue:{},dialogFormVisible:!1,buyerList:[],status:{0:"待审核",2:"启用","-1":"停用"},authStatus:{0:"未认证",1:"已认证"}}},components:{editTemp:l("C7Lr")(r,s,!1,null,null,null).exports},watch:{},created:function(){this.getSupplierList()},methods:{handlePageChange:function(e){this.pageSize=e.pageSize,this.pageNo=e.pageNo,this.getSupplierList()},edit:function(e){this.editValue=e,this.dialogFormVisible=!0},newList:function(){this.editValue={},this.dialogFormVisible=!0},confirm:function(){this.dialogFormVisible=!1,this.editSupplier()},getSupplierList:function(){var e=this;this.request.getSupplierList({pageSize:this.pageSize,pageNo:this.pageNo}).then(function(t){200==t.code?(e.buyerList=t.ext.list,e.total=t.ext.count):e.$message.warning("未查询到采购商列表："+t.msg)})},editSupplier:function(){var e=this;this.request.editSupplier(o()({},this.editValue)).then(function(t){200===t.code?e.$message.success("编辑成功！"):e.$message.warning("编辑失败："+t.msg),e.getSupplierList()})},deleteSupplier:function(e){var t=this;this.$confirm("您确认要删除该店铺？").then(function(l){t.request.deleteSupplier({id:e}).then(function(e){200===e.code?(t.$message.success("删除成功！"),t.getSupplierList()):t.$message.warning("删除失败："+e.msg)})})}}},u={render:function(){var e=this,t=e.$createElement,l=e._self._c||t;return l("div",[l("div",{staticClass:"title"},[l("div",{staticStyle:{display:"flex"}},[e._v("供应商名称: "),l("el-input",{staticStyle:{flex:"1"}})],1),e._v(" "),l("div",[l("el-button",{attrs:{type:"primary"}},[e._v("查询")])],1),e._v(" "),l("div",[l("el-button",{attrs:{type:"primary"},on:{click:e.newList}},[e._v("新增")])],1)]),e._v(" "),l("div",[l("el-table",{staticStyle:{width:"100%"},attrs:{data:e.buyerList,border:""}},[l("el-table-column",{attrs:{type:"index",width:"50"}}),e._v(" "),l("el-table-column",{attrs:{prop:"supplierName",label:"供应商名称"}}),e._v(" "),l("el-table-column",{attrs:{prop:"tags",label:"标签"}}),e._v(" "),l("el-table-column",{attrs:{prop:"shortName",label:"简称"}}),e._v(" "),l("el-table-column",{attrs:{prop:"contact",label:"联系人"}}),e._v(" "),l("el-table-column",{attrs:{prop:"telephone",label:"电话"}}),e._v(" "),l("el-table-column",{attrs:{prop:"payPeriod",label:"账期"}}),e._v(" "),l("el-table-column",{attrs:{prop:"address",label:"供应商地址地址"}}),e._v(" "),l("el-table-column",{attrs:{prop:"merchantName",label:"商家名称"}}),e._v(" "),l("el-table-column",{attrs:{prop:"isUpdateJisu",label:"类型",width:"100px"},scopedSlots:e._u([{key:"default",fn:function(t){return[l("div",[e._v("\n                       "+e._s("1"===t.row.supplierType?"库存供应商":"一般供应商")+"\n                    ")])]}}])}),e._v(" "),l("el-table-column",{attrs:{prop:"address",label:"消息推送"},scopedSlots:e._u([{key:"default",fn:function(t){return[l("div",[e._v("\n                       "+e._s("0"===t.row.needPush?"否":"是")+"\n                    ")])]}}])}),e._v(" "),l("el-table-column",{attrs:{label:"操作",width:"100px"},scopedSlots:e._u([{key:"default",fn:function(t){return[l("div",[l("el-button",{attrs:{type:"text"},on:{click:function(l){return e.edit(t.row)}}},[e._v("编辑")])],1)]}}])})],1)],1),e._v(" "),l("div",{staticClass:"page-box"},[l("div"),e._v(" "),l("my-pagination",{attrs:{"page-size":e.pageSize,"current-page":e.pageNo,total:e.total,layout:e.layout,"page-sizes":[20,30,40,50]},on:{pagechange:e.handlePageChange}})],1),e._v(" "),l("el-dialog",{attrs:{title:"供应商编辑",visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[l("edit-temp",{attrs:{form:e.editValue}}),e._v(" "),l("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[l("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),l("el-button",{attrs:{type:"primary"},on:{click:e.confirm}},[e._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var p=l("C7Lr")(n,u,!1,function(e){l("yVQK")},"data-v-992efafc",null);t.default=p.exports}});