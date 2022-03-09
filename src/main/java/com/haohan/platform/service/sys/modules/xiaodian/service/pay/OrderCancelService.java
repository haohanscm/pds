package com.haohan.platform.service.sys.modules.xiaodian.service.pay;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.pay.OrderCancelDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderCancel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单撤销Service
 * @author haohan
 * @version 2017-12-30
 */
@Service
@Transactional(readOnly = true)
public class OrderCancelService extends CrudService<OrderCancelDao, OrderCancel> {

	public OrderCancel get(String id) {
		return super.get(id);
	}
	
	public List<OrderCancel> findList(OrderCancel orderCancel) {
		return super.findList(orderCancel);
	}
	
	public Page<OrderCancel> findPage(Page<OrderCancel> page, OrderCancel orderCancel) {
		return super.findPage(page, orderCancel);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderCancel orderCancel) {
		super.save(orderCancel);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderCancel orderCancel) {
		super.delete(orderCancel);
	}
	
}