package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.SaleRulesDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.SaleRules;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 售卖规则管理Service
 * @author haohan
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class SaleRulesService extends CrudService<SaleRulesDao, SaleRules> {

	public SaleRules get(String id) {
		return super.get(id);
	}
	
	public List<SaleRules> findList(SaleRules saleRules) {
		return super.findList(saleRules);
	}
	
	public Page<SaleRules> findPage(Page<SaleRules> page, SaleRules saleRules) {
		return super.findPage(page, saleRules);
	}

	public SaleRules findByGoodsId(String goodsId){
		SaleRules saleRules = new SaleRules();
		saleRules.setGoodsId(goodsId);
		List<SaleRules> list = super.findList(saleRules);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Transactional(readOnly = false)
	public void save(SaleRules saleRules) {
		super.save(saleRules);
	}
	
	@Transactional(readOnly = false)
	public void delete(SaleRules saleRules) {
		super.delete(saleRules);
	}

	@Transactional(readOnly = false)
	public void deleteByGoodsId(String goodsId) {
		SaleRules saleRules = new SaleRules();
		saleRules.setGoodsId(goodsId);
		dao.deleteByGoodsId(saleRules);
	}
}