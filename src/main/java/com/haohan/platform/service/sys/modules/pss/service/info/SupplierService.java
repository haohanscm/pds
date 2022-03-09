package com.haohan.platform.service.sys.modules.pss.service.info;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.info.SupplierDao;
import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商管理Service
 * @author haohan
 * @version 2018-09-06
 */
@Service
@Transactional(readOnly = true)
public class SupplierService extends CrudService<SupplierDao, Supplier> {

	public Supplier get(String id) {
		return super.get(id);
	}
	
	public List<Supplier> findList(Supplier supplier) {
		return super.findList(supplier);
	}
	
	public Page<Supplier> findPage(Page<Supplier> page, Supplier supplier) {
		return super.findPage(page, supplier);
	}
	
	@Transactional(readOnly = false)
	public void save(Supplier supplier) {
		super.save(supplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(Supplier supplier) {
		super.delete(supplier);
	}
	
}