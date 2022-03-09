package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.PhotoManageDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图片资源关系Service
 * @author haohan
 * @version 2018-01-12
 */
@Service
@Transactional(readOnly = true)
public class PhotoManageService extends CrudService<PhotoManageDao, PhotoManage> {

	public PhotoManage get(String id) {
		return super.get(id);
	}
	
	public List<PhotoManage> findList(PhotoManage photoManage) {
		return super.findList(photoManage);
	}
	
	public Page<PhotoManage> findPage(Page<PhotoManage> page, PhotoManage photoManage) {
		return super.findPage(page, photoManage);
	}
	
	@Transactional(readOnly = false)
	public void save(PhotoManage photoManage) {
		super.save(photoManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(PhotoManage photoManage) {
		super.delete(photoManage);
	}

	public List<PhotoManage> fetchByGroupNum(String groupNum){
		PhotoManage photoManage = new PhotoManage();
		PhotoGroupManage photoGroupManage = new PhotoGroupManage();
		photoGroupManage.setGroupNum(groupNum);
		photoManage.setPhotoGroupManage(photoGroupManage);
		return super.findList(photoManage);
	}
}