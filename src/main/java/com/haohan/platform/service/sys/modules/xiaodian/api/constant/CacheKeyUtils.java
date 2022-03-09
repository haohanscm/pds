package com.haohan.platform.service.sys.modules.xiaodian.api.constant;

import java.io.Serializable;

/**
 * Created by zgw on 2018/4/20.
 */
public class CacheKeyUtils implements Serializable{

    static final String precachekey="haohanshop-";

    public  static String fetchMethodCacheName(String method){
        return precachekey.concat(method);
    }


    public static String getAppDataDetailListKey(String appId,String category){

        return precachekey.concat(appId).concat("-").concat(category);
    }
}
