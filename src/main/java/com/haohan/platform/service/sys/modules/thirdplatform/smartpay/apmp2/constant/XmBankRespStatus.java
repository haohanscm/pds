package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant;

import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BaseResponse;

import java.io.Serializable;

/**
 * Created by zgw on 2018/3/22.
 */
public class XmBankRespStatus implements Serializable{

    /** 公共响应 */
    /**00, "操作成功"*/
    public static final BaseResponse SUCCESS = new BaseResponse("00", "请求受理业务成功");

    public static final BaseResponse FAIL = new BaseResponse("99", "请求受理业务失败");

    public static final BaseResponse ERROR = new BaseResponse("81", "系统调用异常");

}
