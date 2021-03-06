package com.haohan.platform.service.sys.modules.xiaodian.api.service.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IDeliveryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.*;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.OrderDeliveryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/9/11
 */
@Service
public class OrderDeliveryApiService implements IDeliveryConstant {
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private OrderDeliveryService orderDeliveryService;
    @Autowired
    private GoodsGiftsService goodsGiftsService;
    @Autowired
    private DeliverServiceAreaService deliverServiceAreaService;
    @Autowired
    private DeliverManManageService deliverManManageService;
    @Autowired
    private DeliveryPlanService deliveryPlanService;
    @Autowired
    private GoodsService goodsService;

    /**
     * ??????????????????
     * @param orderPayRecord
     * @return
     */
    public OrderDelivery genDeliveryOrder(GoodsOrder goodsOrder, OrderPayRecord orderPayRecord){
        OrderDelivery orderDelivery = new OrderDelivery();
        String orderId = goodsOrder.getOrderId();
        List<GoodsOrderDetail> list = goodsOrderDetailService.findByOrderId(orderId);

        GoodsOrderDetail goodsOrderDetail = list.get(0);  //????????????????????????????????????????????????
        DeliveryAddress deliveryAddress = deliveryAddressService.get(goodsOrder.getAddressId());

        String extAddress = null;
        String id = IdGen.genByT(OrderDelivery.class);
        if (null != deliveryAddress){
            BeanUtils.copyProperties(deliveryAddress,orderDelivery);
            extAddress = deliveryAddress.getExtAddress();
            orderDelivery.setAddress(deliveryAddress.getReceiveAddress());
            orderDelivery.setCommunityName(deliveryAddress.getCommunityName());
//            orderDelivery.setMemberAddressId(deliveryAddress.getId());
        }
        BeanUtils.copyProperties(goodsOrder,orderDelivery);
//        BeanUtils.copyProperties(goodsOrderDetail,orderDelivery);

//        orderDelivery.setDeliveryType(goodsOrder.getDeliveryType());
        orderDelivery.setStartDeliveryDate(goodsOrderDetail.getDeliveryStartDate());
        orderDelivery.setArriveType(goodsOrderDetail.getArriveType());

        orderDelivery.setMoneyPaid(goodsOrder.getOrderAmount());
        orderDelivery.setDeliveryStatus(OrderDeliveryStatus.wait.getCode());
        orderDelivery.setUserId(goodsOrder.getUid());
//        orderDelivery.setShopId(goodsOrder.getShopId());
//        orderDelivery.setOrderType(goodsOrder.getOrderType());
        orderDelivery.setOrderMark(goodsOrder.getOrderMarks());
        orderDelivery.setPayType(BankServiceType.valueOfServiceType(orderPayRecord.getPayType()).getName());
        orderDelivery.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
        orderDelivery.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_SHIP.getCode());
        orderDelivery.setPlanGenStatus(PlanGenStatus.not_gen.getCode());
        orderDelivery.setId(id);
        orderDelivery.setIsNewRecord(true);

        if (StringUtils.isNotBlank(extAddress)){
            int indexA = extAddress.indexOf("???");
            int indexB = extAddress.indexOf("???");
            int indexC = extAddress.indexOf("???");
            orderDelivery.setBuildingsNum(extAddress.substring(0,indexA));
            orderDelivery.setFloor(extAddress.substring(indexA+1,indexB));
            orderDelivery.setHouseNum(extAddress.substring(indexB+1,indexC));
        }
         orderDeliveryService.save(orderDelivery);
        return orderDelivery;
    }

    /**
     * ????????????
     * @param goodsOrder
     * @param details
     * @param orderDelivery
     * @param isFirst
     * @return
     */
    public boolean formDeliveryPlan(GoodsOrder goodsOrder,List<GoodsOrderDetail> details,OrderDelivery orderDelivery,boolean isFirst){
        try {
            BaseResp baseResp = BaseResp.newError();
            DeliverManManage deliverManManage = fetchDeliverMan(baseResp,goodsOrder,orderDelivery);
            if (!baseResp.isSuccess()){
                orderDelivery.setPlanGenStatus(PlanGenStatus.gen_fail.getCode());
                orderDelivery.setPlanGenDesc(baseResp.getMsg());
                return false;
            }
            for (GoodsOrderDetail goodsOrderDetail : details) {
                GoodsGifts goodsGifts = null ;
                String deliveryPlanType = goodsOrderDetail.getDeliveryPlanType();
                if (isFirst){
                    goodsGifts = goodsGiftsService.findByGoodsId(goodsOrderDetail.getGoodsId());
                }
                if (StringUtils.equals(IDeliveryConstant.DeliveryPlanType.once.getCode(), deliveryPlanType)
                        || StringUtils.equals(IDeliveryConstant.DeliveryPlanType.specific.getCode(), deliveryPlanType)) {
                    //?????????????????????????????????
                    DeliveryPlan deliveryPlan = new DeliveryPlan();
                    //???????????????????????????
                    baseResp = baseParamsSet(goodsOrderDetail, goodsOrder, deliveryPlan,orderDelivery,deliverManManage);
                    if (!baseResp.isSuccess()){
                        //??????????????????????????????
                        orderDelivery.setPlanGenStatus(PlanGenStatus.gen_fail.getCode());
                        orderDelivery.setPlanGenDesc(baseResp.getMsg());
                        return false;
                    }
                    //?????????????????????
                    deliveryPlan.setTheDay(goodsOrderDetail.getDeliveryStartDate());
                    deliveryPlan.setServiceContent(goodsOrderDetail.getServiceDetail());
                    deliveryPlan.setGiftName(goodsOrderDetail.getGiftName());
                    deliveryPlan.setGiftNum(goodsOrderDetail.getGiftNum());
                    //????????????
                    if (null != goodsGifts) {
                        deliveryPlan.setGiftUrl(goodsGiftsService.findByGoodsId(goodsOrderDetail.getGoodsId()).getGiftUrl());
                    }
                    deliveryPlanService.save(deliveryPlan);

                } else if (StringUtils.equals(IDeliveryConstant.DeliveryType.home_delivery.getCode(), goodsOrder.getDeliveryType())
                        && StringUtils.equals(IDeliveryConstant.DeliveryPlanType.cyclical.getCode(), goodsOrderDetail.getDeliveryPlanType())) {
                    //????????????&&???????????????
                    int times = 0;
                    int totalNum = goodsOrderDetail.getDeliveryTotalNum();
                    int deliveryNum = goodsOrderDetail.getDeliveryNum();
                    //???????????????????????????
                    times = totalNum / deliveryNum;
                    int remainder = totalNum % deliveryNum;
                    if (0 != remainder) {
                        times += 1;
                    }

                    //?????????????????????
                    DeliveryPlan deliveryPlan = new DeliveryPlan();
                    baseResp = baseParamsSet(goodsOrderDetail, goodsOrder, deliveryPlan,orderDelivery,deliverManManage);
                    if (!baseResp.isSuccess()){
                        orderDelivery.setPlanGenStatus(PlanGenStatus.gen_fail.getCode());
                        orderDelivery.setPlanGenDesc(baseResp.getMsg());
                        return false;
                    }
                    deliveryPlan.setTheDay(goodsOrderDetail.getDeliveryStartDate());
                    deliveryPlan.setServiceContent(goodsOrderDetail.getServiceDetail());
                    deliveryPlan.setGiftName(goodsOrderDetail.getGiftName());
                    deliveryPlan.setGiftNum(goodsOrderDetail.getGiftNum());
                    if (null != goodsGifts) {
                        deliveryPlan.setGiftUrl(goodsGiftsService.findByGoodsId(goodsOrderDetail.getGoodsId()).getGiftUrl());
                    }
                    deliveryPlanService.save(deliveryPlan);
                    //?????????????????????
                    deliveryPlan.setGiftName(null);
                    deliveryPlan.setGiftNum(null);
                    deliveryPlan.setGiftUrl(null);

                    //??????????????????
                    List<Date> dateList = calDeliveryDateList(goodsOrderDetail.getDeliveryStartDate(), goodsOrderDetail.getDeliverySchedule(), times);
                    if (CollectionUtils.isNotEmpty(dateList)) {
                        //??????????????????
                        for (int i = 0; i < times - 1; i++) {
                            deliveryPlan.setId(IdGen.genByT(DeliveryPlan.class));
                            deliveryPlan.setIsNewRecord(true);
                            deliveryPlan.setTheDay(dateList.get(i));
                            if (i == times -1 && 0 != remainder){
                                //?????????????????????,????????????????????????????????????????????????
                                deliveryPlan.setGoodsNum(remainder);
                            }
                            deliveryPlanService.save(deliveryPlan);
                        }
                    }
                }
            }
            orderDelivery.setDeliveryStatus(IDeliveryConstant.OrderDeliveryStatus.doing.getCode());
            orderDelivery.setPlanGenStatus(PlanGenStatus.finished.getCode());
            orderDelivery.setPlanGenDesc("??????????????????");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            orderDelivery.setPlanGenStatus(PlanGenStatus.gen_fail.getCode());
            orderDelivery.setPlanGenDesc("????????????,??????????????????");
            return false;
        } finally {
            orderDeliveryService.save(orderDelivery);
        }

    }


    /**
     * ?????????????????????????????????
     *
     * @param goodsOrderDetail
     * @param goodsOrder
     * @param deliveryPlan
     */
    private BaseResp baseParamsSet(GoodsOrderDetail goodsOrderDetail, GoodsOrder goodsOrder, DeliveryPlan deliveryPlan ,OrderDelivery orderDelivery,DeliverManManage deliverManManage) throws Exception{
        BaseResp baseResp = BaseResp.newError();
        Goods goods = goodsService.fetchByJsGoodsId(goodsOrderDetail.getGoodsId());
        DeliveryAddress deliveryAddress = deliveryAddressService.get(goodsOrder.getAddressId());

        //????????????????????????
        deliveryPlan.setMerchantId(goodsOrderDetail.getMerchantId());
        deliveryPlan.setMerchantName(goodsOrder.getMerchantName());
        deliveryPlan.setShopId(goodsOrder.getShopId());
        deliveryPlan.setShopName(goodsOrder.getShopName());
        deliveryPlan.setOrderId(goodsOrder.getOrderId());
        deliveryPlan.setPayStatus(goodsOrder.getPayStatus());
        deliveryPlan.setOrderRemark(goodsOrder.getOrderMarks());
        deliveryPlan.setGoodsId(goodsOrderDetail.getGoodsId());
        deliveryPlan.setGoodsName(goodsOrderDetail.getGoodsName());
        deliveryPlan.setGoodsNum(goodsOrderDetail.getDeliveryNum());
        if (null != goods) {
            deliveryPlan.setGoodsUrl(goods.getThumbUrl());
        }
        deliveryPlan.setGoodsUnit(goodsOrderDetail.getGoodsUnit());
        //???????????? TODO
        deliveryPlan.setGoodsInfo(goodsOrderDetail.getModelName());
        deliveryPlan.setGiftName(goodsOrderDetail.getGiftName());
        deliveryPlan.setGiftNum(goodsOrderDetail.getGiftNum());
        //??????????????????
        deliveryPlan.setAddress(orderDelivery.getAddress());
        deliveryPlan.setProvince(orderDelivery.getProvince());
        deliveryPlan.setCity(orderDelivery.getCity());
        deliveryPlan.setArea(orderDelivery.getRegion());
        deliveryPlan.setStreet(orderDelivery.getStreet());
        deliveryPlan.setDistrictArea(orderDelivery.getDistrictArea());
        deliveryPlan.setCommunityId(orderDelivery.getCommunityId());
        deliveryPlan.setCommunityName(orderDelivery.getCommunityName());
        deliveryPlan.setBuildingsNum(orderDelivery.getBuildingsNum());
        deliveryPlan.setFloor(orderDelivery.getFloor());
        deliveryPlan.setHouseNum(orderDelivery.getHouseNum());

        //??????????????????
        deliveryPlan.setMemberId(goodsOrder.getUid());
        deliveryPlan.setMemberName(goodsOrder.getUserName());
        deliveryPlan.setMemberContact(deliveryAddress.getReceiverMobile());
        deliveryPlan.setStatus(IDeliveryConstant.DeliveryStatus.wait.getCode());

        //?????????????????????
        orderDelivery.setDeliveryManId(deliverManManage.getId());
        orderDelivery.setDeliveryManName(deliverManManage.getRealName());
        orderDelivery.setDeliveryManTel(deliverManManage.getMobile());
        orderDeliveryService.save(orderDelivery);
        deliveryPlan.setDeliveryManId(deliverManManage.getId());
        deliveryPlan.setDeliveryManName(deliverManManage.getRealName());
        deliveryPlan.setDeliverManTel(deliverManManage.getMobile());
        deliveryPlan.setDeliveryOrderId(orderDelivery.getId());
        baseResp.success();
        return baseResp;
    }

    /**
     * ?????????????????????????????????
     *
     * @param start
     * @param deliverySchedule
     * @param totalTimes
     * @return
     */
    private List<Date> calDeliveryDateList(Date start, String deliverySchedule, int totalTimes) {
        IDeliveryConstant.DeliverySchedule schedule = IDeliveryConstant.DeliverySchedule.valueOfScheduleCode(deliverySchedule);
        List<Date> dateList = new ArrayList<>();
        for (int i = 0; i < totalTimes - 1; i++) {
            int offset = 0;
            Date nextDate = null;
            switch (schedule) {
                case everyday:
                    offset = 1;
                    nextDate = DateUtils.getOffsetDate(start, offset);
                    start = nextDate;
                    break;
                case interval_one:
                    offset = 2;
                    nextDate = DateUtils.getOffsetDate(start, offset);
                    start = nextDate;
                    break;
                case interval_two:
                    offset = 3;
                    nextDate = DateUtils.getOffsetDate(start, offset);
                    start = nextDate;
                    break;
                case interval_three:
                    offset = 4;
                    nextDate = DateUtils.getOffsetDate(start, offset);
                    start = nextDate;
                    break;
                case workday:
                    offset = 1;
                    nextDate = DateUtils.getOffsetDate(start, offset);
                    //??????nextDate????????????????????????,nextDate+1???
                    int dayOfWeek = DateUtils.getDayForWeek(nextDate);
                    if (dayOfWeek == 6) {
                        nextDate = DateUtils.getOffsetDate(nextDate, 2);
                    }
                    if (dayOfWeek == 7) {
                        nextDate = DateUtils.getOffsetDate(nextDate, 1);
                    }
                    start = nextDate;
                    break;
                case everyweek:
                    offset = 7;
                    nextDate = DateUtils.getOffsetDate(start, offset);
                    start = nextDate;
                    break;
                case everymonth:
                    //????????????????????? TODO

            }
            dateList.add(nextDate);
        }
        return dateList;
    }

    /**
     * ???????????????
     * @param baseResp
     * @param goodsOrder
     * @param orderDelivery
     * @return
     */
    private DeliverManManage fetchDeliverMan(BaseResp baseResp,GoodsOrder goodsOrder,OrderDelivery orderDelivery){
        //???????????????
        DeliverManManage deliverManManage = null;
        DeliverServiceArea deliverServiceArea = new DeliverServiceArea();
        deliverServiceArea.setMerchantId(goodsOrder.getMerchantId());
        deliverServiceArea.setShopId(goodsOrder.getShopId());
        deliverServiceArea.setCommunityId(orderDelivery.getCommunityId());
        List<DeliverServiceArea> deliverServiceAreas = deliverServiceAreaService.findList(deliverServiceArea);
        if (CollectionUtils.isEmpty(deliverServiceAreas)){
            baseResp.error();
            baseResp.setMsg("????????????????????????,??????????????????????????????");
            return deliverManManage;
        }
        //???????????????????????????
        List<DeliverManManage> deliveryManList = deliverServiceAreas.stream().map(areas -> deliverManManageService.get(areas.getDeliverManId()))
                .filter(deliverMan -> StringUtils.equals(IMerchantConstant.available,deliverMan.getStatus())).collect(Collectors.toCollection(ArrayList::new));
        //?????????????????????
        if (CollectionUtils.isEmpty(deliveryManList)){
            baseResp.error();
            baseResp.setMsg("??????????????????");
            return deliverManManage;
        }
        Random random = new Random();
        int randomInt = random.nextInt(deliveryManList.size());
        deliverManManage = deliveryManList.get(randomInt);
        baseResp.success();
        return deliverManManage;
    }
}
