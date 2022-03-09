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
     * 微信小程序下单
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
                baseResp.setMsg("订单已存在,请勿重复下单");
                return baseResp;
            }

            MerchantAppManage appManage = merchantAppManageService.fetchByAppId(appid);
            if (null == appManage){
                baseResp.setMsg("无效的APPID");
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
                        baseResp.setMsg("下单地址有误,请删除地址后重新添加");
                        return baseResp;
                    }
                    if (StringUtils.isBlank(goodsOrderReq.getDeliveryType())){
                        baseResp.setMsg("请选择配送方式");
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
            baseResp.setMsg("系统错误");
        }finally {
            return baseResp;
        }
    }

    /**
     * web后台下单
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
            //先检查订单类型,当面付和到店不需要地址
            IOrderServiceConstant.OrderType orderType = IOrderServiceConstant.OrderType.valueOfCode(apiRequest.getOrderType());
            switch (orderType){
                case face_pay:
                    break;
                case arrived:
                    break;
                default:
                    DeliveryAddress deliveryAddress = deliveryAddressService.get(apiRequest.getAddressId());
                    if (null == deliveryAddress){
                        baseResp.setMsg("addressId有误");
                        return baseResp;
                    }
                    if (StringUtils.isBlank(apiRequest.getDeliveryType())){
                        baseResp.setMsg("请选择配送方式");
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
            baseResp.setMsg("系统错误");
        }finally {
            return baseResp;
        }
    }

    /**
     * 取消订单
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
                baseResp.setMsg("没有找到订单");
                return baseResp;
            }
            baseResp = goodsOrderApiService.orderCancel(baseResp,goodsOrder);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("系统错误");
        }finally {
            return baseResp;
        }
    }


}
