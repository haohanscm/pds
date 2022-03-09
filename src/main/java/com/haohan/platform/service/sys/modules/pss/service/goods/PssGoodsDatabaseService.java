package com.haohan.platform.service.sys.modules.pss.service.goods;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.goods.PssGoodsDatabaseDao;
import com.haohan.platform.service.sys.modules.pss.entity.goods.PssGoodsDatabase;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品数据库管理Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class PssGoodsDatabaseService extends CrudService<PssGoodsDatabaseDao, PssGoodsDatabase> {

	public PssGoodsDatabase get(String id) {
		return super.get(id);
	}
	
	public List<PssGoodsDatabase> findList(PssGoodsDatabase pssGoodsDatabase) {
		return super.findList(pssGoodsDatabase);
	}

	public PssGoodsDatabase fetchByCode(String goodsCode){
		PssGoodsDatabase pssGoodsDatabase = new PssGoodsDatabase();
		pssGoodsDatabase.setGoodsCode(goodsCode);
		List<PssGoodsDatabase> list = dao.findList(pssGoodsDatabase);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public Page<PssGoodsDatabase> findPage(Page<PssGoodsDatabase> page, PssGoodsDatabase pssGoodsDatabase) {
		return super.findPage(page, pssGoodsDatabase);
	}
	
	@Transactional(readOnly = false)
	public void save(PssGoodsDatabase pssGoodsDatabase) {
		super.save(pssGoodsDatabase);
	}
	
	@Transactional(readOnly = false)
	public void delete(PssGoodsDatabase pssGoodsDatabase) {
		super.delete(pssGoodsDatabase);
	}
	
}