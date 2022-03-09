package com.haohan.platform.service.sys.modules.pds.entity.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 车辆管理Entity
 * @author haohan
 * @version 2018-10-18
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TruckManage extends DataEntity<TruckManage> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;		//平台商家ID
	private String truckNo;		// 车辆编号
	private String brand;		// 车辆品牌
	private String truckName;		// 名称
	private String principal;		// 负责人
	private String driver;		// 司机
	private String telephone;		// 联系电话
	private Integer carryWeight;		// 车辆载重Kg
	private Integer carryVolume;		// 车辆容积m3
	private String status;		// 状态

    private String pmName;		// 平台商家名称

	public TruckManage() {
		super();
	}

	public TruckManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="车辆编号长度必须介于 0 和 64 之间")
	public String getTruckNo() {
		return truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}
	
	@Length(min=0, max=64, message="车辆品牌长度必须介于 0 和 64 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getTruckName() {
		return truckName;
	}

	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}
	
	@Length(min=0, max=64, message="负责人长度必须介于 0 和 64 之间")
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	@Length(min=0, max=64, message="司机长度必须介于 0 和 64 之间")
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	@Length(min=0, max=64, message="联系电话长度必须介于 0 和 64 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getCarryWeight() {
		return carryWeight;
	}

	public void setCarryWeight(Integer carryWeight) {
		this.carryWeight = carryWeight;
	}

	public Integer getCarryVolume() {
		return carryVolume;
	}

	public void setCarryVolume(Integer carryVolume) {
		this.carryVolume = carryVolume;
	}

	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
}