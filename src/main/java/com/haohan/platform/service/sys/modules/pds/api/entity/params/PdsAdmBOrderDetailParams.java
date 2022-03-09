package com.haohan.platform.service.sys.modules.pds.api.entity.params;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/10/31
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsAdmBOrderDetailParams {
    private String goodsName;
    private String goodsModel;
    private String goodsUnit;
    private Integer goodsNum;
    private BigDecimal buyPrice;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PdsAdmTradeOrderParams> tradeOrderList;

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

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public List<PdsAdmTradeOrderParams> getTradeOrderList() {
        return tradeOrderList;
    }

    public void setTradeOrderList(List<PdsAdmTradeOrderParams> tradeOrderList) {
        this.tradeOrderList = tradeOrderList;
    }
}
