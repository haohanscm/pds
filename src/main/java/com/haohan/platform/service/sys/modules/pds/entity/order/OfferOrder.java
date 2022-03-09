package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报价单Entity
 *
 * @author haohan
 * @version 2018-10-18
 */
public class OfferOrder extends DataEntity<OfferOrder> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String offerUid;        // 报价用户
    private String offerOrderId;        // 报价单号
    private String askOrderId;        // 询价单号
    private String offerType;        // 报价类型
    private String goodsId;            //商品id  当前使用的规格id sku
    private String goodsName;        //商品名称
    private BigDecimal buyNum;        // 采购数量
    private String supplierId;        // 供应商
    private String supplierName;    //供应商名称
    private BigDecimal supplyNum;        // 供应数量
    private Integer minSaleNum;        //起售数量
    private BigDecimal supplyPrice;        // 供应商报价
    private String supplyImg;        // 实物图片
    private String supplyDesc;        // 供应说明
    private String status;        // 状态 OfferOrderStatus
    private Date askPriceTime;        // 询价时间
    private Date offerPriceTime;        // 报价时间
    private Date dealTime;        //成交时间
    private BigDecimal dealPrice;    //成交单价
    private String shipStatus;    //发货状态
    private Date prepareDate;    //备货日期
    private String buySeq;        //采购批次
    private BigDecimal otherAmount;        // 其他费用
    private BigDecimal totalAmount;        // 总费用

    /**
     * 商品规格id
     */
    private String goodsModelId;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 单位
     */
    private String unit;
    /**
     * 规格名称
     */
    private String modelName;
    /**
     * 揽货方式1.自提2.送货上门 ReceiveType
     */
    private String receiveType;
    /**
     * 商品分类
     */
    private String goodsCategoryId;

    //查询参数
    private Date deliveryTime;
    private String pmName;        // 平台商家名称

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Integer getMinSaleNum() {
        return minSaleNum;
    }

    public void setMinSaleNum(Integer minSaleNum) {
        this.minSaleNum = minSaleNum;
    }

    public OfferOrder() {
        super();
    }

    public OfferOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "报价用户长度必须介于 0 和 64 之间")
    public String getOfferUid() {
        return offerUid;
    }

    public void setOfferUid(String offerUid) {
        this.offerUid = offerUid;
    }

    @Length(min = 0, max = 64, message = "报价单号长度必须介于 0 和 64 之间")
    public String getOfferOrderId() {
        return offerOrderId;
    }

    public void setOfferOrderId(String offerOrderId) {
        this.offerOrderId = offerOrderId;
    }

    @Length(min = 0, max = 64, message = "询价单号长度必须介于 0 和 64 之间")
    public String getAskOrderId() {
        return askOrderId;
    }

    public void setAskOrderId(String askOrderId) {
        this.askOrderId = askOrderId;
    }

    @Length(min = 0, max = 2, message = "报价类型长度必须介于 0 和 2 之间")
    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    @Length(min = 0, max = 64, message = "供应商长度必须介于 0 和 64 之间")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(BigDecimal buyNum) {
        this.buyNum = buyNum;
    }

    public BigDecimal getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(BigDecimal supplyNum) {
        this.supplyNum = supplyNum;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    @Length(min = 0, max = 64, message = "实物图片长度必须介于 0 和 64 之间")
    public String getSupplyImg() {
        return supplyImg;
    }

    public void setSupplyImg(String supplyImg) {
        this.supplyImg = supplyImg;
    }

    @Length(min = 0, max = 255, message = "供应说明长度必须介于 0 和 255 之间")
    public String getSupplyDesc() {
        return supplyDesc;
    }

    public void setSupplyDesc(String supplyDesc) {
        this.supplyDesc = supplyDesc;
    }

    @Length(min = 0, max = 1, message = "状态长度必须介于 0 和 1 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAskPriceTime() {
        return askPriceTime;
    }

    public void setAskPriceTime(Date askPriceTime) {
        this.askPriceTime = askPriceTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOfferPriceTime() {
        return offerPriceTime;
    }

    public void setOfferPriceTime(Date offerPriceTime) {
        this.offerPriceTime = offerPriceTime;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPrepareDate() {
        return prepareDate;
    }

    public void setPrepareDate(Date prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getGoodsModelId() {
        return goodsModelId;
    }

    public void setGoodsModelId(String goodsModelId) {
        this.goodsModelId = goodsModelId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }
}