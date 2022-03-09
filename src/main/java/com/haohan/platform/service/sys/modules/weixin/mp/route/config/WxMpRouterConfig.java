package com.haohan.platform.service.sys.modules.weixin.mp.route.config;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgw on 2018/11/5.
 */
public class WxMpRouterConfig {


    public static final Map<String,String> appRouteMap = new HashMap<>();

    static {
        appRouteMap.put("wxf4110774c0fffb78","configRoute_1");
    }

    public static void config(String appId, WxMpMessageRouter router) {

        if(appRouteMap.containsKey(appId)){
            //方法名固定与key对应
            if(appId.equals("wxf4110774c0fffb78")) {
                configRoute_1(router);
            }
        }
    }

    private static void configRoute_1(WxMpMessageRouter router){

        //设置自定义路由
        router.rule().end();

    }


}
