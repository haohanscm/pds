package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.aliyun.mns.model.Message;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.framework.utils.MD5Util;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankTransResult;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BSCPayResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderPayMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer.IProducer;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.MerchantInfoUpploadReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IMerchantApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOssFileManageBiz;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.AliyunMsgService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.CommonApiService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.*;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGalleryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.partner.PartnerAppService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import com.haohan.platform.service.sys.modules.xiaodian.util.OssUploadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant.*;

/**
 * 商家API接口
 * Created by zgw on 2017/12/10.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant")
public class MerchantApiCtrl extends BaseController {

    @Resource
    private IMerchantApiService merchantApiService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;
    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Resource
    private IOssFileManageBiz fileManageBiz;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private RefundManageService refundManageService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantAppExtService merchantAppExtService;
    @Autowired
    private PayNotifyService payNotifyService;
    @Autowired
    private MerchantAccountService merchantAccountService;
    @Autowired
    private CommonApiService commonApiService;
    @Autowired
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    private UPassportService uPassportService;
    @Autowired
    private PartnerAppService partnerAppService;
    @Autowired
    private PhotoGroupManageService photoGroupManageService;
    @Autowired
    private PhotoGalleryService photoGalleryService;
    @Autowired
    AliyunMsgService aliyunMsgService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private PhotoManageService photoManageService;

    @Autowired
    private AppPayRelationService appPayRelationService;
    @Autowired
    private IProducer producer;
//    MnsQueueMsgProducer mnsQueueMsgProducer = MnsQueueMsgProducer.getInstance();


    /**
     * 商家注册
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "reg")
    @ResponseBody
    public BaseResp reg(HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        try {
            //获取注册信息
            Map<String, Object> reqMap = CommonUtils.getRequestParameters(request);
            String name = (String) reqMap.get("name");
            String merchantType = (String) reqMap.get("merchantType");
            String merchantName = (String) reqMap.get("merchantName");
            String telephone = (String) reqMap.get("telephone");
            String password = (String) reqMap.get("password");
            String validCode = (String) reqMap.get("validCode");

            String openId = (String) reqMap.get("openId");
            String appId = (String) reqMap.get("appId");

            //验证注册状态
            resp = commonApiService.validRegStatus(resp,telephone);
            if (!resp.isSuccess()){
                return resp;
            }

            //验证参数
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            requiredParams.put("name",name);
            requiredParams.put("telephone", telephone);
            requiredParams.put("password", password);
            requiredParams.put("validCode", validCode);
            requiredParams.put("merchantType",merchantType);

            resp = paramsValid(requiredParams);
            //空值判断
            if (!resp.isSuccess()) {
                return resp;
            }

            //用户通行证绑定手机号
            UserOpenPlatform userOpenPlatform = userOpenPlatformService.fetchByAppIdAndOpenId(appId,openId);
            UPassport uPassport = null;
            if (null != userOpenPlatform){
                uPassport = uPassportService.get(userOpenPlatform.getUid());
                if (null != uPassport){
                    uPassport.setTelephone(telephone);
                    uPassportService.save(uPassport);
                }
            }

            //短信验证
            String code = JedisUtils.get(telephone);
            if(!StringUtils.equals(code,validCode)){
                resp.putStatus(RespStatus.ERROR);
                resp.setMsg("验证码错误,请重新获取!");
                return resp;
            }

            //密码安全性验证6~16位数字+字母组合
            String passwordParttern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
            Pattern parttern = Pattern.compile(passwordParttern);
            Matcher matcher = parttern.matcher(password);
            if (!matcher.matches()){
                resp.putStatus(RespStatus.ERROR);
                resp.setMsg("请输入6~16位英文和数字组合密码");
                return resp;
            }

            //保存商家账户信息
            password = MD5Util.MD5(password);
            MerchantAccount merchantAccount = new MerchantAccount();
            Merchant merchant = new Merchant();

            merchant.setUpassport(uPassport);
            merchant.setMerchantName(merchantName);
            merchant.setMerchantType(merchantType);
            merchant.setTelephone(telephone);
            merchant.setContact(name);
            merchant.setPartnerNum("001");
            merchant.setStatus(Integer.parseInt(IMerchantConstant.disabled));
            merchantService.save(merchant);
            merchantAccount.setMobile(telephone);
            merchantAccount.setPassword(password);
            merchantAccount.setLastLoginTime(new Date());
            merchantAccount.setMerchantId(merchant.getId());
            merchantAccount.setAuthCode(IMerchantConstant.AccountAuthCode.parentAccount.getCode());
            merchantAccount.setStep("0");
            merchantAccount.setAppKey(partnerAppService.fetchByPartnerNum(IMerchantConstant.terminalPartnerNum).getAppKey());
            merchantAccount.setMerchantId(merchant.getId());

            // 添加合作商信息
//            agentManageService.addMerchant(merchantAccount, merchant);
            merchantAccountService.save(merchantAccount);

            HashMap<String , String> respMap = new HashMap<>();
            respMap.put("merchantId",merchant.getId());
            respMap.put("telephone",merchant.getTelephone());
            resp.putStatus(RespStatus.SUCCESS);
            resp.setExt(respMap);
        } catch (Exception e) {
            e.printStackTrace();
            resp.putStatus(RespStatus.ERROR);
        }finally {
            return resp;
        }
    }


    @RequestMapping(value = "addShop")
    @ResponseBody
    public String addShop(Shop shop, HttpServletRequest request, HttpServletResponse response) {


        return "";
    }

    @RequestMapping(value = "fetchShopInfo")
    @ResponseBody
    public String fetchShopInfo(HttpServletRequest request,HttpServletResponse response){
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            Map<String , Object> reqMap = CommonUtils.getRequestParameters(request);
            String appkey =(String)reqMap.get("appkey");
            PartnerApp partnerApp = partnerAppService.findByAppKey(appkey);
            List<Merchant> merchantList = null;
            List<Shop> shopList = new ArrayList<>();
            if (null == partnerApp) {
                resp.setMsg("appkey不合法");
                return resp.toJson();
            }
            secret = partnerApp.getAppSecret();
            Merchant merchant = new Merchant();
            merchant.setPartnerNum(partnerApp.getPartnerNum());
            merchantList = merchantService.findList(merchant);
            if (CollectionUtils.isEmpty(merchantList)){
                resp.setMsg("该渠道商无商户");
                return resp.toJson();
            }
            for (Merchant mer : merchantList){
                List<Shop> list =  shopService.fetchByMerchantId(mer.getId());
                shopList.addAll(list);
            }

            resp.success();
            respBody = JacksonUtils.toJson(shopList);

        } catch (Exception e) {
            e.printStackTrace();
            resp.error();
        } finally {
            request.setAttribute("respBody", respBody);
            return  commonApiService.respJson(resp,secret,respBody,request);
        }

    }


    @RequestMapping(value = "fetchValidCode")
    @ResponseBody
    public BaseResp fetchValidCode(HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        try {
            //获取注册信息
            Map<String, Object> respMap = CommonUtils.getRequestParameters(request);
            String telephone = (String) respMap.get("telephone");

            //生成随机四位验证码
            String code = String.valueOf((new Random().nextInt(8999))+1000);

            //验证参数
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            requiredParams.put("telephone", telephone);
            resp = paramsValid(requiredParams);

            //错误处理
            if (!resp.isSuccess()) {
                return resp;
            }

            if (resp.isSuccess()){
                //设置缓存,有效时间600秒
                JedisUtils.set(telephone,code,600);
                resp = aliyunMsgService.sendRegMsg(telephone, code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resp;
        }

    }

    /**
     * 查询用户是否注册
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchRegStep")
    @ResponseBody
    public BaseResp fetchRegStep(HttpServletRequest request){
        BaseResp resp = BaseResp.newError();

        try {
            Map<String , Object> reqMap = CommonUtils.getRequestParameters(request);
            String telephone = (String) reqMap.get("telephone");

            //参数验证
            Map<String,Object> requiredParams = new HashMap<>();
            requiredParams.put("telephone",telephone);
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()){
                return resp;
            }

            MerchantAccount account = merchantAccountService.fetchByMobile(telephone);
            resp.setExt(account.getStep());

        } catch (Exception e) {
            e.printStackTrace();
            resp.error();
        } finally {
            return resp;
        }
    }


    /**
     * 商家资料提交
     *
     * @param
     * @return
     */
    @RequestMapping(value = "merchantProfileUpload")
    @ResponseBody
    public BaseResp merchantProfileUpload(@RequestBody MerchantInfoUpploadReq uploadReq) {
        BaseResp resp = BaseResp.newError();
        String step = "";
        String nextStep = "";
        try {

            String[] licenseIds = uploadReq.getLicense();
            String[] cateringLicenseIds = uploadReq.getCateringLicense();
            String[] shopPhotosIds = uploadReq.getShopPhotos();
            String[] idCardsIds = uploadReq.getIdCards();
            String[] bankCardIds = uploadReq.getBankCard();

            String merchantId = uploadReq.getMerchantId();
            step = uploadReq.getStep();
            String isCover = uploadReq.getIsCover();   //是否覆盖

            //验证参数
            Map<String,Object> requiredParams = new HashMap<>();
            requiredParams.put("merchantId",merchantId);
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()){
                return resp;
            }

            Merchant merchant = merchantService.get(merchantId);
            if (null == merchant){
                resp.error();
                resp.setMsg("上传失败,商户号不合法");
                return resp;
            }

            String licenseGroupNum = merchantId.concat("-").concat(IMerchantConstant.MerchantFilesType.license.getGroupNum());
            String cateringGroupNum = merchantId.concat("-").concat(IMerchantConstant.MerchantFilesType.cateringLicense.getGroupNum());
//            String merchantGroupNum = merchantId.concat("-").concat(IMerchantConstant.MerchantFilesType.MerchantPhotos.getGroupNum());
            String shopPhotoGroupNum = merchantId.concat("-").concat(IMerchantConstant.MerchantFilesType.ShopPhotos.getGroupNum());
            String idCardsGroupNum = merchantId.concat("-").concat(IMerchantConstant.MerchantFilesType.idCardPhotos.getGroupNum());
            String bankCardsGroupNum = merchantId.concat("-").concat(IMerchantConstant.MerchantFilesType.bankCardPhotos.getGroupNum());

            //1 获取营业执照图片组
            PhotoGroupManage licenseManage = new PhotoGroupManage();
            getPhotoGroupManage(licenseManage,merchantId,licenseIds,licenseGroupNum, IMerchantConstant.MerchantFilesType.license.getGroupName());

            //2 获取餐饮许可证图片组
            PhotoGroupManage cateringManage = new PhotoGroupManage();
            getPhotoGroupManage(cateringManage,merchantId,cateringLicenseIds,cateringGroupNum, IMerchantConstant.MerchantFilesType.cateringLicense.getGroupName());

//            //获取商户图片组
//            PhotoGroupManage merchantPhotoManage = new PhotoGroupManage();
//            String[] newAry = ArrayUtils.addAll(ArrayUtils.addAll(idCardsIds,bankCardIds),licenseIds);
//            getPhotoGroupManage(merchantPhotoManage,merchantId,newAry,merchantGroupNum,IMerchantConstant.MerchantFilesType.MerchantPhotos.getGroupName());

            //3 获取门店图片组
            PhotoGroupManage shopPhotoManage = new PhotoGroupManage();
            getPhotoGroupManage(shopPhotoManage,merchantId,shopPhotosIds,shopPhotoGroupNum, IMerchantConstant.MerchantFilesType.ShopPhotos.getGroupName());

            //4 获取身份证图片组
            PhotoGroupManage idCardsManage = new PhotoGroupManage();
            getPhotoGroupManage(idCardsManage,merchantId,idCardsIds,idCardsGroupNum, IMerchantConstant.MerchantFilesType.idCardPhotos.getGroupName());

            //5 获取结算卡或对公账户图片组
            PhotoGroupManage bankCardManage = new PhotoGroupManage();
            getPhotoGroupManage(bankCardManage,merchantId,bankCardIds,bankCardsGroupNum, IMerchantConstant.MerchantFilesType.bankCardPhotos.getGroupName());

//            //变更商家状态为待审核
//            if (IMerchantConstant.MerchantRegStep.success.equals(step)){
//                merchant.setStatus(Integer.parseInt(IMerchantConstant.toAduit));
//                merchantService.save(merchant);
//            }

            if (null != licenseManage){
                saveMerchantPhotos(licenseGroupNum,licenseManage);
            }
            if (null != cateringManage){
                saveMerchantPhotos(cateringGroupNum,cateringManage);
            }
            if (null != shopPhotoManage){
                saveMerchantPhotos(shopPhotoGroupNum,shopPhotoManage);
            }
            if (null != idCardsManage){
                saveMerchantPhotos(idCardsGroupNum,idCardsManage);
            }
            if (null != bankCardManage){
                saveMerchantPhotos(bankCardsGroupNum,bankCardManage);
            }

            //零售商家跳过上传餐饮许可证
            if (merchant.getMerchantType().equals(IMerchantConstant.MerchantShopType.retail.getType())&&step.equals(IMerchantConstant.MerchantRegStep.one.getStepCode())){
                nextStep = IMerchantConstant.MerchantRegStep.three.getStepCode();
            } else {
                if (Integer.parseInt(step)==5){
                    nextStep = IMerchantConstant.MerchantRegStep.success.getStepCode();
                    merchant.setStatus(Integer.parseInt(IMerchantConstant.toAduit));
                    merchantService.save(merchant);
                }else {
                    nextStep = String.valueOf(Integer.parseInt(step)+1);
                }
            }

            //记录注册步骤
            MerchantAccount merchantAccount = merchantAccountService.fetchByMerchantId(merchantId);
            if (null != merchantAccount){
                merchantAccount.setStep(nextStep);
                merchantAccountService.save(merchantAccount);
            }

            if (resp.isSuccess()){
                resp.setExt(nextStep);
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
            resp.putStatus(RespStatus.ERROR);
            resp.setMsg("step有误!");
        } catch (Exception e) {
            e.printStackTrace();
            resp.putStatus(RespStatus.ERROR);
            resp.setExt(step);
        }  finally {
            return resp;
        }
    }


    @RequestMapping(value = "extinfo/{appId}")
    @ResponseBody
    public String extinfo(@PathVariable("appId") String appId, HttpServletRequest request, HttpServletResponse response) {
        BaseResp baseResp = new BaseResp();
        try {
            if (StringUtils.isEmpty(appId)) {
                baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
            }
            Map<String, Object> mapParams = merchantAppExtService.fetchAppExtInfo(appId);
            // 增加返回参数 即速应用Id、商家账号、阿拉丁appkey、发布版本号、发布版本说明
            MerchantAppManage merchantAppManage = merchantAppManageService.fetchByAppId(appId);
            if(merchantAppManage!=null){
                mapParams.put("jisuAppId",merchantAppManage.getJisuAppId());
                MerchantAppExtInfo merchantAppExtInfo = merchantAppManage.fromExt();
                mapParams.put("partnerId",merchantAppExtInfo.getPartnerId());
                mapParams.put("aladId",merchantAppExtInfo.getAladId());
//                mapParams.put("versionNo",merchantAppExtInfo.getVersionNo());
//                mapParams.put("versionDesc",merchantAppExtInfo.getVersionDesc());
            }
            baseResp.putStatus(RespStatus.SUCCESS);
            baseResp.setExt(JacksonUtils.toJson(mapParams));
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.putStatus(RespStatus.ERROR);
        } finally {
            return baseResp.toJson();
        }


    }


    /**
     * 商家支付
     *
     * @param payRecord
     * @return
     */

    @RequestMapping(value = "pay")
    @ResponseBody
    String pay(OrderPayRecord payRecord, HttpServletRequest request) {

        BaseResp baseResp = BaseResp.newError();
        try {
            //验证参数
            String orderId = payRecord.getOrderId();

            //验证已存在的订单信息
            if (StringUtils.isNotBlank(orderId)) {
                OrderPayRecord record = orderPayRecordService.fetchByOrderId(orderId);
                if (null != record) {
                    if (BankServiceStatus.SUCCESS.getCode().equalsIgnoreCase(record.getRespCode())) {
                        baseResp = BaseResp.newSuccess();
                        baseResp.setExt(record.getPayInfo());
                        return baseResp.toJson();
                    } else {
                        baseResp = BaseResp.newError();
                        baseResp.setMsg(record.getRespDesc());
                        return baseResp.toJson();
                    }
                }
            }

            Map<String, Object> requiredParams = new HashMap<String, Object>();
            String shopId = payRecord.getShopId();

            String orderType = payRecord.getOrderType();
            String clientIp = IpUtils.getRemoteIpAddr(request);
            String payChannel = payRecord.getPayChannel();
            String payType = payRecord.getPayType();
            String goodsName = payRecord.getGoodsName();
            String partnerId = payRecord.getPartnerId();
            BigDecimal orderAmount = payRecord.getOrderAmount();
            String merchantId = payRecord.getMerchantId();
            //非必要参数
            String orderInfo = payRecord.getOrderInfo();
            //HTML处理
            payRecord.setOrderInfo(HtmlUtils.htmlUnescape(orderInfo));

            String authCode = payRecord.getAuthCode();
            String openId = payRecord.getOpenid();
            String appId = payRecord.getAppid();

            requiredParams.put("shopId", shopId);
//            requiredParams.put("partnerId", partnerId);
            requiredParams.put("orderId", orderId);
            requiredParams.put("orderType", orderType);
            requiredParams.put("payChannel", payChannel);
            requiredParams.put("payType", payType);
            requiredParams.put("goodsName", goodsName);
            requiredParams.put("orderAmount", orderAmount);

            BankServiceType serviceType = BankServiceType.valueOfServiceType(payType);

            if (BankServiceType.AliAuthPay == serviceType || BankServiceType.WexinAuthPay == serviceType) {
                requiredParams.put("authCode", authCode);
            }
            if (BankServiceType.WexinMpPay == serviceType || BankServiceType.AliServicePay == serviceType || BankServiceType.WexinAppPay == serviceType) {
                requiredParams.put("openId", openId);
                requiredParams.put("appId", appId);
            }

            //错误处理
            baseResp = paramsValid(requiredParams);
            if (!baseResp.isSuccess()) {
                return baseResp.toJson();
            }

            Integer sucCode = BankRegStatus.SUCCESS.getCode();
            Merchant merchant = merchantService.get(merchantId);

            MerchantPayInfo payInfo = null;
            if (StringUtils.isBlank(appId)){
                payInfo = merchantPayInfoService.fetchByMerchantIdEnable(merchantId, null);
            }else {
                AppPayRelation appPayRelation = appPayRelationService.fetchByAppId(appId);
                MerchantAppManage appManage = merchantAppManageService.fetchByAppId(appId);
                if (null != appManage){
                    merchant = merchantService.get(appManage.getMerchantId());
                }
                if (null == appPayRelation){
                    baseResp.error();
                    baseResp.setMsg("APP未配置支付账户");
                    return baseResp.toJson();
                }
                payInfo = merchantPayInfoService.fetchByPartnerId(appPayRelation.getPartnerId());
            }
            if (null == payInfo || !sucCode.equals(payInfo.getRegStatus())) {
                baseResp.setCode(900);
                baseResp.setMsg("账户未启用");
                return baseResp.toJson();
            }
            payRecord.setMerchantName(merchant.getMerchantName());
            //验证商户状态
            if (!sucCode.equals(merchant.getStatus())) {
                baseResp.setCode(900);
                baseResp.setMsg("商户未启用");
                return baseResp.toJson();
            }
            payRecord.setPartnerId(payInfo.getPartnerId());

            //设置小店产品服务购买类型
            if (StringUtils.isNotEmpty(orderInfo) && orderInfo.contains("微信小程序")) {
                OrderPayParamsExt paramsExt = payRecord.fromOrderInfo();
                if (null != paramsExt) {
                    Map<String, String> attrMap = paramsExt.fromAttr();
                    if (null != attrMap) {
                        String modelName = attrMap.get("模板类型");
                        String type = DictUtils.getDictValue(modelName, "order_type", null);
                        if (StringUtils.isNotEmpty(type)) {
                            payRecord.setOrderType(type);
                        }
                    }
                }
            }

            Shop shop = shopService.get(shopId);
            if (null == shop) {
                shop = shopService.fetchByShopCode(shopId);
            }
            //填充参数
            if (null != shop) {
                String shopName = shop.getName();
                payRecord.setShopId(shop.getId());
                payRecord.setShopName(shopName);
            }

            String requestId = CommonUtils.genId(payType);

            payRecord.setClientIp(clientIp);
            payRecord.setRequestId(requestId);
            payRecord.setLimitPay("2");//默认支持信用卡
            payRecord.setOrderTime(new Date());
            payRecord.setPayStatus(PayStatus.NOPAY.getCode());
            payRecord.setNotifyUrl(XmBankConfigUtil.getPayNotifyUrl());

            baseResp = merchantApiService.pay(payRecord);

            //订单超时关闭队列
            Message closeOrderMsg = new Message(orderId);
            closeOrderMsg.setDelaySeconds(3600);
            OrderMsgEntity orderMsgEntity = new OrderMsgEntity();
            orderMsgEntity.setOrderId(orderId);
            orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.ORDER_CLOSE.getTagName());
            producer.sendDelay(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity,9);

            if (baseResp.isSuccess()) {
                OrderPayRecord record = (OrderPayRecord) baseResp.getExt();
                baseResp.setExt(record.getPayInfo());
                //保存订单数据到商品订单表
                commonApiService.copyDataToGoodsOrder(payRecord);
                if (StringUtils.isNotEmpty(payRecord.getOrderDetail())){
                    //商品详情解析
                    payRecord.setOrderDetail(StringEscapeUtils.unescapeHtml3(payRecord.getOrderDetail()));
                    transToGoodsDetail(JacksonUtils.readListValue(payRecord.getOrderDetail(), GoodsOrderDetail.class),payRecord);
                }

                //被扫支付直接推送支付结果
                if (payRecord.getPayType().equals(BankServiceType.WexinAuthPay.getCode())||payRecord.getPayType().equals(BankServiceType.AliAuthPay.getCode())){
                    BSCPayResponse bscPayResponse = JacksonUtils.readValue(record.getPayInfo(),BSCPayResponse.class);
                    if (StringUtils.equals(bscPayResponse.getTransResult(),XmBankTransResult.SUCCESS.getResponseCode())){
                        orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                        producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                        goodsOrderService.updatePayStatus(orderId, PayStatus.SUCCESS);
                    } else if (StringUtils.equals(bscPayResponse.getTransResult(),XmBankTransResult.UNKNOEW.getResponseCode())){
                        //bsc超过一定限额时无法自动扣款,需轮询交易状态
                        OrderPayMsgEntity orderPayMsgEntity = new OrderPayMsgEntity();
                        orderMsgEntity.setMqTags(IRocketMqConstant.PayMsgTag.UNKNOW_PAY_STATUS.getTagName());
                        orderPayMsgEntity.setRetryTimes(0);
                        producer.send(IRocketMqConstant.TopicType.PAY.getName(),orderPayMsgEntity);
                    }
                }
                return baseResp.toJson();
            }
            return baseResp.toJson();
        } catch (Exception e) {
            return BaseResp.newError().toJson();
        }
//        finally {
//            logger.debug("---resp---\n{}", baseResp.toJson());
//        }
    }


    /**
     * 商家订单撤销
     *
     * @param orderCancel
     * @return
     */
    @RequestMapping(value = "orderCancel")
    @ResponseBody
    String orderCancel(OrderCancel orderCancel) {

        try {
            //验证参数

            Map<String, Object> requiredParams = new HashMap<String, Object>();

            String orderId = orderCancel.getOrderId();

            String merchantId = orderCancel.getMerchantId();

            requiredParams.put("orderId", orderId);
            requiredParams.put("merchantId", merchantId);

            //错误处理
            BaseResp resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(orderId);
            if (null == payRecord) {
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }

            PayNotify payNotify = payNotifyService.fetchByOrderId(orderId);
            if (null == payNotify) {
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }

            orderCancel.setPartnerId(payRecord.getPartnerId());
            orderCancel.setRequestId(payRecord.getRequestId());
            orderCancel.setMerchantName(payRecord.getMerchantName());
            orderCancel.setRemarks(payRecord.getGoodsName());
            if (StringUtils.isEmpty(orderCancel.getOrgTransId())) {
                orderCancel.setOrgReqId(payNotify.getTransId());
            }

            BaseResp baseResp = merchantApiService.orderCancel(orderCancel);
            if (baseResp.isSuccess()) {
                payRecord.setPayStatus(PayStatus.NOPAY.getCode());
                orderPayRecordService.save(payRecord);
            }

            return baseResp.toJson();
        } catch (Exception e) {

            return BaseResp.newError().toJson();
        }

    }


    /**
     * 商家退款
     *
     * @param refundManage
     * @return
     */
    @RequestMapping(value = "orderRefund")
    @ResponseBody
    String orderRefund(RefundManage refundManage) {

        try {
            //验证参数

            Map<String, Object> requiredParams = new HashMap<String, Object>();

            String partnerId = refundManage.getPartnerId();
            String orderId = refundManage.getOrderId();
            requiredParams.put("orderId", orderId);
            requiredParams.put("partnerId", partnerId);

            //错误处理
            BaseResp resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //检查订单支付状态
            OrderQuery query = orderQueryService.checkPayStatus(orderId, XmBankEnums.TransType.refund);

            if (null == query) {
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }
            if (!StringUtils.equalsAnyIgnoreCase(query.getPayResult(), TransStatus.SUCCESS.getCode())) {
                resp.setMsg("订单未支付");
                resp.setCode(900);
                return resp.toJson();
            }
            OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(orderId);
//            refundManage = refundManageService.fetchByOrderId(orderId);
            //先查询Notify
            PayNotify notify = payNotifyService.fetchByOrderId(refundManage.getOrderId());
            if (null != notify) {
                refundManage.setId(IdGen.uuid());
                refundManage.setOrgTransId(notify.getTransId());
                refundManage.setOrgReqId(notify.getRequestId());
                refundManage.setOrderAmount(orderPayRecord.getOrderAmount());
                refundManage.setPayAmount(orderPayRecord.getOrderAmount());
                refundManage.setRefundAmount(orderPayRecord.getOrderAmount());

                resp = merchantApiService.refund(refundManage);

                if (resp.isSuccess()){
                    refundManage.setStatus(RefundStatus.REFUNDED.getCode());
                    orderPayRecord.setPayStatus(PayStatus.REFUNDED.getCode());
                    orderPayRecordService.save(orderPayRecord);
                    refundManageService.save(refundManage);
                    goodsOrderService.updatePayStatus(orderPayRecord.getOrderId(), PayStatus.REFUNDED);
                }else {
                    if (!StringUtils.equals(orderPayRecord.getPayStatus(), PayStatus.REFUNDED.getCode())){
                        orderPayRecord.setPayStatus(PayStatus.CHECK.getCode());
                        orderPayRecordService.save(orderPayRecord);
                    }
                }

            }
            return resp.toJson();
        } catch (Exception e) {
            return BaseResp.newError().toJson();
        }

    }


    /**
     * 交易状态查询
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "orderQuery")
    @ResponseBody
    String orderQuery(OrderQuery query) {

        logger.debug("---req---\n{}", JacksonUtils.toJson(query));

        BaseResp resp = BaseResp.newError();
        try {
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            String partnerId = query.getPartnerId();
            String orderId = query.getOrderId();
            requiredParams.put("orderId", orderId);
            requiredParams.put("partnerId", partnerId);

            //错误处理
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            XmBankEnums.TransType transType = XmBankEnums.TransType.consume;
            if (StringUtils.equalsAny(XmBankEnums.TransType.refund.getCode() + "", query.getTransType())) {
                transType = XmBankEnums.TransType.refund;
            }
            //查询银行接口
            OrderQuery queryResp = orderQueryService.checkPayStatus(query.getOrderId(), transType);

            resp.setExt(queryResp);

            return resp.toJson();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.debug("---resp---\n{}", resp.toJson());
            return resp.toJson();
        }
    }


    @RequestMapping("photo")
    @ResponseBody
    public BaseResp uploadPhoto(@RequestParam("file") MultipartFile files, String id, String parent, @RequestParam(defaultValue = "false") Boolean keepName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        BaseResp resp = BaseResp.newError();

        try {
            if (null == files) {
                resp.putStatus(RespStatus.ERROR);
                return resp;
            }
            HashMap<String, String> resultMap = new HashMap<>();
            PhotoGallery photoGallery = new PhotoGallery();
            if(StringUtils.isEmpty(id)){
                id= IdGen.uuid();
            }
            if(StringUtils.isEmpty(parent)){
                parent= "00";
            }
            if (keepName == null){
                keepName = false;
            }

            resp = OssUploadUtils.upload(id, files, parent, keepName);
            if(resp.isSuccess()) {
                //保存图片资源库
                photoGalleryService.transfPhoto(photoGallery, String.valueOf(files.getSize()), resp.getExt().toString(), resp.getMsg(), parent);
                photoGallery.setPicFrom("merchantUpload");
                photoGalleryService.save(photoGallery);

                //返回图片库id,src
                resultMap.put("src",resp.getMsg());
                resultMap.put("photoGalleryId",photoGallery.getId());
                resp.setExt(resultMap);
                return resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    @RequestMapping("getStaticQrCodePath")
    @ResponseBody
    public BaseResp getStaticQrCodePath(HttpServletRequest request,HttpServletResponse response){
        BaseResp resp = BaseResp.newError();

        String merchantId = request.getParameter("merchantId");
        PhotoGroupManage photoGroupManage = null;
        if (StringUtils.isNotEmpty(merchantId)){
            photoGroupManage = photoGroupManageService.fetchPhotoGroupManage(merchantId,merchantId.concat("-11"));
        }
        if (null == photoGroupManage){
            return resp;
        }
        PhotoManage photoManage = photoGroupManage.getPhotoManageList().get(0);
        if (null == photoManage){
            return resp;
        }
        resp.success();
        resp.setExt(photoManage.getPicUrl());
        return resp;
    }

    private void transToGoodsDetail(List<GoodsOrderDetail> detailList, OrderPayRecord payRecord){
        for(GoodsOrderDetail detail:detailList){
            detail.setOrderId(payRecord.getOrderId());
            goodsOrderDetailService.save(detail);
        }
    }

    /**
     * 已上传的图片分组
     * @param groupManage
     * @param merchantId
     * @param photoGalleryIds
     * @param groupNum
     * @param groupName
     * @return
     */
    private PhotoGroupManage getPhotoGroupManage(PhotoGroupManage groupManage, String merchantId, String[] photoGalleryIds, String groupNum, String groupName){
        List<PhotoManage> photoList = new ArrayList<>();
        if (null == photoGalleryIds){
            return null;
        }
        for (String id:photoGalleryIds){
            if (StringUtils.isEmpty(id)){
                return null;
            }
            PhotoManage photoManage = new PhotoManage();
            photoManage.setPhotoGallery(photoGalleryService.get(id));
            photoList.add(photoManage);
        }
        groupManage.setGroupNum(groupNum);
        groupManage.setMerchantId(merchantId);
        groupManage.setGroupName(groupName);
        groupManage.setPhotoManageList(photoList);
        return groupManage;
    }

    private void saveMerchantPhotos(String groupNum, PhotoGroupManage photoGroupManage){
        List<PhotoManage> licensePhotoList = photoManageService.fetchByGroupNum(groupNum);
        if (CollectionUtils.isNotEmpty(licensePhotoList)){
            //已存在则先删除再保存
            for (PhotoManage photo : licensePhotoList){
                photoManageService.delete(photo);
            }
        }
        photoGroupManageService.save(photoGroupManage);
    }

}
