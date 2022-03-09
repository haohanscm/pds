package com.haohan.platform.service.sys.modules.pds.service.cost;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.cost.CostControlDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.CostControl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 成本管控Service
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class CostControlService extends CrudService<CostControlDao, CostControl> {

	public CostControl get(String id) {
		return super.get(id);
	}
	
	public List<CostControl> findList(CostControl costControl) {
		return super.findList(costControl);
	}
	
	public Page<CostControl> findPage(Page<CostControl> page, CostControl costControl) {
		return super.findPage(page, costControl);
	}
	
	@Transactional(readOnly = false)
	public void save(CostControl costControl) {
		super.save(costControl);
	}
	
	@Transactional(readOnly = false)
	public void delete(CostControl costControl) {
		super.delete(costControl);
	}
	
}