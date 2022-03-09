package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.utils;

import com.haohan.framework.utils.MD5Util;

import java.util.UUID;

/**
 * @author shenyu
 * @create 2018/11/22
 */
public class YiCloudUtils {

    public static String getSign(String clientId,int timeStamp,String clientSecret){
        return MD5Util.MD5(clientId+String.valueOf(timeStamp)+clientSecret);
    }

    public static String getUUID4(){
        return UUID.randomUUID().toString().toUpperCase();
    }

}
