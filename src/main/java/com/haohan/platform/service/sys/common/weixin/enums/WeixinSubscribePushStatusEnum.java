package com.haohan.platform.service.sys.common.weixin.enums;


import com.haohan.framework.enums.SuperEnum;
import com.haohan.framework.utils.EnumUtils;

/**
 * @author zhaokuner
 * @ClassName: WeixinSubscribePushStatusEnum
 * @Description: 结果
 * @date 2016年12月1日 下午5:37:37
 */
public enum WeixinSubscribePushStatusEnum implements SuperEnum {
    /**
     * 0 发送成功
     */
    SEND_SUCCESS(0, "发送成功"),
    /**
     * 1 发送失败
     */
    SEND_FAILURE(1, "发送失败"),
    /**
     * 2 确认成功
     */
    CONFIRM_SUCCESS(2, "确认成功"),
    /**
     * 3 确认失败
     */
    CONFIRM_FAILURE(3, "确认失败");

    /**
     * 构造函数
     *
     * @param code        编码
     * @param description 说明
     */
    private WeixinSubscribePushStatusEnum(int code, String description) {
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
    public static WeixinSubscribePushStatusEnum codeOf(Integer code) {
        if (code != null) {
            Object obj = EnumUtils.get(WeixinSubscribePushStatusEnum.class.getName() + code);
            if (null != obj) {
                return (WeixinSubscribePushStatusEnum) obj;
            }
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
