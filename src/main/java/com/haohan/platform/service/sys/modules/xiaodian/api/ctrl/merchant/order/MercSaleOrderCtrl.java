package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.order;

import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.req.MercSaleOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp.MercSaleOrderResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.order.req.ExpressCompanyApiReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.GoodsOrderApiService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.OrderDeliveryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 销售订单
 * Created by zgw on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/order/saleOrder")
public class MercSaleOrderCtrl extends BaseController {
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderApiService goodsOrderApiService;
    @Autowired
    private FeiePrinterService feiePrinterService;
    @Autowired
    private OrderDeliveryService orderDeliveryService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;

    //订单列表
    @RequestMapping(value = "fetchList")
    @ResponseBody
    public BaseResp fetchList(MercSaleOrderReq mercSaleOrderReq , HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();

        Page reqPage = new Page();
        Integer pageNo = mercSaleOrderReq.getPageNo();
        Integer pageSize = mercSaleOrderReq.getPageSize();

        if (null != pageNo){
            reqPage.setPageNo(pageNo);
        }
        if (null != pageSize){
            reqPage.setPageSize(pageSize);
        }

        mercSaleOrderReq.setMerchantId(merchantId);
        Page<MercSaleOrderResp> page = goodsOrderService.findMercSaleOrderPage(reqPage,mercSaleOrderReq);
        List<MercSaleOrderResp> goodsOrderList = page.getList();

        BaseList<MercSaleOrderResp> baseList = new BaseList<>();
        baseList.setTotalPage(page.getTotalPage());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(goodsOrderList);
        return baseList;
    }

    //修改订单备注
    @RequestMapping(value = "edit")
    @ResponseBody
    public BaseResp edit(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        String orderId = request.getParameter("orderId");
        String orderRemark = request.getParameter("orderRemark");

        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        goodsOrder.setOrderMarks(orderRemark);
        goodsOrderService.save(goodsOrder);

        baseResp.success();
        return baseResp;
    }

    //取消订单
    @RequestMapping(value = "orderCancel")
    @ResponseBody
    public BaseResp orderCancel(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        String orderId = request.getParameter("orderId");

        if (StringUtils.isEmpty(orderId)){
            baseResp.error();
            baseResp.setMsg("请先选择订单");
            return baseResp;
        }

        goodsOrderApiService.orderCancel(baseResp,goodsOrderService.fetchByOrderId(orderId));
        return baseResp;
    }

    //打印订单
    @RequestMapping(value = "print")
    @ResponseBody
    public BaseResp print(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        String orderId = request.getParameter("orderId");

        if (StringUtils.isEmpty(orderId)){
            baseResp.error();
            baseResp.setMsg("请先选择订单");
            return baseResp;
        }

        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        baseResp = feiePrinterService.printByShopId(goodsOrder.getShopId(), feiePrinterService.fetchPrintOrderById(orderId));
        return baseResp;
    }

    //订单发货
    @RequestMapping(value = "shippingGoods")
    @ResponseBody
    public BaseResp shippingGoods(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        String orderId = request.getParameter("orderId");
        String expressOrder = request.getParameter("expressOrder");
        String companyCode = request.getParameter("code");

        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        MerchantAppManage merchantAppManage = null;
        String appid = goodsOrder.getAppid();
        if (StringUtils.isNotEmpty(appid)){
            merchantAppManage = merchantAppManageService.fetchByAppId(appid);
        }
        if (null == merchantAppManage){
            baseResp.error();
            baseResp.setMsg("无效的应用");
            return baseResp;
        }
        OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
        orderDelivery.setExpressOrder(expressOrder);
        orderDelivery.setOrderStatus(IOrderServiceConstant.OrderStatus.SHIPPED.getCode());
        goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.SHIPPED.getCode());
        goodsOrder.setShippingTime(new Date());
        orderDeliveryService.save(orderDelivery);
        goodsOrderService.save(goodsOrder);

        return baseResp;
    }

    @Deprecated
    @RequestMapping(value = "fetchExpCompany")
    @ResponseBody
    public BaseResp fetchExpCompany(ExpressCompanyApiReq expressCompanyApiReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = expressCompanyApiReq.getMerchantId();

        String appid = expressCompanyApiReq.getAppid();
        Integer pageNo = expressCompanyApiReq.getPageNo();
        Integer pageSize = expressCompanyApiReq.getPageSize();

        Page page = new Page();
        page.setPageNo(null == pageNo ? 1 : pageNo);
        page.setPageSize(null == pageSize ? 30 : pageSize);

        MerchantAppManage merchantAppManage = merchantAppManageService.fetchByAppId(appid);

        return baseResp;
    }


}
