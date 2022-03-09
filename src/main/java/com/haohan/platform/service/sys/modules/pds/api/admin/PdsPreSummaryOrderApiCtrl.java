package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDateSeqApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsPreSumOrderApiResp;
import com.haohan.platform.service.sys.modules.pds.core.admin.impl.AdminSummaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 预汇总单接口
 *
 * @author shenyu
 * @create 2019/4/9
 */
@RestController
@RequestMapping(value = "${frontPath}/pds/api/admin/preSummaryOrder")
public class PdsPreSummaryOrderApiCtrl {
    @Autowired
    private AdminSummaryServiceImpl adminSummaryService;

    @RequestMapping(method = RequestMethod.GET)
    public BaseResp preSummaryOrder(@RequestBody PdsDateSeqApiReq apiReq) {
        BaseResp baseResp = BaseResp.newSuccess();
        Date reqDate = Optional.ofNullable(apiReq).map(req -> req.getDeliveryDate()).orElse(new Date());
        ArrayList<PdsPreSumOrderApiResp> respData = adminSummaryService.preSummaryOrderList(reqDate).stream().collect(Collectors.toCollection(ArrayList::new));
        baseResp.setExt(respData);
        return baseResp;
    }


}
