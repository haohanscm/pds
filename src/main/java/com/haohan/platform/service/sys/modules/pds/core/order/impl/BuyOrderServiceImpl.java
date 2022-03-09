package com.haohan.platform.service.sys.modules.pds.core.order.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.order.IBuyOrderService;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BuyOrderServiceImpl implements IBuyOrderService, IPdsConstant {

    @Autowired
    private BuyOrderDetailService buyOrderDetailService;


    @Override
    public BaseResp updateStatusWait(String buySeq, Date deliveryTime) {
        BaseResp baseResp = BaseResp.newError();
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setBuySeq(buySeq);
        buyOrderDetail.setDeliveryDate(deliveryTime);
        buyOrderDetail.setStatus(BuyOrderStatus.submit.getCode());
        buyOrderDetail.setFinalStatus(BuyOrderStatus.wait.getCode());
        int row = buyOrderDetailService.updateStatusBatch(buyOrderDetail);
        if (row < 1) {
            baseResp.setMsg("找不到对应信息");
        } else {
            baseResp.success();
        }
        return baseResp;
    }
}
