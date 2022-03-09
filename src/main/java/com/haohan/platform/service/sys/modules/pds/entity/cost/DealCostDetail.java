package com.haohan.platform.service.sys.modules.pds.entity.cost;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 交易成本明细Entity
 *
 * @author haohan
 * @version 2018-12-03
 */
public class DealCostDetail extends DataEntity<DealCostDetail> {

    private static final long serialVersionUID = 1L;
    private String dealDate;        // 交易日
    private String pmId;        // 平台商家ID
    private String costNo;        // 成本编号
    private String name;        // 名称
    private String number;        // 数量
    private BigDecimal amount;        // 金额

    public DealCostDetail() {
        super();
    }

    public DealCostDetail(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "交易日长度必须介于 0 和 64 之间")
    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    @Length(min = 0, max = 64, message = "平台商家ID长度必须介于 0 和 64 之间")
    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    @Length(min = 0, max = 64, message = "成本编号长度必须介于 0 和 64 之间")
    public String getCostNo() {
        return costNo;
    }

    public void setCostNo(String costNo) {
        this.costNo = costNo;
    }

    @Length(min = 0, max = 64, message = "名称长度必须介于 0 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 64, message = "数量长度必须介于 0 和 64 之间")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}