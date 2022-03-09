package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.GoodsGiftsDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.GoodsGifts;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 赠品管理Service
 * @author haohan
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class GoodsGiftsService extends CrudService<GoodsGiftsDao, GoodsGifts> {

	public GoodsGifts get(String id) {
		return super.get(id);
	}
	
	public List<GoodsGifts> findList(GoodsGifts goodsGifts) {
		return super.findList(goodsGifts);
	}
	
	public Page<GoodsGifts> findPage(Page<GoodsGifts> page, GoodsGifts goodsGifts) {
		return super.findPage(page, goodsGifts);
	}

	public GoodsGifts findByGoodsId(String goodsId){
		GoodsGifts goodsGifts =  new GoodsGifts();
		goodsGifts.setGoodsId(goodsId);
		List<GoodsGifts> list = super.findList(goodsGifts);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsGifts goodsGifts) {
		super.save(goodsGifts);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsGifts goodsGifts) {
		super.delete(goodsGifts);
	}

	@Transactional(readOnly = false)
	public void deleteByGoodsId(String goodsId) {
		GoodsGifts goodsGifts = new GoodsGifts();
		goodsGifts.setGoodsId(goodsId);
		dao.deleteByGoodsId(goodsGifts);
	}
}