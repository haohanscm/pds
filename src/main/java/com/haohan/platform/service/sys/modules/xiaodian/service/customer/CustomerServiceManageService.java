package com.haohan.platform.service.sys.modules.xiaodian.service.customer;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.customer.CustomerServiceManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerServiceManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户服务管理Service
 * @author haohan
 * @version 2018-04-22
 */
@Service
@Transactional(readOnly = true)
public class CustomerServiceManageService extends CrudService<CustomerServiceManageDao, CustomerServiceManage> {

	public CustomerServiceManage get(String id) {
		return super.get(id);
	}
	
	public List<CustomerServiceManage> findList(CustomerServiceManage customerServiceManage) {
		return super.findList(customerServiceManage);
	}
	
	public Page<CustomerServiceManage> findPage(Page<CustomerServiceManage> page, CustomerServiceManage customerServiceManage) {
		return super.findPage(page, customerServiceManage);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerServiceManage customerServiceManage) {
		super.save(customerServiceManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerServiceManage customerServiceManage) {
		super.delete(customerServiceManage);
	}
	
}