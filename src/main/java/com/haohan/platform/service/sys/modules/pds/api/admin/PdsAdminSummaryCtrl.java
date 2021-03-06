package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsAdminSumBuyOrder;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsOfferOrderSaveParams;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminSummaryService;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.SummaryOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/10/28
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/summary")
public class PdsAdminSummaryCtrl extends BaseController {
    @Resource
    private SummaryOrderService summaryOrderService;
    @Resource
    private IPdsAdminSummaryService pdsAdminSummaryService;
    @Autowired
    private IPdsSummaryService pdsSummaryService;
    @Autowired
    private OfferOrderService offerOrderService;

    //?????????????????????
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp fetchSummaryOrderList(PdsAdminSumBuyOrder pdsAdminSumBuyOrder, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        Integer pageNo = pdsAdminSumBuyOrder.getPageNo();
        Integer pageSize = pdsAdminSumBuyOrder.getPageSize();
        String pmId = pdsAdminSumBuyOrder.getPmId();

        if (StringUtils.isEmpty(pmId)) {
            baseResp.setMsg("missing param pmId");
            return baseResp;
        }
        Date deliveryDate = pdsAdminSumBuyOrder.getDeliveryDate();
        if (null == pdsAdminSumBuyOrder || null == deliveryDate) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        baseResp = pdsAdminSummaryService.findSummaryOrderPage(reqPage, pdsAdminSumBuyOrder);
        return baseResp;
    }

//    //??????????????????    ?????????
//    @RequestMapping(value = "buy/detail")
//    @ResponseBody
//    public BaseResp fetchBuyOrderDetail(String summaryOrderId,String pmId,HttpServletRequest request){
//        BaseResp baseResp = BaseResp.newError();
//        if (StringUtils.isBlank(summaryOrderId)){
//            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
//            return baseResp;
//        }
//
//        baseResp = pdsAdminSummaryService.findBuyDetailBySumId(summaryOrderId,pmId);
//        return baseResp;
//    }

    /**
     * ?????????????????????
     * ??????detailId ??????????????????
     * ??????????????????????????????????????????
     *
     * @param buyDetailBatchReq
     * @param request
     * @return
     */
    @RequestMapping(value = "buy/edit")
    @ResponseBody
    public BaseResp editBuyOrderDetail(@RequestBody PdsApiSumBuyDetailBatchReq buyDetailBatchReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();

        if (null == buyDetailBatchReq || CollectionUtils.isEmpty(buyDetailBatchReq.getDetailList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        baseResp = pdsAdminSummaryService.editBuyOrderDetailBatch(buyDetailBatchReq);
        return baseResp;
    }

    /**
     * ???????????????
     * ??????????????? ????????????????????????
     * ??????????????????????????????????????????
     *
     * @param offerSaveBatchReq
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "offer/save")
    @ResponseBody
    public BaseResp saveOfferOrder(@RequestBody PdsApiSumOfferSaveBatchReq offerSaveBatchReq) throws Exception {
        BaseResp baseResp = BaseResp.newError();

        if (null == offerSaveBatchReq || CollectionUtils.isEmpty(offerSaveBatchReq.getOfferOrderList())) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        List<String> ids = new ArrayList<>();
        for (PdsOfferOrderSaveParams offerOrder : offerSaveBatchReq.getOfferOrderList()) {
            SummaryOrder summaryOrder = summaryOrderService.fetchBySummaryOrderId(offerOrder.getAskOrderId());
            String confirmCode = IPdsConstant.SummaryOrderStatus.confirm.getCode();
            String sucCode = IPdsConstant.SummaryOrderStatus.deal.getCode();
            String cancelCode = IPdsConstant.SummaryOrderStatus.cancel.getCode();
            String summaryStatus = summaryOrder.getStatus();
            if (null == summaryOrder || sucCode.equals(summaryStatus)
                    || cancelCode.equals(summaryStatus) || confirmCode.equals(summaryStatus)) {
                baseResp.setMsg("???????????????????????????");
                return baseResp;
            }
            baseResp = pdsAdminSummaryService.saveOfferOrder(summaryOrder, offerOrder);
            if (!baseResp.isSuccess()) {
                return baseResp;
            }
            ids.add(offerOrder.getOfferId());
        }
        if (baseResp.isSuccess()) {
            baseResp.setExt(ids.stream().collect(Collectors.toCollection(ArrayList::new)));
        }
        return baseResp;
    }

    //???????????????
    @RequestMapping(value = "summaryOrder/edit")
    @ResponseBody
    public BaseResp editSumOrder(@RequestBody PdsApiSumOrderBatchReq apiSumOrderBatchReq) {
        BaseResp baseResp = BaseResp.newError();

        if (null == apiSumOrderBatchReq || CollectionUtils.isEmpty(apiSumOrderBatchReq.getSummaryOrderList())) {
            baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            return baseResp;
        }

        for (SummaryOrder summaryOrder : apiSumOrderBatchReq.getSummaryOrderList()) {
            String sumOrderId = summaryOrder.getSummaryOrderId();
            if (StringUtils.isEmpty(sumOrderId)) {
                baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
                return baseResp;
            }
            BigDecimal platformPrice = summaryOrder.getPlatformPrice();
            BigDecimal buyAvgPrice = summaryOrder.getBuyAvgPrice();
            baseResp = pdsAdminSummaryService.editSumOrder(sumOrderId, platformPrice, buyAvgPrice);
            if (!baseResp.isSuccess()) {
                return baseResp;
            }
        }
        return baseResp;
    }

    //???????????????
    @RequestMapping(value = "offer/delete")
    @ResponseBody
    public BaseResp deleteOfferOrder(@Validated(DeleteGroup.class) PdsOfferOrderApiReq apiReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        baseResp = pdsAdminSummaryService.delOfferOrder(apiReq.getOfferOrderId());
        return baseResp;
    }

    //?????????????????????????????????
    @RequestMapping(value = "convert")
    @ResponseBody
    public BaseResp convert(@Validated(DefaultGroup.class) PdsDataResetApiReq dateSeqReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        OfferOrder offerOrder = new OfferOrder();
        offerOrder.setPrepareDate(dateSeqReq.getDeliveryDate());
        offerOrder.setBuySeq(dateSeqReq.getBuySeq());
        offerOrder.setPmId(dateSeqReq.getPmId());
        offerOrder.setStatus(IPdsConstant.OfferOrderStatus.success.getCode());
        List<OfferOrder> offerOrderList = offerOrderService.findList(offerOrder);
        if (CollectionUtils.isEmpty(offerOrderList)) {
            baseResp.setMsg("???????????????????????????");
            return baseResp;
        }

        try {
            baseResp = pdsSummaryService.convert(offerOrderList, dateSeqReq.getPmId());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            baseResp.setMsg(e.getMessage());
        } finally {
            return baseResp;
        }
    }


}
