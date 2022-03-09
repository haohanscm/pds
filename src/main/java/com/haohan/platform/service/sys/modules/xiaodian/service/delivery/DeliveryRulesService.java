package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.DeliveryRulesDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryRules;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配送规则管理Service
 * @author haohan
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class DeliveryRulesService extends CrudService<DeliveryRulesDao, DeliveryRules> {

	public DeliveryRules get(String id) {
		return super.get(id);
	}
	
	public List<DeliveryRules> findList(DeliveryRules deliveryRules) {
		return super.findList(deliveryRules);
	}
	
	public Page<DeliveryRules> findPage(Page<DeliveryRules> page, DeliveryRules deliveryRules) {
		return super.findPage(page, deliveryRules);
	}

	public DeliveryRules fetchByGoodsId(String goodsId){
		DeliveryRules deliveryRules =  new DeliveryRules();
		deliveryRules.setGoodsId(goodsId);
		List<DeliveryRules> list = super.findList(deliveryRules);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@Transactional(readOnly = false)
	public void save(DeliveryRules deliveryRules) {
		super.save(deliveryRules);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeliveryRules deliveryRules) {
		super.delete(deliveryRules);
	}

	@Transactional(readOnly = false)
    public void deleteByGoodsId(String goodsId) {
		DeliveryRules deliveryRules = new DeliveryRules();
		deliveryRules.setGoodsId(goodsId);
		dao.deleteByGoodsId(deliveryRules);
    }
}