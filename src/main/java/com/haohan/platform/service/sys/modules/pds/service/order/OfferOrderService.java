package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiOfferOrderResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.order.OfferOrderDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.entity.req.PdsOfferOrderReq;
import com.haohan.platform.service.sys.modules.pds.entity.resp.OfferOrderResp;
import com.haohan.platform.service.sys.modules.pds.entity.resp.PdsGoodsListParams;
import com.haohan.platform.service.sys.modules.pds.entity.resp.PdsSupListParams;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 报价单Service
 * @author haohan
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class OfferOrderService extends CrudService<OfferOrderDao, OfferOrder> {

	public OfferOrder get(String id) {
		return super.get(id);
	}
	
	public List<OfferOrder> findList(OfferOrder offerOrder) {
		return super.findList(offerOrder);
	}

	public Page<PdsApiOfferOrderResp> findAdmRespPage(Page<PdsApiOfferOrderResp> page, PdsApiOfferOrderResp pdsApiOfferOrderResp){
		pdsApiOfferOrderResp.setPage(page);
		page.setList(dao.findAdmRespPage(pdsApiOfferOrderResp));
		return page;
	}

	public List<OfferOrder> findByAskId(String askOrderId){
		OfferOrder offerOrder = new OfferOrder();
		offerOrder.setAskOrderId(askOrderId);
		return dao.findList(offerOrder);
	}

	public OfferOrder fetchByOrderId(String offerOrderId){
		OfferOrder offerOrder = new OfferOrder();
		offerOrder.setOfferOrderId(offerOrderId);
		List<OfferOrder> offerOrderList = dao.findList(offerOrder);
		return CollectionUtils.isEmpty(offerOrderList)?null:offerOrderList.get(0);
	}


	public Page<OfferOrder> findPage(Page<OfferOrder> page, OfferOrder offerOrder) {
		return super.findPage(page, offerOrder);
	}

    /**
     * 筛选当天备货中,待揽货状态报价单的供应商 (已中标)
     */
	public List<PdsSupListParams> findSupList(PdsOfferOrderReq pdsOfferOrderReq){
		return dao.findSupList(pdsOfferOrderReq);
	}

	public Integer countCategoryNum(PdsOfferOrderReq pdsOfferOrderReq){
		Integer num = dao.countCategoryNum(pdsOfferOrderReq);
		return null == num?0:num;
	}

	public List<PdsGoodsListParams> findSupGoodsList(Date deliveryDate,String buySeq,String supplierId,String shipStatus){
		PdsOfferOrderReq pdsOfferOrderReq = new PdsOfferOrderReq();
		pdsOfferOrderReq.setDeliveryDate(deliveryDate);
		pdsOfferOrderReq.setBuySeq(buySeq);
		pdsOfferOrderReq.setSupplierId(supplierId);
		pdsOfferOrderReq.setShipStatus(shipStatus);
		pdsOfferOrderReq.setStatus(IPdsConstant.OfferOrderStatus.success.getCode());
		return dao.findSupGoodsList(pdsOfferOrderReq);
	}

	public Integer countGoodsNum(PdsOfferOrderReq pdsOfferOrderReq){
		Integer num = dao.countGoodsNum(pdsOfferOrderReq);
		return null == num?0:num;
	}

	public BigDecimal countSupplyAvgPrice(String summaryOrderId){
		BigDecimal avgPrice = dao.countSupplyAvgPrice(summaryOrderId);
		return null == avgPrice?new BigDecimal(0):avgPrice;
	}
	
	@Transactional(readOnly = false)
	public void save(OfferOrder offerOrder) {
        // 创建 报价单号
        if (StringUtils.isEmpty(offerOrder.getOfferOrderId())) {
            offerOrder.setOfferOrderId(PdsCommonUtil.incrIdByClass(OfferOrder.class, IPdsConstant.OFFER_ORDER_SN_PRE));
        }
		super.save(offerOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(OfferOrder offerOrder) {
		super.delete(offerOrder);
	}

//    /**
//     * TODO 供应商 查询指定时间批次的报价单  参数 buySeq/deliveryTime
//      */
//	public List<OfferOrder> findListBySeq(OfferOrder offerOrder) {
//		return null;
//	}
//
//    /**
//     * TODO 查询报价单信息  包含采购单汇总 的商品信息  参数 buySeq/deliveryTime
//     * @param offerOrder
//     * @return
//     */
//    public List<OfferOrderResp> findListWithGoods(OfferOrder offerOrder) {
//        return null;
//    }

    /**
     * 用于列表展示
     * @param offerOrder
     * @return
     */
    public List<OfferOrder> findJoinList(OfferOrder offerOrder) {
        return dao.findJoinList(offerOrder);
    }

    @Transactional(readOnly = false)
	public boolean deleteByDateSeqPmId(OfferOrder offerOrder) {
		String pmId = offerOrder.getPmId();
		Date deliveryDate = offerOrder.getPrepareDate();
		String buySeq = offerOrder.getBuySeq();
		if (StringUtils.isAnyEmpty(pmId,buySeq) || null == deliveryDate){
			return false;		//删除失败返回false
		}
		dao.deleteByDateSeqPmId(offerOrder);
		return true;		//删除成功
	}
}