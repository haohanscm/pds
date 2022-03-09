package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

/**
 * Created by weijia on 2017/3/29.
 * USE
 */
public class MerEnterResponse extends BaseResponse{
    private String bankMchtId;
    private String status;
    private String resultMsg;
    private String acquirerTypes;
    private String privateKeyStr;

    public String getBankMchtId() {
        return bankMchtId;
    }

    public void setBankMchtId(String bankMchtId) {
        this.bankMchtId = bankMchtId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getAcquirerTypes() {
        return acquirerTypes;
    }

    public void setAcquirerTypes(String acquirerTypes) {
        this.acquirerTypes = acquirerTypes;
    }

    public String getPrivateKeyStr() {
        return privateKeyStr;
    }

    public void setPrivateKeyStr(String privateKeyStr) {
        this.privateKeyStr = privateKeyStr;
    }
}
