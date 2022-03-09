package com.haohan.platform.service.sys.modules.pds.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.delivery.TruckManageDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 车辆管理Service
 * @author haohan
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class TruckManageService extends CrudService<TruckManageDao, TruckManage> {

	public TruckManage get(String id) {
		return super.get(id);
	}
	
	public List<TruckManage> findList(TruckManage truckManage) {
		return super.findList(truckManage);
	}

	public List<TruckManage> findBuyOrderTrucks(String buyId){
		return dao.findBuyOrderTrucks(buyId);
	}

	public TruckManage fetchByDriver(String driverId){
		TruckManage truckManage = new TruckManage();
		truckManage.setDriver(driverId);
		List<TruckManage> truckManageList = dao.findList(truckManage);
		return CollectionUtils.isEmpty(truckManageList)?null:truckManageList.get(0);
	}

	public List<TruckManage> findEmptyEnableList(TruckManage truckManage) {
		truckManage.setStatus(IPdsConstant.TruckStatus.empty.getCode());
		return super.findList(truckManage);
	}

	public Page<TruckManage> findPage(Page<TruckManage> page, TruckManage truckManage) {
		return super.findPage(page, truckManage);
	}
	
	@Transactional(readOnly = false)
	public void save(TruckManage truckManage) {
		super.save(truckManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(TruckManage truckManage) {
		super.delete(truckManage);
	}

    public List<TruckManage> findJoinList(TruckManage truckManage) {
        return dao.findJoinList(truckManage);
    }
}