package com.haohan.platform.service.sys.modules.xiaodian.service.customer;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.customer.CustomerRevaluateDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerRevaluate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户服务评价Service
 * @author haohan
 * @version 2018-04-22
 */
@Service
@Transactional(readOnly = true)
public class CustomerRevaluateService extends CrudService<CustomerRevaluateDao, CustomerRevaluate> {

	public CustomerRevaluate get(String id) {
		return super.get(id);
	}
	
	public List<CustomerRevaluate> findList(CustomerRevaluate customerRevaluate) {
		return super.findList(customerRevaluate);
	}
	
	public Page<CustomerRevaluate> findPage(Page<CustomerRevaluate> page, CustomerRevaluate customerRevaluate) {
		return super.findPage(page, customerRevaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerRevaluate customerRevaluate) {
		super.save(customerRevaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerRevaluate customerRevaluate) {
		super.delete(customerRevaluate);
	}
	
}