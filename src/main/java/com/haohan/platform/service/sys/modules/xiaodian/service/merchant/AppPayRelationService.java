package com.haohan.platform.service.sys.modules.xiaodian.service.merchant;

import java.util.List;

import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AppPayRelation;
import com.haohan.platform.service.sys.modules.xiaodian.dao.merchant.AppPayRelationDao;

/**
 * app支付账户Service
 * @author yu.shen
 * @version 2019-01-15
 */
@Service
@Transactional(readOnly = true)
public class AppPayRelationService extends CrudService<AppPayRelationDao, AppPayRelation> {

	public AppPayRelation get(String id) {
		return super.get(id);
	}
	
	public List<AppPayRelation> findList(AppPayRelation appPayRelation) {
		return super.findList(appPayRelation);
	}

    /**
     * 带appName
     * @param appPayRelation
     * @return
     */
	public List<AppPayRelation> findJoinList(AppPayRelation appPayRelation) {
		return dao.findJoinList(appPayRelation);
	}

	public Page<AppPayRelation> findPage(Page<AppPayRelation> page, AppPayRelation appPayRelation) {
		return super.findPage(page, appPayRelation);
	}
	
	@Transactional(readOnly = false)
	public void save(AppPayRelation appPayRelation) {
		super.save(appPayRelation);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppPayRelation appPayRelation) {
		super.delete(appPayRelation);
	}

	public AppPayRelation fetchByAppId(String appId) {
		AppPayRelation appPayRelation = new AppPayRelation();
		appPayRelation.setStatus(ICommonConstant.IsEnable.enable.getCode());
		appPayRelation.setAppId(appId);
		List<AppPayRelation> list = super.findList(appPayRelation);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);

	}
}