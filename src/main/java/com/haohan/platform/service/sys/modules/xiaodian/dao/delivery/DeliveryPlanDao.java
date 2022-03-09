package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.DeliveryQueryParams;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp.*;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryPlan;

import java.util.List;

/**
 * 配送计划DAO接口
 * @author yu.shen
 * @version 2018-08-31
 */
@MyBatisDao
public interface DeliveryPlanDao extends CrudDao<DeliveryPlan> {

    // 必传 配送员 uid
    List<MemberResp> findMemberByUid(DeliveryQueryParams params);

    // 必传 deliveryPlanId、uid
    DeliveryPlan getWithUid(DeliveryQueryParams params);

    // 必传 配送员 uid
    SummaryCountResp querySummaryCount(DeliveryQueryParams params);

    // 必传 配送员 uid
    List<CommunityCountResp> queryCommunityCount(DeliveryQueryParams params);

    // 必传 配送员 uid
    List<GoodsCountResp> queryGoodsCount(DeliveryQueryParams params);

    // 必传 配送员 uid
    List<GiftCountResp> queryGiftCount(DeliveryQueryParams params);

    // 必传 配送员 uid
    List<MemberCountResp> queryMemberCount(DeliveryQueryParams params);

    // 必传 配送员 uid
    List<PlanDetailWithMemberResp> queryPlanDetail(DeliveryQueryParams params);

    // 必传 配送员 uid
    List<BuildingDeliveryResp> queryCommunityDelivery(DeliveryQueryParams params);

    // 检查订单配送状态   0未配送 1配送中 2已配送
    PlanDetailResp checkOrderStatus(DeliveryPlan deliveryPlan);

    //批量删除
    Integer deleteBatch(DeliveryPlan deliveryPlan);

    Integer statsGoodsNumHaveFinished(DeliveryPlan deliveryPlan);

    Integer countTimes(DeliveryPlan deliveryPlan);
}