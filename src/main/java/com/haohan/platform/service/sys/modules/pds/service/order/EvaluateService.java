package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.order.EvaluateDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.Evaluate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评价管理Service
 * @author haohan
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class EvaluateService extends CrudService<EvaluateDao, Evaluate> {

	public Evaluate get(String id) {
		return super.get(id);
	}
	
	public List<Evaluate> findList(Evaluate evaluate) {
		return super.findList(evaluate);
	}
	
	public Page<Evaluate> findPage(Page<Evaluate> page, Evaluate evaluate) {
		return super.findPage(page, evaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(Evaluate evaluate) {
		super.save(evaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(Evaluate evaluate) {
		super.delete(evaluate);
	}
	
}