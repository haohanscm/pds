package com.haohan.platform.service.sys.modules.xiaodian.api.service.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderDetailService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryAddress;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ShopServiceDistrict;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.exception.DataNotFoundException;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryAddressService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.ShopServiceDistrictService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2019/1/3
 */
@Service
public class GoodsOrderPdsService {
    final String pmId = IOrderServiceConstant.selfPdsPmId;
    @Autowired
    private ShopServiceDistrictService shopServiceDistrictService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    private BuyOrderService buyOrderService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private GoodsModelService goodsModelService;
    @Autowired
    private BuyOrderDetailService buyOrderDetailService;

    //根据用户下单片区确定采购商家
    private String fetchSelfMerchant(GoodsOrder goodsOrder){
        String addressId = goodsOrder.getAddressId();
        String districtId = "";
        String merchantId = "";
        DeliveryAddress deliveryAddress = deliveryAddressService.get(addressId);
        if (null != deliveryAddress){
            districtId = deliveryAddress.getDistrictArea();
            if (StringUtils.isNotBlank(districtId)){
                ShopServiceDistrict shopServiceDistrict = new ShopServiceDistrict();
                shopServiceDistrict.setMerchantId(merchantId);
                shopServiceDistrict.setServiceType(ICommonConstant.XdServiceType.pds_sys.getCode());
                shopServiceDistrict.setDistrictAreaId(districtId);
                List<ShopServiceDistrict> districtList = shopServiceDistrictService.findList(shopServiceDistrict);
                if (CollectionUtils.isEmpty(districtList)){
                    return "";
                }else {
                    ShopServiceDistrict serviceDistrict = districtList.get(0);
                    return serviceDistrict.getMerchantId();
                }
            }
        }
        return merchantId;
    }

    //判断是否需要新增采购商
    private PdsBuyer createBuyer(GoodsOrder goodsOrder, String merchantId){
        String uid = goodsOrder.getUid();
        PdsBuyer pdsBuyer = pdsBuyerService.fetchByPassPortId(uid,pmId,merchantId);
        if (null != pdsBuyer){
            return pdsBuyer;
        }
        Merchant merchant = merchantService.get(merchantId);
        if (null != merchant){
            DeliveryAddress deliveryAddress = deliveryAddressService.get(goodsOrder.getAddressId());
            if (null != deliveryAddress){
                pdsBuyer = new PdsBuyer();
                pdsBuyer.setPmId(pmId);
                pdsBuyer.setAddress(merchant.getAddress());
                pdsBuyer.setMerchantId(merchantId);
                pdsBuyer.setMerchantName(merchant.getMerchantName());
                pdsBuyer.setPassportId(goodsOrder.getUid());
                pdsBuyer.setTelephone(deliveryAddress.getReceiverMobile());
                pdsBuyer.setBuyerName(goodsOrder.getUserName());
                pdsBuyer.setShortName(goodsOrder.getUserName());
                pdsBuyer.setContact(deliveryAddress.getReceiver());
                pdsBuyer.setBuyerType(IPdsConstant.BuyerType.employee.getCode());
                pdsBuyerService.save(pdsBuyer);
            }
        }
        return pdsBuyer;
    }

    //零售单转采购单
    @Transactional(rollbackFor = Exception.class)
    public BaseResp convertToBuyOrder(GoodsOrder goodsOrder) throws DataNotFoundException {
        BaseResp baseResp = BaseResp.newError();

        //验证订单状态
//        String orderStatus = goodsOrder.getOrderStatus();
//        if (!IOrderServiceConstant.OrderStatus.WAITE_SHIP.getCode().equals(orderStatus)){
//            baseResp.setMsg("订单状态有误");
//            return baseResp;
//        }
        String merchantId = fetchSelfMerchant(goodsOrder);
        if (StringUtils.isBlank(merchantId)){
            baseResp.setMsg("下单地址附近未找到自提点");
            return baseResp;
        }
        PdsBuyer pdsBuyer = createBuyer(goodsOrder,merchantId);
        if (null == pdsBuyer){
            baseResp.setMsg("下单数据有误,无法创建采购商");
            return baseResp;
        }
        BuyOrder buyOrder = copyFrom(goodsOrder);
        buyOrder.setBuyerId(pdsBuyer.getId());
        buyOrder.setBuyerUid(goodsOrder.getUid());
        buyOrder.setMerchantId(pdsBuyer.getMerchantId());
        buyOrder.setBuyerName(pdsBuyer.getBuyerName());
        buyOrder.setContact(pdsBuyer.getContact());
        buyOrder.setTelephone(pdsBuyer.getTelephone());
        buyOrder.setAddress(pdsBuyer.getAddress());
        buyOrderService.save(buyOrder);
        List<GoodsOrderDetail> goodsOrderDetails = goodsOrderDetailService.findByOrderId(goodsOrder.getOrderId());
        for (GoodsOrderDetail detail : goodsOrderDetails){
            BuyOrderDetail buyOrderDetail = copyFrom(buyOrder,detail);
            if (null == buyOrderDetail){
                throw new DataNotFoundException("商品信息有误,采购单创建失败");
            }
            buyOrderDetailService.save(buyOrderDetail);
        }
        baseResp.success();
        return baseResp;
    }


    //下单时间:13:00-06:00 上午送   06:00-13:00 下午送
    private BuyOrder copyFrom(GoodsOrder goodsOrder){
        BuyOrder buyOrder = new BuyOrder();
        Date now = new Date();
        Date deliveryDate = null;
        String buySeq = "";
        Date orderTime = goodsOrder.getOrderTime();
        Date secondSeqStart = DateUtils.getAnyTime("","","",6,0,0);
        Date secondSeqEnd = DateUtils.getAnyTime("","","",13,0,0);
        if (orderTime.after(secondSeqStart) && orderTime.before(secondSeqEnd)){
            deliveryDate = now;
            buySeq = IPdsConstant.BuySeq.second.getCode();
        }else if (orderTime.after(secondSeqEnd) && orderTime.before(DateUtils.getDayEndTime(now))){
            deliveryDate = DateUtils.getOffsetDate(now,1);
            buySeq = IPdsConstant.BuySeq.first.getCode();
        }else {
            deliveryDate = now;
        }
        buyOrder.setBuySeq(buySeq);
        buyOrder.setDeliveryTime(deliveryDate);
//        buyOrder.setBuyerId();
        buyOrder.setStatus(IPdsConstant.BuyOrderStatus.submit.getCode());
        buyOrder.setPmId(pmId);
        buyOrder.setGenPrice(goodsOrder.getOrderAmount());
        buyOrder.setTotalPrice(goodsOrder.getOrderAmount());
        buyOrder.setShipFee(goodsOrder.getShippingFee());
        buyOrder.setBuyTime(now);
        buyOrder.setDeliveryType(IPdsConstant.DeliveryType.home.getCode());
        buyOrder.setGoodsOrderId(goodsOrder.getOrderId());
        return buyOrder;
    }

    private BuyOrderDetail copyFrom(BuyOrder buyOrder, GoodsOrderDetail goodsOrderDetail){
        GoodsModel goodsModel = goodsModelService.get(goodsOrderDetail.getModelId());
        if (null == goodsModel){
            return null;
        }
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setPmId(pmId);
        buyOrderDetail.setBuyId(buyOrder.getBuyId());
        buyOrderDetail.setBuyerId(buyOrder.getBuyerId());

        buyOrderDetail.setGoodsId(goodsModel.getId());
        buyOrderDetail.setGoodsImg(goodsModel.getModelUrl());
        buyOrderDetail.setGoodsName(goodsOrderDetail.getGoodsName());
        buyOrderDetail.setGoodsModel(goodsOrderDetail.getModelName());
        buyOrderDetail.setGoodsNum(goodsOrderDetail.getGoodsNum());
        buyOrderDetail.setMarketPrice(goodsModel.getModelPrice());
        buyOrderDetail.setUnit(goodsOrderDetail.getGoodsUnit());
        buyOrderDetail.setStatus(IPdsConstant.BuyOrderStatus.submit.getCode());
        buyOrderDetail.setGoodsOrderDetailId(goodsOrderDetail.getId());
        return buyOrderDetail;
    }
}
