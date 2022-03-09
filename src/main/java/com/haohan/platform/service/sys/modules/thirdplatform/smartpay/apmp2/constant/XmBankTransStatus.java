package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant;

import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BaseResponse;

import java.io.Serializable;

/**
 * Created by zgw on 2018/3/27.
 */
public class XmBankTransStatus implements Serializable{

    public static final BaseResponse FAIL = new BaseResponse("0", "交易失败");

    public static final BaseResponse HALF_SUCCESS= new BaseResponse("1", "部分成功");

    public static final BaseResponse UNKNOEW= new BaseResponse("3", "未知");

    public static final BaseResponse SUCCESS= new BaseResponse("2", "交易成功");

    public static final BaseResponse CANCEL= new BaseResponse("4", "撤销");

    public static final BaseResponse CLOSED= new BaseResponse("5", "已关闭");

    public static final BaseResponse HAVE_REFUND= new BaseResponse("6", "有退款");

    public static final BaseResponse CANCELING= new BaseResponse("7", "撤销中");

    public static final BaseResponse REFUNDING= new BaseResponse("6", "退款中");




}
