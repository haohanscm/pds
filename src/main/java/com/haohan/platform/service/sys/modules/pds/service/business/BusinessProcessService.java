package com.haohan.platform.service.sys.modules.pds.service.business;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.business.BusinessProcessDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.BusinessProcess;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务流程Service
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class BusinessProcessService extends CrudService<BusinessProcessDao, BusinessProcess> {

	public BusinessProcess get(String id) {
		return super.get(id);
	}
	
	public List<BusinessProcess> findList(BusinessProcess businessProcess) {
		return super.findList(businessProcess);
	}
	
	public Page<BusinessProcess> findPage(Page<BusinessProcess> page, BusinessProcess businessProcess) {
		return super.findPage(page, businessProcess);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessProcess businessProcess) {
		super.save(businessProcess);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessProcess businessProcess) {
		super.delete(businessProcess);
	}
	
}