/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.sys.service;

import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.dao.AreaDao;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
//		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	public Area fetchByName(String name) {
        Area area = new Area();
        area.setName(name);
        List<Area> list = dao.fetchByName(area);
        // 有重复的情况下返回空
        return list.size() == 1 ? list.get(0) : null;
    }

	public List<Area> findChildren(String parentId){
		Area area = new Area();
		if (StringUtils.isNotEmpty(parentId)){
			Area a = get(parentId);
			if (null != a){
				area.setParent(get(parentId));
				return super.findList(area);
			}
		}
		return null;
	}

	public Area fetchByCode(String code){
		if (StringUtils.isEmpty(code)){
			return null;
		}
		Area area = new Area();
		area.setCode(code);
		List<Area> areaList = dao.findList(area);
		return CollectionUtils.isEmpty(areaList)?null:areaList.get(0);
	}

    /**
     * 无关联表 查询条件
     * @param area
     * @return
     */
    public List<Area> findBaseList(Area area){
        return dao.findBaseList(area);
    }

}
