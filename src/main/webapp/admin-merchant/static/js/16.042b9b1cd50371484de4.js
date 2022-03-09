webpackJsonp([16],{"/gcq":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a("5YEj"),s=a.n(i),n=a("G0Dk"),r={data:function(){return{request:new n.a,obj:{}}},props:{date:Array,buyerId:String},watch:{date:{handler:function(){this.orderAnalyze()},deep:!0,immediate:!0},buyerId:{handler:function(){this.orderAnalyze()},deep:!0,immediate:!0}},mounted:function(){this.orderAnalyze()},methods:{orderAnalyze:function(){var t=this;this.request.orderAnalyze({startDate:this.date[0],endDate:this.date[1],buyerId:this.buyerId}).then(function(e){200===e.code?t.obj=e.ext:(t.obj={},t.$message.warning(e.msg))})}}},d={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"wrap"},[a("div",{staticClass:"item item-1"},[a("div",{staticClass:"item-title"},[t._v("销售额（元）")]),t._v(" "),a("div",{staticClass:"item-cont"},[t._v(t._s(t.obj.saleAmount))])]),t._v(" "),a("div",{staticClass:"item item-2"},[a("div",{staticClass:"item-title"},[t._v("销售毛利（元）")]),t._v(" "),a("div",{staticClass:"item-cont"},[t._v(t._s(t.obj.grossProfit))])]),t._v(" "),a("div",{staticClass:"item item-3"},[a("div",{staticClass:"item-title"},[t._v("销售毛利率")]),t._v(" "),a("div",{staticClass:"item-cont"},[t._v(t._s((100*t.obj.profitRate).toFixed(2))+" %")])])])},staticRenderFns:[]};var o=a("C7Lr")(r,d,!1,function(t){a("5P4o")},"data-v-628499e8",null).exports,c={data:function(){return{request:new n.a,seriesData:[{name:"占比",type:"pie",radius:"55%",center:["50%","60%"],itemStyle:{emphasis:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}],legendData:{orient:"vertical",left:"left",data:[]}}},props:{date:Array,buyerId:String},watch:{date:{handler:function(){this.goodsTopN()},deep:!0,immediate:!0},buyerId:{handler:function(){this.goodsTopN()},deep:!0}},mounted:function(){this.goodsTopN()},methods:{initChart:function(){this.chart=s.a.init(document.getElementById("sss")),this.chart.setOption({title:{text:"商品销量TOP20占比",subtext:this.date[0]+"至"+this.date[1],x:"center"},tooltip:{trigger:"item",formatter:"{a} <br/>{b} : {c}元 ({d}%)"},legend:this.legendData,series:this.seriesData})},goodsTopN:function(){var t=this;this.request.goodsTopN({startTime:this.date[0],endTime:this.date[1],buyerId:this.buyerId,limitNum:20}).then(function(e){if(200===e.code){var a=[],i=[];e.ext.sort(function(t,e){return e.num-t.num}),e.ext.forEach(function(t){a.push({value:t.saleAmount,name:t.goodsName}),i.push(t.categoryName)}),t.legendData.data=i,t.seriesData[0].data=a,t.initChart()}})}}},u={render:function(){var t=this.$createElement;return(this._self._c||t)("div",{staticClass:"wrap",attrs:{id:"sss"}})},staticRenderFns:[]};var l=a("C7Lr")(c,u,!1,function(t){a("P7zc")},"data-v-3f9ffa68",null).exports,h={data:function(){return{request:new n.a,xAxis:{type:"category",data:[]},series:[{data:[],type:"line",smooth:!0}],option:{title:{text:"商品销量排行TOP20"},tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},legend:{data:["下单量","销售额"]},toolbox:{show:!0,feature:{dataZoom:{yAxisIndex:"none"},dataView:{readOnly:!1},magicType:{type:["line","bar"]},restore:{},saveAsImage:{}}},grid:{left:"3%",right:"4%",bottom:"3%",containLabel:!0},xAxis:{type:"value",boundaryGap:[0,.01]},yAxis:{type:"category",data:["商品啊","商品啊","商品啊","商品啊","商品啊","商品啊"]},series:[{name:"销售额",type:"bar",data:[18203,23489,29034,104970,131744,630230]},{name:"下单量",type:"bar",data:[1,2,4,8,6,21]}]},period:"0"}},mounted:function(){this.goodsTopN()},props:{date:Array,buyerId:String},watch:{date:{handler:function(){this.goodsTopN()},deep:!0,immediate:!0},buyerId:{handler:function(){this.goodsTopN()},deep:!0}},methods:{initChart:function(){this.chart=s.a.init(document.getElementById("merchant-sale")),this.chart.clear(),this.chart.resize(),this.chart.setOption(this.option)},goodsTopN:function(){var t=this;this.request.goodsTopN({startTime:this.date[0],endTime:this.date[1],buyerId:this.buyerId,limitNum:30}).then(function(e){200===e.code?(t.getOpt(e.ext),t.initChart()):t.$message.warning(e.msg)})},getOpt:function(t){var e=[],a=[],i=[];t.forEach(function(t){e.unshift(t.saleAmount),a.unshift(t.saleNum),i.unshift(t.goodsName)}),this.option.yAxis.data=i,this.option.series[0].data=e,this.option.series[1].data=a,this.option.title.subtext=this.date[0]+"至"+this.date[1]}}},m={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",[e("div",{staticClass:"draw",attrs:{id:"merchant-sale"}})])}]};var f={data:function(){return{request:new n.a,date:[this.$utils.getDay(-7),this.$utils.getDay(0)],buyerId:""}},mounted:function(){},components:{vTotalTitle:o,vCateRate:l,vGoodsSale:a("C7Lr")(h,m,!1,function(t){a("ywtL")},"data-v-05d1952f",null).exports},methods:{}},v={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"wrap"},[a("div",{staticClass:"check-condition"},[a("div",[a("el-date-picker",{attrs:{type:"daterange",format:"yyyy-MM-dd","value-format":"yyyy-MM-dd","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期"},model:{value:t.date,callback:function(e){t.date=e},expression:"date"}})],1),t._v(" "),a("div",[a("buyer-check",{model:{value:t.buyerId,callback:function(e){t.buyerId=e},expression:"buyerId"}})],1)]),t._v(" "),a("div",{staticClass:"title"},[a("v-total-title",{attrs:{date:t.date,buyerId:t.buyerId}})],1),t._v(" "),a("div",{staticClass:"charts"},[a("div",{staticClass:"charts-left"},[a("v-cate-rate",{attrs:{date:t.date,buyerId:t.buyerId}})],1),t._v(" "),a("div",{staticClass:"charts-right"},[a("v-goods-sale",{attrs:{date:t.date,buyerId:t.buyerId}})],1)]),t._v(" "),a("div")])},staticRenderFns:[]};var p=a("C7Lr")(f,v,!1,function(t){a("HsCG")},"data-v-ba95cdf0",null);e.default=p.exports},"5P4o":function(t,e){},HsCG:function(t,e){},P7zc:function(t,e){},ywtL:function(t,e){}});