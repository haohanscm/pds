package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/****
 * 向微信后台发送prepayid ，并同时可以告知错误信息 ，方便给用户以提示
 *
 * @author lenovo
 *
 */
@XStreamAlias("xml")
public class WeixinCompletePrepay {

    /******* UCCESS/FAIL,此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断 ***********/
    @XStreamAlias("return_code")
    @XStreamCDATA
    private String returnCode;

    @XStreamAlias("return_msg")
    @XStreamCDATA
    private String returnMsg;

    @XStreamAlias("appid")
    @XStreamCDATA
    private String appId;

    @XStreamAlias("mch_id")
    @XStreamCDATA
    private String mchId;

    @XStreamAlias("nonce_str")
    @XStreamCDATA
    private String nonceStr;

    @XStreamAlias("prepay_id")
    @XStreamCDATA
    private String prepayId;

    @XStreamAlias("result_code")
    @XStreamCDATA
    private String resultCode;

    @XStreamAlias("err_code_des")
    @XStreamCDATA
    private String errCodeDes;

    @XStreamAlias("sign")
    @XStreamCDATA
    private String sign;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
