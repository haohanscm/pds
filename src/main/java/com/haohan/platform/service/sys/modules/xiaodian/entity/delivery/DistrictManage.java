package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 片区管理Entity
 * @author yu.shen
 * @version 2018-09-03
 */
public class DistrictManage extends DataEntity<DistrictManage> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 片区名称
	private String districtArea;		// 片区面积
	private String province;		// 省
	private String provinceName;	//省名称
	private String city;		// 市
	private String cityName;	//市名称
	private String region;		// 区县
	private String regionName;	//区县名称
	private Integer communityNum;		// 小区数量
	private Integer residents;		// 住户数量
	private Long population;		// 常住人口
	private String communityDesc;		// 片区情况
	private String communityIds;		//所有小区id
	private String merchantId;			//商家id

	//中间字段
	private String merchantName;
	private String communityNames;  // 所有小区名称
	private String queryCommunityId;		//查询包含该小区的片区
	
	public DistrictManage() {
		super();
	}

	public DistrictManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="片区名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="片区面积长度必须介于 0 和 64 之间")
	public String getDistrictArea() {
		return districtArea;
	}

	public void setDistrictArea(String districtArea) {
		this.districtArea = districtArea;
	}
	
	@Length(min=0, max=64, message="省长度必须介于 0 和 64 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=64, message="市长度必须介于 0 和 64 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=64, message="区县长度必须介于 0 和 64 之间")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public Integer getCommunityNum() {
		return communityNum;
	}

	public void setCommunityNum(Integer communityNum) {
		this.communityNum = communityNum;
	}
	
	public Integer getResidents() {
		return residents;
	}

	public void setResidents(Integer residents) {
		this.residents = residents;
	}
	
	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}
	
	@Length(min=0, max=500, message="片区情况长度必须介于 0 和 500 之间")
	public String getCommunityDesc() {
		return communityDesc;
	}

	public void setCommunityDesc(String communityDesc) {
		this.communityDesc = communityDesc;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getCommunityIds() {
		return communityIds;
	}

	public void setCommunityIds(String communityIds) {
		this.communityIds = communityIds;
	}

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCommunityNames() {
        return communityNames;
    }

    public void setCommunityNames(String communityNames) {
        this.communityNames = communityNames;
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

	public String getQueryCommunityId() {
		return queryCommunityId;
	}

	public void setQueryCommunityId(String queryCommunityId) {
		this.queryCommunityId = queryCommunityId;
	}
}