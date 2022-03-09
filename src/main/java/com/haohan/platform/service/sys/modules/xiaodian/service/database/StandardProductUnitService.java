package com.haohan.platform.service.sys.modules.xiaodian.service.database;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.database.StandardProductUnitDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StandardProductUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 标准商品库管理Service
 * @author dy
 * @version 2018-10-17
 */
@Service
@Transactional(readOnly = true)
public class StandardProductUnitService extends CrudService<StandardProductUnitDao, StandardProductUnit> {

    @Autowired
    @Lazy(true)
    private StockKeepingUnitService stockKeepingUnitService;

	public StandardProductUnit get(String id) {
		return super.get(id);
	}

    /**
     * 带分类名称
     * @param standardProductUnit
     * @return
     */
    public StandardProductUnit getWith(StandardProductUnit standardProductUnit) {
        return dao.getWith(standardProductUnit);
    }
	
	public List<StandardProductUnit> findList(StandardProductUnit standardProductUnit) {
		return super.findList(standardProductUnit);
	}
	
	public Page<StandardProductUnit> findPage(Page<StandardProductUnit> page, StandardProductUnit standardProductUnit) {
		return super.findPage(page, standardProductUnit);
	}

    /**
     * 查询结果 带 分类名称categoryName
     * @param standardProductUnit
     * @return
     */
	public List<StandardProductUnit> findJoinList(StandardProductUnit standardProductUnit) {
		return dao.findJoinList(standardProductUnit);
	}

    /**
     * 带 分类名称categoryName
     * @param page
     * @param standardProductUnit
     * @return
     */
	public Page<StandardProductUnit> findJoinPage(Page<StandardProductUnit> page, StandardProductUnit standardProductUnit) {
		standardProductUnit.setPage(page);
		page.setList(dao.findJoinList(standardProductUnit));
		return page;
	}

    /**
     * 根据 spuIds  查询
     * @param ids spu列表  ","分隔
     * @return
     */
    public List<StandardProductUnit> findListByIds(String ids) {
        StandardProductUnit spu = new StandardProductUnit();
        spu.setId(ids);
        return dao.findListByIds(spu);
    }
	
	@Transactional(readOnly = false)
	public void save(StandardProductUnit standardProductUnit) {
		// 生成商品唯一编号
		if(StringUtils.isEmpty(standardProductUnit.getGeneralSn())){
            standardProductUnit.setGeneralSn(createGeneralSn());
		}
		// 默认排序
		if(null == standardProductUnit.getSort()){
		    standardProductUnit.setSort(10);
        }
		super.save(standardProductUnit);
	}


    @Transactional(readOnly = false)
	public void delete(StandardProductUnit standardProductUnit) {
		super.delete(standardProductUnit);
	}

    /**
     * 同时删除所属sku
     * @param standardProductUnit
     */
    @Transactional(readOnly = false)
    public void deleteWithSku(StandardProductUnit standardProductUnit) {
        List<StockKeepingUnit> skuList = stockKeepingUnitService.findList(standardProductUnit.getId());
        for(StockKeepingUnit sku:skuList){
            stockKeepingUnitService.delete(sku);
        }
        super.delete(standardProductUnit);
    }

	// 生成 通用编号 spu
    private String createGeneralSn() {
        Long num = JedisUtils.fetchIncrNum(IGoodsConstant.COMMON_SPU_ID_INCR_KEY, IGoodsConstant.COMMON_SPU_ID_START);
        String result = IGoodsConstant.COMMON_SPU_ID_PREFIX + num;
        return result;
    }

	/**
	 * Excel表头和实体类属性的映射
	 * @return
	 */
	public Map<String, String> fetchHeaderMapper() {
		Map<String, String> headerMapper = new LinkedHashMap<>(16);
        headerMapper.put("商品名称", "goodsName");
        headerMapper.put("分类名称", "categoryName");
        headerMapper.put("商品描述", "detailDesc");
        headerMapper.put("图片地址", "thumbUrl");
        headerMapper.put("行业", "industry");
        headerMapper.put("品牌", "brand");
        headerMapper.put("厂家制造商", "manufacturer");
        headerMapper.put("排序", "sort");
        headerMapper.put("聚合平台类型", "aggregationType");
        headerMapper.put("备注", "remarks");
		// 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
		List<Dict> headerList = DictUtils.getDictList("database_spu_import");
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
        commentMapper.put("商品名称", "必填项,不超过64字符");
        commentMapper.put("分类名称", "只能填一个分类名称,不超过10字符");
        commentMapper.put("商品描述", "不超过6000字符");
        commentMapper.put("图片地址", "不超过500字符");
        commentMapper.put("行业", "不超过64字符");
        commentMapper.put("品牌", "不超过64字符");
        commentMapper.put("厂家制造商", "不超过64字符");
        commentMapper.put("排序", "显示时的顺序,填数字");
        commentMapper.put("聚合平台类型", "填数字,0:非聚合,1:农贸城");
        commentMapper.put("备注", "备注信息,不超过500个字符");
		// 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
		List<Dict> list = DictUtils.getDictList("database_spu_import");
		if (!Collections3.isEmpty(list)) {
			commentMapper.putAll(Collections3.extractToMap(list, "value", "description"));
		}
		return commentMapper;
	}


}