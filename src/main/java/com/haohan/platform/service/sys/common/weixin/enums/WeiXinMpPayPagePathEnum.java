package com.haohan.platform.service.sys.common.weixin.enums;

import com.haohan.framework.enums.SuperStrEnum;
import com.haohan.framework.utils.EnumUtils;

/****
 * 微信公众号支付授权页面
 *
 * @author lcw
 *
 */
public enum WeiXinMpPayPagePathEnum implements SuperStrEnum {
    /**
     * 直播页面打赏
     */
    LIVE("live", "/yzmm/account/course/toLiveDetailPay"),

    /**
     * 点播页面打赏
     */
    VOD("vod", "/yzmm/account/course/toVodDetailPay"),
    /**
     * 用户自主注册
     */
    NEWBIE("newbie", "/yzmm/account/newbie/toPay"),
    /**
     * 用户参加活动
     */
    ACTIVITY("activity", "/yzmm/account/activity/toPay"),
    /**
     * 首页
     */
    INDEX("index", "/yzmm/account/knowledge/toIndex"),
    /**
     * 三千问列表
     */
    SQWLIST("sqwlist", "/yzmm/account/knowledge/toKnowledgeSearchList"),
    /**
     * 三千问详情
     */
    SQWDTL("sqwdtl", "/yzmm/account/knowledge/toDetail"),
    /**
     * 会员信息页面
     */
    VIPSTATUS("vipstatus", "/yzmm/account/toVIPStatus"),
    /**
     * 打包课程组购买
     */
    PACKCOURSEGROUP("packcoursegroup", "/yzmm/account/coursegroup/packcourseGroupPay"),
    /**
     * 喵问喵答购买
     */
    MIAOQA("miaoqa", "/yzmm/account/miaoqa/tocreateMiaoQA"),
    /**
     * 用户分享购买体验会员卡链接
     */
    SHAREFRIENDINVITE("sharefriendinvite", "/yzmm/account/shareFriendInvitation/invitation"),
    /**
     * 主题课包购买
     */
    TOPICCOURSEPACK("topiccoursepack", "/yzmm/account/course/topic/toIndex"),

    PREKINDERGARTEN("prekindergarten", "/yzmm/account/activity/prekindergarten/toEnlistPage"),

    USERINFO("userinfo", "/yzmm/account/user/infoPay");

    /**
     * 构造函数
     *
     * @param code        编码
     * @param description 说明
     */
    private WeiXinMpPayPagePathEnum(String code, String description) {
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
    public static WeiXinMpPayPagePathEnum codeOf(String code) {
        Object obj = EnumUtils.get(WeiXinMpPayPagePathEnum.class.getName() + code);
        if (null != obj) {
            return (WeiXinMpPayPagePathEnum) obj;
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

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
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
