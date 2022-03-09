package com.haohan.platform.service.sys.modules.xiaodian.service.common;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOssConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.common.PhotoGalleryDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图片资源库管理Service
 * @author haohan
 * @version 2018-01-12
 */
@Service
@Transactional(readOnly = true)
public class PhotoGalleryService extends CrudService<PhotoGalleryDao, PhotoGallery> {

	
	public PhotoGallery get(String id) {
		PhotoGallery photoGallery = super.get(id);
		return photoGallery;
	}
	
	public List<PhotoGallery> findList(PhotoGallery photoGallery) {
		return super.findList(photoGallery);
	}
	
	public Page<PhotoGallery> findPage(Page<PhotoGallery> page, PhotoGallery photoGallery) {
		return super.findPage(page, photoGallery);
	}
	
	@Transactional(readOnly = false)
	public void save(PhotoGallery photoGallery) {
		super.save(photoGallery);
	}
	
	@Transactional(readOnly = false)
	public void delete(PhotoGallery photoGallery) {
		super.delete(photoGallery);
	}

	// 上传阿里云图片 设置
    public PhotoGallery transfPhoto(PhotoGallery photoGallery, String photoSize, String photoName, String photoUrl, String photoType){
        photoGallery.setPicSize(photoSize);
        photoGallery.setPicType(photoType);
        photoGallery.setOssType(IOssConstant.aliyunCode);
        photoGallery.setPicName(photoName);
        photoGallery.setPicUrl(photoUrl);
        photoGallery.setPicFrom("platform");
        photoGallery.setStatus(IMerchantConstant.available);
        return photoGallery;
    }
	
}