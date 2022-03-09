package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminCustomerService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dy
 * @date 2019/02/13
 */
@Service
public class AdminCustomerServiceImpl implements IPdsAdminCustomerService {

    @Autowired
    private UserOpenPlatformService userOpenPlatformService;

    @Override
    public BaseResp findCustomerList(String pmId, UserOpenPlatform userOpenPlatform) {
        BaseResp baseResp = BaseResp.newError();
        userOpenPlatform.setPmId(pmId);
        // 查询平台商家的开放平台用户
        List<UserOpenPlatform> list = userOpenPlatformService.findListByPmId(userOpenPlatform);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = userOpenPlatform.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }
}
