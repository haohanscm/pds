package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req.GoodsOrderReportApiReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.GoodsOrderReportApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shenyu
 * @create 2019/1/17
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/order/report")
public class GoodsOrderReportApiCtrl extends BaseController {
    @Autowired
    private GoodsOrderReportApiService goodsOrderReportApiService;

    /**
     *  订单总数,销售总额,退货总额,新增用户,用户总数
     */
    @RequestMapping(value = "summary")
    @ResponseBody
    public BaseResp reportSummary(GoodsOrderReportApiReq apiReq){
        BaseResp baseResp = goodsOrderReportApiService.reportSummary(apiReq,BaseResp.newError());
        return baseResp;
    }

    /**
     *  销售额曲线
     */
    @RequestMapping(value = "saleCurve")
    @ResponseBody
    public BaseResp saleCurve(GoodsOrderReportApiReq apiReq){
        BaseResp baseResp = goodsOrderReportApiService.saleCurve(apiReq,BaseResp.newError());
        return baseResp;
    }


    /**
     * 今日下单金额,今日订单数,今日客单价,昨日下单金额
     */
    @RequestMapping(value = "realTimeSummary")
    @ResponseBody
    public BaseResp realTimeSummary(GoodsOrderReportApiReq apiReq){
        BaseResp baseResp = goodsOrderReportApiService.realTimeSummary(apiReq,BaseResp.newError());
        return baseResp;
    }


    /**
     *  商品销售分类占比
     */
    @RequestMapping(value = "categorySaleStatis")
    @ResponseBody
    public BaseResp categorySaleStatis(GoodsOrderReportApiReq apiReq){
        BaseResp baseResp = goodsOrderReportApiService.categorySaleStatis(apiReq,BaseResp.newError());
        return baseResp;
    }


    /**
     * 商品销量排行
     */
    @RequestMapping(value = "saleVolumeRank")
    @ResponseBody
    public BaseResp saleVolumeRank(GoodsOrderReportApiReq apiReq){
        BaseResp baseResp = goodsOrderReportApiService.saleVolumeRank(apiReq,BaseResp.newError());
        return baseResp;
    }
}
