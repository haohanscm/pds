package com.haohan.platform.service.sys.common.weixin.enums;

import com.haohan.framework.enums.SuperEnum;
import com.haohan.framework.utils.EnumUtils;

/****
 * 微信对账异常明细 对账结果 0 没问题 1 状态不一致 2 金额不一致 3 仅微信存在 4 仅我们存在 5 其他错误
 *
 */
public enum WxCheckBillExceptionDetailResultEnum implements SuperEnum {
    /**
     * 0 没问题
     */
    NONE(0, "NONE"),
    /**
     * 1 状态不一致
     */
    STATUS(1, "STATUS"),
    /**
     * 2 金额不一致
     */
    AMOUNT(2, "AMOUNT"),
    /**
     * 3 仅微信存在
     */
    WEIXIN(3, "WEIXIN"),
    /**
     * 4 仅我们存在
     */
    OURS(4, "OURS"),
    /**
     * 5 其他错误
     */
    OTHER(5, "OTHER"),
    /**
     * 6 退款表存在微信不存在
     */
    REFUND(6, "REFUND"),
    /**
     * 7 订单表存在 退款表不存在
     */
    ORDER(7, "ORDER");

    /**
     * 构造函数
     *
     * @param code        编码
     * @param description 说明
     */
    private WxCheckBillExceptionDetailResultEnum(int code, String description) {
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
    public static WxCheckBillExceptionDetailResultEnum codeOf(Integer code) {
        Object obj = EnumUtils.get(WxCheckBillExceptionDetailResultEnum.class.getName() + code);
        if (null != obj) {
            return (WxCheckBillExceptionDetailResultEnum) obj;
        }
        return null;
    }

    /**
     * 编码
     */
    private int code;

    /**
     * 描述的KEY
     */
    private String description;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
