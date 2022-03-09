package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery.PdsShipBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiOfferOrderResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminBuyOrderService;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrder;
import com.haohan.platform.service.sys.modules.pds.service.order.PdsShipOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shenyu
 * @create 2018/10/31
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/order")
public class PdsAdminOrderCtrl extends BaseController {
    @Resource
    private IPdsAdminBuyOrderService pdsAdminBuyOrderService;
    @Autowired
    private PdsShipOrderService pdsShipOrderService;


    //获取采购单列表
    @RequestMapping(value = "buy/list")
    @ResponseBody
    public BaseResp fetchBuyList(@Validated(DefaultGroup.class) PdsBuyOrderApiReq apiBuyOrderReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Integer pageNo = apiBuyOrderReq.getPageNo();
        Integer pageSize = apiBuyOrderReq.getPageSize();

        Page<BuyOrder> reqPage = new Page<>();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        baseResp = pdsAdminBuyOrderService.fetchBuyOrderList(reqPage, apiBuyOrderReq);
        return baseResp;
    }

    //获取采购明细列表
    @RequestMapping(value = "buyDetail/list")
    @ResponseBody
    public BaseResp fetchBuyDetailList(@Validated(DefaultGroup.class) PdsBuyOrderDetailApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Integer pageNo = apiReq.getPageNo();
        Integer pageSize = apiReq.getPageSize();

        Page<BuyOrderDetail> reqPage = new Page<>();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 20 : pageSize);

        baseResp = pdsAdminBuyOrderService.findBuyOrderdetails(reqPage, apiReq.getBuyId());
        return baseResp;
    }

    //删除采购明细
    @RequestMapping(value = "buyDetail/delete")
    @ResponseBody
    public BaseResp delete(@Validated(DeleteGroup.class) PdsBuyOrderDetailApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        baseResp = pdsAdminBuyOrderService.deleteBuyDetail(apiReq.getDetailId());
        return baseResp;
    }

    //修改运费
    @RequestMapping(value = "editBuyOrder")
    @ResponseBody
    public BaseResp editBuyOrder(@Validated(DefaultGroup.class) PdsBuyShipFeeApiReq shipFeeReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminBuyOrderService.editShipFee(shipFeeReq);
        return baseResp;
    }

    //获取报价单列表
    @RequestMapping(value = "offer/findPage")
    @ResponseBody
    public BaseResp findOfferList(@Validated(DefaultGroup.class) PdsOfferOrderApiReq offerOrderReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Integer pageNo = offerOrderReq.getPageNo();
        Integer pageSize = offerOrderReq.getPageSize();

        Page<PdsApiOfferOrderResp> reqPage = new Page<>();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 20 : pageSize);

        offerOrderReq.setStatus(IPdsConstant.OfferOrderStatus.success.getCode());
        baseResp = pdsAdminBuyOrderService.findOfferPage(reqPage, offerOrderReq);
        return baseResp;
    }

    //查询送货单列表
    @RequestMapping(value = "driver/shipList")
    @ResponseBody
    public BaseResp shipOrderList(@Validated(DefaultGroup.class) PdsShipOrderApiReq shipOrderReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Page reqPage = new Page();
        Integer pageNo = shipOrderReq.getPageNo();
        Integer pageSize = shipOrderReq.getPageSize();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);
        reqPage.setOrderBy("shipId");

        baseResp = pdsAdminBuyOrderService.findShipOrderInfoList(shipOrderReq, reqPage);
        return baseResp;
    }

    //获取送货单明细
    @RequestMapping(value = "driver/shipDetails")
    @ResponseBody
    public BaseResp shipDetailList(@Validated(DefaultGroup.class) PdsShipOrderApiReq apiShipOrderReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        Integer pageNo = apiShipOrderReq.getPageNo();
        Integer pageSize = apiShipOrderReq.getPageSize();

        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);
        reqPage.setOrderBy("buyId");
        PdsShipBuyerApiReq shipBuyerReq = new PdsShipBuyerApiReq();
        BeanUtils.copyProperties(apiShipOrderReq, shipBuyerReq);

        baseResp = pdsAdminBuyOrderService.findShipOrderPage(reqPage, shipBuyerReq);
        return baseResp;
    }

    //获取自提采购商列表
    @RequestMapping(value = "selfOrder/buyerList")
    @ResponseBody
    public BaseResp findSelfOrderBuyerList(@Validated(DefaultGroup.class) PdsSelfOrderApiReq apiSelfOrderReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminBuyOrderService.findSelfOrderBuyerList(apiSelfOrderReq);
        return baseResp;
    }

    //获取自提交易单列表
    @RequestMapping(value = "selfOrder/orderList")
    @ResponseBody
    public BaseResp findSelfOrderList(@Validated PdsSelfOrderApiReq apiSelfOrderReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        Page reqPage = new Page();
        Integer pageNo = apiSelfOrderReq.getPageNo();
        Integer pageSize = apiSelfOrderReq.getPageSize();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        baseResp = pdsAdminBuyOrderService.findSelfOrderPage(reqPage, apiSelfOrderReq);
        return baseResp;
    }

    //修改送货单备注
    @RequestMapping(value = "shipOrder/edit")
    @ResponseBody
    public BaseResp submitShipOrder(String remarks, String shipId, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(shipId, remarks)) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }
        PdsShipOrder pdsShipOrder = pdsShipOrderService.fetchByShipId(shipId);
        if (null == pdsShipOrder) {
            baseResp.setMsg("送货单号有误");
            return baseResp;
        }
        pdsShipOrder.setRemarks(remarks);
        pdsShipOrderService.save(pdsShipOrder);
        baseResp.success();
        return baseResp;
    }


}
