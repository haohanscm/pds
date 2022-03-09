package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminStatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/17
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/statistical")
public class PdsStatisticalApiCtrl extends BaseController {
    @Autowired
    private IPdsAdminStatisticalService adminStatisticalServiceImpl;

    @RequestMapping(value = "overview")
    @ResponseBody
    public BaseResp overView(PdsApiStatisOverViewReq statisticalReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.overview(statisticalReq, new Date());
        return baseResp;
    }

    //销售额曲线
    @RequestMapping(value = "statisCurve")
    @ResponseBody
    public BaseResp statisCurve(PdsApiStatisCurveReq statisCurveReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (null == statisCurveReq.getStartDate() || null == statisCurveReq.getEndDate()) {
            baseResp.setMsg("请选择要查询的日期");
            return baseResp;
        }
        baseResp = adminStatisticalServiceImpl.statisCurve(statisCurveReq);
        return baseResp;
    }

    //分类占比
    @RequestMapping(value = "categoryPercent")
    @ResponseBody
    public BaseResp categoryPercent(PdsApiStatisCurveReq statisCurveReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.categoryPercent(statisCurveReq);
        return baseResp;
    }

    //单品销量排行
    @RequestMapping(value = "goodsTopN")
    @ResponseBody
    public BaseResp goodsTopN(PdsApiStatisSaleTopReq saleTopReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.goodsTopN(saleTopReq);
        return baseResp;
    }

    //待处理订单
    @RequestMapping(value = "orderDeal")
    @ResponseBody
    public BaseResp orderDeal(PdsApiStatisOrderDealReq orderDealReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.orderDeal(orderDealReq);
        return baseResp;
    }


    //采购订单数据分析
    @RequestMapping(value = "orderAnalyze")
    @ResponseBody
    public BaseResp orderAnalyze(PdsApiOrderAnalyzeReq orderAnalyzeReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.orderAnalyze(orderAnalyzeReq);
        return baseResp;
    }

    //商户销量排行
    @RequestMapping(value = "buyerSaleTop")
    @ResponseBody
    public BaseResp buyerSaleTop(PdsApiOrderAnalyzeReq orderAnalyzeReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.buyerSaleTop(orderAnalyzeReq);
        return baseResp;
    }

    //销售趋势
    @RequestMapping(value = "saleTrend")
    @ResponseBody
    public BaseResp saleTrend(PdsApiOrderAnalyzeReq orderAnalyzeReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.saleTrend(orderAnalyzeReq);
        return baseResp;
    }

    //运营简报
    @RequestMapping(value = "briefReport")
    @ResponseBody
    public BaseResp briefReport(PdsApiOrderAnalyzeReq orderAnalyzeReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        baseResp = adminStatisticalServiceImpl.briefReport(orderAnalyzeReq);
        return baseResp;
    }

    /**
     * 查询 时间段内 按采购商所属商家查看每日货款金额
     * 最大允许查询天数为90天
     *
     * @param rangeAmountReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "rangeAmount")
    @ResponseBody
    public BaseResp rangeAmount(@Validated PdsRangeAmountReq rangeAmountReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Date begin = rangeAmountReq.getStartDate();
        Date end = rangeAmountReq.getEndDate();
        if (begin.after(end)) {
            baseResp.setMsg("请选择正确的时间段");
            return baseResp;
        } else if (DateUtils.getDistanceOfTwoDate(begin, end) > 90) {
            // 最大允许查询天数为90天
            rangeAmountReq.setEndDate(DateUtils.getOffsetDate(begin, 90));
        }
        baseResp = adminStatisticalServiceImpl.rangeAmount(rangeAmountReq);
        return baseResp;
    }


}
