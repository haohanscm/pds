package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 配送员管理Entity
 * @author yu.shen
 * @version 2018-08-31
 */
public class DeliverManManage extends DataEntity<DeliverManManage> {
	
	private static final long serialVersionUID = 1L;
	private String realName;		// 姓名
	private String mobile;		// 手机号
	private String nickName;		// 昵称
	private String avatar;		// 头像
	private String photo;		// 照片
	private String merchantId; 	//商家id
	private String shopId;		// 所属店铺
	private String shopName;	//店铺名称
	private String level;		// 星级
	private Integer totalDeliveryTimes;		// 总配送次数
	private String status;		// 状态
	private Date joinDate;		// 加入日期
	private String uid; // 通行证Id

	public DeliverManManage() {
		super();
	}

	public DeliverManManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Length(min=0, max=64, message="手机号长度必须介于 0 和 64 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=64, message="昵称长度必须介于 0 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=64, message="所属店铺长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="星级长度必须介于 0 和 64 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public Integer getTotalDeliveryTimes() {
		return totalDeliveryTimes;
	}

	public void setTotalDeliveryTimes(Integer totalDeliveryTimes) {
		this.totalDeliveryTimes = totalDeliveryTimes;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}