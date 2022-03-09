package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

public class WithdrawQueryResponse extends BaseResponse {

    private String transId;
    private String reqId;
    private String bankMchtId;
    private String withdrawResult;
    private String resultMsg;
    private String withdrawAmount;
    private String currency;
    private String account;
    private String accountName;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getBankMchtId() {
        return bankMchtId;
    }

    public void setBankMchtId(String bankMchtId) {
        this.bankMchtId = bankMchtId;
    }

    public String getWithdrawResult() {
        return withdrawResult;
    }

    public void setWithdrawResult(String withdrawResult) {
        this.withdrawResult = withdrawResult;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
