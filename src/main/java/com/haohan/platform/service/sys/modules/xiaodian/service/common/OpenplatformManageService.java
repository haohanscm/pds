package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.OpenplatformManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenplatformManage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 开放平台应用资料管理Service
 * @author haohan
 * @version 2018-02-01
 */
@Service
@Transactional(readOnly = true)
public class OpenplatformManageService extends TreeService<OpenplatformManageDao, OpenplatformManage> {

	public OpenplatformManage get(String id) {
		return super.get(id);
	}
	
	public List<OpenplatformManage> findList(OpenplatformManage openplatformManage) {
		if (StringUtils.isNotBlank(openplatformManage.getParentIds())){
			openplatformManage.setParentIds(","+openplatformManage.getParentIds()+",");
		}
		return super.findList(openplatformManage);
	}
	
	@Transactional(readOnly = false)
	public void save(OpenplatformManage openplatformManage) {
		super.save(openplatformManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpenplatformManage openplatformManage) {
		super.delete(openplatformManage);
	}

	public OpenplatformManage findByAppId(String appId){

		OpenplatformManage manage = new OpenplatformManage();
		manage.setAppId(appId);

		List<OpenplatformManage> list =  findList(manage);

		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}