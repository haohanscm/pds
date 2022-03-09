package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.DistrictManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DistrictManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 片区管理Service
 * @author yu.shen
 * @version 2018-09-03
 */
@Service
@Transactional(readOnly = true)
public class DistrictManageService extends CrudService<DistrictManageDao, DistrictManage> {

	public DistrictManage get(String id) {
		return super.get(id);
	}
	
	public List<DistrictManage> findList(DistrictManage districtManage) {
		return super.findList(districtManage);
	}

	public List<DistrictManage> findListJoin(DistrictManage districtManage) {
		return dao.findListJoin(districtManage);
	}

	public Page<DistrictManage> findPage(Page<DistrictManage> page, DistrictManage districtManage) {
		districtManage.setPage(page);
		page.setList(dao.findListJoin(districtManage));
		return page;
	}

	public List<DistrictManage> findByRegionCode(String merchantId,String regionCode){
		DistrictManage districtManage = new DistrictManage();
		districtManage.setMerchantId(merchantId);
		districtManage.setRegion(regionCode);
		return super.findList(districtManage);
	}
	
	@Transactional(readOnly = false)
	public void save(DistrictManage districtManage) {
		super.save(districtManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(DistrictManage districtManage) {
		super.delete(districtManage);
	}
	
}