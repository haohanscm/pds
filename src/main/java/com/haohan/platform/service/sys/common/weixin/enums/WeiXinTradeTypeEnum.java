package com.haohan.platform.service.sys.common.weixin.enums;

import com.haohan.framework.enums.SuperEnum;
import com.haohan.framework.utils.EnumUtils;

/****
 * 微信支付类型
 *
 * @author lcw
 *
 */
public enum WeiXinTradeTypeEnum implements SuperEnum {
    /**** 公众号支付 ****/
    JSAPI(0, "JSAPI"),

    /**** 原生扫码支付 ****/
    NATIVE(1, "NATIVE");

    /**
     * 构造函数
     *
     * @param code        编码
     * @param description 说明
     */
    private WeiXinTradeTypeEnum(int code, String description) {
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
    public static WeiXinTradeTypeEnum codeOf(Integer code) {
        Object obj = EnumUtils.get(WeiXinTradeTypeEnum.class.getName() + code);
        if (null != obj) {
            return (WeiXinTradeTypeEnum) obj;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
