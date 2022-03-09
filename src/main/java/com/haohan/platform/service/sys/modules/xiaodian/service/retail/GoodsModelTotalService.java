package com.haohan.platform.service.sys.modules.xiaodian.service.retail;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.retail.GoodsModelTotalDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModelTotal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 零售商品规格名称管理Service
 * @author haohan
 * @version 2018-09-27
 */
@Service
@Transactional(readOnly = true)
public class GoodsModelTotalService extends CrudService<GoodsModelTotalDao, GoodsModelTotal> {

	public GoodsModelTotal get(String id) {
		return super.get(id);
	}
	
	public List<GoodsModelTotal> findList(GoodsModelTotal goodsModelTotal) {
		return super.findList(goodsModelTotal);
	}
	
	public Page<GoodsModelTotal> findPage(Page<GoodsModelTotal> page, GoodsModelTotal goodsModelTotal) {
		return super.findPage(page, goodsModelTotal);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsModelTotal goodsModelTotal) {
		super.save(goodsModelTotal);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsModelTotal goodsModelTotal) {
		super.delete(goodsModelTotal);
	}

    /**
     * 根据goodsId 查询商品的规格信息
     * @param goodsId
     * @return
     */
	public List<GoodsModelTotal> findByGoodsId(String goodsId) {
		GoodsModelTotal model = new GoodsModelTotal();
		model.setGoodsId(goodsId);
		return dao.findList(model);
	}

	@Transactional(readOnly = false)
	public void deleteByGoodsId(String goodsId) {
		GoodsModelTotal goodsModelTotal = new GoodsModelTotal();
		goodsModelTotal.setGoodsId(goodsId);
		dao.deleteByGoodsId(goodsModelTotal);
	}
}