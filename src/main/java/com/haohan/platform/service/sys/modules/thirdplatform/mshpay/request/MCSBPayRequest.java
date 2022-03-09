package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/7/19
 */
public class MCSBPayRequest extends MBaseRequest implements Serializable {
    private String notify_url;      //支付回调地址
    private String callback_url;    //支付完成跳转页面
    private String goods_detail;
    private String totalFee;        //订单金额

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }
}
