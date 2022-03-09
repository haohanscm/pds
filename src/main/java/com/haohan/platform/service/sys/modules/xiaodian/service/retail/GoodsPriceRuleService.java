package com.haohan.platform.service.sys.modules.xiaodian.service.retail;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.retail.GoodsPriceRuleDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsPriceRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定价规则管理Service
 * @author haohan
 * @version 2017-12-07
 */
@Service
@Transactional(readOnly = true)
public class GoodsPriceRuleService extends CrudService<GoodsPriceRuleDao, GoodsPriceRule> {

	public GoodsPriceRule get(String id) {
		return super.get(id);
	}
	
	public List<GoodsPriceRule> findList(GoodsPriceRule goodsPriceRule) {
		return super.findList(goodsPriceRule);
	}
	
	public Page<GoodsPriceRule> findPage(Page<GoodsPriceRule> page, GoodsPriceRule goodsPriceRule) {
        goodsPriceRule.setPage(page);
        page.setList(dao.findJoinList(goodsPriceRule));
        return page;
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsPriceRule goodsPriceRule) {
		super.save(goodsPriceRule);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsPriceRule goodsPriceRule) {
		super.delete(goodsPriceRule);
	}

	@Transactional(readOnly = false)
	public void deleteGoodsId(String goodsId) {
	    GoodsPriceRule goodsPriceRule = new GoodsPriceRule();
	    goodsPriceRule.setGoodsId(goodsId);
		dao.deleteGoodsId(goodsPriceRule);
	}

	@Transactional(readOnly = false)
	public void deleteForCategory(String categoryId) {
	    GoodsPriceRule goodsPriceRule = new GoodsPriceRule();
	    goodsPriceRule.setGoodsId(categoryId);
		dao.deleteForCategory(goodsPriceRule);
	}

	public GoodsPriceRule fetchByGoodsId(String goodsId) {
        GoodsPriceRule goodsPriceRule = new GoodsPriceRule();
        goodsPriceRule.setGoodsId(goodsId);
        List<GoodsPriceRule> list = super.findList(goodsPriceRule);
        return list.size() > 0 ? list.get(0) : null;
	}
}