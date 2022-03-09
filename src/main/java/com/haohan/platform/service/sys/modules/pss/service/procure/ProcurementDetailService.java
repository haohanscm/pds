package com.haohan.platform.service.sys.modules.pss.service.procure;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.dao.procure.ProcurementDetailDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品采购明细Service
 * @author haohan
 * @version 2018-09-07
 */
@Service
@Transactional(readOnly = true)
public class ProcurementDetailService extends CrudService<ProcurementDetailDao, ProcurementDetail> {

	public ProcurementDetail get(String id) {
		return super.get(id);
	}
	
	public List<ProcurementDetail> findList(ProcurementDetail procurementDetail) {
		return super.findList(procurementDetail);
	}

	public List<ProcurementDetail> findByProcureNumWithStatus(String procureNum, IPssConstant.StockStatus stockStatus){
		ProcurementDetail procurementDetail = new ProcurementDetail();
		procurementDetail.setProcureNum(procureNum);
		if (null != stockStatus){
			procurementDetail.setStockStatus(stockStatus.getCode());
		}
		List<ProcurementDetail> list = dao.findList(procurementDetail);
		return list;
	}
	
	public Page<ProcurementDetail> findPage(Page<ProcurementDetail> page, ProcurementDetail procurementDetail) {
		return super.findPage(page, procurementDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(ProcurementDetail procurementDetail) {
		super.save(procurementDetail);
	}

	@Transactional(readOnly = false)
	public void updateSelective(ProcurementDetail procurementDetail){
		procurementDetail.setUpdateDate(new Date());
		dao.updateSelective(procurementDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProcurementDetail procurementDetail) {
		super.delete(procurementDetail);
	}

	public Integer countTotalGoodsNum(String procureNum){
		Integer goodsNum = dao.countTotalGoodsNum(procureNum);
		return null == goodsNum ? 0 : goodsNum;
	}

	public BigDecimal countSumAmount(String procureNum) {
		BigDecimal sumAmount = dao.countSumAmount(procureNum);
		return null == sumAmount ? BigDecimal.ZERO : sumAmount;
	}
}