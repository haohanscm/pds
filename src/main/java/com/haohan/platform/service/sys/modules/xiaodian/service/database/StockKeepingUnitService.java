package com.haohan.platform.service.sys.modules.xiaodian.service.database;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.database.RespSku;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.database.StockKeepingUnitDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存商品管理Service
 * @author dy
 * @version 2018-10-20
 */
@Service
@Transactional(readOnly = true)
public class StockKeepingUnitService extends CrudService<StockKeepingUnitDao, StockKeepingUnit> {

	public StockKeepingUnit get(String id) {
		return super.get(id);
	}
	
	public List<StockKeepingUnit> findList(StockKeepingUnit stockKeepingUnit) {
		return super.findList(stockKeepingUnit);
	}

    /**
     * 根据spuId 查询
     * @param spuId
     * @return
     */
	public List<StockKeepingUnit> findList(String spuId) {
        StockKeepingUnit stockKeepingUnit = new StockKeepingUnit();
        stockKeepingUnit.setSpuId(spuId);
		return super.findList(stockKeepingUnit);
	}
	
	public Page<StockKeepingUnit> findPage(Page<StockKeepingUnit> page, StockKeepingUnit stockKeepingUnit) {
		return super.findPage(page, stockKeepingUnit);
	}

    /**
     * 此处保存时 未处理 stockGoodsSn ;
     * @param stockKeepingUnit
     */
	@Transactional(readOnly = false)
	public void save(StockKeepingUnit stockKeepingUnit) {
		super.save(stockKeepingUnit);
	}
	
	@Transactional(readOnly = false)
	public void delete(StockKeepingUnit stockKeepingUnit) {
		super.delete(stockKeepingUnit);
	}

    /**
     * 拼接生成 sku编号 根据通用编号  spu
     * @param generalSn
     * @param num
     * @return
     */
    public String createSkuSn(String generalSn, int num) {
        return generalSn + IGoodsConstant.COMMON_SKU_INFIX + num;
    }

    /**
     * 获取  stockGoodsSn后缀拼接的数字  错误时返回0
     * @param stockGoodsSn
     * @return
     */
    public int fetchSkuSnNum(String stockGoodsSn){
        String num = StringUtils.substringAfterLast(stockGoodsSn, IGoodsConstant.COMMON_SKU_INFIX);
        return StringUtils.toInteger(num);
    }

    /**
     * sku 详情 带spu信息
     * @param stockGoodsId
     * @return
     */
	public RespSku getSkuInfo(String stockGoodsId) {
		return dao.getSkuInfo(stockGoodsId);
	}

    /**
     * sku列表 带attrInfo
     * @param stockKeepingUnit
     * @return
     */
	public List<StockKeepingUnit> findListWithAttrInfo(StockKeepingUnit stockKeepingUnit){
	    return dao.findListWithAttrInfo(stockKeepingUnit);
    }

	/**
	 * Excel表头和实体类属性的映射
	 * @return
	 */
	public Map<String, String> fetchHeaderMapper() {
		Map<String, String> headerMapper = new LinkedHashMap<>();
		headerMapper.put("商品名称", "goodsName");
        headerMapper.put("售价","salePrice");
        headerMapper.put("库存","stock");
        headerMapper.put("单位","unit");
        headerMapper.put("扫码条码","scanCode");
        headerMapper.put("规格图片地址","attrPhoto");
        headerMapper.put("重量(kg)","weight");
        headerMapper.put("体积(立方米)","volume");
        headerMapper.put("规格属性信息","attrInfo");
		headerMapper.put("排序", "sort");
		headerMapper.put("备注", "remarks");
		// 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
		List<Dict> headerList = DictUtils.getDictList("database_sku_import");
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
		Map<String, String> commentMapper = new LinkedHashMap<>();
        commentMapper.put("商品名称", "必填项,不超过64字符");
        commentMapper.put("售价","填数字,最多2位小数");
        commentMapper.put("库存","默认库存数,填数字,整数");
        commentMapper.put("单位","规格单位,不超过10字符");
        commentMapper.put("扫码条码","条形码上的数字");
        commentMapper.put("规格图片地址","不超过500字符");
        commentMapper.put("重量(kg)","单位千克,填数字,最多3位小数");
        commentMapper.put("体积(立方米)","单位立方米,填数字,最多6位小数");
        commentMapper.put("规格属性信息","使用默认的属性名,可添加属性值,属性名与属性值间使用冒号分隔;多个属性间使用逗号分隔(格式如:尺码:XL,颜色:蓝)");
        commentMapper.put("排序", "显示时的顺序,填数字");
        commentMapper.put("备注", "备注信息,不超过500个字符");
		// 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
		List<Dict> list = DictUtils.getDictList("database_sku_import");
		if (!Collections3.isEmpty(list)) {
			commentMapper.putAll(Collections3.extractToMap(list, "value", "description"));
		}
		return commentMapper;
	}

    /**
     * 查询所有库存商品中 sku商品编号为空的  spuId 的列表
     * @return spuId/generalSn
     */
    public List<StockKeepingUnit> findEmptySnSpuList() {
        return dao.findEmptySnSpuList();
    }
}