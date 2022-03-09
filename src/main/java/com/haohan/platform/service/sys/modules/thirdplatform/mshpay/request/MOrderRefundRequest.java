package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/7/28
 */
public class MOrderRefundRequest extends MBaseRequest implements Serializable {

    private String out_transaction_id;
    private int refundFee;
    private String refundNo;

    public String getOut_transaction_id() {
        return out_transaction_id;
    }

    public void setOut_transaction_id(String out_transaction_id) {
        this.out_transaction_id = out_transaction_id;
    }

    public int getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(int refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }
}
