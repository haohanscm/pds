package com.haohan.platform.service.sys.modules.weixin.open.service;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.weixin.open.dao.AuthAppDao;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 授权应用管理Service
 * @author haohan
 * @version 2018-01-05
 */
@Service
@Transactional(readOnly = true)
public class AuthAppService extends CrudService<AuthAppDao, AuthApp> {

	public AuthApp get(String id) {
		return super.get(new AuthApp(id));
	}
	
	public List<AuthApp> findList(AuthApp authApp) {
		return super.findList(authApp);
	}
	
	public Page<AuthApp> findPage(Page<AuthApp> page, AuthApp authApp) {
		return super.findPage(page, authApp);
	}
	
	@Transactional(readOnly = false)
	public void save(AuthApp authApp) {
		super.save(authApp);
	}
	
	@Transactional(readOnly = false)
	public void delete(AuthApp authApp) {
		super.delete(authApp);
	}

	public AuthApp fetchByAppId(String appId){
		AuthApp authApp = new AuthApp();
		authApp.setAppId(appId);
		List<AuthApp> list = findList(authApp);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

    public AuthApp fetchByAppGhId(String ghId) {
		AuthApp authApp = new AuthApp();
		authApp.setOriginalAppid(ghId);
		List<AuthApp> list = findList(authApp);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
    }
}