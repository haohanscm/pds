package com.haohan.platform.service.sys.modules.pds.service.cost;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.cost.CostOrderDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.CostOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 成本核算单Service
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class CostOrderService extends CrudService<CostOrderDao, CostOrder> {

	public CostOrder get(String id) {
		return super.get(id);
	}
	
	public List<CostOrder> findList(CostOrder costOrder) {
		return super.findList(costOrder);
	}
	
	public Page<CostOrder> findPage(Page<CostOrder> page, CostOrder costOrder) {
		return super.findPage(page, costOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(CostOrder costOrder) {
		super.save(costOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(CostOrder costOrder) {
		super.delete(costOrder);
	}
	
}