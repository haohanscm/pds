package com.haohan.platform.service.sys.modules.weixin.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.haohan.platform.service.sys.common.utils.SpringContextHolder;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgw on 2018/4/24.
 */
@Service
public class WxAppBaseService extends WxMaServiceImpl {


    private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);

    private static Map<String,WxMaService> wxappService = new HashMap<>();

    @Autowired
    private AuthAppService authAppService;

    @PostConstruct
    public void init() throws Exception {

        List<AuthApp> authAppList = authAppService.findList(new AuthApp());
        for(AuthApp authApp:authAppList) {
            configWxappService(authApp);
        }

    }


    public WxMaService fetchByAppId(String appId){
        return wxappService.get(fetchServiceName(appId));
    }


    private static String fetchServiceName(String appId){

        return appId.concat("-WxAppService");
    }


    public static void addWxappService(AuthApp authApp) {

        if(null != authApp){
            try {
                configWxappService(authApp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void configWxappService(AuthApp authApp) throws Exception {
        WxMaService wxService = new cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl();
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        BeanUtils.copyProperties(config,authApp);
        config.setAccessToken(authApp.getAccessToken());
        config.setAppid(authApp.getAppId());
        config.setSecret(authApp.getAppSecret());
        config.setToken(authApp.getFlushToken());
        wxService.setWxMaConfig(config);
        wxappService.put(fetchServiceName(authApp.getAppId()),wxService);
    }
}
