package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/7/19
 */
public class MCSBPayResponse extends MBaseResponse implements Serializable {
    private String code_url;
    private String org_no;
    private String merchantId;
    private String random_str;
    private String sign;
    private String hf_order_no;



    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public String getOrg_no() {
        return org_no;
    }

    public void setOrg_no(String org_no) {
        this.org_no = org_no;
    }

    public String getRandom_str() {
        return random_str;
    }

    public void setRandom_str(String random_str) {
        this.random_str = random_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHf_order_no() {
        return hf_order_no;
    }

    public void setHf_order_no(String hf_order_no) {
        this.hf_order_no = hf_order_no;
    }
}
