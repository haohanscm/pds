package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author dy
 * @create 2018/12/26
 */
public class PdsRangeAmountResultResp implements Serializable {

    private String merchantId;
    private String merchantName;
    private BigDecimal totalBuyAmount;
    private BigDecimal totalAfterSaleAmount;
    private BigDecimal totalPayAmount;
    private List<PdsRangeAmountBaseResp> dateAmountList;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public BigDecimal getTotalBuyAmount() {
        return totalBuyAmount;
    }

    public void setTotalBuyAmount(BigDecimal totalBuyAmount) {
        this.totalBuyAmount = totalBuyAmount;
    }

    public BigDecimal getTotalAfterSaleAmount() {
        return totalAfterSaleAmount;
    }

    public void setTotalAfterSaleAmount(BigDecimal totalAfterSaleAmount) {
        this.totalAfterSaleAmount = totalAfterSaleAmount;
    }

    public BigDecimal getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(BigDecimal totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    public List<PdsRangeAmountBaseResp> getDateAmountList() {
        return dateAmountList;
    }

    public void setDateAmountList(List<PdsRangeAmountBaseResp> dateAmountList) {
        this.dateAmountList = dateAmountList;
    }
}
