package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.modules.xiaodian.cache.ShopServiceDistrictCache;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.ShopServiceDistrictDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ShopServiceDistrict;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺服务小区管理Service
 * @author yu.shen
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class ShopServiceDistrictService extends CrudService<ShopServiceDistrictDao, ShopServiceDistrict> {

	@Autowired
	private ShopServiceDistrictCache shopServiceDistrictCache;
	public ShopServiceDistrict get(String id) {
		return super.get(id);
	}
	
	public List<ShopServiceDistrict> findList(ShopServiceDistrict shopServiceDistrict) {
		List<ShopServiceDistrict> shopServiceDistrictList = null;
		if (!shopServiceDistrictCache.isCache()) {
			return super.findList(shopServiceDistrict);
		}
		//缓存启用,从缓存获取
		shopServiceDistrictList = shopServiceDistrictCache.findListT(shopServiceDistrict);
		//缓存未找到,从数据库查询
		if (CollectionUtils.isEmpty(shopServiceDistrictList)) {
			shopServiceDistrictList = super.findList(shopServiceDistrict);
			//判断数据是否为空,更新缓存
			if (CollectionUtils.isNotEmpty(shopServiceDistrictList)) {
				shopServiceDistrictCache.insertListT(shopServiceDistrict, shopServiceDistrictList);
			}
		}
		return shopServiceDistrictList;
	}
	
	public Page<ShopServiceDistrict> findPage(Page<ShopServiceDistrict> page, ShopServiceDistrict shopServiceDistrict) {
		return super.findPage(page, shopServiceDistrict);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopServiceDistrict shopServiceDistrict) {
		super.save(shopServiceDistrict);

		if(shopServiceDistrictCache.isCache()){
			shopServiceDistrictCache.clearListT();
		}
		JedisUtils.del(shopServiceDistrict.getId());
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopServiceDistrict shopServiceDistrict) {
		super.delete(shopServiceDistrict);

		if(shopServiceDistrictCache.isCache()){
			shopServiceDistrictCache.clearListT();
		}
		JedisUtils.del(shopServiceDistrict.getId());
	}

	public List<ShopServiceDistrict> fetchByShopIdEnable(String shopId){
		ShopServiceDistrict shopServiceDistrict = new ShopServiceDistrict();
		shopServiceDistrict.setShopId(shopId);
		shopServiceDistrict.setStatus(IMerchantConstant.available);
		List<ShopServiceDistrict> list = dao.findList(shopServiceDistrict);
		return CollectionUtils.isEmpty(list)?null:list;
	}


	
}