package com.haohan.platform.service.sys.modules.xiaodian.constant;

import com.haohan.framework.utils.EnumUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgw on 2018/5/3.
 */
public interface ICommonConstant {
    enum YesNoType{
        no("0","否"), yes("1","是");

        private String code;
        private String desc;

        private static final Map<String, YesNoType> MAP = new HashMap<>(8);

        static {
            for (YesNoType type : YesNoType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static YesNoType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        YesNoType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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

    enum IsEnable{
        disable("0","停用"),
        enable("1","启用");

        private String code;
        private String desc;

        IsEnable(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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

    enum ProductSys{
        hh_pay("010","B端","浩瀚付"),
        cloud_mall("011","B端","云商城"),
        shop_helper("012","B端","店铺管家"),
        delivery_helper("013","B端","店铺助手"),
        wx_app("020","C端","微信小程序"),
        hh_platform("030","P端","运营平台"),
        website("031","P端","官网"),
        partner_admin("032","P端","合作商后台"),
        merchant_admin("033","P端","商户后台"),
        ;

        ProductSys(String productNo, String productType, String desc) {
            this.productNo = productNo;
            this.productType = productType;
            this.desc = desc;
            EnumUtils.put(getClass().getName()+productNo,this);
        }

        public static ProductSys getProductSysEnum(String productNo){
            for(ProductSys entity:ProductSys.values()){
                if(productNo.equals(entity.getProductNo())){
                    return entity;
                }
            }
            return  null;
        }

        private String productNo;       //产品编号
        private String productType;     //产品端类型
        private String desc;            //描述

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

    }

    enum AppType{
        QQ(0,"QQ"),
        sina(1,"新浪"),
        wechat(2,"微信"),
        aldOpenApp(3,"阿拉丁开放平台"),
        yilCloud(4,"易联云开放平台")
        ;

        private Integer code;
        private String desc;

        AppType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    enum XdServiceType {
        reserve_sys("0","预定系统"),
        pds_sys("1","采购配送系统");

        private String code;
        private String desc;

        XdServiceType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

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
