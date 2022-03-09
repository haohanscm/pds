package com.haohan.platform.service.sys.common.weixin;

import java.io.Serializable;

/****
 * 销售人员创建二维码 根据productId 保存关联缓存 包含销售人员编号和二维码内容
 *
 * @author lcw
 *
 */
public class WxSalesQrcode implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6945217447118581778L;
    /**** 卡号 ***/
    private String vipCard;

    /****** 销售人员Id *******/
    private String salesId;

    /****** 产品父类Id *******/
    private String pId;//

    /****** 二维码内容 *******/
    private String qrcode;

    /******* 最初的卡号 *****/
    private String originalCard;

    public WxSalesQrcode() {
    }

    public WxSalesQrcode(String salesId, String pId, String qrcode, String vipCard) {
        this.salesId = salesId;
        this.qrcode = qrcode;
        this.pId = pId;
        this.vipCard = vipCard;
        this.originalCard = this.vipCard;
    }

    public WxSalesQrcode(String salesId, String pId, String qrcode, String vipCard, String originalCard) {
        this.salesId = salesId;
        this.qrcode = qrcode;
        this.pId = pId;
        this.vipCard = vipCard;
        this.originalCard = originalCard;
    }

    /**
     * @return
     * @Title: cloneNew
     * @Description: 根据原来数据生成新的数据 老的vipCard-->originalCard
     * @author zhaokuner
     */
    public WxSalesQrcode cloneNew() {
        return new WxSalesQrcode(salesId, pId, qrcode, vipCard, vipCard);
    }

    public String getSalesId() {
        return salesId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getVipCard() {
        return vipCard;
    }

    public void setVipCard(String vipCard) {
        this.vipCard = vipCard;
    }

    public String getOriginalCard() {
        return originalCard;
    }

    public void setOriginalCard(String originalCard) {
        this.originalCard = originalCard;
    }

}
