package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.order.PdsShipOrderDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrder;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 送货单Service
 * @author yu.shen
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class PdsShipOrderService extends CrudService<PdsShipOrderDao, PdsShipOrder> {

	public PdsShipOrder get(String id) {
		return super.get(id);
	}
	
	public List<PdsShipOrder> findList(PdsShipOrder pdsShipOrder) {
		return super.findList(pdsShipOrder);
	}

	public PdsShipOrder fetchByShipId(String shipId){
		PdsShipOrder pdsShipOrder = new PdsShipOrder();
		pdsShipOrder.setShipId(shipId);
		List<PdsShipOrder> shipOrderList = super.findList(pdsShipOrder);
		return CollectionUtils.isEmpty(shipOrderList) ? null : shipOrderList.get(0);
	}

	//获取送货单列表
	public Page findShipOrderInfoList(DeliveryFlow deliveryFlow,Page page) {
		deliveryFlow.setPage(page);
		page.setList(dao.findShipOrderInfoList(deliveryFlow));
		return page;
	}

	public Page<PdsShipOrder> findPage(Page<PdsShipOrder> page, PdsShipOrder pdsShipOrder) {
		return super.findPage(page, pdsShipOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(PdsShipOrder pdsShipOrder) {
		if(StringUtils.isEmpty(pdsShipOrder.getShipId())){
			String shipId = PdsCommonUtil.incrIdByClass(PdsShipOrder.class, IPdsConstant.SHIP_ORDER_SN_PRE);
			pdsShipOrder.setShipId(shipId);
		}
		super.save(pdsShipOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(PdsShipOrder pdsShipOrder) {
		super.delete(pdsShipOrder);
	}

    public List<PdsShipOrder> findJoinList(PdsShipOrder pdsShipOrder) {
        return dao.findJoinList(pdsShipOrder);
    }

	public boolean deleteByDateSeqPmId(PdsShipOrder pdsShipOrder) {
		String pmId = pdsShipOrder.getPmId();
		Date deliveryDate = pdsShipOrder.getDeliveryDate();
		String buySeq = pdsShipOrder.getDeliverySeq();
		if (StringUtils.isAnyEmpty(pmId,buySeq) || null == deliveryDate){
			return false;		//删除失败返回false
		}
		dao.deleteByDateSeqPmId(pdsShipOrder);
		return true;		//删除成功
	}
}