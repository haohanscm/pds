package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/7/19
 */
public class MBSCPayRequest extends MBaseRequest implements Serializable {

    private String totalFee;       //支付金额
    private String authCode;        //授权码
    private String goods_detail;
    private String device_id;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }


    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }
}
