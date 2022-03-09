package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.entity.Entry;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.framework.utils.http.HttpUtils;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.constant.IYiCloudConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req.YiCloudBaseReq;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp.YiCloudBaseResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.utils.YiCloudUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.slf4j.Logger;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/11/22
 */
public abstract class YiCloudAbstractService implements IYiCloudConstant {
    protected abstract Logger getLogger();

    protected <T extends Serializable> T sendReq(String url, Map reqMap, Class<T> clazz, BaseResp baseResp){
        Entry<String,String> entry = HttpUtils.post(url,reqMap,Charset.forName("UTF-8"));
        return transResp(entry.getMsg(),url,clazz,baseResp,JacksonUtils.toJson(reqMap));
    }

    private <T extends Serializable> T transResp(String respData,String url,Class<T> clazz,BaseResp baseResp,String reqParams){
        T t = null;
        try {
            if (StringUtils.isEmpty(respData)){
                baseResp.setMsg("返回参数为空");
                return t;
            }
            YiCloudBaseResp cloudBaseResp = JacksonUtils.readValue(respData,YiCloudBaseResp.class);
            if (!cloudBaseResp.isSuccess()){
                baseResp.setMsg(cloudBaseResp.getMsg());
                return t;
            }
            if (cloudBaseResp.getBody() instanceof Map){
                t = CommonUtils.mapToBean((Map<String, Object>) cloudBaseResp.getBody(),clazz.newInstance());
            }
            baseResp.success();
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setMsg("请求失败");
        } finally {
            getLogger().debug("\nreqApi:{},\nreqParams:{}\n,respParams:{}", url, reqParams, respData);
            return t;
        }
    }


    protected <T extends YiCloudBaseReq> T fillBaseInfo(String clientId, Class<T> clazz,BaseResp baseResp,OpenPlatformConfig openPlatformConfig){
        T t = null;
        try {
            YiCloudBaseReq yiCloudBaseReq = clazz.newInstance();
            int timestamp = DateUtils.getUnixTime().intValue();
            String clientSecret = openPlatformConfig.getAppSecret();

            yiCloudBaseReq.setClient_id(clientId);
            yiCloudBaseReq.setTimestamp(timestamp);
            yiCloudBaseReq.setId(YiCloudUtils.getUUID4());
            yiCloudBaseReq.setSign(YiCloudUtils.getSign(clientId,timestamp,clientSecret));
            t = (T) yiCloudBaseReq;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return t;
        }
    }

}
