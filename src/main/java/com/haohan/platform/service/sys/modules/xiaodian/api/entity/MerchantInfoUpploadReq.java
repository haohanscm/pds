package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/8/16
 */
public class MerchantInfoUpploadReq implements Serializable {

    private String merchantId;
    private String[] license;   //营业执照    步骤1
    private String[] idCards;    //身份证         4
    private String[] bankCard;   //结算卡         5
    private String[] cateringLicense;   //餐饮许可证      2
    private String[] protocol;   //协议扫描件
    private String[] shopPhotos;  //门店照片              3
    private String[] goodsPhotos;   //商品照片
    private String isCover;              //是否覆盖保存    0:否  1:是
    private String step;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String[] getLicense() {
        return license;
    }

    public void setLicense(String[] license) {
        this.license = license;
    }

    public String[] getIdCards() {
        return idCards;
    }

    public void setIdCards(String[] idCards) {
        this.idCards = idCards;
    }

    public String[] getBankCard() {
        return bankCard;
    }

    public void setBankCard(String[] bankCard) {
        this.bankCard = bankCard;
    }

    public String[] getCateringLicense() {
        return cateringLicense;
    }

    public void setCateringLicense(String[] cateringLicense) {
        this.cateringLicense = cateringLicense;
    }

    public String[] getProtocol() {
        return protocol;
    }

    public void setProtocol(String[] protocol) {
        this.protocol = protocol;
    }

    public String[] getShopPhotos() {
        return shopPhotos;
    }

    public void setShopPhotos(String[] shopPhotos) {
        this.shopPhotos = shopPhotos;
    }

    public String[] getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(String[] goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }

    public String getIsCover() {
        return isCover;
    }

    public void setIsCover(String isCover) {
        this.isCover = isCover;
    }

}
