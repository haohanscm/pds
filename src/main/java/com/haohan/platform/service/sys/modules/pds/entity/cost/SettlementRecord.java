package com.haohan.platform.service.sys.modules.pds.entity.cost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算记录管理Entity
 *
 * @author dy
 * @version 2018-11-06
 */
public class SettlementRecord extends DataEntity<SettlementRecord> {

    private static final long serialVersionUID = 1L;
    private String pmId;            //平台商家ID
    private String settlementId;       // 结算单号
    private String settlementType;        // 结算类型
    private BigDecimal settlementAmount;        // 结算金额
    private Date settlementBeginDate;        // 结算开始日期
    private Date settlementEndDate;        // 结算结束日期
    private Date payDate;        // 付款日期
    private String companyType;        // 结算公司类型
    private String companyId;        // 结算公司id:采购商id/供应商id
    private String companyName;        // 结算公司名称
    private String companyOperator;        // 结款人名称
    private String settlementImg;        // 结算凭证图片
    private String settlementDesc;        // 结算说明
    private String operator;        // 经办人名称
    private String paymentSn;        // 对应货款单号列表
    private Date beginPayDate;        // 开始 付款日期
    private Date endPayDate;        // 结束 付款日期

    private String pmName;        // 平台商家名称

    public SettlementRecord() {
        super();
    }

    public SettlementRecord(String id) {
        super(id);
    }

    @Length(min = 0, max = 2, message = "结算类型长度必须介于 0 和 2 之间")
    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getSettlementBeginDate() {
        return settlementBeginDate;
    }

    public void setSettlementBeginDate(Date settlementBeginDate) {
        this.settlementBeginDate = settlementBeginDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getSettlementEndDate() {
        return settlementEndDate;
    }

    public void setSettlementEndDate(Date settlementEndDate) {
        this.settlementEndDate = settlementEndDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Length(min = 0, max = 2, message = "结算公司类型长度必须介于 0 和 2 之间")
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Length(min = 0, max = 64, message = "结算公司id:采购商id/供应商id长度必须介于 0 和 64 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 0, max = 64, message = "结算公司名称长度必须介于 0 和 64 之间")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Length(min = 0, max = 64, message = "结款人名称长度必须介于 0 和 64 之间")
    public String getCompanyOperator() {
        return companyOperator;
    }

    public void setCompanyOperator(String companyOperator) {
        this.companyOperator = companyOperator;
    }

    @Length(min = 0, max = 500, message = "结算凭证图片长度必须介于 0 和 500 之间")
    public String getSettlementImg() {
        return settlementImg;
    }

    public void setSettlementImg(String settlementImg) {
        this.settlementImg = settlementImg;
    }

    @Length(min = 0, max = 500, message = "结算说明长度必须介于 0 和 500 之间")
    public String getSettlementDesc() {
        return settlementDesc;
    }

    public void setSettlementDesc(String settlementDesc) {
        this.settlementDesc = settlementDesc;
    }

    @Length(min = 0, max = 64, message = "经办人名称长度必须介于 0 和 64 之间")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Length(min = 0, max = 500, message = "货款单号长度必须介于 0 和 500 之间")
    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public Date getBeginPayDate() {
        return beginPayDate;
    }

    public void setBeginPayDate(Date beginPayDate) {
        this.beginPayDate = beginPayDate;
    }

    public Date getEndPayDate() {
        return endPayDate;
    }

    public void setEndPayDate(Date endPayDate) {
        this.endPayDate = endPayDate;
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

    public String getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }
}