package com.haohan.platform.service.sys.modules.pss.service.info;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.info.SupplierPayrecordDao;
import com.haohan.platform.service.sys.modules.pss.entity.info.SupplierPayrecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商结款记录Service
 * @author yu
 * @version 2018-10-11
 */
@Service
@Transactional(readOnly = true)
public class SupplierPayrecordService extends CrudService<SupplierPayrecordDao, SupplierPayrecord> {

	public SupplierPayrecord get(String id) {
		return super.get(id);
	}
	
	public List<SupplierPayrecord> findList(SupplierPayrecord supplierPayrecord) {
		return super.findList(supplierPayrecord);
	}
	
	public Page<SupplierPayrecord> findPage(Page<SupplierPayrecord> page, SupplierPayrecord supplierPayrecord) {
		return super.findPage(page, supplierPayrecord);
	}
	
	@Transactional(readOnly = false)
	public void save(SupplierPayrecord supplierPayrecord) {
		super.save(supplierPayrecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(SupplierPayrecord supplierPayrecord) {
		super.delete(supplierPayrecord);
	}
	
}