package com.haohan.platform.service.sys.modules.pss.service.storage;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.storage.GoodsAllotDetailDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllotDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品调拨明细Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class GoodsAllotDetailService extends CrudService<GoodsAllotDetailDao, GoodsAllotDetail> {

	public GoodsAllotDetail get(String id) {
		return super.get(id);
	}
	
	public List<GoodsAllotDetail> findList(GoodsAllotDetail goodsAllotDetail) {
		return super.findList(goodsAllotDetail);
	}

	public List<GoodsAllotDetail> fetchByAllotId(String allotId){
		GoodsAllotDetail goodsAllotDetail = new GoodsAllotDetail();
		goodsAllotDetail.setAllotId(allotId);
		return dao.findList(goodsAllotDetail);
	}
	
	public Page<GoodsAllotDetail> findPage(Page<GoodsAllotDetail> page, GoodsAllotDetail goodsAllotDetail) {
		return super.findPage(page, goodsAllotDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsAllotDetail goodsAllotDetail) {
		super.save(goodsAllotDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsAllotDetail goodsAllotDetail) {
		super.delete(goodsAllotDetail);
	}
	
}