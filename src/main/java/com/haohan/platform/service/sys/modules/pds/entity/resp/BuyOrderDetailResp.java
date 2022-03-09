package com.haohan.platform.service.sys.modules.pds.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购单明细 返回值
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyOrderDetailResp extends BuyOrderDetail {

    private String buyerStatus; // // 交易单中 采购商状态
    private String buySeq;        // 采购批次
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;  // 送货时间
    private BigDecimal sortOutNum;  // 分拣数量

    public BigDecimal getSortOutNum() {
        return sortOutNum;
    }

    public void setSortOutNum(BigDecimal sortOutNum) {
        this.sortOutNum = sortOutNum;
    }

    @Override
    public String getBuyerStatus() {
        return buyerStatus;
    }

    @Override
    public void setBuyerStatus(String buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    @Override
    public String getBuySeq() {
        return buySeq;
    }

    @Override
    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
