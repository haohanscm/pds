package com.haohan.platform.service.sys.modules.xiaodian.util;

import com.haohan.platform.service.sys.common.utils.CacheUtils;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.DateUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 商家/渠道商 管理后台 缓存工具类
 */
public class ManagePlatformCacheKeyUtils implements Serializable {
    // 缓存名称
    public static final String CACHE_NAME = "managePlatform";
    /**
     * token 有效时间 默认1小时(刷新时间10分钟)
     * 单位：毫秒
     */
    public static long DEFAULT_TIMEOUT_SECOND = 70 * 60 * 1000;
    /**
     * token 最短刷新时间 默认10分钟
     * 单位：毫秒
     */
    public static long DEFAULT_FLUSH_SECOND = 10 * 60 * 1000;

    public static String getCacheName() {
        return CACHE_NAME;
    }

    // 设置缓存 键名称
    public static String fetchCacheKey(String key) {
        return CACHE_NAME.concat("-").concat(key);
    }

    // 获取 登录用户的信息
    public static List<String> getLoginAdmin(String userId){
        String cacheName = getCacheName();
        String cacheKey = fetchCacheKey("loginAdmin");
        Map<String,List<String>> adminMap;
        List<String> list ;
        try{
            adminMap = (Map<String,List<String>>) CacheUtils.get(cacheName,cacheKey);
            list = adminMap.get(userId);
        } catch (Exception e){
            list = new ArrayList<>();
        }
        return list;
    }

    // 存入 登录用户的信息
    public static boolean setLoginAdmin(String userId, List<String> list) {
        String cacheName = getCacheName();
        String cacheKey = fetchCacheKey("loginAdmin");
        Map<String, List<String>> adminMap;
        try {
            Object obj = CacheUtils.get(cacheName, cacheKey);
            if (null == obj) {
                adminMap = new HashMap<>();
            } else {
                adminMap = (Map<String, List<String>>) obj;
            }
            adminMap.put(userId, list);
            CacheUtils.put(cacheName, cacheKey, adminMap);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置 登录用户的信息(token/userType/date)
     * @param userId 用户标识
     * @param token 登录令牌  date 上次使用时间
     * @param userType 账号类型  渠道、商户、店铺
     * @return
     */
    public static boolean setUserInfo(String userId, String token, String userType) {
        List<String> list = new ArrayList<>();
        list.add(token);
        list.add(userType);
        // 当前日期字符串
        list.add(DateUtils.getDateTime());
        return setLoginAdmin(userId, list);
    }

    /**
     * 获取 登录用户的信息(token/userType/date)
      * @return
     */
    public static Object[] fetchUserInfo(String userId) {
        String token = null;
        String userType = null;
        Date date = null;
        List<String> list = getLoginAdmin(userId);
        if (!Collections3.isEmpty(list)) {
            token = list.get(0);
            userType = list.get(1);
            date = DateUtils.parseDate(list.get(2));
        }
        return new Object[]{token, userType, date};
    }

    // 判断是否在可用时间内
    public static boolean isUsable(Date date) {
        if (null == date) {
            return false;
        }
        if (new Date().getTime() - date.getTime() > DEFAULT_TIMEOUT_SECOND) {
            return false;
        }
        return true;
    }

    // 更新 登录用户的token有效时间
    public static boolean flushTokenTime(String userId) {
        String cacheName = getCacheName();
        String cacheKey = fetchCacheKey("loginAdmin");
        Map<String, List<String>> adminMap;
        List<String> list;
        try {
            adminMap = (Map<String, List<String>>) CacheUtils.get(cacheName, cacheKey);
            list = adminMap.get(userId);
            // 更新 时间
            list.set(2, DateUtils.getDateTime());
            adminMap.put(userId, list);
            CacheUtils.put(cacheName, cacheKey, adminMap);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
