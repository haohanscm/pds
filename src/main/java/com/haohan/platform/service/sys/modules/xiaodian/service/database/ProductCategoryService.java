package com.haohan.platform.service.sys.modules.xiaodian.service.database;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.database.ProductCategoryDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品库分类管理Service
 * @author dy
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryService extends TreeService<ProductCategoryDao, ProductCategory> {

	public ProductCategory get(String id) {
		return super.get(id);
	}

    /**
     * 带分类下商品数 goodsCount
     * @param productCategory 只需 id
     * @return
     */
    public ProductCategory getWith(ProductCategory productCategory) {
        return dao.getWith(productCategory);
    }
	
	public List<ProductCategory> findList(ProductCategory productCategory) {
		return super.findList(productCategory);
	}

    /**
     * 结果 带父分类名称 parent.name
     * @param productCategory
     * @return
     */
	public List<ProductCategory> findJoinList(ProductCategory productCategory) {
		return dao.findJoinList(productCategory);
	}

    /**
     * 查询ids中分类及其所有父级分类
     * @param ids
     * @return  结果中一级分类排在前
     */
	public List<ProductCategory> findListByIdsWithParent(String ids){
		ProductCategory productCategory = new ProductCategory(ids);
		return dao.findListByIdsWithParent(productCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCategory productCategory) {
        // 生成商品分类唯一编号
        if(StringUtils.isEmpty(productCategory.getGeneralCategorySn())){
            productCategory.setGeneralCategorySn(createCategorySn());
        }
		super.save(productCategory);
	}

    /**
     * 生成 通用编号
     * @return
     */
    private String createCategorySn() {
        Long num = JedisUtils.fetchIncrNum(IGoodsConstant.COMMON_CATEGORY_ID_INCR_KEY, IGoodsConstant.COMMON_SPU_ID_START);
        String result = IGoodsConstant.COMMON_CATEGORY_ID_PREFIX + num;
        return result;
    }
	
	@Transactional(readOnly = false)
	public void delete(ProductCategory productCategory) {
		super.delete(productCategory);
	}

    /**
     * 删除分类及其子分类
     * @param productCategory
     */
    @Transactional(readOnly = false)
    public void deleteWithChildren(ProductCategory productCategory) {
        dao.deleteWithChildren(productCategory);
    }

    /**
     * 查找无分类编号的分类
     * @return
     */
    public List<ProductCategory> findEmptySnList(){
        return dao.findEmptySnList();
    }

	/**
	 * Excel表头和实体类属性的映射
	 * @return
	 */
	public Map<String, String> fetchHeaderMapper() {
		Map<String, String> headerMapper = new LinkedHashMap<>(16);
		headerMapper.put("分类名称", "name");    // 必要
		headerMapper.put("父级分类名称", "parentIds"); // 父分类名称放入parentIds中
		headerMapper.put("分类描述", "categoryDesc");
		headerMapper.put("排序", "sort");
		headerMapper.put("聚合平台类型", "aggregationType");
		headerMapper.put("图片", "logo");
		headerMapper.put("备注", "remarks");
		// 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
		List<Dict> headerList = DictUtils.getDictList("database_category_import");
		if (!Collections3.isEmpty(headerList)) {
			headerMapper.putAll(Collections3.extractToMap(headerList, "value", "label"));
		}
		return headerMapper;
	}

	/**
	 * Excel表头和批注的映射
	 * @return
	 */
	public Map<String, String> fetchCommentMapper() {
		Map<String, String> commentMapper = new LinkedHashMap<>(16);
        commentMapper.put("分类名称", "必填项,不超过10个字符");
        commentMapper.put("父级分类名称", "不填时为一级分类");
        commentMapper.put("分类描述", "不超过500个字符");
        commentMapper.put("排序", "分类显示时的顺序,填数字");
        commentMapper.put("聚合平台类型", "填数字,0:非聚合,1:农贸城,不填时取0");
        commentMapper.put("图片", "图片链接地址");
        commentMapper.put("备注", "备注信息,不超过500个字符");
		// 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
		List<Dict> list = DictUtils.getDictList("database_category_import");
		if (!Collections3.isEmpty(list)) {
			commentMapper.putAll(Collections3.extractToMap(list, "value", "description"));
		}
		return commentMapper;
	}

    /**
     * 查询分类 在公共库和 店铺零售的对应关系 零售分类id存入tempId
     * 一级分类排在前
     * 按generalCategorySn 和 名称匹配
     * 按generalCategorySn 匹配的排在前
     * @param shopId
     * @param parentId        父分类的id  传"0" 只查询一级分类
     * @param excludeParentId 排除的父分类的id  传"0" 不查询一级分类
     * @return 零售店铺的分类id 存入 tempId
     */
    public List<ProductCategory> findRelation(String shopId, String parentId, String excludeParentId) {
        return dao.findRelation(shopId, parentId, excludeParentId);
    }
}