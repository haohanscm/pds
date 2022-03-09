package com.haohan.platform.service.sys.modules.xiaodian.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.order.OrderActionDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderAction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单操作Service
 * @author haohan
 * @version 2017-08-06
 */
@Service
@Transactional(readOnly = true)
public class OrderActionService extends CrudService<OrderActionDao, OrderAction> {

	public OrderAction get(String id) {
		return super.get(id);
	}
	
	public List<OrderAction> findList(OrderAction orderAction) {
		return super.findList(orderAction);
	}
	
	public Page<OrderAction> findPage(Page<OrderAction> page, OrderAction orderAction) {
		return super.findPage(page, orderAction);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderAction orderAction) {
		super.save(orderAction);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderAction orderAction) {
		super.delete(orderAction);
	}




	}
