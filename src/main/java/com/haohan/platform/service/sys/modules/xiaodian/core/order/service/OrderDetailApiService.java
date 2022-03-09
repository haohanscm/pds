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

    //验证订单商品
    public BaseResp check(BaseResp baseResp, BaseOrderDetailReq detailReq, GoodsOrder goodsOrder)throws Exception{
//        for (BaseOrderDetailReq item : detailList){
        String goodsId = detailReq.getGoodsId();
        Goods goods = goodsService.get(goodsId);
        if (null != goods){
            //是否开启售卖规则
            String saleRuleFlag = goods.getSaleRule();
            String yesCode = ICommonConstant.YesNoType.yes.getCode();
            if (StringUtils.equals(yesCode,saleRuleFlag)){
                //是否配置售卖规则
                SaleRules saleRules = saleRulesService.findByGoodsId(goodsId);
                String orderFrom = goodsOrder.getOrderFrom();
                String fromAdminCode = IOrderServiceConstant.OrderFrom.merchant_admin.getCode();
                if (null != saleRules && !fromAdminCode.equals(orderFrom)){
                    //验证规则
                    baseResp = checkSaleRules(baseResp,goodsOrder,saleRules,detailReq);
                    if (!baseResp.isSuccess()){
                        return baseResp;
                    }
                }
            }
        }else {
            baseResp.setMsg("goodsId有误");
            return baseResp;
        }
//        }
        baseResp.success();
        return baseResp;
    }

    //删除订单明细

    //修改

    //验证规则
    public BaseResp checkSaleRules(BaseResp baseResp, GoodsOrder goodsOrder , SaleRules saleRules,BaseOrderDetailReq orderDetail){
        DeliveryAddress deliveryAddress = deliveryAddressService.get(goodsOrder.getAddressId());
        String uid = goodsOrder.getUid();
        String goodsId = orderDetail.getGoodsId();
        BigDecimal goodsNum = orderDetail.getGoodsNum();
        //1 检查是否允许在该区域售卖
        boolean isAllowSale = checkAreas(saleRules, deliveryAddress);
        if (!isAllowSale) {
            baseResp.error();
            baseResp.setMsg("下单失败,下单地址超出部分商品售卖区域");
            return baseResp;
        }
        //2 检查是否未达到最大购买次数
        boolean notToMaxTimes = checkMaxBuy(saleRules, uid, goodsId, goodsNum);
        if (!notToMaxTimes) {
            baseResp.error();
            baseResp.setMsg("下单失败,已达到最大购买次数");
            return baseResp;
        }
        //3 检查购买数量是否大于起售数量
        boolean isOverMinLimitBuy = checkMinBuy(saleRules, goodsNum);
        if (!isOverMinLimitBuy) {
            baseResp.error();
            baseResp.setMsg("下单失败,商品数量少于起售数量");
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
        //配送地址在售卖地址包含的区域内则返回true
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
