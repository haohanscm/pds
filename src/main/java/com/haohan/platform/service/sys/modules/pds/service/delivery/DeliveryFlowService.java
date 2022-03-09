package com.haohan.platform.service.sys.modules.pds.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsShipOrderApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.delivery.DeliveryFlowDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 物流配送Service
 * @author haohan
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly = true)
public class DeliveryFlowService extends CrudService<DeliveryFlowDao, DeliveryFlow> {

	public DeliveryFlow get(String id) {
		return super.get(id);
	}
	
	public List<DeliveryFlow> findList(DeliveryFlow deliveryFlow) {
		return super.findList(deliveryFlow);
	}

	public DeliveryFlow fetchByDateSeqDriver(PdsShipOrderApiReq shipOrderReq, IPdsConstant.DeliveryStatus status){
		DeliveryFlow deliveryFlow = new DeliveryFlow();
		deliveryFlow.setDeliveryDate(shipOrderReq.getDeliveryDate());
		deliveryFlow.setDeliverySeq(shipOrderReq.getBuySeq());
		deliveryFlow.setDriver(shipOrderReq.getDriverId());
		deliveryFlow.setPmId(shipOrderReq.getPmId());
//		deliveryFlow.setBuyerId(shipOrderReq.getBuyerId());
		if (null != status){
			deliveryFlow.setStatus(status.getCode());
		}
		List<DeliveryFlow> list = dao.findList(deliveryFlow);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	public List<TruckManage> findPermissionTrucks(Date deliveryDate,String buySeq){
		DeliveryFlow deliveryFlow = new DeliveryFlow();
		deliveryFlow.setDeliverySeq(buySeq);
		deliveryFlow.setDeliveryDate(deliveryDate);
		return dao.findPermissionTrucks(deliveryFlow);
	}

	//获取车辆配送的采购单号列表

	public Page<DeliveryFlow> findPage(Page<DeliveryFlow> page, DeliveryFlow deliveryFlow) {
		return super.findPage(page, deliveryFlow);
	}
	
	@Transactional(readOnly = false)
	public void save(DeliveryFlow deliveryFlow) {
		if(StringUtils.isEmpty(deliveryFlow.getDeliveryId())){
			String deliveryId = PdsCommonUtil.incrIdByClass(DeliveryFlow.class, IPdsConstant.DELIVERY_FLOW_SN_PR);
			deliveryFlow.setDeliveryId(deliveryId);
		}
		super.save(deliveryFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeliveryFlow deliveryFlow) {
		super.delete(deliveryFlow);
	}

    public List<DeliveryFlow> findJoinList(DeliveryFlow deliveryFlow) {
        return dao.findJoinList(deliveryFlow);
    }

	@Transactional(readOnly = false)
	public int deleteByDateSeqPmId(DeliveryFlow deliveryFlow) {
		String pmId = deliveryFlow.getPmId();
		Date deliveryDate = deliveryFlow.getDeliveryDate();
		String buySeq = deliveryFlow.getDeliverySeq();
		if (StringUtils.isAnyEmpty(pmId,buySeq) || null == deliveryDate){
			return 0;
		}
		int rows = dao.deleteByDateSeqPmId(deliveryFlow);
		return rows;
	}
}