package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2019/1/2
 */
public class BaseOrderDetailReq {
    @NotBlank(message = "goodsId can not be null")
    private String goodsId;
    private String goodsName;
    @NotBlank(message = "modelId can not be null")
    private String modelId;
    private String modelName;
    @NotNull(message = "goodsPrice can not be null")
    private BigDecimal goodsPrice;
    private BigDecimal marketPrice;
    @NotNull(message = "goodsNum can not be null")
    private BigDecimal goodsNum;
    private String goodsUnit;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(BigDecimal goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    //    public GoodsOrderDetail copyToGoodsDetail(GoodsOrderDetail goodsOrderDetail){
//        goodsOrderDetail.setGoodsId(this.getGoodsId());
//        goodsOrderDetail.setGoodsName(this.getGoodsName());
//        goodsOrderDetail.setGoodsPrice(this.getGoodsPrice());
//        goodsOrderDetail.setMarketPrice(this.getMarketPrice());
//        goodsOrderDetail.setGoodsNum(this.getGoodsNum());
//        goodsOrderDetail.setGoodsUnit(this.getGoodsUnit());
//        goodsOrderDetail.setModelName(this.getModelName());
//        return goodsOrderDetail;
//    }
}
