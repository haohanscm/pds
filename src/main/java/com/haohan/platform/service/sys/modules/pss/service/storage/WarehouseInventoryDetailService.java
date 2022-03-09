package com.haohan.platform.service.sys.modules.pss.service.storage;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.storage.WarehouseInventoryDetailDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseInventoryDetail;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 盘点记录Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class WarehouseInventoryDetailService extends CrudService<WarehouseInventoryDetailDao, WarehouseInventoryDetail> {

	public WarehouseInventoryDetail get(String id) {
		return super.get(id);
	}
	
	public List<WarehouseInventoryDetail> findList(WarehouseInventoryDetail warehouseInventoryDetail) {
		return super.findList(warehouseInventoryDetail);
	}

	public WarehouseInventoryDetail fetchLastInventory(WarehouseInventoryDetail warehouseInventoryDetail){
		List<WarehouseInventoryDetail> list = dao.findList(warehouseInventoryDetail);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public Page<WarehouseInventoryDetail> findPage(Page<WarehouseInventoryDetail> page, WarehouseInventoryDetail warehouseInventoryDetail) {
		return super.findPage(page, warehouseInventoryDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(WarehouseInventoryDetail warehouseInventoryDetail) {
		super.save(warehouseInventoryDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(WarehouseInventoryDetail warehouseInventoryDetail) {
		super.delete(warehouseInventoryDetail);
	}
	
}