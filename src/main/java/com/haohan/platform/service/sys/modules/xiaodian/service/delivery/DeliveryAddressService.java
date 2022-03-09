package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.DeliveryAddressDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryAddress;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配送信息Service
 * @author shenyu
 * @version 2018-08-18
 */
@Service
@Transactional(readOnly = true)
public class DeliveryAddressService extends CrudService<DeliveryAddressDao, DeliveryAddress> {

	public DeliveryAddress get(String id) {
		return super.get(id);
	}
	
	public List<DeliveryAddress> findList(DeliveryAddress deliveryAddress) {
		return super.findList(deliveryAddress);
	}
	
	public Page<DeliveryAddress> findPage(Page<DeliveryAddress> page, DeliveryAddress deliveryAddress) {
		return super.findPage(page, deliveryAddress);
	}

	public List<DeliveryAddress> findByUid(String uid){
		DeliveryAddress address = new DeliveryAddress();
		address.setUuid(uid);
		return super.findList(address);
	}

	public DeliveryAddress fetchByJsAppIdAndjsAddressId(String jsAppId,String jsAddressId){
		if (StringUtils.isAnyBlank(jsAppId,jsAddressId)){
			return null;
		}else {
			DeliveryAddress deliveryAddress = new DeliveryAddress();
			deliveryAddress.setJsAppId(jsAppId);
			deliveryAddress.setJsAddressId(jsAddressId);
			List<DeliveryAddress> list = super.findList(deliveryAddress);
			return CollectionUtils.isEmpty(list)?null:list.get(0);
		}
	}

	public List<DeliveryAddress> findByMercIdAndUid(String merchantId,String uid){
		DeliveryAddress deliveryAddress = new DeliveryAddress();
		deliveryAddress.setMerchantId(merchantId);
		deliveryAddress.setUuid(uid);
		List<DeliveryAddress> list = dao.findList(deliveryAddress);
		return list;
	}

	@Transactional(readOnly = false)
	public void save(DeliveryAddress deliveryAddress) {
		super.save(deliveryAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeliveryAddress deliveryAddress) {
		super.delete(deliveryAddress);
	}
	
}