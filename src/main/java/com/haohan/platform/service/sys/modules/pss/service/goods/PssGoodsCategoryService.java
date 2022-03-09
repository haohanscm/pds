package com.haohan.platform.service.sys.modules.pss.service.goods;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pss.dao.goods.PssGoodsCategoryDao;
import com.haohan.platform.service.sys.modules.pss.entity.goods.PssGoodsCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品总分类Service
 * @author haohan
 * @version 2018-09-07
 */
@Service
@Transactional(readOnly = true)
public class PssGoodsCategoryService extends TreeService<PssGoodsCategoryDao, PssGoodsCategory> {

	public PssGoodsCategory get(String id) {
		return super.get(id);
	}
	
	public List<PssGoodsCategory> findList(PssGoodsCategory pssGoodsCategory) {
		if (StringUtils.isNotBlank(pssGoodsCategory.getParentIds())){
			pssGoodsCategory.setParentIds(","+ pssGoodsCategory.getParentIds()+",");
		}
		return super.findList(pssGoodsCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(PssGoodsCategory pssGoodsCategory) {
		super.save(pssGoodsCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(PssGoodsCategory pssGoodsCategory) {
		super.delete(pssGoodsCategory);
	}
	
}