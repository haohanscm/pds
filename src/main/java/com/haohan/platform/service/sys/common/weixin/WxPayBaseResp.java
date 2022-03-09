package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class WxPayBaseResp implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 701599300033173756L;

    /***
     * SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     ****/
    @XStreamAlias("return_code")
    private String returnCode;

    /**** 返回信息，如非空，为错误原因 签名失败 *****/
    @XStreamAlias("return_msg")
    private String returnMsg;

    /*** SUCCESS/FAIL ***/

    @XStreamAlias("result_code")
    private String resultCode;

    /*** SYSTEMERROR ***/
    @XStreamAlias("err_code")
    private String errCode;

    /*** 错误返回的信息描述 ***/
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

}
