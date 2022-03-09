package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 图片资源关系Entity
 * @author haohan
 * @version 2018-01-12
 */
public class PhotoManage extends DataEntity<PhotoManage> {
	
	private static final long serialVersionUID = 1L;
	private PhotoGroupManage photoGroupManage;		// 图片组编号
	private PhotoGallery photoGallery;		// 图片库ID
	private String picUrl;		// 图片地址
	private String picName;		// 图片名称
	
	public PhotoManage() {
		super();
	}

	public PhotoManage(String id){
		super(id);
	}

	public PhotoManage(PhotoGroupManage photoGroupManage){
		this.photoGroupManage = photoGroupManage;
	}
	public PhotoManage(PhotoGallery photoGallery){
		this.photoGallery = photoGallery;
	}


	public PhotoGroupManage getPhotoGroupManage() {
		return photoGroupManage;
	}

	public void setPhotoGroupManage(PhotoGroupManage photoGroupManage) {
		this.photoGroupManage = photoGroupManage;
	}
	
	public PhotoGallery getPhotoGallery() {
		return photoGallery;
	}

	public void setPhotoGallery(PhotoGallery photoGallery) {
		this.photoGallery = photoGallery;
	}
	
	@Length(min=0, max=500, message="图片地址长度必须介于 0 和 500 之间")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@Length(min=0, max=50, message="图片名称长度必须介于 0 和 50 之间")
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}
	
}