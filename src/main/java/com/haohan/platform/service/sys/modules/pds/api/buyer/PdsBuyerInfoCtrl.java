package com.haohan.platform.service.sys.modules.pds.api.buyer;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 采购商 信息 货款
 * 货款查询
 * Created by dy on 2018/10/29.
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/buyer/info")
public class PdsBuyerInfoCtrl extends BaseController {

    @Autowired
    private IBuyerPaymentService buyerPaymentService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService buyerService;

    /**
     * 采购商 货款查询
     *
     * @return
     */
    @RequestMapping(value = "queryPaymentList")
    @ResponseBody
    public BaseResp queryPaymentList(BuyerPayment buyerPayment, Page page) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isEmpty(buyerPayment.getBuyerId())) {
            baseResp.setMsg("缺少参数buyerId");
            return baseResp;
        }
        // 分页
        int pageNo = page.getPageNo() <= 1 ? 1 : page.getPageNo();
        int pageSize = page.getPageSize() <= 10 ? 10 : page.getPageSize();
        page = new Page<>(pageNo, pageSize);
        // 请求参数
        BuyerPayment payment = new BuyerPayment();
        payment.setBuyerId(buyerPayment.getBuyerId());
        payment.setBuyId(buyerPayment.getBuyId());
        payment.setStatus(buyerPayment.getStatus());
        payment.setBeginBuyDate(buyerPayment.getBeginBuyDate());
        payment.setEndBuyDate(buyerPayment.getEndBuyDate());
        baseResp = buyerPaymentService.queryPaymentList(payment, page);
        return baseResp;
    }

    /**
     * 采购商 货款合计查询  暂未使用
     *
     * @return
     */
    @RequestMapping(value = "totalPayment")
    @ResponseBody
    public BaseResp totalPayment(BuyerPayment buyerPayment) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isEmpty(buyerPayment.getBuyerId())) {
            baseResp.setMsg("缺少参数buyerId");
            return baseResp;
        }
        // 请求参数
        BuyerPayment payment = new BuyerPayment();
        payment.setBuyerId(buyerPayment.getBuyerId());
        payment.setBeginBuyDate(buyerPayment.getBeginBuyDate());
        payment.setEndBuyDate(buyerPayment.getEndBuyDate());
        baseResp = buyerPaymentService.totalPayment(payment);
        return baseResp;
    }

    /**
     * 采购商 采购单生成货款记录
     *
     * @return
     */
    @RequestMapping(value = "paymentRecord")
    @ResponseBody
    public BaseResp paymentRecord(BuyOrder buyOrder) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isAnyEmpty(buyOrder.getBuyerId(), buyOrder.getBuyId())) {
            baseResp.setMsg("缺少参数buyerId/buyId");
            return baseResp;
        }
        // 请求参数
        baseResp = buyerPaymentService.paymentRecord(buyOrder);
        return baseResp;
    }

    /**
     * 采购商 列表查询
     * (目前是 类型为老板/运营 可查询)
     * 运营查询同一平台商家下采购商
     * 老板查询同一商家下采购商
     *
     * @return
     */
    @RequestMapping(value = "buyerList")
    @ResponseBody
    public BaseResp buyerList(PdsBuyerReq buyerReq) {

        BaseResp baseResp = BaseResp.newError();
        String buyerId = buyerReq.getBuyerId();
        String pmId = buyerReq.getPmId();
        //验证参数
        if (StringUtils.isAnyEmpty(buyerId, pmId)) {
            baseResp.setMsg("缺少参数buyerId/pmId");
            return baseResp;
        }
        // 验证采购商
        PdsBuyer buyer = buyerService.get(buyerId);
        if (null == buyer || !StringUtils.equals(buyer.getPmId(), pmId)) {
            baseResp.setMsg("采购商有误");
            return baseResp;
        }
        String buyType = buyer.getBuyerType();
        List<PdsBuyer> buyerList;
        PdsBuyer query = new PdsBuyer();
        query.setPmId(pmId);
        if (StringUtils.equals(buyType, IPdsConstant.BuyerType.boss.getCode())) {
            // 老板能查 商家下 所有采购商
            query.setMerchantId(buyer.getMerchantId());
        } else if (StringUtils.equals(buyType, IPdsConstant.BuyerType.operator.getCode())) {
            // 运营 能查 平台下 所有采购商
        } else {
            baseResp.setMsg("该采购商无权限");
            return baseResp;
        }
        buyerList = buyerService.findUsableList(query);
        if (Collections3.isEmpty(buyerList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            baseResp.setExt(new ArrayList<>(buyerList));
        }
        return baseResp;
    }

    @RequestMapping(value = "merchantList")
    @ResponseBody
    public BaseResp merchantList(@Validated PdsBuyerReq buyerReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        List<PdsBuyer> merchantList = buyerService.findMerchantList(buyerReq.getPmId());
        if (Collections3.isEmpty(merchantList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            HashMap<String, Object> resultMap = new HashMap<>(8);
            resultMap.put("count", merchantList.size());
            resultMap.put("merchantList", merchantList);
            baseResp.setExt(resultMap);
        }
        return baseResp;
    }

}