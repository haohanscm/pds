package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req.YiCloudTextPrintReq;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp.YiCloudPrintResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.inf.IYiCloudPrintService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.OpenPlatformConfigService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

/**
 * @author shenyu
 * @create 2018/11/22
 */
@Service
public class YiCloudPrintServiceImpl extends YiCloudAbstractService implements IYiCloudPrintService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private OpenPlatformConfigService openPlatformConfigService;

    @Override
    public BaseResp textPrint(String clientId,String content,String originId,String machineCode) {
        BaseResp baseResp = BaseResp.newError();
        OpenPlatformConfig openPlatformConfig = openPlatformConfigService.fetchByAppIdAndType(clientId,ICommonConstant.AppType.yilCloud);
        if (null == openPlatformConfig){
            baseResp.setMsg("应用不存在");
            return baseResp;
        }
        content = HtmlUtils.htmlUnescape(content);
        YiCloudTextPrintReq textPrintReq = fillBaseInfo(clientId,YiCloudTextPrintReq.class,baseResp,openPlatformConfig);
        textPrintReq.setMachine_code(machineCode);
        textPrintReq.setOrigin_id(originId);
        textPrintReq.setContent(content);
        textPrintReq.setAccess_token(openPlatformConfig.getAccessToken());
        YiCloudPrintResp printResp = sendReq(printUrl,CommonUtils.beanToStrMap(textPrintReq),YiCloudPrintResp.class,baseResp);
        return baseResp;
    }

    @Override
    public BaseResp picturePrint() {
        return null;
    }

    @Override
    public BaseResp expressPrint() {
        return null;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
