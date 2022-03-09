/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TSaleAmountResp
 * Author:   Lenovo
 * Date:     2018/6/15 9:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/6/15
 * @since 1.0.0
 */
public class TSaleAmountResp implements Serializable {
    private BigDecimal wxMaPay;

    private BigDecimal aliMaPay;

    private BigDecimal wxAuthPay;

    private BigDecimal aliAuthPay;

    private BigDecimal cashPay;

    private BigDecimal bankCardPay;

    private BigDecimal wxPay;

    private BigDecimal aliPay;

    public BigDecimal getWxMaPay() {
        return wxMaPay;
    }

    public void setWxMaPay(BigDecimal wxMaPay) {
        this.wxMaPay = wxMaPay;
    }

    public BigDecimal getAliMaPay() {
        return aliMaPay;
    }

    public void setAliMaPay(BigDecimal aliMaPay) {
        this.aliMaPay = aliMaPay;
    }

    public BigDecimal getWxAuthPay() {
        return wxAuthPay;
    }

    public void setWxAuthPay(BigDecimal wxAuthPay) {
        this.wxAuthPay = wxAuthPay;
    }

    public BigDecimal getAliAuthPay() {
        return aliAuthPay;
    }

    public void setAliAuthPay(BigDecimal aliAuthPay) {
        this.aliAuthPay = aliAuthPay;
    }

    public BigDecimal getCashPay() {
        return cashPay;
    }

    public void setCashPay(BigDecimal cashPay) {
        this.cashPay = cashPay;
    }

    public BigDecimal getBankCardPay() {
        return bankCardPay;
    }

    public void setBankCardPay(BigDecimal bankCardPay) {
        this.bankCardPay = bankCardPay;
    }

    public BigDecimal getWxPay() {
        return wxPay;
    }

    public void setWxPay(BigDecimal wxPay) {
        this.wxPay = wxPay;
    }

    public BigDecimal getAliPay() {
        return aliPay;
    }

    public void setAliPay(BigDecimal aliPay) {
        this.aliPay = aliPay;
    }
}
