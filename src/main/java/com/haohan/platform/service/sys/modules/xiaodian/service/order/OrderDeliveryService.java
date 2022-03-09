package com.haohan.platform.service.sys.modules.xiaodian.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.order.OrderDeliveryDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 订单配送信息管理Service
 * @author haohan
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class OrderDeliveryService extends CrudService<OrderDeliveryDao, OrderDelivery> {

	public OrderDelivery get(String id) {
		return super.get(id);
	}
	
	public List<OrderDelivery> findList(OrderDelivery orderDelivery) {
		return super.findList(orderDelivery);
	}
	
	public Page<OrderDelivery> findPage(Page<OrderDelivery> page, OrderDelivery orderDelivery) {
		return super.findPage(page, orderDelivery);
	}

	public OrderDelivery fetchByOrderId(String orderId){
		OrderDelivery orderDelivery =  new OrderDelivery();
		orderDelivery.setOrderId(orderId);
		List<OrderDelivery> list = super.findList(orderDelivery);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderDelivery orderDelivery) {
		super.save(orderDelivery);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderDelivery orderDelivery) {
		super.delete(orderDelivery);
	}

    /**
     * 修改 订单配送状态  deliveryStatus/orderStatus
     * @param orderDelivery
     */
	@Transactional(readOnly = false)
	public void modifyStatus(OrderDelivery orderDelivery) {
		dao.modifyStatus(orderDelivery);
	}
}