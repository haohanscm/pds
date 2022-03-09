package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.utils.TreeDictUtils;
import org.hibernate.validator.constraints.Length;

/**
 * 店铺服务小区管理Entity
 * @author yu.shen
 * @version 2018-08-31
 */
public class ShopServiceDistrict extends DataEntity<ShopServiceDistrict> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家id
	private String merchantName;		// 商家名称
	private String shopId;		// 店铺id
	private String shopName;		// 店铺名称
	private String province;	//省
	private String city;		//市
	private String region;		//区县
	private String districtAreaId;		// 服务片区id
	private String communityId;		// 服务小区id
	private String status;		// 状态
	private String serviceType;	//服务类型 0:预定系统  1:采购系统

	//查询补充参数
	private String districtAreaName;	//片区名称
	private String communityName;		//小区名称
	
	public ShopServiceDistrict() {
		super();
	}

	public ShopServiceDistrict(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商家名称长度必须介于 0 和 64 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=64, message="店铺id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="店铺名称长度必须介于 0 和 64 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Length(min=0, max=64, message="服务片区id长度必须介于 0 和 64 之间")
	public String getDistrictAreaId() {
		return districtAreaId;
	}

	public void setDistrictAreaId(String districtAreaId) {
		this.districtAreaId = districtAreaId;
	}
	
	@Length(min=0, max=64, message="服务小区id长度必须介于 0 和 64 之间")
	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrictAreaName() {
		return districtAreaName;
	}

	public void setDistrictAreaName(String districtAreaName) {
		this.districtAreaName = districtAreaName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getDetailAddressName(){
		StringBuilder sb = new StringBuilder();
		String addressName = JedisUtils.get(this.getId());
		if (StringUtils.isEmpty(addressName)){
			sb.append(TreeDictUtils.getTreeDictName("01",this.getProvince()));
			sb.append(TreeDictUtils.getTreeDictName("01",this.getCity()));
			sb.append(TreeDictUtils.getTreeDictName("01",this.getRegion()));
			JedisUtils.set(this.getId(),sb.toString(),86400);
			return sb.toString();
		}
		return addressName;
	}
	
}