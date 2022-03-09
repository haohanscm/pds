package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;

/**
 * 小程序用户 管理
 *
 * @author dy
 * @date 2019/2/13
 */
public interface IPdsAdminCustomerService {

    /**
     * 获取用户列表
     *
     * @param pmId
     * @param userOpenPlatform
     * @return
     */
    BaseResp findCustomerList(String pmId, UserOpenPlatform userOpenPlatform);

}
