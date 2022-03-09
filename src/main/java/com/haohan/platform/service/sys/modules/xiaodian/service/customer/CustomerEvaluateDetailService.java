package com.haohan.platform.service.sys.modules.xiaodian.service.customer;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.customer.CustomerEvaluateDetailDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerEvaluateDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户服务评价明细Service
 * @author haohan
 * @version 2018-04-22
 */
@Service
@Transactional(readOnly = true)
public class CustomerEvaluateDetailService extends CrudService<CustomerEvaluateDetailDao, CustomerEvaluateDetail> {

	public CustomerEvaluateDetail get(String id) {
		return super.get(id);
	}
	
	public List<CustomerEvaluateDetail> findList(CustomerEvaluateDetail customerEvaluateDetail) {
		return super.findList(customerEvaluateDetail);
	}
	
	public Page<CustomerEvaluateDetail> findPage(Page<CustomerEvaluateDetail> page, CustomerEvaluateDetail customerEvaluateDetail) {
		return super.findPage(page, customerEvaluateDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerEvaluateDetail customerEvaluateDetail) {
		super.save(customerEvaluateDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerEvaluateDetail customerEvaluateDetail) {
		super.delete(customerEvaluateDetail);
	}
	
}