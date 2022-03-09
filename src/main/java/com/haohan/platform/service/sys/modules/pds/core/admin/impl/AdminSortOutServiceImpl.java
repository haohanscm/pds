package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiTradeSortProcessResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminSortOutService;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/11/1
 */
@Service
public class AdminSortOutServiceImpl implements IPdsAdminSortOutService {
    @Resource
    private TradeOrderService tradeOrderService;

    @Override
    public BaseResp findList(TradeOrder tradeOrder, Page reqPage) {
        BaseResp baseResp = BaseResp.newError();

        String opStatus = tradeOrder.getOpStatus();
        if (StringUtils.isEmpty(opStatus)) {
            String waitTakeCode = IPdsConstant.OperatorViewStatus.waitSortOut.getCode();
            String sortedCode = IPdsConstant.OperatorViewStatus.sorted.getCode();
            String[] statusArry = new String[]{waitTakeCode, sortedCode};
            tradeOrder.setOpStatusArry(statusArry);
        }
        tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
        tradeOrder.setDeliveryStatus(IPdsConstant.DeliveryStatus.wait_delivery.getCode());
        reqPage.setOrderBy("goodsName");
        tradeOrderService.findPage(reqPage, tradeOrder);
        if (CollectionUtils.isEmpty(reqPage.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        BaseList<TradeOrder> baseList = new BaseList<>();
        baseList.setTotalRows(new Long(reqPage.getCount()).intValue());
        baseList.setCurPage(reqPage.getPageNo());
        baseList.setTotalPage(reqPage.getTotalPage());
        baseList.setList(reqPage.getList());
        baseList.setPageSize(reqPage.getPageSize());
        return baseList;
    }

    @Override
    public BaseResp editSortNum(String tradeOrderId, BigDecimal sortOutNum) {
        BaseResp baseResp = BaseResp.newError();
        TradeOrder tradeOrder = tradeOrderService.fetchByTradeOrderId(tradeOrderId);
        tradeOrder.setSortOutNum(sortOutNum);
        tradeOrderService.save(tradeOrder);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp confirm(String tradeOrderId, BigDecimal sortOutNum) {
        BaseResp baseResp = BaseResp.newError();

        TradeOrder tradeOrder = tradeOrderService.fetchByTradeOrderId(tradeOrderId);
        tradeOrder.setSortOutNum(sortOutNum);
        tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
        tradeOrderService.save(tradeOrder);

        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp allProcess(Date deliveryDate, String buySeq, String pmId) {
        BaseResp baseResp = BaseResp.newError();

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setDeliveryTime(deliveryDate);
        tradeOrder.setBuySeq(buySeq);
        tradeOrder.setPmId(pmId);
        int all = tradeOrderService.countProcess(tradeOrder);
        tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
        int finish = tradeOrderService.countProcess(tradeOrder);
        DecimalFormat df = new DecimalFormat("0.0000");
        double result = (double) finish / (double) all;
        baseResp.success();
        baseResp.setExt(df.format(result));
        return baseResp;
    }

    //分拣进度
    @Override
    public BaseResp buyOrderProcess(Date deliveryDate, String buySeq, String pmId) {
        BaseResp baseResp = BaseResp.newError();

        String waitCode = IPdsConstant.OperatorViewStatus.waitSortOut.getCode();
        String sortedCode = IPdsConstant.OperatorViewStatus.sorted.getCode();
        String[] opStatusArry = new String[]{waitCode, sortedCode};

        List<PdsApiTradeSortProcessResp> processRespList = tradeOrderService.goodsSortProcessList(deliveryDate, buySeq, opStatusArry, pmId);
        for (PdsApiTradeSortProcessResp processResp : processRespList) {
            String buyId = processResp.getBuyId();
            Integer sortedNum = tradeOrderService.countGoodsProcess(buyId, buySeq, new String[]{sortedCode}, pmId);
            processResp.setSortedNum(sortedNum);
            Integer notNum = tradeOrderService.countGoodsProcess(buyId, buySeq, new String[]{waitCode}, pmId);
            processResp.setNotNum(notNum);
            Integer totalNum = tradeOrderService.countGoodsProcess(buyId, buySeq, opStatusArry, pmId);
            processResp.setTotalNum(totalNum);
        }

        baseResp.success();
        baseResp.setExt(processRespList.stream().collect(Collectors.toCollection(ArrayList::new)));
        return baseResp;
    }

    @Override
    public BaseResp fastSortOut(Date deliveryDate, String buySeq, String pmId) {
        BaseResp baseResp = BaseResp.newError();

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setDeliveryTime(deliveryDate);
        tradeOrder.setBuySeq(buySeq);
        tradeOrder.setPmId(pmId);
        tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.waitSortOut.getCode());
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
        if (CollectionUtils.isEmpty(tradeOrderList)) {
            baseResp.setMsg("未找到待分拣的订单");
            return baseResp;
        }
        for (TradeOrder order : tradeOrderList) {
            order.setSortOutNum(order.getBuyNum());
            order.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
            tradeOrderService.save(order);
        }
        baseResp.success();
        return baseResp;
    }
}
