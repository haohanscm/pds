package com.haohan.platform.service.sys.modules.pds.service.business;

import java.util.List;

import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsTopNGoodsResp;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.entity.business.GoodsCollections;
import com.haohan.platform.service.sys.modules.pds.dao.business.GoodsCollectionsDao;

/**
 * 商品收藏Service
 * @author yu
 * @version 2018-12-13
 */
@Service
@Transactional(readOnly = true)
public class GoodsCollectionsService extends CrudService<GoodsCollectionsDao, GoodsCollections> {

	public GoodsCollections get(String id) {
		return super.get(id);
	}
	
	public List<GoodsCollections> findList(GoodsCollections goodsCollections) {
		return super.findList(goodsCollections);
	}
	
	public Page<GoodsCollections> findPage(Page<GoodsCollections> page, GoodsCollections goodsCollections) {
		return super.findPage(page, goodsCollections);
	}

	public Page<PdsTopNGoodsResp> selectCollectPage(Page<PdsTopNGoodsResp> page,GoodsCollections goodsCollections){
		page.setCount(dao.countPageRows(goodsCollections));
		page.initialize();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int pageStart = (pageNo-1)*pageSize;
		goodsCollections.setPageStart(pageStart);
		goodsCollections.setPageSize(pageSize);
		page.setList(dao.selectCollectPage(goodsCollections));
		return page;
	}

	public GoodsCollections fetchByGoodsId(String pmId,String uid,String goodsId){
		GoodsCollections goodsCollections = new GoodsCollections();
		goodsCollections.setPmId(pmId);
		goodsCollections.setUid(uid);
		goodsCollections.setGoodsId(goodsId);
		List<GoodsCollections> goodsCollectionsList = dao.findList(goodsCollections);
		return CollectionUtils.isEmpty(goodsCollectionsList) ? null : goodsCollectionsList.get(0);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsCollections goodsCollections) {
		super.save(goodsCollections);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsCollections goodsCollections) {
		super.delete(goodsCollections);
	}
	
}