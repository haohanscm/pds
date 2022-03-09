package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminCustomerListReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminCustomerService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 小程序用户管理
 *
 * @author dy
 * @date 2019/2/13
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/customer")
public class PdsAdminCustomerCtrl extends BaseController {

    @Autowired
    private IPdsAdminCustomerService pdsAdminCustomerService;

    /**
     * 获取用户列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "findList")
    @ResponseBody
    public BaseResp findList(@Validated PdsAdminCustomerListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        UserOpenPlatform userOpenPlatform = new UserOpenPlatform();
        userOpenPlatform.setMerchantId(listReq.getMerchantId());
        userOpenPlatform.setUid(listReq.getUid());
        userOpenPlatform.setNickName(listReq.getNickName());
        userOpenPlatform.setAppType(listReq.getAppType());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        userOpenPlatform.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminCustomerService.findCustomerList(listReq.getPmId(), userOpenPlatform);
        return baseResp;
    }


}
