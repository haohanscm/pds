package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购单详情Entity
 *
 * @author haohan
 * @version 2018-10-24
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyOrderDetail extends DataEntity<BuyOrderDetail> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String smmaryBuyId;        // 汇总单号
    private String goodsOrderDetailId;    //零售单明细ID
    private String buyId;        // 采购编号
    private String buyerId;        // 采购商
    private String goodsId;        // 商品ID
    private String goodsImg;        // 商品图片
    private String goodsName;        // 商品名称
    private String goodsModel;        // 商品规格
    private BigDecimal goodsNum;        // 采购数量
    private BigDecimal marketPrice;        // 市场价格
    private BigDecimal buyPrice;        // 采购价格
    private String unit;        // 单位
    private String status;        // 状态
    private BigDecimal orderGoodsNum;        // 商品下单数量 仅记录商品原始的下单数量
    /**
     * 采购明细编号
     */
    private String buyDetailSn;
    /**
     * 明细汇总状态:0未处理1已汇总2已备货
     */
    private String summaryFlag;

    private String buyerName;
    private String pmName;        // 平台商家名称
    private String merchantId;  // 采购商商家id
    private String merchantName;

    //查询参数
    private Date deliveryDate;
    @JsonIgnore
    private String summaryOrderStatus;    //汇总单状态
    private String buySeq;        // 采购批次
    @JsonIgnore
    private String buyerStatus; //  交易单中 采购商状态
    @JsonIgnore
    private String finalStatus; // 修改状态时,修改后的状态
    /**
     * 上次采购价
     */
    private BigDecimal lastPrice;
    /**
     * 上次采购配送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastDate;


    public String getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(String buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public String getSummaryOrderStatus() {
        return summaryOrderStatus;
    }

    public void setSummaryOrderStatus(String summaryOrderStatus) {
        this.summaryOrderStatus = summaryOrderStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BuyOrderDetail() {
        super();
    }

    public BuyOrderDetail(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "汇总单号长度必须介于 0 和 64 之间")
    public String getSmmaryBuyId() {
        return smmaryBuyId;
    }

    public void setSmmaryBuyId(String smmaryBuyId) {
        this.smmaryBuyId = smmaryBuyId;
    }

    @Length(min = 0, max = 64, message = "采购编号长度必须介于 0 和 64 之间")
    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    @Length(min = 0, max = 64, message = "采购商长度必须介于 0 和 64 之间")
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Length(min = 0, max = 64, message = "采购批次长度必须介于 0 和 64 之间")
    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    @Length(min = 0, max = 64, message = "商品ID长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Length(min = 0, max = 500, message = "商品图片长度必须介于 0 和 500 之间")
    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    @Length(min = 0, max = 64, message = "商品名称长度必须介于 0 和 64 之间")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Length(min = 0, max = 64, message = "商品规格长度必须介于 0 和 64 之间")
    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public BigDecimal getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(BigDecimal goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Length(min = 0, max = 64, message = "单位长度必须介于 0 和 64 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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

    public String getGoodsOrderDetailId() {
        return goodsOrderDetailId;
    }

    public void setGoodsOrderDetailId(String goodsOrderDetailId) {
        this.goodsOrderDetailId = goodsOrderDetailId;
    }

    public BigDecimal getOrderGoodsNum() {
        return orderGoodsNum;
    }

    public void setOrderGoodsNum(BigDecimal orderGoodsNum) {
        this.orderGoodsNum = orderGoodsNum;
    }

    public String getBuyDetailSn() {
        return buyDetailSn;
    }

    public void setBuyDetailSn(String buyDetailSn) {
        this.buyDetailSn = buyDetailSn;
    }

    public String getSummaryFlag() {
        return summaryFlag;
    }

    public void setSummaryFlag(String summaryFlag) {
        this.summaryFlag = summaryFlag;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public boolean equals(Object obj) {
        BuyOrderDetail buyOrderDetail = (BuyOrderDetail) obj;
        return this.id.equals(buyOrderDetail.getId());
    }

    @Override
    public int hashCode() {
        int code = 17;
        code = 31 * code + (this.id == null ? 0 : this.id.hashCode());
        return code;
    }
}