package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery.PdsShipBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsTradeOrderCommonParamsResp;
import com.haohan.platform.service.sys.modules.pds.dao.order.PdsShipOrderDetailDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrderDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 送货单明细Service
 * @author yu.shen
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class PdsShipOrderDetailService extends CrudService<PdsShipOrderDetailDao, PdsShipOrderDetail> {

	public PdsShipOrderDetail get(String id) {
		return super.get(id);
	}
	
	public List<PdsShipOrderDetail> findList(PdsShipOrderDetail pdsShipOrderDetail) {
		return super.findList(pdsShipOrderDetail);
	}

	public List<PdsShipOrderDetail> findByShipIdList(String shipId) {
		PdsShipOrderDetail pdsShipOrderDetail = new PdsShipOrderDetail();
		pdsShipOrderDetail.setShipId(shipId);
		return super.findList(pdsShipOrderDetail);
	}

	public Page findTruckShipDetailList(Page reqPage, PdsShipBuyerApiReq shipBuyerReq){
		DeliveryFlow deliveryFlow = new DeliveryFlow();
		deliveryFlow.setDeliveryDate(shipBuyerReq.getDeliveryDate());
		deliveryFlow.setDeliverySeq(shipBuyerReq.getBuySeq());
		deliveryFlow.setBuyerId(shipBuyerReq.getBuyerId());
		deliveryFlow.setDriver(shipBuyerReq.getDriverId());
		deliveryFlow.setPmId(shipBuyerReq.getPmId());
		deliveryFlow.setPage(reqPage);
		List<PdsTradeOrderCommonParamsResp> list = dao.findTruckShipDetailList(deliveryFlow);
		reqPage.setList(list);
		return reqPage;
	}


	public BigDecimal countShipOrderAmount(Date deliveryDate,String buySeq,String driver,String buyerId,String pmId){
		DeliveryFlow deliveryFlow = new DeliveryFlow();
		deliveryFlow.setDeliveryDate(deliveryDate);
		deliveryFlow.setDeliverySeq(buySeq);
		deliveryFlow.setDriver(driver);
		deliveryFlow.setBuyerId(buyerId);
		deliveryFlow.setPmId(pmId);
		BigDecimal amount = dao.countShipOrderAmount(deliveryFlow);
		return null == amount ? BigDecimal.ZERO : amount;
	}

	public Integer countDriverShipGoods(Date deliveryDate,String buySeq,String driver,String buyerId){
		DeliveryFlow deliveryFlow = new DeliveryFlow();
		deliveryFlow.setDeliveryDate(deliveryDate);
		deliveryFlow.setDeliverySeq(buySeq);
		deliveryFlow.setDriver(driver);
		deliveryFlow.setBuyerId(buyerId);
		Integer goodsNum = dao.countDriverShipGoods(deliveryFlow);
		return null == goodsNum ? 0 : goodsNum;
	}
	
	public Page<PdsShipOrderDetail> findPage(Page<PdsShipOrderDetail> page, PdsShipOrderDetail pdsShipOrderDetail) {
		return super.findPage(page, pdsShipOrderDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(PdsShipOrderDetail pdsShipOrderDetail) {
		super.save(pdsShipOrderDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(PdsShipOrderDetail pdsShipOrderDetail) {
		super.delete(pdsShipOrderDetail);
	}

    public List<PdsShipOrderDetail> findJoinList(PdsShipOrderDetail pdsShipOrderDetail) {
        return dao.findJoinList(pdsShipOrderDetail);
    }

	public int deleteByShipOrderId(String shipId) {
		return dao.deleteByShipOrderId(shipId);
	}
}