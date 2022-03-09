package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/7/28
 */
public class MOrderRefundResponse extends MBaseResponse implements Serializable {

    private String outTradeNo;
    private String out_transaction_id;
    private String refundNo;
    private String refundFee;
    private String channel;
    private String sign;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOut_transaction_id() {
        return out_transaction_id;
    }

    public void setOut_transaction_id(String out_transaction_id) {
        this.out_transaction_id = out_transaction_id;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
