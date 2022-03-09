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
 * 交易订单Entity
 *
 * @author haohan
 * @version 2018-10-24
 */
@JsonIgnoreProperties(value = {"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeOrder extends DataEntity<TradeOrder> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String tradeId;        // 交易单号
    private String summaryBuyId;        // 汇总单号
    private String buyId;        // 采购编号
    private String offerType;        // 报价类型
    private String offerId;        // 报价单号
    private String buyerId;        // 采购商
    private String supplierId;        // 供应商
    private String buySeq;        // 采购批次
    private String goodsId;        // 商品ID
    private String goodsName;        // 商品名称
    private String goodsCategory;    //商品分类
    private String goodsImg;        // 商品图片
    private String goodsModel;        // 商品规格
    private String unit;        // 单位
    private BigDecimal buyNum;        // 采购数量
    private Date buyTime;        // 采购时间
    private String buyNode;        // 采购备注
    private BigDecimal marketPrice;        // 市场价
    private BigDecimal supplyPrice;        // 供应单价
    private BigDecimal buyPrice;        // 采购单价
    private Date dealTime;        // 成交时间
    private String contact;        // 联系人
    private String contactPhone;        // 联系电话
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;        // 送货时间
    private String deliveryAddress;        // 送货地址
    private String supplierStatus;        // 供应商状态
    private String transStatus;        // 交易状态
    private String buyerStatus;        // 采购商状态
    private String opStatus;        // 运营状态
    private String deliveryStatus;        // 配送状态
    private Date beginBuyTime;        // 开始 采购时间
    private Date endBuyTime;        // 结束 采购时间
    private BigDecimal sortOutNum;        //分拣数量
    private String buyOperator;        //采购员
    private String deliveryType;    //配送方式

    //查询
    private String buyerName;        //采购商名称
    private String supplierName;    //供应商名称
    @JsonIgnore
    private String[] opStatusArry;    //多状态查询
    @JsonIgnore
    private String truckNo;
    private String pmName;        // 平台商家名称
    private Date startTime;        //查询参数
    private Date endTime;        //查询参数

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String[] getOpStatusArry() {
        return opStatusArry;
    }

    public void setOpStatusArry(String[] opStatusArry) {
        this.opStatusArry = opStatusArry;
    }

    public BigDecimal getSortOutNum() {
        return sortOutNum;
    }

    public void setSortOutNum(BigDecimal sortOutNum) {
        this.sortOutNum = sortOutNum;
    }

    public TradeOrder() {
        super();
    }

    public TradeOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "交易单号长度必须介于 0 和 64 之间")
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Length(min = 0, max = 64, message = "汇总单号长度必须介于 0 和 64 之间")
    public String getSummaryBuyId() {
        return summaryBuyId;
    }

    public void setSummaryBuyId(String summaryBuyId) {
        this.summaryBuyId = summaryBuyId;
    }

    @Length(min = 0, max = 64, message = "采购编号长度必须介于 0 和 64 之间")
    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    @Length(min = 0, max = 5, message = "报价类型长度必须介于 0 和 5 之间")
    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    @Length(min = 0, max = 64, message = "报价单号长度必须介于 0 和 64 之间")
    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Length(min = 0, max = 64, message = "采购商长度必须介于 0 和 64 之间")
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Length(min = 0, max = 64, message = "供应商长度必须介于 0 和 64 之间")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    @Length(min = 0, max = 64, message = "商品名称长度必须介于 0 和 64 之间")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Length(min = 0, max = 255, message = "商品图片长度必须介于 0 和 255 之间")
    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    @Length(min = 0, max = 64, message = "商品规格长度必须介于 0 和 64 之间")
    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    @Length(min = 0, max = 64, message = "单位长度必须介于 0 和 64 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(BigDecimal buyNum) {
        this.buyNum = buyNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    @Length(min = 0, max = 255, message = "采购备注长度必须介于 0 和 255 之间")
    public String getBuyNode() {
        return buyNode;
    }

    public void setBuyNode(String buyNode) {
        this.buyNode = buyNode;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    @Length(min = 0, max = 64, message = "联系人长度必须介于 0 和 64 之间")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Length(min = 0, max = 64, message = "联系电话长度必须介于 0 和 64 之间")
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Length(min = 0, max = 64, message = "送货地址长度必须介于 0 和 64 之间")
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Length(min = 0, max = 5, message = "供应商状态长度必须介于 0 和 5 之间")
    public String getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    @Length(min = 0, max = 5, message = "交易状态长度必须介于 0 和 5 之间")
    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    @Length(min = 0, max = 5, message = "采购商状态长度必须介于 0 和 5 之间")
    public String getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(String buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    @Length(min = 0, max = 5, message = "运营状态长度必须介于 0 和 5 之间")
    public String getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
    }

    @Length(min = 0, max = 5, message = "配送状态长度必须介于 0 和 5 之间")
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Date getBeginBuyTime() {
        return beginBuyTime;
    }

    public void setBeginBuyTime(Date beginBuyTime) {
        this.beginBuyTime = beginBuyTime;
    }

    public Date getEndBuyTime() {
        return endBuyTime;
    }

    public void setEndBuyTime(Date endBuyTime) {
        this.endBuyTime = endBuyTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBuyOperator() {
        return buyOperator;
    }

    public void setBuyOperator(String buyOperator) {
        this.buyOperator = buyOperator;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}