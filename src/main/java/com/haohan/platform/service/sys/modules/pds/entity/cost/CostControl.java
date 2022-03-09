package com.haohan.platform.service.sys.modules.pds.entity.cost;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 成本管控Entity
 *
 * @author haohan
 * @version 2018-12-03
 */
public class CostControl extends DataEntity<CostControl> {

    private static final long serialVersionUID = 1L;
    private String pmId;        // 平台商家ID
    private String costId;        // 成本编号
    private String costName;        // 成本名称
    private String costType;        // 成本分类
    private String countUnit;        // 计算单位
    private Integer controlLimit;        // 管控阀值
    private String status;        // 状态

    public CostControl() {
        super();
    }

    public CostControl(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "平台商家ID长度必须介于 0 和 64 之间")
    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    @Length(min = 0, max = 64, message = "成本编号长度必须介于 0 和 64 之间")
    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    @Length(min = 0, max = 64, message = "成本名称长度必须介于 0 和 64 之间")
    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    @Length(min = 0, max = 64, message = "成本分类长度必须介于 0 和 64 之间")
    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    @Length(min = 0, max = 64, message = "计算单位长度必须介于 0 和 64 之间")
    public String getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
    }

    public Integer getControlLimit() {
        return controlLimit;
    }

    public void setControlLimit(Integer controlLimit) {
        this.controlLimit = controlLimit;
    }

    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}