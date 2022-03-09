package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 配送信息Entity
 * @author shenyu
 * @version 2018-08-18
 */
public class DeliveryAddress extends DataEntity<DeliveryAddress> {
	private static final long serialVersionUID = 1L;
	private String merchantId;		//商家id
	private String uuid;		// 通行证id
	private String receiver;		// 收货人
	private String receiverMobile;		// 收货人手机
	private String province;		//省份
	private String provinceName;		//省中文名
	private String city;			//城市
	private String cityName;			//市中文名
	private String region;			//地区
	private String regionName;				//区县中文名
	private String street;			//街道
	private String districtArea;	//片区
	private String districtAreaName;	//片区名称
	private String communityId;		//小区
	private String communityName;	//小区名称
	private String buildingId;		//楼栋
	private String floor;			//楼层
	private String extAddress;		//补充信息
	private String isDefault;		//是否默认	yes/no
	private String tag;				//标签: 家,公司,学校
//	private String receiveAddress;		// 收货详细地址

	private String jsAppId;			//即速appid
	private String jsAddressId;		//即速地址id


	public DeliveryAddress() {
		super();
	}

	public DeliveryAddress(String id){
		super(id);
	}

	@Length(min=0, max=64, message="通行证id长度必须介于 0 和 64 之间")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Length(min=0, max=64, message="收货人长度必须介于 0 和 64 之间")
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@Length(min=0, max=64, message="收货人手机长度必须介于 0 和 64 之间")
	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	
	@Length(min=0, max=64, message="收货详细地址长度必须介于 0 和 64 之间")
	public String getReceiveAddress() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.provinceName);
		sb.append(this.cityName);
		sb.append(this.regionName);
		sb.append(this.districtAreaName);
		sb.append(this.communityName);
		sb.append(this.extAddress);
		return sb.toString();
	}

//	public void setReceiveAddress(String receiveAddress) {
//		this.receiveAddress = receiveAddress;
//	}
	
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrictArea() {
		return districtArea;
	}

	public void setDistrictArea(String districtArea) {
		this.districtArea = districtArea;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getExtAddress() {
		return extAddress;
	}

	public void setExtAddress(String extAddress) {
		this.extAddress = extAddress;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getJsAddressId() {
		return jsAddressId;
	}

	public void setJsAddressId(String jsAddressId) {
		this.jsAddressId = jsAddressId;
	}

	public String getJsAppId() {
		return jsAppId;
	}

	public void setJsAppId(String jsAppId) {
		this.jsAppId = jsAppId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getDistrictAreaName() {
		return districtAreaName;
	}

	public void setDistrictAreaName(String districtAreaName) {
		this.districtAreaName = districtAreaName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}