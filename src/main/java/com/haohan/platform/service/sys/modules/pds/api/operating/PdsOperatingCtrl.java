package com.haohan.platform.service.sys.modules.pds.api.operating;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.operate.PdsAfterSaleOrderReq;
import com.haohan.platform.service.sys.modules.pds.core.operation.IPdsOperationService;
import com.haohan.platform.service.sys.modules.pds.core.pss.IPdsGoodsStorageOpService;
import com.haohan.platform.service.sys.modules.pds.entity.req.PdsOfferOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 运营小程序接口
 *
 * @author shenyu
 * @create 2018/10/26
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/operating")
public class PdsOperatingCtrl extends BaseController {
    @Resource
    private IPdsOperationService pdsOperationService;
    @Resource
    private IPdsGoodsStorageOpService goodsStorageOpService;


    //获取已成交的供应商列表
    @RequestMapping(value = "freight/supList")
    @ResponseBody
    public BaseResp fetchSupplierList(PdsOfferOrderReq pdsOfferOrderReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        Date deliveryDate = pdsOfferOrderReq.getDeliveryDate();
        if (null == deliveryDate) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        baseResp = pdsOperationService.findDealSupList(pdsOfferOrderReq);
        return baseResp;
    }

    //查询待揽货数量
    @RequestMapping(value = "freight/waitDealNum")
    @ResponseBody
    public BaseResp waitDeal(PdsOfferOrderReq pdsOfferOrderReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        Date deliveryDate = pdsOfferOrderReq.getDeliveryDate();
        String pmId = pdsOfferOrderReq.getPmId();
        if (null == deliveryDate) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        baseResp = pdsOperationService.countDealNum(deliveryDate, pmId);
        return baseResp;
    }

    //供应商供货商品列表
    @RequestMapping(value = "freight/goodsList")
    @ResponseBody
    public BaseResp fetchGoodsList(PdsOfferOrderReq pdsOfferOrderReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        String supplierId = pdsOfferOrderReq.getSupplierId();
        Date deliveryDate = pdsOfferOrderReq.getDeliveryDate();
        String buySeq = pdsOfferOrderReq.getBuySeq();
        String shipStatus = pdsOfferOrderReq.getShipStatus();

        if (null == deliveryDate || StringUtils.isEmpty(supplierId)) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }
        baseResp = pdsOperationService.findSupGoodsList(deliveryDate, buySeq, supplierId, shipStatus);
        return baseResp;
    }


    //揽货确认
    @RequestMapping(value = "freight/confirm")
    @ResponseBody
    public BaseResp freightConfirm(String uid, String offerOrderId, String goodsId, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        if (StringUtils.isAnyEmpty(uid, offerOrderId, goodsId)) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        baseResp = pdsOperationService.freightConfirm(uid, offerOrderId, goodsId);
        return baseResp;
    }

    //售后
    @RequestMapping(value = "afterSale")
    @ResponseBody
    public BaseResp afterSale(PdsAfterSaleOrderReq pdsAfterSaleOrderReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        String offerOrderId = pdsAfterSaleOrderReq.getOfferOrderId();
        String goodsId = pdsAfterSaleOrderReq.getGoodsId();
        String category = pdsAfterSaleOrderReq.getServiceCategory();
        String[] photos = pdsAfterSaleOrderReq.getPhotos();
        String note = pdsAfterSaleOrderReq.getNote();
        String pmId = pdsAfterSaleOrderReq.getPmId();
        if (StringUtils.isAnyEmpty(offerOrderId, goodsId, category, note, pmId) || ArrayUtils.isEmpty(photos)) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        baseResp = pdsOperationService.afterSale(pdsAfterSaleOrderReq);
        return baseResp;
    }

    //分拣列表(按商品分组 || 按采购商分组)
    @RequestMapping(value = "sortout/fetchList")
    @ResponseBody
    public BaseResp fetchSortOutList(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();


        return baseResp;
    }

    //分拣确认
    @RequestMapping(value = "sortout/confirm")
    @ResponseBody
    public BaseResp sortoutConfirm(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();


        return baseResp;
    }

    //打印订单
    @RequestMapping(value = "sortout/printOrder")
    @ResponseBody
    public BaseResp printOrder(HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();


        return baseResp;
    }

    //入库
    @RequestMapping(value = "enterStock")
    @ResponseBody
    public BaseResp enterStockApply(String[] offerOrderIds, String merchantId, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (ArrayUtils.isEmpty(offerOrderIds) || StringUtils.isEmpty(merchantId)) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }
        try {
            baseResp = goodsStorageOpService.freightGoodsEnterStock(offerOrderIds, merchantId);
        } catch (StorageOperationException e) {
            e.printStackTrace();
            baseResp.setMsg(e.getMessage());
        } finally {
            return baseResp;
        }
    }

}
