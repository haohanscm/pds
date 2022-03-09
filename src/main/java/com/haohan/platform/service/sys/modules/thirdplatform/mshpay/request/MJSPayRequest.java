package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/7/19
 */
public class MJSPayRequest extends MBaseRequest implements Serializable {

    private String totalFee;        //订单金额
    private String goods_detail;
    private String openid;              //微信openid  支付宝uid
    private String notify_url;          //支付回调地址

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
