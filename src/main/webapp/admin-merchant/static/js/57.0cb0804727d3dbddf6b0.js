webpackJsonp([57],{"873Z":function(e,s){},PLlc:function(e,s,t){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var i={render:function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",[t("div",{staticClass:"title"},[t("div",[t("el-input",{attrs:{placeholder:"请输入分拣机IP"},model:{value:e.ip,callback:function(s){e.ip=s},expression:"ip"}}),t("el-button",{on:{click:e.setIp}},[e._v("设置分拣机ip")])],1),e._v(" "),t("div",[t("el-input",{attrs:{placeholder:"请输入收银台默认地址Id"},model:{value:e.addressId,callback:function(s){e.addressId=s},expression:"addressId"}}),t("el-button",{on:{click:e.setAddressId}},[e._v("设置默认地址ID")])],1)])])},staticRenderFns:[]};var d=t("C7Lr")({data:function(){return{ip:this.$utils.getCookie("ip"),addressId:""}},components:{},watch:{},created:function(){},methods:{setIp:function(){try{this.$utils.setCookie("ip",this.ip,1e4),this.$message.success("保存成功！")}catch(e){this.$message.warning(e)}},setAddressId:function(){try{this.$utils.setCookie("addressId",this.addressId,1e4),this.$message.success("保存成功！")}catch(e){this.$message.warning(e)}}}},i,!1,function(e){t("873Z")},"data-v-11d60924",null);s.default=d.exports}});