package com.haohan.platform.service.sys.modules.xiaodian.api.service;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.entity.Entry;
import com.haohan.framework.utils.Des3Util;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.framework.utils.http.HttpUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.partner.PartnerAppService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgw on 2018/6/1.
 */
@Service
public class PartnerNotifyService {

    @Autowired
    private PartnerAppService partnerAppService;

    @Autowired
    private OrderPayRecordService orderPayRecordService;

    //通知给渠道厂商
    public BaseResp notifyPartner(String reqParams, String orderId, XmBankEnums.TransType transType){
        BaseResp baseResp = BaseResp.newError();
        String appKey =  JedisUtils.get(orderId);
        if(StringUtils.isEmpty(appKey)){
            baseResp.setMsg("appkey is empty!");
            return baseResp;
        }
        PartnerApp partnerApp = partnerAppService.findByAppKey(appKey);

        OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(orderId);

        String secret = partnerApp.getAppSecret();

        BaseResp resp = BaseResp.newSuccess();
        String notifyUrl = "";

        if (null  == partnerApp || StringUtils.isEmpty(secret) ){
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return resp;
        }

        if (StringUtils.isEmpty(orderPayRecord.getPartnerNotifyUrl())){
            notifyUrl = partnerApp.getNotifyUrl();
        }else {
            notifyUrl = orderPayRecord.getPartnerNotifyUrl();
        }

        //加密
        String reqBody = Des3Util.encrypt(reqParams, secret);
        resp.setExt(reqBody);

        Map<String,String> mapParams = new HashMap<>();
        mapParams.put("appkey",appKey);
        mapParams.put("params", JacksonUtils.toJson(resp));
        mapParams.put("transType",transType.getCode());
        if(StringUtils.isNotEmpty(notifyUrl)) {
            Entry<String,String> result = HttpUtils.post(notifyUrl, mapParams);
            JedisUtils.set(orderId,appKey,3600);//1小时后自动销毁
            if (StringUtils.equals(result.msg,"SUCCESS")){
                baseResp.success();
                return baseResp;
            }
        }
        return baseResp;
    }

}
