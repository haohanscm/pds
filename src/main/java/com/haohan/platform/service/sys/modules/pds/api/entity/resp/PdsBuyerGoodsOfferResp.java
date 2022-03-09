package com.haohan.platform.service.sys.modules.pds.api.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsApiBuyerGoodsCategoryResp;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zgw on 2018/12/8.
 * 采购商商品报价返回
 */
public class PdsBuyerGoodsOfferResp implements Serializable {

    private PdsBuyer pdsBuyer;  // 采购商信息 pmName buyerName 联系人/电话/地址
    private Date startDate;  // 定价时间段 开始时间
    private Date endDate; // 定价时间段 结束时间
//    private Map<String, List<PdsBuyerGoodsResp>> mapGoods;  // 商品定价映射   分类名称:商品定价

    private List<PdsApiBuyerGoodsCategoryResp> categoryList;

    public List<PdsApiBuyerGoodsCategoryResp> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<PdsApiBuyerGoodsCategoryResp> categoryList) {
        this.categoryList = categoryList;
    }

    public PdsBuyer getPdsBuyer() {
        return pdsBuyer;
    }

    public void setPdsBuyer(PdsBuyer pdsBuyer) {
        this.pdsBuyer = pdsBuyer;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

//    public Map<String, List<PdsBuyerGoodsResp>> getMapGoods() {
//        return mapGoods;
//    }
//
//    public void setMapGoods(Map<String, List<PdsBuyerGoodsResp>> mapGoods) {
//        this.mapGoods = mapGoods;
//    }
}
