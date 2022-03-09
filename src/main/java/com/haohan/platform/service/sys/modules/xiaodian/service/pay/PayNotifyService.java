package com.haohan.platform.service.sys.modules.xiaodian.service.pay;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.pay.PayNotifyDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.PayNotify;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付结果通知Service
 * @author haohan
 * @version 2017-12-07
 */
@Service
@Transactional(readOnly = true)
public class PayNotifyService extends CrudService<PayNotifyDao, PayNotify> {

	public PayNotify get(String id) {
		return super.get(id);
	}
	
	public List<PayNotify> findList(PayNotify payNotify) {
		return super.findList(payNotify);
	}
	
	public Page<PayNotify> findPage(Page<PayNotify> page, PayNotify payNotify) {
		return super.findPage(page, payNotify);
	}
	
	@Transactional(readOnly = false)
	public void save(PayNotify payNotify) {
		super.save(payNotify);
	}
	
	@Transactional(readOnly = false)
	public void delete(PayNotify payNotify) {
		super.delete(payNotify);
	}


	public PayNotify fetchByOrderId(String orderId){
		PayNotify order = new PayNotify();
		order.setOrderId(orderId);
		List<PayNotify> list =findList(order);

		return CollectionUtils.isNotEmpty(list)?list.get(0):null;
	}
}