webpackJsonp([10],{"CkR+":function(t,e,i){"use strict";var r=i("Yarq"),n=i.n(r),s=i("AA3o"),o=i.n(s),a=i("xSur"),l=i.n(a),c=i("UzKs"),u=i.n(c),d=i("Y7Ml"),h=i.n(d),v=function(t){function e(){return o()(this,e),u()(this,(e.__proto__||n()(e)).call(this))}return h()(e,t),l()(e,[{key:"fetchBuyerList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/common/buyer/list",t)}},{key:"fetchSortingList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/findPage",t)}},{key:"confirmSortingNumber",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/confirm",t)}},{key:"fetchSortingProcess",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/process/all",t)}},{key:"printTicket",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/common/printer/textPrint",t)}},{key:"printFeieTicket",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/xiaodian/api/feieyun/printMsg",t)}},{key:"fetchPrinterList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/common/printer/yiPrinterList",t)}},{key:"fetchFeiPrinterList",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/common/printer/fetchPrinterList",t)}},{key:"shortcutFinish",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/shortcut/finish",t)}},{key:"fastSortOut",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return this.post("/f/pds/api/admin/sortout/fastSortOut",t)}},{key:"reg",value:function(t){return this.get("http://"+(t||"pds.haohanshop.com")+"/reg?sn=17332f01ffd017bf4360f7b87813b770a978b628c3e079d9c7e20e96790301193ae0de5b982c792741b58bb1bad8451f&rnd=1545394807801&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}},{key:"open",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"pds.haohanshop.com";return this.get("http://"+(t||"pds.haohanshop.com")+"/open?cn=1&s=2400%2CN%2C8%2C1&dt=utf-8&rto=5&cmi=WEDEM&oc=1&rnd=1545394807836&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}},{key:"read",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"pds.haohanshop.com";return this.get("http://"+(t||"pds.haohanshop.com")+"/read?cn=1&cmi=WEDEM&rid=100&rnd=1545394382592&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}},{key:"close",value:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"pds.haohanshop.com";return this.get("http://"+(t||"pds.haohanshop.com")+"/close?cn=1&cmi=WEDEM&rnd=1545395286010&rel=d21wLml5YW5ob25nLmNvbQ%3D%3D")}}]),e}(i("FdFV").a);e.a=v},Uwb7:function(t,e,i){"use strict";var r=i("3cXf"),n=i.n(r),s=new(i("CkR+").a),o={data:function(){return{printerSn:"",feieSn:"",feieType:"",printerType:"3",printerList:[],feieList:[],buySeqOptions:this.$dic.buySeqOptions,printType:"3",printList:[{value:"0",label:"70 * 40"},{value:"1",label:"60 * 40"},{value:"2",label:"50 * 40"},{value:"3",label:"50 * 30"}]}},mounted:function(){var t=this;this.fetchPrinterList().then(function(e){t.printerList=e}),this.fetchFeiPrinterList().then(function(e){console.log(e),t.feieList=e.list}),this.initPrinterInfo()},methods:{fetchPrinterList:function(){var t={shopId:$constant.shopId};return s.fetchPrinterList(t).then(function(t){if(200==t.code)return t.ext;console.log("获取打印机列表错误: ",t.msg)})},fetchFeiPrinterList:function(){var t={pageSize:100,shopId:$constant.shopId};return s.fetchFeiPrinterList(t).then(function(t){if(200==t.code)return t.ext;console.log("获取打印机列表错误: ",t.msg)})},printTicket:function(t){var e=t.tradeId,i=t.buyerName,r=t.goodsName,n=t.sortOutNum,o=t.goodsModel,a=t.unit,l=t.contact,c=t.contactPhone,u=t.deliveryAddress,d=t.deliveryTime,h=t.buySeq,v=(n*t.buyPrice).toFixed(2);if(this.feieSn){var f=void 0;f="2"==this.feieType?"<C>-----------------------------------------------</C><B>交易单号: "+e+"</B><BR><BR><B>采购商家: "+i+"</B><BR><BR><B>商品名称: "+r+"</B><BR><BR><B>实际数量: "+n+(a?" /"+a:a)+"</B><BR><BR>商品规格: "+o+"<BR>单位: "+a+"<BR><C>-----------------------------------------------</C>联系人: "+l+"<BR>电话: "+c+"<BR>地址: "+u+"<BR>配送日期: "+d+"<BR>采购批次: "+this.buySeqOptions[+h].label+"<BR>":"<C>--------------------------------</C><L>交易单号:</L><BOLD><L>"+e+"</L></BOLD><BR><BR><L>采购商家:</L><BOLD><L>"+i+"</L></BOLD><BR><BR><L>商品名称:</L><BOLD><L>"+r+"</L></BOLD><BR><BR><L>实际数量:</L><B>"+n+(a?" /"+a:a)+"</B><BR><BR>商品规格: "+o+"<BR>单位: "+a+"<BR><C>--------------------------------</C>联系人: "+l+"<BR>电话: "+c+"<BR>地址: "+u+"<BR>配送日期: "+d+"<BR>采购批次: "+this.buySeqOptions[+h].label+"<BR>",s.printFeieTicket({sn:this.feieSn,content:f})}if(this.printerSn){var p=void 0,m=this.printerSn,g=this.printerType;p="0"==g?"<FH2><FS><FW><center>采购商名称</center></FW></FS></FH2><FH2><FS>"+r+"("+o+")x"+n+a+"</FS></FH2>\r<LR>单号:"+e+",日期:"+d+"</LR>":"1"==g?"<PW>058</PW><table><tr><td><FB><FS>"+r+"("+o+")x"+n+a+"</FS></FB></td></tr><tr><td>日期："+d+"</td><td> </td><td>价格：<FB>"+v+"</FB></td></tr><tr><td>商家："+i+"</td></tr><tr><td>地址："+u+"</td></tr><tr><td><BR2>"+e+"</BR2></td></tr></table>":"2"==g?"<table><tr><td><FB><FS>"+r+"</FS></FB></td></tr><tr><td>数量："+n+a+"</td><td>规格："+o+"</td></tr><tr><td>日期："+d+"</td></tr><tr><td>商家："+i+"</td></tr><tr><td><BR2>"+e+"</BR2></td><td></td></tr></table>":"<FH2><FS><FW><center>"+i+"</center></FW></FS></FH2>\r<FH2><FS>"+r+"</FS></FH2>\r<FH><FS><right>("+o+") x "+n+a+"</right></FS></FH>\r<LR>单号:"+e+",日期:"+d+"</LR>",s.printTicket({orderId:e,machineCode:m,content:p})}},initPrinterInfo:function(){var t=this.$utils.getStorage("printer_info"),e=JSON.parse(t);t&&(this.printerSn=e.printerSn,this.printerType=e.printerType||"2");var i=this.$utils.getStorage("feie_printer_info"),r=JSON.parse(i);r&&(this.feieSn=r.printerSn,this.feieType=r.feieType||"2")},savePrinterInfo:function(t){var e=this.printType;this.printerType=e;var i=n()({printerSn:t,printerType:e});this.$utils.setStorage("printer_info",i)},saveFeieInfo:function(t){var e=this.feieList.map(function(t){return t.printerSn}).indexOf(t),i=this.feieList[e].printerType;this.feieType=i;var r=n()({printerSn:t,feieType:this.feieType});this.$utils.setStorage("feie_printer_info",r)}}};e.a=o},deGe:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=i("CkR+"),n={props:{value:{type:[String,Number],default:0},disabled:Boolean},data:function(){return{number:""}},created:function(){this.number=this.value+""},mounted:function(){},computed:{result:function(){return(+this.number).toFixed(2)},float:function(){return this.number=this.number+"",this.number.indexOf(".")>0}},methods:{tapBoard:function(t){if("C"==t)return this.number="",void this.changeResult();if("back"==t)return this.number=this.number.slice(0,-1),void this.changeResult();if(this.float&&this.number.split(".")[1].length>=2)return;"."==t&&this.float||(this.number+=t,this.changeResult())},clickPlus:function(t){var e=+t+ +this.number;this.number=e.toString(),this.changeResult()},changeResult:function(){this.$emit("change",this.number)},confirm:function(){this.disabled||this.$emit("confirm")},reset:function(){this.number=""}}},s={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"virtual-board"},[i("div",[i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("7")}}},[t._v("7")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("8")}}},[t._v("8")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("9")}}},[t._v("9")]),t._v(" "),i("div",{staticClass:"width2x virtual-board-item",on:{click:function(e){return t.clickPlus("20")}}},[t._v("20")])]),t._v(" "),i("div",[i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("4")}}},[t._v("4")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("5")}}},[t._v("5")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("6")}}},[t._v("6")]),t._v(" "),i("div",{staticClass:"width2x virtual-board-item",on:{click:function(e){return t.clickPlus("50")}}},[t._v("50")])]),t._v(" "),i("div",[i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("1")}}},[t._v("1")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("2")}}},[t._v("2")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("3")}}},[t._v("3")]),t._v(" "),i("div",{staticClass:"width2x virtual-board-item",on:{click:function(e){return t.clickPlus("100")}}},[t._v("100")])]),t._v(" "),i("div",[i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("C")}}},[t._v("C")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard("0")}}},[t._v("0")]),t._v(" "),i("div",{staticClass:"virtual-board-item",on:{click:function(e){return t.tapBoard(".")}}},[t._v(".")]),t._v(" "),i("div",{staticClass:"width2x virtual-board-item",on:{click:function(e){return t.tapBoard("back")}}},[i("i",{staticClass:"el-icon-hh-backdelete"})])])])},staticRenderFns:[]};var o=i("C7Lr")(n,s,!1,function(t){i("xAIq")},"data-v-380ea92c",null).exports,a={mixins:[i("Uwb7").a],data:function(){return{request:new r.a,sortingSearchForm:{},sortingList:[],waitingList:[],currentSortingIndex:0,editSortingIndex:"",buySeqOptions:this.$dic.buySeqOptions,buyerOptions:[],loading:!1,sortOutNum:"",number:"",isEdit:!1,currentSorting:{},isAuto:!1,timer:{},ip:this.$utils.getCookie("ip"),sortType:"nor",sortProp:""}},computed:{filterSortingList:function(){return this.sortingList.filter(function(t){return 2==t.opStatus})},filterSortedList:function(){return this.sortingList.filter(function(t){return 3==t.opStatus})},sortingNumber:function(){return this.filterSortingList.length},sortedNumber:function(){return this.filterSortedList.length},getSortingItem:function(){return this.waitingList[0]},getSortedItem:function(){return this.editSortedItem}},components:{virtualBoard:o},mounted:function(){this.fetchSortingList(),this.request.reg(this.ip)},methods:{fetchSortingList:function(){var t=this;this.loading=!0;var e=this.sortingSearchForm;return e.pageSize=1e3,this.request.fetchSortingList(e).then(function(e){983==e.code&&(t.sortingList=[],t.loading=!1),200==e.code&&(t.sortingList=e.list),t.loading=!1,t.waitingList=t.filterSortingList,"up"===t.sortType?t.waitingList.sort(function(e,i){return e[t.sortProp]>i[t.sortProp]?1:-1}):"down"===t.sortType&&t.waitingList.sort(function(e,i){return i[t.sortProp]>e[t.sortProp]?1:-1}),t.currentSorting=t.getSortingItem||{}})},confirmSortingNumber:function(t){return this.request.confirmSortingNumber(t)},fastSortOut:function(){var t=this;if(void 0!==this.sortingSearchForm.buySeq&&void 0!==this.sortingSearchForm.deliveryTime){var e=this.buySeqOptions[this.sortingSearchForm.buySeq].label,i=this.sortingSearchForm.deliveryTime;this.$confirm("您确认要对"+i+e+"的商品进行一键分拣吗？").then(function(r){t.request.fastSortOut({deliveryDate:t.sortingSearchForm.deliveryTime,buySeq:t.sortingSearchForm.buySeq}).then(function(r){200==r.code?t.$message.success(i+e+"分拣成功！"):t.$message.warning(r.msg)})})}else this.$message.warning("请选择时间和批次！")},currentRowClass:function(t){t.row;return 0===t.rowIndex?"sorting--current":""},editRowClass:function(t){t.row;return t.rowIndex===this.editRowClass?"sorting--current":""},thStyle:function(t){return{background:"#"+t,color:"#fff"}},tcStyle:function(t){return{background:"#"+t}},readList:function(t){var e=this;t?this.request.open(this.ip).then(function(t){e.computeWeight()}):clearInterval(this.timer)},getList:function(t){switch(this.sortType){case"up":this.sortType="down";break;case"down":this.sortType="nor";break;case"nor":this.sortType="up"}this.sortProp=t,this.fetchSortingList()},getItem:function(t,e,i,r){this.currentSorting=t,this.sortOutNum=""},computeWeight:function(){var t=this;this.timer=setInterval(function(){t.request.read(t.ip).then(function(e){var i=e.split("(kg)"),r=i.length;if(!(r<2)){var n=Math.ceil(r/2)-10,s=0;n<0&&(n=0);for(var o=20,a=0;a<20;a++)if(i[n+a]){var l=+i[n+a].split("=")[1];isNaN(l)?o--:s+=+l}else o--;var c=s/o;isNaN(c)||(t.sortOutNum=c.toFixed(2)),t.currentSorting.sortOutNum=t.sortOutNum}})},500)},editSortedList:function(t,e,i,r){this.currentSorting=t,this.sortOutNum=t.sortOutNum},handleSave:function(){var t=this;if(this.sortOutNum<=0)this.$message.error("实际数量应该大于0");else{var e={};e.tradeId=this.currentSorting.tradeId,e.sortOutNum=this.sortOutNum,this.confirmSortingNumber(e).then(function(e){t.$message.success("分拣成功!"),t.$set(t.currentSorting,"hasSubmit",!0)})}},handlePrint:function(){this.currentSorting.sortOutNum?this.printTicket(this.currentSorting):this.$message.error("请填写实际分拣数量")},handleNext:function(){this.fetchSortingList(),this.sortOutNum="",this.resetVirtualBoard()},changeNumber:function(t){this.currentSorting.sortOutNum=t,this.sortOutNum=t},resetForm:function(){this.sortingSearchForm={}},resetVirtualBoard:function(){this.$refs.vboard.reset()}}},l={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"container page-wrap"},[i("div",{staticClass:"search"},[i("el-form",{attrs:{model:t.sortingSearchForm,inline:!0,"label-width":"100px"}},[i("el-form-item",{attrs:{label:"配送日期:"}},[i("el-date-picker",{staticClass:"form-field",attrs:{type:"date",placeholder:"选择日期","value-format":"yyyy-MM-dd"},model:{value:t.sortingSearchForm.deliveryTime,callback:function(e){t.$set(t.sortingSearchForm,"deliveryTime",e)},expression:"sortingSearchForm.deliveryTime"}})],1),t._v(" "),i("el-form-item",{attrs:{label:"配送批次:"}},[i("el-select",{staticClass:"form-field",attrs:{placeholder:"请选择"},model:{value:t.sortingSearchForm.buySeq,callback:function(e){t.$set(t.sortingSearchForm,"buySeq",e)},expression:"sortingSearchForm.buySeq"}},t._l(t.buySeqOptions,function(t){return i("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})}),1)],1),t._v(" "),i("el-form-item",{attrs:{label:"采购商:"}},[i("buyer-check",{model:{value:t.sortingSearchForm.buyerId,callback:function(e){t.$set(t.sortingSearchForm,"buyerId",e)},expression:"sortingSearchForm.buyerId"}})],1),t._v(" "),i("el-form-item",{attrs:{label:"商品名称"}},[i("el-input",{staticClass:"form-field",attrs:{placeholder:""},model:{value:t.sortingSearchForm.goodsName,callback:function(e){t.$set(t.sortingSearchForm,"goodsName",e)},expression:"sortingSearchForm.goodsName"}})],1),t._v(" "),i("el-form-item",{attrs:{label:"","label-width":"20"}},[i("el-button",{attrs:{type:"primary"},on:{click:t.fetchSortingList}},[t._v("搜索")]),t._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:t.fastSortOut}},[t._v("一键分拣")]),t._v(" "),i("el-button",{staticStyle:{"margin-left":"15px"},attrs:{type:"default"},on:{click:t.resetForm}},[t._v("重置")])],1)],1)],1),t._v(" "),i("div",{staticClass:"body"},[i("el-container",{staticClass:"inner"},[i("el-aside",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],attrs:{width:"40%"}},[i("div",{staticClass:"left-top"},[i("div",{staticClass:"left-title"},[i("div",[t._v("待分拣（"+t._s(t.sortingNumber)+"）")]),t._v(" "),i("div",{staticClass:"refresh",on:{click:t.fetchSortingList}},[t._v("刷新")])]),t._v(" "),i("el-table",{staticClass:"table top-table",attrs:{data:t.waitingList,border:"","header-cell-style":t.thStyle("494949"),"cell-style":t.tcStyle("f5efe3"),"row-class-name":t.currentRowClass},on:{"row-click":t.getItem,"sort-change":t.getList}},[i("el-table-column",{attrs:{prop:"buyerName"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("div",[t._v("\n                                    "+t._s(e.row.buyerName)+"\n                                ")])]}}])},[i("template",{slot:"header"},[i("div",{on:{click:function(e){return t.getList("buyerName")}}},[t._v("\n                                    单位 "),i("i",{staticClass:"el-icon-sort"})])])],2),t._v(" "),i("el-table-column",{attrs:{prop:"goodsName"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("el-popover",{attrs:{trigger:"hover",placement:"top",width:"120px"}},[i("p",[t._v(t._s(e.row.goodsName))]),t._v(" "),i("div",{attrs:{slot:"reference"},slot:"reference"},[i("p",{staticClass:"text-ellipsis"},[t._v(t._s(e.row.goodsName))])])])]}}])},[i("template",{slot:"header"},[i("div",{on:{click:function(e){return t.getList("goodsName")}}},[t._v("\n                                    商品 "),i("i",{staticClass:"el-icon-sort"})])])],2),t._v(" "),i("el-table-column",{attrs:{prop:"goodsModel",width:"100"}},[i("template",{slot:"header"},[i("div",{on:{click:function(e){return t.getList("goodsModel")}}},[t._v("\n                                    规格 "),i("i",{staticClass:"el-icon-sort"})])])],2),t._v(" "),i("el-table-column",{attrs:{prop:"buyNum",width:"100"}},[i("template",{slot:"header"},[i("div",{on:{click:function(e){return t.getList("buyNum")}}},[t._v("\n                                    数量 "),i("i",{staticClass:"el-icon-sort"})])])],2)],1)],1),t._v(" "),i("div",{staticClass:"left-bottom"},[i("div",{staticClass:"left-title"},[t._v("已分拣（"+t._s(t.sortedNumber)+"）")]),t._v(" "),i("el-table",{staticClass:"table bottom-table",attrs:{data:t.filterSortedList,border:"","header-cell-style":t.thStyle("494949"),"cell-style":t.tcStyle("edf9fc"),"row-class-name":t.editRowClass},on:{"row-click":t.editSortedList}},[i("el-table-column",{attrs:{label:"单位",prop:"buyerName",sortable:""}}),t._v(" "),i("el-table-column",{attrs:{label:"商品"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("el-popover",{attrs:{trigger:"hover",placement:"top",width:"120px"}},[i("p",[t._v(t._s(e.row.goodsName))]),t._v(" "),i("div",{attrs:{slot:"reference"},slot:"reference"},[i("p",{staticClass:"text-ellipsis"},[t._v(t._s(e.row.goodsName))])])])]}}])}),t._v(" "),i("el-table-column",{attrs:{sortable:"",label:"规格",prop:"goodsModel",width:"100"}}),t._v(" "),i("el-table-column",{attrs:{sortable:"",label:"分拣量",prop:"sortOutNum",width:"100"}})],1)],1)]),t._v(" "),i("el-main",{staticClass:"right"},[i("div",{staticClass:"right-header"},[i("div",{staticClass:"print"},[i("span",[t._v("易联云打印:")]),t._v(" "),i("el-select",{attrs:{placeholder:"选择打印机",clearable:""},on:{change:t.savePrinterInfo},model:{value:t.printerSn,callback:function(e){t.printerSn=e},expression:"printerSn"}},t._l(t.printerList,function(t){return i("el-option",{key:t.id,attrs:{label:t.name,value:t.machineCode}})}),1)],1),t._v(" "),i("div",{staticClass:"print"},[i("span",[t._v("打印规格:")]),t._v(" "),i("el-select",{attrs:{clearable:"",placeholder:"选择易联云打印机打印规格"},on:{change:t.savePrinterInfo},model:{value:t.printType,callback:function(e){t.printType=e},expression:"printType"}},t._l(t.printList,function(t){return i("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})}),1)],1)]),t._v(" "),i("div",{staticClass:"right-header"},[i("div",{staticClass:"print"},[i("span",[t._v("飞鹅打印机:")]),t._v(" "),i("el-select",{attrs:{clearable:"",placeholder:"选择飞鹅打印机"},on:{change:t.saveFeieInfo},model:{value:t.feieSn,callback:function(e){t.feieSn=e},expression:"feieSn"}},t._l(t.feieList,function(t){return i("el-option",{key:t.id,attrs:{label:t.printerName,value:t.printerSn}})}),1)],1)]),t._v(" "),i("div",{staticClass:"station"},[t.currentSorting?i("div",[i("h2",[t._v(t._s(t.currentSorting.goodsName))]),t._v(" "),i("el-row",{staticClass:"field"},[i("el-col",{staticClass:"label",attrs:{span:8}},[t._v("商品")]),t._v(" "),i("el-col",{attrs:{span:16}},[t._v(t._s(t.currentSorting.goodsName))])],1),t._v(" "),i("el-row",{staticClass:"field"},[i("el-col",{staticClass:"label",attrs:{span:8}},[t._v("规格")]),t._v(" "),i("el-col",{attrs:{span:16}},[t._v(t._s(t.currentSorting.goodsModel||"/"))])],1),t._v(" "),i("el-row",{staticClass:"field"},[i("el-col",{staticClass:"label",attrs:{span:8}},[t._v("订购数量")]),t._v(" "),i("el-col",{attrs:{span:16}},[t._v(t._s(t.currentSorting.buyNum)+" "+t._s(t.currentSorting.unit?"/"+t.currentSorting.unit:""))])],1),t._v(" "),i("el-row",{staticClass:"field remark"},[i("el-col",{staticClass:"label",attrs:{span:8}},[t._v("备注")]),t._v(" "),i("el-col",{attrs:{span:16}},[t._v(t._s(t.currentSorting.remarks))])],1),t._v(" "),i("el-row",{staticClass:"field sorting"},[i("el-col",{staticClass:"label",attrs:{span:8}},[t._v("实际分拣")]),t._v(" "),i("el-col",{attrs:{span:8}},[i("el-input",{staticClass:"input",attrs:{placeholder:"0",size:"default",readonly:""},model:{value:t.sortOutNum,callback:function(e){t.sortOutNum=e},expression:"sortOutNum"}}),t._v(" "),t.currentSorting.hasSubmit?i("div",{staticClass:"has-submit"},[i("i",{staticClass:"el-icon-check"})]):t._e()],1),t._v(" "),i("el-col",{attrs:{span:8}},[i("el-switch",{attrs:{"active-text":"自动称重"},on:{change:t.readList},model:{value:t.isAuto,callback:function(e){t.isAuto=e},expression:"isAuto"}})],1)],1),t._v(" "),i("el-row",{staticClass:"field vboard"},[i("el-col",{attrs:{span:24}},[i("virtual-board",{ref:"vboard",attrs:{value:t.number},on:{change:t.changeNumber}})],1)],1),t._v(" "),i("el-row",{staticClass:"field btn-group"},[i("el-button",{staticClass:"btn",attrs:{type:"primary"},on:{click:t.handleSave}},[i("i",{staticClass:"el-icon-hh-check"}),t._v(" 确定\n                            ")]),t._v(" "),i("el-button",{staticClass:"btn",on:{click:t.handlePrint}},[i("i",{staticClass:"el-icon-hh-punch"}),t._v(" 单次打印\n                            ")]),t._v(" "),i("el-button",{staticClass:"btn",on:{click:t.handleNext}},[i("i",{staticClass:"el-icon-hh-right"}),t._v(" 下一个\n                            ")]),t._v(" "),i("el-button",{staticClass:"btn"},[i("i",{staticClass:"el-icon-hh-punch"}),t._v(" 批量打印\n                            ")])],1)],1):t._e()])])],1)],1)])},staticRenderFns:[]};var c=i("C7Lr")(a,l,!1,function(t){i("qohM"),i("uDAm")},"data-v-e96fdfdc",null);e.default=c.exports},qohM:function(t,e){},uDAm:function(t,e){},xAIq:function(t,e){}});