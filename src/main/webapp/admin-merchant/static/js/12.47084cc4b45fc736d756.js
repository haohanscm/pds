webpackJsonp([12],{"3cXf":function(t,e,r){t.exports={default:r("NUnD"),__esModule:!0}},"CkR+":function(t,e,r){"use strict";var n=r("Yarq"),i=r.n(n),o=r("AA3o"),s=r.n(o),a=r("xSur"),l=r.n(a),c=r("UzKs"),u=r.n(c),d=r("Y7Ml"),p=r.n(d),h=function(t){function e(){return s()(this,e),u()(this,(e.__proto__||i()(e)).call(this))}return p()(e,t),l()(e,[{key:"fetchBuyerList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/common/buyer/list",t)}},{key:"fetchSortingList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/findPage",t)}},{key:"confirmSortingNumber",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/confirm",t)}},{key:"fetchSortingProcess",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/process/all",t)}},{key:"printTicket",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/common/printer/textPrint",t)}},{key:"printFeieTicket",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/xiaodian/api/feieyun/printMsg",t)}},{key:"fetchPrinterList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/common/printer/yiPrinterList",t)}},{key:"fetchFeiPrinterList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/common/printer/fetchPrinterList",t)}},{key:"shortcutFinish",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/shortcut/finish",t)}},{key:"fastSortOut",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/fastSortOut",t)}},{key:"reg",value:function(t){return this.get("http://"+(t||"pds.haohanshop.com")+"/reg?sn=17332f01ffd017bf4360f7b87813b770a978b628c3e079d9c7e20e96790301193ae0de5b982c792741b58bb1bad8451f&rnd=1545394807801&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}},{key:"open",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"pds.haohanshop.com";return this.get("http://"+(t||"pds.haohanshop.com")+"/open?cn=1&s=2400%2CN%2C8%2C1&dt=utf-8&rto=5&cmi=WEDEM&oc=1&rnd=1545394807836&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}},{key:"read",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"pds.haohanshop.com";return this.get("http://"+(t||"pds.haohanshop.com")+"/read?cn=1&cmi=WEDEM&rid=100&rnd=1545394382592&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}},{key:"close",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"pds.haohanshop.com";return this.get("http://"+(t||"pds.haohanshop.com")+"/close?cn=1&cmi=WEDEM&rnd=1545395286010&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}}]),e}(r("FdFV").a);e.a=h},NUnD:function(t,e,r){var n=r("/KQr"),i=n.JSON||(n.JSON={stringify:JSON.stringify});t.exports=function(t){return i.stringify.apply(i,arguments)}},TbER:function(t,e){},Uwb7:function(t,e,r){"use strict";var n=r("3cXf"),i=r.n(n),o=new(r("CkR+").a),s={data:function(){return{printerSn:"",feieSn:"",feieType:"",printerType:"3",printerList:[],feieList:[],buySeqOptions:this.$dic.buySeqOptions,printType:"3",printList:[{value:"0",label:"70 * 40"},{value:"1",label:"60 * 40"},{value:"2",label:"50 * 40"},{value:"3",label:"50 * 30"}]}},mounted:function(){var t=this;this.fetchPrinterList().then(function(e){t.printerList=e}),this.fetchFeiPrinterList().then(function(e){console.log(e),t.feieList=e.list}),this.initPrinterInfo()},methods:{fetchPrinterList:function(){var t={shopId:$constant.shopId};return o.fetchPrinterList(t).then(function(t){if(200==t.code)return t.ext;console.log("获取打印机列表错误: ",t.msg)})},fetchFeiPrinterList:function(){var t={pageSize:100,shopId:$constant.shopId};return o.fetchFeiPrinterList(t).then(function(t){if(200==t.code)return t.ext;console.log("获取打印机列表错误: ",t.msg)})},printTicket:function(t){var e=t.tradeId,r=t.buyerName,n=t.goodsName,i=t.sortOutNum,s=t.goodsModel,a=t.unit,l=t.contact,c=t.contactPhone,u=t.deliveryAddress,d=t.deliveryTime,p=t.buySeq,h=(i*t.buyPrice).toFixed(2);if(this.feieSn){var f=void 0;f="2"==this.feieType?"<C>-----------------------------------------------</C><B>交易单号: "+e+"</B><BR><BR><B>采购商家: "+r+"</B><BR><BR><B>商品名称: "+n+"</B><BR><BR><B>实际数量: "+i+(a?" /"+a:a)+"</B><BR><BR>商品规格: "+s+"<BR>单位: "+a+"<BR><C>-----------------------------------------------</C>联系人: "+l+"<BR>电话: "+c+"<BR>地址: "+u+"<BR>配送日期: "+d+"<BR>采购批次: "+this.buySeqOptions[+p].label+"<BR>":"<C>--------------------------------</C><L>交易单号:</L><BOLD><L>"+e+"</L></BOLD><BR><BR><L>采购商家:</L><BOLD><L>"+r+"</L></BOLD><BR><BR><L>商品名称:</L><BOLD><L>"+n+"</L></BOLD><BR><BR><L>实际数量:</L><B>"+i+(a?" /"+a:a)+"</B><BR><BR>商品规格: "+s+"<BR>单位: "+a+"<BR><C>--------------------------------</C>联系人: "+l+"<BR>电话: "+c+"<BR>地址: "+u+"<BR>配送日期: "+d+"<BR>采购批次: "+this.buySeqOptions[+p].label+"<BR>",o.printFeieTicket({sn:this.feieSn,content:f})}if(this.printerSn){var v=void 0,m=this.printerSn,b=this.printerType;v="0"==b?"<FH2><FS><FW><center>采购商名称</center></FW></FS></FH2><FH2><FS>"+n+"("+s+")x"+i+a+"</FS></FH2>\r<LR>单号:"+e+",日期:"+d+"</LR>":"1"==b?"<PW>058</PW><table><tr><td><FB><FS>"+n+"("+s+")x"+i+a+"</FS></FB></td></tr><tr><td>日期："+d+"</td><td> </td><td>价格：<FB>"+h+"</FB></td></tr><tr><td>商家："+r+"</td></tr><tr><td>地址："+u+"</td></tr><tr><td><BR2>"+e+"</BR2></td></tr></table>":"2"==b?"<table><tr><td><FB><FS>"+n+"</FS></FB></td></tr><tr><td>数量："+i+a+"</td><td>规格："+s+"</td></tr><tr><td>日期："+d+"</td></tr><tr><td>商家："+r+"</td></tr><tr><td><BR2>"+e+"</BR2></td><td></td></tr></table>":"<FH2><FS><FW><center>"+r+"</center></FW></FS></FH2>\r<FH2><FS>"+n+"</FS></FH2>\r<FH><FS><right>("+s+") x "+i+a+"</right></FS></FH>\r<LR>单号:"+e+",日期:"+d+"</LR>",o.printTicket({orderId:e,machineCode:m,content:v})}},initPrinterInfo:function(){var t=this.$utils.getStorage("printer_info"),e=JSON.parse(t);t&&(this.printerSn=e.printerSn,this.printerType=e.printerType||"2");var r=this.$utils.getStorage("feie_printer_info"),n=JSON.parse(r);n&&(this.feieSn=n.printerSn,this.feieType=n.feieType||"2")},savePrinterInfo:function(t){var e=this.printType;this.printerType=e;var r=i()({printerSn:t,printerType:e});this.$utils.setStorage("printer_info",r)},saveFeieInfo:function(t){var e=this.feieList.map(function(t){return t.printerSn}).indexOf(t),r=this.feieList[e].printerType;this.feieType=r;var n=i()({printerSn:t,feieType:this.feieType});this.$utils.setStorage("feie_printer_info",n)}}};e.a=s},qAsH:function(t,e,r){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=r("CkR+"),i={mixins:[r("Uwb7").a],data:function(){return{request:new n.a,sortingSearchForm:{deliveryTime:this.today},sortingList:[],buySeqOptions:this.$dic.buySeqOptions,buyerOptions:[],loading:!1}},computed:{today:function(){var t=new Date;return this.$utils.formatDate(t,"yyyy-MM-dd")}},mounted:function(){this.fetchSortingList()},methods:{fetchSortingList:function(){var t=this;this.loading=!0;var e=this.sortingSearchForm;return e.pageSize=1e3,this.request.fetchSortingList(e).then(function(e){983==e.code&&(t.sortingList=[]),200==e.code&&(t.sortingList=e.list),t.loading=!1})},confirmSortingNumber:function(t){var e=this;return this.loading=!0,this.request.confirmSortingNumber(t).then(function(t){e.loading=!1})},thStyle:function(){return{background:"#494949",color:"#fff"}},handleEdit:function(t,e){this.$set(e,"isEdit",!0)},handleSave:function(t,e){var r=this;this.$set(e,"isEdit",!1);var n={};n.tradeId=e.tradeId,n.sortOutNum=e.sortOutNum,this.confirmSortingNumber(n).then(function(t){r.$message.success("修改分拣数量成功!"),r.fetchSortingList()})},resetForm:function(){this.sortingSearchForm={}},handlerPrint:function(t,e){e.sortOutNum?this.printTicket(e):this.$message.error("请填写实际分拣数量")},shortcutFinish:function(){var t=this;this.$confirm("您确认该批次的商品已装车并且已送达?").then(function(){t.request.shortcutFinish(t.sortingSearchForm).then(function(e){200==e.code?t.$message.success("分拣成功！"):t.$message.warning(e.msg)})})}}},o={render:function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"page-wrap"},[r("div",{staticClass:"container search"},[r("el-form",{attrs:{model:t.sortingSearchForm,inline:!0,"label-width":"100px"}},[r("el-form-item",{attrs:{label:"配送日期:"}},[r("el-date-picker",{attrs:{clearable:"",type:"date",placeholder:"选择日期","value-format":"yyyy-MM-dd"},model:{value:t.sortingSearchForm.deliveryTime,callback:function(e){t.$set(t.sortingSearchForm,"deliveryTime",e)},expression:"sortingSearchForm.deliveryTime"}})],1),t._v(" "),r("el-form-item",{attrs:{label:"配送批次:"}},[r("el-select",{attrs:{clearable:"",placeholder:"请选择"},model:{value:t.sortingSearchForm.buySeq,callback:function(e){t.$set(t.sortingSearchForm,"buySeq",e)},expression:"sortingSearchForm.buySeq"}},t._l(t.buySeqOptions,function(t){return r("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})}),1)],1),t._v(" "),r("el-form-item",[r("buyer-check",{model:{value:t.sortingSearchForm.buyerId,callback:function(e){t.$set(t.sortingSearchForm,"buyerId",e)},expression:"sortingSearchForm.buyerId"}})],1),t._v(" "),r("el-form-item",{attrs:{label:"商品名称"}},[r("el-input",{attrs:{placeholder:""},model:{value:t.sortingSearchForm.goodsName,callback:function(e){t.$set(t.sortingSearchForm,"goodsName",e)},expression:"sortingSearchForm.goodsName"}})],1),t._v(" "),r("el-form-item",{attrs:{label:" "}},[r("el-button",{attrs:{type:"primary"},on:{click:t.fetchSortingList}},[t._v("搜索")]),t._v(" "),t.$constant.isPds?t._e():r("el-button",{on:{click:t.shortcutFinish}},[t._v("确认装车并送达")])],1)],1)],1),t._v(" "),r("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"container body"},[r("div",{staticClass:"check-block"},[r("div",[r("span",[t._v("易联云打印机:")]),t._v(" "),r("el-select",{attrs:{placeholder:"选择打印机",clearable:""},on:{change:t.savePrinterInfo},model:{value:t.printerSn,callback:function(e){t.printerSn=e},expression:"printerSn"}},t._l(t.printerList,function(t){return r("el-option",{key:t.id,attrs:{label:t.name,value:t.machineCode}})}),1)],1),t._v(" "),r("div",[r("span",[t._v("打印规格:")]),t._v(" "),r("el-select",{attrs:{clearable:"",placeholder:"选择易联云打印机打印规格"},on:{change:t.savePrinterInfo},model:{value:t.printType,callback:function(e){t.printType=e},expression:"printType"}},t._l(t.printList,function(t){return r("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})}),1)],1),t._v(" "),r("div",[r("span",[t._v("飞鹅打印机:")]),t._v(" "),r("el-select",{attrs:{clearable:"",placeholder:"选择飞鹅打印机"},on:{change:t.saveFeieInfo},model:{value:t.feieSn,callback:function(e){t.feieSn=e},expression:"feieSn"}},t._l(t.feieList,function(t){return r("el-option",{key:t.id,attrs:{label:t.printerName,value:t.printerSn}})}),1)],1)]),t._v(" "),r("el-table",{attrs:{data:t.sortingList,border:"","header-cell-style":t.thStyle}},[r("el-table-column",{attrs:{type:"selection"}}),t._v(" "),r("el-table-column",{attrs:{label:"单位名称",prop:"buyerName"}}),t._v(" "),r("el-table-column",{attrs:{label:"商品名称"},scopedSlots:t._u([{key:"default",fn:function(e){return[r("el-popover",{attrs:{trigger:"hover",placement:"top",width:"120px"}},[r("p",[t._v(t._s(e.row.goodsName))]),t._v(" "),r("div",{attrs:{slot:"reference"},slot:"reference"},[r("p",{staticClass:"text-ellipsis"},[t._v(t._s(e.row.goodsName))])])])]}}])}),t._v(" "),r("el-table-column",{attrs:{label:"规格",prop:"goodsModel",width:"100"}}),t._v(" "),r("el-table-column",{attrs:{label:"单位",prop:"unit",width:"100"}}),t._v(" "),r("el-table-column",{attrs:{label:"订购数量",prop:"buyNum",width:"100"}}),t._v(" "),r("el-table-column",{attrs:{label:"备注"},scopedSlots:t._u([{key:"default",fn:function(e){return[r("el-popover",{attrs:{trigger:"hover",placement:"top",width:"120px"}},[r("p",[t._v(t._s(e.row.buyNode))]),t._v(" "),r("div",{attrs:{slot:"reference"},slot:"reference"},[r("p",{staticClass:"text-ellipsis"},[t._v(t._s(e.row.buyNode))])])])]}}])}),t._v(" "),r("el-table-column",{attrs:{label:"实际数量",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[r("el-input",{attrs:{placeholder:"0",disabled:!e.row.isEdit},model:{value:e.row.sortOutNum,callback:function(r){t.$set(e.row,"sortOutNum",r)},expression:"scope.row.sortOutNum"}})]}}])}),t._v(" "),r("el-table-column",{attrs:{label:"分拣状态",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[2==e.row.opStatus?r("p",[t._v("待分拣")]):t._e(),t._v(" "),3==e.row.opStatus?r("p",[t._v("已分拣")]):t._e()]}}])}),t._v(" "),r("el-table-column",{attrs:{label:"管理操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.isEdit?t._e():r("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(r){return t.handleEdit(e.$index,e.row)}}},[t._v("修改\n                    ")]),t._v(" "),e.row.isEdit?r("el-button",{attrs:{type:"success",size:"mini"},on:{click:function(r){return t.handleSave(e.$index,e.row)}}},[t._v("保存\n                    ")]):t._e(),t._v(" "),r("el-button",{attrs:{type:"mini"},on:{click:function(r){return t.handlerPrint(e.$index,e.row)}}},[t._v("打印\n                    ")])]}}])})],1)],1)])},staticRenderFns:[]};var s=r("C7Lr")(i,o,!1,function(t){r("TbER")},"data-v-30cfe2f4",null);e.default=s.exports}});