package com.haohan.platform.service.sys.modules.xiaodian.api.constant;


import com.haohan.framework.utils.EnumUtils;

/**
 * Created by zgw on 2017/12/8.
 * 银行接口服务类型
 */
public enum BankServiceType {


    /**
     * 商户入驻
     */
    MerchantReg("9007", "80", "", "", "商户入驻", "商户入驻"),

    /**
     * 费率设置
     */
    PayRateConfig("9008", "81", "", "", "费率设置", "费率设置"),

    /**
     * 微信扫码支付 OEM:4001
     */
    WexinQrCode("5001", "01", "wechat", "0","微信扫码支付", "微信扫码支付"),
    /**
     * 微信刷卡支付 OEM:4005
     */
    WexinAuthPay("5005","02","wechat","","微信刷卡支付","微信刷卡支付"),
    /**
     * 微信公众号支付 OEM:4002
     */
    WexinMpPay("5002", "03","wechat","1","微信公众号支付","微信公众号支付"),
    /**
     * 微信小程序支付 OEM:4002
     */
    WexinAppPay("5002", "04","wechat","1","微信小程序","微信小程序支付"),
    /**
     * 支付宝扫码支付 OEM:4003
     */
    AliPayQrCode("5003","05","alipay","0","支付宝扫码支付","支付宝扫码支付"),
    /**
     * 支付宝条码支付 OEM:4006
     */
    AliAuthPay("5006","06","alipay","","支付宝条码支付","支付宝条码支付"),
    /**
     * 支付宝服务窗支付 OEM:4004
     */
    AliServicePay("5004","07","alipay","1","支付宝服务窗支付","支付宝服务窗支付"),
    /**
     * QQ支付 OEM:4004
     */
    QQPay("5004","08","qq","1","QQ支付","QQ支付"),
    /**
     * 百度支付 OEM:4004
     */
    BaiDuPay("5004","09","baidu","1","百度支付","百度支付"),
    /**
     * 京东支付 OEM:4004
     */
    JdPay("5004","10","jd","1","京东支付","京东支付"),
    /**
     * 京东Online支付 OEM:4004
     */
    JdOnlinePay("5004","11","jdOnline","1","京东Online支付","京东Online支付"),
    /**
     *
     */
    CashPay("5004","12","cash","","现金支付","现金支付"),
    /**
     * 退款申请 OEM:4007
     */
    RefundApply("5007","82","","","退款申请","退款申请"),
    /**
     * 交易状态查询 OEM:4008
     */
    PayStatusQuery("5008","83","","","交易状态查询","交易状态查询"),
    /**
     * 订单撤销 OEM:4009
     */
    OrderCancel("5009","84","","","订单撤销","订单撤销"),
    /**
     * 账户余额查询 OEM:4010
     */
    AccountQuery("5009","85","","","账户余额查询","账户余额查询"),

    /**
     * 支付通知
     */
    PayNotify("9005","86","","","支付通知","支付通知"),

    ;


    private String bizType;//消息类型
    private String code;//接口编码
    private String channel;//支付渠道
    private String payType;//支付方式
    private String name;//接口名称
    private String desc;//接口描述


    public static boolean isEnum(String code) {
        BankServiceType[] values = BankServiceType.values();
        for (BankServiceType e : values) {
            if (e.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    BankServiceType(String bizType, String code, String channel, String payType, String name, String desc) {
        this.bizType = bizType;
        this.code = code;
        this.channel = channel;
        this.payType = payType;
        this.name = name;
        this.desc = desc;
        EnumUtils.put(this.getClass().getName() + code, this);
    }

    public static BankServiceType valueOfServiceType(String code) {
        Object obj = EnumUtils.get(BankServiceType.class.getName() + code);
        if (null != obj) {
            return (BankServiceType) obj;
        }
        return null;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

