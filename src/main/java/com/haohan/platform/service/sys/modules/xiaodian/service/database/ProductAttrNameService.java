package com.haohan.platform.service.sys.modules.xiaodian.service.database;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.database.ProductAttrNameDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductAttrName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品库属性名管理Service
 * @author dy
 * @version 2018-10-22
 */
@Service
@Transactional(readOnly = true)
public class ProductAttrNameService extends CrudService<ProductAttrNameDao, ProductAttrName> {

	public ProductAttrName get(String id) {
		return super.get(id);
	}
	
	public List<ProductAttrName> findList(ProductAttrName productAttrName) {
		return super.findList(productAttrName);
	}
	
	public Page<ProductAttrName> findPage(Page<ProductAttrName> page, ProductAttrName productAttrName) {
		return super.findPage(page, productAttrName);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductAttrName productAttrName) {
		super.save(productAttrName);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductAttrName productAttrName) {
		super.delete(productAttrName);
	}

	// 根据规格名ids 查询
	public List<ProductAttrName> findByNameIds(String nameIds){
	    if(StringUtils.isEmpty(nameIds)){
	        return new ArrayList<>();
        }
	    ProductAttrName productAttrName = new ProductAttrName();
	    productAttrName.setId(nameIds);
	    return dao.findByNameIds(productAttrName);
    }

}