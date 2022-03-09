package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 配送员服务区域Entity
 * @author yu.shen
 * @version 2018-08-31
 */
public class DeliverServiceArea extends DataEntity<DeliverServiceArea> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家id
	private String merchantName;	//商家名称
	private String shopId;		// 店铺id
	private String shopName;	//店铺名称
	private String deliverManId;		// 配送员编号
	private String communityId;		// 服务小区编号

	//补充字段
	private String deliverManName;
	private String communityName;
	
	public DeliverServiceArea() {
		super();
	}

	public DeliverServiceArea(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="店铺id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="配送员编号长度必须介于 0 和 64 之间")
	public String getDeliverManId() {
		return deliverManId;
	}

	public void setDeliverManId(String deliverManId) {
		this.deliverManId = deliverManId;
	}
	
	@Length(min=0, max=64, message="服务小区编号长度必须介于 0 和 64 之间")
	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDeliverManName() {
		return deliverManName;
	}

	public void setDeliverManName(String deliverManName) {
		this.deliverManName = deliverManName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
}