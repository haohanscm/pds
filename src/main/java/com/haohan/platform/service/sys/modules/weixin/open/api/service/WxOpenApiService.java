package com.haohan.platform.service.sys.modules.weixin.open.api.service;

import com.haohan.platform.service.sys.common.utils.SpringContextHolder;
import com.haohan.platform.service.sys.modules.weixin.open.api.inf.IWxOpenAppService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.OpenPlatformConfigService;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgw on 2017/12/23.
 */
@Service
public class WxOpenApiService extends WxOpenServiceImpl {

    private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);

    private  static String  token  = "haohanshop";
    private  static String  aesKey  = "K7r0t0b2yCSkItLrfNd3WefhHhucJYPaCWlfHBTjuRY";

    @Autowired
    private OpenPlatformConfigService platformConfigService;

    private static  Map<String,WxOpenApiService> apiService = new HashMap<>();


    @PostConstruct
    public void init() {

        if(IWxOpenAppService.isTest) {

            String appId = "wx50368461596ef49e";
            String appSecret = "a4f86c93d1f3fbdd9ddfc530b9219419";
            WxOpenInRedisConfigStorage inRedisConfigStorage = new WxOpenInRedisConfigStorage(jedisPool);
            inRedisConfigStorage.setComponentAppId(appId);
            inRedisConfigStorage.setComponentAppSecret(appSecret);
            inRedisConfigStorage.setComponentToken(token);
            inRedisConfigStorage.setComponentAesKey(aesKey);
            WxOpenApiService service = new WxOpenApiService();
            service.setWxOpenConfigStorage(inRedisConfigStorage);
            apiService.put(fetchServiceName(appId), service);
        }else {

            //从数据库获取批量加载
            OpenPlatformConfig config = new OpenPlatformConfig();
            config.setAppType(2);
            config.setStatus(IMerchantConstant.available);
          List<OpenPlatformConfig> configs = platformConfigService.findList(config);

          if(CollectionUtils.isNotEmpty(configs)) {

              for (OpenPlatformConfig platformConfig : configs) {
                  WxOpenInRedisConfigStorage inRedisConfigStorage = new WxOpenInRedisConfigStorage(jedisPool);
                  inRedisConfigStorage.setComponentAppId(platformConfig.getAppId());
                  inRedisConfigStorage.setComponentAppSecret(platformConfig.getAppSecret());
                  inRedisConfigStorage.setComponentToken(token);
                  inRedisConfigStorage.setComponentAesKey(aesKey);
                  WxOpenApiService service1 = new WxOpenApiService();
                  service1.setWxOpenConfigStorage(inRedisConfigStorage);
                  apiService.put(fetchServiceName(platformConfig.getAppId()), service1);
              }
          }

        }
    }



    public WxOpenApiService fetchByAppId(String appId){
        return apiService.get(fetchServiceName(appId));
    }



    private String fetchServiceName(String appId){
        return appId.concat("-WxOpenApiService");
    }


}
