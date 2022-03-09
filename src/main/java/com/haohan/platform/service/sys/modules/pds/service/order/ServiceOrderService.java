package com.haohan.platform.service.sys.modules.pds.service.order;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.dao.order.ServiceOrderDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 售后单Service
 * @author haohan
 * @version 2018-10-20
 */
@Service
@Transactional(readOnly = true)
public class ServiceOrderService extends CrudService<ServiceOrderDao, ServiceOrder> {

	public ServiceOrder get(String id) {
		return super.get(id);
	}
	
	public List<ServiceOrder> findList(ServiceOrder serviceOrder) {
		return super.findList(serviceOrder);
	}
	
	public Page<ServiceOrder> findPage(Page<ServiceOrder> page, ServiceOrder serviceOrder) {
		return super.findPage(page, serviceOrder);
	}

	//  带pmName / buyerName / supplierName
	public List<ServiceOrder> findJoinList(ServiceOrder serviceOrder) {
		return dao.findJoinList(serviceOrder);
	}

	@Transactional(readOnly = false)
	public void save(ServiceOrder serviceOrder) {
        if(StringUtils.isEmpty(serviceOrder.getServiceId())){
            String serviceId = PdsCommonUtil.incrIdByClass(ServiceOrder.class, IPdsConstant.SERVICEORDER_PREFIX);
            serviceOrder.setServiceId(serviceId);
        }
		super.save(serviceOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(ServiceOrder serviceOrder) {
		super.delete(serviceOrder);
	}
	
}