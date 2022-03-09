package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/7/19
 */
public class MOrderQueryResponse extends MBaseResponse implements Serializable {

    private String outTradeNo;
    private String out_transaction_id;
    private String hf_order_no;
    private String orderStatus;
    private String totalFee;
    private String channel;
    private String time_end;
    private String sign;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
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

    public String getOut_transaction_id() {
        return out_transaction_id;
    }

    public void setOut_transaction_id(String out_transaction_id) {
        this.out_transaction_id = out_transaction_id;
    }

    public String getHf_order_no() {
        return hf_order_no;
    }

    public void setHf_order_no(String hf_order_no) {
        this.hf_order_no = hf_order_no;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }
}
