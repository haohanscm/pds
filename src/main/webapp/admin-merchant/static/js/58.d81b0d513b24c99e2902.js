webpackJsonp([58],{"32eb":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("4YfN"),r=a.n(l),o=a("eofT"),i={data:function(){return{}},props:{form:{type:Object,default:{}}},methods:{onSubmit:function(){console.log("submit!")}}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"车牌号"}},[a("el-input",{model:{value:e.form.truckNo,callback:function(t){e.$set(e.form,"truckNo",t)},expression:"form.truckNo"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"车辆名称"}},[a("el-input",{model:{value:e.form.truckName,callback:function(t){e.$set(e.form,"truckName",t)},expression:"form.truckName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"负责人"}},[a("el-input",{model:{value:e.form.principal,callback:function(t){e.$set(e.form,"principal",t)},expression:"form.principal"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"联系电话"}},[a("el-input",{model:{value:e.form.telephone,callback:function(t){e.$set(e.form,"telephone",t)},expression:"form.telephone"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"品牌"}},[a("el-input",{model:{value:e.form.brand,callback:function(t){e.$set(e.form,"brand",t)},expression:"form.brand"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"载重"}},[a("el-input",{model:{value:e.form.carryWeight,callback:function(t){e.$set(e.form,"carryWeight",t)},expression:"form.carryWeight"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"容积"}},[a("el-input",{model:{value:e.form.carryVolume,callback:function(t){e.$set(e.form,"carryVolume",t)},expression:"form.carryVolume"}})],1)],1)],1)},staticRenderFns:[]},s={data:function(){return{pageSize:20,pageNo:1,total:0,request:new o.a,editValue:{},dialogFormVisible:!1,buyerList:[],status:{0:"休息中",1:"空闲中",2:"送货中"},authStatus:{0:"未认证",1:"已认证"}}},components:{editTemp:a("C7Lr")(i,n,!1,null,null,null).exports},watch:{},created:function(){this.getCarList()},methods:{handlePageChange:function(e){this.pageSize=e.pageSize,this.pageNo=e.pageNo,this.getCarList()},edit:function(e){this.editValue=e,this.dialogFormVisible=!0},confirm:function(){this.dialogFormVisible=!1,this.editCar()},getCarList:function(){var e=this;this.request.getCarList({pageSize:this.pageSize,pageNo:this.pageNo}).then(function(t){200==t.code?e.buyerList=t.ext:e.$message.warning("未查询到采购商列表："+t.msg)})},editCar:function(){var e=this;this.request.editCar(r()({},this.editValue)).then(function(t){200===t.code?e.$message.success("编辑成功！"):e.$message.warning("编辑失败："+t.msg),e.getCarList()})}}},u={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"title"},[a("div",{staticStyle:{display:"flex"}},[e._v("采购商名称: "),a("el-input",{staticStyle:{flex:"1"}})],1),e._v(" "),a("div",[a("el-button",{attrs:{type:"primary"}},[e._v("查询")])],1)]),e._v(" "),a("div",[a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.buyerList,border:""}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"brand",label:"汽车品牌"}}),e._v(" "),a("el-table-column",{attrs:{prop:"carryVolume",label:"体积（m^3）"}}),e._v(" "),a("el-table-column",{attrs:{prop:"carryWeight",label:"载重（T）"}}),e._v(" "),a("el-table-column",{attrs:{prop:"truckName",label:"名称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"truckNo",label:"车牌号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"principal",label:"负责人"}}),e._v(" "),a("el-table-column",{attrs:{prop:"telephone",label:"联系电话"}}),e._v(" "),a("el-table-column",{attrs:{prop:"isUpdateJisu",label:"是否更新即速"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("div",[e._v("\n                       "+e._s("0"===t.row.isUpdateJisu?"否":"是")+" \n                    ")])]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"审核状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("div",[e._v("\n                       "+e._s(e.status[t.row.status])+" \n                    ")])]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"操作",width:"100px"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("div",[a("el-button",{attrs:{type:"text"},on:{click:function(a){return e.edit(t.row)}}},[e._v("编辑")])],1)]}}])})],1)],1),e._v(" "),a("div",{staticClass:"page-box"},[a("div"),e._v(" "),a("my-pagination",{attrs:{"page-size":e.pageSize,"current-page":e.pageNo,total:e.total,"page-sizes":[20,30,40,50]},on:{pagechange:e.handlePageChange}})],1),e._v(" "),a("el-dialog",{attrs:{title:"商家编辑",visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("edit-temp",{attrs:{form:e.editValue}}),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.confirm}},[e._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var c=a("C7Lr")(s,u,!1,function(e){a("M+Ao")},"data-v-0e0a0746",null);t.default=c.exports},"M+Ao":function(e,t){}});