package com.haohan.platform.service.sys.common.weixin.enums;

import com.haohan.framework.enums.SuperStrEnum;
import com.haohan.framework.utils.EnumUtils;

/**
 * 微信对账明细 交易状态 SUCCESS REFUND
 */
public enum WxCheckBillDetailTradeTypeEnum implements SuperStrEnum {
    /**
     * SUCCESS
     */
    SUCCESS("SUCCESS", "SUCCESS"),

    /**
     * REFUND
     */
    REFUND("REFUND", "REFUND");

    /**
     * 构造函数
     *
     * @param code        编码
     * @param description 说明
     */
    private WxCheckBillDetailTradeTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
        EnumUtils.put(this.getClass().getName() + code, this);
    }

    /**
     * <pre>
     * 一个便利的方法，方便使用者通过code获得枚举对象，
     * 对于非法状态，以个人处理&lt;/b&gt;
     * </pre>
     *
     * @param code
     * @return
     */
    public static WxCheckBillDetailTradeTypeEnum codeOf(String code) {
        Object obj = EnumUtils.get(WxCheckBillDetailTradeTypeEnum.class.getName() + code);
        if (null != obj) {
            return (WxCheckBillDetailTradeTypeEnum) obj;
        }
        return null;
    }

    /**
     * 编码
     */
    private String code;

    /**
     * 描述的KEY
     */
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
