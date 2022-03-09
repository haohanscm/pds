package com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer;

import com.haohan.platform.service.sys.modules.pds.api.entity.resp.PdsBuyerGoodsResp;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/12
 */
public class PdsApiBuyerGoodsCategoryResp implements Serializable {
    private String categoryId;
    private String categoryName;
    private List<PdsBuyerGoodsResp> goodsList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<PdsBuyerGoodsResp> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PdsBuyerGoodsResp> goodsList) {
        this.goodsList = goodsList;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
