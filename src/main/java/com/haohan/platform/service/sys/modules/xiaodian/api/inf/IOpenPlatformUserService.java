package com.haohan.platform.service.sys.modules.xiaodian.api.inf;

import com.haohan.framework.entity.BaseResp;

/**
 * @author dy
 * @date 2019/9/6
 */
public interface IOpenPlatformUserService {

    /**
     * 开放平台用户新增
     * @param appId
     * @param code
     * @param userInfo
     * @param encryptedData
     * @param iv
     * @return
     */
    BaseResp openUserAdd(String appId, String code, String userInfo, String encryptedData, String signature, String iv, String regIp);

    /**
     * 微信登录凭证校验
     * @param appId
     * @param code
     * @return
     */
    BaseResp wechatCheck(String appId, String code);

}
