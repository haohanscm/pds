package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsBuyerTopNGoodsApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiDeliveryBuyerResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiTradeSortProcessResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsTopNGoodsResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsTradeOrderCommonParamsResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.order.TradeOrderDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 交易订单Service
 * @author haohan
 * @version 2018-10-24
 */
@Service
@Transactional(readOnly = true)
public class TradeOrderService extends CrudService<TradeOrderDao, TradeOrder> {

	public TradeOrder get(String id) {
		return super.get(id);
	}
	
	public List<TradeOrder> findList(TradeOrder tradeOrder) {
		return super.findList(tradeOrder);
	}

	//获取已分拣完成准备装车的商家列表
	public List<String> findBuyerIdList(Date deliveryDate,String buySeq,String pmId){
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setDeliveryTime(deliveryDate);
		tradeOrder.setBuySeq(buySeq);
		tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
		tradeOrder.setDeliveryType(IPdsConstant.DeliveryType.home.getCode());
		tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
		tradeOrder.setPmId(pmId);
		return dao.findBuyerIdList(tradeOrder);
	}

	//获取自提单商家列表
	public List<PdsApiDeliveryBuyerResp> findSelfOrderInfoList(Date deliveryDate, String buySeq, String pmId){
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setDeliveryTime(deliveryDate);
		tradeOrder.setBuySeq(buySeq);
		tradeOrder.setPmId(pmId);
		tradeOrder.setDeliveryType(IPdsConstant.DeliveryType.self.getCode());
		tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
		tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
		tradeOrder.setPmId(pmId);
		return dao.findSelfOrderInfoList(tradeOrder);
	}

	public TradeOrder fetchByTradeOrderId(String tradeId){
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setTradeId(tradeId);
		List<TradeOrder> tradeOrderList = dao.findList(tradeOrder);
		return CollectionUtils.isEmpty(tradeOrderList)?null:tradeOrderList.get(0);
	}

	public Page<PdsTradeOrderCommonParamsResp> findSimpleTradeOrderPage(Page page,TradeOrder order){
		order.setPage(page);
		List<PdsTradeOrderCommonParamsResp> list = dao.findSimpleTradeOrderList(order);
		page.setList(list);
		return page;
	}

	public Page<TradeOrder> findPage(Page<TradeOrder> page, TradeOrder tradeOrder) {
		return super.findPage(page, tradeOrder);
	}

	//分拣进度列表
	public List<PdsApiTradeSortProcessResp> goodsSortProcessList(Date deliveryDate, String buySeq, String[] opStatus, String pmId){
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setPmId(pmId);
		tradeOrder.setDeliveryTime(deliveryDate);
		tradeOrder.setBuySeq(buySeq);
		tradeOrder.setOpStatusArry(opStatus);
		tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
		tradeOrder.setPmId(pmId);
		return dao.goodsSortProcessList(tradeOrder);
	}

	//获取分拣进度
	public Integer countProcess(TradeOrder tradeOrder){
		tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
		Integer result = dao.countProcess(tradeOrder);
		return null == result ? 0 :result;
	}

	//获取分拣进度
	public Integer countGoodsProcess(String buyId,String buySeq,String[] opStatusArry,String pmId){
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setBuyId(buyId);
		tradeOrder.setBuySeq(buySeq);
		tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
		tradeOrder.setOpStatusArry(opStatusArry);
		tradeOrder.setPmId(pmId);
		Integer result = dao.countGoodsProcess(tradeOrder);
		return null == result ? 0 :result;
	}

	//计算订单总金额
	public BigDecimal countOrderAmount(Date deliveryDate,String buySeq,String buyerId,String pmId){
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setDeliveryTime(deliveryDate);
		tradeOrder.setBuyerId(buyerId);
		tradeOrder.setBuySeq(buySeq);
		tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
		tradeOrder.setDeliveryType(IPdsConstant.DeliveryType.self.getCode());
		tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
		tradeOrder.setPmId(pmId);
		return dao.countOrderAmount(tradeOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(TradeOrder tradeOrder) {
		if(StringUtils.isEmpty(tradeOrder.getTradeId())){
			String tradeId = PdsCommonUtil.incrIdByClass(TradeOrder.class,IPdsConstant.TRADE_ORDER_SN_PRE);
			tradeOrder.setTradeId(tradeId);
		}
		super.save(tradeOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(TradeOrder tradeOrder) {
		super.delete(tradeOrder);
	}

    public Integer countFreightNum(Date date,String opStatus,String pmId) {
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setDeliveryTime(date);
		tradeOrder.setOpStatus(opStatus);
		tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
		tradeOrder.setPmId(pmId);
		Integer num = dao.countFreightNum(tradeOrder);
		return null == num?0:num;
    }

    /**
     * 修改状态 supplierStatus/transStatus/buyerStatus/opStatus/deliveryStatus
     * 根据id
     * @param tradeOrder
     * @return
     */
    @Transactional(readOnly = false)
    public int updateStatus(TradeOrder tradeOrder){
        if(StringUtils.isEmpty(tradeOrder.getId())){
            return 0;
        }
        return dao.updateStatus(tradeOrder);
    }


    // 按配送情况(配送编号/配送日期/物流车号) 修改配送状态
    @Transactional(readOnly = false)
    public int updateStatusByDelivery(DeliveryFlow deliveryFlow) {
        // TODO 添加限制 可三种条件分别查询
        if (StringUtils.isEmpty(deliveryFlow.getDeliveryId())) {
            return 0;
        }
        return dao.updateStatusByDelivery(deliveryFlow);
    }

    public List<TradeOrder> findJoinList(TradeOrder tradeOrder) {
        return dao.findJoinList(tradeOrder);
    }

	@Transactional(readOnly = false)
	public int deleteByDateSeqPmId(TradeOrder tradeOrder) {
		String pmId = tradeOrder.getPmId();
		Date deliveryDate = tradeOrder.getDeliveryTime();
		String buySeq = tradeOrder.getBuySeq();
		if (StringUtils.isAnyEmpty(pmId,buySeq) || null == deliveryDate){
			return 0;
		}
		return dao.deleteByDateSeqPmId(tradeOrder);
	}

	public List<PdsTopNGoodsResp> selectBuyerGoodsTopN(PdsBuyerTopNGoodsApiReq pdsApiBuyerTopNGoodsReq){
    	return dao.selectBuyerGoodsTopN(pdsApiBuyerTopNGoodsReq);
	}

	public Integer countBuyOrderNum(TradeOrder tradeOrder) {
		Integer result = dao.countBuyOrderNum(tradeOrder);
		return null == result ? 0 : result;
	}

	public Integer countTradeOrderNum(TradeOrder tradeOrder) {
		Integer result = dao.countTradeOrderNum(tradeOrder);
		return null == result ? 0 : result;
	}
}