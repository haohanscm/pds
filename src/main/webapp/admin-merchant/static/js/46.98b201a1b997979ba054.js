webpackJsonp([46],{Qosq:function(e,t){},hC4l:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("4YfN"),o=a.n(l),i=a("zdsy"),r={data:function(){return{}},props:{form:{type:Object,default:{}}},methods:{onSubmit:function(){console.log("submit!")}}},s={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"采购商"}},[a("el-input",{model:{value:e.form.buyerName,callback:function(t){e.$set(e.form,"buyerName",t)},expression:"form.buyerName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"简称"}},[a("el-input",{model:{value:e.form.shortName,callback:function(t){e.$set(e.form,"shortName",t)},expression:"form.shortName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"联系人"}},[a("el-input",{model:{value:e.form.contact,callback:function(t){e.$set(e.form,"contact",t)},expression:"form.contact"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"联系电话"}},[a("el-input",{model:{value:e.form.telephone,callback:function(t){e.$set(e.form,"telephone",t)},expression:"form.telephone"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"账期"}},[a("el-input",{model:{value:e.form.payPeriod,callback:function(t){e.$set(e.form,"payPeriod",t)},expression:"form.payPeriod"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"地址"}},[a("el-input",{model:{value:e.form.address,callback:function(t){e.$set(e.form,"address",t)},expression:"form.address"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"商家名称"}},[a("el-input",{model:{value:e.form.merchantName,callback:function(t){e.$set(e.form,"merchantName",t)},expression:"form.merchantName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"备注"}},[a("el-input",{model:{value:e.form.bizDesc,callback:function(t){e.$set(e.form,"bizDesc",t)},expression:"form.bizDesc"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"推送"}},[a("el-select",{attrs:{placeholder:"请选择推送状态"},model:{value:e.form.needPush,callback:function(t){e.$set(e.form,"needPush",t)},expression:"form.needPush"}},[a("el-option",{attrs:{label:"否",value:"0"}}),e._v(" "),a("el-option",{attrs:{label:"是",value:"1"}})],1)],1)],1)],1)},staticRenderFns:[]},n={data:function(){return{pageSize:20,pageNo:1,total:0,request:new i.a,editValue:{},dialogFormVisible:!1,buyerList:[],status:{0:"待审核",2:"启用","-1":"停用"},merchantName:"",contact:"",selectStatus:""}},components:{editTemp:a("C7Lr")(r,s,!1,null,null,null).exports},watch:{},created:function(){this.fetchBuyerList()},methods:{handlePageChange:function(e){this.pageSize=e.pageSize,this.pageNo=e.pageNo,this.fetchBuyerList()},edit:function(e){this.editValue=e,this.dialogFormVisible=!0},newList:function(){this.editValue={},this.dialogFormVisible=!0},confirm:function(){this.dialogFormVisible=!1,this.editBuyer()},fetchBuyerList:function(){var e=this;this.request.getBuyerList({status:this.selectStatus,contact:this.contact,pageSize:this.pageSize,pageNo:this.pageNo,merchantName:this.merchantName}).then(function(t){200==t.code?(e.buyerList=t.ext.list,e.total=t.ext.count):e.$message.warning("未查询到采购商列表："+t.msg)})},editBuyer:function(){var e=this;this.request.editBuyer(o()({},this.editValue)).then(function(t){200===t.code?(e.$message.success("编辑成功！"),e.fetchBuyerList()):e.$message.warning("编辑失败："+t.msg)})},deleteBuyer:function(e){var t=this;this.$confirm("您确认要删除该采购商？").then(function(a){t.request.deleteBuyer({id:e}).then(function(e){200===e.code?(t.$message.success("删除成功！"),t.fetchBuyerList()):t.$message.warning("删除失败："+e.msg)})})}}},c={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"title"},[a("div",{staticStyle:{display:"flex"}},[e._v("\n            商家名称: "),a("el-input",{staticStyle:{flex:"1"},attrs:{placeholder:"请输入商家名称"},model:{value:e.merchantName,callback:function(t){e.merchantName=t},expression:"merchantName"}})],1),e._v(" "),a("div",{staticStyle:{display:"flex"}},[e._v("\n            联系人: "),a("el-input",{staticStyle:{flex:"1"},attrs:{placeholder:"请输入联系人姓名"},model:{value:e.contact,callback:function(t){e.contact=t},expression:"contact"}})],1),e._v(" "),a("div",[a("el-button",{attrs:{type:"primary"},on:{click:e.fetchBuyerList}},[e._v("查询")])],1),e._v(" "),a("div",[a("el-button",{attrs:{type:"primary"},on:{click:e.newList}},[e._v("新增")])],1)]),e._v(" "),a("div",[a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.buyerList,border:""}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"buyerName",label:"采购商"}}),e._v(" "),a("el-table-column",{attrs:{prop:"shortName",label:"简称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"contact",label:"联系人"}}),e._v(" "),a("el-table-column",{attrs:{prop:"telephone",label:"电话"}}),e._v(" "),a("el-table-column",{attrs:{prop:"payPeriod",label:"账期"}}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"地址"}}),e._v(" "),a("el-table-column",{attrs:{prop:"merchantName",label:"商家名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"消息推送"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("div",[e._v("\n                       "+e._s("0"===t.row.needPush?"否":"是")+" \n                    ")])]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"操作",width:"100px"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("div",[a("el-button",{attrs:{type:"text"},on:{click:function(a){return e.edit(t.row)}}},[e._v("编辑")]),e._v(" "),a("el-button",{attrs:{type:"text"},on:{click:function(a){return e.deleteBuyer(t.row.id)}}},[e._v("删除")])],1)]}}])})],1)],1),e._v(" "),a("div",{staticClass:"page-box"},[a("div"),e._v(" "),a("my-pagination",{attrs:{"page-size":e.pageSize,"current-page":e.pageNo,total:e.total,"page-sizes":[20,30,40,50]},on:{pagechange:e.handlePageChange}})],1),e._v(" "),a("el-dialog",{attrs:{title:"商家编辑",visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("edit-temp",{attrs:{form:e.editValue}}),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.confirm}},[e._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var u=a("C7Lr")(n,c,!1,function(e){a("Qosq")},"data-v-4824ca72",null);t.default=u.exports}});