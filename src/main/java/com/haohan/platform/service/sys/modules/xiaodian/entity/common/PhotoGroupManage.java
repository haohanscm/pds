package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.google.common.collect.Lists;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 图片组管理Entity
 * @author haohan
 * @version 2018-01-12
 */
public class PhotoGroupManage extends DataEntity<PhotoGroupManage> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String groupNum;		// 图片组编号
	private String groupName;		// 图片组名称
	private String categroyTag;		// 类别标签
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private List<PhotoManage> photoManageList = Lists.newArrayList();		// 子表列表

	private String photoGalleryIds;

	public PhotoGroupManage() {
		super();
	}

	public PhotoGroupManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="图片组编号长度必须介于 0 和 64 之间")
	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}
	
	@Length(min=0, max=64, message="图片组名称长度必须介于 0 和 64 之间")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Length(min=0, max=100, message="类别标签长度必须介于 0 和 100 之间")
	public String getCategroyTag() {
		return categroyTag;
	}

	public void setCategroyTag(String categroyTag) {
		this.categroyTag = categroyTag;
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
		
	public List<PhotoManage> getPhotoManageList() {
		return photoManageList;
	}

	public void setPhotoManageList(List<PhotoManage> photoManageList) {
		this.photoManageList = photoManageList;
	}

    public void config(IMerchantConstant.MerchantFilesType merchantFilesType,String merchantId) {
	    this.setGroupName(merchantFilesType.getGroupName());
	    this.setGroupNum(merchantFilesType.getGroupNum());
	    this.setMerchantId(merchantId);
    }

	public String getPhotoGalleryIds() {
		return photoGalleryIds;
	}

	public void setPhotoGalleryIds(String photoGalleryIds) {
		this.photoGalleryIds = photoGalleryIds;
	}
}