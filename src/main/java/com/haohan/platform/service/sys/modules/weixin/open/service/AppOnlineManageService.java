package com.haohan.platform.service.sys.modules.weixin.open.service;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.weixin.open.dao.AppOnlineManageDao;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AppOnlineManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 应用上线管理Service
 * @author haohan
 * @version 2017-12-26
 */
@Service
@Transactional(readOnly = true)
public class AppOnlineManageService extends CrudService<AppOnlineManageDao, AppOnlineManage> {

	public AppOnlineManage get(String id) {
		return super.get(id);
	}
	
	public List<AppOnlineManage> findList(AppOnlineManage appOnlineManage) {
		return super.findList(appOnlineManage);
	}
	
	public Page<AppOnlineManage> findPage(Page<AppOnlineManage> page, AppOnlineManage appOnlineManage) {
		return super.findPage(page, appOnlineManage);
	}
	
	@Transactional(readOnly = false)
	public void save(AppOnlineManage appOnlineManage) {
		super.save(appOnlineManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppOnlineManage appOnlineManage) {
		super.delete(appOnlineManage);
	}



}