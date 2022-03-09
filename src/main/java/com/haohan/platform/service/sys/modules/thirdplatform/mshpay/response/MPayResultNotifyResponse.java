package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/8/6
 */
public class MPayResultNotifyResponse extends MBaseResponse implements Serializable {

    private String mchnt_cd;            //惠付平台商户编号
    private String random_str;          //随机字符串 固定返回0
    private String order_type;          //支付方式 1 微信 2 支付宝 3 银联卡 4 京东 5 退款
    private String mchnt_order_no;      //我方平台订单号
    private String wwwt_order_no;     //惠付平台订单号
    private String total_amt;       //交易金额
    private String sign;            //签名字符串

    public String getMchnt_cd() {
        return mchnt_cd;
    }

    public void setMchnt_cd(String mchnt_cd) {
        this.mchnt_cd = mchnt_cd;
    }

    public String getRandom_str() {
        return random_str;
    }

    public void setRandom_str(String random_str) {
        this.random_str = random_str;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getMchnt_order_no() {
        return mchnt_order_no;
    }

    public void setMchnt_order_no(String mchnt_order_no) {
        this.mchnt_order_no = mchnt_order_no;
    }

    public String getWwwt_order_no() {
        return wwwt_order_no;
    }

    public void setWwwt_order_no(String wwwt_order_no) {
        this.wwwt_order_no = wwwt_order_no;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
