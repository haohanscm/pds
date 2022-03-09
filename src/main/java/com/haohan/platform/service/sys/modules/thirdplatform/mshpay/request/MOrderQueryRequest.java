package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/7/19
 */
public class MOrderQueryRequest extends MBaseRequest implements Serializable {

    private String hf_order_no;             //惠付平台订单编号
    private String out_transaction_id;      //第三方交易订单号

    public String getHf_order_no() {
        return hf_order_no;
    }

    public void setHf_order_no(String hf_order_no) {
        this.hf_order_no = hf_order_no;
    }

    public String getOut_transaction_id() {
        return out_transaction_id;
    }

    public void setOut_transaction_id(String out_transaction_id) {
        this.out_transaction_id = out_transaction_id;
    }
}
