package com.haohan.platform.service.sys.modules.pds.api.entity.req.operate;

/**
 * @author shenyu
 * @create 2018/11/5
 */
public class PdsAfterSaleOrderReq {
    private String offerOrderId;        //报价单号
    private String goodsId;             //商品id
    private String serviceCategory;   //售后分类
    private String[] photos;        //图片
    private String note;            //备注
    private String uid;     //操作员UID
    private String pmId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOfferOrderId() {
        return offerOrderId;
    }

    public void setOfferOrderId(String offerOrderId) {
        this.offerOrderId = offerOrderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }
}
