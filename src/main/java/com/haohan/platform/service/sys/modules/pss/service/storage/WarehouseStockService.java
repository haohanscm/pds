package com.haohan.platform.service.sys.modules.pss.service.storage;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.storage.WarehouseStockDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 库存管理Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class WarehouseStockService extends CrudService<WarehouseStockDao, WarehouseStock> {

	public WarehouseStock get(String id) {
		return super.get(id);
	}
	
	public List<WarehouseStock> findList(WarehouseStock warehouseStock) {
		return super.findList(warehouseStock);
	}

	public WarehouseStock fetchStockGoods(String goodsCode, String warehouseId){
		WarehouseStock warehouseStock = new WarehouseStock();
		warehouseStock.setGoodsCode(goodsCode);
		warehouseStock.setWarehouseId(warehouseId);
//		warehouseStock.setSupplierId(supplierId);
		List<WarehouseStock> list = findList(warehouseStock);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public List<WarehouseStock> findByModelAndWarehouse(String goodsCode, String warehouseId){
		WarehouseStock warehouseStock = new WarehouseStock();
		warehouseStock.setGoodsCode(goodsCode);
		warehouseStock.setWarehouseId(warehouseId);
		return dao.findList(warehouseStock);
	}

	public Page<WarehouseStock> findPage(Page<WarehouseStock> page, WarehouseStock warehouseStock) {
		return super.findPage(page, warehouseStock);
	}
	
	@Transactional(readOnly = false)
	public void save(WarehouseStock warehouseStock) {
		super.save(warehouseStock);
	}
	
	@Transactional(readOnly = false)
	public void delete(WarehouseStock warehouseStock) {
		super.delete(warehouseStock);
	}
	
}