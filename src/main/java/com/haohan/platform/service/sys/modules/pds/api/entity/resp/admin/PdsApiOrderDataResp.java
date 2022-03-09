package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/12/18
 */
public class PdsApiOrderDataResp implements Serializable {
    private Integer waitSummary;        //待汇总
    private Integer offerPrice;         //待报价
    private Integer confirm;            //待确认
    private Integer delivering;         //配送中
    private Integer sortout;            //待分拣
    private Integer notPay;      //待支付

    public Integer getWaitSummary() {
        return waitSummary;
    }

    public void setWaitSummary(Integer waitSummary) {
        this.waitSummary = waitSummary;
    }

    public Integer getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Integer offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getDelivering() {
        return delivering;
    }

    public void setDelivering(Integer delivering) {
        this.delivering = delivering;
    }

    public Integer getSortout() {
        return sortout;
    }

    public void setSortout(Integer sortout) {
        this.sortout = sortout;
    }

    public Integer getNotPay() {
        return notPay;
    }

    public void setNotPay(Integer notPay) {
        this.notPay = notPay;
    }
}
