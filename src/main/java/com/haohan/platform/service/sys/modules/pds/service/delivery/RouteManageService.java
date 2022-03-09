package com.haohan.platform.service.sys.modules.pds.service.delivery;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.dao.delivery.RouteManageDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.RouteManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 路线管理Service
 * @author haohan
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class RouteManageService extends CrudService<RouteManageDao, RouteManage> {

	public RouteManage get(String id) {
		return super.get(id);
	}
	
	public List<RouteManage> findList(RouteManage routeManage) {
		return super.findList(routeManage);
	}
	
	public Page<RouteManage> findPage(Page<RouteManage> page, RouteManage routeManage) {
		return super.findPage(page, routeManage);
	}
	
	@Transactional(readOnly = false)
	public void save(RouteManage routeManage) {
		super.save(routeManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(RouteManage routeManage) {
		super.delete(routeManage);
	}

    public List<RouteManage> findJoinList(RouteManage routeManage) {
        return dao.findJoinList(routeManage);
    }
}