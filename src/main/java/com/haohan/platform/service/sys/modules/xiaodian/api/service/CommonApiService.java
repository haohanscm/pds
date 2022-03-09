package com.haohan.platform.service.sys.modules.xiaodian.api.service;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.Des3Util;
import com.haohan.framework.utils.IpUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.framework.utils.MD5Util;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.JGPushUtil;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.BaseParams;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.TAppInfoResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.TShopDto;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAccount;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayParamsExt;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAccountService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.partner.PartnerAppService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgw on 2018/6/11.
 */
@Service
public class CommonApiService {

    @Autowired
    private PartnerAppService partnerAppService;
    @Resource
    private MerchantPayInfoService merchantPayInfoService;
    @Resource
    private MerchantService merchantService;
    @Resource
    private ShopService shopService;
    @Resource
    private MerchantAccountService merchantAccountService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private GoodsOrderDetailService goodsOrderDetailService;
    @Resource
    private OrderPayRecordService orderPayRecordService;


    public static final String loginPreStr = "login:sid-";


    public <T extends BaseParams> T fetchByHttpParams(HttpServletRequest request, Class<T> clazz, BaseResp resp) {

        Map<String, Object> respMap = CommonUtils.getRequestParameters(request);

        String appkey = (String) respMap.get("appkey");

        //通过appKey获取secret

        PartnerApp partnerApp = partnerAppService.findByAppKey(appkey);


        if (null == partnerApp || StringUtils.isEmpty(partnerApp.getAppSecret())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return null;
        }
        String secret = partnerApp.getAppSecret();

        if (!IMerchantConstant.available.equals(partnerApp.getStatus())) {
            resp.setCode(901);
            resp.setMsg("应用未启用");
            return null;
        }
        String params = (String) respMap.get("params");

        //解密
        String reqBody = Des3Util.decrypt(params, secret);

        request.setAttribute("reqBody", reqBody);
        request.setAttribute("partnerApp", partnerApp);

        resp.putStatus(RespStatus.SUCCESS);

        //JSON转Bean
        T t = JacksonUtils.readValue(reqBody, clazz);
        if(null == t){
            resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
        }
        if (null != t) {
            t.setPartnerApp(partnerApp);
        }

        return t;
    }


    public BaseResp validMerchantInfo(String partnerId, BaseResp resp, OrderPayRecord payRecord) {

        //验证账户信息
        Integer sucCode = IBankServiceConstant.BankRegStatus.SUCCESS.getCode();

        MerchantPayInfo payInfo = merchantPayInfoService.fetchByPartnerId(partnerId);

        if (null == payInfo || !sucCode.equals(payInfo.getRegStatus())) {
            resp.setCode(900);
            resp.setMsg("账户未启用");
            return resp;
        }
        //验证商户状态

        Merchant merchant = merchantService.get(payInfo.getMerchantId());
        if (null == merchant || !sucCode.equals(merchant.getStatus())) {
            resp.setCode(901);
            resp.setMsg("商户未启用");
            return resp;
        }
        payRecord.setMerchantId(payInfo.getMerchantId());
        payRecord.setMerchantName(merchant.getMerchantName());
        payRecord.setBankChannel(payInfo.getBankChannel());

        return resp;
    }


    public String respJson(BaseResp resp, String secret, String respBody, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(secret)) {
            request.setAttribute("respBody", respBody);
            String respExt = Des3Util.encrypt(respBody, secret);
            resp.setExt(respExt);
            return resp.toJson();
        }
        return resp.toJson();
    }


    /**
     * login success
     *
     * @param merchantAccount
     */
    public String loginSuccess(MerchantAccount merchantAccount) throws Exception {

        String sid = loginPreStr.concat(IdGen.uuid());
        JedisUtils.set(sid, merchantAccount.getId(), 0);
        return sid;

    }


    public BaseResp validMerchantAccount(MerchantAccount merchantAccount, BaseResp resp, HttpServletRequest request, String password) throws Exception {

        if (null == merchantAccount) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return resp;
        }

        //验证密码是否正确
        if(StringUtils.isNotEmpty(password)) {
            String pwd = MD5Util.MD5(password);
            if (!StringUtils.equals(pwd, merchantAccount.getPassword())) {
                resp.error();
                resp.setMsg("密码错误");
                return resp;
            }
        }

        String appKey = merchantAccount.getAppKey();
        String partnerId = merchantAccount.getPartnerId();
        // 查询对应的app信息
        PartnerApp partnerApp = partnerAppService.findByAppKey(appKey);
        //  生成SID 并存储
        String sid = loginSuccess(merchantAccount);

        merchantAccount.setSid(sid);
        merchantAccount.setLastLoginIp(IpUtils.getRemoteIpAddr(request));
        merchantAccount.setLastLoginTime(new Date());
        merchantAccount.setLastLoginArea("");

        TAppInfoResp infoResp = new TAppInfoResp();
        infoResp.setSid(sid);
        infoResp.setAppKey(appKey);
        infoResp.setMerchantId(merchantAccount.getMerchantId());
        infoResp.setAppSecret(partnerApp.getAppSecret());
        infoResp.setPartnerId(merchantAccount.getPartnerId());

        List<TShopDto> shops;

        //总部账户可查看所有店铺
        if (IMerchantConstant.AccountAuthCode.parentAccount.getCode().equals(merchantAccount.getAuthCode())){
            shops = shopService.selectByMerchant(merchantAccount.getMerchantId());
        }else {
            //子店账户只能查看自己店铺
            shops = shopService.selectById(merchantAccount.getShopId());
        }

        //没有创建店铺时返回错误
        if (CollectionUtils.isEmpty(shops)){
            resp.error();
            resp.setMsg("账号未绑定店铺");
            return resp;
        }
        infoResp.setShops(shops);
        infoResp.setMerchantName(merchantService.get(merchantAccount.getMerchantId()).getMerchantName());

        resp.putStatus(RespStatus.SUCCESS);
        resp.setExt(JacksonUtils.toJson(infoResp));
        return resp;
    }

    /**
     * 验证是否注册
     * @param resp
     * @param telephone
     * @return
     */
    public BaseResp validRegStatus(BaseResp resp,String telephone){
        if (StringUtils.isNotEmpty(telephone)){
            MerchantAccount merchantAccount = merchantAccountService.fetchByMobile(telephone);
            if (null!=merchantAccount){
                resp.putStatus(RespStatus.ERROR);
                resp.setMsg("该手机号已注册,请直接登录");
                return resp;
            }
            resp.success();
        }
        return resp;
    }

    /**
     * 极光推送订单支付结果
     *
     */
    public String buildJgPayResultMsg(GoodsOrder goodsOrder){
        //推送已完成订单到终端
        OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(goodsOrder.getOrderId());
        Map<String,Object> jPushMap = new HashMap<>();
        jPushMap.put("action","notify/payStatusNotify");
        if (StringUtils.isNotEmpty(goodsOrder.getOrderInfo())){
            jPushMap.put("orderInfo",JacksonUtils.readValue(HtmlUtils.htmlUnescape(goodsOrder.getOrderInfo()),OrderPayParamsExt.class));
        }
        jPushMap.put("orderDetail",goodsOrderDetailService.findByOrderId(goodsOrder.getOrderId()));
        jPushMap.put("orderId",goodsOrder.getOrderId());
        jPushMap.put("reqId",goodsOrder.getPayId());
        jPushMap.put("payStatus",goodsOrder.getPayStatus());
        jPushMap.put("orderAmount",goodsOrder.getOrderAmount());
        jPushMap.put("payChannel",orderPayRecord.getPayChannel());
        jPushMap.put("orderTime",goodsOrder.getOrderTime());
        String jPushMsg = JacksonUtils.toJson(jPushMap);
        return jPushMsg;

//        JGPushUtil.pushMsgHhf("alias",record.getShopId(),jPushMsg);
    }

    /**
     * 极光推送新订单提醒
     */
    public String buildJgNewOrderMsg(GoodsOrder goodsOrder){
        Map<String,String> jPushMap = new HashMap<>();
        jPushMap.put("action","notify/newOrderNotify");
        jPushMap.put("orderId",goodsOrder.getOrderId());
        String jPushMsg = JacksonUtils.toJson(jPushMap);
        return jPushMsg;
//        JGPushUtil.pushMsgHhf("alias",record.getShopId(),jPushMsg);
    }

    public void sendJgMsg(GoodsOrder goodsOrder, String target){
        //判断店铺的设备是否开启接收推送 TODO
        String jgMsg1 = buildJgPayResultMsg(goodsOrder);
        String jgMsg2 = buildJgNewOrderMsg(goodsOrder);
        JGPushUtil.pushMsgHhf(JGPushUtil.ALIAS,target,jgMsg1);
        JGPushUtil.pushMsgHhf(JGPushUtil.ALIAS,target,jgMsg2);
        JGPushUtil.pushMsgHhfhd(JGPushUtil.ALIAS,target,jgMsg1);
        JGPushUtil.pushMsgHhfhd(JGPushUtil.ALIAS,target,jgMsg2);
    }

    /**
     * 同步商品订单数据
     * @param orderPayRecord
     */
    public void copyDataToGoodsOrder(OrderPayRecord orderPayRecord) throws Exception{
        String orderId = orderPayRecord.getOrderId();
        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        boolean isNew = false;
        String id = "";
        if (null == goodsOrder){
            goodsOrder = new GoodsOrder();
            goodsOrderService.save(goodsOrder);
        }
        id = goodsOrder.getId();
        BeanUtils.copyProperties(goodsOrder,orderPayRecord);
        goodsOrder.setIsNewRecord(isNew);
        goodsOrder.setId(id);
        goodsOrder.setPayId(orderPayRecord.getRequestId());
        goodsOrder.setPayStatus(orderPayRecord.getPayStatus());
        if (StringUtils.isNotEmpty(orderPayRecord.getOrderInfo())){
            OrderPayParamsExt orderPayParamsExt = orderPayRecord.fromOrderInfo();
            if (null != orderPayParamsExt){
                goodsOrder.setUid(orderPayParamsExt.getUid());
                goodsOrder.setUserName(orderPayParamsExt.getUserName());
            }
        }
        goodsOrder.setOrderTime(orderPayRecord.getOrderTime());
        goodsOrder.setPayTime(orderPayRecord.getOrderTime());
        if (BankServiceType.CashPay.getCode().equals(orderPayRecord.getPayType())){
            goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.FINISHED.getCode());
        } else {
            goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_PAY.getCode());
        }
        goodsOrder.setOrderInfo(orderPayRecord.getOrderInfo());
        goodsOrder.setOrderDesc(orderPayRecord.getGoodsName());
        goodsOrderService.save(goodsOrder);

    }

    public void transToGoodsDetail(String goodsDetail,String orderId){
        List<GoodsOrderDetail> detailList = JacksonUtils.readListValue(StringEscapeUtils.escapeJson(goodsDetail), GoodsOrderDetail.class);
        for(GoodsOrderDetail detail:detailList){
            detail.setOrderId(orderId);
            goodsOrderDetailService.save(detail);
        }
    }

}
