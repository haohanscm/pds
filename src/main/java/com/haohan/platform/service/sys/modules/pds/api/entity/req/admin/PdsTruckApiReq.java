package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.AddGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2019/2/14
 */
public class PdsTruckApiReq extends PdsBaseApiReq {
    @NotBlank(groups = {DeleteGroup.class}, message = "missing param id")
    private String id;
    @NotBlank(groups = {AddGroup.class}, message = "missing param truckNo")
    private String truckNo;        // 车辆编号
    private String brand;        // 车辆品牌
    private String truckName;        // 名称
    private String principal;        // 负责人
    @NotBlank(groups = {AddGroup.class}, message = "missing param driver")
    private String driver;        // 司机
    private String telephone;        // 联系电话
    private Integer carryWeight;        // 车辆载重Kg
    private Integer carryVolume;        // 车辆容积m3
    private String status;              // 状态

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
