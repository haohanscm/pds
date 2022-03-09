package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;

/**
 * 图片资源库管理DAO接口
 * @author haohan
 * @version 2018-01-12
 */
@MyBatisDao
public interface PhotoGalleryDao extends CrudDao<PhotoGallery> {
	
}