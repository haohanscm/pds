package com.haohan.platform.service.sys.modules.pds.service.cost;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.cost.DealCostDetailDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.DealCostDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交易成本明细Service
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class DealCostDetailService extends CrudService<DealCostDetailDao, DealCostDetail> {

	public DealCostDetail get(String id) {
		return super.get(id);
	}
	
	public List<DealCostDetail> findList(DealCostDetail dealCostDetail) {
		return super.findList(dealCostDetail);
	}
	
	public Page<DealCostDetail> findPage(Page<DealCostDetail> page, DealCostDetail dealCostDetail) {
		return super.findPage(page, dealCostDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(DealCostDetail dealCostDetail) {
		super.save(dealCostDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(DealCostDetail dealCostDetail) {
		super.delete(dealCostDetail);
	}
	
}