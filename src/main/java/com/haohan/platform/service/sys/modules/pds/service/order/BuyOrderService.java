package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiOrderAnalyzeReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisCurveReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisOverViewReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.*;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.order.BuyOrderDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 采购单Service
 * @author haohan
 * @version 2018-10-22
 */
@Service
@Transactional(readOnly = true)
public class BuyOrderService extends CrudService<BuyOrderDao, BuyOrder> {

	@Override
    public BuyOrder get(String id) {
		return super.get(id);
	}
	
	@Override
    public List<BuyOrder> findList(BuyOrder buyOrder) {
		return super.findList(buyOrder);
	}

    /**
     * 获取所有商品均已报价的采购单列表
     * @param buyOrder
     * @return
     */
	public List<BuyOrder> findOfferedList(BuyOrder buyOrder){
		return dao.findOfferedList(buyOrder);
	}

	public PdsApiSumBuyOrderResp findAdmSumList(String buyOrderId){
		return dao.findAdmSumList(buyOrderId);
	}

	public BuyOrder fetchByBuyId(String buyId){
		BuyOrder buyOrder = new BuyOrder();
		buyOrder.setBuyId(buyId);
		List<BuyOrder> buyOrderList = dao.findList(buyOrder);
		return CollectionUtils.isEmpty(buyOrderList)?null:buyOrderList.get(0);
	}
	
	@Override
    public Page<BuyOrder> findPage(Page<BuyOrder> page, BuyOrder buyOrder) {
		return super.findPage(page, buyOrder);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void save(BuyOrder buyOrder) {
		if(StringUtils.isEmpty(buyOrder.getBuyId())){
            String buyId = PdsCommonUtil.incrIdByClass(BuyOrder.class, IPdsConstant.BUY_ORDER_SN_PRE);
            buyOrder.setBuyId(buyId);
        }
		super.save(buyOrder);
	}
	
	@Override
    @Transactional(readOnly = false)
	public void delete(BuyOrder buyOrder) {
		super.delete(buyOrder);
	}

    /**
     * 部分属性修改   totalPrice/status
     * 未传入值不修改
     * @param buyOrder id
     */
    @Transactional(readOnly = false)
    public void updatePart(BuyOrder buyOrder) {
        buyOrder.preUpdate();
        dao.updatePart(buyOrder);
    }
    /**
     * 用于列表展示
     * @param buyOrder
     * @return
     */
    public List<BuyOrder> findJoinList(BuyOrder buyOrder) {
        return dao.findJoinList(buyOrder);
    }

    public int countOrderNum(PdsApiStatisOverViewReq apiStatisticalReq){
		return dao.countOrderNum(apiStatisticalReq);
	}

	public BigDecimal summarySaleAmount(PdsApiStatisOverViewReq apiStatisticalReq) {
		BigDecimal result = dao.summarySaleAmount(apiStatisticalReq);
		return null == result ? BigDecimal.ZERO : result;
	}

	public List<PdsApiStatisCurveResp> statisCurvePast(PdsApiStatisCurveReq statisticalReq) {
		return dao.statisCurvePast(statisticalReq);
	}


	public Integer countPayOrderNum(String pmId, ICommonConstant.YesNoType payStatus) {
		return dao.countPayOrderNum(pmId,payStatus.getCode());
	}

	public PdsApiOrderAnalyzeResp orderAnalyze(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
		return dao.orderAnalyze(orderAnalyzeReq);
	}

	public List<PdsApiBuyerSaleTopResp> buyerSaleTop(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
		return dao.buyerSaleTop(orderAnalyzeReq);
	}


	public PdsApiBriefReportResp briefReport(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
		return dao.briefReport(orderAnalyzeReq);
	}

    /**
     * 按采购商的商家分组  用于查看下单的商家
     * @param buyOrder  pmId/deliveryTime/buySeq   可选merchantId/status
     * @return merchantId/merchantName/deliveryTime/buySeq/buyId/genPrice
     */
    public List<BuyOrder> findListGroupByMerchant(BuyOrder buyOrder) {
        return dao.findListGroupByMerchant(buyOrder);
    }

	public BuyOrder fetchByGoodsOrderId(String orderId) {
		BuyOrder buyOrder = new BuyOrder();
		buyOrder.setGoodsOrderId(orderId);
		List<BuyOrder> buyOrderList = super.findList(buyOrder);
		return CollectionUtils.isEmpty(buyOrderList) ? null : buyOrderList.get(0);
	}
}