package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.order;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.GoodsOrderApiService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.IOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.WebGoodsOrderApiReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.WxAppGoodsOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryAddress;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryAddressService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shenyu
 * @create 2018/8/30
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/order/goodsOrder")
public class GoodsOrderApiCtrl extends BaseController {


    @Autowired
    private GoodsOrderApiService goodsOrderApiService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Resource
    @Qualifier("weixinAppOrderService")
    private IOrderService wxOrderServiceImpl;
    @Autowired
    @Qualifier("webOrderService")
    private IOrderService webOrderServiceImpl;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;


    /**
     * ?????????????????????
     * @param goodsOrderReq
     * @param bindingResult
     * @param request
     * @return
     */
    @RequestMapping(value = "createWxOrder")
    @ResponseBody
    public BaseResp createWxOrder(@Validated @RequestBody WxAppGoodsOrderReq goodsOrderReq, BindingResult bindingResult, HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        try {
            String appid = goodsOrderReq.getAppid();

            if (StringUtils.isNotBlank(goodsOrderReq.getOrderId()) && null != goodsOrderService.fetchByOrderId(goodsOrderReq.getOrderId())){
                baseResp.setMsg("???????????????,??????????????????");
                return baseResp;
            }

            MerchantAppManage appManage = merchantAppManageService.fetchByAppId(appid);
            if (null == appManage){
                baseResp.setMsg("?????????APPID");
                return baseResp;
            }

            String jsAppId = appManage.getJisuAppId();
            String partnerNum = goodsOrderReq.getPartnerNum();

            IOrderServiceConstant.OrderType orderType = IOrderServiceConstant.OrderType.valueOfCode(goodsOrderReq.getOrderType());
            switch (orderType){
                case face_pay:
                    break;
                case arrived:
                    break;
                case prodService:
                    break;
                default:
                    DeliveryAddress  deliveryAddress = deliveryAddressService.get(goodsOrderReq.getAddressId());

                    if (null != deliveryAddress){
                        goodsOrderReq.setAddressId(deliveryAddress.getId());
                    } else {
                        baseResp.setMsg("??????????????????,??????????????????????????????");
                        return baseResp;
                    }
                    if (StringUtils.isBlank(goodsOrderReq.getDeliveryType())){
                        baseResp.setMsg("?????????????????????");
                        return baseResp;
                    }
            }

            GoodsOrder goodsOrder = new GoodsOrder();
            baseResp = wxOrderServiceImpl.createOrder(baseResp,goodsOrderReq,goodsOrder);
            if (!baseResp.isSuccess()){
                return baseResp;
            }

            goodsOrderService.save(goodsOrder);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("????????????");
        }finally {
            return baseResp;
        }
    }

    /**
     * web????????????
     * @param apiRequest
     * @param bindingResult
     * @param request
     * @return
     */
    @RequestMapping(value = "createWebOrder")
    @ResponseBody
    public BaseResp createWebOrder(@Validated @RequestBody WebGoodsOrderApiReq apiRequest, BindingResult bindingResult, HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        try {
            //?????????????????????,?????????????????????????????????
            IOrderServiceConstant.OrderType orderType = IOrderServiceConstant.OrderType.valueOfCode(apiRequest.getOrderType());
            switch (orderType){
                case face_pay:
                    break;
                case arrived:
                    break;
                default:
                    DeliveryAddress deliveryAddress = deliveryAddressService.get(apiRequest.getAddressId());
                    if (null == deliveryAddress){
                        baseResp.setMsg("addressId??????");
                        return baseResp;
                    }
                    if (StringUtils.isBlank(apiRequest.getDeliveryType())){
                        baseResp.setMsg("?????????????????????");
                        return baseResp;
                    }
            }

            GoodsOrder goodsOrder = new GoodsOrder();
            baseResp = webOrderServiceImpl.createOrder(baseResp,apiRequest,goodsOrder);
            if (!baseResp.isSuccess()){
                return baseResp;
            }
            goodsOrderService.save(goodsOrder);
            baseResp.setExt(goodsOrder.getOrderId());
        }catch (Exception e){
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("????????????");
        }finally {
            return baseResp;
        }
    }

    /**
     * ????????????
     * @param request
     * @return
     */
    @RequestMapping(value = "cancelOrder")
    @ResponseBody
    public BaseResp cancelOrder(String orderId,HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        try {
            if (StringUtils.isBlank(orderId)){
                baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
                return baseResp;
            }

            GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
            if (null == goodsOrder){
                baseResp.error();
                baseResp.setMsg("??????????????????");
                return baseResp;
            }
            baseResp = goodsOrderApiService.orderCancel(baseResp,goodsOrder);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("????????????");
        }finally {
            return baseResp;
        }
    }


}
