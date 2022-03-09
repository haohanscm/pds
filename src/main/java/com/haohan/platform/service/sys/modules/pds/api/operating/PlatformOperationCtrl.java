package com.haohan.platform.service.sys.modules.pds.api.operating;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.core.delivery.IPdsDeliveryService;
import com.haohan.platform.service.sys.modules.pds.core.operation.IPlatformOperationService;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 运营平台交易流程操作  接口
 *
 * @author dy
 * @create 2018/11/7
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/process")
public class PlatformOperationCtrl extends BaseController {

    @Autowired
    private IPlatformOperationService platformOperationService;
    @Autowired
    private IPdsDeliveryService pdsDeliveryService;


    // 采购单状态变更为待确认
    @RequestMapping(value = "buyOrder/statusWait")
    @ResponseBody
    public BaseResp updateBuyOrderStatus(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();

        Date deliveryTime = buyOrder.getDeliveryTime();
        String buySeq = buyOrder.getBuySeq();

        if (null == deliveryTime || StringUtils.isEmpty(buySeq)) {
            baseResp.setMsg("缺少参数deliveryTime/buySeq");
            return baseResp;
        }
        baseResp = platformOperationService.updateBuyOrderStatus(buySeq, deliveryTime);
        return baseResp;
    }


    // 货物送达
    @RequestMapping(value = "tradeOrder/goodsArrived")
    @ResponseBody
    public BaseResp goodsArrived(DeliveryFlow deliveryFlow) {
        BaseResp baseResp = BaseResp.newError();

        if (StringUtils.isEmpty(deliveryFlow.getDeliveryId())) {
            baseResp.setMsg("缺少参数deliveryId");
            return baseResp;
        }
        baseResp = pdsDeliveryService.goodsArrived(deliveryFlow);
        return baseResp;
    }

}
