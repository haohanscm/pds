package com.haohan.platform.service.sys.modules.xiaodian.util;

import java.io.Serializable;

/**
 * 商家缓存工具类
 */
public class MerchantCacheKeyUtils implements Serializable {
    // 缓存名称
    static final String CACHE_NAME = "merchant";

    public static String getCacheName(){
        return CACHE_NAME;
    }

    // 存放商家应用 对应的键
    public  static String getAppListKey(){
        return CACHE_NAME.concat("-").concat("appList");
    }

    // 设置缓存 键名称
    public  static String fetchCacheKey(String key){
        return CACHE_NAME.concat("-").concat(key);
    }

}
