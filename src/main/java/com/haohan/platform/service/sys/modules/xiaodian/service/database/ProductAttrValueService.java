package com.haohan.platform.service.sys.modules.xiaodian.service.database;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.database.ProductAttrValueDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductAttrValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品库属性值管理Service
 * @author dy
 * @version 2018-10-22
 */
@Service
@Transactional(readOnly = true)
public class ProductAttrValueService extends CrudService<ProductAttrValueDao, ProductAttrValue> {

	public ProductAttrValue get(String id) {
		return super.get(id);
	}
	
	public List<ProductAttrValue> findList(ProductAttrValue productAttrValue) {
		return super.findList(productAttrValue);
	}
	
	public Page<ProductAttrValue> findPage(Page<ProductAttrValue> page, ProductAttrValue productAttrValue) {
		return super.findPage(page, productAttrValue);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductAttrValue productAttrValue) {
		super.save(productAttrValue);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductAttrValue productAttrValue) {
		super.delete(productAttrValue);
	}

	// 查询   带 属性名
    public List<ProductAttrValue> findJoinList(ProductAttrValue productAttrValue) {
        return dao.findJoinList(productAttrValue);
    }

    // 根据规格值ids 查询  结果带属性名
    public List<ProductAttrValue> findByValueIds(String valueIds){
        if(StringUtils.isEmpty(valueIds)){
            return new ArrayList<>();
        }
        ProductAttrValue productAttrValue = new ProductAttrValue();
        productAttrValue.setId(valueIds);
        return dao.findByValueIds(productAttrValue);
    }

    // 获取属性名:属性值 的对应关系
	public List<String> fetchNameValue(ProductAttrValue productAttrValue){
		return dao.fetchNameValue(productAttrValue);
	}

}