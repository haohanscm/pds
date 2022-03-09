package com.haohan.platform.service.sys.modules.pds.core.supplier.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.service.BaseService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierOrderService;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.operation.PdsTradeProcessService;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.SummaryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SupplierOrderServiceImpl extends BaseService implements ISupplierOrderService {

    @Autowired
    @Lazy(true)
    private SummaryOrderService summaryOrderService;
    @Autowired
    @Lazy(true)
    private OfferOrderService offerOrderService;
    @Autowired
    @Lazy(true)
    private PdsTradeProcessService pdsTradeProcessService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService pdsSupplierService;


//    /**
//     * 报价单初始化 单个供应商
//     * （根据采购单汇总 生成对应报价单）
//     *
//     * @param supplierId
//     * @param buySeq
//     * @param deliveryTime
//     * @return
//     */
//    @Override
//    public BaseResp createOfferOrder(String supplierId, String buySeq, Date deliveryTime) {
//        BaseResp baseResp = BaseResp.newError();
//        // 查询 采购单汇总
//        SummaryOrder summaryOrder = new SummaryOrder();
//        summaryOrder.setBuySeq(buySeq);
//        summaryOrder.setDeliveryTime(deliveryTime);
//        List<SummaryOrder> summaryOrderList = summaryOrderService.findList(summaryOrder);
//        // 采购单汇总需已生成
//        if (!Collections3.isEmpty(summaryOrderList)) {
//            // 查询已存在报价单 时间/批次  所有供应商
//            OfferOrderResp offerOrderResp = new OfferOrderResp();
//            offerOrderResp.setBuySeq(buySeq);
//            offerOrderResp.setDeliveryTime(deliveryTime);
//            // TODO sql未完善
//            List<OfferOrder> existOfferList = offerOrderService.findListBySeq(offerOrderResp);
//            // 采购单汇总id(询价单号):报价单
//            Map<String, OfferOrder> existOfferMap = new HashMap<>();
//            for (OfferOrder offer : existOfferList) {
//                if (StringUtils.isNotEmpty(offer.getAskOrderId())) {
//                    existOfferMap.put(offer.getAskOrderId(), offer);
//                }
//            }
//            // 新增/修改 报价单 supplierId/askOrderId/askPriceTime/buyNum/status/offerType
//            OfferOrder offerOrder;
//            for (SummaryOrder s : summaryOrderList) {
//                if (existOfferMap.containsKey(s.getSummaryOrderId())) {
//                    offerOrder = existOfferMap.get(s.getSummaryOrderId());
//                } else {
//                    offerOrder = new OfferOrder();
//                }
//                offerOrder.setSupplierId(supplierId);
//                offerOrder.setAskOrderId(s.getSummaryOrderId());
//                offerOrder.setOfferType(IPdsConstant.OfferType.platform.getCode());
//                offerOrder.setBuyNum(s.getNeedBuyNum());
//                offerOrder.setAskPriceTime(s.getBuyTime());
//                offerOrder.setStatus(IPdsConstant.OfferOrderStatus.wait.getCode());
//                offerOrder.setStatus(IPdsConstant.OfferShipStatus.disabled.getCode());
//                offerOrderService.save(offerOrder);
//            }
//            baseResp.success();
//        } else {
//            baseResp.setMsg("找不到采购单汇总记录");
//        }
//        logger.debug("--createOfferOrder---\n{}--{}--{}\n{}", supplierId, buySeq, deliveryTime, baseResp);
//        return baseResp;
//    }

//    /**
//     * 报价单列表查询 单个供应商
//     *
//     * @return 包含采购单汇总 的商品信息
//     */
//    @Override
//    public BaseResp queryOfferOrderList(ReqOfferOrders reqOfferOrders) {
//        BaseResp baseResp = BaseResp.newError();
//        OfferOrderResp offerOrder = new OfferOrderResp();
//        offerOrder.setSupplierId(reqOfferOrders.getSupplierId());
//        offerOrder.setBuySeq(reqOfferOrders.getBuySeq());
//        offerOrder.setDeliveryTime(reqOfferOrders.getDeliveryTime());
//        // TODO sql未完善
//        List<OfferOrderResp> list = offerOrderService.findListWithGoods(offerOrder);
//        if (Collections3.isEmpty(list)) {
//            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
//        } else {
//            baseResp.success();
//            baseResp.setExt(new ArrayList<>(list));
//        }
//        logger.debug("--queryOfferOrderList---\n{}--{}--{}\n{}", reqOfferOrders.getSupplierId(),
//                reqOfferOrders.getDeliveryTime(), reqOfferOrders.getBuySeq(), baseResp.getMsg());
//        return baseResp;
//    }

//    /**
//     * 报价单提交（状态修改） 单个供应商
//     *
//     * @param reqOfferOrders
//     * @return
//     */
//    @Override
//    public BaseResp supplierOfferOrders(ReqOfferOrders reqOfferOrders) {
//        BaseResp baseResp = BaseResp.newError();
//        String supplierId = reqOfferOrders.getSupplierId();
//        List<OfferOrder> offerOrderList = reqOfferOrders.getOfferOrderList();
//        PdsSupplier supplier = pdsSupplierService.get(supplierId);
//        if (null == supplier) {
//            baseResp.setMsg("供应商id有误");
//            return baseResp;
//        }
//        // 交易阶段
////        PdsTradeProcess process = pdsTradeProcessService.fetchProcess(buySeq, deliveryTime);
//        // 判断是否可提交报价单  状态为 供应商报价时可提交
////        if(null!= process && StringUtils.equals(process.getStatus(),IPdsConstant.TradeProcessStatus.offer.getCode())){
//        if (!Collections3.isEmpty(offerOrderList)) {
//            // 该供应商的报价单 查询
//            OfferOrder offerOrder = new OfferOrder();
//            offerOrder.setSupplierId(supplierId);
//            offerOrder.setStatus(IPdsConstant.OfferOrderStatus.wait.getCode());
//            List<OfferOrder> existOrderList = offerOrderService.findListBySeq(offerOrder);
//            int size = existOrderList.size() * 4 / 3 + 1;
//            size = (size > 8) ? size : 8;
//            Map<String, OfferOrder> existOrderMap = new HashMap<>(size);
//            for (OfferOrder offer : existOrderList) {
//                existOrderMap.put(offer.getAskOrderId(), offer);
//            }
//            //
//            Integer minSaleNum;
//            BigDecimal supplyNum;
//            for (OfferOrder offer : offerOrderList) {
//                // 剔除无效项
//                if (StringUtils.isEmpty(offer.getAskOrderId()) || null == offer.getSupplyPrice()) {
//                    continue;
//                }
//                // 报价单 新增或修改
//                if (existOrderMap.containsKey(offer.getAskOrderId())) {
//                    offerOrder = existOrderMap.get(offer.getAskOrderId());
//                    minSaleNum = offer.getMinSaleNum();
//                    minSaleNum = (null == minSaleNum || minSaleNum <= 0) ? 0 : minSaleNum;
//                    offerOrder.setMinSaleNum(minSaleNum);
//                    offerOrder.setSupplyPrice(offer.getSupplyPrice());
//                    offerOrder.setOtherAmount(offer.getOtherAmount());
//                } else {
//                    offerOrder = offer;
//                    offerOrder.setId(null);
//                    offerOrder.setOfferOrderId(null);
//                }
//                // 默认供应数量
//                supplyNum = offerOrder.getSupplyNum();
//                if (null == supplyNum || BigDecimal.ZERO.compareTo(supplyNum) >= 0) {
//                    supplyNum = new BigDecimal(9999);
//                }
//                // 保存报价单
//                offerOrder.setSupplierId(supplierId);
//                offerOrder.setSupplierName(supplier.getSupplierName());
//                offerOrder.setSupplyNum(supplyNum);
//                offerOrder.setOfferUid(supplier.getPassportId());
//                offerOrder.setOfferType(IPdsConstant.OfferType.platform.getCode());
//                offerOrder.setStatus(IPdsConstant.OfferOrderStatus.submit.getCode());
//                offerOrder.setShipStatus(IPdsConstant.OfferShipStatus.disabled.getCode());
//                offerOrder.setOfferPriceTime(new Date());
//                // 金额设置
//                BigDecimal supplyPrice = offerOrder.getSupplyPrice();
//                BigDecimal buyNum = offerOrder.getBuyNum();
//                if(null != supplyPrice && null != buyNum){
//                    BigDecimal other = offerOrder.getOtherAmount();
//                    other = (null == other) ? BigDecimal.ZERO : other;
//                    BigDecimal total = supplyPrice.multiply(buyNum).add(other);
//                    offerOrder.setTotalAmount(total);
//                }
//                offerOrderService.save(offerOrder);
//            }
//            baseResp.success();
//        } else {
//            baseResp.setMsg("未提交报价单");
//        }
////        }else{
////            IPdsConstant.TradeProcessStatus status = IPdsConstant.TradeProcessStatus.getTypeByCode(process.getStatus());
////            String processDesc = "还未开始";
////            if(null != status){
////                processDesc = status.getDesc();
////            }
////            baseResp.setMsg("当前阶段为"+processDesc+",不能提交报价单");
////        }
//        logger.debug("--supplierOfferOrders---\n{}\n{}", supplierId, baseResp);
//        return baseResp;
//    }

    @Override
    public BaseResp queryWaitOfferList(String supplierId, String buySeq, Date deliveryTime) {
        BaseResp baseResp = BaseResp.newError();
        // 关联查询 供应商 已关联商品列表  和采购汇总单列表 返回采购汇总单信息
        SummaryOrder order = new SummaryOrder();
        order.setSupplierId(supplierId);
        order.setBuySeq(buySeq);
        order.setDeliveryTime(deliveryTime);
        List<SummaryOrder> list = summaryOrderService.queryWaitOfferList(order);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        ApiRespPage respPage = new ApiRespPage();
        int count = list.size();
        respPage.setPageNo(1);
        respPage.setPageSize(count);
        respPage.setCount(count);
        respPage.setTotalPage(1);
        respPage.setList(list);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }
}
