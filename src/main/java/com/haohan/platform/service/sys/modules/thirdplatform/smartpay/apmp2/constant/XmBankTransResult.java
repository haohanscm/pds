package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant;

import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BaseResponse;

import java.io.Serializable;

/**
 * Created by zgw on 2018/3/27.
 */
public class XmBankTransResult implements Serializable{

    public static final BaseResponse FAIL = new BaseResponse("0", "交易失败");

    public static final BaseResponse HALF_SUCCESS= new BaseResponse("1", "部分成功");

    public static final BaseResponse SUCCESS= new BaseResponse("2", "交易成功");

    public static final BaseResponse UNKNOEW= new BaseResponse("3", "支付中未知");
}
