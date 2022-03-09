package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 市场报价Entity
 *
 * @author haohan
 * @version 2018-12-03
 */
public class Marketplace extends DataEntity<Marketplace> {

    private static final long serialVersionUID = 1L;
    private String marketplaceNo;        // 编号
    private String pmId;        // 平台商家ID
    private String marketplaceType;        // 类型
    private String merchantId;        // 商家
    private String goodsId;        // 商品ID
    private String goodsModel;        // 商品规格
    private String goodsUrls;        // 商品图片
    private String goodsMemo;        // 说明
    private Date demandTime;        // 需求时间
    private String status;        // 状态
    private String payType;        // 支付方式
    private String deliveryType;        // 配送方式

    public Marketplace() {
        super();
    }

    public Marketplace(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "编号长度必须介于 0 和 64 之间")
    public String getMarketplaceNo() {
        return marketplaceNo;
    }

    public void setMarketplaceNo(String marketplaceNo) {
        this.marketplaceNo = marketplaceNo;
    }

    @Length(min = 0, max = 64, message = "平台商家ID长度必须介于 0 和 64 之间")
    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    @Length(min = 0, max = 64, message = "类型长度必须介于 0 和 64 之间")
    public String getMarketplaceType() {
        return marketplaceType;
    }

    public void setMarketplaceType(String marketplaceType) {
        this.marketplaceType = marketplaceType;
    }

    @Length(min = 0, max = 64, message = "商家长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 64, message = "商品ID长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Length(min = 0, max = 64, message = "商品规格长度必须介于 0 和 64 之间")
    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    @Length(min = 0, max = 500, message = "商品图片长度必须介于 0 和 500 之间")
    public String getGoodsUrls() {
        return goodsUrls;
    }

    public void setGoodsUrls(String goodsUrls) {
        this.goodsUrls = goodsUrls;
    }

    @Length(min = 0, max = 500, message = "说明长度必须介于 0 和 500 之间")
    public String getGoodsMemo() {
        return goodsMemo;
    }

    public void setGoodsMemo(String goodsMemo) {
        this.goodsMemo = goodsMemo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDemandTime() {
        return demandTime;
    }

    public void setDemandTime(Date demandTime) {
        this.demandTime = demandTime;
    }

    @Length(min = 0, max = 2, message = "状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 2, message = "支付方式长度必须介于 0 和 2 之间")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Length(min = 0, max = 2, message = "配送方式长度必须介于 0 和 2 之间")
    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

}