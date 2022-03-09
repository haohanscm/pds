package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.order.MarketplaceDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.Marketplace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 市场报价Service
 * @author haohan
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly = true)
public class MarketplaceService extends CrudService<MarketplaceDao, Marketplace> {

	public Marketplace get(String id) {
		return super.get(id);
	}
	
	public List<Marketplace> findList(Marketplace marketplace) {
		return super.findList(marketplace);
	}
	
	public Page<Marketplace> findPage(Page<Marketplace> page, Marketplace marketplace) {
		return super.findPage(page, marketplace);
	}
	
	@Transactional(readOnly = false)
	public void save(Marketplace marketplace) {
		super.save(marketplace);
	}
	
	@Transactional(readOnly = false)
	public void delete(Marketplace marketplace) {
		super.delete(marketplace);
	}
	
}