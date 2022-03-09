package com.haohan.platform.service.sys.modules.xiaodian.constant;

import com.haohan.framework.utils.EnumUtils;

/**
 * @author shenyu
 * @create 2018/10/1
 */
public interface IUserConstant {

    //开放平台用户状态
    enum OpenUserStatus{

        subscribe("subscribe","关注"),unsubscribe("unsubscribe","未关注");

        OpenUserStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString()+code,this);
        }

        public static OpenUserStatus valueOfOpenUserStatus(String code) {
            Object obj = EnumUtils.get(OpenUserStatus.class.getName() + code);
            if (null != obj) {
                return (OpenUserStatus) obj;
            }
            return null;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    enum RegType{
        loginName("0","登录名"),
        mobile("1","手机"),
        email("2","邮箱"),
        qq("3","QQ"),
        sinaBlog("4","新浪微博"),
        wechat("5","微信"),
        alipay("6","支付宝");

        RegType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    enum RegFrom{
        web("0","PC端"),
        app("1","移动端"),
        wap("2","wap"),
        wxapp("3","微信小程序");


        RegFrom(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
