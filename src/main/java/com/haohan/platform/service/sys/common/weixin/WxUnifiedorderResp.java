package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 统一下单后 返回的订单详情
 *
 * @author lcw
 *
 */
@XStreamAlias("xml")
public class WxUnifiedorderResp extends WxPayBaseResp {

    /**
     *
     */
    public static final long serialVersionUID = 1048891868612549342L;

    /**** 业返回状态码 SUCCESS/FAIL *****/
    public static String RETURN_CODE_SUCCESS = "SUCCESS";

    /**** 业务结果 SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断 *****/
    public static String RESULT_CODE_SUCCESS = "SUCCESS";

    @XStreamAlias("appid")
    @XStreamCDATA
    private String appid;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("device_info")
    @XStreamCDATA
    private String deviceInfo;

    @XStreamAlias("nonce_str")
    @XStreamCDATA
    private String nonceStr;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("prepay_id")
    private String prepayId;

    @XStreamAlias("code_url")
    private String codeUrl;

    public String getAppid() {
        return appid;
    }

    public String getMchId() {
        return mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

}
