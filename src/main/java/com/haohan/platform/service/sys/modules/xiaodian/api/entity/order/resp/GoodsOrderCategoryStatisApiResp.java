package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2019/1/19
 */
public class GoodsOrderCategoryStatisApiResp {
    private String categoryId;
    private String categoryName;
    private Integer saleGoodsNum;
    private BigDecimal saleAmount;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSaleGoodsNum() {
        return saleGoodsNum;
    }

    public void setSaleGoodsNum(Integer saleGoodsNum) {
        this.saleGoodsNum = saleGoodsNum;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }
}
