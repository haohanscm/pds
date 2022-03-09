package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.ShopTemplateDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺模板管理Service
 * @author haohan
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class ShopTemplateService extends CrudService<ShopTemplateDao, ShopTemplate> {

	public ShopTemplate get(String id) {
		return super.get(id);
	}
	
	public List<ShopTemplate> findList(ShopTemplate shopTemplate) {
		return super.findList(shopTemplate);
	}
	
	public Page<ShopTemplate> findPage(Page<ShopTemplate> page, ShopTemplate shopTemplate) {
		return super.findPage(page, shopTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopTemplate shopTemplate) {
		super.save(shopTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopTemplate shopTemplate) {
		super.delete(shopTemplate);
	}
	
}