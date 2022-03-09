package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.DeliveryQueryParams;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp.*;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.DeliveryPlanDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverManManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配送计划Service
 * @author yu.shen
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class DeliveryPlanService extends CrudService<DeliveryPlanDao, DeliveryPlan> {
	@Autowired
	private DeliverManManageService deliverManManageService;

	public DeliveryPlan get(String id) {
		return super.get(id);
	}
	
	public List<DeliveryPlan> findList(DeliveryPlan deliveryPlan) {
		return super.findList(deliveryPlan);
	}
	
	public Page<DeliveryPlan> findPage(Page<DeliveryPlan> page, DeliveryPlan deliveryPlan) {
		return super.findPage(page, deliveryPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(DeliveryPlan deliveryPlan) {
		super.save(deliveryPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeliveryPlan deliveryPlan) {
		super.delete(deliveryPlan);
	}

	// orderId /deliveryOrderId 检查订单配送状态  0未配送 1配送中 2已配送
	public PlanDetailResp checkOrderStatus(String orderId, String deliveryOrderId){
	    DeliveryPlan deliveryPlan = new DeliveryPlan();
	    deliveryPlan.setOrderId(orderId);
	    deliveryPlan.setDeliveryOrderId(deliveryOrderId);
	    return dao.checkOrderStatus(deliveryPlan);
    }

	// 必传  deliveryPlanId、uid   找到配送计划
	public DeliveryPlan getWithUid(DeliveryQueryParams params){
	    return dao.getWithUid(params);
    }
    // 必传 配送员 uid   获取 会员列表
	public List<MemberResp> findMemberByUid(DeliveryQueryParams params){
	    return dao.findMemberByUid(params);
    }

    // 必传 配送员 uid  配送情况汇总数据
    public SummaryCountResp querySummaryCount(DeliveryQueryParams params) {
	    return dao.querySummaryCount(params);
    }

    // 必传 配送员 uid   按小区统计配送情况
    public List<CommunityCountResp> queryCommunityCount(DeliveryQueryParams params) {
        return dao.queryCommunityCount(params);
    }

    // 必传 配送员 uid   统计商品 配送数量
	public ProductCountResp queryProductCount(DeliveryQueryParams params) {
	    ProductCountResp productCount = new ProductCountResp();
	    productCount.setGoodsList(dao.queryGoodsCount(params));
	    productCount.setGiftList(dao.queryGiftCount(params));
		return productCount;
	}

    // 必传 配送员 uid   按会员统计 配送数量
    public List<MemberCountResp> queryMemberCount(DeliveryQueryParams params) {
        return dao.queryMemberCount(params);
    }

    // 配送计划详细信息  必传 配送员 uid
    public List<PlanDetailWithMemberResp> queryPlanDetail(DeliveryQueryParams params) {
        return dao.queryPlanDetail(params);
    }

    // 配送计划详细信息  必传 配送员 uid  分页
    public Page<PlanDetailWithMemberResp> queryPlanDetailPage(Page page, DeliveryQueryParams params) {
		params.setPage(page);
		List<PlanDetailWithMemberResp> list = dao.queryPlanDetail(params);
		page.setList(list);
        return page;
    }

    // 查询小区拥有订单配送信息 必传 配送员 uid
    public List<BuildingDeliveryResp> queryCommunityDelivery(DeliveryQueryParams params) {
        return dao.queryCommunityDelivery(params);
    }

    //替换配送员信息
	@Transactional(readOnly = false)
	public DeliveryPlan replaceDeliveryMan(DeliveryPlan deliveryPlan, DeliverManManage deliverManManage){
//		DeliverManManage deliverManManage = deliverManManageService.get(deliverManId);
		deliveryPlan.setDeliveryManId(deliverManManage.getId());
		deliveryPlan.setDeliverManTel(deliverManManage.getMobile());
		deliveryPlan.setDeliveryManName(deliverManManage.getRealName());
		super.save(deliveryPlan);
		return deliveryPlan;
	}

	//批量删除
	@Transactional(readOnly = false)
	public int deleteBatch(DeliveryPlan deliveryPlan){
		if (StringUtils.isEmpty(deliveryPlan.getOrderId())){
			throw new RuntimeException("Dangerous ! The \"order_id\" is necessary!");
		} else {
			return dao.deleteBatch(deliveryPlan);
		}
	}

	public Integer statsGoodsNumHaveFinished(DeliveryPlan deliveryPlan){
		Integer finishedNum = dao.statsGoodsNumHaveFinished(deliveryPlan);
		return null == finishedNum ? 0 : finishedNum;
	}

	public Integer countTimes(DeliveryPlan deliveryPlan){
		Integer times = dao.countTimes(deliveryPlan);
		return null==times?0:times;
	}
}