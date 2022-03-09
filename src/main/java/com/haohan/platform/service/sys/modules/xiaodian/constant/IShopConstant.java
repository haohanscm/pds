package com.haohan.platform.service.sys.modules.xiaodian.constant;

import com.haohan.platform.service.sys.common.utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dy on 2018/8/28.
 */
public interface IShopConstant {

    // 店铺级别
    enum ShopLevelType{
        head("0","总店"),
        sub("1","子店"),
        pds("2","采购配送店")
        ;

        private String code;
        private String desc;

        ShopLevelType(String code, String desc) {
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

    // 店铺 状态
    enum ShopStatus{
        disable("-1","停用"),check("0", "待审核"), enable("2","启用");

        private String code;
        private String desc;

        ShopStatus(String code, String desc) {
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

    // 店铺服务模式
    enum ShopServiceType{
        single("1","云小店"),
        headChain("2","云连锁总店"),
        subChain("3","云连锁分店");

        private String code;
        private String desc;

        private static final Map<String, ShopServiceType> MAP = new HashMap<>(8);

        static {
            for (ShopServiceType type : ShopServiceType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static ShopServiceType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        ShopServiceType(String code, String desc) {
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

    // 店铺图片类型
    enum ShopPhotoType{
        logo("10","店铺Logo"),
        payCode("11","店铺收款二维码"),
        qrcode("12","店铺二维码"),
        card("13","名片二维码");

        private String code;
        private String desc;

        private static final Map<String, ShopPhotoType> MAP = new HashMap<>(8);

        static {
            for (ShopPhotoType type : ShopPhotoType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static ShopPhotoType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        ShopPhotoType(String code, String desc) {
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

    /**
     * 店铺认证类型
     */
    enum ShopAuthType{
        no("0","未认证"),
        yes("1","已认证")
        ;

        private String code;
        private String desc;
        private static final Map<String, ShopAuthType> MAP = new HashMap<>(8);

        static {
            for (ShopAuthType type : ShopAuthType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static ShopAuthType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        ShopAuthType(String code, String desc) {
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

    /**
     * 聚合平台店铺类型
     */
    enum AggregationShopType {
        non("0","非聚合"),
        agriculture("1","农贸城")
        ;

        private String code;
        private String desc;

        private static final Map<String, AggregationShopType> MAP = new HashMap<>(8);

        static {
            for (AggregationShopType type : AggregationShopType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static AggregationShopType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        AggregationShopType(String code, String desc) {
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

    /**
     * 店铺交易类型
     */
    enum ShopTradeType {
        face("1","当面交易"),
        online("2","在线下单"),
        home("3","送货上门")
        ;

        private String code;
        private String desc;

        private static final Map<String, ShopTradeType> MAP = new HashMap<>(8);

        static {
            for (ShopTradeType type : ShopTradeType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static ShopTradeType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        ShopTradeType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        /**
         * 检查传入的交易类型 并返回正确的
         * @return
         */
        public static String tradeTypeCheck(String tradeTypes){
            String result = "";
            if(StringUtils.isNotEmpty(tradeTypes)){
                String[] typeArray = StringUtils.split(tradeTypes, ",");
                Set<String> typeSet = new HashSet<>(8);
                for(String type:typeArray){
                    if(null != getTypeByCode(type)){
                        typeSet.add(type);
                    }
                }
                result = StringUtils.join(typeSet, ",");
            }
            return result;
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
