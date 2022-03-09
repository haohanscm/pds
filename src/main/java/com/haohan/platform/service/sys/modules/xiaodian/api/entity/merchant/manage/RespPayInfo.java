package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage;


import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 商户后台  支付账户返回参数
 * Created by dy on 2018/10/9.
 */
public class RespPayInfo implements Serializable {

    private String partnerId; // 商户编号
    private String mercAbbr; // 商户简称
    private Integer status; //  开户状态
    private String isEnable; // 是否启用支付账户
    private List<RespChannel> channelList;  //渠道列表


    // 获取支付账户 信息
    public void copyFromPayInfo(MerchantPayInfo payInfo){
        this.partnerId = payInfo.getPartnerId();
        this.mercAbbr = payInfo.getMercAbbr();
        this.status = payInfo.getRegStatus();
        this.isEnable = payInfo.getIsEnable();
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getMercAbbr() {
        return mercAbbr;
    }

    public void setMercAbbr(String mercAbbr) {
        this.mercAbbr = mercAbbr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public List<RespChannel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<RespChannel> channelList) {
        this.channelList = channelList;
    }
}
