package com.haohan.platform.service.sys.modules.xiaodian.service.delivery;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.BuildingsManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.BuildingsManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 楼栋管理Service
 * @author yu.shen
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class BuildingsManageService extends TreeService<BuildingsManageDao, BuildingsManage> {

	public BuildingsManage get(String id) {
		return super.get(id);
	}
	
	public List<BuildingsManage> findList(BuildingsManage buildingsManage) {
		if (StringUtils.isNotBlank(buildingsManage.getParentIds())){
			buildingsManage.setParentIds(","+buildingsManage.getParentIds()+",");
		}
		return super.findList(buildingsManage);
	}
	
	@Transactional(readOnly = false)
	public void save(BuildingsManage buildingsManage) {
		super.save(buildingsManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(BuildingsManage buildingsManage) {
		super.delete(buildingsManage);
	}
	
}