package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.ServiceSelectionDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ServiceSelection;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务选项管理Service
 * @author haohan
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class ServiceSelectionService extends CrudService<ServiceSelectionDao, ServiceSelection> {

	public ServiceSelection get(String id) {
		return super.get(id);
	}
	
	public List<ServiceSelection> findList(ServiceSelection serviceSelection) {
		return super.findList(serviceSelection);
	}
	
	public Page<ServiceSelection> findPage(Page<ServiceSelection> page, ServiceSelection serviceSelection) {
		return super.findPage(page, serviceSelection);
	}

	public List<ServiceSelection> findByGoodsId(String goodsId){
		ServiceSelection serviceSelection =  new ServiceSelection();
		serviceSelection.setGoodsId(goodsId);
		List<ServiceSelection> list = super.findList(serviceSelection);
		return CollectionUtils.isEmpty(list)?null:list;
	}
	
	@Transactional(readOnly = false)
	public void save(ServiceSelection serviceSelection) {
		super.save(serviceSelection);
	}
	
	@Transactional(readOnly = false)
	public void delete(ServiceSelection serviceSelection) {
		super.delete(serviceSelection);
	}

	@Transactional(readOnly = false)
	public void deleteByGoodsId(String goodsId) {
		ServiceSelection serviceSelection = new ServiceSelection();
		serviceSelection.setGoodsId(goodsId);
		dao.deleteByGoodsId(serviceSelection);
	}
}