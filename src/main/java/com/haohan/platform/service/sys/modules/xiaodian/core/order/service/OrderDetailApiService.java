package com.haohan.platform.service.sys.modules.xiaodian.core.order.service;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.service.AreaService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderDetailReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryAddress;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.SaleRules;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryAddressService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.SaleRulesService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/9/30
 */
@Service
public class OrderDetailApiService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SaleRulesService saleRulesService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;

    public BaseResp save(BaseResp baseResp, GoodsOrderDetail goodsOrderDetail){
        goodsOrderDetailService.save(goodsOrderDetail);
        baseResp.success();
        return baseResp;
    }

    //??????????????????
    public BaseResp check(BaseResp baseResp, BaseOrderDetailReq detailReq, GoodsOrder goodsOrder)throws Exception{
//        for (BaseOrderDetailReq item : detailList){
        String goodsId = detailReq.getGoodsId();
        Goods goods = goodsService.get(goodsId);
        if (null != goods){
            //????????????????????????
            String saleRuleFlag = goods.getSaleRule();
            String yesCode = ICommonConstant.YesNoType.yes.getCode();
            if (StringUtils.equals(yesCode,saleRuleFlag)){
                //????????????????????????
                SaleRules saleRules = saleRulesService.findByGoodsId(goodsId);
                String orderFrom = goodsOrder.getOrderFrom();
                String fromAdminCode = IOrderServiceConstant.OrderFrom.merchant_admin.getCode();
                if (null != saleRules && !fromAdminCode.equals(orderFrom)){
                    //????????????
                    baseResp = checkSaleRules(baseResp,goodsOrder,saleRules,detailReq);
                    if (!baseResp.isSuccess()){
                        return baseResp;
                    }
                }
            }
        }else {
            baseResp.setMsg("goodsId??????");
            return baseResp;
        }
//        }
        baseResp.success();
        return baseResp;
    }

    //??????????????????

    //??????

    //????????????
    public BaseResp checkSaleRules(BaseResp baseResp, GoodsOrder goodsOrder , SaleRules saleRules,BaseOrderDetailReq orderDetail){
        DeliveryAddress deliveryAddress = deliveryAddressService.get(goodsOrder.getAddressId());
        String uid = goodsOrder.getUid();
        String goodsId = orderDetail.getGoodsId();
        BigDecimal goodsNum = orderDetail.getGoodsNum();
        //1 ????????????????????????????????????
        boolean isAllowSale = checkAreas(saleRules, deliveryAddress);
        if (!isAllowSale) {
            baseResp.error();
            baseResp.setMsg("????????????,??????????????????????????????????????????");
            return baseResp;
        }
        //2 ???????????????????????????????????????
        boolean notToMaxTimes = checkMaxBuy(saleRules, uid, goodsId, goodsNum);
        if (!notToMaxTimes) {
            baseResp.error();
            baseResp.setMsg("????????????,???????????????????????????");
            return baseResp;
        }
        //3 ??????????????????????????????????????????
        boolean isOverMinLimitBuy = checkMinBuy(saleRules, goodsNum);
        if (!isOverMinLimitBuy) {
            baseResp.error();
            baseResp.setMsg("????????????,??????????????????????????????");
            return baseResp;
        }
        baseResp.success();
        return baseResp;
    }

    private Boolean checkAreas(SaleRules saleRules,DeliveryAddress deliveryAddress){
        Area area = saleRules.getArea();
        if (null == area || null == deliveryAddress){
            return true;
        }
        String saleAreaCode = saleRules.getArea().getCode();
        if (StringUtils.isEmpty(saleAreaCode)){
            return true;
        }
        Area saleArea = areaService.fetchByCode(saleAreaCode);
        String deliveryRegionCode = deliveryAddress.getRegion();
        Area deliveryArea = areaService.fetchByCode(deliveryRegionCode);
        //??????????????????????????????????????????????????????true
        if (deliveryArea.getParentIds().indexOf(saleArea.getId())!=-1
                || StringUtils.equals(deliveryArea.getId(),saleArea.getId())){
            return true;
        } else {
            return false;
        }
    }

    private Boolean checkMaxBuy(SaleRules saleRules,String uid,String goodsId,BigDecimal goodsNum){
        BigDecimal boughtNum = goodsOrderDetailService.countMemberBoughtNum(uid,goodsId);
        Integer limitBuy = saleRules.getLimitBuyTimes();
        if (null == limitBuy || StringUtils.isEmpty(uid)){
            return true;
        }
        if (boughtNum.add(goodsNum).compareTo(new BigDecimal(limitBuy)) <= 0){
            return true;
        }else {
            return false;
        }
    }

    private Boolean checkMinBuy(SaleRules saleRules,BigDecimal goodsNum){
        Integer minSaleNum = saleRules.getMinSaleNum();
        if (null == minSaleNum){
            return true;
        }
        BigDecimal minSaleNumDecimal = new BigDecimal(minSaleNum);
        if (goodsNum.compareTo(minSaleNumDecimal) < 0){
            return false;
        } else {
            return true;
        }
    }


}
