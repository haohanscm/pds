package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 店铺模板管理Entity
 * @author haohan
 * @version 2017-12-25
 */
public class ShopTemplate extends DataEntity<ShopTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String industryCategory;		// 行业类型
	private String wxModelId;		// 微信模板ID
	private String appId;		// 小程序APPid
	private String templateType;		// 模板类型
	private String templateName;		// 模板名称
	private String templateDesc;		// 模板描述
	private String templatePic;		// 模板图片
	private String versionNo;		// 版本号
	private String versionDesc;		// 版本描述
	private Date uploadTime;		// 上传时间
	private String respDesc;		// 返回信息
	private Date beginUploadTime;		// 开始 上传时间
	private Date endUploadTime;		// 结束 上传时间


	private PhotoGroupManage shopTemplatePhotos;
	
	public ShopTemplate() {
		super();
	}

	public ShopTemplate(String id){
		super(id);
	}

	@Length(min=0, max=64, message="行业类型长度必须介于 0 和 64 之间")
	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}
	
	@Length(min=0, max=64, message="微信模板ID长度必须介于 0 和 64 之间")
	public String getWxModelId() {
		return wxModelId;
	}

	public void setWxModelId(String wxModelId) {
		this.wxModelId = wxModelId;
	}
	
	@Length(min=0, max=64, message="小程序APPid长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=64, message="模板类型长度必须介于 0 和 64 之间")
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	
	@Length(min=0, max=64, message="模板名称长度必须介于 0 和 64 之间")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	@Length(min=0, max=1000, message="模板描述长度必须介于 0 和 1000 之间")
	public String getTemplateDesc() {
		return templateDesc;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	
	@Length(min=0, max=200, message="模板图片长度必须介于 0 和 200 之间")
	public String getTemplatePic() {
		return templatePic;
	}

	public void setTemplatePic(String templatePic) {
		this.templatePic = templatePic;
	}
	
	@Length(min=0, max=64, message="版本号长度必须介于 0 和 64 之间")
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
	@Length(min=0, max=1000, message="版本描述长度必须介于 0 和 1000 之间")
	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	@Length(min=0, max=128, message="返回信息长度必须介于 0 和 128 之间")
	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	public Date getBeginUploadTime() {
		return beginUploadTime;
	}

	public void setBeginUploadTime(Date beginUploadTime) {
		this.beginUploadTime = beginUploadTime;
	}
	
	public Date getEndUploadTime() {
		return endUploadTime;
	}

	public void setEndUploadTime(Date endUploadTime) {
		this.endUploadTime = endUploadTime;
	}

	public PhotoGroupManage getShopTemplatePhotos() {
		return shopTemplatePhotos;
	}

	public void setShopTemplatePhotos(PhotoGroupManage shopTemplatePhotos) {
		this.shopTemplatePhotos = shopTemplatePhotos;
	}
}