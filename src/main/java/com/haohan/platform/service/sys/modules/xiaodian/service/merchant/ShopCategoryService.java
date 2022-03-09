package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopCategory;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.ShopCategoryDao;

/**
 * 店铺分类管理Service
 * @author haohan
 * @version 2019-01-15
 */
@Service
@Transactional(readOnly = true)
public class ShopCategoryService extends TreeService<ShopCategoryDao, ShopCategory> {

	public ShopCategory get(String id) {
		return super.get(id);
	}
	
	public List<ShopCategory> findList(ShopCategory shopCategory) {
		if (StringUtils.isNotBlank(shopCategory.getParentIds())){
			shopCategory.setParentIds(","+shopCategory.getParentIds()+",");
		}
		return super.findList(shopCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopCategory shopCategory) {
		super.save(shopCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopCategory shopCategory) {
		super.delete(shopCategory);
	}
	
}