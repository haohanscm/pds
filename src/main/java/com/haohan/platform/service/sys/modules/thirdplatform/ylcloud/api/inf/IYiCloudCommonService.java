package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.inf;

import com.haohan.framework.entity.BaseResp;
import org.springframework.stereotype.Service;

/**
 * 易联云公共Service
 * token获取,token刷新,终端授权
 * @author shenyu
 * @create 2018/11/22
 */
@Service
public interface IYiCloudCommonService{

    //获取AccessToken
    BaseResp fetchAccessToken(String clientId);

    //刷新token
    BaseResp refreshToken(String clientId);

    //终端授权
    BaseResp addPrinter(String clientId,String machineCode,String msign,String printerName);

    //设置logo
    BaseResp setLogo(String clientId,String machineCode,String imgUrl);

    //取消logo
    BaseResp cancelLogo(String clientId,String machineCode);

    //推送url设置与取消
    BaseResp setCallBackUrl(String clientId,String cmd,String url,String status);


}
