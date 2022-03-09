package com.haohan.platform.service.sys.modules.xiaodian.service;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.BrandDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 品牌Service
 * @author haohan
 * @version 2017-08-05
 */
@Service
@Transactional(readOnly = true)
public class BrandService extends CrudService<BrandDao, Brand> {

	public Brand get(String id) {
		return super.get(id);
	}
	
	public List<Brand> findList(Brand brand) {
		return super.findList(brand);
	}
	
	public Page<Brand> findPage(Page<Brand> page, Brand brand) {
		return super.findPage(page, brand);
	}
	
	@Transactional(readOnly = false)
	public void save(Brand brand) {
		super.save(brand);
	}
	
	@Transactional(readOnly = false)
	public void delete(Brand brand) {
		super.delete(brand);
	}
	
}