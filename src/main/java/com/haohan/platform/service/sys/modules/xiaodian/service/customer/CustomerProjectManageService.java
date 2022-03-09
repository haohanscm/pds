package com.haohan.platform.service.sys.modules.xiaodian.service.customer;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.customer.CustomerProjectManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerProjectManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户项目管理Service
 * @author haohan
 * @version 2018-04-22
 */
@Service
@Transactional(readOnly = true)
public class CustomerProjectManageService extends CrudService<CustomerProjectManageDao, CustomerProjectManage> {

	public CustomerProjectManage get(String id) {
		return super.get(id);
	}
	
	public List<CustomerProjectManage> findList(CustomerProjectManage customerProjectManage) {
		return super.findList(customerProjectManage);
	}
	
	public Page<CustomerProjectManage> findPage(Page<CustomerProjectManage> page, CustomerProjectManage customerProjectManage) {
		return super.findPage(page, customerProjectManage);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerProjectManage customerProjectManage) {
		super.save(customerProjectManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerProjectManage customerProjectManage) {
		super.delete(customerProjectManage);
	}
	
}