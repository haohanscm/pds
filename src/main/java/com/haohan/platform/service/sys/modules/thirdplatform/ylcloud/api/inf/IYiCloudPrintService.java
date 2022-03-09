package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.inf;

import com.haohan.framework.entity.BaseResp;

/**
 * 易联云打印Service
 * @author shenyu
 * @create 2018/11/22
 */
public interface IYiCloudPrintService{
    //文本打印
    BaseResp textPrint(String clientId,String content,String originId,String machineCode);

    //图形打印
    BaseResp picturePrint();

    //面单打印
    BaseResp expressPrint();
}
