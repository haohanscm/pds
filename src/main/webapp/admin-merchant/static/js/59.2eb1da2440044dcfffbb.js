webpackJsonp([59],{QlWu:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var s=t("Yarq"),o=t.n(s),n=t("AA3o"),a=t.n(n),u=t("xSur"),i=t.n(u),l=t("UzKs"),m=t.n(l),c=t("Y7Ml"),d=t.n(c),p=function(e){function r(){return a()(this,r),m()(this,(r.__proto__||o()(r)).call(this))}return d()(r,e),i()(r,[{key:"login",value:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/auth/login",e)}}]),r}(t("FdFV").a),f={data:function(){return{request:new p,ruleForm:{userName:"采购员",password:""},rules:{userName:[{required:!0,message:"请输入用户名",trigger:"change"}],password:[{required:!0,message:"请输入密码",trigger:"change"}]}}},created:function(){},methods:{submitForm:function(){var e=this;this.request.login(this.ruleForm).then(function(r){200==r.code?(e.$utils.setCookie("pmId",r.ext.pmId,7),e.$utils.setCookie("shopId",r.ext.shopId,7),e.$utils.setCookie("pmName",r.ext.pmName,7),e.$utils.setCookie("isSelfPm",r.ext.isSelfPm||!1,7),e.$router.replace("/dashboard")):e.$message(r.msg)}).catch(function(e){console.log(e)})}}},v={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"login-wrap"},[t("div",{staticClass:"ms-title"},[e._v(e._s(e.$constant.merchantName)+"农产品供应链系统")]),e._v(" "),t("div",{staticClass:"ms-login"},[t("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-width":"0px"}},[t("el-form-item",{attrs:{prop:"userName"}},[t("el-input",{attrs:{placeholder:"userName"},model:{value:e.ruleForm.userName,callback:function(r){e.$set(e.ruleForm,"userName",r)},expression:"ruleForm.userName"}})],1),e._v(" "),t("el-form-item",{attrs:{prop:"password"}},[t("el-input",{attrs:{type:"password",placeholder:"password"},nativeOn:{keyup:function(r){return!r.type.indexOf("key")&&e._k(r.keyCode,"enter",13,r.key,"Enter")?null:e.submitForm("ruleForm")}},model:{value:e.ruleForm.password,callback:function(r){e.$set(e.ruleForm,"password",r)},expression:"ruleForm.password"}})],1),e._v(" "),t("div",{staticClass:"login-btn"},[t("el-button",{attrs:{type:"primary"},on:{click:function(r){return e.submitForm("ruleForm")}}},[e._v("登录")])],1),e._v(" "),t("div",{ref:"box"})],1)],1)])},staticRenderFns:[]};var h=t("C7Lr")(f,v,!1,function(e){t("uOb7")},"data-v-0889c5c3",null);r.default=h.exports},uOb7:function(e,r){}});