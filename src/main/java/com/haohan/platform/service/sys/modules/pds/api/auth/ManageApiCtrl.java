package com.haohan.platform.service.sys.modules.pds.api.auth;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp.MerchantInfoResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.manage.MerchantManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cx
 * @date 2019/8/15
 */


@RestController
@RequestMapping(value = "${frontPath}/pds/api/manage")
public class ManageApiCtrl {

    @Autowired
    @Lazy
    private MerchantManageService merchantManageService;

    /**
     * 根据租户id 查询当前平台商家信息
     *
     * @param tenantId
     * @return
     */
    @RequestMapping("/merchantInfo/{tenantId}")
    public BaseResp getMerchantInfoResp(@PathVariable("tenantId") Integer tenantId) {
        BaseResp res = BaseResp.newError();
        MerchantInfoResp merchantInfo = merchantManageService.getMerchantInfoResp(tenantId);
        if (null == merchantInfo) {
            res.setMsg("此商家不存在");
            return res;
        }
        merchantInfo.setPdsUrl("/pds");
        merchantInfo.setSelfPm(false);
        merchantInfo.setPds(false);
        res.success();
        res.setExt(merchantInfo);
        return res;
    }
}
