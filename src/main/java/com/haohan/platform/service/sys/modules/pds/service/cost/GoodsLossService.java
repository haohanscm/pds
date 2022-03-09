package com.haohan.platform.service.sys.modules.pds.service.cost;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.cost.GoodsLossDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.GoodsLoss;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品损耗管理Service
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class GoodsLossService extends CrudService<GoodsLossDao, GoodsLoss> {

	public GoodsLoss get(String id) {
		return super.get(id);
	}
	
	public List<GoodsLoss> findList(GoodsLoss goodsLoss) {
		return super.findList(goodsLoss);
	}

	public GoodsLoss fetchByGoodsId(String goodsId){
		GoodsLoss goodsLoss = new GoodsLoss();
		goodsLoss.setGoodsId(goodsId);
		List<GoodsLoss> lossList = dao.findList(goodsLoss);
		return CollectionUtils.isEmpty(lossList)?null:lossList.get(0);
	}
	
	public Page<GoodsLoss> findPage(Page<GoodsLoss> page, GoodsLoss goodsLoss) {
		return super.findPage(page, goodsLoss);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsLoss goodsLoss) {
		super.save(goodsLoss);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsLoss goodsLoss) {
		super.delete(goodsLoss);
	}

    public List<GoodsLoss> findJoinList(GoodsLoss goodsLoss) {
        return dao.findJoinList(goodsLoss);
    }
}