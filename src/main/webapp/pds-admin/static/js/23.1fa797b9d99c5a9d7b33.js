webpackJsonp([23],{"LJ+L":function(t,e){},j2mu:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var l=a("3cXf"),s=a.n(l),r=a("64PB"),o={data:function(){return{}},props:{form:{type:Object,default:{}}},methods:{onSubmit:function(){console.log("submit!")}}},n={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-container",[a("el-main",[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("商家名称")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"50",minlength:"1",placeholder:"必填"},model:{value:t.form.merchantName,callback:function(e){t.$set(t.form,"merchantName",e)},expression:"form.merchantName"}})],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("商家地址")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"255",minlength:"1",placeholder:"必填"},model:{value:t.form.address,callback:function(e){t.$set(t.form,"address",e)},expression:"form.address"}})],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("联系人")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"50",minlength:"1",placeholder:"必填"},model:{value:t.form.contact,callback:function(e){t.$set(t.form,"contact",e)},expression:"form.contact"}})],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("联系电话")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"50",minlength:"1",placeholder:"必填"},model:{value:t.form.telephone,callback:function(e){t.$set(t.form,"telephone",e)},expression:"form.telephone"}})],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("启用状态")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-select",{attrs:{placeholder:"请选择启用状态"},model:{value:t.form.status,callback:function(e){t.$set(t.form,"status",e)},expression:"form.status"}},[a("el-option",{attrs:{label:"待审核",value:0}}),t._v(" "),a("el-option",{attrs:{label:"停用",value:-1}}),t._v(" "),a("el-option",{attrs:{label:"启用",value:2}})],1)],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("所属行业")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"50"},model:{value:t.form.industry,callback:function(e){t.$set(t.form,"industry",e)},expression:"form.industry"}})],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("规模")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"50"},model:{value:t.form.scale,callback:function(e){t.$set(t.form,"scale",e)},expression:"form.scale"}})],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("业务介绍")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"1000",type:"textarea",autosize:""},model:{value:t.form.bizDesc,callback:function(e){t.$set(t.form,"bizDesc",e)},expression:"form.bizDesc"}})],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("采购配送类型")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-select",{model:{value:t.form.pdsType,callback:function(e){t.$set(t.form,"pdsType",e)},expression:"form.pdsType"}},[a("el-option",{attrs:{label:"普通商家",value:"0"}})],1)],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("产品线类型")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-select",{model:{value:t.form.productLine,callback:function(e){t.$set(t.form,"productLine",e)},expression:"form.productLine"}},[a("el-option",{attrs:{label:"采购配送",value:"1"}})],1)],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("订单自动分配")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-select",{model:{value:t.form.isAutomaticOrder,callback:function(e){t.$set(t.form,"isAutomaticOrder",e)},expression:"form.isAutomaticOrder"}},[a("el-option",{attrs:{label:"否",value:"0"}}),t._v(" "),a("el-option",{attrs:{label:"是",value:"1"}})],1)],1)],1),t._v(" "),a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:4}},[t._v("备注")]),t._v(" "),a("el-col",{attrs:{span:20}},[a("el-input",{attrs:{maxlength:"500",type:"textarea",autosize:""},model:{value:t.form.remarks,callback:function(e){t.$set(t.form,"remarks",e)},expression:"form.remarks"}})],1)],1)],1)],1)},staticRenderFns:[]};var i={data:function(){return{pageSize:20,pageNo:1,total:0,layout:"total, sizes, prev, pager, next, jumper",request:new r.a,editValue:{merchantName:"",address:"",contact:"",telephone:"",status:0,industry:"",scale:"",bizDesc:"",pdsType:"",productLine:"",isAutomaticOrder:"",remarks:""},dialogFormVisible:!1,buyerList:[],status:{0:"待审核",2:"启用","-1":"停用"},merchantName:"",contact:"",selectStatus:""}},components:{editTemp:a("C7Lr")(o,n,!1,function(t){a("LJ+L")},"data-v-75dcb3d6",null).exports},watch:{},created:function(){this.getMerchantList()},methods:{handlePageChange:function(t){this.pageSize=t.pageSize,this.pageNo=t.pageNo,this.getMerchantList()},edit:function(t){this.editValue=t,this.dialogFormVisible=!0},newList:function(){this.editValue={merchantName:"",address:"",contact:"",telephone:"",status:0,industry:"",scale:"",bizDesc:"",pdsType:"",productLine:"",isAutomaticOrder:"",remarks:""},this.dialogFormVisible=!0},confirm:function(){if(this.editValue.status||(this.editValue.status=0),this.editValue.pdsType||(this.editValue.pdsType="0"),this.editValue.productLine||(this.editValue.productLine="1"),this.editValue.isAutomaticOrder||(this.editValue.isAutomaticOrder="0"),this.editValue.merchantName&&this.editValue.address&&this.editValue.contact&&this.editValue.telephone){var t=JSON.parse(s()(this.editValue));t.area="",this.editValue.area&&this.editValue.area.id&&(t.area=this.editValue.area.id),t.user="",this.editValue.user&&this.editValue.user.id&&(t.user=this.editValue.user.id),this.editMerchant(t)}else this.$message.error("商家必填信息未填写")},getMerchantList:function(){var t=this,e={pageSize:this.pageSize,pageNo:this.pageNo,status:this.selectStatus,contact:this.contact,merchantName:this.merchantName};this.request.getMerchantList(e).then(function(e){200==e.code?(t.buyerList=e.ext.list,t.total=e.ext.count):t.$message.warning("未查询到采购商列表："+e.msg)})},editMerchant:function(t){var e=this;this.request.editMerchant(t).then(function(t){200===t.code?(e.dialogFormVisible=!1,e.$message.success("编辑成功！"),e.getMerchantList()):e.$message.warning("编辑失败："+t.msg)})},deleteMerchant:function(t){var e=this;this.$confirm("您确认要删除该商家？").then(function(a){e.request.deleteMerchant({id:t}).then(function(t){200===t.code?(e.$message.success("删除成功！"),e.getMerchantList()):e.$message.warning("删除失败："+t.msg)})})}}},c={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",{staticClass:"title"},[a("el-row",{attrs:{gutter:10}},[a("el-col",{attrs:{span:2.5}},[t._v("商家名称:")]),t._v(" "),a("el-col",{attrs:{span:4}},[a("el-input",{attrs:{clearable:"",maxlength:"20",placeholder:"模糊查询"},model:{value:t.merchantName,callback:function(e){t.merchantName=e},expression:"merchantName"}})],1),t._v(" "),a("el-col",{attrs:{span:2}},[t._v("联系人:")]),t._v(" "),a("el-col",{attrs:{span:4}},[a("el-input",{attrs:{clearable:"",maxlength:"20",placeholder:"模糊查询"},model:{value:t.contact,callback:function(e){t.contact=e},expression:"contact"}})],1),t._v(" "),a("el-col",{attrs:{span:1.5}},[t._v("状态: ")]),t._v(" "),a("el-col",{attrs:{span:4}},[a("el-select",{attrs:{clearable:"",placeholder:"请选择"},model:{value:t.selectStatus,callback:function(e){t.selectStatus=e},expression:"selectStatus"}},[a("el-option",{attrs:{label:"待审核",value:"0"}}),t._v(" "),a("el-option",{attrs:{label:"停用",value:"-1"}}),t._v(" "),a("el-option",{attrs:{label:"启用",value:"2"}})],1)],1),t._v(" "),a("el-col",{attrs:{span:2}},[a("el-button",{attrs:{type:"primary"},on:{click:t.getMerchantList}},[t._v("查询")])],1),t._v(" "),a("el-col",{attrs:{span:2}},[a("el-button",{attrs:{type:"primary"},on:{click:t.newList}},[t._v("新增")])],1)],1)],1),t._v(" "),a("div",[a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.buyerList,border:""}},[a("el-table-column",{attrs:{type:"index",fixed:"",width:"50"}}),t._v(" "),a("el-table-column",{attrs:{prop:"merchantName",label:"商家名称",fixed:""}}),t._v(" "),a("el-table-column",{attrs:{prop:"contact",label:"联系人",width:"100px"}}),t._v(" "),a("el-table-column",{attrs:{prop:"telephone",label:"联系电话",width:"100px"}}),t._v(" "),a("el-table-column",{attrs:{prop:"address",label:"地址"}}),t._v(" "),a("el-table-column",{attrs:{prop:"status",label:"启用状态"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("div",[t._v("\n                       "+t._s(t.status[e.row.status])+"\n                    ")])]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"bizDesc",label:"业务介绍"}}),t._v(" "),a("el-table-column",{attrs:{prop:"industry",label:"行业",width:"100px"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",width:"100px",fixed:"right"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("div",[a("el-button",{attrs:{type:"text"},on:{click:function(a){return t.edit(e.row)}}},[t._v("编辑")])],1)]}}])})],1)],1),t._v(" "),a("div",{staticClass:"page-box"},[a("div"),t._v(" "),a("my-pagination",{attrs:{"page-size":t.pageSize,"current-page":t.pageNo,total:t.total,layout:t.layout,"page-sizes":[20,30,40,50]},on:{pagechange:t.handlePageChange}})],1),t._v(" "),a("el-dialog",{attrs:{title:"商家编辑",visible:t.dialogFormVisible,width:"70%"},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[a("edit-temp",{attrs:{form:t.editValue}}),t._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:t.confirm}},[t._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var u=a("C7Lr")(i,c,!1,function(t){a("nREP")},"data-v-4850e390",null);e.default=u.exports},nREP:function(t,e){}});