package com.haohan.platform.service.sys.modules.xiaodian.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2018/9/21.
 */
public interface IGoodsConstant {

    /**
     * 最大价格 8位数
     */
    int MAX_PRICE = 90000000;
    /**
     * goods自增id  redis 键
     */
    String GOODS_ID_INCR_KEY = "goods_id_incr";
    /**
     * goods自增id  前缀(xiaodian)
     */
    String GOODS_ID_PREFIX = "xd";
    /**
     * goods自增id  起始值
     */
    int GOODS_ID_START = 100000;
    /**
     * goodsModelSn  拼接使用(model)
     */
    String GOODS_MODEL_INFIX = "M";

    /**
     * 公共商品库spu 自增id  redis 键
     */
    String COMMON_SPU_ID_INCR_KEY = "common_spu_id_incr";
    /**
     * 公共商品库spu 自增id  前缀(goods database)
     */
    String COMMON_SPU_ID_PREFIX = "gd";
    /**
     * 公共商品库spu 自增id  起始值
     */
    int COMMON_SPU_ID_START = 100000;
    /**
     * 公共商品库 sku   拼接使用(stock)
     */
    String COMMON_SKU_INFIX = "S";
    /**
     * 公共商品库分类 自增id  redis 键
     */
    String COMMON_CATEGORY_ID_INCR_KEY = "common_category_id_incr";
    /**
     * 公共商品库分类 自增id  前缀(goods category database)
     */
    String COMMON_CATEGORY_ID_PREFIX = "gcd";

    /**
     * 商品分类类型
     */
    enum RetailCategoryType{
        two("0","二级分类"), one("1","一级分类"),three("2","三级分类");

        private String code;
        private String desc;

        RetailCategoryType(String code, String desc) {
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

    // 商品状态
    enum GoodsStatus {
        sale("0","出售中"),
        order("1","仓库中"),
        takeout("2","已售罄");

        private String code;
        private String desc;

        GoodsStatus(String code, String desc) {
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

    // 商品来源
    enum GoodsFromType {
        platform("0","小店平台"),
        jisuapp("1","即速应用");

        private String code;
        private String desc;

        GoodsFromType(String code, String desc) {
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

    // 商品规格类型  对应即速商品规格类型
    enum GoodsModelType {

        yardage("1","尺码"),
        colour("2","颜色"),
        taste("3","口味"),
        capacity("4","容量"),
        group("5","套餐"),
        type("6","种类"),
        size("7","尺寸"),
        weight("8","重量"),
        model("9","型号"),
        style("10","款式");

        private String code;
        private String desc;

        private static final Map<String, GoodsModelType> MAP = new HashMap<>();

        static {
            for (GoodsModelType type : GoodsModelType.values()) {
                MAP.put(type.getCode(), type);
            }
        }

        public static GoodsModelType getTypeByCode(String code) {
            if (MAP.containsKey(code)) {
                return MAP.get(code);
            }
            return null;
        }

        private static final Map<String, GoodsModelType> DESC_MAP = new HashMap<>();

        static {
            for (GoodsModelType type : GoodsModelType.values()) {
                DESC_MAP.put(type.getDesc(), type);
            }
        }

        public static GoodsModelType getTypeByDesc(String desc) {
            if (DESC_MAP.containsKey(desc)) {
                return DESC_MAP.get(desc);
            }
            return null;
        }

        GoodsModelType(String code, String desc) {
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

    // 商品类型 0实体商品，1虚拟商品
    enum GoodsType {
        physical("0","实体商品"),
        virtual("1","虚拟商品"),
        productServ("2","产品服务");

        private String code;
        private String desc;

        GoodsType(String code, String desc) {
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
