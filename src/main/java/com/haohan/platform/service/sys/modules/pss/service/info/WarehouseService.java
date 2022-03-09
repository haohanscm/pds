package com.haohan.platform.service.sys.modules.pss.service.info;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.pss.dao.info.WarehouseDao;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import com.haohan.platform.service.sys.modules.xiaodian.util.PssCommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 仓库管理Service
 * @author haohan
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class WarehouseService extends CrudService<WarehouseDao, PssWarehouse> {

	public PssWarehouse get(String id) {
		return super.get(id);
	}
	
	public List<PssWarehouse> findList(PssWarehouse warehouse) {
		return super.findList(warehouse);
	}
	
	public Page<PssWarehouse> findPage(Page<PssWarehouse> page, PssWarehouse warehouse) {
		return super.findPage(page, warehouse);
	}
	
	@Transactional(readOnly = false)
	public void save(PssWarehouse warehouse) {
		if (StringUtils.isEmpty(warehouse.getWarehouseNum())){
			String warehouseNum = PdsCommonUtil.incrIdByClass(PssWarehouse.class, IPdsConstant.WAREHOUSE_SN_PRE);
			warehouse.setWarehouseNum(warehouseNum);
		}
		super.save(warehouse);
	}
	
	@Transactional(readOnly = false)
	public void delete(PssWarehouse warehouse) {
		super.delete(warehouse);
	}
	
}