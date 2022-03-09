package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IPayQueryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.*;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IMerchantApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.CommonApiService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderQuery;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 支付API接口
 * Created by zgw on 2017/12/10.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/pay")
public class PayQueryApiCtrl extends BaseController {

    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @Resource
    private IMerchantApiService imerchantApiService;

    @Autowired
    private CommonApiService commonApiService;

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;

    @Autowired
    private MerchantPayInfoService merchantPayInfoService;

    @Autowired
    private ShopService shopService;


    /**
     * 第一次查询支付结果
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "query")
    public String query(HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            //JSON转Bean
            ReqPayQuery payQuery = commonApiService.fetchByHttpParams(request, ReqPayQuery.class, resp);

            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == payQuery) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }
            PartnerApp partnerApp = payQuery.getPartnerApp();
            secret = partnerApp.getAppSecret();

            //验证参数
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            String orderId = payQuery.getOrderId();
            String partnerId = payQuery.getPartnerId();

            requiredParams.put("orderId", orderId);
            requiredParams.put("partnerId", partnerId);

            //错误处理
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //验证订单是否存在
            OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(orderId);
            if (null == payRecord || StringUtils.isEmpty(payRecord.getPartnerId())) {
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }
            //验证商户及账户信息
            resp = commonApiService.validMerchantInfo(partnerId, resp, payRecord);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //处理业务
            OrderQuery query = new OrderQuery();

            BeanUtils.copyProperties(payQuery, query);
            query.setThirdOrdNo(payQuery.getTransId());
            query.setRequestId(payQuery.getReqId());
            query.setMerchantId(payRecord.getMerchantId());

            //调用查询接口
            resp = imerchantApiService.orderQuery(query);

            if (resp.isSuccess()) {
                respBody = (String) resp.getExt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }
    }


    /**
     * 查询营业额接口,可按昨日/今日/本周查询数据 , 带支付方式
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "querySaleAmount")
    @ResponseBody
    public String querySaleAmount(HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            TSaleAmountReq tSaleAmountReq = commonApiService.fetchByHttpParams(request, TSaleAmountReq.class, resp);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == tSaleAmountReq) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }

            //参数准备
            PartnerApp partnerApp = tSaleAmountReq.getPartnerApp();
            String partnerId = tSaleAmountReq.getPartnerId();
            secret = partnerApp.getAppSecret();

            OrderPayRecord orderPayRecord = new OrderPayRecord();
            BeanUtils.copyProperties(tSaleAmountReq, orderPayRecord);
            if (StringUtils.isNotEmpty(partnerId)) {
                MerchantPayInfo merchantPayInfo = merchantPayInfoService.fetchByPartnerId(tSaleAmountReq.getPartnerId());
                if (null != merchantPayInfo) {
                    orderPayRecord.setMerchantId(merchantPayInfo.getMerchantId());
                }
            }

            //设置时间
            Date date = new Date();
            Date start = date;
            Date end = date;
            if (IPayQueryConstant.QueryPeriod.YESTERDAY.getCode().equals(tSaleAmountReq.getPeriod())) {
                //获取昨天0点和昨天12点的时间
                start = DateUtils.getStartOfYesterday(date);
                end = DateUtils.getEndOfYesterday(date);
            }

            if (IPayQueryConstant.QueryPeriod.TODAY.getCode().equals(tSaleAmountReq.getPeriod())) {
                //获取今天0点到目前的时间
                start = DateUtils.getStartOfToday(date);
                end = new Date();
            }

            if (IPayQueryConstant.QueryPeriod.THIS_WEEK.getCode().equals(tSaleAmountReq.getPeriod())) {
                //获取本周时间段
                start = DateUtils.getTimesWeekStart();
                end = DateUtils.getTimesWeekEnd();
            }

            orderPayRecord.setStartTime(start);
            orderPayRecord.setEndTime(end);
            orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());

            //查询结果
            TSaleAmountResp tSaleAmountResp = new TSaleAmountResp();

            orderPayRecord.setPayType(BankServiceType.WexinQrCode.getCode());
            BigDecimal wxMaPay = orderPayRecordService.sumSaleAmount(orderPayRecord);
            orderPayRecord.setPayType(BankServiceType.WexinAppPay.getCode());
            BigDecimal wxAppPay = orderPayRecordService.sumSaleAmount(orderPayRecord);
            orderPayRecord.setPayType(BankServiceType.WexinAuthPay.getCode());
            BigDecimal wxAuthPay = orderPayRecordService.sumSaleAmount(orderPayRecord);
            orderPayRecord.setPayType(BankServiceType.AliPayQrCode.getCode());
            BigDecimal aliMaPay = orderPayRecordService.sumSaleAmount(orderPayRecord);
            orderPayRecord.setPayType(BankServiceType.AliAuthPay.getCode());
            BigDecimal aliAuthPay = orderPayRecordService.sumSaleAmount(orderPayRecord);
            orderPayRecord.setPayType(BankServiceType.CashPay.getCode());
            BigDecimal cash = orderPayRecordService.sumSaleAmount(orderPayRecord);

            tSaleAmountResp.setWxPay(wxMaPay.add(wxAuthPay).add(wxAppPay));
            tSaleAmountResp.setAliPay(aliMaPay.add(aliAuthPay));
            tSaleAmountResp.setCashPay(cash);
            tSaleAmountResp.setBankCardPay(BigDecimal.ZERO);

            respBody = JacksonUtils.toJson(tSaleAmountResp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.putStatus(RespStatus.ERROR);
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }
    }

    /**
     * 查询销量接口,可按昨日/今日/本周查询数据 (按商品名称)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "querySaleVolume")
    @ResponseBody
    public String querySaleVolume(HttpServletRequest request) {
        BaseResp resp = new BaseResp();
        String respBody = "";
        String secret = "";

        try {
            //接收请求参数
            TSaleVolumeReq tSaleVolumeReq = commonApiService.fetchByHttpParams(request, TSaleVolumeReq.class, resp);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == tSaleVolumeReq) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }

            //参数准备
            PartnerApp partnerApp = tSaleVolumeReq.getPartnerApp();
            String partnerId = tSaleVolumeReq.getPartnerId();
            secret = partnerApp.getAppSecret();
            GoodsOrderDetail goodsOrderDetail = new GoodsOrderDetail();
            BeanUtils.copyProperties(goodsOrderDetail, tSaleVolumeReq);
            if (StringUtils.isNotEmpty(partnerId)) {
                MerchantPayInfo merchantPayInfo = merchantPayInfoService.fetchByPartnerId(tSaleVolumeReq.getPartnerId());
                if (null != merchantPayInfo) {
                    goodsOrderDetail.setMerchantId(merchantPayInfo.getMerchantId());
                }
            }
            //只统计交易成功的订单
            goodsOrderDetail.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());

            //设置时间
            Date date = new Date();
            Date start = date;
            Date end = date;
            if (IPayQueryConstant.QueryPeriod.YESTERDAY.getCode().equals(tSaleVolumeReq.getPeriod())) {
                //获取昨天0点和昨天12点的时间
                start = DateUtils.getStartOfYesterday(date);
                end = DateUtils.getEndOfYesterday(date);
            }
            if (IPayQueryConstant.QueryPeriod.TODAY.getCode().equals(tSaleVolumeReq.getPeriod())) {
                //获取今天0点到目前的时间
                start = DateUtils.getStartOfToday(date);
                end = new Date();
            }
            if (IPayQueryConstant.QueryPeriod.THIS_WEEK.getCode().equals(tSaleVolumeReq.getPeriod())) {
                //获取本周时间段
                start = DateUtils.getTimesWeekStart();
                end = DateUtils.getTimesWeekEnd();
            }
            goodsOrderDetail.setStartTime(start);
            goodsOrderDetail.setEndTime(end);

            //查询结果
            List<TSaleVolumeResp> list = goodsOrderDetailService.sumSaleVolume(goodsOrderDetail);
            respBody = JacksonUtils.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            resp.putStatus(RespStatus.ERROR);
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }
    }

    /**
     * 查询订单记录接口,分类展示,所有订单/已完成/交易中/未完成/有退款
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryOrderRecord")
    @ResponseBody
    public String queryOrderList(HttpServletRequest request, HttpServletResponse response) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            //JSON转Bean
            TOrderQueryReq payQuery = commonApiService.fetchByHttpParams(request, TOrderQueryReq.class, resp);

            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == payQuery) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }
            PartnerApp partnerApp = payQuery.getPartnerApp();
            secret = partnerApp.getAppSecret();
            GoodsOrder goodsOrder = new GoodsOrder();
            BeanUtils.copyProperties(goodsOrder, payQuery);

            Page pageReq = new Page();
            pageReq.setPageNo(payQuery.getPageNo());
            pageReq.setPageSize(payQuery.getPageSize());

            Page<GoodsOrder> page = goodsOrderService.findPage(pageReq, goodsOrder);

            if (null == page) {
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }

            BaseList<GoodsOrder> baseList = new BaseList<>();
            baseList.setList(page.getList());
            baseList.setTotalRows((int) page.getCount());
            baseList.setTotalPage(page.getTotalPage());
            baseList.setCurPage(page.getPageNo());
            baseList.setPageSize(page.getPageSize());

            respBody = baseList.toJson();

            //使用gson解决转json后时间不对的问题
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//            respBody = gson.toJson(baseList);
        } catch (Exception e) {
            e.printStackTrace();
            resp.error();
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }
    }

    /**
     * 查询订单记录详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "fetchOrderRecord")
    @ResponseBody
    public String fetchOrderRecord(HttpServletRequest request, HttpServletResponse response) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";

        try {
            TOrderQueryReq payReq = commonApiService.fetchByHttpParams(request, TOrderQueryReq.class, resp);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == payReq) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }

            PartnerApp partnerApp = payReq.getPartnerApp();
            secret = partnerApp.getAppSecret();
            OrderPayRecord orderPayRecord = new OrderPayRecord();
            BeanUtils.copyProperties(payReq, orderPayRecord);

            //验证参数
            Map<String, Object> requiredParams = new HashMap<>();
            requiredParams.put("partnerId", orderPayRecord.getPartnerId());
            requiredParams.put("orderId", orderPayRecord.getOrderId());
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

//            //验证商户
//            resp = commonApiService.validMerchantInfo(payReq.getPartnerId(), resp, orderPayRecord);
//            if (!resp.isSuccess()) {
//                return resp.toJson();
//            }

            List<OrderPayRecord> list = orderPayRecordService.findList(orderPayRecord);
            if (CollectionUtils.isEmpty(list)) {
                resp.putStatus(RespStatus.ERROR);
                resp.setMsg("订单不存在!");
                return resp.toJson();
            }

            orderPayRecord = list.get(0);

            //返回参数封装
            TOrderRecordResp orderRecordResp = new TOrderRecordResp();
            BeanUtils.copyProperties(orderPayRecord, orderRecordResp);

            //查询商品信息
            GoodsOrderDetail goodsOrderDetail = new GoodsOrderDetail();
            goodsOrderDetail.setOrderId(orderPayRecord.getOrderId());
            List<GoodsOrderDetail> goodsOrderDetailList = goodsOrderDetailService.findList(goodsOrderDetail);
            if (CollectionUtils.isNotEmpty(goodsOrderDetailList)) {
                orderRecordResp.setOrderName(orderPayRecord.getGoodsName());
                orderRecordResp.setOrderDetails(goodsOrderDetailList);
                orderRecordResp.setBuyerInfo(orderPayRecord.fromOrderInfo());
                orderRecordResp.setPayStatus(orderPayRecord.getPayStatus());
            }

            respBody = orderRecordResp.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            resp.putStatus(RespStatus.ERROR);
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }
    }


    /**
     * 查询完成订单支付记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPayRecord")
    @ResponseBody
    public String queryOrderPayRecord(HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            TOrderQueryReq queryReq = commonApiService.fetchByHttpParams(request, TOrderQueryReq.class, resp);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            PartnerApp partnerApp = queryReq.getPartnerApp();
            secret = partnerApp.getAppSecret();

            OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(queryReq.getOrderId());
            if (null != payRecord) {
                respBody = JacksonUtils.toJson(payRecord);
                resp.setExt(respBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }


    }


    /**
     * 根据APPID查询支付账户
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryAccountByAppId")
    @ResponseBody
    public String queryAccountByAppId(HttpServletRequest request) {


        BaseResp resp = BaseResp.newError();

        String respBody = "";
        String secret = "";
        try {
            //JSON转Bean
            PayAccountReq accountReq = commonApiService.fetchByHttpParams(request, PayAccountReq.class, resp);
            String appId = accountReq.getAppId();
            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == accountReq || StringUtils.isEmpty(appId)) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }
            PartnerApp partnerApp = accountReq.getPartnerApp();
            secret = partnerApp.getAppSecret();


            MerchantPayInfo payInfo =  merchantPayInfoService.fetchByAppId(appId);
            PayAccountResp accountResp = new PayAccountResp();
            if(null != payInfo) {
                accountResp.setAppId(appId);
                accountResp.setMerchantId(payInfo.getMerchantId());
                accountResp.setPartnerId(payInfo.getPartnerId());
            List<Shop> shopList =   shopService.fetchByMerchantId(payInfo.getMerchantId());
             if(CollectionUtils.isNotEmpty(shopList)) {
                 accountResp.setShopId(shopList.get(0).getId());
             }
             String payUrl =   DictUtils.getDictValue("支付地址","pay_url","https://wxapp.haohanshop.com/pay");
                accountResp.setPayUrl(payUrl);

            }
            respBody=JacksonUtils.toJson(accountResp);
            resp.setExt(respBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }

    }


    /**
     * 轮询支付结果
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPayResult")
    @ResponseBody
    public String queryPayResult(HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        String payStatus = "";

        try {
            TOrderQueryReq queryReq = commonApiService.fetchByHttpParams(request, TOrderQueryReq.class, resp);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            PartnerApp partnerApp = queryReq.getPartnerApp();
            secret = partnerApp.getAppSecret();

            String orderId = queryReq.getOrderId();
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            requiredParams.put("orderId", orderId);
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //从缓存中查询订单状态
            payStatus = JedisUtils.get("payStatus-".concat(orderId));

            //如果状态为空则从数据库查询
            if (StringUtils.isEmpty(payStatus)) {
                OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(orderId);
                payStatus = payRecord.getPayStatus();
                //从数据库查询的结果不为空则加入缓存
                if (StringUtils.isNotEmpty(payStatus)) {
                    JedisUtils.set("payStatus-".concat(orderId), payStatus, 3600);
                }
                //查询不到该条支付记录返回异常
                if (StringUtils.isEmpty(payStatus)) {
                    resp.error();
                    return resp.toJson();
                }
            }

            //payStatus 不为空则处理返回
            Map<String, Object> respMap = new HashMap<String, Object>();
            respMap.put("orderId", orderId);
            respMap.put("payStatus", payStatus);

            respBody = JacksonUtils.toJson(respMap);
            request.setAttribute("respBody", respBody);
            resp.setExt(respBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp, secret, respBody, request);
        }

    }
}
