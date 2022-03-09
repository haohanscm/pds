package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请求 结算记录编辑
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminSettlementRecordEditReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    @NotBlank(message = "companyType不能为空")
    @Length(min = 0, max = 2, message = "结算公司类型长度必须介于 0 和 2 之间")
    private String companyType;
    @NotBlank(message = "settlementType不能为空")
    @Length(min = 0, max = 2, message = "结算类型长度必须介于 0 和 2 之间")
    private String settlementType;
    @NotBlank(message = "companyId不能为空")
    @Length(min = 0, max = 64, message = "结算公司id:采购商id/供应商id长度必须介于 0 和 64 之间")
    private String companyId;
    @NotNull(message = "请选择结算开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date settlementBeginDate;
    @NotNull(message = "请选择结算结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date settlementEndDate;
    @NotNull(message = "请选择付款日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payDate;
    @NotBlank(message = "paymentSn不能为空")
    @Length(min = 0, max = 500, message = "货款单号长度必须介于 0 和 500 之间")
    private String paymentSn;        // 对应货款单号列表
    @NotBlank(message = "settlementAmount不能为空")
    @Length(min = 0, max = 10, message = "结算金额长度必须介于 0 和 10 之间")
    private String settlementAmount;
    @NotBlank(message = "companyOperator不能为空")
    @Length(min = 0, max = 64, message = "结款人名称长度必须介于 0 和 64 之间")
    private String companyOperator;

    @Length(min = 0, max = 64, message = "结算记录id长度必须介于 0 和 64 之间")
    private String id;
    @Length(min = 0, max = 64, message = "结算公司名称长度必须介于 0 和 64 之间")
    private String companyName;
    @Length(min = 0, max = 500, message = "结算凭证图片长度必须介于 0 和 500 之间")
    private String settlementImg;
    @Length(min = 0, max = 500, message = "结算说明长度必须介于 0 和 500 之间")
    private String settlementDesc;        // 结算说明
    @Length(min = 0, max = 64, message = "经办人名称长度必须介于 0 和 64 之间")
    private String operator;        // 经办人名称
    @Length(min = 0, max = 255, message = "备注信息长度必须介于 0 和 255 之间")
    private String remarks;


    public void transToSettlement(SettlementRecord settlementRecord) {
        settlementRecord.setPmId(this.pmId);
        settlementRecord.setCompanyType(this.companyType);
        settlementRecord.setSettlementType(this.settlementType);
        settlementRecord.setCompanyId(this.companyId);
        settlementRecord.setCompanyName(this.companyName);
        settlementRecord.setSettlementBeginDate(this.settlementBeginDate);
        settlementRecord.setSettlementEndDate(this.settlementEndDate);
        settlementRecord.setPayDate(this.payDate);
        settlementRecord.setPaymentSn(this.paymentSn);
        settlementRecord.setSettlementAmount(new BigDecimal(this.settlementAmount));
        settlementRecord.setCompanyOperator(this.companyOperator);
        settlementRecord.setId(this.id);
        settlementRecord.setSettlementImg(this.settlementImg);
        settlementRecord.setSettlementDesc(this.settlementDesc);
        settlementRecord.setOperator(this.operator);
        settlementRecord.setRemarks(this.remarks);
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getSettlementBeginDate() {
        return settlementBeginDate;
    }

    public void setSettlementBeginDate(Date settlementBeginDate) {
        this.settlementBeginDate = settlementBeginDate;
    }

    public Date getSettlementEndDate() {
        return settlementEndDate;
    }

    public void setSettlementEndDate(Date settlementEndDate) {
        this.settlementEndDate = settlementEndDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getCompanyOperator() {
        return companyOperator;
    }

    public void setCompanyOperator(String companyOperator) {
        this.companyOperator = companyOperator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSettlementImg() {
        return settlementImg;
    }

    public void setSettlementImg(String settlementImg) {
        this.settlementImg = settlementImg;
    }

    public String getSettlementDesc() {
        return settlementDesc;
    }

    public void setSettlementDesc(String settlementDesc) {
        this.settlementDesc = settlementDesc;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
