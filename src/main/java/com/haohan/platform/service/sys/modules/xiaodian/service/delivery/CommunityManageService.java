package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.CommunityManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.CommunityManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 小区信息管理Service
 * @author yu.shen
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class CommunityManageService extends CrudService<CommunityManageDao, CommunityManage> {

	public CommunityManage get(String id) {
		return super.get(id);
	}
	
	public List<CommunityManage> findList(CommunityManage communityManage) {
		return super.findList(communityManage);
	}
	
	public Page<CommunityManage> findPage(Page<CommunityManage> page, CommunityManage communityManage) {
		return super.findPage(page, communityManage);
	}

	public List<CommunityManage> findBYDistrictId(String districtId){
		CommunityManage communityManage = new CommunityManage();
		communityManage.setDistrictId(districtId);
		return super.findList(communityManage);
	}

	@Transactional(readOnly = false)
	public void save(CommunityManage communityManage) {
		super.save(communityManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommunityManage communityManage) {
		super.delete(communityManage);
	}


}