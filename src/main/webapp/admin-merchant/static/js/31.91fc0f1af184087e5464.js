webpackJsonp([31],{YtnH:function(t,e){},aSTC:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var l=a("eofT"),r={data:function(){return{request:new l.a,buyerList:[]}},watch:{},created:function(){this.fetchBuyerList()},methods:{fetchBuyerList:function(){var t=this;this.request.getBuyerList().then(function(e){200==e.code?t.buyerList=e.ext:t.$message.warning("未查询到采购商列表："+e.msg)})}}},n={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",{staticClass:"title"},[a("div",{staticStyle:{display:"flex"}},[t._v("采购商名称: "),a("el-input",{staticStyle:{flex:"1"}})],1),t._v(" "),a("div",[a("el-button",{attrs:{type:"primary"}},[t._v("查询")])],1)]),t._v(" "),a("div",[a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.buyerList,border:""}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),t._v(" "),a("el-table-column",{attrs:{prop:"buyerName",label:"采购商名称"}}),t._v(" "),a("el-table-column",{attrs:{prop:"merchantName",label:"所属商家"}}),t._v(" "),a("el-table-column",{attrs:{prop:"contact",label:"联系人",width:"100px"}}),t._v(" "),a("el-table-column",{attrs:{prop:"telephone",label:"联系电话",width:"100px"}}),t._v(" "),a("el-table-column",{attrs:{prop:"address",label:"地址"}})],1)],1)])},staticRenderFns:[]};var s=a("C7Lr")(r,n,!1,function(t){a("YtnH")},"data-v-c468d898",null);e.default=s.exports}});