package com.haohan.platform.service.sys.modules.pds.api.entity.params;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSumOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/10/29
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsAdminSumBuyOrder extends DataEntity<PdsAdminSumBuyOrder> {
    private Integer pageNo;
    private Integer pageSize;
    private String pmId;
    private String summaryOrderId;
    private String category;
    private String goodsName;
    private String goodsId;
    private String goodsModel;
    private String unit;
    private BigDecimal weight;
    private BigDecimal volume;
    private BigDecimal needBuyNum;
    private BigDecimal realBuyNum;
    private BigDecimal lossPercent;
    private BigDecimal platformPrice;
    private BigDecimal marketPrice;
    private BigDecimal avgBuyPrice;
    private BigDecimal buyerBillAmount;
    private BigDecimal profits;
    private Integer limitSupplyNum;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    private String buySeq;
    private String status;
    private BigDecimal buyAvgPrice;
    private BigDecimal stockNum;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<OfferOrder> offerOrderList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PdsAdminSumOrderDetail> buyOrderDetailList;
    /**
     * 按分类查询 展示分类名称
     */
    private String categoryId;
    private String parentCategoryId;
    private String parentCategoryName;

    public List<PdsAdminSumOrderDetail> getBuyOrderDetailList() {
        return buyOrderDetailList;
    }

    public void setBuyOrderDetailList(List<PdsAdminSumOrderDetail> buyOrderDetailList) {
        this.buyOrderDetailList = buyOrderDetailList;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBuyAvgPrice() {
        return buyAvgPrice;
    }

    public void setBuyAvgPrice(BigDecimal buyAvgPrice) {
        this.buyAvgPrice = buyAvgPrice;
    }

    public Integer getLimitSupplyNum() {
        return limitSupplyNum;
    }

    public void setLimitSupplyNum(Integer limitSupplyNum) {
        this.limitSupplyNum = limitSupplyNum;
    }

    public String getSummaryOrderId() {
        return summaryOrderId;
    }

    public void setSummaryOrderId(String summaryOrderId) {
        this.summaryOrderId = summaryOrderId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getNeedBuyNum() {
        return needBuyNum;
    }

    public void setNeedBuyNum(BigDecimal needBuyNum) {
        this.needBuyNum = needBuyNum;
    }

    public BigDecimal getRealBuyNum() {
        return realBuyNum;
    }

    public void setRealBuyNum(BigDecimal realBuyNum) {
        this.realBuyNum = realBuyNum;
    }

    public BigDecimal getLossPercent() {
        return lossPercent;
    }

    public void setLossPercent(BigDecimal lossPercent) {
        this.lossPercent = lossPercent;
    }

    public BigDecimal getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(BigDecimal platformPrice) {
        this.platformPrice = platformPrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(BigDecimal avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public BigDecimal getBuyerBillAmount() {
        return buyerBillAmount;
    }

    public void setBuyerBillAmount(BigDecimal buyerBillAmount) {
        this.buyerBillAmount = buyerBillAmount;
    }

    public BigDecimal getProfits() {
        return profits;
    }

    public void setProfits(BigDecimal profits) {
        this.profits = profits;
    }

    public List<OfferOrder> getOfferOrderList() {
        return offerOrderList;
    }

    public void setOfferOrderList(List<OfferOrder> offerOrderList) {
        this.offerOrderList = offerOrderList;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public BigDecimal getStockNum() {
        return stockNum;
    }

    public void setStockNum(BigDecimal stockNum) {
        this.stockNum = stockNum;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
}
