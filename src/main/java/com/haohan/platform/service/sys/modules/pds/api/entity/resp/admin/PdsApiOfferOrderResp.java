package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/11/1
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsApiOfferOrderResp extends DataEntity<PdsApiOfferOrderResp> {
    private String pmId;        //平台商家ID
    private String offerOrderId;        // 报价单号
    private Integer buyNum;        // 采购数量
    private BigDecimal supplyPrice;        // 供应商报价
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dealTime;        //成交时间
    private BigDecimal dealPrice;    //成交单价
    private String shipStatus;    //发货状态
    private Date prepareDate;    //备货日期
    private String buySeq;      //采购批次
    private String status;      //状态

    private String goodsName;
    private String goodsModel;
    private String goodsUnit;
    private String goodsCategory;

    //查询
    private String supplierId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getOfferOrderId() {
        return offerOrderId;
    }

    public void setOfferOrderId(String offerOrderId) {
        this.offerOrderId = offerOrderId;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getPrepareDate() {
        return prepareDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setPrepareDate(Date prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
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

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
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
}
