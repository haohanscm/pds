package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 售后订单controller
 * @author shenyu
 * @create 2018/9/26
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/order/afterSaleOrder")
public class MercAfterSaleOrderCtrl extends BaseController {

    //查询列表
    @RequestMapping(value = "fetchList")
    @ResponseBody
    public BaseResp fetchList() {
        BaseResp baseResp = BaseResp.newError();


        return baseResp;
    }

    //订单操作  同意退款/拒绝退款


}
