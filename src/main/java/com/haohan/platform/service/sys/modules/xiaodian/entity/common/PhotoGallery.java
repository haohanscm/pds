package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 图片资源库管理Entity
 * @author haohan
 * @version 2018-01-12
 */
public class PhotoGallery extends DataEntity<PhotoGallery> {
	
	private static final long serialVersionUID = 1L;
	private String picName;		// 图片名称
	@JsonProperty("src")
	private String picUrl;		// 图片地址
	private String picType;		// 图片类型
	private String picSize;		// 图片大小
	private String picFrom;		// 图片来源
	private String ossType;		// 存储云服务
	private String status;		// 状态
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public PhotoGallery() {
		super();
	}

	public PhotoGallery(String id){
		super(id);
	}

	@Length(min=0, max=50, message="图片名称长度必须介于 0 和 50 之间")
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}
	
	@Length(min=0, max=500, message="图片地址长度必须介于 0 和 100 之间")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@Length(min=0, max=5, message="图片类型长度必须介于 0 和 5 之间")
	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}
	
	@Length(min=0, max=50, message="图片大小长度必须介于 0 和 50 之间")
	public String getPicSize() {
		return picSize;
	}

	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	
	@Length(min=0, max=100, message="图片来源长度必须介于 0 和 100 之间")
	public String getPicFrom() {
		return picFrom;
	}

	public void setPicFrom(String picFrom) {
		this.picFrom = picFrom;
	}
	
	@Length(min=0, max=5, message="存储云服务长度必须介于 0 和 5 之间")
	public String getOssType() {
		return ossType;
	}

	public void setOssType(String ossType) {
		this.ossType = ossType;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}