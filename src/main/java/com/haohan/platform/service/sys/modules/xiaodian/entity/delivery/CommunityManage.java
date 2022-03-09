package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 小区信息管理Entity
 * @author yu.shen
 * @version 2018-08-31
 */
public class CommunityManage extends DataEntity<CommunityManage> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		//所属商家
	private String street;		// 所属街道
	private String districtId;		// 片区ID
	private String name;		// 小区名称
	private String alias;		// 别名
	private String developer;		// 开发商
	private Integer buildings;		// 楼栋数量
	private Integer residents;		// 户数
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String areas;		// 所占面积
	private String detailAddress;		// 具体位置
	private String postCode;		// 邮编
	private String status;		// 状态
	private String province;		// 省
	private String city;		// 市
	private String region;		// 区县

	//查询补充参数
	private Boolean queryUnSelected;

	public CommunityManage() {
		super();
	}

	public CommunityManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="所属街道长度必须介于 0 和 64 之间")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	@Length(min=0, max=64, message="片区ID长度必须介于 0 和 64 之间")
	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	@Length(min=0, max=64, message="小区名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="别名长度必须介于 0 和 64 之间")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Length(min=0, max=64, message="开发商长度必须介于 0 和 64 之间")
	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	
	public Integer getBuildings() {
		return buildings;
	}

	public void setBuildings(Integer buildings) {
		this.buildings = buildings;
	}
	
	public Integer getResidents() {
		return residents;
	}

	public void setResidents(Integer residents) {
		this.residents = residents;
	}
	
	@Length(min=0, max=64, message="经度长度必须介于 0 和 64 之间")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Length(min=0, max=64, message="纬度长度必须介于 0 和 64 之间")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Length(min=0, max=64, message="所占面积长度必须介于 0 和 64 之间")
	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}
	
	@Length(min=0, max=64, message="具体位置长度必须介于 0 和 64 之间")
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	@Length(min=0, max=64, message="邮编长度必须介于 0 和 64 之间")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Boolean getQueryUnSelected() {
		return queryUnSelected;
	}

	public void setQueryUnSelected(Boolean queryUnSelected) {
		this.queryUnSelected = queryUnSelected;
	}

}