package com.haohan.platform.service.sys.modules.pss.service.storage;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pss.dao.storage.WareHouseInventoryDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WareHouseInventory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 库存盘点Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class WareHouseInventoryService extends CrudService<WareHouseInventoryDao, WareHouseInventory> {

	public WareHouseInventory get(String id) {
		return super.get(id);
	}
	
	public List<WareHouseInventory> findList(WareHouseInventory wareHouseInventory) {
		return super.findList(wareHouseInventory);
	}
	
	public Page<WareHouseInventory> findPage(Page<WareHouseInventory> page, WareHouseInventory wareHouseInventory) {
		return super.findPage(page, wareHouseInventory);
	}
	
	@Transactional(readOnly = false)
	public void save(WareHouseInventory wareHouseInventory) {
		super.save(wareHouseInventory);
	}
	
	@Transactional(readOnly = false)
	public void delete(WareHouseInventory wareHouseInventory) {
		super.delete(wareHouseInventory);
	}
	
}