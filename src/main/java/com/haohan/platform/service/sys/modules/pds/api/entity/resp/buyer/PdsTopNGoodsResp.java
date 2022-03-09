package com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/13
 */
public class PdsTopNGoodsResp implements Serializable {
    private String id;
    private String goodsName;
    private String goodsSn;
    private String thumbUrl;
    private String categoryName;
    private String categoryId;
    private BigDecimal marketPrice;
    private Integer buyTimes;
    private List<PdsTopNGoodsModelResp> goodsModelList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(Integer buyTimes) {
        this.buyTimes = buyTimes;
    }

    public List<PdsTopNGoodsModelResp> getGoodsModelList() {
        return goodsModelList;
    }

    public void setGoodsModelList(List<PdsTopNGoodsModelResp> goodsModelList) {
        this.goodsModelList = goodsModelList;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }
}
