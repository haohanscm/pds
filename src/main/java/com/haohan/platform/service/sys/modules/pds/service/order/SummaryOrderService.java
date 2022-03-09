package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsAdminSumBuyOrder;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisOverViewReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.order.SummaryOrderDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 采购单汇总Service
 * @author haohan
 * @version 2018-10-24
 */
@Service
@Transactional(readOnly = true)
public class SummaryOrderService extends CrudService<SummaryOrderDao, SummaryOrder> {

	public SummaryOrder get(String id) {
		return super.get(id);
	}
	
	public List<SummaryOrder> findList(SummaryOrder summaryOrder) {
		return super.findList(summaryOrder);
	}

	public SummaryOrder fetchBySummaryOrderId (String summaryOrderId){
		SummaryOrder summaryOrder = new SummaryOrder();
		summaryOrder.setSummaryOrderId(summaryOrderId);
		List<SummaryOrder> summaryOrderList = dao.findList(summaryOrder);
		return Collections3.isEmpty(summaryOrderList)?null:summaryOrderList.get(0);
	}

	public Page<PdsAdminSumBuyOrder> findAdminSumOrderPage(Page<PdsAdminSumBuyOrder> page,PdsAdminSumBuyOrder pdsAdminSumBuyOrder){
		pdsAdminSumBuyOrder.setPage(page);
		page.setList(dao.findAdminSumOrderPage(pdsAdminSumBuyOrder));
		return page;
	}

	@Transactional(readOnly = false)
	public void updateStatus(String summaryOrderId, IPdsConstant.SummaryOrderStatus summaryOrderStatus){
		dao.updateStatus(summaryOrderId,summaryOrderStatus.getCode());
	}

    /**
     * 用于修改采购/供应均价  平台价格/状态
     * 未传入值不修改
     * @param summaryOrder
     */
    @Transactional(readOnly = false)
    public void updatePrice(SummaryOrder summaryOrder){
        summaryOrder.preUpdate();
        dao.updatePrice(summaryOrder);
    }

	public Page<SummaryOrder> findPage(Page<SummaryOrder> page, SummaryOrder summaryOrder) {
		return super.findPage(page, summaryOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(SummaryOrder summaryOrder) {
		super.save(summaryOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(SummaryOrder summaryOrder) {
		super.delete(summaryOrder);
	}

	@Transactional(readOnly = false)
	public boolean deleteByDateSeqPmId(SummaryOrder summaryOrder){
		String pmId = summaryOrder.getPmId();
		Date deliveryDate = summaryOrder.getDeliveryTime();
		String buySeq = summaryOrder.getBuySeq();
		if (StringUtils.isAnyEmpty(pmId,buySeq) || null == deliveryDate){
			return false;		//删除失败返回false
		}
		dao.deleteByDateSeqPmId(summaryOrder);
		return true;		//删除成功
	}

	// 查询待报价的采购汇总单
    public List<SummaryOrder> queryWaitOfferList(SummaryOrder summaryOrder) {
	    summaryOrder.setStatus(IPdsConstant.SummaryOrderStatus.wait.getCode());
        return dao.queryWaitOfferList(summaryOrder);
    }

    public List<SummaryOrder> findJoinList(SummaryOrder summaryOrder) {
        return dao.findJoinList(summaryOrder);
    }

	public Integer countNotOffer(PdsApiStatisOverViewReq overViewReq) {
		Integer result = dao.countNotOffer(overViewReq);
		return null == result ? 0 : result;
	}
}